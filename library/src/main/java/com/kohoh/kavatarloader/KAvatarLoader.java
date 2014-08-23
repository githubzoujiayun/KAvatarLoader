package com.kohoh.kavatarloader;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;

import java.util.concurrent.Executor;

/**
 * Created by kohoh on 14-7-28.
 */
public class KAvatarLoader {
    static public final String TAG = KAvatarLoader.class.getSimpleName() + "_tag";
    static private Executor executor;
    private Context context;
    private DefaultAvatar default_avatar;
    private AvatarRating avatar_rating;

    private static boolean ifUseCachedAvatar = true;
    private static boolean ifUseSavedAvatar = true;

    public KAvatarLoader(Context context) {
        this.context = context;
        this.default_avatar = DefaultAvatar.HTTP_404;
        this.avatar_rating = AvatarRating.GENERAL_AUDIENCES;
    }

    int calculateAvatarSize(ImageView imageView) {
        //计算头像的尺寸
        final int height = imageView.getHeight();
        final int width = imageView.getWidth();
        final int avatar_size;
        if (height != width) {
            avatar_size = height < width ? height : width;
        } else if (height != 0) {
            avatar_size = height;
        } else {
            avatar_size = context.getResources().getDimensionPixelSize(R.dimen.default_avatar_size);
        }

        return avatar_size;
    }

    /**
     * 将指定的Gravatar头像绑定到指定的ImageView上
     * bind_listener实现了BindListener接口的对象。当完成对容器的绑定之后，
     * 会回调BindListener#onBindFinished(RESULT_CODE result_code)方法。
     * 你还可以通过KAvatarLoader#bindImageViewByHashCode和KAvatarLoader#bindImageViewByUrl实现该功能
     *
     * @param image_view    指定的ImagView
     * @param email         通过Email加载头像
     * @param bind_listener 绑定监听者
     * @return KAvatarLoader
     */
    public KAvatarLoader bindImageViewByEmail(final ImageView image_view, final String email,
                                              final BindListener bind_listener) {

        loadAvatar(image_view, bind_listener, TaskParm.TASK_PARM_STYLE.TASK_PARM_USE_EMAIL, email);

        return this;
    }

    /**
     * 将指定的Gravatar头像绑定到指定的ImageView上
     * bind_listener实现了BindListener接口的对象。当完成对容器的绑定之后，
     * 会回调BindListener#onBindFinished(RESULT_CODE result_code)方法。
     * 你还可以通过KAvatarLoader#bindImageViewByEmial和KAvatarLoader#bindImageViewByUrl实现该功能
     *
     * @param image_view    指定的ImagView
     * @param hash_code     通过HashCode加载头像
     * @param bind_listener 绑定监听者
     * @return KAvatarLoader
     */
    public KAvatarLoader bindImageViewByHashCode(final ImageView image_view, final String hash_code,
                                                 final BindListener bind_listener) {

        loadAvatar(image_view, bind_listener, TaskParm.TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE, hash_code);

        return this;
    }

    /**
     * 将指定的Gravatar头像绑定到指定的ImageView上
     * bind_listener实现了BindListener接口的对象。当完成对容器的绑定之后，
     * 会回调BindListener#onBindFinished(RESULT_CODE result_code)方法。
     * 你还可以通过KAvatarLoader#bindImageViewByHashCode和KAvatarLoader#bindImageViewByEmail实现该功能
     *
     * @param image_view    指定的ImagView
     * @param url           通过URL加载头像
     * @param bind_listener 绑定监听者
     * @return KAvatarLoader
     */
    public KAvatarLoader bindImageViewByUrl(final ImageView image_view, final String url,
                                            final BindListener bind_listener) {

        loadAvatar(image_view, bind_listener, TaskParm.TASK_PARM_STYLE.TASK_PARM_USE_URL, url);

        return this;
    }


    /**
     * 将指定的Gravatar头像绑定到指定的ActionBar上
     * bind_listener实现了BindListener接口的对象。当完成对容器的绑定之后，
     * 会回调BindListener#onBindFinished(RESULT_CODE result_code)方法。
     * 你还可以通过KAvatarLoader#bindActionBarByHashCode和KAvatarLoader#bindActionBarByUrl实现该功能
     *
     * @param action_bar    指定的ImagView
     * @param email         通过Email加载头像
     * @param bind_listener 绑定监听者
     * @return KAvatarLoader
     */
    public KAvatarLoader bindActionBarByEmail(final ActionBar action_bar, final String email,
                                              final BindListener bind_listener) {

        loadAvatar(action_bar, bind_listener, TaskParm.TASK_PARM_STYLE.TASK_PARM_USE_EMAIL, email);

        return this;
    }

    /**
     * 将指定的Gravatar头像绑定到指定的ActionBar上
     * bind_listener实现了BindListener接口的对象。当完成对容器的绑定之后，
     * 会回调BindListener#onBindFinished(RESULT_CODE result_code)方法。
     * 你还可以通过KAvatarLoader#bindActionBarByEmail和KAvatarLoader#bindActionBarByUrl实现该功能
     *
     * @param action_bar    指定的ImagView
     * @param hash_code     通过HashCode加载头像
     * @param bind_listener 绑定监听者
     * @return KAvatarLoader
     */
    public KAvatarLoader bindActionBarByHashCode(final ActionBar action_bar, final String hash_code,
                                                 final BindListener bind_listener) {

        loadAvatar(action_bar, bind_listener, TaskParm.TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE, hash_code);

        return this;
    }

