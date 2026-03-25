package dev.ftb.mods.ftbteams.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.ftb.mods.ftblibrary.platform.event.NativeEventPosting;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.event.TeamInfoEvent;
import dev.ftb.mods.ftbteams.data.FTBTUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static dev.ftb.mods.ftbteams.command.FTBTeamsCommands.*;

public class InfoCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("info")
                .then(Commands.literal("server_id")
                        .executes(ctx -> serverId(ctx.getSource()))
                )
                .then(createTeamArg()
                        .executes(ctx -> info(ctx.getSource(), teamArg(ctx)))
                )
                .executes(ctx -> info(ctx.getSource(), getTeam(ctx)));
    }

    private static int info(CommandSourceStack source, Team team) {
        List<Component> lines = new ArrayList<>(team.getTeamInfo());

        NativeEventPosting.INSTANCE.postEvent(new TeamInfoEvent.Data(team, lines::add));

        lines.forEach(line -> source.sendSuccess(() -> line, false));

        return Command.SINGLE_SUCCESS;
    }

    private static int serverId(CommandSourceStack source) {
        UUID managerId = FTBTeamsAPI.api().getManager().getId();
        source.sendSuccess(() -> Component.literal("Server ID: ")
                        .append(FTBTUtils.makeCopyableComponent(managerId.toString()).withStyle(ChatFormatting.YELLOW)),
                false);
        return Command.SINGLE_SUCCESS;
    }
}
