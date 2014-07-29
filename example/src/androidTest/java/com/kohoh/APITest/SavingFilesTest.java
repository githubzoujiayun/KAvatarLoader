package com.kohoh.APITest;

import android.content.Context;
import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by kohoh on 14-7-29.
 */
public class SavingFilesTest extends AndroidTestCase {

    private Context context;
    static final private String TAG = SavingFilesTest.class.getSimpleName();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = getContext();
    }

    public void testGetFilesDir() throws IOException {
        File root = context.getFilesDir();
        logFileInf(root,"root");
    }

    public void testGetCacheDir() throws IOException {
        File cacheDir = context.getCacheDir();
        logFileInf(cacheDir,"cacheDir");

    }

    public void testGetExternal() throws IOException {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.d(TAG, "External storage exists");
            File externalRoot = context.getExternalFilesDir(null);
            logFileInf(externalRoot, "externalRoot");
        } else {
            Log.d(TAG, "External storage not exists");
        }
        Log.d(TAG, "----------------------------------");
    }

    public void logFileInf(File file,String file_name) throws IOException {
        if (file.exists()) {
            Log.d(TAG, file_name+"exists");

            String absolutePath = file.getAbsolutePath();
            Log.d(TAG, "AbsolutePath=" + absolutePath);

            String canonicalPath = file.getCanonicalPath();
            Log.d(TAG, "CanonicalPath=" + canonicalPath);

            String path = file.getPath();
            Log.d(TAG, "Path=" + path);

            String name = file.getName();
            Log.d(TAG, "Name=" + name);

            String parent = file.getParent();
            Log.d(TAG, "Parent=" + parent);
        } else {
            Log.d(TAG, file_name + "not exists");
        }
        Log.d(TAG, "-------------------------------");
    }
}
