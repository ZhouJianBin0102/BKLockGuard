# [贝壳锁屏防盗](http://android.myapp.com/myapp/detail.htm?apkName=com.zhoujianbin.bklockguard)

[![bannar][bannar_svg]][bannar]

[![weibo][weibo_svg]][weibo]
[![jianshu][jianshu_svg]][jianshu]
[![qq_group][qq_group_svg]][qq_group]
[![License][license_svg]][license] 

## 简述

**贝壳锁屏防盗**，这是一款使用手机已有锁屏（自带锁屏或第三方锁屏应用），基于手机的传感器功能和各种状态的监听功能进行防盗的应用。

本人职业产品汪，业余程序猿，代码根基不深，请多包涵。

本来想用MVP模式开发的，但是功能和界面较少，主要都是监听等逻辑问题，所以就没有用也没必要。

（嗯~其实就是懒Ψ(￣∀￣)Ψ）

因为没有用复杂结构模式且代码基本都进行了注释，所以非常**适合初学者**。
 
（嗯~其实就是业余能力有限o(*￣3￣)o）

欢迎大家能指出一些想法和问题，然后共同探讨研究修正。

## 预览

![screen_gif](https://github.com/ChinaFir/BKLockGuard/blob/origin/screenshot/screenshow.gif)
![screen1](https://github.com/ChinaFir/BKLockGuard/blob/origin/screenshot/shotscreen1.jpg)
![screen2](https://github.com/ChinaFir/BKLockGuard/blob/origin/screenshot/shotscreen2.jpg)
![screen3](https://github.com/ChinaFir/BKLockGuard/blob/origin/screenshot/shotscreen3.jpg)
![screen4](https://github.com/ChinaFir/BKLockGuard/blob/origin/screenshot/shotscreen4.jpg)
![screen5](https://github.com/ChinaFir/BKLockGuard/blob/origin/screenshot/shotscreen5.jpg)

## 下载

### [贝壳锁屏防盗（腾讯应用宝下载页）](http://android.myapp.com/myapp/detail.htm?apkName=com.zhoujianbin.bklockguard)

不用应用宝的亲，本应用最新版已上传至应用宝、360、豌豆荚、搜狗、锤子等多个主流市场，华为、小米、vivo、oppo等因为不接受个人应用上传所以没有。

同时，可以百度一下“贝壳锁屏防盗”或“贝壳防盗”进行下载。

注意：很多盗版市场不知怎么有了我的应用，这些市场的版本有些是V1.0.4（含）版本以下的，但是V1.0.4（含）版本以下的，我已经不维护且已不能使用（原因见下面的“历史版本”），下载时请注意。

## 使用方法

**口袋防盗**

原理：利用手机距离传感器判断

1. 点击主界面的按钮，默认会有震动和提示音提醒防盗服务开启；
2. 随时锁屏后放入口袋（必须先锁屏），延时后默认会有震动和提示音提醒防护服务开启；
3. 防护服务开启后，只要拿出口袋，就会立即震动提醒；
4. 此时解除锁屏即可解除震动；
5. 如果震动一段时间仍然不解除锁屏，就会发出报警声（所以报警声发出延时最好设置为您解除锁屏需要的时间）
6. 此时解除锁屏即可解除报警声；
（使用过程中的声音、震动、延时都可以在设置界面自定义）

**USB防盗**

原理：监听充电状态

1. 点击主界面的按钮，默认会有震动和提示音提醒防盗服务开启；
2. 锁屏后插上USB线连接电脑或充电（必须先锁屏），默认会有震动和提示音提醒防护服务开启；
3. 防护服务开启后，只要拔出USB线，就会立即震动提醒；
4. 此时解除锁屏即可解除震动；
5. 如果震动一段时间仍然不解除锁屏，就会发出报警声（所以报警声发出延时最好设置为您解除锁屏需要的时间）
6. 此时解除锁屏即可解除报警声；
（使用过程中的声音、震动、延时都可以在设置界面自定义）

**使用手机已有锁屏而不另外自己做锁屏的原因**

- 自带锁屏或第三方锁屏应用符合用户使用习惯；
- 自带锁屏或第三方锁屏应用一般花样繁多更能满足用户；
- 懒(￣o￣) . z Z

**应用场景**

- 公交车等有被盗隐患的公共场所；
- 充电时怕离开被人拿走的情况；
- 更多场景功能想法欢迎交流探讨。

## 历史版本

**V1.0.5、V1.0.6**

- 全新改版
- 界面主题使用科技黑色调
- 功能简化

**V1.0.4（含）以前版本（不维护）**

不维护原因：

- 这些版本因为友盟服务迁移原因，导致应用开启会秒闪退，虽然有新SDK但是不想换，理由见下面“吐个槽”。
- 这些版本使用的是友盟更新，但是友盟更新2016年中下旬就已经停止服务了，导致这些版本无法升级到最新版。
- V1.0.4（含）以前版本是2015年底前做的，当时代码没有做托管，时间隔得太久，源码加签名已经都不见了，所以1.0.5以上版本是完全重新撸出来的
（这个应用其实是我的毕设，至于为什么隔了两年还去碰这个毕设，其中原委请见下面的“关于本应用”）

**吐个槽**

友盟被阿里收购后真的是折磨死人！ (ノ｀ー´)ノ・・・~~┻━┻
- 友盟自动更新服务停了。
- 服务迁移阿里百川，你说迁移就迁移把，但是很多都只迁移一半，就是友盟有这功能，阿里百川也有这功能，然后就两边残缺。
- 友盟的用户反馈先是迁到阿里百川，然后现在直接放在了阿里云。。。
- 虽然友盟曾经那么的好用，但是我现在已经不敢用友盟了，哪知道他哪天又抽风又迁移一个服务，又要做死人，唉~

## 使用的第三方服务和应用

**第三方服务和应用的挑选都是经过我细心研究和深思熟虑的，有时间我写一篇文章，希望能帮到大家，敬请期待。**

**产品服务应用**

身为一个职业产品汪先要接入的当然是产品服务应用

- 统计分析：腾讯分析MTA
- 用户反馈：阿里云移动用户反馈
- 市场分析：ASO100

**开发服务应用**

作为一个业余程序猿用到的开发服务应用

- 代码托管：coding
- 异常上报：腾讯Bugly
- 应用更新：腾讯Bugly（没有错哦~Bugly有更新服务，且支持线上自定义bannar）
- 应用托管：蒲公英托管平台
- 社会分享：ShareSDK
- 图片存放：七牛云
- 移动广告：广点通（声明：本应用除了开屏广告，没有其它任何广告）
- 应用加固：360加固
- 渠道分包：360加固
- 分发签名：360加固
- 市场上传：酷传

**郑重感谢提供以上第三方服务和应用的公司!**

## 使用的开源项目和插件

- ButterKnife、CardView、RecyclerView
- [log库：ALog](https://github.com/Blankj/ALog)
- [提醒标记：BadgeView](https://github.com/qstumn/BadgeView)
- [切换按钮：SwitchButton](https://github.com/zcweng/SwitchButton)
- [进度条：AVLoadingIndicatorView](https://github.com/81813780/AVLoadingIndicatorView)
- [波纹点击效果：material-ripple](https://github.com/balysv/material-ripple)
- [波纹背景效果：ripple-background](https://github.com/skyfishjy/android-ripple-background)
- [右划返回：BGASwipeBackLayout](https://github.com/bingoogolapple/BGASwipeBackLayout-Android)
- [波纹跳转效果：CircularAnim](https://github.com/XunMengWinter/CircularAnim)

**郑重感谢以上开源作者！**

## 关于应用

**应用信息**

应用名：贝壳锁屏防盗

出品：杉木君工作室

产品：杉木君

开发：杉木君

设计：杉木君

运营：杉木君

后勤：杉木君

......

**应用轶事**

本人是大四时一年基本大部分自学的android，但是各种原因不想进程序猿这行，同时我对创造设计App应用充满兴趣，所以毕业后变成了产品汪。

当时这个应用是我大四的毕业设计，做完顺便放上了各大应用商店，然后就没理过了。

下载量和反馈虽然少但也有断断续续的，因为快两年没更新过。

今年（2017年）陆陆续续有下载的用户找到我反映安装后使用不了的问题（该问题是友盟问题，详见上面的“历史版本”），然后今年6月份有个用户找到通过我的微博私信希望我解决下并在交流中感谢了我，于是我顺便有了把这个应用重新捡起来的想法。

因为历史久远，所以源码和签名都没了，只能自己撸，过程中发现github上的开源项目大大缩减了我编代码的时间（想起两年前，觉得安卓默认控件真的丑。那时只能自己一个个自定义控件，说多了都是泪(TㅅT)），那我想想就顺便把这个应用也开源了吧，虽然我根基不深，但是希望能给到别人一些想法，帮到别人。

好像都是顺便。。。好吧，我就是顺便的~b(￣▽￣)d

接下来我想把我自己设计产品，功能开发，分发上线的一些经验写一写，就当记录了，希望能帮到有需要的人。

[![weibo][weibo_svg]][weibo]
[![jianshu][jianshu_svg]][jianshu]
[![qq_group][qq_group_svg]][qq_group]

[bannar_svg]:https://github.com/ChinaFir/BKLockGuard/blob/origin/screenshot/banner.png
[bannar]:http://android.myapp.com/myapp/detail.htm?apkName=com.zhoujianbin.bklockguard

[license_svg]:https://img.shields.io/badge/License-Apache--2.0-blue.svg
[license]:https://github.com/ChinaFir/BKLockGuard/blob/master/LICENSE

[weibo_svg]:https://img.shields.io/badge/%E5%BE%AE%E5%8D%9A-%E6%9D%89%E6%9C%A8%E5%90%9BChinaFir-brightgreen.svg
[weibo]:http://weibo.com/1739265670

[jianshu_svg]:https://img.shields.io/badge/%E7%AE%80%E4%B9%A6-%E6%9D%89%E6%9C%A8%E5%90%9BChinaFir-brightgreen.svg
[jianshu]:http://www.jianshu.com/u/2b445046ee55

[qq_group_svg]:https://img.shields.io/badge/QQ%E7%BE%A4-652759380-brightgreen.svg
[qq_group]:https://shang.qq.com/wpa/qunwpa?idkey=a47a17736206abdbd13da19712b4499d64456f26328d71e9363ea162277c3360
