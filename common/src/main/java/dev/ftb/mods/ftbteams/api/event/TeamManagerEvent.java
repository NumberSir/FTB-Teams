package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.TeamManager;

import java.util.function.Consumer;

@FunctionalInterface
public interface TeamManagerEvent extends Consumer<TeamManagerEvent.Data> {
	record Data(TeamManager manager, Action action) {
	}

	enum Action {
		CREATED,
		LOADED,
		SAVED,
		DESTROYED
	}
}