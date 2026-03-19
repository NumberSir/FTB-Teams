package dev.ftb.mods.ftbteams.api.event;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

@FunctionalInterface
public interface PlayerTransferredOwnershipEvent extends Consumer<PlayerTransferredOwnershipEvent.Data> {
	record Data(Team team, @Nullable ServerPlayer fromPlayer, Either<ServerPlayer,NameAndId> to) {
		public static Data transferToPlayer(Team team, @Nullable ServerPlayer from, ServerPlayer newOwner) {
			return new Data(team, from, Either.left(newOwner));
		}

		public static Data transferToProfile(Team team, @Nullable ServerPlayer from, NameAndId toProfile) {
			return new Data(team, from, Either.right(toProfile));
		}

		@Nullable
		public ServerPlayer toPlayer() {
			return to.left().orElse(null);
		}

		public GameProfile toProfile() {
			return to.map(Player::getGameProfile, nameAndId -> new GameProfile(nameAndId.id(), nameAndId.name()));
		}
	}
}
