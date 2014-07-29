package com.kohoh.KAvatarLoader.example;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.ImageView;

import com.kohoh.kavatarloader.KAvatarLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by kohoh on 14-7-28.
 */
public class BindImgaeViewTest extends ActivityInstrumentationTestCase2<SingleBindViewActivity> {
    public BindImgaeViewTest() {
        super(SingleBindViewActivity.class);
    }

    private Activity activity;
    private File avatar1;
    static String TAG = BindImgaeViewTest.class.getSimpleName();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        avatar1 = new File(activity.getFilesDir().getPath() + "/avatar1.jpg");
        assertTrue("avatar1 noe exists", avatar1.exists());
    }

    public void testBindImageViewByEmail() {
        final ImageView img_avatar = (ImageView) activity.findViewById(R.id.iv_avatar);
        final String email = "qxw2012@hotamil.com";

        final KAvatarLoader loader = new KAvatarLoader(activity);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loader.bind(img_avatar, email);

                Drawable drawable=img_avatar.getDrawable();
                assertNotNull("drawable is null", drawable);

                String id = (String) img_avatar.getTag();
                assertEquals("bind failed", email, id);
            }
        });


    }
}
