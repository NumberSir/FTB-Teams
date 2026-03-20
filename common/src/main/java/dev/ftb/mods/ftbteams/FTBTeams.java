package dev.ftb.mods.ftbteams;

import com.mojang.brigadier.CommandDispatcher;
import de.marhali.json5.Json5Object;
import dev.ftb.mods.ftblibrary.nbtedit.NBTEditResponseHandlers;
import dev.ftb.mods.ftblibrary.platform.event.NativeEventPosting;
import dev.ftb.mods.ftblibrary.util.Json5Ops;
import dev.ftb.mods.ftblibrary.util.result.Outcome;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.event.CollectTeamPropertiesEvent;
import dev.ftb.mods.ftbteams.api.event.TeamManagerEvent;
import dev.ftb.mods.ftbteams.api.property.TeamProperties;
import dev.ftb.mods.ftbteams.command.FTBTeamsCommands;
import dev.ftb.mods.ftbteams.data.AbstractTeam;
import dev.ftb.mods.ftbteams.data.TeamManagerImpl;
import dev.ftb.mods.ftbteams.net.FTBTeamsNet;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class FTBTeams {
	public static final Logger LOGGER = LogManager.getLogger(FTBTeamsAPI.MOD_NAME);

	private static final Identifier TEAM_RESPONSE_HANDLER = FTBTeamsAPI.id("team");

	public FTBTeams() {
		FTBTeamsAPI._init(FTBTeamsAPIImpl.INSTANCE);

		FTBTeamsNet.register();
	}

	public void serverStarted(MinecraftServer server) {
		NBTEditResponseHandlers.INSTANCE.registerHandler(TEAM_RESPONSE_HANDLER, (ignoredPlayer, info, data) ->
				info.read("id", UUIDUtil.CODEC).flatMap(uuid -> FTBTeamsAPI.api().getManager().getTeamByID(uuid)).ifPresent(team -> {
                    if (team instanceof AbstractTeam abstractTeam && NbtOps.INSTANCE.convertTo(Json5Ops.INSTANCE, data) instanceof Json5Object json) {
                        abstractTeam.deserializeJson(json, server.registryAccess());
                        abstractTeam.markDirty();
                    }
				}));
	}

	public void serverAboutToStart(MinecraftServer server) {
		TeamManagerImpl.INSTANCE = new TeamManagerImpl(server);
		NativeEventPosting.INSTANCE.postEvent(new TeamManagerEvent.Data(TeamManagerImpl.INSTANCE, TeamManagerEvent.Action.CREATED));
		try {
			TeamManagerImpl.INSTANCE.load();
		} catch (IOException e) {
			FTBTeams.LOGGER.error("Load failure for ");
		}
	}

	public void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection selection) {
		new FTBTeamsCommands().register(dispatcher);
	}

	public void serverStopped(MinecraftServer server) {
		if (TeamManagerImpl.INSTANCE != null) {
			NativeEventPosting.INSTANCE.postEvent(new TeamManagerEvent.Data(TeamManagerImpl.INSTANCE, TeamManagerEvent.Action.DESTROYED));
			TeamManagerImpl.INSTANCE = null;
		}
	}

	public void worldSaved() {
		if (TeamManagerImpl.INSTANCE != null) {
			TeamManagerImpl.INSTANCE.saveNow();
		}
	}

	public void addBuiltinTeamProperties(CollectTeamPropertiesEvent.Data eventData) {
		eventData.addProperty(TeamProperties.DISPLAY_NAME);
		eventData.addProperty(TeamProperties.DESCRIPTION);
		eventData.addProperty(TeamProperties.COLOR);
		eventData.addProperty(TeamProperties.FREE_TO_JOIN);
		eventData.addProperty(TeamProperties.MAX_MSG_HISTORY_SIZE);
		eventData.addProperty(TeamProperties.TEAM_STAGES);
	}

	public void playerLoggedIn(ServerPlayer player) {
		if (TeamManagerImpl.INSTANCE != null) {
			TeamManagerImpl.INSTANCE.playerLoggedIn(player, player.getUUID(), player.getScoreboardName());
		}
	}

	public void playerLoggedOut(ServerPlayer player) {
		if (TeamManagerImpl.INSTANCE != null) {
			TeamManagerImpl.INSTANCE.playerLoggedOut(player);
		}
	}

	public Outcome redirectChatMessage(@Nullable ServerPlayer player, Component component) {
		if (TeamManagerImpl.INSTANCE != null && player != null && TeamManagerImpl.INSTANCE.isChatRedirected(player)) {
			return FTBTeamsAPI.api().getManager().getTeamForPlayer(player).map(team -> {
				team.sendMessage(player.getUUID(), component);
				return Outcome.SUCCESS;
			}).orElse(Outcome.PASS);
		}
		return Outcome.PASS;
	}
}
