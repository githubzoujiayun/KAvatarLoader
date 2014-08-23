package com.kohoh.gravatar;

import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A gravatar is a dynamic image resource that is requested from the
 * gravatar.com server. This class calculates the gravatar url and fetches
 * gravatar images. See http://en.gravatar.com/site/implement/url .
 * <p/>
 * This class is thread-safe, Gravatar objects can be shared.
 * <p/>
 * Usage example:
 * <p/>
 * <code>
 * Gravatar gravatar = new Gravatar();
 * gravatar.setSize(50);
 * gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
 * gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
 * String url = gravatar.getUrlByEmail("iHaveAn@email.com");
 * byte[] jpg = gravatar.downloadByEmail("info@ralfebert.de");
 * </code>
 */
public final class Gravatar {

    public final static int DEFAULT_SIZE = 80;
    private int size = DEFAULT_SIZE;
    public static final GravatarRating DEFAULT_RATING = GravatarRating.GENERAL_AUDIENCES;
    private GravatarRating rating = DEFAULT_RATING;
    public static final GravatarDefaultImage DEFAULT_DEFAULT_IMAGE = GravatarDefaultImage.HTTP_404;
    private GravatarDefaultImage defaultImage = DEFAULT_DEFAULT_IMAGE;
    static final public String TAG = Gravatar.class.getSimpleName() + "_tag";
    private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    public void log() {
        Log.d(TAG, "size= " + size);
        Log.d(TAG, "rating= " + rating);
        Log.d(TAG, "default avatar= " + defaultImage);
    }

    /**
     * Specify a gravatar size between 1 and 512 pixels. If you omit this, a
     * default size of 80 pixels is used.
     */
    public void setSize(int sizeInPixels) {
        Validate.isTrue(sizeInPixels >= 1 && sizeInPixels <= 512,
                "sizeInPixels needs to be between 1 and 512");
        this.size = sizeInPixels;
    }

    /**
     * Specify a rating to ban gravatar images with explicit content.
     */
    public void setRating(GravatarRating rating) {
        Validate.notNull(rating, "rating");
        this.rating = rating;
    }

    /**
     * Specify the default image to be produced if no gravatar image was found.
     */
    public void setDefaultImage(GravatarDefaultImage defaultImage) {
        Validate.notNull(defaultImage, "defaultImage");
        this.defaultImage = defaultImage;
    }

    /**
     * Returns the Gravatar URL for the given email address.
     */
    public String getUrlByEmail(String email) {
        Validate.notNull(email, "email");

        String emailHash = MD5Util.md5Hex(email.toLowerCase().trim());
        String params = formatUrlParameters();
        return GRAVATAR_URL + emailHash + ".jpg" + params;
    }

    public String getUrlByHashCode(String hash_code) {
        Validate.notNull(hash_code, "hash_code");

        String params = formatUrlParameters();
        return GRAVATAR_URL + hash_code + ".jpg" + params;
    }

    public String getHashCodeByUrl(String url) {
        url=url.replace("http://www.gravatar.com/avatar/", "");
        int start = url.indexOf(".jpg");
        String usless = url.substring(start);
        String hash_code = url.replace(usless, "");
        return hash_code;
    }

    /**
     * Downloads the gravatar for the given URL using Java {@link URL} and
     * returns a byte array containing the gravatar jpg, returns null if no
     * gravatar was found.
     */
    public byte[] downloadByEmail(String email) {
        return dowloadByUrl(getUrlByEmail(email));
    }

    public byte[] downloadByHashCode(String hash_code) {
        return dowloadByUrl(getUrlByHashCode(hash_code));
    }

    public byte[] dowloadByUrl(String url_address) {
        InputStream stream = null;
        try {
            URL url = new URL(url_address);
            stream = url.openStream();
            return IOUtils.toByteArray(stream);
        } catch (Exception e) {
            Log.e(TAG, "download avatar fialed", e);
            return null;
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    private String formatUrlParameters() {
        List<String> params = new ArrayList<String>();

        if (size != DEFAULT_SIZE)
            params.add("s=" + size);
        if (rating != DEFAULT_RATING)
            params.add("r=" + rating.getCode());
        if (defaultImage != GravatarDefaultImage.GRAVATAR_ICON)
            params.add("d=" + defaultImage.getCode());

        if (params.isEmpty())
            return "";
        else
            return "?" + StringUtils.join(params.iterator(), "&");
    }

}
