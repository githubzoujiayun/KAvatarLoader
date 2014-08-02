package com.kohoh.KAvatarLoader.example;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

import com.kohoh.kavatarloader.BindListener;
import com.kohoh.kavatarloader.KAvatarLoader;

/**
 * Created by kohoh on 14-7-28.
 */
public class KAvatarLoaderTest extends ActivityInstrumentationTestCase2<SingleBindViewActivity> {
    public KAvatarLoaderTest() {
        super(SingleBindViewActivity.class);
    }

    private Activity activity;
    private KAvatarLoader avatarLoader;
    static String TAG = KAvatarLoaderTest.class.getSimpleName();

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
        avatarLoader = new KAvatarLoader(activity);

        iv_avatar_no_size = (ImageView) activity.findViewById(R.id.iv_avatar_no_size);
        iv_avatar_small_size = (ImageView) activity.findViewById(R.id.iv_avatar_small_size);
        iv_avatar_medium_size = (ImageView) activity.findViewById(R.id.iv_avatar_medium_size);
        iv_avatar_large_size = (ImageView) activity.findViewById(R.id.iv_avatar_large_size);
        iv_avatar_xlarge_size = (ImageView) activity.findViewById(R.id.iv_avatar_xlarge_size);
        iv_avatar_actionbar_size = (ImageView) activity.findViewById(R.id.iv_avatar_actionbar_size);
    }

    //TestCase001 给KAvatarLoader#bind(),穿入一个ImageView和一个Email地址，
    // 根据这个Email地址加载正确的头像到ImageV上
    public void testBindImageViewByEmail() throws InterruptedException {
        bindImageView(iv_avatar_no_size,GravatarConstant.EXIST_EMAIL1);
        bindImageView(iv_avatar_actionbar_size,GravatarConstant.EXIST_EMAIL1);
        bindImageView(iv_avatar_small_size,GravatarConstant.EXIST_EMAIL1);
        bindImageView(iv_avatar_medium_size,GravatarConstant.EXIST_EMAIL2);
        bindImageView(iv_avatar_large_size,GravatarConstant.EXIST_EMAIL2);
        bindImageView(iv_avatar_xlarge_size,GravatarConstant.EXIST_EMAIL2);
    }

    private void bindImageView(final ImageView imageView, String email) {
        avatarLoader.bind(imageView, email, new BindListener() {
            @Override
            public void onBindFinished() {
                Drawable drawable = imageView.getDrawable();
                assertNotNull("drawable is null", drawable);

                String tag = (String) imageView.getTag();
                assertEquals("bind failed", GravatarConstant.EXIST_EMAIL1, tag);
            }
        });
    }
}
