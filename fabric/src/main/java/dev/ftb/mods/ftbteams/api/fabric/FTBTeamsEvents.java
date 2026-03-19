package dev.ftb.mods.ftbteams.api.fabric;

import dev.ftb.mods.ftbteams.api.event.*;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class FTBTeamsEvents {
    public static Event<CollectTeamPropertiesEvent> COLLECT_TEAM_PROPERTIES = EventFactory.createArrayBacked(CollectTeamPropertiesEvent.class,
            callbacks -> data -> {
                for (CollectTeamPropertiesEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<TeamCreatedEvent> TEAM_CREATED = EventFactory.createArrayBacked(TeamCreatedEvent.class,
            callbacks -> data -> {
                for (TeamCreatedEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<TeamDeletedEvent> TEAM_DELETED = EventFactory.createArrayBacked(TeamDeletedEvent.class,
            callbacks -> data -> {
                for (TeamDeletedEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<TeamLoadedEvent> TEAM_LOADED = EventFactory.createArrayBacked(TeamLoadedEvent.class,
            callbacks -> data -> {
                for (TeamLoadedEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<TeamSavedEvent> TEAM_SAVED = EventFactory.createArrayBacked(TeamSavedEvent.class,
            callbacks -> data -> {
                for (TeamSavedEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<TeamInfoEvent> TEAM_INFO = EventFactory.createArrayBacked(TeamInfoEvent.class,
            callbacks -> data -> {
                for (TeamInfoEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<TeamPlayerLoggedInEvent> TEAM_PLAYER_LOGGED_IN = EventFactory.createArrayBacked(TeamPlayerLoggedInEvent.class,
            callbacks -> data -> {
                for (TeamPlayerLoggedInEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<PlayerTransferredOwnershipEvent> PLAYER_TRANSFERRED_OWNERSHIP = EventFactory.createArrayBacked(PlayerTransferredOwnershipEvent.class,
            callbacks -> data -> {
                for (PlayerTransferredOwnershipEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<PlayerJoinedPartyTeamEvent> PLAYER_JOINED_PARTY_TEAM = EventFactory.createArrayBacked(PlayerJoinedPartyTeamEvent.class,
            callbacks -> data -> {
                for (PlayerJoinedPartyTeamEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<PlayerLeftPartyTeamEvent> PLAYER_LEFT_PARTY_TEAM = EventFactory.createArrayBacked(PlayerLeftPartyTeamEvent.class,
            callbacks -> data -> {
                for (PlayerLeftPartyTeamEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<PlayerChangedTeamEvent> PLAYER_CHANGED_TEAM = EventFactory.createArrayBacked(PlayerChangedTeamEvent.class,
            callbacks -> data -> {
                for (PlayerChangedTeamEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<TeamAllyEvent> TEAM_ALLY = EventFactory.createArrayBacked(TeamAllyEvent.class,
            callbacks -> data -> {
                for (TeamAllyEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<TeamPropertiesChangedEvent> TEAM_PROPERTIES_CHANGED = EventFactory.createArrayBacked(TeamPropertiesChangedEvent.class,
            callbacks -> data -> {
                for (TeamPropertiesChangedEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
    public static Event<TeamManagerEvent> TEAM_MANAGER = EventFactory.createArrayBacked(TeamManagerEvent.class,
            callbacks -> data -> {
                for (TeamManagerEvent c : callbacks) {
                    c.accept(data);
                }
            }
    );
}
