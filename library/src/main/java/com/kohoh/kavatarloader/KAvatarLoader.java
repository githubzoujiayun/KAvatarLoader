package com.kohoh.kavatarloader;

import android.app.ActionBar;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.util.concurrent.Executor;

/**
 * Created by kohoh on 14-7-28.
 */
public class KAvatarLoader {
    static public final String TAG = KAvatarLoader.class.getSimpleName() + "_tag";
    static private Executor executor = AsyncTask.SERIAL_EXECUTOR;
    private Context context;
    private DefaultAvatar default_avatar;
    private AvatarRating avatar_rating;

    public KAvatarLoader(Context context) {
        this.context = context;
        this.default_avatar = DefaultAvatar.HTTP_404;
        this.avatar_rating = AvatarRating.GENERAL_AUDIENCES;
    }

    static public void setLoadExector(Executor executor) {
        KAvatarLoader.executor = executor;
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

    public KAvatarLoader bindImageViewByEmail(final ImageView image_view, final String email,
                                              final BindListener bind_listener) {

        loadAvatar(image_view, bind_listener, TaskParm.TASK_PARM_STYLE.TASK_PARM_USE_EMAIL, email);

        return this;
    }

    public KAvatarLoader bindImageViewByHashCode(final ImageView image_view, final String hash_code,
                                                 final BindListener bind_listener) {

        loadAvatar(image_view, bind_listener, TaskParm.TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE, hash_code);

        return this;
    }

    public KAvatarLoader bindImageViewByUrl(final ImageView image_view, final String url,
                                            final BindListener bind_listener) {

        loadAvatar(image_view, bind_listener, TaskParm.TASK_PARM_STYLE.TASK_PARM_USE_URL, url);

        return this;
    }

    public KAvatarLoader bindActionBarByEmail(final ActionBar action_bar, final String email,
                                              final BindListener bind_listener) {

        loadAvatar(action_bar, bind_listener, TaskParm.TASK_PARM_STYLE.TASK_PARM_USE_EMAIL, email);

        return this;
    }

    public KAvatarLoader bindActionBarByHashCode(final ActionBar action_bar, final String hash_code,
                                                 final BindListener bind_listener) {

        loadAvatar(action_bar, bind_listener, TaskParm.TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE, hash_code);

        return this;
    }

    public KAvatarLoader bindActionBarByUrl(final ActionBar action_bar, final String url,
                                            final BindListener bind_listener) {

        loadAvatar(action_bar, bind_listener, TaskParm.TASK_PARM_STYLE.TASK_PARM_USE_URL, url);

        return this;
    }

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
                setTargetView(target_view).setBindListner(bind_listener);

        new AvatarLoadTask(context, task_parm).executeOnExecutor(executor);
    }

    public KAvatarLoader setDefaultAvatar(DefaultAvatar default_avatar) {
        this.default_avatar = default_avatar;
        return this;
    }

    public KAvatarLoader setDefaultAvatar(int default_avatar_resource) {
        DefaultAvatar default_avatar = DefaultAvatar.CUSTOM_DEFAULT_AVATAR;
        default_avatar.setCustomDefaultAvatar(context.getResources().getDrawable(default_avatar_resource));
        setDefaultAvatar(default_avatar);
        return this;
    }

    public KAvatarLoader setAvatarRating(AvatarRating avatar_rating) {
        this.avatar_rating = avatar_rating;
        return this;
    }

}


