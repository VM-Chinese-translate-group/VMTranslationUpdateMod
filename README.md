<div align="center"> 
   <a href="https://vmct-cn.top/img/vmupdate.png"><img height="100px" alt="logo" src="https://vmct-cn.top/img/vmupdate.png"/></a> 
   <p><em>在汉化发布新版本时，在游戏里通知玩家去下载更新。</em></p>
</div> 

## 重要！使用

发布汉化时，尤其是组长曲逸请注意。请在汉化更新时随手改一下这个模组配置里的版本号。
模组会在config文件夹创建`vmtranslationupdate`的配置文件（1.12.2后缀是.cfg，其他版本都是.toml），有4个配置选项。
1. 获取TXT检测更新的url。比如`https://vmct-cn.top/sb3/update.txt`就是读取这个网络文件的内容，必须是UTF-8编码，可以直接打开的直链。
2. 提示玩家下载地址的url。比如`https://vmct-cn.top/sb3/`，会在聊天栏展示。
3. 当前汉化版本。比如第一版。
4. 间隔多少分钟发送知识（仅1.12.2 1.18.x），默认25分钟一次。
--- 
 ## 支持版本
 - [x] Forge
     - [x] 1.12.2
     - [x] 1.16.5
     - [x] 1.18.x
     - [x] 1.19.2
     - [x] 1.19.3/4
     - [x] 1.20.x
 - [x] Fabric
   - [x] 1.16.5
   - [x] 1.18.x
   - [x] 1.19.2
   - [x] 1.19.3/4
   - [x] 1.20.x

## 原理
很简单，将网络读取txt内容和本地配置里的汉化版本对比。相同则不提示，反之提示。
比如网络上txt里的内容是第二版，本地配置里是第一版，不一样。那么就会提示更新。
说白了就是a≠b，提示；a=b，不提示。
## 其他

特别感谢TexTrue移植模组！
## 许可证
MIT
