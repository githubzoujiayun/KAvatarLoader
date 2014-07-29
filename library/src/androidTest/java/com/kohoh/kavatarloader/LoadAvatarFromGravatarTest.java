package com.kohoh.kavatarloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.test.AndroidTestCase;

/**
 * Created by kohoh on 14-7-29.
 */
public class LoadAvatarFromGravatarTest extends AndroidTestCase {

    private Context context;
    private KAvatarLoader avatarLoader;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = getContext();
        avatarLoader = new KAvatarLoader(context);
    }

    public void testLoadAvatarFromGravatar() {
        String email = "qxw2012@hotamil.com";
        Avatar avatar = avatarLoader.loadAvatar(email);

        assertNotNull("avatar is null", avatar);
        assertNotNull("avatar's drawable is null", avatar.getDrawable());
        assertEquals("avatar drawable tag not right", email, avatar.getTag());
    }
}
