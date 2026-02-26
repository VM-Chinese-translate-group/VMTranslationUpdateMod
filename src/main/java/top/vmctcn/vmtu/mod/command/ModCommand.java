package top.vmctcn.vmtu.mod.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.source.CommandSource;
import net.minecraftforge.server.command.CommandTreeBase;
import top.vmctcn.vmtu.mod.ModContexts;

public class ModCommand extends CommandTreeBase {

    public ModCommand() {
        addSubcommand(new CommandCheck());
    }

    @Override
    public String getName() {
        return "vmtu";
    }

    @Override
    public String getUsage(CommandSource source) {
        return ModContexts.getTranslationKey("command", "usage");
    }

    @Override
    public boolean canUse(MinecraftServer server, CommandSource source) {
        return true;
    }
}
