package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

/// Fired server-side when a player joins a party team
///
///  Corresponding platform-native events to listen to:
/// * `FTBTeamsEvent.PlayerJoinedPartyTeam` (NeoForge)
/// * `FTBTeamsEvents.PLAYER_JOINED_PARTY_TEAM` (Fabric)
@FunctionalInterface
public interface PlayerJoinedPartyTeamEvent extends Consumer<PlayerJoinedPartyTeamEvent.Data> {
	/// @param team the party team being joined
	/// @param previousTeam the player's personal team
	/// @param player the player who just joined
	record Data(Team team, Team previousTeam, ServerPlayer player) {
	}
}