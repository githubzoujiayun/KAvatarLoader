package com.kohoh.kavatarloader;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.kohoh.gravatar.Gravatar;
import com.kohoh.gravatar.GravatarDefaultImage;
import com.kohoh.gravatar.GravatarRating;

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

        class Task extends AsyncTask<String, Integer, Avatar> {

            @Override
            protected Avatar doInBackground(String... params) {
                Log.d(TAG, "start loading avatar");
                return loadAvatar(params[0]);
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
        };

        new Task().execute(email);
    }

    public Avatar loadAvatar(String email) {
        Gravatar gravatar = new Gravatar();
        gravatar.setSize(50);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        byte[] jpg = gravatar.download(email);
        return new Avatar(jpg, email);
    }
}


