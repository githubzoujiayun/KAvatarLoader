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
        testBindImageView(iv_size100, GravatarConstant.EXIST_EMAIL1,
                GravatarConstant.EXIST_EMAIL1_SIZE_100_URL, TASK_PARM_STYLE.TASK_PARM_USE_EMAIL);
        testBindImageView(iv_size200, GravatarConstant.EXIST_EMAIL2,
                GravatarConstant.EXIST_EMAIL2_SIZE_200_URL, TASK_PARM_STYLE.TASK_PARM_USE_EMAIL);
    }

    //TestCase010 测试KAvatarLoader#bindImageViewByHashCode是否正常工作
    public void testBindImageViewByHashCode() {
        testBindImageView(iv_size100, GravatarConstant.EXIST_EMAIL1_HASH_CODE,
                GravatarConstant.EXIST_EMAIL1_SIZE_100_URL, TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE);
        testBindImageView(iv_size200, GravatarConstant.EXIST_EMAIL2_HASH_CODE,
                GravatarConstant.EXIST_EMAIL2_SIZE_200_URL, TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE);
    }

    //TestCase030 测试KAvatarLoader#bindImageViewByUrl能否正常工作
    public void testBindImageViewByUrl() {
        testBindImageView(iv_size100, GravatarConstant.EXIST_EMAIL1_SIZE_100_URL,
                GravatarConstant.EXIST_EMAIL1_SIZE_100_URL, TASK_PARM_STYLE.TASK_PARM_USE_URL);
        testBindImageView(iv_size100, GravatarConstant.EXIST_EMAIL2_SIZE_200_URL,
                GravatarConstant.EXIST_EMAIL2_SIZE_200_URL, TASK_PARM_STYLE.TASK_PARM_USE_URL);
    }

    private void testBindImageView(final ImageView image_view, final String address, final String tag_expect,
                                   final TASK_PARM_STYLE task_parm_style) {
        final BindCondition bind_condition = new BindCondition();
        final BindListener listener = new BindListener() {
            @Override
            public void onBindFinished(RESULT_CODE result_code) {
                assertEquals("result code is fail", RESULT_CODE.SUCCESS, result_code);
                bind_condition.setBindFinished(true);
            }
        };
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (task_parm_style) {
                    case TASK_PARM_USE_EMAIL:
                        avatar_loader.bindImageViewByEmail(image_view, address, listener);
                        break;
                    case TASK_PARM_USE_URL:
                        avatar_loader.bindImageViewByUrl(image_view, address, listener);
                        break;
                    case TASK_PARM_USE_HASH_CODE:
                        avatar_loader.bindImageViewByHashCode(image_view, address, listener);
                        break;
                }
            }
        });
        boolean wait_result = solo.waitForCondition(bind_condition, 10000);
        if (wait_result) {
            Log.d(TAG, "bind ImageView success " + task_parm_style);
            assertNotNull("drawable is null", image_view.getDrawable());
            assertNotNull("tag is null", image_view.getTag());
            assertEquals("tag not right", tag_expect, image_view.getTag());
        } else {
            Log.d(TAG, "bind ImageView fail " + task_parm_style);
            fail("wait condition fail");
        }
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

    //TestCase032 测试KAvatarLoader#bindActionBarByEmail能否正常工作
    public void testBindActionBarByEmail() {
        testBindActionBar(action_bar, GravatarConstant.EXIST_EMAIL1,
                TASK_PARM_STYLE.TASK_PARM_USE_EMAIL);
    }

    //TestCase033 测试KAvatarLoader#bindActionBarByHashCode能否正常工作
    public void testBindActionBarByHashCode() {
        testBindActionBar(action_bar, GravatarConstant.EXIST_EMAIL1_HASH_CODE,
                TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE);
    }

    //TestCase034 测试KAvatarLoader#binActionBarByUrl能否正常工作
    public void testBindActionBarByUrl() {
        testBindActionBar(action_bar, GravatarConstant.EXIST_EMAIL1_SIZE_100_URL,
                TASK_PARM_STYLE.TASK_PARM_USE_URL);
    }

    private void testBindActionBar(final ActionBar action_bar, final String address, final TASK_PARM_STYLE task_parm_style) {
        final BindCondition bind_condition = new BindCondition();
        final BindListener listener = new BindListener() {
            @Override
            public void onBindFinished(RESULT_CODE result_code) {
                assertEquals("bind actionbar failed", BindListener.RESULT_CODE.SUCCESS, result_code);
                bind_condition.setBindFinished(true);
            }
        };
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (task_parm_style) {
                    case TASK_PARM_USE_EMAIL:
                        avatar_loader.bindActionBarByEmail(action_bar, address, listener);
                        break;
                    case TASK_PARM_USE_URL:
                        avatar_loader.bindActionBarByUrl(action_bar, address, listener);
                        break;
                    case TASK_PARM_USE_HASH_CODE:
                        avatar_loader.bindActionBarByHashCode(action_bar, address, listener);
                        break;
                }
            }
        });
        boolean wait_result = solo.waitForCondition(bind_condition, 10000);
        if (wait_result) {
            Log.d(TAG, "bind ActionBar success " + task_parm_style);

        } else {
            Log.d(TAG, "bind ActionBar fail " + task_parm_style);
            fail("wait condition fail");
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
}
