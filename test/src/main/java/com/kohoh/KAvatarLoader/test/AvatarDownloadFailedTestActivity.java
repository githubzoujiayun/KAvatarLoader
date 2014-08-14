package com.kohoh.KAvatarLoader.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.kohoh.gravatar.Gravatar;


public class AvatarDownloadFailedTestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_download_failed_test);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Gravatar gravatar = new Gravatar();
        gravatar.downloadByEmail("doesntexist@example.com");
    }
}
