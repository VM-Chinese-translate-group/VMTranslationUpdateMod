package top.vmctcn.vmtranslationupdate.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdateMod;

@Config(name = VMTranslationUpdateMod.MOD_ID)
public class ConfigScreen implements ConfigData {
    public String updateUrl = "https://vmct-cn.top/sb3/update.txt";
    public String downloadUrl = "https://vmct-cn.top/sb3";
    public String translationVersion = "1.0.0";
}
