package dev.ftb.mods.ftbteams.fabric;

import dev.ftb.mods.ftblibrary.fabric.FTBLibraryFabricEvents;
import dev.ftb.mods.ftbteams.client.FTBTeamsClient;
import dev.ftb.mods.ftbteams.net.OpenGUIMessage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class FTBTeamsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FTBTeamsClient.init();

        FTBLibraryFabricEvents.CUSTOM_CLICK.register(event -> {
            if (event.id().equals(FTBTeamsClient.OPEN_GUI_ID)) {
                OpenGUIMessage.sendToServer();
                return true;
            }
            return false;
        });

        FTBLibraryFabricEvents.SIDEBAR_BUTTON_CREATED.register(FTBTeamsClient::onSidebarButtonCreated);

        ClientTickEvents.END_CLIENT_TICK.register(FTBTeamsClient::keyPressed);
    }
}
