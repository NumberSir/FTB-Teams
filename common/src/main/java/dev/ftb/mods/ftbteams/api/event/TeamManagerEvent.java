package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.TeamManager;

import java.util.function.Consumer;

/// This lifecycle event is fired server-side when the team manager state changes in some way.
///
///  Corresponding platform-native events to listen to:
/// * `FTBTeamsEvent.TeamManager` (NeoForge)
/// * `FTBTeamsEvents.TEAM_MANAGER` (Fabric)
@FunctionalInterface
public interface TeamManagerEvent extends Consumer<TeamManagerEvent.Data> {
	/// @param manager the team manager
	/// @param action the action that has just occurred to the team manager
	record Data(TeamManager manager, Action action) {
	}

	enum Action {
		/// Team manager has been created (on server startup)
		CREATED,
		/// Team manager data has been loaded from disk (see also [TeamLoadedEvent] for individual teams)
		LOADED,
		/// Team manager data has been saved to disk
		SAVED,
		/// Team manager has been destroyed (on server shutdown)
		DESTROYED
	}
}