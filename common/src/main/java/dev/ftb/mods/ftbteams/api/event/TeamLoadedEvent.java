package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;

import java.util.function.Consumer;

/// Fired server-side when a team is loaded from disk.
///
/// Corresponding platform-native events to listen to:
/// * `FTBTeamsEvent.TeamLoaded` (NeoForge)
/// * `FTBTeamsEvents.TEAM_LOADED` (Fabric)
@FunctionalInterface
public interface TeamLoadedEvent extends Consumer<TeamLoadedEvent.Data> {
    /// @param team the team which has been loaded
    record Data(Team team) {
    }
}
