package com.kohoh.kavatarloader.test;

import android.content.res.AssetManager;
import android.test.AndroidTestCase;

import com.kohoh.gravatar.Gravatar;
import com.kohoh.gravatar.GravatarDefaultImage;
import com.kohoh.gravatar.GravatarRating;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kohoh on 14-8-1.
 */
public class GravatarTest extends AndroidTestCase {
    private Gravatar gravatar;

    static public final String TAG = GravatarTest.class.getSimpleName() + "_TAG";

    @Override
    protected void setUp() throws Exception {
        gravatar = new Gravatar();
    }


    //TestCase019 测试Gravatar#getUrl输入的email前后有空格的情况下能否正常工作
    public void testGetImageUrlTrimLeadingAndTrailingWhitespace() {
        assertEquals(GravatarConstant.EXIST_EMAIL1_DEFAULT_URL, gravatar.getUrl("kavatarloader1@126.com   "));
        assertEquals(GravatarConstant.EXIST_EMAIL1_DEFAULT_URL, gravatar.getUrl("   kavatarloader1@126.com"));
        assertEquals(GravatarConstant.EXIST_EMAIL1_DEFAULT_URL, gravatar.getUrl("   kavatarloader1@126.com   "));
    }
    //TestCase020 测试Gravatar#getUrl输入的email存在大写字母的情况下能否正常工作
    public void testGetImageUrlForceAllCharactersToLowerCase() {
        assertEquals(GravatarConstant.EXIST_EMAIL1_DEFAULT_URL, gravatar.getUrl("kaVatArloAdEr1@126.Com"));
        assertEquals(GravatarConstant.EXIST_EMAIL1_DEFAULT_URL, gravatar.getUrl("kAvaTarlOadeR1@126.COM"));
    }

    //TestCase021 测试Gravatar#getUrl能否正常工作
    public void testGetImageUrlDefaults() {
        assertEquals(GravatarConstant.DOSENT_EXIST_EMAIL_DEFAULT_URL,gravatar.getUrl(GravatarConstant.DOSENT_EXIST_EMAIL));
        assertEquals(GravatarConstant.EXIST_EMAIL1_DEFAULT_URL,gravatar.getUrl(GravatarConstant.EXIST_EMAIL1));
        assertEquals(GravatarConstant.EXIST_EMAIL2_DEFAULT_URL,gravatar.getUrl(GravatarConstant.EXIST_EMAIL2));
    }

    //TestCase021 测试Gravatar#getUrl设置size之后能否正确工作
    public void testGetImageUrlSize() {
        gravatar.setSize(300);
        assertEquals(GravatarConstant.DOSENT_EXIST_EMAIL_SIZE_300_URL,gravatar.getUrl(GravatarConstant.DOSENT_EXIST_EMAIL));
        gravatar.setSize(100);
        assertEquals(GravatarConstant.EXIST_EMAIL1_SIZE_100_URL,gravatar.getUrl(GravatarConstant.EXIST_EMAIL1));
        gravatar.setSize(200);
        assertEquals(GravatarConstant.EXIST_EMAIL2_SIZE_200_URL,gravatar.getUrl(GravatarConstant.EXIST_EMAIL2));
    }

    //TestCase022 测试Gravatar#getUrl设置rating之后能否正确工作
    public void testGetImageUrlRating() {
        gravatar.setRating(GravatarRating.PARENTAL_GUIDANCE_SUGGESTED);
        assertEquals(GravatarConstant.EXIST_EMAIL1_RATING_PARENTAL_GUIDANCE_SUGGESTED_URL,gravatar.getUrl(GravatarConstant.EXIST_EMAIL1));
        gravatar.setRating(GravatarRating.XPLICIT);
        assertEquals(GravatarConstant.EXIST_EMAIL2_RATING_XPLICIT_URL,gravatar.getUrl(GravatarConstant.EXIST_EMAIL2));
    }

