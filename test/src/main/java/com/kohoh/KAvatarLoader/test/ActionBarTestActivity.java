package com.kohoh.KAvatarLoader.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class ActionBarTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_test);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getActionBar().setLogo(R.drawable.banner);
        getActionBar().setLogo(R.drawable.banner_vertical);

//        getActionBar().setLogo(R.drawable.logo100);

//        ImageView iv_icon_and_logo = (ImageView) findViewById(android.R.id.home);
//        if (iv_icon_and_logo != null) {
//            iv_icon_and_logo.setImageDrawable(getResources().getDrawable(R.drawable.icon100));
//        } else {
//            Log.e("kohoh_tag", "iv_icon_and_logo is null");
//        }

    }
}
