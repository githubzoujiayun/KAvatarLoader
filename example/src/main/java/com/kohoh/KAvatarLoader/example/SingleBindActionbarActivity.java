package com.kohoh.KAvatarLoader.example;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import com.kohoh.kavatarloader.DefaultAvatar;
import com.kohoh.kavatarloader.KAvatarLoader;


public class SingleBindActionbarActivity extends Activity {

    private KAvatarLoader avatar_loader;
    private ActionBar action_bar;
    static final public String email = "kavatarloader1@126.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_bind_actionbar);

        avatar_loader = new KAvatarLoader(this);
        action_bar = getActionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();

        avatar_loader.setDefaultAvatar(DefaultAvatar.MONSTERID);
        avatar_loader.bindActionBarByEmail(action_bar, email, null);
    }
}
