package com.kohoh.KAvatarLoader.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.kohoh.kavatarloader.KAvatarLoader;

public class SingleBindViewActivity extends Activity {

    private ImageView iv_avatar_no_size;
    private ImageView iv_avatar_small_size;
    private ImageView iv_avatar_medium_size;
    private ImageView iv_avatar_large_size;
    private ImageView iv_avatar_xlarge_size;
    private ImageView iv_avatar_actionbar_size;
    private KAvatarLoader avatarLoader;
    static final private String email = "info@ralfebert.de";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_bind_view);
        avatarLoader = new KAvatarLoader(this);
        iv_avatar_no_size = (ImageView) findViewById(R.id.iv_avatar_no_size);
        iv_avatar_small_size = (ImageView) findViewById(R.id.iv_avatar_small_size);
        iv_avatar_medium_size = (ImageView) findViewById(R.id.iv_avatar_medium_size);
        iv_avatar_large_size = (ImageView) findViewById(R.id.iv_avatar_large_size);
        iv_avatar_xlarge_size = (ImageView) findViewById(R.id.iv_avatar_xlarge_size);
        iv_avatar_actionbar_size = (ImageView) findViewById(R.id.iv_avatar_actionbar_size);
    }

    @Override
    protected void onResume() {
        super.onResume();

        avatarLoader.bindImageViewByEmail(iv_avatar_no_size, email, null);
        avatarLoader.bindImageViewByEmail(iv_avatar_small_size, email, null);
        avatarLoader.bindImageViewByEmail(iv_avatar_medium_size, email, null);
        avatarLoader.bindImageViewByEmail(iv_avatar_large_size, email, null);
        avatarLoader.bindImageViewByEmail(iv_avatar_xlarge_size, email, null);
        avatarLoader.bindImageViewByEmail(iv_avatar_actionbar_size, email, null);
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
