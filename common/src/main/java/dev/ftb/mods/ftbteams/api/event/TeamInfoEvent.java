package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

/// Fired on both client and server to add extra info lines to a team info display
/// * On server, fired when the `/team info` command is used
/// * On client, fired when team screen "Info" button is moused-over
/// Your listener can use [Team#isClientTeam()] to easily check which side you are on.
///
/// Corresponding platform-native events to listen to:
/// /// * `FTBTeamsEvent.Info` (NeoForge)
/// /// * `FTBTeamsEvents.INFO` (Fabric)
@FunctionalInterface
public interface TeamInfoEvent extends Consumer<TeamInfoEvent.Data> {
	/// @param team the team to add info lines
	/// @param consumer a consumer to accept new [Component] lines
	record Data(Team team, Consumer<Component> consumer) {
		public void add(Component component) {
			consumer.accept(component);
		}
	}
}