    //TestCase023 测试Gravatar#getUrl设置deafult_image之后能否正确工作
    public void testGetImageUrlDefaultImage() {
        gravatar.setDefaultImage(GravatarDefaultImage.GRAVATAR_ICON);
        assertEquals(GravatarConstant.EXIST_EMAIL1_DEFAULT_IMAGE_GRAVATAR_ICON_URL, gravatar.getUrl(GravatarConstant.EXIST_EMAIL1));
        gravatar.setDefaultImage(GravatarDefaultImage.WAVATAR);
        assertEquals(GravatarConstant.EXIST_EMAIL2_DEFAULT_IMAGE_WAVATAR_URL, gravatar.getUrl(GravatarConstant.EXIST_EMAIL2));
    }

    //TestCase024 测试Gravatar#getUrl设置deafult_image,size,rating之后能否正确工作
    public void testGetImageUrlCombined() {
        gravatar.setSize(123);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        assertEquals(GravatarConstant.EXIST_EMAIL2_123_GENERAL_AUDIENCES_IDENTICON_URL, gravatar.getUrl(GravatarConstant.EXIST_EMAIL2));
        gravatar.setSize(321);
        gravatar.setDefaultImage(GravatarDefaultImage.MONSTERID);
        gravatar.setRating(GravatarRating.RESTRICTED);
        assertEquals(GravatarConstant.EXIST_EMAIL1_321_RESTRICTED_MONSTERID_URL, gravatar.getUrl(GravatarConstant.EXIST_EMAIL1));
    }

    //TestCase024 测试Gravatar#download是否正常工作
    public void testDownload() throws IOException {
        gravatar.setSize(100);
        byte[] bytes_email1_size100_actural = gravatar.download(GravatarConstant.EXIST_EMAIL1);
        byte[] bytes_email2_size100_actural = gravatar.download(GravatarConstant.EXIST_EMAIL2);
        gravatar.setSize(200);
        byte[] bytes_email1_size200_actural = gravatar.download(GravatarConstant.EXIST_EMAIL1);
        byte[] bytes_email2_size200_actural = gravatar.download(GravatarConstant.EXIST_EMAIL2);

        AssetManager asset_manager = getContext().getAssets();
        InputStream inputStream;
        BufferedInputStream bufferedInputStream;

        inputStream = asset_manager.open(GravatarConstant.EXIST_EMAIL1_SIZE_100_BYTE_FILE_NAME);
        byte[] bytes_email1_size100_expect = new byte[inputStream.available()];
        bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.read(bytes_email1_size100_expect);

        inputStream = asset_manager.open(GravatarConstant.EXIST_EMAIL1_SIZE_200_BYTE_FILE_NAME);
        byte[] bytes_email1_size200_expect = new byte[inputStream.available()];
        bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.read(bytes_email1_size200_expect);

        inputStream = asset_manager.open(GravatarConstant.EXIST_EMAIL2_SIZE_100_BYTE_FILE_NAME);
        byte[] bytes_email2_size100_expect = new byte[inputStream.available()];
        bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.read(bytes_email2_size100_expect);

        inputStream = asset_manager.open(GravatarConstant.EXIST_EMAIL2_SIZE_200_BYTE_FILE_NAME);
        byte[] bytes_email2_size200_expect = new byte[inputStream.available()];
        bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.read(bytes_email2_size200_expect);

        assertNotNull("actural raw bytes is null", bytes_email1_size100_actural);
        assertNotNull("actural raw bytes is null", bytes_email1_size200_actural);
        assertNotNull("actural raw bytes is null", bytes_email2_size100_actural);
        assertNotNull("actural raw bytes is null", bytes_email2_size200_actural);

        assertNotNull("expect raw bytes is null", bytes_email1_size100_expect);
        assertNotNull("expect raw bytes is null", bytes_email1_size200_expect);
        assertNotNull("expect raw bytes is null", bytes_email2_size100_expect);
        assertNotNull("expect raw bytes is null", bytes_email2_size200_expect);

        assertTrue(isBytesEqual(bytes_email1_size100_actural, bytes_email1_size100_expect));
        assertTrue(isBytesEqual(bytes_email1_size200_actural, bytes_email1_size200_expect));
        assertTrue(isBytesEqual(bytes_email2_size100_actural, bytes_email2_size100_expect));
        assertTrue(isBytesEqual(bytes_email2_size200_actural, bytes_email2_size200_expect));
    }

