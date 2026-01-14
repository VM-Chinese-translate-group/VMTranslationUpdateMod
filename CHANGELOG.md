# 新功能

- 支持自定义资源包加载，并应用到最高层级
- 支持检测 TLR 模组是否安装，提供可选的纹理资源本地化支持 https://github.com/LocalizedMC/TextureLocaleRedirector
- 优化游戏内配置页面 UI，现在按功能分类显示
- 优化了一些日志格式
- 现在转换 Minecraft 版本对应支持的资源包元数据是联网获取的
- 添加 modpackinfo 里`modpack.version`自动读取整合包内置的metadata（如果有的话）功能（此功能需手动适配）

# 代码

- 将除 1.12.2 的其他版本迁移到stonecutter
- 迁移至 Mojmap
- 更新 VMTUCore
- 支持 MC 1.21.9-1.21.11
- 恢复publish workflow

# 已移除支持

- 移除了资源包下载源配置项及配置文件`PackSource`条目（已在 VMTU 4 起弃用）
- 移除了

# 修复

- 修复了在一些极端情况下 v2 版的 `vm-meta.json` 读取错误导致卡在创建世界屏幕的问题
