package com.kohoh.kavatarloader;

import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.ImageView;

import com.kohoh.gravatar.GravatarDefaultImage.GravatarProvideImage;
import com.kohoh.gravatar.GravatarRating;

import java.io.File;

/**
 * Created by kohoh on 14-8-12.
 */
abstract public class TaskParm {
    public final static String TAG = TaskParm.class.getSimpleName() + "_tag";
    private BindListener bind_listner;
    private Integer avatar_size = 80;
    private DefaultAvatar default_avatar = DefaultAvatar.valueOf(GravatarProvideImage.HTTP_404.toString());
    private AvatarRating avatar_rating = AvatarRating.valueOf(GravatarRating.GENERAL_AUDIENCES.toString());
    private Object target_view;
    private boolean if_use_cached_avatar = true;
    private boolean if_use_saved_avatar = true;
    private File custom_saved_avatars_dir = null;

    public static TARGET_VIEW_STYLE getTargetViewSytle(Object target_view) {
        boolean isActionBar = ActionBar.class.isInstance(target_view);
        boolean isImageView = ImageView.class.isInstance(target_view);

        TARGET_VIEW_STYLE target_view_style = null;

        if (isActionBar && !isImageView) {
            target_view_style = TARGET_VIEW_STYLE.ACTION_BAR;
        }

        if (!isActionBar && isImageView) {
            target_view_style = TARGET_VIEW_STYLE.IMAGE_VIEW;
        }

        if (target_view_style == null) {
            Log.e(TAG, "getTargetViewStyle error");
        }

        return target_view_style;
    }

    public static TASK_PARM_STYLE getTaskParmStyle(TaskParm task_parm) {
        boolean isTaskParmUseUrl = TaskParmUseUrl.class.isInstance(task_parm);
        boolean isTaskParmUseEmail = TaskParmUseEmail.class.isInstance(task_parm);
        boolean isTaskParmUseHashCode = TaskParmUseHashCode.class.isInstance(task_parm);

        TASK_PARM_STYLE task_parm_style = null;

        if (isTaskParmUseUrl && !isTaskParmUseEmail && !isTaskParmUseHashCode) {
            task_parm_style = TASK_PARM_STYLE.TASK_PARM_USE_URL;
        }

        if (!isTaskParmUseUrl && isTaskParmUseEmail && !isTaskParmUseHashCode) {
            task_parm_style = TASK_PARM_STYLE.TASK_PARM_USE_EMAIL;
        }

        if (!isTaskParmUseUrl && !isTaskParmUseEmail && isTaskParmUseHashCode) {
            task_parm_style = TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE;
        }

        if (task_parm_style == null) {
            Log.e(TAG, "getTaskParmStyle error");
        }

        return task_parm_style;
    }

    public void log() {
        Log.d(TAG, "-------TaskParmInfo-------");
        Log.d(TAG, "size = " + avatar_size);
        Log.d(TAG, "rating = " + avatar_rating);
        Log.d(TAG, "default avatar = " + default_avatar);
        Log.d(TAG, "bind_listener " + (bind_listner == null ? "is null" : "not null"));
        Log.d(TAG, "target_view = " + getTargetViewSytle(target_view).toString());
        Log.d(TAG, "custom saved avatars dir = " + (custom_saved_avatars_dir == null ?
                " is null" : custom_saved_avatars_dir.getPath()));

        switch (getTaskParmStyle(this)) {
            case TASK_PARM_USE_EMAIL:
                Log.d(TAG, "email = " + ((TaskParmUseEmail) this).getEmail());
                break;
            case TASK_PARM_USE_HASH_CODE:
                Log.d(TAG, "hash_code = " + ((TaskParmUseHashCode) this).getHashCode());
                break;
            case TASK_PARM_USE_URL:
                Log.d(TAG, "url = " + ((TaskParmUseUrl) this).getUrl());
                break;
        }
        Log.d(TAG, "---------END---------");
    }

    public Object getTargetView() {
        return target_view;
    }

    public TaskParm setTargetView(Object target_view) {
        this.target_view = target_view;
        return this;
    }

    public BindListener getBindListner() {
        return bind_listner;
    }

    public TaskParm setBindListner(BindListener bind_listner) {
        this.bind_listner = bind_listner;
        return this;
    }

    public int getAvatarSize() {
        return avatar_size;
    }

    public TaskParm setAvatarSize(int avatar_size) {
        this.avatar_size = avatar_size;
        return this;
    }

    public DefaultAvatar getDefaultAvatar() {
        return default_avatar;
    }

    public TaskParm setDefaultAvatar(DefaultAvatar default_avatar) {
        this.default_avatar = default_avatar;
        return this;
    }

    public AvatarRating getAvatarRating() {
        return avatar_rating;
    }

    public TaskParm setAvatarRating(AvatarRating avatar_rating) {
        this.avatar_rating = avatar_rating;
        return this;
    }

    public boolean isUseCachedAvatar() {
        return if_use_cached_avatar;
    }

    public TaskParm setUseCachedAvatar(boolean ifUseCachedAvatar) {
        this.if_use_cached_avatar = ifUseCachedAvatar;
        return this;
    }

    public boolean isUseSavedAvatar() {
        return if_use_saved_avatar;
    }

    public TaskParm setUseSavedAvatar(boolean ifUseSavedAvatar) {
        this.if_use_saved_avatar = ifUseSavedAvatar;
        return this;
    }

    public File getCustomSavedAvatarsDir() {
        return custom_saved_avatars_dir;
    }

    public void setCustomSavedAvatarsDir(File custom_saved_avatars_dir) {
        this.custom_saved_avatars_dir = custom_saved_avatars_dir;
    }

    public enum TARGET_VIEW_STYLE {ACTION_BAR, IMAGE_VIEW;}

    public enum TASK_PARM_STYLE {TASK_PARM_USE_URL, TASK_PARM_USE_EMAIL, TASK_PARM_USE_HASH_CODE;}
}
