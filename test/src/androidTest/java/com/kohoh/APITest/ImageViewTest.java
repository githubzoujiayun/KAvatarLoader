package com.kohoh.APITest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.kohoh.KAvatarLoader.test.R;
import com.kohoh.KAvatarLoader.test.KAvatarLoaderTestUseActivity;


/**
 * Created by kohoh on 14-8-2.
 */
public class ImageViewTest extends ActivityInstrumentationTestCase2<KAvatarLoaderTestUseActivity> {

    public ImageViewTest() {
        super(KAvatarLoaderTestUseActivity.class);
    }

    private Activity activity;
    private ImageView iv_size100;
    private ImageView iv_size200;

    static public final String TAG = ImageViewTest.class.getSimpleName() + "_TAG";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        iv_size100 = (ImageView) activity.findViewById(R.id.iv_size_100);
        iv_size200 = (ImageView) activity.findViewById(R.id.iv_size_200);
    }

    public void logImageSize() {
        printDpi();

        printSize(iv_size100);
        printSize(iv_size200);
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

    private void printDpi() {
        DisplayMetrics display_metrics = activity.getResources().getDisplayMetrics();
        int densityDpi = display_metrics.densityDpi;
        float density = display_metrics.density;

        Log.d(TAG, "densityDpi= " + densityDpi);
        Log.d(TAG, "density= " + density);
        Log.d(TAG, "------------------------------------");

    }
}
