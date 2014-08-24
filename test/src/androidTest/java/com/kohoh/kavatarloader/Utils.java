package com.kohoh.kavatarloader;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kohoh on 14-8-24.
 */
public class Utils {

    static BindCondition getBindCondition() {
        return new BindCondition();
    }

    static class BindCondition implements com.robotium.solo.Condition {

        private boolean isBindFinished = false;

        public void setBindFinished(boolean isBindFinished) {
            this.isBindFinished = isBindFinished;
        }

        @Override
        public boolean isSatisfied() {
            return isBindFinished;
        }
    }

    static public TaskParm getMockTaskParm(TaskParm.TASK_PARM_STYLE task_parm_style, String address,
                                           DefaultAvatar default_avatar, AvatarRating avatar_rating,
                                           Integer size, Boolean use_cached_avatar,
                                           Boolean use_saved_avatar, Object target_view,
                                           BindListener bind_listener) {
        TaskParm parm = new TaskParmUseEmail();
        if (task_parm_style != null && address != null) {
            switch (task_parm_style) {
                case TASK_PARM_USE_URL:
                    parm = new TaskParmUseUrl();
                    ((TaskParmUseUrl) parm).setUrl(address);
                    break;
                case TASK_PARM_USE_EMAIL:
                    parm = new TaskParmUseEmail();
                    ((TaskParmUseEmail) parm).setEmail(address);
                    break;
                case TASK_PARM_USE_HASH_CODE:
                    parm = new TaskParmUseHashCode();
                    ((TaskParmUseHashCode) parm).setHashCode(address);
                    break;
            }
        }

        if (default_avatar != null) {
            parm.setDefaultAvatar(default_avatar);
        }

        if (avatar_rating != null) {
            parm.setAvatarRating(avatar_rating);

        }

        if (size != null) {
            parm.setAvatarSize(size);
        }

        if (use_cached_avatar != null) {
            parm.setUseCachedAvatar(use_cached_avatar);
        }

        if (use_saved_avatar != null) {
            parm.setUseSavedAvatar(use_saved_avatar);
        }

        if (target_view != null) {
            parm.setTargetView(target_view);
        }

        if (bind_listener != null) {
            parm.setBindListner(bind_listener);
        }

        return parm;
    }

    static public TaskParm getMockTaskParm(TaskParm.TASK_PARM_STYLE task_parm_style, String address) {
        return getMockTaskParm(task_parm_style, address, null, null, null, null, null, null, null);
    }

    static public TaskParm getMockTaskParm(TaskParm.TASK_PARM_STYLE task_parm_style, String address,
                                           DefaultAvatar defaultAvatar) {
        return getMockTaskParm(task_parm_style, address, defaultAvatar, null, null, null, null, null, null);
    }

    static public TaskParm getMockTaskParm(Object target_view, BindListener bind_listener) {
        return getMockTaskParm(null, null, null, null, null, null, null, target_view, bind_listener);
    }

    static public AvatarLoadTask getMockAvatarLoadTask(Context context) {
        return new AvatarLoadTask(context, null);
    }

    static Avatar getMockAvatarSize100Email1(Context context) {
        Avatar avatar = null;
        try {
            InputStream input_stream = context.getResources().
                    getAssets().open("kavatarloader1@126.com_size100_bytes");
            byte[] raw_bytes = new byte[input_stream.available()];
            input_stream.read(raw_bytes);
            input_stream.close();
            avatar = new Avatar(raw_bytes, Constant.EXIST_EMAIL1_D_404_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return avatar;
    }
}


