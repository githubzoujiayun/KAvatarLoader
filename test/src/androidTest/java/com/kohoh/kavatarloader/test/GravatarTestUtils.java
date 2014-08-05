package com.kohoh.kavatarloader.test;

import android.content.Context;
import android.content.res.AssetManager;

import junit.framework.TestCase;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kohoh on 14-8-5.
 */
public class GravatarTestUtils{

    private Context context;

    public GravatarTestUtils(Context context) {
        this.context = context;
    }

    public byte[] loadExpectRawGravatar(String raw_gravatar_expect_file_name) throws IOException {
        AssetManager asset_manager = context.getAssets();
        InputStream inputStream;
        BufferedInputStream bufferedInputStream;

        inputStream = asset_manager.open(raw_gravatar_expect_file_name);
        byte[] raw_gravatar_expect = new byte[inputStream.available()];
        bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.read(raw_gravatar_expect);

        return raw_gravatar_expect;
    }

    public void assertRawGravatarEquals(byte[] raw_gravatar_expect, byte[] raw_gravatar_actural) {
        TestCase.assertNotNull("raw_gravatar_actural is null", raw_gravatar_actural);
        TestCase.assertNotNull("raw_gravatar_expect is null", raw_gravatar_expect);

        boolean result = true;

        if (raw_gravatar_actural.length != raw_gravatar_expect.length) {
            result = false;
        }

        for (int i = 0; i < raw_gravatar_actural.length; i++) {
            if (raw_gravatar_actural[i] != raw_gravatar_expect[i]) {
                result = false;
            }
        }

        TestCase.assertTrue("raw_gravatar not equals", result);
    }
}
