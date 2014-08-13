package com.kohoh.kavatarloader;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.widget.ImageView;

import com.kohoh.KAvatarLoader.test.*;
import com.kohoh.kavatarloader.test.Resources;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kohoh on 14-8-12.
 */
public class AvatarLoadTaskTest extends ActivityInstrumentationTestCase2<KAvatarLoaderTestUseActivity> {
    public AvatarLoadTaskTest() {
        super(KAvatarLoaderTestUseActivity.class);
    }

    private Context context;
    private Activity activity;

    public final static String TAG = AvatarLoadTaskTest.class.getSimpleName() + "_tag";

    private ImageView iv_no_size;
    private ActionBar action_bar;
    private Resources resources;

    @Override
    protected void setUp() throws Exception {
        this.activity = getActivity();
        this.context = this.activity;
        this.resources = new Resources(context);
        iv_no_size = (ImageView) activity.findViewById(com.kohoh.KAvatarLoader.test.R.id.iv_no_size);
        action_bar = activity.getActionBar();
    }

    public void testLoadAvatarUesUrl() throws Exception {
        testLoadAvatarWhoseAccountExist(getTaskParmUseUrl(AvatarLoadTaskConstant.EXIST_EMAIL1_DEFAULT_URL),
                AvatarLoadTaskConstant.EXIST_EMAIL1_DEFAULT_URL);

        testLoadAvatarWhoseAccountExist(getTaskParmUseUrl(AvatarLoadTaskConstant.EXIST_EMAIL2_DEFAULT_URL),
                AvatarLoadTaskConstant.EXIST_EMAIL2_DEFAULT_URL);
    }

    public void testLoadAvatarUseEmail() throws Exception {
        testLoadAvatarWhoseAccountExist(getTaskParmUseEmail(AvatarLoadTaskConstant.EXIST_EMAIL1),
                AvatarLoadTaskConstant.EXIST_EMAIL1_DEFAULT_URL);

        testLoadAvatarWhoseAccountExist(getTaskParmUseEmail(AvatarLoadTaskConstant.EXIST_EMAIL2),
                AvatarLoadTaskConstant.EXIST_EMAIL2_DEFAULT_URL);
    }

    public void testLoadAvatarUseHashCode() throws Exception {
        testLoadAvatarWhoseAccountExist(getTaskParmUseHashCode(AvatarLoadTaskConstant.EXIST_EMAIL1_HASH_CODE),
                AvatarLoadTaskConstant.EXIST_EMAIL1_DEFAULT_URL);

        testLoadAvatarWhoseAccountExist(getTaskParmUseHashCode(AvatarLoadTaskConstant.EXIST_EMAIL2_HASH_CODE),
                AvatarLoadTaskConstant.EXIST_EMAIL2_DEFAULT_URL);
    }

    public void testLoadAvatarWhoseAccountDosentExist() throws Exception {
        testLoadAvatarWhoseAccountDosentExist(getTaskParmUseUrl(AvatarLoadTaskConstant.DOSENT_EXIST_EMAIL_DEFAULT_RUL),
                AvatarLoadTaskConstant.DOSENT_EXIST_EMAIL_DEFAULT_RUL);

        testLoadAvatarWhoseAccountDosentExist(getTaskParmUseEmail(AvatarLoadTaskConstant.DOSENT_EXIST_EMAIL),
                AvatarLoadTaskConstant.DOSENT_EXIST_EMAIL_DEFAULT_RUL);

        testLoadAvatarWhoseAccountDosentExist(getTaskParmUseHashCode(AvatarLoadTaskConstant.DOSENT_EXIST_EMAIL_HASH_CODE),
                AvatarLoadTaskConstant.DOSENT_EXIST_EMAIL_DEFAULT_RUL);

        TaskParm task_parm = getTaskParmUseUrl(AvatarLoadTaskConstant.DOSENT_EXIST_EMAIL_DEFAULT_IMAGE_MONSTERID_RUL);
        task_parm.setDefaultAvatar(DefaultAvatar.MONSTERID);
        testLoadAvatarWhoseAccountDosentExist(task_parm, AvatarLoadTaskConstant.DOSENT_EXIST_EMAIL_DEFAULT_IMAGE_MONSTERID_RUL);

        task_parm = getTaskParmUseEmail(AvatarLoadTaskConstant.DOSENT_EXIST_EMAIL);
        task_parm.setDefaultAvatar(DefaultAvatar.IDENTICON);
        testLoadAvatarWhoseAccountDosentExist(task_parm, AvatarLoadTaskConstant.DOSENT_EXIST_EMAIL_DEFAULT_IMAGE_IDENTICON_RUL);

        task_parm = getTaskParmUseHashCode(AvatarLoadTaskConstant.DOSENT_EXIST_EMAIL_HASH_CODE);
        task_parm.setDefaultAvatar(DefaultAvatar.WAVATAR);
        testLoadAvatarWhoseAccountDosentExist(task_parm, AvatarLoadTaskConstant.DOSENT_EXIST_EMAIL_DEFAULT_IMAGE_WAVATAR_RUL);
    }

