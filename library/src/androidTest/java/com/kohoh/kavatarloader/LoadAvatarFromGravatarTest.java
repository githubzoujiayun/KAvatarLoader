package com.kohoh.kavatarloader;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.test.AndroidTestCase;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

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

    public void testLoadAvatarByGravatar() throws IOException {
        Avatar avatar_email1_size100 = avatarLoader.loadAvatar(EMAIL1,100);
        byte[] bytes_email1_size100_actural = avatar_email1_size100.getBytes();
        Avatar avatar_email1_size200 = avatarLoader.loadAvatar(EMAIL1,200);
        byte[] bytes_email1_size200_actural = avatar_email1_size200.getBytes();
        Avatar avatar_email2_size100 = avatarLoader.loadAvatar(EMAIL2,100);
        byte[] bytes_email2_size100_actural = avatar_email2_size100.getBytes();
        Avatar avatar_email2_size200 = avatarLoader.loadAvatar(EMAIL2,200);
        byte[] bytes_email2_size200_actural = avatar_email2_size200.getBytes();

        AssetManager asset_manager = getContext().getAssets();
        InputStream inputStream;
        BufferedInputStream bufferedInputStream;

        inputStream = asset_manager.open("kavatarloader1@126.com_size100_bytes");
        byte[] bytes_email1_size100_expect = new byte[inputStream.available()];
        bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.read(bytes_email1_size100_expect);

        inputStream = asset_manager.open("kavatarloader1@126.com_size200_bytes");
        byte[] bytes_email1_size200_expect = new byte[inputStream.available()];
        bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.read(bytes_email1_size200_expect);

        inputStream = asset_manager.open("kavatarloader2@126.com_size100_bytes");
        byte[] bytes_email2_size100_expect = new byte[inputStream.available()];
        bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.read(bytes_email2_size100_expect);

        inputStream = asset_manager.open("kavatarloader2@126.com_size200_bytes");
        byte[] bytes_email2_size200_expect = new byte[inputStream.available()];
        bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.read(bytes_email2_size200_expect);

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

        assertNotNull("avatar's raw bytes is null", bytes_email1_size100_actural);
        assertNotNull("avatar's raw bytes is null", bytes_email1_size200_actural);
        assertNotNull("avatar's raw bytes is null", bytes_email2_size100_actural);
        assertNotNull("avatar's raw bytes is null", bytes_email2_size200_actural);

        assertNotNull("actural raw bytes is null", bytes_email1_size100_expect);
        assertNotNull("actural raw bytes is null", bytes_email1_size200_expect);
        assertNotNull("actural raw bytes is null", bytes_email2_size100_expect);
        assertNotNull("actural raw bytes is null", bytes_email2_size200_expect);


        assertEquals("avatar drawable tag not right", EMAIL1, avatar_email1_size100.getTag());
        assertEquals("avatar drawable tag not right", EMAIL1, avatar_email1_size200.getTag());
        assertEquals("avatar drawable tag not right", EMAIL2, avatar_email2_size100.getTag());
        assertEquals("avatar drawable tag not right", EMAIL2, avatar_email2_size200.getTag());

        assertTrue(isBytesEqual(bytes_email1_size100_actural, bytes_email1_size100_expect));
        assertTrue(isBytesEqual(bytes_email1_size200_actural, bytes_email1_size200_expect));
        assertTrue(isBytesEqual(bytes_email2_size100_actural, bytes_email2_size100_expect));
        assertTrue(isBytesEqual(bytes_email2_size200_actural, bytes_email2_size200_expect));

    }

    private boolean isBytesEqual(byte[] bytes1, byte[] bytes2) {
        if (bytes1.length != bytes2.length) {
            return false;
        }

        for (int i = 0; i < bytes1.length; i++) {
            if (bytes1[i] != bytes2[i]) {
                return false;
            }
        }

        return true;
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
