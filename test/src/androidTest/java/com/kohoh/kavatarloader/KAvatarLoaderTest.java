package com.kohoh.kavatarloader;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.widget.ImageView;

import com.kohoh.KAvatarLoader.test.KAvatarLoaderTestUseActivity;
import com.kohoh.KAvatarLoader.test.R;
import com.kohoh.kavatarloader.TaskParm.TASK_PARM_STYLE;
import com.kohoh.kavatarloader.test.GravatarConstant;
import com.robotium.solo.Solo;

/**
 * Created by kohoh on 14-7-29.
 */
public class KAvatarLoaderTest extends ActivityInstrumentationTestCase2<KAvatarLoaderTestUseActivity> {
    private ActionBarActivity activity;
    private KAvatarLoader avatar_loader;
    private Solo solo;
    private ImageView iv_size100;
    private ImageView iv_size200;
    private ImageView iv_no_size;
    private ImageView iv_not_square_size99;
    private ImageView iv_not_square_size222;
    private ActionBar action_bar;

    private static String TAG = KAvatarLoaderTest.class.getSimpleName() + "_tag";

    public KAvatarLoaderTest() {
        super(KAvatarLoaderTestUseActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        avatar_loader = new KAvatarLoader(activity);
        solo = new Solo(getInstrumentation(), activity);

        iv_size100 = (ImageView) activity.findViewById(R.id.iv_size_100);
        iv_size200 = (ImageView) activity.findViewById(R.id.iv_size_200);
        iv_no_size = (ImageView) activity.findViewById(R.id.iv_no_size);
        iv_not_square_size99 = (ImageView) activity.findViewById(R.id.iv_not_square_size_99);
        iv_not_square_size222 = (ImageView) activity.findViewById(R.id.iv_not_square_size_222);
        action_bar = activity.getSupportActionBar();
    }


    private void assertLoadAvatar(Avatar avatar, String tag_expect) {
        assertNotNull("avatar is null", avatar);
        assertNotNull("avatar's drawable is null", avatar.getDrawable());
        assertNotNull("avatar's bitmap is null", avatar.getBitmap());
        assertNotNull("avatar's byte is null", avatar.getBytes());
        assertNotNull("avatar's tag is null", avatar.getTag());
        assertEquals("avatar's tag not equal", tag_expect, avatar.getTag());
    }


    //    TestCase029 测试是否能够根据ImageView计算出正确的AvatarSize
    public void testCalculateAvatarSize() {
        assertEquals("avatar's size not equal", 100, avatar_loader.calculateAvatarSize(iv_size100));
        assertEquals("avatar's size not equal", 200, avatar_loader.calculateAvatarSize(iv_size200));
        assertEquals("avatar's size not equal",
                activity.getResources().getDimensionPixelSize(R.dimen.default_avatar_size),
                avatar_loader.calculateAvatarSize(iv_no_size));
        assertEquals("avatar's size not equal", 99, avatar_loader.calculateAvatarSize(iv_not_square_size99));
        assertEquals("avatar's size not equal", 222, avatar_loader.calculateAvatarSize(iv_not_square_size222));
    }

    //TestCase001 检测KAvatarLoader#bindImageViewByEmail是否正常工作
    public void testBindImageViewByEmail() throws InterruptedException {
        testBindImageViewByEmail(iv_size100, GravatarConstant.EXIST_EMAIL1, GravatarConstant.EXIST_EMAIL1_SIZE_100_URL);
        testBindImageViewByEmail(iv_size200, GravatarConstant.EXIST_EMAIL2, GravatarConstant.EXIST_EMAIL2_SIZE_200_URL);
    }

    //TestCase010 测试KAvatarLoader#bindImageViewByHashCode是否正常工作
    public void testBindImageViewByHashCode() {
        testBindImageViewByHashCode(iv_size100, GravatarConstant.EXIST_EMAIL1_HASH_CODE, GravatarConstant.EXIST_EMAIL1_SIZE_100_URL);
        testBindImageViewByHashCode(iv_size200, GravatarConstant.EXIST_EMAIL2_HASH_CODE, GravatarConstant.EXIST_EMAIL2_SIZE_200_URL);
    }

    //TestCase030 测试KAvatarLoader#bindImageViewByUrl能否正常工作
    public void testBindImageViewByUrl() {
        testBindImageViewByUrl(iv_size100, GravatarConstant.EXIST_EMAIL1_SIZE_100_URL, GravatarConstant.EXIST_EMAIL1_SIZE_100_URL);
        testBindImageViewByUrl(iv_size100, GravatarConstant.EXIST_EMAIL2_SIZE_200_URL, GravatarConstant.EXIST_EMAIL2_SIZE_200_URL);
    }

    private void testBindImageViewByUrl(final ImageView image_view, final String url,
                                        final String tag_expect) {
        testBindImageView(image_view, url, tag_expect, TASK_PARM_STYLE.TASK_PARM_USE_URL);
    }

    private void testBindImageViewByEmail(final ImageView image_view, final String email,
                                          final String tag_expect) {
        testBindImageView(image_view, email, tag_expect, TASK_PARM_STYLE.TASK_PARM_USE_EMAIL);
    }

    private void testBindImageViewByHashCode(final ImageView image_view, final String hash_code,
                                             final String tag_expect) {
        testBindImageView(image_view, hash_code, tag_expect, TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE);
    }

    private void testBindImageView(final ImageView imageView, final String address, final String tag_expect,
                                   final TASK_PARM_STYLE task_parm_style) {
        final BindCondition bind_condition = new BindCondition();
        final BindImageViewListener listener = new BindImageViewListener(bind_condition);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (task_parm_style) {
                    case TASK_PARM_USE_EMAIL:
                        avatar_loader.bindImageViewByEmail(imageView, address, listener);
                        break;
                    case TASK_PARM_USE_URL:
                        avatar_loader.bindImageViewByUrl(imageView, address, listener);
                        break;
                    case TASK_PARM_USE_HASH_CODE:
                        avatar_loader.bindImageViewByHashCode(imageView, address, listener);
                        break;
                }
            }
        });
        assertBindImageView(imageView, tag_expect, bind_condition);
    }

    class BindCondition implements com.robotium.solo.Condition {

        private boolean isBindFinished = false;

        public void setBindFinished(boolean isBindFinished) {
            this.isBindFinished = isBindFinished;
        }

        @Override
        public boolean isSatisfied() {
            return isBindFinished;
        }
    }

    private void assertBindImageView(ImageView image_view, String tag_expect, BindCondition bind_condition) {
        boolean wait_result = solo.waitForCondition(bind_condition, 10000);
        if (wait_result) {
            Log.d(TAG, "wait condition success");
            assertNotNull("drawable is null", image_view.getDrawable());
            assertNotNull("tag is null", image_view.getTag());
            assertEquals("tag not right", tag_expect, image_view.getTag());
        } else {
            Log.d(TAG, "wait condition fail");
            fail("wait condition fail");
        }
    }

    //TestCase032 测试KAvatarLoader#bindActionBarByEmail能否正常工作
    public void testBindActionBarByEmail() {
        final String log_message_wait_for = "testBindActionBarByEmail log_message_wait_for";
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avatar_loader.bindActionBarByEmail(action_bar, GravatarConstant.EXIST_EMAIL1, new BindListener() {
                    @Override
                    public void onBindFinished(BindListener.RESULT_CODE result_code) {
                        assertEquals("bind actionbar failed", BindListener.RESULT_CODE.SUCCESS, result_code);
                        Log.d(TAG, log_message_wait_for);
                    }
                });
            }
        });
        boolean wait_result = solo.waitForLogMessage(log_message_wait_for);
        if (!wait_result) {
            fail("wait log fail");
        }
    }

    //TestCase033 测试KAvatarLoader#bindActionBarByHashCode能否正常工作
    public void testBindActionBarByHashCode() {
        final String log_message_wait_for = "testBindActionBarByHashCode log_message_wait_for";
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avatar_loader.bindActionBarByHashCode(action_bar, GravatarConstant.EXIST_EMAIL1_HASH_CODE, new BindListener() {
                    @Override
                    public void onBindFinished(BindListener.RESULT_CODE result_code) {
                        assertEquals("bind actionbar failed", BindListener.RESULT_CODE.SUCCESS, result_code);
                        Log.d(TAG, log_message_wait_for);
                    }
                });
            }
        });
        boolean wait_result = solo.waitForLogMessage(log_message_wait_for);
        if (!wait_result) {
            fail("wait log fail");
        }
    }

    //TestCase034 测试KAvatarLoader#binActionBarByUrl能否正常工作
    public void testBindActionBarByUrl() {
        final String log_message_wait_for = "testBindActionBarByUrl log_message_wait_for";
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avatar_loader.bindActionBarByUrl(action_bar, GravatarConstant.EXIST_EMAIL1_SIZE_100_URL, new BindListener() {
                    @Override
                    public void onBindFinished(BindListener.RESULT_CODE result_code) {
                        assertEquals("bind actionbar failed", BindListener.RESULT_CODE.SUCCESS, result_code);
                        Log.d(TAG, log_message_wait_for);
                    }
                });
            }
        });
        boolean wait_result = solo.waitForLogMessage(log_message_wait_for);
        if (!wait_result) {
            fail("wait log fail");
        }
    }

    //TestCase036 测试KAvatarLoader能否在加载Avatar之前首先加载默认的Avatar
    @UiThreadTest
    public void testLoadDefaultImageBeforeRealLoad() {
        testLoadDeaultImageBeforeLoadRealAvatar(DefaultAvatar.MYSTERY_MEN);
        testLoadDeaultImageBeforeLoadRealAvatar(DefaultAvatar.GRAVATAR_ICON);
    }

    private void testLoadDeaultImageBeforeLoadRealAvatar(DefaultAvatar default_avatar) {
        avatar_loader.setDefaultAvatar(default_avatar);
        avatar_loader.bindImageViewByEmail(iv_size100, GravatarConstant.EXIST_EMAIL1, null);

        String avatar_tag_expect = "default avatar " + default_avatar.toString();
        String avatar_tag_actural = (String) iv_size100.getTag();
        assertEquals("LoadDefaultImageBeforeLoadRealAvatar failed", avatar_tag_expect, avatar_tag_actural);
    }

    //TestCase037 测试KAvatarLoader#setDefaultAvatar能否设置自定义的default avatar
    @UiThreadTest
    public void testSetCustomDefaultAvatarBeforeRealLoad() {
        avatar_loader.setDefaultAvatar(R.drawable.custom_default_avatar);
        avatar_loader.bindImageViewByEmail(iv_size100, GravatarConstant.EXIST_EMAIL1, null);

        String avatar_tag_expect = "custom default avatar";
        String avatar_tag_actural = (String) iv_size100.getTag();
        assertEquals("LoadDefaultImageBeforeLoadRealAvatar failed", avatar_tag_expect, avatar_tag_actural);
    }

    //TestCase038 测试当DefaultAvatar被设置为HTTP_404或者CUSTOM_DEFAULT_AVATAR状态时，加载avatar后能否正常工作
    public void testSetCustomDefaultAvatarAfterRealLoadFailed() {
        avatar_loader.setDefaultAvatar(R.drawable.custom_default_avatar);
        final String log_message_wait_for = "testSetCustomDefaultAvatarAfterRealLoadFailed log_message_wait_for";
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avatar_loader.bindImageViewByEmail(iv_size100, GravatarConstant.DOSENT_EXIST_EMAIL, new BindListener() {
                    @Override
                    public void onBindFinished(BindListener.RESULT_CODE result_code) {
                        assertEquals("result code wrong", BindListener.RESULT_CODE.FAIL, result_code);
                        Log.d(TAG, log_message_wait_for);
                    }
                });
            }
        });
        if (solo.waitForLogMessage(log_message_wait_for)) {
            assertEquals("tag wrong", "custom default avatar", iv_size100.getTag());
            Log.d(TAG, "log wait success");
        } else {
            fail("wait log fail");
        }
    }

    class BindImageViewListener implements BindListener {
        private BindCondition bind_condition;

        BindImageViewListener(BindCondition bind_condition) {
            this.bind_condition = bind_condition;
        }

        @Override
        public void onBindFinished(BindListener.RESULT_CODE result_code) {
            assertEquals("result code is fail", RESULT_CODE.SUCCESS, result_code);
            if (bind_condition != null) {
                bind_condition.setBindFinished(true);
            }
        }
    }
}
