package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

/// Fired server-side after a player has logged in, and their team info has all been properly set up.
///
///  Corresponding platform-native events to listen to:
/// * `FTBTeamsEvent.TeamPlayerLoggedIn` (NeoForge)
/// * `FTBTeamsEvents.TEAM_PLAYER_LOGGED_IN` (Fabric)
@FunctionalInterface
public interface TeamPlayerLoggedInEvent extends Consumer<TeamPlayerLoggedInEvent.Data> {
	/// @param team the player's team (either a player team or a party team)
	/// @param player the player who has just logged in
	record Data(Team team, ServerPlayer player) {
	}
}