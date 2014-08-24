package com.kohoh.KAvatarLoader.example;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.kohoh.kavatarloader.DefaultAvatar;
import com.kohoh.kavatarloader.KAvatarLoader;


public class LoadAvatarByCachedOrSavedActivity extends ActionBarActivity {

    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageView iv6;
    private ImageView iv7;
    private KAvatarLoader avatar_loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_avatar_by_cached_or_saved);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv5 = (ImageView) findViewById(R.id.iv5);
        iv6 = (ImageView) findViewById(R.id.iv6);
        iv7 = (ImageView) findViewById(R.id.iv7);

        avatar_loader = new KAvatarLoader(this);
        avatar_loader.setDefaultAvatar(DefaultAvatar.BLANK);
//        avatar_loader.setCustomSavedAvatarsDir(this.getExternalCacheDir());
        avatar_loader.setUseCachedAvatar(false);
//        avatar_loader.setUseSavedAvatar(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        avatar_loader.bindImageViewByEmail(iv1, "hagonzalez94@gmail.com", null);
        avatar_loader.bindImageViewByEmail(iv2, "jfeinstein10@gmail.com", null);
        avatar_loader.bindImageViewByEmail(iv3, "chris@chrisjenx.co.uk", null);
        avatar_loader.bindImageViewByEmail(iv4, "hagonzalez94@gmail.com", null);
        avatar_loader.bindImageViewByEmail(iv5, "jfeinstein10@gmail.com", null);
        avatar_loader.bindImageViewByEmail(iv6, "chris@chrisjenx.co.uk", null);
        avatar_loader.bindImageViewByEmail(iv7, "hagonzalez94@gmail.com", null);
    }
}
