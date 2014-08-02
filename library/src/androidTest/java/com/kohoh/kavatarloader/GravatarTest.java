package com.kohoh.kavatarloader;

import android.test.AndroidTestCase;
import android.util.Log;

import junit.framework.TestCase;

import com.kohoh.gravatar.Gravatar;
import com.kohoh.gravatar.GravatarDefaultImage;
import com.kohoh.gravatar.GravatarRating;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kohoh on 14-8-1.
 */
public class GravatarTest extends AndroidTestCase {
    private Gravatar gravatar;

    static public final String TAG = GravatarTest.class.getSimpleName() + "_TAG";

    private final String DOSENT_EXIST_EMAIL = "doesntexist@example.com";
    private final String EXIST_EMAIL1 = "kavatarloader1@126.com";
    private final String EXIST_EMAIL2 = "kavatarloader2@126.com";


    @Override
    protected void setUp() throws Exception {
        gravatar = new Gravatar();
    }

    public void testExample() {
//        gravatar.setSize(100);
//        gravatar.setRating(GravatarRating.RESTRICTED);

        String url_doesnt_exit_email = gravatar.getUrl(DOSENT_EXIST_EMAIL);
        String url_exist_email1 = gravatar.getUrl(EXIST_EMAIL1);
        String url_exist_email2 = gravatar.getUrl(EXIST_EMAIL2);

        Log.d(TAG, "url_doesnt_exit_email= " + url_doesnt_exit_email);
        Log.d(TAG, "url_exist_email1= " + url_exist_email1);
        Log.d(TAG, "url_exist_email2= " + url_exist_email2);

        gravatar = new Gravatar();
        String url_exist_email1_with_whitespace1 = gravatar.getUrl("kavatarloader1@126.com   ");
        String url_exist_email1_with_whitespace2 = gravatar.getUrl("  kavatarloader1@126.com");
        String url_exist_email1_with_hith_case1 = gravatar.getUrl("kaVatArlOader1@126.com");
        String url_exist_email1_with_hith_case2 = gravatar.getUrl("KavaTarloaDer1@126.cOm");

        Log.d(TAG, "url_exist_email1_with_hith_case1= " + url_exist_email1_with_hith_case1);
        Log.d(TAG, "url_exist_email1_with_hith_case2= " + url_exist_email1_with_hith_case2);
        Log.d(TAG, "url_exist_email1_with_whitespace1= " + url_exist_email1_with_whitespace1);
        Log.d(TAG, "url_exist_email1_with_whitespace2= " + url_exist_email1_with_whitespace2);


        gravatar.setSize(100);
        String url_exist_mail1_with_size100 = gravatar.getUrl(EXIST_EMAIL1);
        gravatar.setSize(321);
        String url_exist_mail2_with_size321 = gravatar.getUrl(EXIST_EMAIL2);

        Log.d(TAG, "url_exist_mail1_with_size100= " + url_exist_mail1_with_size100);
        Log.d(TAG, "url_exist_mail2_with_size321= " + url_exist_mail2_with_size321);

        gravatar.setRating(GravatarRating.XPLICIT);
        String url_exist_mail1_with_rating_xplicit = gravatar.getUrl(EXIST_EMAIL1);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        String url_exist_mail1_with_rating_general_audiences = gravatar.getUrl(EXIST_EMAIL1);
        gravatar.setRating(GravatarRating.RESTRICTED);
        String url_exist_mail1_with_rating_restricted = gravatar.getUrl(EXIST_EMAIL1);
        gravatar.setRating(GravatarRating.PARENTAL_GUIDANCE_SUGGESTED);
        String url_exist_mail1_with_rating_parental_guidance_suggested = gravatar.getUrl(EXIST_EMAIL1);

        Log.d(TAG, "url_exist_mail1_with_rating_xplicit= " + url_exist_mail1_with_rating_xplicit);
        Log.d(TAG, "url_exist_mail1_with_rating_restricted= " + url_exist_mail1_with_rating_restricted);
        Log.d(TAG, "url_exist_mail1_with_rating_general_audiences= " + url_exist_mail1_with_rating_general_audiences);
        Log.d(TAG, "url_exist_mail1_with_rating_parental_guidance_suggested= " + url_exist_mail1_with_rating_parental_guidance_suggested);

        gravatar = new Gravatar();
        gravatar.setDefaultImage(GravatarDefaultImage.GRAVATAR_ICON);
        String url_exist_mail1_with_default_image_gravatar_icon = gravatar.getUrl(EXIST_EMAIL1);
        Log.d(TAG, "url_exist_mail1_with_default_image_gravatar_icon= " + url_exist_mail1_with_default_image_gravatar_icon);

        gravatar = new Gravatar();
        gravatar.setSize(123);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        gravatar.setRating(GravatarRating.XPLICIT);
        String url_exist_mail1_combined = gravatar.getUrl(EXIST_EMAIL1);
        Log.d(TAG, "url_exist_mail1_combined= " + url_exist_mail1_combined);


    }

