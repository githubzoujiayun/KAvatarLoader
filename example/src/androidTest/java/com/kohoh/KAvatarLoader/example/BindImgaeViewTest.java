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
public class BindImgaeViewTest extends ActivityInstrumentationTestCase2<SingleBindViewActivity> {
    public BindImgaeViewTest() {
        super(SingleBindViewActivity.class);
    }

    private Activity activity;
    private KAvatarLoader avatarLoader;
    static String TAG = BindImgaeViewTest.class.getSimpleName();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        avatarLoader = new KAvatarLoader(activity);
    }

    //TestCase001 给KAvatarLoader#bind(),穿入一个ImageView和一个Email地址，
    // 根据这个Email地址加载正确的头像到ImageV上
    public void testBindImageViewByEmail() throws InterruptedException {
        final ImageView img_avatar = (ImageView) activity.findViewById(R.id.iv_avatar_no_size);
        final String email = "qxw2012@hotamil.com";

        avatarLoader.bind(img_avatar, email, new BindListener() {
            @Override
            public void onBindFinished() {
                Drawable drawable = img_avatar.getDrawable();
                assertNotNull("drawable is null", drawable);

                String id = (String) img_avatar.getTag();
                assertEquals("bind failed", email, id);
            }
        });
    }
}
