# v3.0 更新日志

## 移除

- 移除指定玩家id称呼功能，包括`playerNameCheck`和`nameUrl`配置项
- 移除籽岷游戏id彩蛋
- 移除tips功能

# 新功能

- 现在自动切换语言（`autoSwitchLanguage`）功能默认为开启状态
- 未安装推荐模组时模组安装提示屏幕的“退出游戏”按钮改为“我知道我在做什么！”按钮，将不会退出游戏
- 模组安装提示屏幕现在仅会在简体中文时出现
- 重新添加资源包下载装载功能 （下载核心基于I18nUpdateMod3代码修改）
- 新增`modpackinfo.json`，内容详见 [#14](https://github.com/VM-Chinese-translate-group/VMTranslationUpdateMod/issues/14)

# 修复

- 修复按esc键无法退出推荐模组安装提示屏幕的bug
- 修复更新检测出现问题时提示两次的问题

# 优化

- 优化一些语言文件的内容表达
- 繁体中文本地化改进
- 优化语言切换功能 （基于I18nUpdateMod3代码修改）
- 优化CoreMod检测方式
- 整合包信息从配置文件迁移至`modpackinfo.json`，迁移项如下：
    - `switchLanguage` -> `modpack`下的`translation`下的`language`
    - `modPackTranslationUpdateCheckUrl` -> `modpack`下的`translation`下的`updateCheckUrl`
    - `modPackTranslationUrl` -> `modpack`下的`translation`下的`url`
    - `modPackTranslationVersion`-> `modpack`下的`translation`下的`version`

## 核心开发

- 合并模组为多模组加载器版工具 forgix -> modfusioner
- `com.github.johnrengelman.shadow` -> `com.gradleup.shadow`
- 更新所有依赖版本
- 修复GitHub CI 工作流
- 简化代码
- 添加部分跨版本兼容代码
- 从1.21.4backport至1.20.6
- 添加基于I18nUpdateMod3代码的下载核心