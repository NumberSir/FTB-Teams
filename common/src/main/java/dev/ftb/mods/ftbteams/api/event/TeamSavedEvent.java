package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;

import java.util.function.Consumer;

/// Fired server-side when a team is saved to disk.
///
/// Corresponding platform-native events to listen to:
/// * `FTBTeamsEvent.TeamSaved` (NeoForge)
/// * `FTBTeamsEvents.TEAM_SAVED` (Fabric)
@FunctionalInterface
public interface TeamSavedEvent extends Consumer<TeamSavedEvent.Data> {
    /// @param team the team which has been loaded
    record Data(Team team) {
    }
}
