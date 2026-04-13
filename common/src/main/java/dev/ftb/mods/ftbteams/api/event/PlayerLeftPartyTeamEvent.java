package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.server.level.ServerPlayer;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

/// Fired server-side when a player leaves a party team
///
///  Corresponding platform-native events to listen to:
/// * `FTBTeamsEvent.PlayerLeftPartyTeam` (NeoForge)
/// * `FTBTeamsEvents.PLAYER_LEFT_PARTY_TEAM` (Fabric)
@FunctionalInterface
public interface PlayerLeftPartyTeamEvent extends Consumer<PlayerLeftPartyTeamEvent.Data> {
	/// @param team the party team that the player left
	/// @param playerTeam the player's own personal team
	/// @param playerId the player's UUID
	/// @param player the player object, may be null if the player is offline
	/// @param teamDeleted true if the party team is being deleted (last player to leave)
	record Data(Team team, Team playerTeam, UUID playerId, @Nullable ServerPlayer player, boolean teamDeleted) {
	}
}