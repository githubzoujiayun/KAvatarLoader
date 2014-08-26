package com.kohoh.gravatar;

import com.kohoh.gravatar.GravatarDefaultImage.GravatarProvideImage;

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

    public void testGravatraAPI() {
        Gravatar gravatar = new Gravatar();

        //设置头像的大小
        // 单位为像素，默认大小为80。有效范围为1～2048。
        gravatar.setSize(100);
        //设置默认头像
        //Gravtar提供了GRAVATAR_ICON，MYSTERY_MEN，IDENTICON，WAVATAR等默认头像。如果所要加载的头像不存在，就会返回默认头像。
        //默认头像默认被设置为HTTP-404
        gravatar.setDefaultImage(GravatarProvideImage.MYSTERY_MEN);
        //设置自定义的默认头像。如果所要加载的头像不存在，就会根据提供的URL加载对应的默认头像。
        //默认头像对应的URL需要满足一下条件
        //1、图片必须是能够公开访问的。
        //2、只能使用HTTP和HTTPS协议，端口分别对应80和443
        //3、图片只能那是jpg，jpeg，gif，png这几种格式
        //4、不能包含请求代码
        gravatar.setDefaultImage("http://example.com/images/avatar.jpg");
        //设置头像等级
        //Gravatar将头像定义为GENERAL_AUDIENCES，PARENTAL_GUIDANCE_SUGGESTED，RESTRICTED，XPLICIT四个等级
        //默认的等级为GENERAL_AUDIENCES。
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        //设置是否强制加载默认头像
        gravatar.setForceLoadDefaultImage(false);
        //设置Gravtar的服务器地址
        //默认的地址为 GRAVATAR_GENERAL_SERVER http://www.gravatar.com
        //但是如果你需要使用HTTPS加载头像，那么你可以使用 GRAVATAR_SECURE_SERVERR https://secure.gravatar.com 更安全的加载头像
        gravatar.setGravatarServer(GravatarServer.GRAVATAR_GENERAL_SERVER);

        byte[] gravatar_raw = gravatar.downloadByEmail("jgravatar@126.com");
    }
}
