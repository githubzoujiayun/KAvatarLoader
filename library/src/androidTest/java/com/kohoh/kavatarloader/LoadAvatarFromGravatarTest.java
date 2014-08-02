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
    final private String EMAIL1 = "kavatarloader1@126.com";
    final private String EMAIL2 = "kavatarloader2@126.com";
    final private int EMAIL1_SIZE100_LENGTH = 4348;
    final private int EMAIL1_SIZE200_LENGTH = 11340;
    final private int EMAIL2_SIZE100_LENGTH = 3140;
    final private int EMAIL2_SIZE200_LENGTH = 7702;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = getContext();
        avatarLoader = new KAvatarLoader(context);
    }

    public void testLoadAvatarFromGravatar() {
        String email = "qxw2012@hotamil.com";
        Avatar avatar = avatarLoader.loadAvatar(email,100);

        assertNotNull("avatar is null", avatar);
        assertNotNull("avatar's drawable is null", avatar.getDrawable());
        assertEquals("avatar drawable tag not right", email, avatar.getTag());
    }

    public void testLoadSuitableSizeAvatar() {
        Avatar avatar_email1_size100 = avatarLoader.loadAvatar(EMAIL1, 100);
        Avatar avatar_email1_size200 = avatarLoader.loadAvatar(EMAIL1, 200);
        Avatar avatar_email2_size100 = avatarLoader.loadAvatar(EMAIL2, 100);
        Avatar avatar_email2_size200 = avatarLoader.loadAvatar(EMAIL2, 200);

        assertEquals(EMAIL1_SIZE100_LENGTH, avatar_email1_size100.getBytes().length);
        assertEquals(EMAIL1_SIZE200_LENGTH, avatar_email1_size200.getBytes().length);
        assertEquals(EMAIL2_SIZE100_LENGTH, avatar_email2_size100.getBytes().length);
        assertEquals(EMAIL2_SIZE200_LENGTH, avatar_email2_size200.getBytes().length);
    }
}
