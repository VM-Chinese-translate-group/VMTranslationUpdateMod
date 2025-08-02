package top.vmctcn.vmtu.mod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.ServerCommandSource;
import top.vmctcn.vmtu.mod.ModEvents;

public class ModCommands {
    public static void VMTUCommand() {
        LiteralArgumentBuilder.<ServerCommandSource>literal("vmtu")
                .requires(source -> source.hasPermissionLevel(0))
                .then(LiteralArgumentBuilder.<ServerCommandSource>literal("check")
                        .executes(context -> {
                            ModEvents.playerJoinEvent(MinecraftClient.getInstance().player);
                            return Command.SINGLE_SUCCESS;
                        }));
    }
}
