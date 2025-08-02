package top.vmctcn.vmtu.mod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.network.ClientCommandSource;
import top.vmctcn.vmtu.mod.ModEvents;

public class ModCommands {
    public static void VMTUCommand() {
        LiteralArgumentBuilder.<ClientCommandSource>literal("vmtu")
                .requires(source -> source.hasPermissionLevel(0))
                .then(LiteralArgumentBuilder.<ClientCommandSource>literal("check")
                        .executes(context -> {
                            ModEvents.playerJoinEvent();
                            return Command.SINGLE_SUCCESS;
                        }));
    }
}
