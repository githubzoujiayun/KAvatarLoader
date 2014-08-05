package com.kohoh.kavatarloader.test;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

import com.kohoh.KAvatarLoader.test.R;
import com.kohoh.KAvatarLoader.test.SingleBindViewActivity;
import com.kohoh.kavatarloader.Avatar;
import com.kohoh.kavatarloader.BindListener;
import com.kohoh.kavatarloader.KAvatarLoader;

import java.io.IOException;

/**
 * Created by kohoh on 14-7-29.
 */
public class KAvatarLoaderTest extends ActivityInstrumentationTestCase2<SingleBindViewActivity> {
    public KAvatarLoaderTest() {
        super(SingleBindViewActivity.class);
    }

    private Activity activity;
    private KAvatarLoader avatar_loader;
    final private String EMAIL1 = GravatarConstant.EXIST_EMAIL1;
    final private String EMAIL2 = GravatarConstant.EXIST_EMAIL2;
    final private int EMAIL1_SIZE100_LENGTH = GravatarConstant.EMAIL1_SIZE100_LENGTH;
    final private int EMAIL1_SIZE200_LENGTH = GravatarConstant.EMAIL1_SIZE200_LENGTH;
    final private int EMAIL2_SIZE100_LENGTH = GravatarConstant.EMAIL2_SIZE100_LENGTH;
    final private int EMAIL2_SIZE200_LENGTH = GravatarConstant.EMAIL2_SIZE200_LENGTH;


    private ImageView iv_avatar_no_size;
    private ImageView iv_avatar_small_size;
    private ImageView iv_avatar_medium_size;
    private ImageView iv_avatar_large_size;
    private ImageView iv_avatar_xlarge_size;
    private ImageView iv_avatar_actionbar_size;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        avatar_loader = new KAvatarLoader(activity);

        iv_avatar_no_size = (ImageView) activity.findViewById(R.id.iv_avatar_no_size);
        iv_avatar_small_size = (ImageView) activity.findViewById(R.id.iv_avatar_small_size);
        iv_avatar_medium_size = (ImageView) activity.findViewById(R.id.iv_avatar_medium_size);
        iv_avatar_large_size = (ImageView) activity.findViewById(R.id.iv_avatar_large_size);
        iv_avatar_xlarge_size = (ImageView) activity.findViewById(R.id.iv_avatar_xlarge_size);
        iv_avatar_actionbar_size = (ImageView) activity.findViewById(R.id.iv_avatar_actionbar_size);
    }

    public void testLoadAvatar() throws IOException {
        Avatar avatar_email1_size100 = avatar_loader.loadAvatar(EMAIL1, 100);
        Avatar avatar_email1_size200 = avatar_loader.loadAvatar(EMAIL1, 200);
        Avatar avatar_email2_size100 = avatar_loader.loadAvatar(EMAIL2, 100);
        Avatar avatar_email2_size200 = avatar_loader.loadAvatar(EMAIL2, 200);

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
        Avatar avatar_email1_size100 = avatar_loader.loadAvatar(EMAIL1, 100);
        Avatar avatar_email1_size200 = avatar_loader.loadAvatar(EMAIL1, 200);
        Avatar avatar_email2_size100 = avatar_loader.loadAvatar(EMAIL2, 100);
        Avatar avatar_email2_size200 = avatar_loader.loadAvatar(EMAIL2, 200);

        assertEquals(EMAIL1_SIZE100_LENGTH, avatar_email1_size100.getBytes().length);
        assertEquals(EMAIL1_SIZE200_LENGTH, avatar_email1_size200.getBytes().length);
        assertEquals(EMAIL2_SIZE100_LENGTH, avatar_email2_size100.getBytes().length);
        assertEquals(EMAIL2_SIZE200_LENGTH, avatar_email2_size200.getBytes().length);
    }

//    TestCase001 给KAvatarLoader#bind(),穿入一个ImageView和一个Email地址，
//    根据这个Email地址加载正确的头像到ImageV上
    public void testBindImageViewByEmail() throws InterruptedException {
        bindImageView(iv_avatar_no_size,GravatarConstant.EXIST_EMAIL1);
        bindImageView(iv_avatar_actionbar_size,GravatarConstant.EXIST_EMAIL1);
        bindImageView(iv_avatar_small_size,GravatarConstant.EXIST_EMAIL1);
        bindImageView(iv_avatar_medium_size,GravatarConstant.EXIST_EMAIL2);
        bindImageView(iv_avatar_large_size,GravatarConstant.EXIST_EMAIL2);
        bindImageView(iv_avatar_xlarge_size,GravatarConstant.EXIST_EMAIL2);
    }

    private void bindImageView(final ImageView imageView, final String email) {
        avatar_loader.bind(imageView, email, new BindListener() {
            @Override
            public void onBindFinished() {
                Drawable drawable = imageView.getDrawable();
                assertNotNull("drawable is null", drawable);

                String tag = (String) imageView.getTag();
                assertEquals("bind failed", email, tag);
            }
        });
    }

    //TODO 待完成
//    public void testBindImageViewByHashCode() {
//        String hash_code = GravatarConstant.DOSENT_EXIST_EMAIL_HASH_CODE;
//        final String tag_expect = "http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=404";
//
//        avatar_loader.bindImageViewByHashCode(iv_avatar_medium_size, hash_code, new BindListener() {
//
//            @Override
//            public void onBindFinished() {
//                Drawable drawable = iv_avatar_medium_size.getDrawable();
//                assertNotNull("drawable is null", drawable);
//
//                String tag_actural = (String) iv_avatar_medium_size.getTag();
//                assertEquals("tag not right", tag_expect, tag_actural);
//
//                //TODO 检测drawable对应的byte[]是否正确。
//
//            }
//        });
//    }

    //TestCase27 测试KAvatarLoader#loadAvatarByHashCode是否正常工作
    public void testLoadAvatarByHashCode() throws IOException {
        String hash_code = GravatarConstant.EXIST_EMAIL1_HASH_CODE;
        int avatar_size = 100;
        Avatar avatar = avatar_loader.loadAvatarByHashCode(hash_code, avatar_size);

        assertNotNull("avatar is null", avatar);
        assertNotNull("bitmap is null", avatar.getBitmap());
        assertNotNull("drawable is null", avatar.getDrawable());
        assertNotNull("bytes is null", avatar.getBytes());
        assertNotNull("tag is null", avatar.getTag());
        assertEquals("tag not equal", GravatarConstant.EXIST_EMAIL1_SIZE_100_URL, avatar.getTag());

        GravatarTestUtils gravatar_test_utils = new GravatarTestUtils(this.getActivity());
        byte[] raw_gravatar_expect = gravatar_test_utils.loadExpectRawGravatar(GravatarConstant.EXIST_EMAIL1_SIZE_100_BYTE_FILE_NAME);
        byte[] raw_gravatar_actural = avatar.getBytes();
        gravatar_test_utils.assertRawGravatarEquals(raw_gravatar_expect, raw_gravatar_actural);
    }
}
