package com.kohoh.gravatar;

import com.kohoh.gravatar.GravatarDefaultImage.DefaultImageStyle;
import com.kohoh.gravatar.GravatarDefaultImage.GravatarProvideImage;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public final class Gravatar {

    private int size = 80;
    private GravatarRating rating = GravatarRating.GENERAL_AUDIENCES;
    private GravatarDefaultImage default_image = new GravatarDefaultImage().
            setDefaultImage(DefaultImageStyle.GRAVATAR_PROVIDE, GravatarProvideImage.GRAVATAR_ICON);
    private GravatarServer gravatar_server = GravatarServer.GRAVATAR_GENERAL_SERVER;
    private boolean isForceLoadDefaultImage = false;

    public Gravatar setSize(int sizeInPixels) {
        Validate.isTrue(sizeInPixels >= 1 && sizeInPixels <= 2048,
                "sizeInPixels needs to be between 1 and 512");
        this.size = sizeInPixels;
        return this;
    }

    public Gravatar setRating(GravatarRating rating) {
        Validate.notNull(rating, "rating");
        this.rating = rating;
        return this;
    }

    public Gravatar setDefaultImage(GravatarProvideImage gravatar_provide_image) {
        Validate.notNull(gravatar_provide_image, "gravatar_provide_image");
        this.default_image.setDefaultImage(DefaultImageStyle.GRAVATAR_PROVIDE, gravatar_provide_image);
        return this;
    }

    public Gravatar setDefaultImage(String url) {
        this.default_image.setDefaultImage(DefaultImageStyle.CUSTOM_DEFAULT_IMAGE_ON_INTERNET, url);
        return this;
    }

    public Gravatar setForceLoadDefaultImage(boolean isForceLoadDefaultImage) {
        this.isForceLoadDefaultImage = isForceLoadDefaultImage;
        return this;
    }

    public Gravatar setGravatarServer(GravatarServer gravatar_server) {
        this.gravatar_server = gravatar_server;
        return this;
    }

    public String getUrlByEmail(String email) {
        Validate.notNull(email, "email");

        return getUrlByEmail(gravatar_server, email, size, default_image, rating, isForceLoadDefaultImage);
    }

    static public String getUrlByEmail(GravatarServer server, String email,
                                       Integer size, GravatarDefaultImage default_image, GravatarRating rating,
                                       Boolean isForceLoadDefaultImage) {
        String hash_code = getHashCodeByEmail(email);
        String params = formatUrlParmeters(size, default_image, rating, isForceLoadDefaultImage);
        return getUrl(server, hash_code, params);
    }

    public String getUrlByHashCode(String hash_code) {
        Validate.notNull(hash_code, "hash_code");

        return getUrlByHashCode(gravatar_server, hash_code, size, default_image, rating, isForceLoadDefaultImage);
    }

    static public String getUrlByHashCode(GravatarServer server, String hash_code,
                                          Integer size, GravatarDefaultImage default_image, GravatarRating rating,
                                          Boolean isForceLoadDefaultImage) {
        String params = formatUrlParmeters(size, default_image, rating, isForceLoadDefaultImage);
        return getUrl(server, hash_code, params);
    }

    static public String getHashCodeByUrl(String url) {
        url = url.replace(GravatarServer.GRAVATAR_GENERAL_SERVER.getServer(), "");
        url = url.replace(GravatarServer.GRAVATAR_SECURE_SERVERR.getServer(), "");
        int start = url.indexOf(".jpg");
        String usless = url.substring(start);
        String hash_code = url.replace(usless, "");
        return hash_code;
    }

    static public String getHashCodeByEmail(String email) {
        return MD5Util.md5Hex(email.toLowerCase().trim());
    }

    public byte[] downloadByEmail(String email) {
        return dowloadByUrl(getUrlByEmail(email));
    }

    public byte[] downloadByHashCode(String hash_code) {
        return dowloadByUrl(getUrlByHashCode(hash_code));
    }

    public byte[] dowloadByUrl(String address) {
        InputStream stream = null;
        try {
            URL url = new URL(address);
            stream = url.openStream();
            return IOUtils.toByteArray(stream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    static String formatUrlParmeters(Integer size, GravatarDefaultImage default_image,
                                     GravatarRating rating, Boolean isForceLoadDefaultImage) {
        String params = null;

        List<String> param_list = new ArrayList<String>();

        if (size != null && size != 80) {
            param_list.add("s=" + size);

        }

        if (rating != null && rating != GravatarRating.GENERAL_AUDIENCES) {
            param_list.add("r=" + rating.getCode());

        }

        if (default_image != null) {
            switch (default_image.getStyle()) {
                case GRAVATAR_PROVIDE:
                    GravatarProvideImage gravatarProvideImage = default_image.getGravatarProvideImage();
                    if (!gravatarProvideImage.equals(GravatarProvideImage.GRAVATAR_ICON)) {
                        param_list.add("d=" + gravatarProvideImage.getCode());
                    }
                    break;
                case CUSTOM_DEFAULT_IMAGE_ON_INTERNET:
                    String raw_url = default_image.getCustomDefaultImageOnInternet();
                    try {
                        String encod_url = URLEncoder.encode(raw_url, "utf-8");
                        param_list.add("d=" + encod_url);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
            }
        }

        if (isForceLoadDefaultImage != null && isForceLoadDefaultImage != false) {
            param_list.add("f=y");
        }

        if (param_list.isEmpty())
            params = "";
        else
            params = "?" + StringUtils.join(param_list.iterator(), "&");

        return params;
    }

    static String getUrl(GravatarServer server, String hash_code, String params) {

        return server.getServer() + hash_code + ".jpg" + params;
    }
}
