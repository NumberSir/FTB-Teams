package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;

import java.util.function.Consumer;

@FunctionalInterface
public interface TeamSavedEvent extends Consumer<TeamSavedEvent.Data> {
    record Data(Team team) {
    }
}
