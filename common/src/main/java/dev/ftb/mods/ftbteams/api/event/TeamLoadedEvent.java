package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;

import java.util.function.Consumer;

@FunctionalInterface
public interface TeamLoadedEvent extends Consumer<TeamLoadedEvent.Data> {
    record Data(Team team) {
    }
}
