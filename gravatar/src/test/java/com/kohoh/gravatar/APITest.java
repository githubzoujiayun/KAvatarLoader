package com.kohoh.gravatar;

import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static org.junit.Assert.assertEquals;

/**
 * Created by kohoh on 14-8-26.
 */
public class APITest {

    @Test
    public void testUrlEncoding() throws UnsupportedEncodingException {
        String raw_url = "http://example.com/images/avatar.jpg";
        String url_expect = "http%3A%2F%2Fexample.com%2Fimages%2Favatar.jpg";

        String url_actural = URLEncoder.encode(raw_url, "utf-8");

        assertEquals(url_expect, url_actural);
    }

    @Test
    public void testUrlDecoding() throws UnsupportedEncodingException {
        String raw_url = "http%3A%2F%2Fexample.com%2Fimages%2Favatar.jpg";
        String url_expect = "http://example.com/images/avatar.jpg";

        String url_actural = URLDecoder.decode(raw_url, "utf-8");

        assertEquals(url_expect, url_actural);
    }

//    @Test
//    public void downLoadAvatar() throws IOException {
//        Gravatar gravatar = new Gravatar();
//        byte[] qxw2012=gravatar.downloadByEmail("jgravatar@126.com");
//        saveBytes("jgravatar",qxw2012);
//    }

    private void saveBytes(String file_name, byte[] bytes) throws IOException {
        File filesDir = new File("/home/kohoh");
        File byteFile = new File(filesDir, file_name);
        if (byteFile.exists()) {
            byteFile.delete();
        }

        FileOutputStream outputStream = new FileOutputStream(byteFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        bufferedOutputStream.write(bytes);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }
}
