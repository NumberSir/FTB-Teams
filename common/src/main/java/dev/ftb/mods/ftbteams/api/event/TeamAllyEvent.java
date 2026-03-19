package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.server.players.NameAndId;

import java.util.List;
import java.util.function.Consumer;

/**
 * Fired when one or more players are added or removed as allies for a team.
 */
@FunctionalInterface
public interface TeamAllyEvent extends Consumer<TeamAllyEvent.Data> {
    /**
     * @param team the team
     * @param players the players who being added or removed
     * @param adding true if players are being added, false if removed
     */
    record Data(Team team, List<NameAndId> players, boolean adding) {
    }
}
