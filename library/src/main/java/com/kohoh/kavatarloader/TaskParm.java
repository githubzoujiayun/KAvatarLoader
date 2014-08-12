package com.kohoh.kavatarloader;

import android.util.Log;

import com.kohoh.gravatar.Gravatar;
import com.kohoh.gravatar.GravatarDefaultImage;
import com.kohoh.gravatar.GravatarRating;

/**
 * Created by kohoh on 14-8-12.
 */
public class TaskParm {
    private BindListener bind_listner;
    private int avatar_size = Gravatar.DEFAULT_SIZE;
    private DefaultAvatar default_avatar = DefaultAvatar.valueOf(Gravatar.DEFAULT_DEFAULT_IMAGE.toString());
    private AvatarRating avatar_rating = AvatarRating.valueOf(Gravatar.DEFAULT_RATING.toString());
    private Object target_view;

    public final static String TAG = TaskParm.class.getSimpleName()+"_tag";

    public void log() {
        Log.d(TAG, "--------------------------------");
        Log.d(TAG, "size= " + avatar_size);
        Log.d(TAG, "rating= " + avatar_rating);
        Log.d(TAG, "default avatar= " + default_avatar);
    }


    public Object getTargetView() {
        return target_view;
    }

    public void setTargetView(Object target_view) {
        this.target_view = target_view;
    }

    public BindListener getBindListner() {
        return bind_listner;
    }

    public void setBindListner(BindListener bind_listner) {
        this.bind_listner = bind_listner;
    }

    public int getAvatarSize() {
        return avatar_size;
    }

    public void setAvatarSize(int avatar_size) {
        this.avatar_size = avatar_size;
    }

    public DefaultAvatar getDefaultAvatar() {
        return default_avatar;
    }

    public void setDefaultAvatar(DefaultAvatar default_avatar) {
        this.default_avatar = default_avatar;
    }

    public AvatarRating getAvatarRating() {
        return avatar_rating;
    }

    public void setAvatarRating(AvatarRating avatar_rating) {
        this.avatar_rating = avatar_rating;
    }
}
