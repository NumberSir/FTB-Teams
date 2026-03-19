package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

@FunctionalInterface
public interface TeamPlayerLoggedInEvent extends Consumer<TeamPlayerLoggedInEvent.Data> {
	record Data(Team team, ServerPlayer player) {
	}
}