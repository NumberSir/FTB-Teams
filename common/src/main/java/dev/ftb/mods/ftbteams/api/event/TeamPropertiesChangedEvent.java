package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyCollection;

import java.util.function.Consumer;

/// Fired on both client and server when a team's properties have changed in some way.
/// * On client, fired when the client receives an updated notification from the server for the team
/// * On server, fired when a team's properties are changed, either via GUI or command
///
///  Corresponding platform-native events to listen to:
/// * `FTBTeamsEvent.TeamPropertiesChanged` (NeoForge)
/// * `FTBTeamsEvents.TEAM_PROPERTIES_CHANGED` (Fabric)
@FunctionalInterface
public interface TeamPropertiesChangedEvent extends Consumer<TeamPropertiesChangedEvent.Data> {
	record Data(Team team, TeamPropertyCollection previousProperties, boolean isClient) {
	}
}