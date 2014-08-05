package com.kohoh.APITest;

import android.test.AndroidTestCase;
import android.util.Log;

import com.kohoh.gravatar.Gravatar;
import com.kohoh.gravatar.GravatarDefaultImage;
import com.kohoh.gravatar.GravatarRating;
import com.kohoh.kavatarloader.test.GravatarConstant;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kohoh on 14-8-5.
 */
public class GravatarTest extends AndroidTestCase{
    private Gravatar gravatar = new Gravatar();
    public static final String TAG = Gravatar.class.getSimpleName() + "_tag";

    public void logAPI() {
        String url_doesnt_exit_email = gravatar.getUrlByEmail(GravatarConstant.DOSENT_EXIST_EMAIL);
        String url_exist_email1 = gravatar.getUrlByEmail(GravatarConstant.EXIST_EMAIL1);
        String url_exist_email2 = gravatar.getUrlByEmail(GravatarConstant.EXIST_EMAIL2);
        Log.d(TAG, "url_doesnt_exit_email= " + url_doesnt_exit_email);
        Log.d(TAG, "url_exist_email1= " + url_exist_email1);
        Log.d(TAG, "url_exist_email2= " + url_exist_email2);

        gravatar = new Gravatar();
        String url_exist_email1_with_whitespace1 = gravatar.getUrlByEmail("kavatarloader1@126.com   ");
        String url_exist_email1_with_whitespace2 = gravatar.getUrlByEmail("  kavatarloader1@126.com");
        String url_exist_email1_with_hith_case1 = gravatar.getUrlByEmail("kaVatArlOader1@126.com");
        String url_exist_email1_with_hith_case2 = gravatar.getUrlByEmail("KavaTarloaDer1@126.cOm");
        Log.d(TAG, "url_exist_email1_with_hith_case1= " + url_exist_email1_with_hith_case1);
        Log.d(TAG, "url_exist_email1_with_hith_case2= " + url_exist_email1_with_hith_case2);
        Log.d(TAG, "url_exist_email1_with_whitespace1= " + url_exist_email1_with_whitespace1);
        Log.d(TAG, "url_exist_email1_with_whitespace2= " + url_exist_email1_with_whitespace2);

        gravatar = new Gravatar();
        gravatar.setSize(100);
        String url_exist_mail1_with_size100 = gravatar.getUrlByEmail(GravatarConstant.EXIST_EMAIL1);
        gravatar.setSize(321);
        String url_exist_mail2_with_size321 = gravatar.getUrlByEmail(GravatarConstant.EXIST_EMAIL2);
        Log.d(TAG, "url_exist_mail1_with_size100= " + url_exist_mail1_with_size100);
        Log.d(TAG, "url_exist_mail2_with_size321= " + url_exist_mail2_with_size321);

        gravatar = new Gravatar();
        gravatar.setRating(GravatarRating.XPLICIT);
        String url_exist_mail1_with_rating_xplicit = gravatar.getUrlByEmail(GravatarConstant.EXIST_EMAIL1);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        String url_exist_mail1_with_rating_general_audiences = gravatar.getUrlByEmail(GravatarConstant.EXIST_EMAIL1);
        gravatar.setRating(GravatarRating.RESTRICTED);
        String url_exist_mail1_with_rating_restricted = gravatar.getUrlByEmail(GravatarConstant.EXIST_EMAIL1);
        gravatar.setRating(GravatarRating.PARENTAL_GUIDANCE_SUGGESTED);
        String url_exist_mail1_with_rating_parental_guidance_suggested = gravatar.getUrlByEmail(GravatarConstant.EXIST_EMAIL1);
        Log.d(TAG, "url_exist_mail1_with_rating_xplicit= " + url_exist_mail1_with_rating_xplicit);
        Log.d(TAG, "url_exist_mail1_with_rating_restricted= " + url_exist_mail1_with_rating_restricted);
        Log.d(TAG, "url_exist_mail1_with_rating_general_audiences= " + url_exist_mail1_with_rating_general_audiences);
        Log.d(TAG, "url_exist_mail1_with_rating_parental_guidance_suggested= " + url_exist_mail1_with_rating_parental_guidance_suggested);

        gravatar = new Gravatar();
        gravatar.setDefaultImage(GravatarDefaultImage.GRAVATAR_ICON);
        String url_exist_mail1_with_default_image_gravatar_icon = gravatar.getUrlByEmail(GravatarConstant.EXIST_EMAIL1);
        Log.d(TAG, "url_exist_mail1_with_default_image_gravatar_icon= " + url_exist_mail1_with_default_image_gravatar_icon);

        gravatar = new Gravatar();
        gravatar.setSize(123);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        gravatar.setRating(GravatarRating.XPLICIT);
        String url_exist_mail1_combined = gravatar.getUrlByEmail(GravatarConstant.EXIST_EMAIL1);
        Log.d(TAG, "url_exist_mail1_combined= " + url_exist_mail1_combined);
    }