    /**
     * 将指定的Gravatar头像绑定到指定的ActionBar上
     * bind_listener实现了BindListener接口的对象。当完成对容器的绑定之后，
     * 会回调BindListener#onBindFinished(RESULT_CODE result_code)方法。
     * 你还可以通过KAvatarLoader#bindActionBarByHashCode和KAvatarLoader#bindActionBarByEmail实现该功能
     *
     * @param action_bar    指定的ImagView
     * @param url           通过RUL加载头像
     * @param bind_listener 绑定监听者
     * @return KAvatarLoader
     */
    public KAvatarLoader bindActionBarByUrl(final ActionBar action_bar, final String url,
                                            final BindListener bind_listener) {

        loadAvatar(action_bar, bind_listener, TaskParm.TASK_PARM_STYLE.TASK_PARM_USE_URL, url);

        return this;
    }

    @TargetApi(11)
    private void loadAvatar(Object target_view, BindListener bind_listener,
                            TaskParm.TASK_PARM_STYLE task_parm_style, String address) {
        TaskParm task_parm = null;

        switch (task_parm_style) {
            case TASK_PARM_USE_URL:
                task_parm = new TaskParmUseUrl().setUrl(address);
                break;
            case TASK_PARM_USE_EMAIL:
                task_parm = new TaskParmUseEmail().setEmail(address);
                break;
            case TASK_PARM_USE_HASH_CODE:
                task_parm = new TaskParmUseHashCode().setHashCode(address);
                break;
        }

        switch (TaskParm.getTargetViewSytle(target_view)) {
            case ACTION_BAR:
                task_parm.setAvatarSize(context.getResources().getDimensionPixelSize(R.dimen.default_avatar_size));
                break;
            case IMAGE_VIEW:
                task_parm.setAvatarSize(calculateAvatarSize((ImageView) target_view));
                break;
        }

        task_parm.setDefaultAvatar(default_avatar).setAvatarRating(avatar_rating).
                setTargetView(target_view).setBindListner(bind_listener).
                setUseCachedAvatar(ifUseCachedAvatar).setUseSavedAvatar(ifUseSavedAvatar);

        //该特性只有API11以上版本拥有
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR1) {
            new AvatarLoadTask(context, task_parm).execute();
        } else {
            if (executor == null) {
                executor = AsyncTask.SERIAL_EXECUTOR;
            }
            new AvatarLoadTask(context, task_parm).executeOnExecutor(executor);
        }
    }

    /**
     * 设置默认的头像。
     * 在加载头像之前，容器会被绑定为默认的头像。如果加载失败，默认头像会始终与容器绑定。
     * 项目中提供了GRAVATAR_ICON，MYSTERY_MEN，IDENTICON，WAVATAR等，由Gravatar提供的默认头像。
     * 如果设置为HTTP_404,则不会绑定默认头像。且如果加载失败，你会在log中发现一条Error.
     * 当然，你还以使用KAvatarLoader#setDefaultAvatar(int default_avatar_resource)设置自定义的默认头像。
     *
     * @param default_avatar 默认头像
     * @return KAvatarLoader
     */
    public KAvatarLoader setDefaultAvatar(DefaultAvatar default_avatar) {
        this.default_avatar = default_avatar;
        return this;
    }

    /**
     * 设置自定义的默认头像。
     * 在加载头像之前，容器会被绑定为默认的头像。如果加载失败，默认头像会始终与容器绑定。
     * 当然，你还以使用KAvatarLoader#setDefaultAvatar(DefaultAvatar default_avatar)设置由Gravatar提供的默认头像。
     *
     * @param default_avatar_resource 默认头像
     * @return KAvatarLoader
     */
    public KAvatarLoader setDefaultAvatar(int default_avatar_resource) {
        DefaultAvatar default_avatar = DefaultAvatar.HTTP_404;
        default_avatar.setCustomDefaultAvatar(context.getResources().getDrawable(default_avatar_resource));
        setDefaultAvatar(default_avatar);
        return this;
    }

    /**
     * 设置头像的等级。
     * Gravatar将头像定义为GENERAL_AUDIENCES，PARENTAL_GUIDANCE_SUGGESTED，RESTRICTED，XPLICIT四个等级
     * 默认的等级为GENERAL_AUDIENCES。
     *
     * @param avatar_rating 头像等级
     * @return KAvatarLoader
     */
    public KAvatarLoader setAvatarRating(AvatarRating avatar_rating) {
        this.avatar_rating = avatar_rating;
        return this;
    }

    /**
     * 设置KAvatarLoader的Execuor。该特性只有API11以上版本拥有。
     * 通过该方法，可以控制KAvatarLoader的绑定行为。
     * 如果设置为AsyncTask.SERIAL_EXECUTOR，则每次同时绑定一个容器。
     * 如果设置为AsyncTask.THREAD_POOL_EXECUTOR,则每次最多同时绑定五个容器。
     * 当然你也可是使用你自定义的Executor。
     * 默认的Executor为AsyncTask.SERIAL_EXECUTOR。
     * 只有API11以上版本支持该功能
     *
     * @param executor
     */
    static public void setLoadExector(Executor executor) {
        KAvatarLoader.executor = executor;
    }

    public static void setUseCachedAvatar(boolean ifUseCachedAvatar) {
        KAvatarLoader.ifUseCachedAvatar = ifUseCachedAvatar;
    }

    public static void setUseSavedAvatar(boolean ifUseSavedAvatar) {
        KAvatarLoader.ifUseSavedAvatar = ifUseSavedAvatar;
    }
}


