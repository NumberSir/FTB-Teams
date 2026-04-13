package dev.ftb.mods.ftbteams.client.gui;

import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.platform.network.Play2ServerNetworking;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.client.KnownClientPlayer;
import dev.ftb.mods.ftbteams.net.PlayerGUIOperationMessage;
import dev.ftb.mods.ftbteams.net.PlayerGUIOperationMessage.Operation;
import net.minecraft.network.chat.Component;

public class InviteScreen extends BaseInvitationScreen {
    public InviteScreen() {
        super(Component.translatable("ftbteams.gui.invite"));
    }

    @Override
    protected boolean shouldIncludePlayer(KnownClientPlayer player) {
        // any player who is online and not in a team
        return player.isOnlineAndNotInParty() && !player.equals(FTBTeamsAPI.api().getClientManager().self());
    }

    @Override
    protected ExecuteButton makeExecuteButton() {
        return new ExecuteButton(Component.translatable("ftbteams.gui.send_invite"), Icons.ADD, () -> {
            Play2ServerNetworking.send(PlayerGUIOperationMessage.forGameProfiles(Operation.INVITE, invites));
            closeGui();
        }) {
            @Override
            public boolean isEnabled() {
                return !invites.isEmpty();
            }
        };
    }
}