    private TaskParmUseHashCode getTaskParmUseHashCode(String hash_code) {
        TaskParmUseHashCode task_parm = new TaskParmUseHashCode();
        task_parm.setHashCode(hash_code);
        return task_parm;
    }

    private TaskParmUseEmail getTaskParmUseEmail(String email) {
        TaskParmUseEmail task_parm = new TaskParmUseEmail();
        task_parm.setEmail(email);
        return task_parm;
    }

    private TaskParmUseUrl getTaskParmUseUrl(String url) {
        TaskParmUseUrl task_parm = new TaskParmUseUrl();
        task_parm.setUrl(url);
        return task_parm;
    }

    private void testLoadAvatarWhoseAccountExist(TaskParm task_parm, String tag_expect) throws Exception {
        AvatarLoadTask task = getAvatarTask();
        Avatar avatar = task.loadAvatar(task_parm);
        assertNotNull("avatar is null", avatar);
        assertNotNull("avatar's drawable is null", avatar.getDrawable());
        assertNotNull("avatar's bitmap is null", avatar.getBitmap());
        assertNotNull("avatar's byte is null", avatar.getBytes());
        assertNotNull("avatar's tag is null", avatar.getTag());
        assertEquals("avatar's tag not equal", tag_expect, avatar.getTag());
    }

    private void testLoadAvatarWhoseAccountDosentExist(TaskParm task_parm, String tag_expect) throws Exception {
        AvatarLoadTask task = getAvatarTask();
        Avatar avatar = task.loadAvatar(task_parm);

        assertNotNull("avatar is null", avatar);
        assertNotNull("avatar's tag is null", avatar.getTag());
        assertEquals("avatar's tag not equal", tag_expect, avatar.getTag());

        DefaultAvatar default_avatar = task_parm.getDefaultAvatar();
        if (default_avatar.equals(DefaultAvatar.HTTP_404) ||
                default_avatar.equals(DefaultAvatar.CUSTOM_DEFAULT_AVATAR)) {
            assertNull("avatar's drawable is not null", avatar.getDrawable());
            assertNull("avatar's bitmap is not null", avatar.getBitmap());
            assertNull("avatar's byte is not null", avatar.getBytes());
        } else {
            assertNotNull("avatar's drawable is null", avatar.getDrawable());
            assertNotNull("avatar's bitmap is null", avatar.getBitmap());
            assertNotNull("avatar's byte is null", avatar.getBytes());
        }
    }

    @UiThreadTest
    public void testOnBindImageViewFinished() throws Exception {
        AvatarLoadTask task = getAvatarTask();
        Avatar avatar = getAvatar();
        TaskParm task_parm = getTaskParm();
        task_parm.setTargetView(iv_no_size);
        task_parm.setBindListner(new AssertOnBindImageViewFinished(iv_no_size, avatar.getTag()));
        task.onBindTargetViewFinished(task_parm, avatar);
    }

    @UiThreadTest
    public void testOnBindActionBarFinished() throws IOException {
        AvatarLoadTask task = getAvatarTask();
        Avatar avatar = getAvatar();
        TaskParm task_parm = getTaskParm();
        task_parm.setTargetView(action_bar);
        task_parm.setBindListner(new AssertOnBindActionBarFinished(action_bar));
        task.onBindTargetViewFinished(task_parm, avatar);
    }

    @UiThreadTest
    public void testOnBindTargetViewFinisheFailed() throws IOException {
        AvatarLoadTask task = getAvatarTask();
        Avatar avatar = getAvatarWhoseByteIsNull();
        TaskParm task_parm = getTaskParm();
        task_parm.setTargetView(iv_no_size);
        task_parm.setBindListner(new AssertOnBindImageViewFinishedFailed(iv_no_size));
        task.onBindTargetViewFinished(task_parm, avatar);
    }

