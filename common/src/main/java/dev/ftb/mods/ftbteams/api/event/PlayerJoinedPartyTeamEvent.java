package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

@FunctionalInterface
public interface PlayerJoinedPartyTeamEvent extends Consumer<PlayerJoinedPartyTeamEvent.Data> {
	/**
	 * @param team the party team being joined
	 * @param previousTeam the player's personal team
	 * @param player the player who just joined
	 */
	record Data(Team team, Team previousTeam, ServerPlayer player) {
	}
}