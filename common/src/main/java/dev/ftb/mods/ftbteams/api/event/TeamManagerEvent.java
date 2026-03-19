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

//	public static final Event<Consumer<TeamManagerEvent>> CREATED = EventFactory.createConsumerLoop(TeamManagerEvent.class);
//	public static final Event<Consumer<TeamManagerEvent>> LOADED = EventFactory.createConsumerLoop(TeamManagerEvent.class);
//	public static final Event<Consumer<TeamManagerEvent>> SAVED = EventFactory.createConsumerLoop(TeamManagerEvent.class);
//	public static final Event<Consumer<TeamManagerEvent>> DESTROYED = EventFactory.createConsumerLoop(TeamManagerEvent.class);
//
//	private final TeamManager manager;
//
//	public TeamManagerEvent(TeamManager t) {
//		manager = t;
//	}
//
//	public TeamManager getManager() {
//		return manager;
//	}
//
//	public CompoundTag getExtraData() {
//		return manager.getExtraData();
//	}
}