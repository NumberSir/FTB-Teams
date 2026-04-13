package dev.ftb.mods.ftbteams.api.event;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

/// Fired server-side when a player (who was the owner of a party team) has transferred ownership of the
/// party to another player.
///
///  Corresponding platform-native events to listen to:
/// * `FTBTeamsEvent.PlayerTransferredOwnership` (NeoForge)
/// * `FTBTeamsEvents.PLAYER_TRANSFERRED_OWNERSHIP` (Fabric)
@FunctionalInterface
public interface PlayerTransferredOwnershipEvent extends Consumer<PlayerTransferredOwnershipEvent.Data> {
	/// @param team the team in question
	/// @param fromPlayer the previous owner (null if the previous owner is offline; change made via console command)
	/// @param to either a player object or name-and-id details for the new owner, depending on whether they are online
	/// (see also [#toPlayer()] and [#toProfile()] methods)
	record Data(Team team, @Nullable ServerPlayer fromPlayer, Either<ServerPlayer,NameAndId> to) {
		public static Data transferToPlayer(Team team, @Nullable ServerPlayer from, ServerPlayer newOwner) {
			return new Data(team, from, Either.left(newOwner));
		}

		public static Data transferToProfile(Team team, @Nullable ServerPlayer from, NameAndId toProfile) {
			return new Data(team, from, Either.right(toProfile));
		}

		/// Get the player object for the new team owner; may be null if the player is not currently online
		@Nullable
		public ServerPlayer toPlayer() {
			return to.left().orElse(null);
		}

		/// Get the name-and-ID details of the new team owner
		public GameProfile toProfile() {
			return to.map(Player::getGameProfile, nameAndId -> new GameProfile(nameAndId.id(), nameAndId.name()));
		}
	}
}
