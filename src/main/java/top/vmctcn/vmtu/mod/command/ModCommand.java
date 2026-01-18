package top.vmctcn.vmtu.mod.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.source.CommandSource;
import net.minecraftforge.server.command.CommandTreeBase;

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
        return "vmtu.command.usage";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canUse(MinecraftServer server, CommandSource source) {
        return true;
    }
}
