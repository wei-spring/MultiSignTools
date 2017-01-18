# MultiSignTools

> MultiSignTools是Android多渠道打包签名的一个可视化小工具，主要适用于网上比较常见的多渠道打包方式：在META-INF/目录下放一空文件标识渠道，然后运行时，读取文件名字，标识渠道。支持Android 7.0+(已测)签名只支持常见的keystore签名，如果需要证书等信息时候，需要支持certificate参数，可以clone源码，自行修改.

**软件使用环境：**
* Windows操作系统(配置jdk环境)
* Mac操作系统(配置jdk环境)

**整体多渠道打包流程简介：**

1. 运行软件，准备好签名keystore,对应channel.txt,以及其他相关信息，选择打包
2. Android代码中运行时，动态读取对应渠道标识

**软件界面介绍：**

1. 启动登录页面：

 这里主要是用户名，密码的校验，由于一般企业内部使用，会有一些密码什么敏感信息，所以对用户名，密码需要校验，在userVerify方法里面，这个可以clone源码，自己修改校验逻辑，现在是默认只要用户名是.com结尾的，也既是默认是企业邮箱，密码随便输入就可以登录成功.

2. 打包界面：
 > 关于打包界面的各个选项输入说明

 *Apk安装路径：*需要打多渠道包的Apk文件位置，可以选择，可以直接输入

 *KeyStore路径：*签名的keystore的路径

 *渠道文件路径：*该文件可以为.txt文件，里面输入渠道列表，渠道换行分开，读取时候会每行按一个渠道，进行打包，参见：img/channel.txt,同时也可以输入单个渠道，或者多个渠道，输入时候以英文,分割渠道，例如：BaiduZhuShou,MeiZuShiChang

 *标识文件前缀：*塞入空文件的前缀，为了读取方便，参见img/GetChannelTools.java里面读取逻辑，也即是CHANNEL_KEY。

 *Alias ,KeyStore密码就不用说了*


**附录：**
软件运行截图如下

Windows:

<img src="/img/multiTools_1_windows.png" width="600" height="330" />

<img src="/img/multiTools_2_windows.png" width="600" height="330" />

Mac:

<img src="/img/multiTools_1_mac.png" width="600" height="330" />

<img src="/img/multiTools_2_mac.png" width="600" height="330" />