    private Avatar getAvatar() throws IOException {
        InputStream input_stream = context.getResources().getAssets().open("kavatarloader1@126.com_size100_bytes");
        byte[] raw_bytes = new byte[input_stream.available()];
        input_stream.read(raw_bytes);
        input_stream.close();
        Avatar avatar = new Avatar(context, raw_bytes, AvatarLoadTaskConstant.EXIST_EMAIL1_SIZE_100_URL);
        return avatar;
    }

    private Avatar getAvatarWhoseByteIsNull() {
        return new Avatar(context, null, AvatarLoadTaskConstant.EXIST_EMAIL1_SIZE_100_URL);
    }

    private AvatarLoadTask getAvatarTask() {
        return new AvatarLoadTask(context, null);
    }


    class AssertOnBindImageViewFinished implements BindListener {

        private ImageView image_view;
        private String tag_expect;

        public AssertOnBindImageViewFinished(ImageView image_view, String tag_expect) {
            this.image_view = image_view;
            this.tag_expect = tag_expect;
        }

        @Override
        public void onBindFinished(RESULT_CODE result_code) {
            assertEquals("bind fail,result code is fail", RESULT_CODE.SUCCESS, result_code);
            assertNotNull("bind fail,image view is null", image_view);
            assertNotNull("bind fail,imgae view 's drawable is null", image_view.getDrawable());
            assertNotNull("bind fail,image view 's tag is null", image_view.getTag());
            assertEquals("bind fail,image view 's tag is wrong", tag_expect, image_view.getTag());
            Log.d(TAG, "bind finish");
        }
    }

    class AssertOnBindActionBarFinished implements BindListener {
        private ActionBar action_bar;

        public AssertOnBindActionBarFinished(ActionBar action_bar) {
            this.action_bar = action_bar;
        }

        @Override
        public void onBindFinished(RESULT_CODE result_code) {
            assertEquals("bind fail,result code is fail", RESULT_CODE.SUCCESS, result_code);
            assertNotNull("bind fail,image view is null", action_bar);
            Log.d(TAG, "bind finish");
        }
    }

    class AssertOnBindImageViewFinishedFailed implements BindListener {

        private ImageView image_view;

        public AssertOnBindImageViewFinishedFailed(ImageView image_view) {
            this.image_view = image_view;
        }

        @Override
        public void onBindFinished(RESULT_CODE result_code) {
            assertEquals("bind fail,result code is fail", RESULT_CODE.FAIL, result_code);
            assertNotNull("bind fail,image view is null", image_view);
            assertNull("bind fail,imgae view 's drawable is not null", image_view.getDrawable());
            assertNull("bind fail,image view 's tag is not null", image_view.getTag());
            Log.d(TAG, "bind finish");
        }
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
        taskParm.setDefaultAvatar(DefaultAvatar.CUSTOM_DEFAULT_AVATAR);
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


    class AvatarLoadTaskConstant {
        public static final String DOSENT_EXIST_EMAIL = "doesntexist@example.com";
        public static final String EXIST_EMAIL1 = "kavatarloader1@126.com";
        public static final String EXIST_EMAIL2 = "kavatarloader2@126.com";

        public static final String EXIST_EMAIL1_HASH_CODE = "79494f79a67ea995a8f128b8331b3306";
        public static final String EXIST_EMAIL2_HASH_CODE = "228ff1d1d1910536d99790691eb45882";
        public static final String DOSENT_EXIST_EMAIL_HASH_CODE = "628df4c8f4d7c3bed231df493987e808";

        public static final String EXIST_EMAIL1_DEFAULT_URL = "http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?d=404";
        public static final String EXIST_EMAIL2_DEFAULT_URL = "http://www.gravatar.com/avatar/228ff1d1d1910536d99790691eb45882.jpg?d=404";
        public static final String DOSENT_EXIST_EMAIL_DEFAULT_RUL = "http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=404";

        public static final String DOSENT_EXIST_EMAIL_DEFAULT_IMAGE_MONSTERID_RUL = "http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=monsterid";
        public static final String DOSENT_EXIST_EMAIL_DEFAULT_IMAGE_IDENTICON_RUL = "http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=identicon";
        public static final String DOSENT_EXIST_EMAIL_DEFAULT_IMAGE_WAVATAR_RUL = "http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=wavatar";

        public static final String EXIST_EMAIL1_SIZE_100_URL = "http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?s=100&d=404";
    }

}