    public void logAvatarSize() {
        sameAvatarDiffSize(GravatarConstant.EXIST_EMAIL1);
        sameAvatarDiffSize(GravatarConstant.EXIST_EMAIL2);
        sameAvatarDiffSize(GravatarConstant.DOSENT_EXIST_EMAIL);

        sameAvatarDiffDefaultAvatar(GravatarConstant.EXIST_EMAIL1);
        sameAvatarDiffDefaultAvatar(GravatarConstant.EXIST_EMAIL2);
        sameAvatarDiffDefaultAvatar(GravatarConstant.DOSENT_EXIST_EMAIL);

        sameAvatarDiffRating(GravatarConstant.EXIST_EMAIL1);
        sameAvatarDiffRating(GravatarConstant.EXIST_EMAIL2);
        sameAvatarDiffRating(GravatarConstant.DOSENT_EXIST_EMAIL);
    }

    private void sameAvatarDiffDefaultAvatar(String email) {
        Log.d(TAG, email);
        gravatar.setSize(100);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        byte[] bytes;

        gravatar.setDefaultImage(GravatarDefaultImage.GRAVATAR_ICON);
        bytes = gravatar.downloadByEmail(email);
        Log.d(TAG, "defaultImage=GRAVATAR_ICON bytes length= " + bytes.length);

        gravatar.setDefaultImage(GravatarDefaultImage.MONSTERID);
        bytes = gravatar.downloadByEmail(email);
        Log.d(TAG, "defaultImage=MONSTERID bytes length= " + bytes.length);

        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        bytes = gravatar.downloadByEmail(email);
        Log.d(TAG, "defaultImage=IDENTICON bytes length= " + bytes.length);

        Log.d(TAG, "----------------------------");
    }

    private void sameAvatarDiffRating(String email) {

        Log.d(TAG, email);
        gravatar.setSize(100);
        gravatar.setDefaultImage(GravatarDefaultImage.GRAVATAR_ICON);
        byte[] bytes;

        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        bytes = gravatar.downloadByEmail(email);
        Log.d(TAG, "rating=GENERAL_AUDIENCES bytes length= " + bytes.length);

        gravatar.setRating(GravatarRating.PARENTAL_GUIDANCE_SUGGESTED);
        bytes = gravatar.downloadByEmail(email);
        Log.d(TAG, "rating=PARENTAL_GUIDANCE_SUGGESTEDS bytes length= " + bytes.length);

        gravatar.setRating(GravatarRating.XPLICIT);
        bytes = gravatar.downloadByEmail(email);
        Log.d(TAG, "rating=XPLICIT bytes length= " + bytes.length);

        Log.d(TAG, "----------------------------");

    }

    private void sameAvatarDiffSize(String email) {
        byte[] bytes;

        Log.d(TAG, email);
        gravatar.setDefaultImage(GravatarDefaultImage.GRAVATAR_ICON);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);

        gravatar.setSize(50);
        bytes = gravatar.downloadByEmail(email);
        Log.d(TAG, "size=50 bytes length= " + bytes.length);

        gravatar.setSize(100);
        bytes = gravatar.downloadByEmail(email);
        Log.d(TAG, "size=100 bytes length= " + bytes.length);

        gravatar.setSize(200);
        bytes = gravatar.downloadByEmail(email);
        Log.d(TAG, "size= 200 bytes length= " + bytes.length);

        Log.d(TAG, "----------------------------");
    }

    public void logAvatarEqual() throws IOException {
        byte[] bytes1 = gravatar.downloadByEmail(GravatarConstant.EXIST_EMAIL1);
        byte[] bytes2 = gravatar.downloadByEmail(GravatarConstant.EXIST_EMAIL2);

        Log.d(TAG, bytes1.equals(bytes2) ? "equals" : "not equals");

        Log.d(TAG, "bytes1 hash= " + bytes1.hashCode());
        Log.d(TAG, "bytes2 hash= " + bytes2.hashCode());

        Log.d(TAG, "bytes1 length= " + bytes1.length);
        Log.d(TAG, "bytes2 length= " + bytes2.length);

        Log.d(TAG, "bytes1= " + bytes1.toString());
        Log.d(TAG, "bytes2= " + bytes2.toString());
    }

    public void downloadAndSave() throws IOException {
        gravatar.setSize(100);
        byte[] bytes_email1_size100 = gravatar.downloadByEmail(GravatarConstant.EXIST_EMAIL1);
        byte[] bytes_email2_size100 = gravatar.downloadByEmail(GravatarConstant.EXIST_EMAIL2);
        gravatar.setSize(200);
        byte[] bytes_email1_size200 = gravatar.downloadByEmail(GravatarConstant.EXIST_EMAIL1);
        byte[] bytes_email2_size200 = gravatar.downloadByEmail(GravatarConstant.EXIST_EMAIL2);

        saveBytes(GravatarConstant.EXIST_EMAIL1 + "_size100_bytes", bytes_email1_size100);
        saveBytes(GravatarConstant.EXIST_EMAIL1 + "_size200_bytes", bytes_email1_size200);
        saveBytes(GravatarConstant.EXIST_EMAIL2 + "_size100_bytes", bytes_email2_size100);
        saveBytes(GravatarConstant.EXIST_EMAIL2 + "_size200_bytes", bytes_email2_size200);
    }

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
