package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;

import java.util.function.Consumer;

@FunctionalInterface
public interface TeamDeletedEvent extends Consumer<TeamDeletedEvent.Data> {
    /**
     * @param team the team that has just been deleted
     */
    record Data(Team team) {
    }
}
