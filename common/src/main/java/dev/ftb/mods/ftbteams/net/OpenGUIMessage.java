package dev.ftb.mods.ftbteams.net;

import dev.ftb.mods.ftblibrary.platform.network.PacketContext;
import dev.ftb.mods.ftblibrary.platform.network.Play2ServerNetworking;
import dev.ftb.mods.ftblibrary.platform.network.Server2PlayNetworking;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.data.PlayerPermissions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record OpenGUIMessage() implements CustomPacketPayload {
	public static final Type<OpenGUIMessage> TYPE = new Type<>(FTBTeamsAPI.id("open_gui"));

	private static final OpenGUIMessage INSTANCE = new OpenGUIMessage();

	public static final StreamCodec<FriendlyByteBuf, OpenGUIMessage> STREAM_CODEC = StreamCodec.unit(INSTANCE);

	public static void handle(@SuppressWarnings("unused") OpenGUIMessage message, PacketContext context) {
			ServerPlayer player = (ServerPlayer) context.player();
			FTBTeamsAPI.api().getManager().getTeamForPlayer(player)
					.ifPresent(_ -> Server2PlayNetworking.send(player, new OpenMyTeamGUIMessage(PlayerPermissions.forPlayer(player))));
	}

	public static void sendToServer() {
		Play2ServerNetworking.send(INSTANCE);
	}

	@Override
	public Type<OpenGUIMessage> type() {
		return TYPE;
	}
}
