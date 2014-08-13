package com.kohoh.kavatarloader;

import android.graphics.drawable.Drawable;

/**
 * Created by kohoh on 14-8-11.
 */
public enum DefaultAvatar {
    CUSTOM_DEFAULT_AVATAR,

    GRAVATAR_ICON,

    IDENTICON,

    MONSTERID,

    WAVATAR,

    HTTP_404,

    MYSTERY_MEN,

    RETRO,

    BLANK;

    private Drawable custom_default_avatar = null;

    public Drawable getCustomDefaultAvatar() {
        return custom_default_avatar;
    }

    public void setCustomDefaultAvatar(Drawable custom_default_avatar) {
        this.custom_default_avatar = custom_default_avatar;
    }

}
