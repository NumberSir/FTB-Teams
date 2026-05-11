package dev.ftb.mods.ftbteams.net;

import dev.ftb.mods.ftblibrary.platform.network.PacketContext;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.client.FTBTeamsClient;
import dev.ftb.mods.ftbteams.data.PlayerPermissions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record OpenMyTeamGUIMessage(PlayerPermissions permissions) implements CustomPacketPayload {
	public static final Type<OpenMyTeamGUIMessage> TYPE = new Type<>(FTBTeamsAPI.id("open_my_team_gui"));

	public static final StreamCodec<RegistryFriendlyByteBuf, OpenMyTeamGUIMessage> STREAM_CODEC = StreamCodec.composite(
			PlayerPermissions.STREAM_CODEC, OpenMyTeamGUIMessage::permissions,
			OpenMyTeamGUIMessage::new
	);

	public static void handle(OpenMyTeamGUIMessage message, PacketContext ignored) {
		FTBTeamsClient.openMyTeamGui(message.permissions);
	}

	@Override
	public Type<OpenMyTeamGUIMessage> type() {
		return TYPE;
	}
}
