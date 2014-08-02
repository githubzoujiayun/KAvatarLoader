package com.kohoh.APITest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.ImageView;

import com.kohoh.KAvatarLoader.example.R;
import com.kohoh.KAvatarLoader.example.SingleBindViewActivity;

/**
 * Created by kohoh on 14-8-2.
 */
public class ImageViewTest extends ActivityInstrumentationTestCase2<SingleBindViewActivity> {

    public ImageViewTest() {
        super(SingleBindViewActivity.class);
    }

    private Activity activity;
    private ImageView iv_avatar_no_size;
    private ImageView iv_avatar_small_size;
    private ImageView iv_avatar_medium_size;
    private ImageView iv_avatar_large_size;
    private ImageView iv_avatar_xlarge_size;
    private ImageView iv_avatar_actionbar_size;

    static public final String TAG = ImageViewTest.class.getSimpleName() + "_TAG";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        iv_avatar_no_size = (ImageView) activity.findViewById(R.id.iv_avatar_no_size);
        iv_avatar_small_size = (ImageView) activity.findViewById(R.id.iv_avatar_small_size);
        iv_avatar_medium_size = (ImageView) activity.findViewById(R.id.iv_avatar_medium_size);
        iv_avatar_large_size = (ImageView) activity.findViewById(R.id.iv_avatar_large_size);
        iv_avatar_xlarge_size = (ImageView) activity.findViewById(R.id.iv_avatar_xlarge_size);
        iv_avatar_actionbar_size = (ImageView) activity.findViewById(R.id.iv_avatar_actionbar_size);
    }

    public void testImageSize() {
        printSize(iv_avatar_no_size);
        printSize(iv_avatar_small_size);
        printSize(iv_avatar_medium_size);
        printSize(iv_avatar_large_size);
        printSize(iv_avatar_xlarge_size);
        printSize(iv_avatar_actionbar_size);
    }

    private void printSize(ImageView view) {
        if (view != null) {
            Log.d(TAG, "width= " + view.getWidth());
            Log.d(TAG, "height= " + view.getHeight());
            Log.d(TAG, "measuredWidth= " + view.getMeasuredWidth());
            Log.d(TAG, "measuredHeight= " + view.getMeasuredHeight());
            Log.d(TAG, "------------------------------------------");
        } else {
            Log.e(TAG, "view is null");
        }
    }
}
