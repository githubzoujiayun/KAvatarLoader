package com.kohoh.KAvatarLoader.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.kohoh.KAvatarLoader.example.R;
import com.kohoh.kavatarloader.KAvatarLoader;

public class SingleBindViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_bind_view);
        KAvatarLoader avatarLoader = new KAvatarLoader(this);
        ImageView imageView = (ImageView) findViewById(R.id.iv_avatar);
        String email = "qxw2012@hotmail.com";

        avatarLoader.bind(imageView, email, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_bind_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
