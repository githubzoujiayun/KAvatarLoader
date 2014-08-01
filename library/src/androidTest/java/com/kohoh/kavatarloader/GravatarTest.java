package com.kohoh.kavatarloader;

import android.util.Log;

import junit.framework.TestCase;

import jgravatar.Gravatar;
import jgravatar.GravatarDefaultImage;
import jgravatar.GravatarRating;

/**
 * Created by kohoh on 14-8-1.
 */
public class GravatarTest extends TestCase {
    private Gravatar gravatar;

    static public final String TAG = GravatarTest.class.getSimpleName() + "_TAG";

    private final String DOSENT_EXIST_EMAIL = "doesntexist@example.com";
    private final String EXIST_EMAIL1 = "info@ralfebert.de";
    private final String EXIST_EMAIL2 = "qxw2012@hotmail.com";


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
        String url_exist_email1_with_whitespace1 = gravatar.getUrl("info@ralfebert.de   ");
        String url_exist_email1_with_whitespace2 = gravatar.getUrl("  info@ralfebert.de");
        String url_exist_email1_with_hith_case1 = gravatar.getUrl("INFO@ralfebert.DE");
        String url_exist_email1_with_hith_case2 = gravatar.getUrl("InFo@RaLfebeRt.dE");

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
        assertEquals("http://www.gravatar.com/avatar/fa8771dec9da9299afed9ffce70c2c18.jpg?d=404", gravatar.getUrl("info@ralfebert.de   "));
        assertEquals("http://www.gravatar.com/avatar/fa8771dec9da9299afed9ffce70c2c18.jpg?d=404", gravatar.getUrl("   info@ralfebert.de"));
        assertEquals("http://www.gravatar.com/avatar/fa8771dec9da9299afed9ffce70c2c18.jpg?d=404", gravatar.getUrl("   info@ralfebert.de   "));
    }

    public void testGetImageUrlForceAllCharactersToLowerCase() {
        assertEquals("http://www.gravatar.com/avatar/fa8771dec9da9299afed9ffce70c2c18.jpg?d=404", gravatar.getUrl("INFO@rAlFeBeRt.DE"));
        assertEquals("http://www.gravatar.com/avatar/fa8771dec9da9299afed9ffce70c2c18.jpg?d=404", gravatar.getUrl("info@ralFEBert.dE"));

    }

    public void testGetImageUrlDefaults() {
        assertEquals("http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=404",
                gravatar.getUrl(DOSENT_EXIST_EMAIL));
        assertEquals("http://www.gravatar.com/avatar/fa8771dec9da9299afed9ffce70c2c18.jpg?d=404",
                gravatar.getUrl(EXIST_EMAIL1));
        assertEquals("http://www.gravatar.com/avatar/93c7566a00a574b94e73565fa74bbbc9.jpg?d=404",
                gravatar.getUrl(EXIST_EMAIL2));
    }

    public void testGetImageUrlSize() {
        gravatar.setSize(100);
        assertEquals("http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?s=100&d=404",
                gravatar.getUrl(DOSENT_EXIST_EMAIL));
        gravatar.setSize(123);
        assertEquals("http://www.gravatar.com/avatar/fa8771dec9da9299afed9ffce70c2c18.jpg?s=123&d=404",
                gravatar.getUrl(EXIST_EMAIL1));
        gravatar.setSize(321);
        assertEquals("http://www.gravatar.com/avatar/93c7566a00a574b94e73565fa74bbbc9.jpg?s=321&d=404",
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
        assertEquals("http://www.gravatar.com/avatar/fa8771dec9da9299afed9ffce70c2c18.jpg?r=r&d=404",
                gravatar.getUrl(EXIST_EMAIL1));
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        assertEquals("http://www.gravatar.com/avatar/93c7566a00a574b94e73565fa74bbbc9.jpg?d=404",
                gravatar.getUrl(EXIST_EMAIL2));
        gravatar.setRating(GravatarRating.XPLICIT);
        assertEquals("http://www.gravatar.com/avatar/93c7566a00a574b94e73565fa74bbbc9.jpg?r=x&d=404",
                gravatar.getUrl(EXIST_EMAIL2));
    }

    public void testGetImageUrlDefaultImage() {
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        assertEquals("http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=identicon", gravatar.getUrl(DOSENT_EXIST_EMAIL));
        gravatar.setDefaultImage(GravatarDefaultImage.GRAVATAR_ICON);
        assertEquals("http://www.gravatar.com/avatar/fa8771dec9da9299afed9ffce70c2c18.jpg", gravatar.getUrl(EXIST_EMAIL1));
        gravatar.setDefaultImage(GravatarDefaultImage.MONSTERID);
        assertEquals("http://www.gravatar.com/avatar/93c7566a00a574b94e73565fa74bbbc9.jpg?d=monsterid", gravatar.getUrl(EXIST_EMAIL2));
        gravatar.setDefaultImage(GravatarDefaultImage.WAVATAR);
        assertEquals("http://www.gravatar.com/avatar/93c7566a00a574b94e73565fa74bbbc9.jpg?d=wavatar", gravatar.getUrl(EXIST_EMAIL2));
        gravatar.setDefaultImage(GravatarDefaultImage.HTTP_404);
        assertEquals("http://www.gravatar.com/avatar/93c7566a00a574b94e73565fa74bbbc9.jpg?d=404", gravatar.getUrl(EXIST_EMAIL2));

    }

    public void testGetImageUrlCombined() {
        gravatar.setSize(123);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        gravatar.setRating(GravatarRating.XPLICIT);
        assertEquals("http://www.gravatar.com/avatar/93c7566a00a574b94e73565fa74bbbc9.jpg?s=123&r=x&d=identicon", gravatar.getUrl(EXIST_EMAIL2));
        gravatar.setSize(321);
        gravatar.setDefaultImage(GravatarDefaultImage.MONSTERID);
        gravatar.setRating(GravatarRating.RESTRICTED);
        assertEquals("http://www.gravatar.com/avatar/fa8771dec9da9299afed9ffce70c2c18.jpg?s=321&r=r&d=monsterid", gravatar.getUrl(EXIST_EMAIL1));

    }

    public void testDownload() {
        byte[] bytes = gravatar.download(EXIST_EMAIL1);
        assertTrue("content present", bytes.length > 100);

        assertNull("null for no gravatar by default", gravatar.download(DOSENT_EXIST_EMAIL));

        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        bytes = gravatar.download(DOSENT_EXIST_EMAIL);
        assertTrue("content present", bytes.length > 100);
    }

}
