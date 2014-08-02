package com.kohoh.kavatarloader;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.kohoh.gravatar.Gravatar;
import com.kohoh.gravatar.GravatarDefaultImage;
import com.kohoh.gravatar.GravatarRating;

import java.util.Objects;

/**
 * Created by kohoh on 14-7-28.
 */
public class KAvatarLoader {
    private Context context;
    static private final String TAG = KAvatarLoader.class.getSimpleName();

    public KAvatarLoader(Context context) {
        this.context = context;
    }

    public void bind(final ImageView imageView, final String email, final BindListener bindListener) {

        if (imageView == null) {
            return;
        }

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


        class Task extends AsyncTask<Objects, Objects, Avatar> {

            @Override
            protected Avatar doInBackground(Objects... params) {
                Log.d(TAG, "start loading avatar");
                return loadAvatar(email,avatar_size);
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

    public Avatar loadAvatar(String email,int avatar_size) {
        Gravatar gravatar = new Gravatar();
        gravatar.setSize(avatar_size);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        byte[] jpg = gravatar.download(email);
        return new Avatar(jpg, email);
    }
}


