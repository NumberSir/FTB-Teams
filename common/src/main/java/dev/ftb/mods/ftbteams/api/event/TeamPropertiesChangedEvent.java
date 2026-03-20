package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyCollection;

import java.util.function.Consumer;

@FunctionalInterface
public interface TeamPropertiesChangedEvent extends Consumer<TeamPropertiesChangedEvent.Data> {
	record Data(Team team, TeamPropertyCollection previousProperties, boolean isClient) {
	}
}