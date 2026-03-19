package dev.ftb.mods.ftbteams.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import static dev.ftb.mods.ftbteams.command.FTBTeamsCommands.*;

public class NbtEditCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("nbtedit")
                .requires(requiresOPorSP())
                .executes(NbtEditCommand::editPlayerTeamNBT)
                .then(createTeamArg()
                        .executes(NbtEditCommand::editTeamNBT)
                );
    }

    private static int editPlayerTeamNBT(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        return edit(ctx, ctx.getSource().getPlayerOrException(), getTeam(ctx));
    }

    private static int editTeamNBT(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        return edit(ctx, ctx.getSource().getPlayerOrException(), TeamArgument.get(ctx, "team"));
    }

    private static int edit(CommandContext<CommandSourceStack> ctx, ServerPlayer editor, Team team) {
//        if (team instanceof AbstractTeam abstractTeam) {
//            CompoundTag info = Util.make(new CompoundTag(), t -> {
//                t.store("title", ComponentSerialization.CODEC, abstractTeam.getColoredName());
//                t.putString("type", "ftbteams:team");
//                t.store("id", UUIDUtil.CODEC, team.getTeamId());
//                t.putString("team_type", abstractTeam.getType().getSerializedName());
//                t.put("text", FTBLibraryCommands.InfoBuilder.create(ctx)
//                        .add("Team Type", Component.translatable(team.getTypeTranslationKey()))
//                        .add("Owner", Component.literal(team.getOwner().toString()))
//                        .add("Members", Component.literal(String.valueOf(team.getMembers().size())))
//                        .build()
//                );
//            });
//            Json5Object json = abstractTeam.toJson(ctx.getSource().getServer().registryAccess());
//            Server2PlayNetworking.send(editor, new EditNBTPacket(info, json));
//            return Command.SINGLE_SUCCESS;
//        }
        ctx.getSource().sendFailure(Component.literal("not implemented yet"));
        return 0;
    }
}