    public void testGetImageUrlTrimLeadingAndTrailingWhitespace() {
        assertEquals("http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?d=404", gravatar.getUrl("kavatarloader1@126.com   "));
        assertEquals("http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?d=404", gravatar.getUrl("   kavatarloader1@126.com"));
        assertEquals("http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?d=404", gravatar.getUrl("   kavatarloader1@126.com   "));
    }

    public void testGetImageUrlForceAllCharactersToLowerCase() {
        assertEquals("http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?d=404", gravatar.getUrl("kaVatArloAdEr1@126.Com"));
        assertEquals("http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?d=404", gravatar.getUrl("kAvaTarlOadeR1@126.COM"));

    }

    public void testGetImageUrlDefaults() {
        assertEquals("http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=404",
                gravatar.getUrl(DOSENT_EXIST_EMAIL));
        assertEquals("http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?d=404",
                gravatar.getUrl(EXIST_EMAIL1));
        assertEquals("http://www.gravatar.com/avatar/228ff1d1d1910536d99790691eb45882.jpg?d=404",
                gravatar.getUrl(EXIST_EMAIL2));
    }

    public void testGetImageUrlSize() {
        gravatar.setSize(100);
        assertEquals("http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?s=100&d=404",
                gravatar.getUrl(DOSENT_EXIST_EMAIL));
        gravatar.setSize(123);
        assertEquals("http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?s=123&d=404",
                gravatar.getUrl(EXIST_EMAIL1));
        gravatar.setSize(321);
        assertEquals("http://www.gravatar.com/avatar/228ff1d1d1910536d99790691eb45882.jpg?s=321&d=404",
                gravatar.getUrl(EXIST_EMAIL2));
    }

    //TODO 测试ImageSize的范围1-2048
    //TODO 测试自定义DefaultImage
    //TODO 增加DefalutImage的测试，因为Gravatar增加了DefaultImage
    //TODO 增加DefaultImage的force load 测试
    //TODO 增加对ssl的加载方式 也就是使用https://secure.gravatar.com/加载。


    public void testGetImageUrlRating() {
        gravatar.setRating(GravatarRating.PARENTAL_GUIDANCE_SUGGESTED);
        assertEquals("http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?r=pg&d=404",
                gravatar.getUrl(DOSENT_EXIST_EMAIL));
        gravatar.setRating(GravatarRating.RESTRICTED);
        assertEquals("http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?r=r&d=404",
                gravatar.getUrl(EXIST_EMAIL1));
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        assertEquals("http://www.gravatar.com/avatar/228ff1d1d1910536d99790691eb45882.jpg?d=404",
                gravatar.getUrl(EXIST_EMAIL2));
        gravatar.setRating(GravatarRating.XPLICIT);
        assertEquals("http://www.gravatar.com/avatar/228ff1d1d1910536d99790691eb45882.jpg?r=x&d=404",
                gravatar.getUrl(EXIST_EMAIL2));
    }

    public void testGetImageUrlDefaultImage() {
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        assertEquals("http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=identicon", gravatar.getUrl(DOSENT_EXIST_EMAIL));
        gravatar.setDefaultImage(GravatarDefaultImage.GRAVATAR_ICON);
        assertEquals("http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg", gravatar.getUrl(EXIST_EMAIL1));
        gravatar.setDefaultImage(GravatarDefaultImage.MONSTERID);
        assertEquals("http://www.gravatar.com/avatar/228ff1d1d1910536d99790691eb45882.jpg?d=monsterid", gravatar.getUrl(EXIST_EMAIL2));
        gravatar.setDefaultImage(GravatarDefaultImage.WAVATAR);
        assertEquals("http://www.gravatar.com/avatar/228ff1d1d1910536d99790691eb45882.jpg?d=wavatar", gravatar.getUrl(EXIST_EMAIL2));
        gravatar.setDefaultImage(GravatarDefaultImage.HTTP_404);
        assertEquals("http://www.gravatar.com/avatar/228ff1d1d1910536d99790691eb45882.jpg?d=404", gravatar.getUrl(EXIST_EMAIL2));

    }

    public void testGetImageUrlCombined() {
        gravatar.setSize(123);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        gravatar.setRating(GravatarRating.XPLICIT);
        assertEquals("http://www.gravatar.com/avatar/228ff1d1d1910536d99790691eb45882.jpg?s=123&r=x&d=identicon", gravatar.getUrl(EXIST_EMAIL2));
        gravatar.setSize(321);
        gravatar.setDefaultImage(GravatarDefaultImage.MONSTERID);
        gravatar.setRating(GravatarRating.RESTRICTED);
        assertEquals("http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?s=321&r=r&d=monsterid", gravatar.getUrl(EXIST_EMAIL1));

    }

    public void testDownload() {
        byte[] bytes = gravatar.download(EXIST_EMAIL1);
        assertTrue("content present", bytes.length > 100);

        assertNull("null for no gravatar by default", gravatar.download(DOSENT_EXIST_EMAIL));

        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        bytes = gravatar.download(DOSENT_EXIST_EMAIL);
        assertTrue("content present", bytes.length > 100);
    }

