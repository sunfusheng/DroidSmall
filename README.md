# DroidSmall

Small 插件化调研、学习、示例应用

## 工程模块

[app](https://github.com/sfsheng0322/DroidSmall/tree/master/app) : 宿主模块  
[lib.style](https://github.com/sfsheng0322/DroidSmall/tree/master/lib.style) : styles库，包括公共的strings、colors、dimens等  
[lib.framework](https://github.com/sfsheng0322/DroidSmall/tree/master/lib.framework) : 基本框架库，包括网络请求、缓存、动态代理回调、Utils等。  
[app.main](https://github.com/sfsheng0322/DroidSmall/tree/master/app.main) : App的主模块  
[app.phone](https://github.com/sfsheng0322/DroidSmall/tree/master/app.phone) : 查询手机号码归属地模块  
[app.weather](https://github.com/sfsheng0322/DroidSmall/tree/master/app.weather) : 查询北京天气模块  
[app.shanghai.weather](https://github.com/sfsheng0322/DroidSmall/tree/master/app.shanghai.weather) : 查询上海天气模块  


## 业务模块和库模块的编译命令

编译 app.* 模块： ./gradlew buildBundle -q  
清除 app.* 数据： ./gradlew cleanBundle

编译 lib.* 库： ./gradlew buildLib -q  
清除 lib.* 数据： ./gradlew cleanLib

## Small应用记录

1、lib.* 与 lib.* 之间不支持资源的引用；app.* 可以引用 lib.* 下的资源。  

2、如果 lib.* 下资源变化特别是删除资源，也请删掉 lib.* 下 public.txt 资源编号集合。 

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

所以我的策略是App启动时检查是否需要更新插件，这时启动的是 IntentService 服务，IntentService 的优点在这里可以充分显示出来。
有关 IntentService 的使用请参考 [IntentService 示例与详解](http://sunfusheng.com/android/2016/07/01/IntentService.html)。
如果需要更新插件，则再次启动服务，下载最新插件，下载完毕后，服务会自动停止。当退出 App 的启动服务检查是否需要更新插件，
需要的话将插件合并到宿主包中，再次启动 App 是就可以看到最新的功能啦。

[APK下载地址: http://fir.im/DroidSmall](http://fir.im/DroidSmall)

如果 App 在您手机上崩溃，请联系我，微信号：sfs321.

更新插件的 Json 数据如下：  
http://sunfusheng.com/assets/small/updates.json

增加插件的 Json 数据如下：  
http://sunfusheng.com/assets/small/additions.json

### 我的公众号

<img src="https://raw.githubusercontent.com/sfsheng0322/StickyHeaderListView/master/screenshots/%E5%BE%AE%E4%BF%A1%E5%85%AC%E4%BC%97%E5%8F%B7.jpg" style="width: 30%;">

### 关于我

个人邮箱：sfsheng0322@126.com

[GitHub主页](https://github.com/sfsheng0322)

[简书主页](http://www.jianshu.com/users/88509e7e2ed1/latest_articles)

[个人博客](http://sunfusheng.com/)

[新浪微博](http://weibo.com/u/3852192525)