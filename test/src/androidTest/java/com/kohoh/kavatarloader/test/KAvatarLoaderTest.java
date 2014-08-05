package com.kohoh.kavatarloader.test;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

import com.kohoh.KAvatarLoader.test.KAvatarLoaderTestUseActivity;
import com.kohoh.KAvatarLoader.test.R;
import com.kohoh.kavatarloader.Avatar;
import com.kohoh.kavatarloader.BindListener;
import com.kohoh.kavatarloader.KAvatarLoader;

import java.io.IOException;

/**
 * Created by kohoh on 14-7-29.
 */
public class KAvatarLoaderTest extends ActivityInstrumentationTestCase2<KAvatarLoaderTestUseActivity> {
    public KAvatarLoaderTest() {
        super(KAvatarLoaderTestUseActivity.class);
    }

    private Activity activity;
    private KAvatarLoader avatar_loader;

    private ImageView iv_size100;
    private ImageView iv_size200;
    private ImageView iv_no_size;
    private ImageView iv_not_square_size99;
    private ImageView iv_not_square_size222;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        avatar_loader = new KAvatarLoader(activity);

        iv_size100 = (ImageView) activity.findViewById(R.id.iv_size_100);
        iv_size200 = (ImageView) activity.findViewById(R.id.iv_size_200);
        iv_no_size = (ImageView) activity.findViewById(R.id.iv_no_size);
        iv_not_square_size99 = (ImageView) activity.findViewById(R.id.iv_not_square_size_99);
        iv_not_square_size222 = (ImageView) activity.findViewById(R.id.iv_not_square_size_222);
    }

    //TestCase28 测试KAvatatLoader#loadAvatarByEmail是否正常工作
    public void testLoadAvatarByEmail() throws IOException {
        testLoadAvatarByEmail(GravatarConstant.EXIST_EMAIL1, 100,
                GravatarConstant.EXIST_EMAIL1_SIZE_100_URL);

        testLoadAvatarByEmail(GravatarConstant.EXIST_EMAIL2, 200,
                GravatarConstant.EXIST_EMAIL2_SIZE_200_URL);
    }

    //TestCase27 测试KAvatarLoader#loadAvatarByHashCode是否正常工作
    public void testLoadAvatarByHashCode() throws IOException {
        testLoadAvatarByHashCode(GravatarConstant.EXIST_EMAIL1_HASH_CODE, 100,
                GravatarConstant.EXIST_EMAIL1_SIZE_100_URL);

        testLoadAvatarByHashCode(GravatarConstant.EXIST_EMAIL2_HASH_CODE, 200,
                GravatarConstant.EXIST_EMAIL2_SIZE_200_URL);
    }

    private void assertLoadAvatar(Avatar avatar, String tag_expect) {
        assertNotNull("avatar is null", avatar);
        assertNotNull("avatar's drawable is null", avatar.getDrawable());
        assertNotNull("avatar's bitmap is null", avatar.getBitmap());
        assertNotNull("avatar's byte is null", avatar.getBytes());
        assertNotNull("avatar's tag is null", avatar.getTag());
        assertEquals("avatar's tag not equal", tag_expect, avatar.getTag());
    }

    private void testLoadAvatarByEmail(String email, int size, String tag_expect) {
        Avatar avatar = avatar_loader.loadAvatarByEmail(email, size);
        assertLoadAvatar(avatar, tag_expect);
    }

    private void testLoadAvatarByHashCode(String hash_code, int size, String tag_expect) {
        Avatar avatar = avatar_loader.loadAvatarByHashCode(hash_code, size);
        assertLoadAvatar(avatar, tag_expect);
    }


    //TestCase029 测试是否能够根据ImageView计算出正确的AvatarSize
    public void testCalculateAvatarSize() {
        assertEquals("avatar's size not equal", 100, avatar_loader.calculateAvatarSize(iv_size100));
        assertEquals("avatar's size not equal", 200, avatar_loader.calculateAvatarSize(iv_size200));
        assertEquals("avatar's size not equal",
                activity.getResources().getDimensionPixelSize(R.dimen.default_avatar_size),
                avatar_loader.calculateAvatarSize(iv_no_size));
        assertEquals("avatar's size not equal", 99, avatar_loader.calculateAvatarSize(iv_not_square_size99));
        assertEquals("avatar's size not equal", 222, avatar_loader.calculateAvatarSize(iv_not_square_size222));
    }

//    TestCase001 检测KAvatarLoader#bindImageViewByEmail是否正常工作
    public void testBindImageViewByEmail() throws InterruptedException {
        testBindImageViewByEmail(iv_size100, GravatarConstant.EXIST_EMAIL1, GravatarConstant.EXIST_EMAIL1_SIZE_100_URL);
        testBindImageViewByEmail(iv_size200, GravatarConstant.EXIST_EMAIL2, GravatarConstant.EXIST_EMAIL2_SIZE_200_URL);
    }

//    TestCase010 测试KAvatarLoader#bindImageViewByHashCode是否正常工作
    public void testBindImageViewByHashCode() {
        testBindImageViewByHashCode(iv_size100, GravatarConstant.EXIST_EMAIL1_HASH_CODE, GravatarConstant.EXIST_EMAIL1_SIZE_100_URL);
        testBindImageViewByHashCode(iv_size200, GravatarConstant.EXIST_EMAIL2_HASH_CODE, GravatarConstant.EXIST_EMAIL2_SIZE_200_URL);
    }

    private void testBindImageViewByEmail(final ImageView image_view, final String email, final String tag_expect) {
        avatar_loader.bindImageViewByEmail(image_view, email, new AssertBindImageViewListener(image_view, tag_expect));
    }

    private void testBindImageViewByHashCode(final ImageView image_view, final String hash_code, final String tag_expect) {
        avatar_loader.bindImageViewByHashCode(image_view, hash_code, new AssertBindImageViewListener(image_view, tag_expect));
    }

    class AssertBindImageViewListener implements BindListener {

        private ImageView image_view;
        private String tag_expect;

        AssertBindImageViewListener(ImageView image_view, String tag_expected) {
            this.image_view = image_view;
            this.tag_expect = tag_expected;

        }

        @Override
        public void onBindFinished() {
            Drawable drawable = image_view.getDrawable();
            assertNotNull("drawable is null", drawable);

            String tag_actural = (String) image_view.getTag();
            assertEquals("tag not equal", tag_expect, tag_actural);
        }
    };
}
