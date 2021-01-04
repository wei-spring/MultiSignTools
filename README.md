
<img src="https://github.com/wei-spring/MultiSignTools/raw/master/src/resources/logo.png" alt="Logo">

# MultiSignTools

> MultiSignTools是Android APK二次签名和打多渠道包签名的一个可视化小工具。支持Android 7.0+ 签名只支持常见的keystore签名，如果需要证书等信息时候，需要支持certificate参数，可以clone源码，自行修改.

**软件使用环境：**
* Windows操作系统(配置jdk环境)
* Mac操作系统(配置jdk环境)

**整体多渠道打包流程简介：**

1. 运行软件，准备好签名keystore
2. 选择二次签名或者打多渠道包，如果是多渠道包，需要准备渠道信息，支持单渠道，也支持多个渠道（通过配置channel.txt，见img目录下示例）
3. Android代码中运行时，动态读取对应渠道标识

**软件界面介绍：**

1. 启动页面：

 这里主要是介绍软件使用场景.

2. 打包界面：
 > 关于打包界面的各个选项输入说明

 *Apk安装路径：*需要打多渠道包的Apk文件位置，可以选择，可以直接输入

 *KeyStore路径：*签名的keystore的路径

 *渠道文件路径：*该文件可以为.txt文件，里面输入渠道列表，渠道换行分开，读取时候会每行按一个渠道，进行打包，参见：img/channel.txt,同时也可以输入单个渠道，例如：BaiduZhuShou

 *Alias ,KeyStore密码就不用说了*

**附录：**
软件运行截图如下

Mac & Windows:

<img src="/img/multiTools_1_mac.png" width="887" height="673" />

<img src="/img/multiTools_2_mac.png" width="874" height="609" />

<img src="/img/multiTools_3_mac.jpg" width="853" height="612" />
