package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;

import java.util.function.Consumer;

/// Fired server-side when a team has been deleted
///
/// Corresponding platform-native events to listen to:
/// * `FTBTeamsEvent.TeamDeleted` (NeoForge)
/// * `FTBTeamsEvents.TEAM_DELETED` (Fabric)
@FunctionalInterface
public interface TeamDeletedEvent extends Consumer<TeamDeletedEvent.Data> {
    /// @param team the team that has just been deleted
    record Data(Team team) {
    }
}
