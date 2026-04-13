package dev.ftb.mods.ftbteams.neoforge;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftblibrary.api.neoforge.FTBLibraryEvent;
import dev.ftb.mods.ftbteams.client.FTBTeamsClient;
import dev.ftb.mods.ftbteams.net.OpenGUIMessage;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = FTBTeamsAPI.MOD_ID, dist = Dist.CLIENT)
public class FTBTeamsNeoForgeClient {
    public FTBTeamsNeoForgeClient() {
        FTBTeamsClient.init();

        NeoForge.EVENT_BUS.addListener(FTBLibraryEvent.CustomClick.class, event -> {
            if (event.getEventData().id().equals(FTBTeamsClient.OPEN_GUI_ID)) {
                OpenGUIMessage.sendToServer();
                event.setCanceled(true);
            }
        });

        NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, ignored -> FTBTeamsClient.keyPressed(Minecraft.getInstance()));
        NeoForge.EVENT_BUS.addListener(FTBLibraryEvent.SidebarButtonCreated.class, event -> {
            FTBTeamsClient.onSidebarButtonCreated(event.getEventData());
        });
    }
}
