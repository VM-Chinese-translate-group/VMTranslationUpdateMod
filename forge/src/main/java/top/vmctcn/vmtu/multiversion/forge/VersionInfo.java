//? if 1.16.5 {
/*package top.vmctcn.vmtu.multiversion.forge;

import net.minecraftforge.versions.forge.ForgeVersion;
import net.minecraftforge.versions.mcp.MCPVersion;

public class VersionInfo {
    public final String forgeVersion;
    public final String mcVersion;
    public final String mcpVersion;
    public final String forgeGroup;

    public VersionInfo() {
        this.forgeVersion = ForgeVersion.getVersion();
        this.mcVersion = MCPVersion.getMCVersion();
        this.mcpVersion = MCPVersion.getMCPVersion();
        this.forgeGroup = ForgeVersion.getGroup();
    }

    public String forgeVersion() {
        return forgeVersion;
    }

    public String mcVersion() {
        return mcVersion;
    }

    public String mcpVersion() {
        return mcpVersion;
    }

    public String forgeGroup() {
        return forgeGroup;
    }

    public String mcAndForgeVersion() {
        return mcVersion + "-" + forgeVersion;
    }

    public String mcAndMCPVersion() {
        return mcVersion + "-" + mcpVersion;
    }
}
*///?}
