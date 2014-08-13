package com.kohoh.kavatarloader;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by kohoh on 14-7-28.
 */
public class KAvatarLoader {
    private Context context;
    private DefaultAvatar default_avatar;
    private AvatarRating avatar_rating;
    static private final String TAG = KAvatarLoader.class.getSimpleName();

    public KAvatarLoader(Context context) {
        this.context = context;
        this.default_avatar = DefaultAvatar.HTTP_404;
        this.avatar_rating = AvatarRating.GENERAL_AUDIENCES;
    }

    public int calculateAvatarSize(ImageView imageView) {
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

        final int avatar_size = calculateAvatarSize(image_view);

        TaskParmUseEmail task_parm = new TaskParmUseEmail();
        task_parm.setEmail(email);
        task_parm.setTargetView(image_view);
        task_parm.setBindListner(bind_listener);
        task_parm.setAvatarSize(avatar_size);
        task_parm.setAvatarRating(avatar_rating);
        task_parm.setDefaultAvatar(default_avatar);
        new AvatarLoadTask(context, task_parm).execute();

        return this;
    }

    public KAvatarLoader bindImageViewByHashCode(final ImageView image_view, final String hash_code,
                                                 final BindListener bind_listener) {

        final int avatar_size = calculateAvatarSize(image_view);

        TaskParmUseHashCode task_parm = new TaskParmUseHashCode();
        task_parm.setHashCode(hash_code);
        task_parm.setTargetView(image_view);
        task_parm.setBindListner(bind_listener);
        task_parm.setAvatarSize(avatar_size);
        task_parm.setAvatarRating(avatar_rating);
        task_parm.setDefaultAvatar(default_avatar);
        new AvatarLoadTask(context, task_parm).execute();

        return this;
    }

    public KAvatarLoader bindImageViewByUrl(final ImageView image_view, final String url,
                                            final BindListener bind_listener) {
        final int avatar_size = calculateAvatarSize(image_view);

        TaskParmUseUrl task_parm = new TaskParmUseUrl();
        task_parm.setUrl(url);
        task_parm.setTargetView(image_view);
        task_parm.setBindListner(bind_listener);
        task_parm.setAvatarSize(avatar_size);
        task_parm.setAvatarRating(avatar_rating);
        task_parm.setDefaultAvatar(default_avatar);
        new AvatarLoadTask(context, task_parm).execute();

        return this;
    }

    public KAvatarLoader bindActionBarByEmail(final ActionBar action_bar, final String email,
                                              final BindListener bind_listener) {
        //default_avatar_size是翻看ActionBar的源码得到的logo的大小
        final int avatar_size = context.getResources().getDimensionPixelSize(R.dimen.default_avatar_size);

        TaskParmUseEmail task_parm = new TaskParmUseEmail();
        task_parm.setEmail(email);
        task_parm.setTargetView(action_bar);
        task_parm.setBindListner(bind_listener);
        task_parm.setAvatarSize(avatar_size);
        task_parm.setAvatarRating(avatar_rating);
        task_parm.setDefaultAvatar(default_avatar);
        new AvatarLoadTask(context, task_parm).execute();

        return this;
    }

    public KAvatarLoader bindActionBarByHashCode(final ActionBar action_bar, final String hash_code,
                                                 final BindListener bind_listener) {
        //default_avatar_size是翻看ActionBar的源码得到的logo的大小
        final int avatar_size = context.getResources().getDimensionPixelSize(R.dimen.default_avatar_size);

        TaskParmUseHashCode task_parm = new TaskParmUseHashCode();
        task_parm.setHashCode(hash_code);
        task_parm.setTargetView(action_bar);
        task_parm.setBindListner(bind_listener);
        task_parm.setAvatarSize(avatar_size);
        task_parm.setAvatarRating(avatar_rating);
        task_parm.setDefaultAvatar(default_avatar);
        new AvatarLoadTask(context, task_parm).execute();

        return this;
    }

    public KAvatarLoader bindActionBarByUrl(final ActionBar action_bar, final String url,
                                            final BindListener bind_listener) {
        //default_avatar_size是翻看ActionBar的源码得到的logo的大小
        final int avatar_size = context.getResources().getDimensionPixelSize(R.dimen.default_avatar_size);

        TaskParmUseUrl task_parm = new TaskParmUseUrl();
        task_parm.setUrl(url);
        task_parm.setTargetView(action_bar);
        task_parm.setBindListner(bind_listener);
        task_parm.setAvatarSize(avatar_size);
        task_parm.setAvatarRating(avatar_rating);
        task_parm.setDefaultAvatar(default_avatar);
        new AvatarLoadTask(context, task_parm).execute();

        return this;
    }

    public KAvatarLoader setDefaultAvatar(DefaultAvatar default_avatar, Integer default_avatar_resource) {
        if (default_avatar.equals(DefaultAvatar.CUSTOM_DEFAULT_AVATAR)) {
            Drawable custom_default_avatar = context.getResources().getDrawable(default_avatar_resource);
            default_avatar.setCustomDefaultAvatar(custom_default_avatar);
        }
        this.default_avatar = default_avatar;
        return this;
    }

    public KAvatarLoader setAvatarRating(AvatarRating avatar_rating) {
        this.avatar_rating = avatar_rating;
        return this;
    }

}


