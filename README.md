<div align="center"> 
   <img height="100px" alt="logo" src="https://cdn.modrinth.com/data/wvCSIW08/a1ff154a62300a7e7813008d327a526503ad96f4.png"/> 
   <h1>VM Translation Update</h1>
</div> 

## 功能
1. 对比最新版汉化版本号与本地配置文件中的版本号，在汉化发布新版本时会在游戏里通知玩家去下载更新。
2. 默认每25分钟在聊天栏发送一条知识。内容包括但不限于汉化组冷知识，汉字易错读音和MC冷知识等。
3. 每次启动时自动下载VM汉化组通用模组汉化资源包。
4. 如果玩家已经下载资源包且发生错误未启用，会在聊天框提示玩家在选项->资源包中手动启用。
5. 如果玩家名是Zi__Min，会在每次进入世界时发送`欢迎来到籽岷的Minecraft游戏世界！`，且称呼改为岷叔。
6. 自动修改游戏语言，当安装Stenographer模组后不修改。

## 使用与配置文件

**发布汉化时，请特别注意。在汉化更新时改一下模组配置里的版本号！**

模组会在config文件夹创建名为`vmtranslationupdate`的配置文件（1.12.2后缀是.cfg，其他版本为.toml），共有7个配置选项。
1. 自动根据地区切换语言（`autoSwitchLanguage`）：默认禁用，在安装了Stenographer后将禁用。
2. 自动检测整合包汉化版本更新（`checkModPackTranslationUpdate`）：默认启用，在每次玩家进入世界后检查整合包汉化更新。
3. 自动下载VM汉化资源包（`autoDownloadVMTranslationPack`）：默认启用，在每次加载游戏时下载VM汉化资源包。
4. 自动安装VM汉化资源包（`autoInstallVMTranslationPack`）：默认启用，在每次加载游戏时安装VM汉化资源包。
5. 显示知识内容（`displayTips`）：默认启用，在玩家进入存档后每25分钟（可配置）在聊天栏发送一条知识。
6. 检测玩家名称（`playerNameCheck`）：默认启用，在玩家进入存档后检测玩家名称用于称呼。
7. 更新检测链接（`modPackTranslationUpdateCheckUrl`）：只能填写指向一个txt文件的链接，链接的txt文件只能有此整合包翻译版本号。如：https://vmct-cn.top/modpacks/example/update.txt
8. 下载链接（`modPackTranslationUrl`）：填写一个指向整合包下载页的链接。如：https://vmct-cn.top/modpacks/example/
9. 获取知识内容的链接（`tipsUrl`）：用于知识显示功能，此链接必须指向一个txt文件。
10. 整合包翻译版本（`modPackTranslationVersion`）：整合包汉化本地版本号。
11. 发送知识的时间间隔（分钟）（`tipsMinutes`）：用于知识显示功能，可配置显示的时间间隔，单位为分钟。
12. 下载资源包的链接（`translationPackUrl`）：填写一个汉化资源包下载链接，但不包括文件名及其后缀。如：https://cdn.modrinth.com/data/IDWIdXwS/versions/V5YtW17O/VM汉化组模组汉化包1.20.zip 你只需要填写 https://cdn.modrinth.com/data/IDWIdXwS/versions/V5YtW17O/
13. 资源包的文件名（`translationPackName`）：填写汉化资源包名称，不要带文件名后缀。如：VM汉化组模组汉化包1.20
14. 玩家对应称呼的链接（`nameUrl`）：用于称呼玩家功能，必须为一个指向json文件的链接。如：https://vmct-cn.top/name.json

--- 
## 支持版本
- [x] Forge
    - [x] 1.12.2
    - [x] 1.16.5
    - [x] 1.18.x
    - [x] 1.19.x
    - [x] 1.20~1.20.2
    - [x] 1.20.3~1.20.4
- [x] Fabric
    - [x] 1.16.5
    - [x] 1.18.x
    - [x] 1.19.x
    - [x] 1.20~1.20.2
    - [x] 1.20.3~1.20.4
- [x] NeoForge
    - [x] 1.20.2
    - [x] 1.20.3~1.20.4

## 其他
特别感谢TexTrue跨加载器重构，以及Lichiiiiiii修复部分bug！
许可证使用MIT协议
