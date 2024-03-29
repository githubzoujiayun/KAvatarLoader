package com.kohoh.kavatarloader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by kohoh on 14-7-29.
 */
public class Avatar {
    final private String tag;
    final private byte[] raw_avatar;

    public Avatar(byte[] raw_avatar, String tag) {
        this.raw_avatar = raw_avatar;
        this.tag = tag;
    }

    public Drawable getDrawable(Resources resources) {
        if (raw_avatar == null) {
            return null;
        }

        return new BitmapDrawable(resources, getBitmap());
    }

    public Bitmap getBitmap() {
        if (raw_avatar == null) {
            return null;
        }

        return BitmapFactory.decodeByteArray(raw_avatar, 0, raw_avatar.length);
    }

    public byte[] getBytes() {
        return raw_avatar;
    }

    public String getTag() {
        return tag;
    }
}
