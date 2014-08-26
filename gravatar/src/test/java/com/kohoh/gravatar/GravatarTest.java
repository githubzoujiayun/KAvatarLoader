package com.kohoh.gravatar;

import com.kohoh.gravatar.GravatarDefaultImage.GravatarProvideImage;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by kohoh on 14-8-1.
 */
public class GravatarTest {
    private Gravatar gravatar;

    @Before
    public void setUp() {
        gravatar = new Gravatar();
    }

    @Test
    public void testGetImageUrlTrimLeadingAndTrailingWhitespace() {
        assertEquals(TestConstant.EXIST_EMAIL_URL, gravatar.getUrlByEmail("jgravatar@126.com   "));
        assertEquals(TestConstant.EXIST_EMAIL_URL, gravatar.getUrlByEmail("   jgravatar@126.com"));
        assertEquals(TestConstant.EXIST_EMAIL_URL, gravatar.getUrlByEmail("   jgravatar@126.com   "));
    }

    @Test
    public void testGetImageUrlForceAllCharactersToLowerCase() {
        assertEquals(TestConstant.EXIST_EMAIL_URL, gravatar.getUrlByEmail("jGRavAtAr@126.COM"));
    }

    @Test
    public void testGetUrlByEmail() {
        assertEquals(TestConstant.EXIST_EMAIL_URL, gravatar.getUrlByEmail(TestConstant.EXIST_EMAIL));
    }

    @Test
    public void testGetUrlByHashCode() {
        assertEquals(TestConstant.EXIST_EMAIL_URL, gravatar.getUrlByHashCode(TestConstant.
                EXIST_EMAIL_HASH_CODE));
    }

    @Test
    public void testGetHashCodeByUrl() {
        String hash_code_expect = TestConstant.EXIST_EMAIL_HASH_CODE;
        String url = TestConstant.EXIST_EMAIL_URL;
        String hash_code_actural = gravatar.getHashCodeByUrl(url);
        assertEquals(hash_code_expect, hash_code_actural);
    }

    @Test
    public void testGetHashCodeByEmail() {
        String hash_code_expect = TestConstant.EXIST_EMAIL_HASH_CODE;
        String email = TestConstant.EXIST_EMAIL;
        String hash_code_actural = gravatar.getHashCodeByEmail(email);
        assertEquals(hash_code_expect, hash_code_actural);
    }

    @Test
    public void testSetSize() {
        gravatar.setSize(100);
        assertEquals(TestConstant.EXIST_EMAIL_S_100_URL, gravatar.getUrlByEmail(TestConstant.EXIST_EMAIL));
    }

    @Test
    public void testSetRating() {
        gravatar.setRating(GravatarRating.PARENTAL_GUIDANCE_SUGGESTED);
        assertEquals(TestConstant.EXIST_EMAIL_R_PG_URL, gravatar.getUrlByEmail(TestConstant.EXIST_EMAIL));
    }

    @Test
    public void testSetDefaultImage() {
        gravatar.setDefaultImage(GravatarProvideImage.HTTP_404);
        assertEquals(TestConstant.EXIST_EMAIL_D_404_URL, gravatar.getUrlByEmail(TestConstant.EXIST_EMAIL));
    }

    @Test
    public void testSetCustomDefaultImage() {
        gravatar.setDefaultImage(TestConstant.CUSTOM_DEFAULT_IMAGE_ON_INTERNET_URL);
        assertEquals(TestConstant.EXIST_EMAIL_D_CUSTOM_DEFAULT_IMAGE_ON_INTERNET_URL,
                gravatar.getUrlByEmail(TestConstant.EXIST_EMAIL));
    }

    @Test
    public void testSetIsForceLoadDefaultImage() {
        gravatar.setForceLoadDefaultImage(true);
        assertEquals(TestConstant.EXIST_EMAIL_FORCE_LOAD_DEFAULT_IMAGE_URL, gravatar.getUrlByHashCode(TestConstant.EXIST_EMAIL_HASH_CODE));
    }

    @Test
    public void testSetGravatarServer() {
        gravatar.setGravatarServer(GravatarServer.GRAVATAR_SECURE_SERVERR);
        assertEquals(TestConstant.EXIST_EMAIL_GRAVATAR_SECURE_SERVER_URL, gravatar.getUrlByHashCode(TestConstant.EXIST_EMAIL_HASH_CODE));
    }

    @Test
    public void testCombinedSet() {
        gravatar.setSize(200);
        gravatar.setDefaultImage(GravatarProvideImage.WAVATAR);
        gravatar.setRating(GravatarRating.XPLICIT);
        assertEquals(TestConstant.EXIST_EMAIL_S_200_R_X_D_WAVATAR_URL, gravatar.getUrlByEmail(TestConstant.EXIST_EMAIL));
    }

    @Test
    public void testDownloadByEmail() {
        byte[] raw_gravatar_actural = gravatar.downloadByEmail(TestConstant.EXIST_EMAIL);
        byte[] raw_gravatar_expect = loadExpectRawGravatar(TestConstant.EXIST_EMAIL_FILE);
        assertRawGravatarEquals(raw_gravatar_expect, raw_gravatar_actural);
    }

    @Test
    public void testDownloadByHashCode() {
        byte[] raw_gravatar_actural = gravatar.downloadByHashCode(TestConstant.EXIST_EMAIL_HASH_CODE);
        byte[] raw_gravatar_expect = loadExpectRawGravatar(TestConstant.EXIST_EMAIL_FILE);
        assertRawGravatarEquals(raw_gravatar_expect, raw_gravatar_actural);
    }

    @Test
    public void testDownloadByUrl() {
        byte[] raw_gravatar_actural = gravatar.dowloadByUrl(TestConstant.EXIST_EMAIL_URL);
        byte[] raw_gravatar_expect = loadExpectRawGravatar(TestConstant.EXIST_EMAIL_FILE);
        assertRawGravatarEquals(raw_gravatar_expect, raw_gravatar_actural);
    }

    private byte[] loadExpectRawGravatar(String raw_gravatar_expect_file_name) {
        BufferedInputStream inputStream = new BufferedInputStream(getClass().
                getResourceAsStream("/" + raw_gravatar_expect_file_name));
        byte[] raw_gravatar_expect = null;

        try {
            raw_gravatar_expect = new byte[inputStream.available()];
            inputStream.read(raw_gravatar_expect);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return raw_gravatar_expect;
    }

    private void assertRawGravatarEquals(byte[] raw_gravatar_expect, byte[] raw_gravatar_actural) {
        assertNotNull("raw_gravatar_actural is null", raw_gravatar_actural);
        assertNotNull("raw_gravatar_expect is null", raw_gravatar_expect);

        if (raw_gravatar_actural.length != raw_gravatar_expect.length) {
            fail("raw_gravatar not equals");
        }

        for (int i = 0; i < raw_gravatar_actural.length; i++) {
            if (raw_gravatar_actural[i] != raw_gravatar_expect[i]) {
                fail("raw_gravatar not equals");
            }
        }
    }
}
