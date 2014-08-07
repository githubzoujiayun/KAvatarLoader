package com.kohoh.kavatarloader;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.kohoh.gravatar.Gravatar;

import java.util.Objects;

/**
 * Created by kohoh on 14-7-28.
 */
public class KAvatarLoader {
    private Context context;
    private Gravatar gravatar;
    static private final String TAG = KAvatarLoader.class.getSimpleName();

    static public final int RESULT_CODE_SUCCESS = 1;
    static public final int RESULT_CODE_FAIL = 1;

    public KAvatarLoader(Context context) {
        this.context = context;
        this.gravatar = new Gravatar();
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

    public KAvatarLoader bindImageViewByEmail(final ImageView image_view, final String email, final BindListener bind_listener) {

        final int avatar_size = calculateAvatarSize(image_view);

        class Task extends AsyncTask<Objects, Objects, Avatar> {

            @Override
            protected Avatar doInBackground(Objects... params) {
                Log.d(TAG, "start loading avatar");
                return loadAvatarByEmail(email, avatar_size);
            }

            @Override
            protected void onPostExecute(Avatar avatar) {
                Log.d(TAG, "finish loading avatar");
                onBindImageViewFinished(image_view, avatar, bind_listener);
            }
        }

        new Task().execute();
        return this;
    }

    public KAvatarLoader bindImageViewByHashCode(final ImageView image_view, final String hash_code, final BindListener bind_listener) {

        final int avatar_size = calculateAvatarSize(image_view);

        class Task extends AsyncTask<Objects, Objects, Avatar> {

            @Override
            protected Avatar doInBackground(Objects... params) {
                Log.d(TAG, "start loading avatar");
                return loadAvatarByHashCode(hash_code, avatar_size);
            }

            @Override
            protected void onPostExecute(Avatar avatar) {
                Log.d(TAG, "finish loading avatar");
                onBindImageViewFinished(image_view, avatar, bind_listener);
            }
        }

        new Task().execute();
        return this;
    }

    public KAvatarLoader bindImageViewByUrl(final ImageView image_view, final String url, final BindListener bind_listener) {
        final int avatar_size = calculateAvatarSize(image_view);

        class Task extends AsyncTask<Objects, Objects, Avatar> {

            @Override
            protected Avatar doInBackground(Objects... params) {
                Log.d(TAG, "start loading avatar");
                return loadAvatarByUrl(url, avatar_size);
            }

            @Override
            protected void onPostExecute(Avatar avatar) {
                Log.d(TAG, "finish loading avatar");
                onBindImageViewFinished(image_view, avatar, bind_listener);
            }
        }

        new Task().execute();
        return this;
    }

    public KAvatarLoader bindActionBarByEmail(final ActionBar action_bar, final String email, final BindListener bind_listener) {
        //default_avatar_size是翻看ActionBar的源码得到的logo的大小
        final int avatar_size = context.getResources().getDimensionPixelSize(R.dimen.default_avatar_size);

        class Task extends AsyncTask<Objects, Objects, Avatar> {

            @Override
            protected Avatar doInBackground(Objects... params) {
                Log.d(TAG, "start loading avatar");
                return loadAvatarByEmail(email, avatar_size);
            }

            @Override
            protected void onPostExecute(Avatar avatar) {
                Log.d(TAG, "finish loading avatar");
                onBindActionBarFinished(action_bar, avatar, bind_listener);

            }
        }

        new Task().execute();
        return this;
    }

    public KAvatarLoader bindActionBarByHashCode(final ActionBar action_bar, final String hash_code, final BindListener bind_listener) {
        //default_avatar_size是翻看ActionBar的源码得到的logo的大小
        final int avatar_size = context.getResources().getDimensionPixelSize(R.dimen.default_avatar_size);

        class Task extends AsyncTask<Objects, Objects, Avatar> {

            @Override
            protected Avatar doInBackground(Objects... params) {
                Log.d(TAG, "start loading avatar");
                return loadAvatarByHashCode(hash_code, avatar_size);
            }

            @Override
            protected void onPostExecute(Avatar avatar) {
                Log.d(TAG, "finish loading avatar");
                onBindActionBarFinished(action_bar, avatar, bind_listener);

            }
        }

        new Task().execute();
        return this;
    }

    public KAvatarLoader bindActionBarByUrl(final ActionBar action_bar, final String url, final BindListener bind_listener) {
        //default_avatar_size是翻看ActionBar的源码得到的logo的大小
        final int avatar_size = context.getResources().getDimensionPixelSize(R.dimen.default_avatar_size);

        class Task extends AsyncTask<Objects, Objects, Avatar> {

            @Override
            protected Avatar doInBackground(Objects... params) {
                Log.d(TAG, "start loading avatar");
                return loadAvatarByUrl(url, avatar_size);
            }

            @Override
            protected void onPostExecute(Avatar avatar) {
                Log.d(TAG, "finish loading avatar");
                onBindActionBarFinished(action_bar, avatar, bind_listener);

            }
        }

        new Task().execute();
        return this;
    }

    private void onBindImageViewFinished(ImageView imageView, Avatar avatar, BindListener bind_listener) {
        Drawable avatar_drawable = avatar.getDrawable();
        String avatar_tag = avatar.getTag();
        if (avatar_drawable != null && avatar_tag != null) {
            imageView.setImageDrawable(avatar_drawable);
            imageView.setTag(avatar_tag);
        }

        if (bind_listener != null) {
            int result_code = (avatar_drawable != null && avatar_tag != null) ? RESULT_CODE_SUCCESS : RESULT_CODE_FAIL;
            bind_listener.onBindFinished(result_code);
        }
    }

    private void onBindActionBarFinished(ActionBar action_bar, Avatar avatar, BindListener bind_listener) {
        Drawable avatar_drawable = avatar.getDrawable();
        if (avatar_drawable != null) {
            action_bar.setDisplayUseLogoEnabled(true);
            action_bar.setLogo(avatar_drawable);
        }

        if (bind_listener != null) {
            int result_code = (avatar_drawable != null) ? RESULT_CODE_SUCCESS : RESULT_CODE_FAIL;
            bind_listener.onBindFinished(result_code);
        }
    }

    public Avatar loadAvatarByHashCode(String hash_code, int avatar_size) {
        gravatar.setSize(avatar_size);
        byte[] raw_gravatar = gravatar.downloadByHashCode(hash_code);
        String tag = gravatar.getUrlByHashCode(hash_code);
        return new Avatar(context,raw_gravatar, tag);
    }

    public Avatar loadAvatarByEmail(String email, int avatar_size) {
        gravatar.setSize(avatar_size);
        byte[] raw_gravatar = gravatar.downloadByEmail(email);
        String tag = gravatar.getUrlByEmail(email);
        return new Avatar(context,raw_gravatar, tag);
    }

    //这里的avatar_size本质上没有任何作用
    public Avatar loadAvatarByUrl(String url, int avatar_size) {
        gravatar.setSize(avatar_size);
        byte[] raw_gravatar = gravatar.dowloadByUrl(url);
        String tag = url;
        return new Avatar(context,raw_gravatar, tag);
    }

}


