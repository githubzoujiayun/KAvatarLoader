package com.kohoh.kavatarloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by kohoh on 14-7-29.
 */
public class Avatar {
    final private String TAG;
    final private Bitmap bitmap;

    public Avatar(byte[] raw_avatar,String TAG) {
        bitmap = BitmapFactory.decodeByteArray(raw_avatar, 0, raw_avatar.length);
        this.TAG = TAG;
    }

    public Drawable getDrawable() {
        return new BitmapDrawable(null, bitmap);
    }

    public String getTag() {
        return TAG;
    }
}
