package dev.ftb.mods.ftbteams.api.event;

import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

@FunctionalInterface
public interface TeamInfoEvent extends Consumer<TeamInfoEvent.Data> {
	record Data(Team team, Consumer<Component> consumer) {
		public void add(Component component) {
			consumer.accept(component);
		}
	}

//	private final CommandSourceStack source;
//
//	public TeamInfoEvent(Team t, CommandSourceStack p) {
//		super(t);
//		source = p;
//	}
//
//	public CommandSourceStack getSource() {
//		return source;
//	}
//
//	public void add(Component component) {
//		source.sendSuccess(() -> component, false);
//	}
}