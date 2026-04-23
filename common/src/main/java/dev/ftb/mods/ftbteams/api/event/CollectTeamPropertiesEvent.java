package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.property.TeamProperty;

import java.util.function.Consumer;

/// Fired server-side whenever a team is created and ready to have its properties registered. You can listen for this
///  event in your mod to attach any additional properties you wish to add to the team.
///
///  Corresponding platform-native events to listen to:
/// * `FTBTeamsEvent.CollectTeamProperties` (NeoForge)
/// * `FTBTeamsEvents.COLLECT_TEAM_PROPERTIES` (Fabric)
@FunctionalInterface
public interface CollectTeamPropertiesEvent extends Consumer<CollectTeamPropertiesEvent.Data> {
	record Data(Consumer<TeamProperty<?>> consumer) {
		/// Add the given property to the team. It will appear for display and editing in the team properties GUI when the
		/// "Settings" button in the main team is clicked (dependent on whether [TeamProperty#hidden()] and/or
		/// [TeamProperty#notPlayerEditable()] were specified).
		///
		/// @param property the property to add
		public void addProperty(TeamProperty<?> property) {
			consumer.accept(property);
		}
	}
}