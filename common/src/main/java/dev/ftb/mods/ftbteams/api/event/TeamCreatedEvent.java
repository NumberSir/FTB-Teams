package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.server.level.ServerPlayer;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Fired server-side when a new team is created; could be a player, party, or server team.
 */
@FunctionalInterface
public interface TeamCreatedEvent extends Consumer<TeamCreatedEvent.Data> {
    /**
     * @param team the team that has just been created
     * @param creator the player who created the team (this may be null for a server team created via console)
     * @param creatorId UUID of the creator ({@code Util.NIL_UUID} if there is no player associated)
     */
	record Data(Team team, @Nullable ServerPlayer creator, UUID creatorId) {
	}
}