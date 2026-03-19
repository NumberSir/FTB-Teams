package dev.ftb.mods.ftbteams.api.neoforge;

import dev.ftb.mods.ftblibrary.api.neoforge.BaseEventWithData;
import dev.ftb.mods.ftbteams.api.event.*;
import dev.ftb.mods.ftbteams.api.property.TeamProperty;
import net.minecraft.network.chat.Component;

public class FTBTeamsEvent {
    public static class CollectTeamProperties extends BaseEventWithData<CollectTeamPropertiesEvent.Data> {
        public CollectTeamProperties(CollectTeamPropertiesEvent.Data eventData) {
            super(eventData);
        }

        public void addProperty(TeamProperty<?> property) {
            data.addProperty(property);
        }
    }

    public static class TeamInfo extends BaseEventWithData<TeamInfoEvent.Data> {
        public TeamInfo(TeamInfoEvent.Data eventData) {
            super(eventData);
        }

        public void add(Component line) {
            data.consumer().accept(line);
        }
    }

    public static class TeamPlayerLoggedIn extends BaseEventWithData<TeamPlayerLoggedInEvent.Data> {
        public TeamPlayerLoggedIn(TeamPlayerLoggedInEvent.Data eventData) {
            super(eventData);
        }
    }

    public static class TeamCreated extends BaseEventWithData<TeamCreatedEvent.Data> {
        public TeamCreated(TeamCreatedEvent.Data eventData) {
            super(eventData);
        }
    }

    public static class TeamDeleted extends BaseEventWithData<TeamDeletedEvent.Data> {
        public TeamDeleted(TeamDeletedEvent.Data eventData) {
            super(eventData);
        }
    }

    public static class TeamLoaded extends BaseEventWithData<TeamLoadedEvent.Data> {
        public TeamLoaded(TeamLoadedEvent.Data eventData) {
            super(eventData);
        }
    }

    public static class TeamSaved extends BaseEventWithData<TeamSavedEvent.Data> {
        public TeamSaved(TeamSavedEvent.Data eventData) {
            super(eventData);
        }
    }

    public static class PlayerTransferredOwnership extends BaseEventWithData<PlayerTransferredOwnershipEvent.Data> {
        public PlayerTransferredOwnership(PlayerTransferredOwnershipEvent.Data eventData) {
            super(eventData);
        }
    }

    public static class TeamPropertiesChanged extends BaseEventWithData<TeamPropertiesChangedEvent.Data> {
        public TeamPropertiesChanged(TeamPropertiesChangedEvent.Data eventData) {
            super(eventData);
        }
    }

    public static class PlayerChangedTeam extends BaseEventWithData<PlayerChangedTeamEvent.Data> {
        public PlayerChangedTeam(PlayerChangedTeamEvent.Data eventData) {
            super(eventData);
        }
    }

    public static class PlayerJoinedPartyTeam extends BaseEventWithData<PlayerJoinedPartyTeamEvent.Data> {
        public PlayerJoinedPartyTeam(PlayerJoinedPartyTeamEvent.Data eventData) {
            super(eventData);
        }
    }

    public static class PlayerLeftPartyTeam extends BaseEventWithData<PlayerLeftPartyTeamEvent.Data> {
        public PlayerLeftPartyTeam(PlayerLeftPartyTeamEvent.Data eventData) {
            super(eventData);
        }
    }

    public static class TeamAlly extends BaseEventWithData<TeamAllyEvent.Data> {
        public TeamAlly(TeamAllyEvent.Data eventData) {
            super(eventData);
        }
    }

    public static class TeamManager extends BaseEventWithData<TeamManagerEvent.Data> {
        public TeamManager(TeamManagerEvent.Data eventData) {
            super(eventData);
        }

        public TeamManagerEvent.Action getAction() {
            return getEventData().action();
        }
    }
}
