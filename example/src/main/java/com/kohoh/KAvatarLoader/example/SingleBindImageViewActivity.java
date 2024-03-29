package com.kohoh.KAvatarLoader.example;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.kohoh.kavatarloader.AvatarRating;
import com.kohoh.kavatarloader.DefaultAvatar;
import com.kohoh.kavatarloader.KAvatarLoader;

import java.io.File;

public class SingleBindImageViewActivity extends ActionBarActivity {

    private ImageView iv_avatar_no_size;
    private ImageView iv_avatar_small_size;
    private ImageView iv_avatar_medium_size;
    private ImageView iv_avatar_large_size;
    private ImageView iv_avatar_xlarge_size;
    private ImageView iv_avatar_actionbar_size;
    private ActionBar action_bar;

    private Context context;
    private KAvatarLoader avatar_loader;

    @Override
    @TargetApi(11)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_bind_view);

        context = this;

        //实例化KavatarLoader
        avatar_loader = new KAvatarLoader(context);
        //设置默认的头像。在加载头像之前，容器会被绑定为默认的头像。如果加载失败，默认头像会始终与容器绑定。
        //项目中提供了GRAVATAR_ICON，MYSTERY_MEN，IDENTICON，WAVATAR等，由Gravatar提供的默认头像。
        //如果设置为HTTP_404,则不会绑定默认头像。且如果加载失败，你会在log中发现一条Error.
        //当然，你还以使用KAvatarLoader#setDefaultAvatar(int default_avatar_resource)设置自定义的默认头像。
        avatar_loader.setDefaultAvatar(DefaultAvatar.MYSTERY_MEN);
        //设置头像的等级。Gravatar将头像定义为GENERAL_AUDIENCES，
        // PARENTAL_GUIDANCE_SUGGESTED，RESTRICTED，XPLICIT四个等级
        //默认的等级为GENERAL_AUDIENCES。
        avatar_loader.setAvatarRating(AvatarRating.GENERAL_AUDIENCES);
        //设置KAvatarLoader的Execuor。通过该方法，可以控制KAvatarLoader的绑定行为。
        //如果设置为AsyncTask.SERIAL_EXECUTOR，则每次同时绑定一个容器。
        //如果设置为AsyncTask.THREAD_POOL_EXECUTOR,则每次最多同时绑定五个容器。
        //当然你也可是使用你自定义的Executor。
        //默认的Executor为AsyncTask.SERIAL_EXECUTOR。
        //只有API11以上版本支持该功能
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR1) {
            avatar_loader.setLoadExector(AsyncTask.SERIAL_EXECUTOR);
        }
        //设置是否缓存头像到运行内存中
        //如果设置为缓存头像，被加载的头像会缓存在运行内存中，以便重复使用。
        //默认为缓存头像
        avatar_loader.setUseCachedAvatar(true);
        //设置是否保存头像到设备中
        //如果设置为保存头像，被加载的头像会缓存在设备中，以便重复使用。在失去网络的情况下，仍旧可以加载头像。
        //默认为保存头像
        avatar_loader.setUseSavedAvatar(true);
        //设置保存头像的位置
        //通过该方法，你可以指定头像的存储位置。
        //默认的头像会保存在app相应文件的cache文件夹下
        avatar_loader.setCustomSavedAvatarsDir(new File(this.getExternalFilesDir(null), "avatars"));

        iv_avatar_no_size = (ImageView) findViewById(R.id.iv_avatar_no_size);
        iv_avatar_small_size = (ImageView) findViewById(R.id.iv_avatar_small_size);
        iv_avatar_medium_size = (ImageView) findViewById(R.id.iv_avatar_medium_size);
        iv_avatar_large_size = (ImageView) findViewById(R.id.iv_avatar_large_size);
        iv_avatar_xlarge_size = (ImageView) findViewById(R.id.iv_avatar_xlarge_size);
        iv_avatar_actionbar_size = (ImageView) findViewById(R.id.iv_avatar_actionbar_size);
        action_bar = getSupportActionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();

        bindImageView();
        bindActionBar();
    }

    //绑定ImageView，你还可以通过KAvatarLoader#bindImageViewByHashCode和
    // KAvatarLoader#bindImageViewByUrl实现该功能
    //bind_listener是一个实现了BindListener接口的对象。当完成对容器的绑定之后，
    // 会回调BindListener#onBindFinished(RESULT_CODE result_code)方法。
    private void bindImageView() {
        avatar_loader.bindImageViewByEmail(iv_avatar_no_size, "hagonzalez94@gmail.com", null);
        avatar_loader.bindImageViewByEmail(iv_avatar_small_size, "jfeinstein10@gmail.com", null);
        avatar_loader.bindImageViewByEmail(iv_avatar_medium_size, "chris@chrisjenx.co.uk", null);
        avatar_loader.bindImageViewByEmail(iv_avatar_large_size, "donn@donnfelker.com", null);
        avatar_loader.bindImageViewByEmail(iv_avatar_xlarge_size, "linuxmotion@gmail.com", null);
        avatar_loader.bindImageViewByEmail(iv_avatar_actionbar_size, "doesent@exist.com", null);
    }

    //绑定ActionBar，你还可以通过KAvatarLoader#bindActionBarByHashCode和
    // KAvatarLoader#bindActionBarByUrl实现该功能
    private void bindActionBar() {
        avatar_loader.bindActionBarByEmail(action_bar, "yccheok@yahoo.com", null);
    }
}
