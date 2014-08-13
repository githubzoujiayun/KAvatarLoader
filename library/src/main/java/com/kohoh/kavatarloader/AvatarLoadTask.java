package com.kohoh.kavatarloader;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.kohoh.gravatar.Gravatar;
import com.kohoh.gravatar.GravatarDefaultImage;
import com.kohoh.gravatar.GravatarRating;

import java.util.Objects;

import static com.kohoh.kavatarloader.TaskParm.getTargetViewSytle;
import static com.kohoh.kavatarloader.TaskParm.getTaskParmStyle;

/**
 * Created by kohoh on 14-8-12.
 */
public class AvatarLoadTask extends AsyncTask<Objects, Objects, Avatar> {

    public static final String TAG = AvatarLoadTask.class.getSimpleName() + "_tag";

    final private Context context;
    final private Gravatar gravatar;
    final private TaskParm task_parm;


    public AvatarLoadTask(Context context, TaskParm task_parm) {
        this.context = context;
        this.gravatar = new Gravatar();
        this.task_parm = task_parm;
    }

    void bindTargetViewWithDefaultAvatar(TaskParm task_parm) {
        Object target_view = task_parm.getTargetView();
        TaskParm.TARGET_VIEW_STYLE target_view_style = getTargetViewSytle(target_view);
        switch (target_view_style) {
            case ACTION_BAR:
                bindActionBarWithDefaultAvatar((ActionBar) target_view, task_parm.getDefaultAvatar());
                break;
            case IMAGE_VIEW:
                bindImageViewWithDefaultAvatar((ImageView) target_view, task_parm.getDefaultAvatar());
                break;
        }
    }

    private void bindImageViewWithDefaultAvatar(ImageView image_view, DefaultAvatar default_avatar) {
        Resources resources = context.getResources();
        String tag = "default avatar ";

        switch (default_avatar) {
            case GRAVATAR_ICON:
                image_view.setImageDrawable(resources.getDrawable(R.drawable.gravatar_icon));
                image_view.setTag(tag + GravatarDefaultImage.GRAVATAR_ICON.toString());
                break;
            case IDENTICON:
                image_view.setImageDrawable(resources.getDrawable(R.drawable.identicon));
                image_view.setTag(tag + GravatarDefaultImage.IDENTICON.toString());
                break;
            case MONSTERID:
                image_view.setImageDrawable(resources.getDrawable(R.drawable.monsterid));
                image_view.setTag(tag + GravatarDefaultImage.MONSTERID.toString());
                break;
            case WAVATAR:
                image_view.setImageDrawable(resources.getDrawable(R.drawable.wavatar));
                image_view.setTag(tag + GravatarDefaultImage.WAVATAR.toString());
                break;
            case MYSTERY_MEN:
                image_view.setImageDrawable(resources.getDrawable(R.drawable.mystery_men));
                image_view.setTag(tag + GravatarDefaultImage.MYSTERY_MEN.toString());
                break;
            case RETRO:
                image_view.setImageDrawable(resources.getDrawable(R.drawable.retro));
                image_view.setTag(tag + GravatarDefaultImage.RETRO.toString());
                break;
            case BLANK:
                image_view.setImageDrawable(resources.getDrawable(R.drawable.blank));
                image_view.setTag(tag + GravatarDefaultImage.BLANK.toString());
                break;
            case CUSTOM_DEFAULT_AVATAR:
                image_view.setImageDrawable(default_avatar.getCustomDefaultAvatar());
                image_view.setTag("custom default avatar");
                break;
        }
    }

    private void bindActionBarWithDefaultAvatar(ActionBar action_bar, DefaultAvatar default_avatar) {
        switch (default_avatar) {
            case GRAVATAR_ICON:
                action_bar.setLogo(R.drawable.gravatar_icon);
                break;
            case IDENTICON:
                action_bar.setLogo(R.drawable.identicon);
                break;
            case MONSTERID:
                action_bar.setLogo(R.drawable.monsterid);
                break;
            case WAVATAR:
                action_bar.setLogo(R.drawable.wavatar);
                break;
            case MYSTERY_MEN:
                action_bar.setLogo(R.drawable.mystery_men);
                break;
            case RETRO:
                action_bar.setLogo(R.drawable.retro);
                break;
            case BLANK:
                action_bar.setLogo(R.drawable.blank);
                break;
            case CUSTOM_DEFAULT_AVATAR:
                action_bar.setLogo(default_avatar.getCustomDefaultAvatar());
                break;
        }
    }

