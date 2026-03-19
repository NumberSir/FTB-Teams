package dev.ftb.mods.ftbteams.net;

import dev.ftb.mods.ftblibrary.platform.network.PacketContext;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record SendMessageMessage(String msg) implements CustomPacketPayload {
	public static final Type<SendMessageMessage> TYPE = new Type<>(FTBTeamsAPI.id("send_message"));

	public static final StreamCodec<FriendlyByteBuf, SendMessageMessage> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8, SendMessageMessage::msg,
			SendMessageMessage::new
	);

	public static void handle(SendMessageMessage message, PacketContext context) {
		ServerPlayer player = (ServerPlayer) context.player();
		FTBTeamsAPI.api().getManager().getTeamForPlayer(player)
				.ifPresent(team -> team.sendMessage(player.getUUID(), message.msg));
	}

	@Override
	public Type<SendMessageMessage> type() {
		return TYPE;
	}
}
