package com.kohoh.kavatarloader;

import android.content.Context;
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

    public void bind(final ImageView imageView, final String email, final BindListener bindListener) {

        if (imageView == null) {
            return;
        }

        final int avatar_size = calculateAvatarSize(imageView);

        class Task extends AsyncTask<Objects, Objects, Avatar> {

            @Override
            protected Avatar doInBackground(Objects... params) {
                Log.d(TAG, "start loading avatar");
                return loadAvatarByEmail(email, avatar_size);
            }

            @Override
            protected void onPostExecute(Avatar avatar) {
                Log.d(TAG, "finish loading avatar");
                imageView.setImageDrawable(avatar.getDrawable());
                imageView.setTag(avatar.getTag());
                if (bindListener != null) {
                    bindListener.onBindFinished();
                }
            }
        }
        ;

        new Task().execute();
    }

    //TODO 待完成
    public KAvatarLoader bindImageViewByHashCode(final ImageView image_view, final String hash_code,final BindListener bind_listener) {

        //计算头像的尺寸
        final int height = image_view.getHeight();
        final int width = image_view.getWidth();
        final int avatar_size;
        if (height != width) {
            avatar_size = height < width ? height : width;
        } else if (height != 0) {
            avatar_size = height;
        } else {
            avatar_size = context.getResources().getDimensionPixelSize(R.dimen.default_avatar_size);
        }


        class Task extends AsyncTask<Objects, Objects, Avatar> {

            @Override
            protected Avatar doInBackground(Objects... params) {
                Log.d(TAG, "start loading avatar");
                return loadAvatarByHashCode(hash_code, avatar_size);
            }

            @Override
            protected void onPostExecute(Avatar avatar) {
                Log.d(TAG, "finish loading avatar");
                image_view.setImageDrawable(avatar.getDrawable());
                image_view.setTag(gravatar.getUrlByHashCode(hash_code));
                if (bind_listener != null) {
                    bind_listener.onBindFinished();
                }
            }
        }
        ;

        new Task().execute();

        return this;
    }

    public Avatar loadAvatarByHashCode(String hash_code, int avatar_size) {
        gravatar.setSize(avatar_size);
        byte[] raw_gravatar = gravatar.downloadByHashCode(hash_code);
        String tag = gravatar.getUrlByHashCode(hash_code);
        return new Avatar(raw_gravatar, tag);
    }

    public Avatar loadAvatarByEmail(String email, int avatar_size) {
        gravatar.setSize(avatar_size);
        byte[] raw_gravatar = gravatar.downloadByEmail(email);
        String tag = gravatar.getUrlByEmail(email);
        return new Avatar(raw_gravatar, tag);
    }

}