    public void testAvatarSize() {
        sameAvatarDiffSize(EXIST_EMAIL1);
        sameAvatarDiffSize(EXIST_EMAIL2);
        sameAvatarDiffSize(DOSENT_EXIST_EMAIL);

        sameAvatarDiffDefaultAvatar(EXIST_EMAIL1);
        sameAvatarDiffDefaultAvatar(EXIST_EMAIL2);
        sameAvatarDiffDefaultAvatar(DOSENT_EXIST_EMAIL);

        sameAvatarDiffRating(EXIST_EMAIL1);
        sameAvatarDiffRating(EXIST_EMAIL2);
        sameAvatarDiffRating(DOSENT_EXIST_EMAIL);

    }

    private void sameAvatarDiffDefaultAvatar(String email) {
        Log.d(TAG, email);
        gravatar.setSize(100);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        byte[] bytes;

        gravatar.setDefaultImage(GravatarDefaultImage.GRAVATAR_ICON);
        bytes = gravatar.download(email);
        Log.d(TAG, "defaultImage=GRAVATAR_ICON bytes length= " + bytes.length);

        gravatar.setDefaultImage(GravatarDefaultImage.MONSTERID);
        bytes = gravatar.download(email);
        Log.d(TAG, "defaultImage=MONSTERID bytes length= " + bytes.length);

        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        bytes = gravatar.download(email);
        Log.d(TAG, "defaultImage=IDENTICON bytes length= " + bytes.length);

        Log.d(TAG, "----------------------------");
    }

    private void sameAvatarDiffRating(String email) {

        Log.d(TAG, email);
        gravatar.setSize(100);
        gravatar.setDefaultImage(GravatarDefaultImage.GRAVATAR_ICON);
        byte[] bytes;

        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        bytes = gravatar.download(email);
        Log.d(TAG, "rating=GENERAL_AUDIENCES bytes length= " + bytes.length);

        gravatar.setRating(GravatarRating.PARENTAL_GUIDANCE_SUGGESTED);
        bytes = gravatar.download(email);
        Log.d(TAG, "rating=PARENTAL_GUIDANCE_SUGGESTEDS bytes length= " + bytes.length);

        gravatar.setRating(GravatarRating.XPLICIT);
        bytes = gravatar.download(email);
        Log.d(TAG, "rating=XPLICIT bytes length= " + bytes.length);

        Log.d(TAG, "----------------------------");

    }

    private void sameAvatarDiffSize(String email) {
        byte[] bytes;

        Log.d(TAG, email);
        gravatar.setDefaultImage(GravatarDefaultImage.GRAVATAR_ICON);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);

        gravatar.setSize(50);
        bytes = gravatar.download(email);
        Log.d(TAG, "size=50 bytes length= " + bytes.length);

        gravatar.setSize(100);
        bytes = gravatar.download(email);
        Log.d(TAG, "size=100 bytes length= " + bytes.length);

        gravatar.setSize(200);
        bytes = gravatar.download(email);
        Log.d(TAG, "size= 200 bytes length= " + bytes.length);

        Log.d(TAG, "----------------------------");
    }

    public void testAvatarEqual() throws IOException {
        byte[] bytes1 = gravatar.download(EXIST_EMAIL1);
        byte[] bytes2 = gravatar.download(EXIST_EMAIL2);

        Log.d(TAG, bytes1.equals(bytes2) ? "equals" : "not equals");

        Log.d(TAG, "bytes1 hash= " + bytes1.hashCode());
        Log.d(TAG, "bytes2 hash= " + bytes2.hashCode());

        Log.d(TAG, "bytes1 length= " + bytes1.length);
        Log.d(TAG, "bytes2 length= " + bytes2.length);

        Log.d(TAG, "bytes1= " + bytes1.toString());
        Log.d(TAG, "bytes2= " + bytes2.toString());
    }

//    public void testDonloadAndSave() throws IOException {
//        gravatar.setSize(100);
//        byte[] bytes_email1_size100 = gravatar.download(EXIST_EMAIL1);
//        byte[] bytes_email2_size100 = gravatar.download(EXIST_EMAIL2);
//        gravatar.setSize(200);
//        byte[] bytes_email1_size200 = gravatar.download(EXIST_EMAIL1);
//        byte[] bytes_email2_size200 = gravatar.download(EXIST_EMAIL2);
//
//        saveBytes(EXIST_EMAIL1 + "_size100_bytes", bytes_email1_size100);
//        saveBytes(EXIST_EMAIL1 + "_size200_bytes", bytes_email1_size200);
//        saveBytes(EXIST_EMAIL2 + "_size100_bytes", bytes_email2_size100);
//        saveBytes(EXIST_EMAIL2 + "_size200_bytes", bytes_email2_size200);
//    }

    private void saveBytes(String file_name, byte[] bytes) throws IOException {
        File filesDir = getContext().getFilesDir();
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
