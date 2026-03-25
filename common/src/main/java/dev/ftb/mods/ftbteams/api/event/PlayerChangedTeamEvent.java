package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.server.level.ServerPlayer;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

/// Fired server-side when a player changes team for any reason. This event is likely to be immediately followed by
/// either a [PlayerJoinedPartyTeamEvent] or [PlayerLeftPartyTeamEvent], and possibly a [TeamDeletedEvent] if the
/// party team was disbanded due to the player leaving it.
///
///  Corresponding platform-native events to listen to:
/// * `FTBTeamsEvent.PlayerChangedTeam` (NeoForge)
/// * `FTBTeamsEvents.PLAYER_CHANGED_TEAM` (Fabric)
@FunctionalInterface
public interface PlayerChangedTeamEvent extends Consumer<PlayerChangedTeamEvent.Data> {
	record Data(Team team, @Nullable Team previousTeam, UUID playerId, @Nullable ServerPlayer player) {
	}
}