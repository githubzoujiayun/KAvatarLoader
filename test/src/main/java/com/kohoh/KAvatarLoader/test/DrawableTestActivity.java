package com.kohoh.KAvatarLoader.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.kohoh.kavatarloader.Avatar;
import com.kohoh.kavatarloader.KAvatarLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class DrawableTestActivity extends Activity {
    private ImageView iv_from_net;
    private ImageView iv_from_local;
    private KAvatarLoader avatar_loader;

    public static final String EXIST_EMAIL1_SIZE_100_URL = "http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?s=100&d=404";
    public static final String EXIST_EMAIL1_SIZE_100_BYTE_FILE_NAME = "kavatarloader1@126.com_size100_bytes";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_test);

        iv_from_local = (ImageView) findViewById(R.id.iv_from_local);
        iv_from_net = (ImageView) findViewById(R.id.iv_frome_net);

        avatar_loader = new KAvatarLoader(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                return  avatar_loader.loadAvatarByUrl(EXIST_EMAIL1_SIZE_100_URL,100);
            }

            @Override
            protected void onPostExecute(Object o) {
                Avatar avatar = (Avatar) o;

                BitmapDrawable drawable = new BitmapDrawable(getResources(), avatar.getBitmap());
                iv_from_net.setImageDrawable(drawable);

                Bitmap bitmap=BitmapFactory.decodeByteArray(avatar.getBytes(), 0, avatar.getBytes().length);
                iv_from_local.setImageBitmap(bitmap);

            }
        }.execute();


    }
}
