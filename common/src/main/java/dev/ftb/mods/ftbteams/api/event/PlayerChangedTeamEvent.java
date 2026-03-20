package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.server.level.ServerPlayer;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

@FunctionalInterface
public interface PlayerChangedTeamEvent extends Consumer<PlayerChangedTeamEvent.Data> {
	record Data(Team team, @Nullable Team previousTeam, UUID playerId, @Nullable ServerPlayer player) {
	}
}