    void onBindTargetViewFinished(TaskParm task_parm, Avatar avatar) {
        Object target_view = task_parm.getTargetView();
        BindListener bind_listener = task_parm.getBindListner();

        //当网络出现问题或者加载失败时会出现这种状况
        if (avatar == null || avatar.getBytes() == null) {
            if (bind_listener != null) {
                bind_listener.onBindFinished(BindListener.RESULT_CODE.FAIL);
            }
            return;
        }

        Drawable avatar_drawable = avatar.getDrawable();
        String avatar_tag = avatar.getTag();

        try {
            TaskParm.TARGET_VIEW_STYLE target_view_stye = getTargetViewSytle(target_view);
            switch (target_view_stye) {
                case ACTION_BAR:
                    if (avatar_drawable != null) {
                        ActionBar action_bar = (ActionBar) target_view;
                        action_bar.setDisplayUseLogoEnabled(true);
                        action_bar.setLogo(avatar_drawable);
                    }
                    break;
                case IMAGE_VIEW:
                    if (avatar_drawable != null && avatar_tag != null) {
                        ImageView imageView = (ImageView) target_view;
                        imageView.setImageDrawable(avatar_drawable);
                        imageView.setTag(avatar_tag);
                    }
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onBindTargetViewFinished error", e);
        }

        if (bind_listener != null) {
            BindListener.RESULT_CODE result_code = (avatar_drawable != null && avatar_tag != null) ? BindListener.RESULT_CODE.SUCCESS : BindListener.RESULT_CODE.FAIL;
            bind_listener.onBindFinished(result_code);
        }
    }

    Avatar loadAvatar(TaskParm task_parm) {
        byte[] raw_gravatar = null;
        String tag = null;

        gravatar.setSize(task_parm.getAvatarSize());
        gravatar.setRating(GravatarRating.valueOf(task_parm.getAvatarRating().toString()));
        if (task_parm.getDefaultAvatar().equals(DefaultAvatar.CUSTOM_DEFAULT_AVATAR)) {
            gravatar.setDefaultImage(GravatarDefaultImage.HTTP_404);
        } else {
            gravatar.setDefaultImage(GravatarDefaultImage.valueOf(task_parm.getDefaultAvatar().toString()));
        }

        gravatar.log();

        TaskParm.TASK_PARM_STYLE style = getTaskParmStyle(task_parm);
        switch (style) {
            case TASK_PARM_USE_URL:
                String url = ((TaskParmUseUrl) task_parm).getUrl();
                raw_gravatar = gravatar.dowloadByUrl(url);
                tag = url;
                break;
            case TASK_PARM_USE_EMAIL:
                String email = ((TaskParmUseEmail) task_parm).getEmail();
                raw_gravatar = gravatar.downloadByEmail(email);
                tag = gravatar.getUrlByEmail(email);
                break;
            case TASK_PARM_USE_HASH_CODE:
                String hash_code = ((TaskParmUseHashCode) task_parm).getHashCode();
                raw_gravatar = gravatar.downloadByHashCode(hash_code);
                tag = gravatar.getUrlByHashCode(hash_code);
                break;
            default:
                Log.e(TAG, "style 必须是TASK_PARM_USE_URL，TASK_PARM_USE_EMAIL，TASK_PARM_USE_HASH_CODE之一");
        }

        return new Avatar(context,raw_gravatar,tag);
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "-----START LOAD AVATAR------");
        task_parm.log();
        bindTargetViewWithDefaultAvatar(task_parm);
    }

    @Override
    protected Avatar doInBackground(Objects... params) {
        Log.d(TAG, "------LOADING AVATAR------");
        try {
            return loadAvatar(task_parm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Avatar avatar) {
        Log.d(TAG, "avatar's raw_bytes " + (avatar.getBytes() == null ? "is null" : "not null"));
        Log.d(TAG, "avatar's tag " + (avatar.getTag() == null ? "is null" : "is " + avatar.getTag()));
        onBindTargetViewFinished(task_parm, avatar);
        Log.d(TAG, "------LOAD FINISH------");
    }
}