    private boolean isBytesEqual(byte[] bytes1, byte[] bytes2) {
        if (bytes1.length != bytes2.length) {
            return false;
        }

        for (int i = 0; i < bytes1.length; i++) {
            if (bytes1[i] != bytes2[i]) {
                return false;
            }
        }

        return true;
    }

    //TestCase018 测试Gravatar#getUrlByHashCode能否正确提供url
    public void testGetUrlByHashCode() {
        testGetUrlByHashCode(GravatarConstant.EXIST_EMAIL1_HASH_CODE, GravatarConstant.EXIST_EMAIL1_DEFAULT_URL);
        testGetUrlByHashCode(GravatarConstant.EXIST_EMAIL2_HASH_CODE, GravatarConstant.EXIST_EMAIL2_DEFAULT_URL);

        gravatar.setSize(100);
        testGetUrlByHashCode(GravatarConstant.EXIST_EMAIL1_HASH_CODE, GravatarConstant.EXIST_EMAIL1_SIZE_100_URL);

        //TODO 增加Gravatar#reset功能，实现恢复初始化设置
        gravatar = new Gravatar();
        gravatar.setDefaultImage(GravatarDefaultImage.WAVATAR);
        testGetUrlByHashCode(GravatarConstant.EXIST_EMAIL2_HASH_CODE, GravatarConstant.EXIST_EMAIL2_DEFAULT_IMAGE_WAVATAR_URL);

        gravatar = new Gravatar();
        gravatar.setRating(GravatarRating.PARENTAL_GUIDANCE_SUGGESTED);
        testGetUrlByHashCode(GravatarConstant.EXIST_EMAIL1_HASH_CODE, GravatarConstant.EXIST_EMAIL1_RATING_PARENTAL_GUIDANCE_SUGGESTED_URL);
    }

    private void testGetUrlByHashCode(String hash_code, String url_expect) {
        String url_actural = gravatar.getUrlByHashCode(hash_code);
        assertEquals("url not equal", url_expect, url_actural);
    }

    //TestCase25 测试Gravatar#downloadByHashCode能否正常运行
    public void testDownloadByHashCode() throws IOException {
        gravatar.setSize(100);
        testDownloadByHashCode(GravatarConstant.EXIST_EMAIL1_HASH_CODE, GravatarConstant.EXIST_EMAIL1_SIZE_100_BYTE_FILE_NAME);
        testDownloadByHashCode(GravatarConstant.EXIST_EMAIL2_HASH_CODE, GravatarConstant.EXIST_EMAIL2_SIZE_100_BYTE_FILE_NAME);
        gravatar.setSize(200);
        testDownloadByHashCode(GravatarConstant.EXIST_EMAIL1_HASH_CODE, GravatarConstant.EXIST_EMAIL1_SIZE_200_BYTE_FILE_NAME);
        testDownloadByHashCode(GravatarConstant.EXIST_EMAIL2_HASH_CODE, GravatarConstant.EXIST_EMAIL2_SIZE_200_BYTE_FILE_NAME);
    }

    private void testDownloadByHashCode(String hash_code, String raw_gravatar_expect_file_name) throws IOException {
        byte[] raw_gravatar_actural = gravatar.downloadByHashCode(hash_code);

        AssetManager asset_manager = getContext().getAssets();
        InputStream inputStream;
        BufferedInputStream bufferedInputStream;

        inputStream = asset_manager.open(raw_gravatar_expect_file_name);
        byte[] raw_gravatar_expect = new byte[inputStream.available()];
        bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.read(raw_gravatar_expect);

        assertNotNull("raw_gravatar_actural is null", raw_gravatar_actural);
        assertNotNull("raw_gravatar_expect is null", raw_gravatar_expect);
        assertTrue("raw_gravatar not equals,raw_gravatar_expect_file_name="+raw_gravatar_expect_file_name, isBytesEqual(raw_gravatar_actural, raw_gravatar_expect));
    }

}
