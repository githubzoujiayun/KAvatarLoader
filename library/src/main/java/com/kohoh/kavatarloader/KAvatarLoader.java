package com.kohoh.kavatarloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import jgravatar.Gravatar;
import jgravatar.GravatarDefaultImage;
import jgravatar.GravatarRating;

/**
 * Created by kohoh on 14-7-28.
 */
public class KAvatarLoader {
    private Context context;
    static private final String TAG = KAvatarLoader.class.getSimpleName();

    public KAvatarLoader(Context context) {
        this.context = context;
    }

    public boolean bind(ImageView imageView, String email) {
        File avatar1 = new File(context.getFilesDir().getPath() + "/avatar1.jpg");
        if (avatar1.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(avatar1);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                imageView.setTag(email);

            } catch (FileNotFoundException e) {
                Log.e(TAG, "bind failed");
                e.printStackTrace();
            }
            return true;

        }
        Log.e(TAG, "bind failed");
        return false;
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
