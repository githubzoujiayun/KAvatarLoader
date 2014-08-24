package com.kohoh.kavatarloader;


import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.widget.ImageView;

import com.kohoh.KAvatarLoader.test.KAvatarLoaderTestUseActivity;
import com.kohoh.gravatar.Gravatar;
import com.kohoh.kavatarloader.test.Resources;
import com.robotium.solo.Solo;
import com.kohoh.kavatarloader.TaskParm.TASK_PARM_STYLE;
import com.kohoh.kavatarloader.Utils.BindCondition;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
        AvatarLoadTask task = getAvatarTask();
        Avatar avatar = task.loadAvatarByInternet(task_parm);
        assertNotNull("avatar is null", avatar);
        assertNotNull("avatar's drawable is null", avatar.getDrawable(context.getResources()));
        assertNotNull("avatar's bitmap is null", avatar.getBitmap());
        assertNotNull("avatar's byte is null", avatar.getBytes());
        assertNotNull("avatar's tag is null", avatar.getTag());
        assertEquals("avatar's tag not equal", tag_expect, avatar.getTag());
    }

    private void testLoadAvatarAccountDosentExist(TaskParm task_parm, String tag_expect) throws Exception {
        AvatarLoadTask task = getAvatarTask();
        Avatar avatar = task.loadAvatarByInternet(task_parm);

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
                assertOnBindImageViewFinished(RESULT_CODE.SUCCESS, iv_no_size,
                        Constant.EXIST_EMAIL1_D_404_URL);
                condition.setBindFinished(true);
            }
        });

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mock_task.onBindTargetViewFinished(mock_task_parm, mock_avatar);
            }
        });

        if (!solo.waitForCondition(condition,10000)) {
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

        if (!solo.waitForCondition(condition,10000)) {
            fail("wait for condition fail");
        }
    }

    private AvatarLoadTask getAvatarTask() {
        return new AvatarLoadTask(context, null);
    }

    @UiThreadTest
    public void testbindImageViewWithDefaultAvatar() {
        AvatarLoadTask task = getAvatarTask();
        TaskParm taskParm = getTaskParm();
        taskParm.setDefaultAvatar(DefaultAvatar.WAVATAR);
        taskParm.setTargetView(iv_no_size);
        task.bindTargetViewWithDefaultAvatar(taskParm);

        assertNotNull("bind failed", iv_no_size.getDrawable());
        assertNotNull("bind failed", iv_no_size.getTag());
        assertEquals("bind failed", "default avatar WAVATAR", iv_no_size.getTag());
    }

    @UiThreadTest
    public void testBindActionBarWithDefauleAvatar() {
        AvatarLoadTask task = getAvatarTask();
        TaskParm taskParm = getTaskParm();
        taskParm.setDefaultAvatar(DefaultAvatar.GRAVATAR_ICON);
        taskParm.setTargetView(action_bar);
        task.bindTargetViewWithDefaultAvatar(taskParm);
    }

    @UiThreadTest
    public void testBindImageViewWithCustomDefaultAvatar() {
        AvatarLoadTask task = getAvatarTask();
        TaskParm taskParm = getTaskParm();
        taskParm.setDefaultAvatar(DefaultAvatar.HTTP_404);
        taskParm.getDefaultAvatar().setCustomDefaultAvatar(resources.getCunstomDefaultAvatar());
        taskParm.setTargetView(iv_no_size);
        task.bindTargetViewWithDefaultAvatar(taskParm);

        assertNotNull("bind failed", iv_no_size.getDrawable());
        assertNotNull("bind failed", iv_no_size.getTag());
        assertEquals("bind failed", "custom default avatar", iv_no_size.getTag());
    }

    private TaskParm getTaskParm() {
        return new TaskParmUseEmail();
    }


    public void testSaveAvatar() {
        TaskParmUseEmail parmUseEmail = new TaskParmUseEmail();
        parmUseEmail.setEmail(Constant.EXIST_EMAIL1);
        testSaveAvatar(parmUseEmail);

        TaskParmUseHashCode parmUseHashCode = new TaskParmUseHashCode();
        parmUseHashCode.setHashCode(Constant.EXIST_EMAIL2_HASH_CODE);
        testSaveAvatar(parmUseHashCode);
    }

    private void testSaveAvatar(TaskParm parm) {
        File saved_avatar_file = saveAvatar(parm);

        boolean wait_result = solo.waitForLogMessage("save avatar success");
        if (wait_result) {
            if (saved_avatar_file.exists()) {
                Log.d(TAG, "testSaveAvatar success,avatar_save file name = " + saved_avatar_file.getPath());
            } else {
                Log.d(TAG, "testSaveAvatar fail");
                fail("avatar_save dosent exist,avatar_save file name = " + saved_avatar_file.getPath());
            }
        } else {
            Log.d(TAG, "testSaveAvatar fail");
            fail("wait for log fail,avatar_save file name = " + saved_avatar_file.getPath());
        }
    }

    private File saveAvatar(TaskParm parm) {
        AvatarLoadTask task = new AvatarLoadTask(context, null);
        Avatar avatar = task.loadAvatarByInternet(parm);

        File saved_avatar_file = new File(task.getSavedAvatarsDir(), Gravatar.getHashCodeByUrl(avatar.getTag()));
        if (saved_avatar_file.exists()) {
            saved_avatar_file.delete();
        }
        task.saveAvatar(avatar);

        return saved_avatar_file;
    }

    public void testGetSavedAvatar() {
        testGetSavedAvatar(Constant.EXIST_EMAIL1_HASH_CODE);
        testGetSavedAvatar(Constant.EXIST_EMAIL2_HASH_CODE);
    }

    private void testGetSavedAvatar(String hash_code) {
        TaskParmUseHashCode parm = new TaskParmUseHashCode();
        parm.setHashCode(hash_code);
        saveAvatar(parm);

        AvatarLoadTask task = new AvatarLoadTask(context, null);
        Avatar avatar = task.getSavedAvatar(hash_code);

        assertNotNull("avatar is null", avatar);
        assertEquals("tag not right", "saved avatar,HashCode = " + hash_code, avatar.getTag());
        assertNotNull("avatar bytes is null", avatar.getBytes());
        Log.d(TAG, "testGetSavedAvatar success,avatar'tag = " + avatar.getTag());
    }

    public void testClearSavedAvatar() {
        saveAvatar(Constant.getTaskParmUseEmail(Constant.EXIST_EMAIL1));
        saveAvatar(Constant.getTaskParmUseEmail(Constant.EXIST_EMAIL2));

        AvatarLoadTask task = new AvatarLoadTask(context, null);
        task.clearSavedAvatars();
        File saved_avatars_dir = task.getSavedAvatarsDir();
        assertFalse("save avatars dir is not null", saved_avatars_dir.exists());
    }

    public void testClearCachedAvatars() {
        AvatarLoadTask task = new AvatarLoadTask(context, null);
        task.clearCachedAvatars();
        Map cached_avatars = AvatarLoadTask.getCachedAvatars();
        assertTrue("clear cached avatars fail", cached_avatars.isEmpty());
    }

    public void testCacheAvatar() {
        AvatarLoadTask task = new AvatarLoadTask(context, null);
        task.clearCachedAvatars();
        cacheAvatar(Constant.getTaskParmUseEmail(Constant.EXIST_EMAIL1));
        Map cached_avatars = task.getCachedAvatars();
        assertFalse("cached_avatars is empty", cached_avatars.isEmpty());
    }

    private Avatar cacheAvatar(TaskParm parm) {
        AvatarLoadTask task = new AvatarLoadTask(context, null);
        Avatar avatar = task.loadAvatarByInternet(parm);
        task.cacheAvatar(avatar);
        return avatar;
    }

    public void testGetCachedAvatar() {
        testGetCachedAvatar(Constant.EXIST_EMAIL1);
        testGetCachedAvatar(Constant.EXIST_EMAIL2);
    }

    private void testGetCachedAvatar(String email) {
        cacheAvatar(Constant.getTaskParmUseEmail(email));
        AvatarLoadTask task = new AvatarLoadTask(context, null);
        String hash_code = Gravatar.getHashCodeByEmail(email);
        Avatar avatar = task.getCachedAvatar(hash_code);

        assertNotNull("avatar is null", avatar);
        assertNotNull("avatar's bytes is null", avatar.getBytes());
        assertNotNull("avatar's tag is null", avatar.getTag());
        assertEquals("avatar's tag not right", "cached avatar,HashCode = " + hash_code, avatar.getTag());
    }

    private void assertOnBindImageViewFinished(BindListener.RESULT_CODE result_code,ImageView image_view,
                                              String tag_expect) {
        assertEquals("bind fail,result code is fail", BindListener.RESULT_CODE.SUCCESS, result_code);
        assertNotNull("bind fail,image view is null", image_view);
        assertNotNull("bind fail,imgae view 's drawable is null", image_view.getDrawable());
        assertNotNull("bind fail,image view 's tag is null", image_view.getTag());
        assertEquals("bind fail,image view 's tag is wrong", tag_expect, image_view.getTag());
        Log.d(TAG, "testOnBindImagViewFinished success");
    }


}
