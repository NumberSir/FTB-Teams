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

//	@Nullable
//	private final Team previousTeam;
//	private final UUID playerId;
//	@Nullable
//	private final ServerPlayer player;
//
//	public PlayerChangedTeamEvent(Team newTeam, @Nullable Team previousTeam, UUID playerId, @Nullable ServerPlayer player) {
//		super(newTeam);
//		this.previousTeam = previousTeam;
//		this.playerId = playerId;
//		this.player = player;
//	}
//
//	public Optional<Team> getPreviousTeam() {
//		return Optional.ofNullable(previousTeam);
//	}
//
//	public UUID getPlayerId() {
//		return playerId;
//	}
//
//	@Nullable
//	public ServerPlayer getPlayer() {
//		return player;
//	}
}