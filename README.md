KAvatarLoader
=============

##项目介绍

KAvatarLoader是一个工具。通过她你可以简单高效将Gravatar上的头像绑定到特定的容器当中。目前支持的容器有ActionBar和ImageView。  

##使用指南

####STEP0 导项目
你只需将项目中的library文件复制到你的项目中，并在你的gradle配置文件中设置相应的依赖。你可以通过参考example模块的build.gradle得到更多信息。

<b>注意：</b>需要在gradle配置文件中加入下面内容，否则会出现构建错误。  
```
packagingOptions {
    exclude 'META-INF/LICENSE.txt'
    exclude 'META-INF/NOTICE.txt'
}
```

####STEP1 实例化KAvatarLoader
```
avatar_loader = new KAvatarLoader(context);
```

####STEP2 配KAvatarLoader
```
//设置默认的头像。
avatar_loader.setDefaultAvatar(DefaultAvatar.MYSTERY_MEN);
//设置头像的等级。
avatar_loader.setAvatarRating(AvatarRating.GENERAL_AUDIENCES);
//设置KAvatarLoader的Execuor。
//只有API11以上版本支持该功能
if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR1) {
    avatar_loader.setLoadExector(AsyncTask.SERIAL_EXECUTOR);
}
//设置是否缓存头像到运行内存中
avatar_loader.setUseCachedAvatar(true);
//设置是否保存头像到设备中
avatar_loader.setUseSavedAvatar(true);
//设置保存头像的位置
avatar_loader.setCustomSavedAvatarsDir(new File(this.getExternalFilesDir(null), "avatars"));
```

通过`KavatarLoade#setDefaultAvatar`设置默认头像。在加载头像之前，容器会被绑定为默认的头像。如果加载失败，默认头像会始终与容器绑定。项目中提供了`GRAVATAR_ICON`，`MYSTERY_MEN，IDENTICON`，`WAVATAR`等，由Gravatar提供的默认头像。如果默认头像被设置为`HTTP_404`,则不会绑定默认头像。此时如果加载失败，你会在log中发现一条Error。当然，你还以使用`KAvatarLoader#setDefaultAvatar(int default_avatar_resource)`设置自定义的默认头像。

通过`KavatarLoade#setAvatarRating`设置头像的等级。Gravatar将头像定义为`GENERAL_AUDIENCES`，`PARENTAL_GUIDANCE_SUGGESTED`，`RESTRICTED`，`XPLICIT`四个等级。默认的等级为GENERAL_AUDIENCES。

通过`KavatarLoade#setLoadExector`设置KAvatarLoader的Execuor。通过该方法，可以控制KAvatarLoader的绑定行为。如果设置为`AsyncTask.SERIAL_EXECUTOR`，则每次同时绑定一个容器。如果设置为`AsyncTask.THREAD_POOL_EXECUTOR`,则每次最多同时绑定五个容器。当然你也可是使用你自定义的Executor。默认的Executor为`AsyncTask.SERIAL_EXECUTOR`。只有API11以上版本支持该功能。

通过`KavatarLoade#setUseCachedAvatar`设置是否缓存头像到运行内存中。如果设置为缓存头像，被加载的头像会缓存在运行内存中，以便重复使用。默认为缓存头像。

通过`KavatarLoade#setUseSavedAvatar`设置是否保存头像到设备中。如果设置为保存头像，被加载的头像会缓存在设备中，以便重复使用。在失去网络的情况下，仍旧可以加载头像。默认为保存头像。

通过`KavatarLoade#setCustomSavedAvatarsDir`设置保存头像的位置。设置保存头像的位置。默认的头像会保存在app相应文件的cache文件夹下。

####STEP3 绑定容器
```
//绑定ImageView
avatar_loader.bindImageViewByEmail(image_view, "kavatarloader1@126.com", bind_listener);
```
你还可以通过`KAvatarLoader#bindImageViewByHashCode`和`KAvatarLoader#bindImageViewByUrl`实现绑定`ImageView`

```
//绑定ActionBar
avatar_loader.bindActionBarByEmail(action_bar, "kavatarloader2@126.com", bind_listener);
```
你还可以通过`KAvatarLoader#bindActionBarByHashCode`和`KAvatarLoader#bindActionBarByUrl`实现绑定`ActionBar`

`bind_listener`是一个实现了`BindListener`接口的对象。当完成对容器的绑定之后，会回调`BindListener#onBindFinished(RESULT_CODE result_code)`方法。

##更新内容

20140814 兼容API11以下版本

20140824 实现缓存和保存头像的功能

##待完成功能

+ 实现头像的滤镜功能
