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
    final private byte[] raw_avatar;

    public Avatar(byte[] raw_avatar,String TAG) {
        this.raw_avatar = raw_avatar;
        this.TAG = TAG;
    }

    public Drawable getDrawable() {
        return new BitmapDrawable(null, getBitmap());
    }

    public Bitmap getBitmap() {
        return BitmapFactory.decodeByteArray(raw_avatar, 0, raw_avatar.length);
    }

    public byte[] getBytes() {
        return raw_avatar;
    }

    public String getTag() {
        return TAG;
    }
}
