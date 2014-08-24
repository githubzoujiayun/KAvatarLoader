package com.kohoh.kavatarloader;


import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.ImageView;

import com.kohoh.KAvatarLoader.test.KAvatarLoaderTestUseActivity;
import com.kohoh.gravatar.Gravatar;
import com.kohoh.kavatarloader.TaskParm.TASK_PARM_STYLE;
import com.kohoh.kavatarloader.Utils.BindCondition;
import com.kohoh.kavatarloader.test.Resources;
import com.robotium.solo.Solo;

import java.io.File;
import java.io.IOException;

/**
 * Created by kohoh on 14-8-12.
 */
public class AvatarLoadTaskTest extends ActivityInstrumentationTestCase2<KAvatarLoaderTestUseActivity> {
    public final static String TAG = AvatarLoadTaskTest.class.getSimpleName() + "_tag";
    private Context context;
    private ActionBarActivity activity;
    private Solo solo;
    private Resources resources;
    private ImageView iv_no_size;
    private ActionBar action_bar;

    public AvatarLoadTaskTest() {
        super(KAvatarLoaderTestUseActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        this.activity = getActivity();
        this.context = this.activity;
        this.resources = new Resources(context);
        this.solo = new Solo(getInstrumentation(), activity);
        iv_no_size = (ImageView) activity.findViewById(com.kohoh.KAvatarLoader.test.R.id.iv_no_size);
        action_bar = activity.getSupportActionBar();
    }

    public void testLoadAvatarUesUrl() throws Exception {
        testLoadAvatarAccountExist(Utils.getMockTaskParm(TASK_PARM_STYLE.TASK_PARM_USE_URL,
                        Constant.EXIST_EMAIL1_D_404_URL, DefaultAvatar.HTTP_404),
                Constant.EXIST_EMAIL1_D_404_URL);

        testLoadAvatarAccountExist(Utils.getMockTaskParm(TASK_PARM_STYLE.TASK_PARM_USE_URL,
                        Constant.EXIST_EMAIL2_D_404_URL, DefaultAvatar.HTTP_404),
                Constant.EXIST_EMAIL2_D_404_URL);
    }

    public void testLoadAvatarUseEmail() throws Exception {
        testLoadAvatarAccountExist(Utils.getMockTaskParm(TASK_PARM_STYLE.TASK_PARM_USE_EMAIL,
                        Constant.EXIST_EMAIL1, DefaultAvatar.HTTP_404),
                Constant.EXIST_EMAIL1_D_404_URL);

        testLoadAvatarAccountExist(Utils.getMockTaskParm(TASK_PARM_STYLE.TASK_PARM_USE_EMAIL,
                        Constant.EXIST_EMAIL2, DefaultAvatar.HTTP_404),
                Constant.EXIST_EMAIL2_D_404_URL);
    }

    public void testLoadAvatarUseHashCode() throws Exception {
        testLoadAvatarAccountExist(Utils.getMockTaskParm(TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE,
                        Constant.EXIST_EMAIL1_HASH_CODE, DefaultAvatar.HTTP_404),
                Constant.EXIST_EMAIL1_D_404_URL);

        testLoadAvatarAccountExist(Utils.getMockTaskParm(TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE,
                        Constant.EXIST_EMAIL2_HASH_CODE, DefaultAvatar.HTTP_404),
                Constant.EXIST_EMAIL2_D_404_URL);
    }

    //测试加载不存在账户的头像
    public void testLoadAvatarAccountDosentExist() throws Exception {
        //默认头像被设置为HTTP_404
        testLoadAvatarAccountDosentExist(Utils.getMockTaskParm(TASK_PARM_STYLE.TASK_PARM_USE_URL,
                        Constant.DOSENT_EXIST_EMAIL_D_404_RUL, DefaultAvatar.HTTP_404),
                Constant.DOSENT_EXIST_EMAIL_D_404_RUL);

        testLoadAvatarAccountDosentExist(Utils.getMockTaskParm(TASK_PARM_STYLE.TASK_PARM_USE_EMAIL,
                        Constant.DOSENT_EXIST_EMAIL, DefaultAvatar.HTTP_404),
                Constant.DOSENT_EXIST_EMAIL_D_404_RUL);

        testLoadAvatarAccountDosentExist(Utils.getMockTaskParm(TASK_PARM_STYLE.TASK_PARM_USE_HASH_CODE,
                        Constant.DOSENT_EXIST_EMAIL_HASH_CODE, DefaultAvatar.HTTP_404),
                Constant.DOSENT_EXIST_EMAIL_D_404_RUL);

        //默认头像被设置为非HTTP_404
        testLoadAvatarAccountDosentExist(Utils.getMockTaskParm(TASK_PARM_STYLE.TASK_PARM_USE_EMAIL,
                        Constant.DOSENT_EXIST_EMAIL, DefaultAvatar.MONSTERID),
                Constant.DOSENT_EXIST_EMAIL_D_MONSTERID_RUL);

        testLoadAvatarAccountDosentExist(Utils.getMockTaskParm(TASK_PARM_STYLE.TASK_PARM_USE_EMAIL,
                        Constant.DOSENT_EXIST_EMAIL, DefaultAvatar.IDENTICON),
                Constant.DOSENT_EXIST_EMAIL_D_IDENTICON_RUL);

        testLoadAvatarAccountDosentExist(Utils.getMockTaskParm(TASK_PARM_STYLE.TASK_PARM_USE_EMAIL,
                        Constant.DOSENT_EXIST_EMAIL, DefaultAvatar.WAVATAR),
                Constant.DOSENT_EXIST_EMAIL_D_WAVATAR_RUL);
    }

    private void testLoadAvatarAccountExist(TaskParm task_parm, String tag_expect) throws Exception {
        AvatarLoadTask mock_task = Utils.getMockAvatarLoadTask(context);
        Avatar avatar = mock_task.loadAvatarByInternet(task_parm);
        assertNotNull("avatar is null", avatar);
        assertNotNull("avatar's drawable is null", avatar.getDrawable(context.getResources()));
        assertNotNull("avatar's bitmap is null", avatar.getBitmap());
        assertNotNull("avatar's byte is null", avatar.getBytes());
        assertNotNull("avatar's tag is null", avatar.getTag());
        assertEquals("avatar's tag not equal", tag_expect, avatar.getTag());
    }

    private void testLoadAvatarAccountDosentExist(TaskParm task_parm, String tag_expect) throws Exception {
        AvatarLoadTask mock_task = Utils.getMockAvatarLoadTask(context);
        Avatar avatar = mock_task.loadAvatarByInternet(task_parm);

        assertNotNull("avatar is null", avatar);
        assertNotNull("avatar's tag is null", avatar.getTag());
        assertEquals("avatar's tag not equal", tag_expect, avatar.getTag());

        DefaultAvatar default_avatar = task_parm.getDefaultAvatar();
        if (default_avatar.equals(DefaultAvatar.HTTP_404)) {
            assertNull("avatar's drawable is not null", avatar.getDrawable(context.getResources()));
            assertNull("avatar's bitmap is not null", avatar.getBitmap());
            assertNull("avatar's byte is not null", avatar.getBytes());
        } else {
            assertNotNull("avatar's drawable is null", avatar.getDrawable(context.getResources()));
            assertNotNull("avatar's bitmap is null", avatar.getBitmap());
            assertNotNull("avatar's byte is null", avatar.getBytes());
        }
    }

    public void testOnBindImageViewFinished() throws Exception {
        final BindCondition condition = Utils.getBindCondition();
        final AvatarLoadTask mock_task = Utils.getMockAvatarLoadTask(context);
        final Avatar mock_avatar = Utils.getMockAvatarSize100Email1(context);
        final TaskParm mock_task_parm = Utils.getMockTaskParm(iv_no_size, new BindListener() {
            @Override
            public void onBindFinished(RESULT_CODE result_code) {
                assertEquals("bind fail,result code is fail", BindListener.RESULT_CODE.SUCCESS, result_code);
                assertNotNull("bind fail,image view is null", iv_no_size);
                assertNotNull("bind fail,imgae view 's drawable is null", iv_no_size.getDrawable());
                assertNotNull("bind fail,image view 's tag is null", iv_no_size.getTag());
                assertEquals("bind fail,image view 's tag is wrong", Constant.EXIST_EMAIL1_D_404_URL,
                        iv_no_size.getTag());
                condition.setBindFinished(true);
            }
        });

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mock_task.onBindTargetViewFinished(mock_task_parm, mock_avatar);
            }
        });

        if (!solo.waitForCondition(condition, 10000)) {
            fail("wait for condition fail");
        }
    }

    public void testOnBindActionBarFinished() throws IOException {
        final BindCondition condition = com.kohoh.kavatarloader.Utils.getBindCondition();
        final AvatarLoadTask mock_task = Utils.getMockAvatarLoadTask(context);
        final Avatar mock_avatar = Utils.getMockAvatarSize100Email1(context);
        final TaskParm mock_task_parm = Utils.getMockTaskParm(action_bar, new BindListener() {
            @Override
            public void onBindFinished(RESULT_CODE result_code) {
                assertEquals("result code not right", RESULT_CODE.SUCCESS, result_code);
                condition.setBindFinished(true);
            }
        });

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mock_task.onBindTargetViewFinished(mock_task_parm, mock_avatar);
            }
        });

        if (!solo.waitForCondition(condition, 10000)) {
            fail("wait for condition fail");
        }
    }

    @UiThreadTest
    public void testbindImageViewWithDefaultAvatar() {
        AvatarLoadTask mock_task = Utils.getMockAvatarLoadTask(context);
        TaskParm mock_task_parm = Utils.getMockTaskParm(iv_no_size, DefaultAvatar.WAVATAR);
        mock_task.bindTargetViewWithDefaultAvatar(mock_task_parm);

        assertNotNull("bind failed", iv_no_size.getDrawable());
        assertNotNull("bind failed", iv_no_size.getTag());
        assertEquals("bind failed", "default avatar WAVATAR", iv_no_size.getTag());
    }

    @UiThreadTest
    public void testBindActionBarWithDefauleAvatar() {
        AvatarLoadTask mock_task = Utils.getMockAvatarLoadTask(context);
        TaskParm mock_task_parm = Utils.getMockTaskParm(action_bar, DefaultAvatar.GRAVATAR_ICON);
        mock_task.bindTargetViewWithDefaultAvatar(mock_task_parm);
        mock_task.bindTargetViewWithDefaultAvatar(mock_task_parm);
    }

    @UiThreadTest
    public void testBindImageViewWithCustomDefaultAvatar() {
        AvatarLoadTask mock_task = Utils.getMockAvatarLoadTask(context);
        TaskParm mock_task_parm = Utils.getMockTaskParm(iv_no_size, DefaultAvatar.HTTP_404);
        mock_task_parm.getDefaultAvatar().setCustomDefaultAvatar(resources.getCunstomDefaultAvatar());
        mock_task.bindTargetViewWithDefaultAvatar(mock_task_parm);

        assertNotNull("bind failed", iv_no_size.getDrawable());
        assertNotNull("bind failed", iv_no_size.getTag());
        assertEquals("bind failed", "custom default avatar", iv_no_size.getTag());
    }

    public void testSaveAvatar() {
        Avatar mock_avatar = Utils.getMockAvatarSize100Email1(context);
        String hash_code = Gravatar.getHashCodeByUrl(mock_avatar.getTag());
        AvatarLoadTask mock_task = Utils.getMockAvatarLoadTask(context);

        mock_task.clearSavedAvatars();
        mock_task.saveAvatar(mock_avatar);
        Avatar avatar = mock_task.getSavedAvatar(hash_code);

        boolean wait_result = solo.waitForLogMessage("save avatar success");
        if (wait_result) {
            assertNotNull(avatar);
            assertNotNull(avatar.getBytes());
            assertNotNull(avatar.getTag());
            assertEquals("saved avatar,HashCode = " + hash_code, avatar.getTag());
        } else {
            fail("wait for log fail");
        }
    }

    public void testGetSavedAvatar() {
        Avatar mock_avatar = Utils.getMockAvatarSize100Email1(context);
        AvatarLoadTask mock_task = Utils.getMockAvatarLoadTask(context);
        String hash_code = Gravatar.getHashCodeByUrl(mock_avatar.getTag());

        mock_task.clearSavedAvatars();
        mock_task.saveAvatar(mock_avatar);

        Avatar avatar = mock_task.getSavedAvatar(hash_code);

        if (solo.waitForLogMessage("get saved avatar,HashCode = " + hash_code)) {
            assertNotNull("avatar is null", avatar);
            assertEquals("tag not right", "saved avatar,HashCode = " + hash_code, avatar.getTag());
            assertNotNull("avatar bytes is null", avatar.getBytes());
        } else {
            fail("wait for log fail");
        }

        mock_task.getSavedAvatar("ehdihe879879");
        if (!solo.waitForLogMessage("saved avatar dosen't exist,HashCode = ehdihe879879")) {
            fail("wait for log fail");
        }
    }

    public void testClearSavedAvatar() {
        AvatarLoadTask mock_task = Utils.getMockAvatarLoadTask(context);
        mock_task.clearSavedAvatars();

        if (solo.waitForLogMessage("clear saved avatars success")) {
            File saved_avatars_dir = mock_task.getSavedAvatarsDir();
            assertTrue(!saved_avatars_dir.exists());
        } else {
            fail("wait for log fail");
        }
    }

    public void testCacheAvatar() {
        AvatarLoadTask mock_task = Utils.getMockAvatarLoadTask(context);
        AvatarLoadTask.clearCachedAvatars();
        Avatar mock_avatar = Utils.getMockAvatarSize100Email1(context);
        String hash_code = Gravatar.getHashCodeByUrl(mock_avatar.getTag());

        mock_task.cacheAvatar(mock_avatar);

        if (solo.waitForLogMessage("cache avatar success,HashCode = " + hash_code)) {
            Avatar avatar = mock_task.getCachedAvatar(hash_code);
            assertNotNull(avatar);
            assertNotNull(avatar.getBytes());
            assertNotNull(avatar.getTag());
            assertEquals("cached avatar,HashCode = " + hash_code, avatar.getTag());
        } else {
            fail("wait for log fail");
        }
    }

    public void testGetCachedAvatar() {
        String email = Constant.EXIST_EMAIL1;
        AvatarLoadTask mock_task = Utils.getMockAvatarLoadTask(context);
        Avatar mock_avatar = Utils.getMockAvatarSize100Email1(context);
        String hash_code = Gravatar.getHashCodeByEmail(email);
        AvatarLoadTask.clearCachedAvatars();
        mock_task.cacheAvatar(mock_avatar);

        Avatar avatar = mock_task.getCachedAvatar(hash_code);

        if (!solo.waitForLogMessage("get cached avatar,HashCode = " + hash_code)) {
            fail("wait for log fail");
        } else {
            assertNotNull(avatar);
            assertNotNull(avatar.getBytes());
            assertNotNull(avatar.getTag());
            assertEquals("cached avatar,HashCode = " + hash_code, avatar.getTag());
        }

        //不存在的hashcode
        mock_task.getCachedAvatar("dhaiusdhiuw7897879");
        if (!solo.waitForLogMessage("cached avatar not exist,HashCode = dhaiusdhiuw7897879")) {
            fail("wait for log fail");
        }
    }

    public void testClearCachedAvatars() {
        AvatarLoadTask.clearCachedAvatars();

        boolean wait_result = solo.waitForLogMessage("clear cached avatars success");
        if (!wait_result) {
            fail("wait for log fail");
        } else {
            assertTrue(AvatarLoadTask.getCachedAvatars().isEmpty());
        }
    }


}
