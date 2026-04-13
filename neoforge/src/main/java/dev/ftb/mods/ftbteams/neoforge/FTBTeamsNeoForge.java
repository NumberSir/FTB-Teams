package dev.ftb.mods.ftbteams.neoforge;

import dev.ftb.mods.ftbteams.FTBTeams;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.event.*;
import dev.ftb.mods.ftbteams.api.neoforge.FTBTeamsEvent;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

import static dev.ftb.mods.ftblibrary.util.neoforge.NeoEventHelper.registerNeoEventPoster;

@Mod(FTBTeamsAPI.MOD_ID)
public class FTBTeamsNeoForge {
	private final FTBTeams teams;

	public FTBTeamsNeoForge(IEventBus modEventBus) {
		teams = new FTBTeams();

		ArgumentTypes.COMMAND_ARGUMENT_TYPES.register(modEventBus);

		IEventBus bus = NeoForge.EVENT_BUS;

		bus.addListener(ServerAboutToStartEvent.class, event -> teams.serverAboutToStart(event.getServer()));
		bus.addListener(ServerStartedEvent.class, event -> teams.serverStarted(event.getServer()));
		bus.addListener(ServerStoppedEvent.class, event -> teams.serverStopped(event.getServer()));
		bus.addListener(LevelEvent.Save.class, event -> teams.worldSaved());
		bus.addListener(RegisterCommandsEvent.class, event ->
				teams.registerCommands(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection()));
		bus.addListener(PlayerEvent.PlayerLoggedInEvent.class, event -> teams.playerLoggedIn((ServerPlayer) event.getEntity()));
		bus.addListener(PlayerEvent.PlayerLoggedOutEvent.class, event -> teams.playerLoggedOut((ServerPlayer) event.getEntity()));
		bus.addListener(ServerChatEvent.class, event -> {
            if (teams.redirectChatMessage(event.getPlayer(), event.getMessage()).isSuccess()) {
				event.setCanceled(true);
			}
        });

		bus.addListener(FTBTeamsEvent.CollectTeamProperties.class, event -> teams.addBuiltinTeamProperties(event.getEventData()));
		bus.addListener(PlayerEvent.Clone.class, event -> {
			if (event.getOriginal() instanceof ServerPlayer && event.getEntity() instanceof ServerPlayer) {
				FTBTeams.playerCloned((ServerPlayer) event.getOriginal(), (ServerPlayer) event.getEntity(), !event.isWasDeath());
			}
		});

		registerNeoEventPosters(bus);
	}

	private static void registerNeoEventPosters(IEventBus bus) {
		registerNeoEventPoster(bus, PlayerJoinedPartyTeamEvent.Data.class, FTBTeamsEvent.PlayerJoinedPartyTeam::new);
		registerNeoEventPoster(bus, PlayerLeftPartyTeamEvent.Data.class, FTBTeamsEvent.PlayerLeftPartyTeam::new);
		registerNeoEventPoster(bus, TeamPlayerLoggedInEvent.Data.class, FTBTeamsEvent.TeamPlayerLoggedIn::new);
		registerNeoEventPoster(bus, TeamInfoEvent.Data.class, FTBTeamsEvent.TeamInfo::new);
		registerNeoEventPoster(bus, TeamCreatedEvent.Data.class, FTBTeamsEvent.TeamCreated::new);
		registerNeoEventPoster(bus, TeamDeletedEvent.Data.class, FTBTeamsEvent.TeamDeleted::new);
		registerNeoEventPoster(bus, TeamLoadedEvent.Data.class, FTBTeamsEvent.TeamLoaded::new);
		registerNeoEventPoster(bus, TeamSavedEvent.Data.class, FTBTeamsEvent.TeamSaved::new);
		registerNeoEventPoster(bus, PlayerTransferredOwnershipEvent.Data.class, FTBTeamsEvent.PlayerTransferredOwnership::new);
		registerNeoEventPoster(bus, CollectTeamPropertiesEvent.Data.class, FTBTeamsEvent.CollectTeamProperties::new);
		registerNeoEventPoster(bus, PlayerChangedTeamEvent.Data.class, FTBTeamsEvent.PlayerChangedTeam::new);
		registerNeoEventPoster(bus, TeamAllyEvent.Data.class, FTBTeamsEvent.TeamAlly::new);
		registerNeoEventPoster(bus, TeamPropertiesChangedEvent.Data.class, FTBTeamsEvent.TeamPropertiesChanged::new);
		registerNeoEventPoster(bus, TeamManagerEvent.Data.class, FTBTeamsEvent.TeamManager::new);
	}
}
