package com.kohoh.kavatarloader;

import android.content.Context;
import android.test.AndroidTestCase;

import java.io.IOException;

/**
 * Created by kohoh on 14-7-29.
 */
public class KAvatarLoaderTest extends AndroidTestCase {

    private Context context;
    private KAvatarLoader avatarLoader;
    final private String EMAIL1 = GravatarConstant.EXIST_EMAIL1;
    final private String EMAIL2 = GravatarConstant.EXIST_EMAIL2;
    final private int EMAIL1_SIZE100_LENGTH = GravatarConstant.EMAIL1_SIZE100_LENGTH;
    final private int EMAIL1_SIZE200_LENGTH = GravatarConstant.EMAIL1_SIZE200_LENGTH;
    final private int EMAIL2_SIZE100_LENGTH = GravatarConstant.EMAIL2_SIZE100_LENGTH;
    final private int EMAIL2_SIZE200_LENGTH = GravatarConstant.EMAIL2_SIZE200_LENGTH;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = getContext();
        avatarLoader = new KAvatarLoader(context);
    }

    public void testLoadAvatar() throws IOException {
        Avatar avatar_email1_size100 = avatarLoader.loadAvatar(EMAIL1, 100);
        Avatar avatar_email1_size200 = avatarLoader.loadAvatar(EMAIL1, 200);
        Avatar avatar_email2_size100 = avatarLoader.loadAvatar(EMAIL2, 100);
        Avatar avatar_email2_size200 = avatarLoader.loadAvatar(EMAIL2, 200);

        assertNotNull("avatar is null", avatar_email1_size100);
        assertNotNull("avatar is null", avatar_email1_size200);
        assertNotNull("avatar is null", avatar_email2_size100);
        assertNotNull("avatar is null", avatar_email2_size200);

        assertNotNull("avatar's drawable is null", avatar_email1_size100.getDrawable());
        assertNotNull("avatar's drawable is null", avatar_email1_size200.getDrawable());
        assertNotNull("avatar's drawable is null", avatar_email2_size100.getDrawable());
        assertNotNull("avatar's drawable is null", avatar_email2_size200.getDrawable());

        assertNotNull("avatar's bitmap is null", avatar_email1_size100.getBitmap());
        assertNotNull("avatar's bitmap is null", avatar_email1_size200.getBitmap());
        assertNotNull("avatar's bitmap is null", avatar_email2_size100.getBitmap());
        assertNotNull("avatar's bitmap is null", avatar_email2_size200.getBitmap());

        assertNotNull("avatar's raw bytes is null", avatar_email1_size100.getBytes());
        assertNotNull("avatar's raw bytes is null", avatar_email1_size200.getBytes());
        assertNotNull("avatar's raw bytes is null", avatar_email2_size100.getBytes());
        assertNotNull("avatar's raw bytes is null", avatar_email2_size200.getBytes());

        assertEquals("avatar's tag not right", EMAIL1, avatar_email1_size100.getTag());
        assertEquals("avatar's tag not right", EMAIL1, avatar_email1_size200.getTag());
        assertEquals("avatar's tag not right", EMAIL2, avatar_email2_size100.getTag());
        assertEquals("avatar's tag not right", EMAIL2, avatar_email2_size200.getTag());
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
