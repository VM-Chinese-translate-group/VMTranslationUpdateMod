<div align="center"> 
   <img height="100px" width="100px" alt="logo" src="./common/src/main/resources/icon.png"/> 
   <h1>VM Translation Update</h1>

<a href="https://modrinth.com/project/vmupdate/">
<img alt="modrinth" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg">
</a>
<a href="https://www.curseforge.com/minecraft/mc-mods/vmtranslationupdate">
<img alt="curseforge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/curseforge_vector.svg">
</a> 

[MC百科](https://www.mcmod.cn/class/11203.html)

<img alt="forge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/forge_vector.svg"> (1.12.2~1.20.4)
<img alt="neoforge" height="56" src="https://github.com/mc-wiki/minecraft-mod-heywiki/blob/master/docs/supports_neoforge.svg?raw=true">
<img alt="fabric" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/fabric_vector.svg">
</div>

## 功能

模组的所有功能都是可配置的，关于配置文件请看后文。

1. 可配置在汉化发布新版本时会在游戏里通知玩家去下载更新。
2. 可配置是否开启自动下载并启用VM模组汉化资源包。
3. 可配置自动修改游戏语言。
4. 可配置是否检测安装i18nUpdateMod或Vault Patch补全汉化模组。如未安装将弹出提示页面引导下载（可按esc退出）

## 使用与配置文件

本模组需要前置依赖：Cloth Config API

模组会在`.minecraft/<mcversion>/config/`文件夹创建名为`vmtranslationupdate.toml`的配置文件（1.12.2后缀是.cfg）。

默认情况下，配置文件的内容和功能启用情况如下所示：
```toml
autoSwitchLanguage = true   # 自动切换语言
switchLanguage = "zh_cn"    # 自动切换为简体中文
autoDownloadVMTranslationPack = false # 默认不自动下载VM汉化资源包
translationPackSource = "GITEE"       # 汉化资源包下载源
checkModPackTranslationUpdate = true  # 默认检查汉化更新
modPackTranslationUpdateCheckUrl = "https://gitee.com/Wulian233/vmtu/raw/main/update/example.txt" # 汉化更新检查地址
modPackTranslationUrl = "https://vmct-cn.top/modpacks/example/" # 汉化下载地址
modPackTranslationVersion = "1.0.0" # 汉化版本
i18nUpdateModCheck = true # 默认检查是否安装i18nUpdateMod模组
vaultPatcherCheck = false # 默认不检查是否安装Vault Patch模组
```

--- 

## 支持版本

| 模组加载器    | 支持的Minecraft版本                         |
|----------|----------------------------------------|
| Forge    | 1.12/1.16.5/1.18-1.20.4                |
| NeoForge | 1.20.1-1.21.4（1.20.1NeoForge兼容Forge模组） |
| Fabric   | 1.16.5-1.21.4（支持ModMenu）               |

## 其他

作者：Wulian233（捂脸）、TexTrue、Lichiiiiiii

本模组使用MIT许可证
VMTUCore基于I18nUpdateMod3修改，AGPL许可证
