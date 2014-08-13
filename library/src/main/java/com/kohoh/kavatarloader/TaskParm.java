package com.kohoh.kavatarloader;

import android.app.ActionBar;
import android.util.Log;
import android.widget.ImageView;

import com.kohoh.gravatar.Gravatar;

/**
 * Created by kohoh on 14-8-12.
 */
abstract public class TaskParm {
    private BindListener bind_listner;
    private Integer avatar_size = Gravatar.DEFAULT_SIZE;
    private DefaultAvatar default_avatar = DefaultAvatar.valueOf(Gravatar.DEFAULT_DEFAULT_IMAGE.toString());
    private AvatarRating avatar_rating = AvatarRating.valueOf(Gravatar.DEFAULT_RATING.toString());
    private Object target_view;

    public final static String TAG = TaskParm.class.getSimpleName() + "_tag";

    public void log() {
        Log.d(TAG, "-------TaskParmInfo-------");
        Log.d(TAG, "size = " + avatar_size);
        Log.d(TAG, "rating = " + avatar_rating);
        Log.d(TAG, "default avatar = " + default_avatar);
        Log.d(TAG, "bind_listener " + (bind_listner == null ? "is null" : "not null"));
        try {
            Log.d(TAG, "target_view = " + getTargetViewSytle(target_view).toString());

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "---------END---------");
    }

    public enum TARGET_VIEW_STYLE {ACTION_BAR, IMAGE_VIEW;}

    public static TARGET_VIEW_STYLE getTargetViewSytle(Object target_view) throws Exception {
        boolean isActionBar = ActionBar.class.isInstance(target_view);
        boolean isImageView = ImageView.class.isInstance(target_view);

        if (isActionBar && !isImageView) {
            return TARGET_VIEW_STYLE.ACTION_BAR;
        }

        if (!isActionBar && isImageView) {
            return TARGET_VIEW_STYLE.IMAGE_VIEW;
        }

        Log.e(TAG, "getTargetViewStyle error");
        throw new Exception("target view 必须是ImageView或者ActionBar");
    }

    public enum TASK_PARM_STYLE {TASK_PARM_USE_URL, TASK_PARM_USE_EMAIL, TASK_PARM_USE_HASH_CODE;}

    public static TASK_PARM_STYLE getTaskParmStyle(TaskParm task_parm) throws Exception {
        boolean isTaskParmUseUrl = TaskParmUseUrl.class.isInstance(task_parm);
        boolean isTaskParmUseEmail = TaskParmUseEmail.class.isInstance(task_parm);
        boolean isTaskParmUseHashCode = TaskParmUseHashCode.class.isInstance(task_parm);

        if (isTaskParmUseUrl && !isTaskParmUseEmail && !isTaskParmUseHashCode) {
            return TASK_PARM_STYLE.TASK_PARM_USE_URL;
        }

        if (!isTaskParmUseUrl && isTaskParmUseEmail && !isTaskParmUseHashCode) {
            return TASK_PARM_STYLE.TASK_PARM_USE_EMAIL;
        }

        if (!isTaskParmUseUrl && !isTaskParmUseEmail && isTaskParmUseHashCode) {
            return TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE;
        }

        Log.e(TAG, "getTaskParmStyle error");
        throw new Exception("task_parm必须是TaskParmUseUrl，TaskParmUseEail，TaskParmUseHashCode中的一个");
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
