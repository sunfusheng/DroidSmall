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

3、模块之间跳转有两种方式  

    (1). Small.openUri("phone/Number?num=18600604600", mContext);  
    (2). Intent intent = Small.getIntentOfUri("phone/Number", mContext);  
         intent.putExtra("num", "18600604600");  
         startActivity(intent);  
         不过第二种方式还有问题（Small稳定版本：0.9）  
         
4、获取传递的参数也是通过 Small 实现的  

    Uri uri = Small.getUri(this);  
    String num = uri.getQueryParameter("num");  
    
5、回传数据简单些不需要 startActivityForResult(Intent intent, int requestCode)，直接返回就好  

    Intent intent = new Intent();  
    intent.putExtra("result", "XXOO");  
    setResult(requestCode, intent);  
    建议传递 requestCode，将 requestCode 做为 resultCode 返回。  
    
## 插件升级

    测试发现 Small 在前台更新完插件，重新启动 App 新的插件功能才会生效，如果不重新启动插件使用插件功能，会崩掉。