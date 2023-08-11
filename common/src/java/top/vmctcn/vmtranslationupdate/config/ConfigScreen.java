package top.vmctcn.vmtranslationupdate.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;

@Config(name = VMTranslationUpdate.MOD_ID)
public class ConfigScreen implements ConfigData {
    public String updateUrl = "https://vmct-cn.top/sb3/update.txt";//更新检测链接
    public String downloadUrl = "https://vmct-cn.top/sb3/";//下载链接
    public  String tipsUrl = "https://vmct-cn.top/tips.txt";//获取知识内容的链接
    public  String translationVersion = "1.0.0";//汉化mod版本
    public  Integer minutes = 25;
    public  String packName = "VM汉化组模组汉化包1.18.zip";
    public  String packUrl= "https://cdn.modrinth.com/data/IDWIdXwS/versions/xCpjJgHS/VM汉化组模组汉化包1.18.zip";
}
