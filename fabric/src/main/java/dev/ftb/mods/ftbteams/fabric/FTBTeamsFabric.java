package dev.ftb.mods.ftbteams.fabric;

import dev.ftb.mods.ftbteams.FTBTeams;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.event.*;
import dev.ftb.mods.ftbteams.api.fabric.FTBTeamsEvents;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyArgument;
import dev.ftb.mods.ftbteams.command.TeamArgument;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;

import static dev.ftb.mods.ftblibrary.util.fabric.FabricEventHelper.registerFabricEventPoster;

public class FTBTeamsFabric implements ModInitializer {
	private FTBTeams teams;

	@Override
	public void onInitialize() {
		teams = new FTBTeams();

		ArgumentTypeRegistry.registerArgumentType(FTBTeamsAPI.id("team"), TeamArgument.class, new TeamArgument.Info());
		ArgumentTypeRegistry.registerArgumentType(FTBTeamsAPI.id("team_property"), TeamPropertyArgument.class, new TeamPropertyArgument.Info());

		ServerLifecycleEvents.SERVER_STARTING.register(teams::serverAboutToStart);
		ServerLifecycleEvents.SERVER_STARTED.register(teams::serverStarted);
		ServerLifecycleEvents.SERVER_STOPPED.register(teams::serverStopped);
		ServerLifecycleEvents.AFTER_SAVE.register((server,flush, force) -> teams.worldSaved());
		CommandRegistrationCallback.EVENT.register(teams::registerCommands);
		ServerPlayerEvents.JOIN.register(teams::playerLoggedIn);
		ServerPlayerEvents.LEAVE.register(teams::playerLoggedOut);
		ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, sender, boundChatType) ->
				!teams.redirectChatMessage(sender, message.decoratedContent()).isSuccess()
		);
		ServerPlayerEvents.COPY_FROM.register(FTBTeams::playerCloned);

		FTBTeamsEvents.COLLECT_TEAM_PROPERTIES.register(teams::addBuiltinTeamProperties);

		registerNativeEventPosting();
	}

	private static void registerNativeEventPosting() {
		registerFabricEventPoster(PlayerJoinedPartyTeamEvent.Data.class, FTBTeamsEvents.PLAYER_JOINED_PARTY_TEAM);
		registerFabricEventPoster(PlayerLeftPartyTeamEvent.Data.class, FTBTeamsEvents.PLAYER_LEFT_PARTY_TEAM);
		registerFabricEventPoster(TeamPlayerLoggedInEvent.Data.class, FTBTeamsEvents.TEAM_PLAYER_LOGGED_IN);
		registerFabricEventPoster(TeamInfoEvent.Data.class, FTBTeamsEvents.TEAM_INFO);
		registerFabricEventPoster(TeamCreatedEvent.Data.class, FTBTeamsEvents.TEAM_CREATED);
		registerFabricEventPoster(TeamDeletedEvent.Data.class, FTBTeamsEvents.TEAM_DELETED);
		registerFabricEventPoster(TeamLoadedEvent.Data.class, FTBTeamsEvents.TEAM_LOADED);
		registerFabricEventPoster(TeamSavedEvent.Data.class, FTBTeamsEvents.TEAM_SAVED);
		registerFabricEventPoster(PlayerTransferredOwnershipEvent.Data.class, FTBTeamsEvents.PLAYER_TRANSFERRED_OWNERSHIP);
		registerFabricEventPoster(CollectTeamPropertiesEvent.Data.class, FTBTeamsEvents.COLLECT_TEAM_PROPERTIES);
		registerFabricEventPoster(PlayerChangedTeamEvent.Data.class, FTBTeamsEvents.PLAYER_CHANGED_TEAM);
		registerFabricEventPoster(TeamAllyEvent.Data.class, FTBTeamsEvents.TEAM_ALLY);
		registerFabricEventPoster(TeamPropertiesChangedEvent.Data.class, FTBTeamsEvents.TEAM_PROPERTIES_CHANGED);
		registerFabricEventPoster(TeamManagerEvent.Data.class, FTBTeamsEvents.TEAM_MANAGER);
	}
}
