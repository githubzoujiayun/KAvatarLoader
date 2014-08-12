package com.kohoh.kavatarloader.test;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.kohoh.KAvatarLoader.test.R;

/**
 * Created by kohoh on 14-8-12.
 */
public class Resources {
    private Context context;
    private android.content.res.Resources resources;


    public Resources(Context context) {
        this.context = context;
        this.resources = context.getResources();
    }

    public Drawable getCunstomDefaultAvatar() {
        return resources.getDrawable(R.drawable.custom_default_avatar);
    }
}
