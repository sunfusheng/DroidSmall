# DroidSmall

该项目是 [Small](https://github.com/wequick/Small) 插件化调研和学习的示例应用，希望对你有帮助。

## 工程模块

[app](https://github.com/sfsheng0322/DroidSmall/tree/master/app) : 宿主模块  
[lib.style](https://github.com/sfsheng0322/DroidSmall/tree/master/lib.style) : styles库，包括公共的strings、colors、dimens等  
[lib.framework](https://github.com/sfsheng0322/DroidSmall/tree/master/lib.framework) : 基本框架库，包括网络请求、缓存、动态代理回调、Utils等。  
[app.main](https://github.com/sfsheng0322/DroidSmall/tree/master/app.main) : App的主模块  
[app.phone](https://github.com/sfsheng0322/DroidSmall/tree/master/app.phone) : 查询手机号码归属地模块  
[app.weather.beijing](https://github.com/sfsheng0322/DroidSmall/tree/master/app.weather) : 查询北京天气模块  
[app.weather.shanghai](https://github.com/sfsheng0322/DroidSmall/tree/master/app.shanghai.weather) : 查询上海天气模块  

## 写在前面的话  
这两年热修复、组件化、插件化很火，火到中国这方面的开源项目遍地开花，例如：屠毅敏的AndroidDynamicLoader、任玉刚的dynamic-load-apk、张勇的DroidPlugin、阿里的AndFix、林光亮的Small等，除了中国这些热修复、插件化的开源项目，你有听过外国的嘛。虽然你可能看过这样的文章《插件化从入门到放弃》，但你是否还看过这样的文章《插件化从放弃到捡起》，尽管应用热修复和插件化坑多、难度高，但我们还是一往情深、纵身向前，因为她的优点远多于她的缺点。

## 插件化在实际开发中的主要好处有：  
1、团队插件化开发并可以单独编译运行。  
2、如果插件出问题可以动态更新插件进行补救。  
3、根据业务需求增加和去掉相关插件。

这半年多我已经尝试应用了阿里的AndFix、张勇的DroidPlugin和林光亮的Small，最终决定在公司的项目中应用Small框架，因为它让开发更轻巧、简单、清晰的与插件无缝结合。下面具体说说Small的使用与相关问题，先看下作者给出的Small最佳实践。

## 基本原则  
宿主中不要放业务逻辑。只做加载插件以及调起主插件的操作。

## 重构步骤  
Android工程的组件一般分为两种：lib组件和application组件。 

#### 拆 `lib.*` ：公共库模块插件  
把各个第三方库拆出来做成一个个 `lib.*` 库模块插件，包括统计、地图、网络、图片、支付、分享等库。  
把老项目积累的业务公共代码(utils)分离出来封装成一个 lib.utils 插件。  
把基础的样式、主题分离出来封装成一个 lib.style 插件。

#### 拆 `app.*` ：业务模块插件  
把业务模块拆成 `app.*` 业务模块插件，他们可以依赖 `lib.*` 模块，显示调用 `lib.*` 中的各个API。  
相对独立的业务模块先拆，比如“详情页”、“关于我们”，如果剩下的业务不好拆，先放一个插件里。  
如果都不好拆，先把全部业务做成一个 app.main 主插件。

## Small的使用与注意  
Small的具体使用还请参考[GitHub 上 Small 的 Android 应用](https://github.com/wequick/Small/tree/master/Android)。  
需要注意的是模块的命名：  
模块命名如：`app.*`，`lib.*`，`web.*`  
包名命名如：`宿主包名.app.*`，`宿主包名.lib.*`，`宿主包名.web.*`  
因为Small会根据包名对插件进行归类，特殊的域名空间如：“.app.” 会让这变得容易。  
从Small的组件打包源码中(buildSrc/.../PluginType)能看到该框架是对插件进行归类的：  

    public enum PluginType {
        Unknown (0),
        Host    (1),
        App     (2),
        Library (3),
        Asset   (4)

        private int value
        public PluginType(int value) {
            this.value = value
        }
    }

## Small框架的源码概括  

在 GitHub 上 Small/DevSample 下可以看到Small包涵两套源码：  
buildSrc：组件编译插件，用于打包安卓组件包，包括分离宿主和公共库的类与资源、签名组建包、通过脚本的方式来解决资源冲突等问题。  
small：核心库，用于加载安卓组件包，包括动态加载类、动态加载资源、通过占坑Activity达到动态注册Activity的效果等。

## 业务模块和库模块的编译命令

编译 app.* 模块： ./gradlew buildBundle -q  
清除 app.* 数据： ./gradlew cleanBundle

编译 lib.* 库： ./gradlew buildLib -q  
清除 lib.* 数据： ./gradlew cleanLib

## Small应用记录

1、lib.* 与 lib.* 之间不支持资源的引用；app.* 可以引用 lib.* 下的资源。  

2、如果 lib.* 下资源变化特别是删除资源，也请删掉 lib.* 下 public.txt 资源编号集合，有时需要删掉宿主包下生成的smallLibs。 

3、第三方SDK的 meta、Service 等需要在宿主 App 的 manifest 中声明。

4、模块之间跳转有两种方式  

    (1). Small.openUri("phone/Number?num=18600604600", mContext);  
    (2). Intent intent = Small.getIntentOfUri("phone/Number", mContext);  
         intent.putExtra("num", "18600604600");  
         startActivity(intent);  
         不过第二种方式还有问题（Small稳定版本：0.9）  
         
5、获取传递的参数也是通过 Small 实现的  

    Uri uri = Small.getUri(this);  
    String num = uri.getQueryParameter("num");  
    
6、回传数据简单些不需要 startActivityForResult(Intent intent, int requestCode)，直接返回就好  

    Intent intent = new Intent();  
    intent.putExtra("result", "XXOO");  
    setResult(requestCode, intent);  
    建议传递 requestCode，将 requestCode 做为 resultCode 返回。  
    
## 插件升级策略

测试发现 Small 在前台更新完插件，重新启动 App 新的插件功能才会生效，
如果不重新启动插件而继续使用插件，程序可能会崩掉，可以看出 Small 还不支持热更新。

所以我的策略是App启动时检查是否需要更新插件，更新插件的 [updates.json](http://sunfusheng.com/assets/small/updates.json) 数据，增加插件的 [additions.json](http://sunfusheng.com/assets/small/additions.json) 数据。这时启动的是 IntentService 服务，IntentService 的优点在这里可以充分显示出来。
有关 IntentService 的使用请参考 [IntentService 示例与详解](http://sunfusheng.com/android/2016/07/01/IntentService.html)。如果需要更新插件，则再次启动服务，下载最新插件，下载完毕后，服务会自动停止。当退出 App 时再次启动服务检查是否需要更新插件（updateBundles），需要的话将插件合并到宿主包中，再次启动 App 时就可以看到最新的功能啦。

[GitHub上示例工程DroidSmall](https://github.com/sfsheng0322/DroidSmall)  
[APK下载地址](http://fir.im/DroidSmall)

如果 App 在您手机上运行异常或崩溃，请联系我，微信号：sfs321.

### 我的公众号

<img src="https://raw.githubusercontent.com/sfsheng0322/StickyHeaderListView/master/screenshots/%E5%BE%AE%E4%BF%A1%E5%85%AC%E4%BC%97%E5%8F%B7.jpg" style="width: 30%;">

### 关于我

个人邮箱：sfsheng0322@126.com

[GitHub主页](https://github.com/sfsheng0322)

[简书主页](http://www.jianshu.com/users/88509e7e2ed1/latest_articles)

[个人博客](http://sunfusheng.com/)

[新浪微博](http://weibo.com/u/3852192525)