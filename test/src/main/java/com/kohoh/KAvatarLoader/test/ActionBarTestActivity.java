package com.kohoh.KAvatarLoader.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;


public class ActionBarTestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_test);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        getActionBar().setLogo(R.drawable.banner);
//        getActionBar().setLogo(R.drawable.banner_vertical);
//
//        getActionBar().setLogo(R.drawable.logo20);
//
//        int height = getActionBar().getHeight();
//        Log.d("kohoh_tag", "ActionBar height= " + height);

        getSupportActionBar().setLogo(R.drawable.logo100);

        Log.d("kohoh_tag", "actionbar size= " + this.getResources().getDimensionPixelSize(R.dimen.default_avatar_size));


        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ImageView iv_icon_and_logo = (ImageView) findViewById(android.R.id.home);
                Log.d("kohoh_tag", "ActionBar logo height == " + iv_icon_and_logo.getHeight());
                int height = getActionBar().getHeight();
                Log.d("kohoh_tag", "ActionBar height= " + height);
                return null;
            }
        }.execute();


//        getActionBar().setLogo(R.drawable.logo100);

//        ImageView iv_icon_and_logo = (ImageView) findViewById(android.R.id.home);
//        if (iv_icon_and_logo != null) {
//            iv_icon_and_logo.setImageDrawable(getResources().getDrawable(R.drawable.icon100));
//        } else {
//            Log.e("kohoh_tag", "iv_icon_and_logo is null");
//        }

    }


}
