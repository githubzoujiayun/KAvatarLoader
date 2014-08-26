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

    /**
     * 设置头像的大小
     * 单位为像素，默认大小为80。有效范围为1～2048。
     *
     * @param sizeInPixels 头像大小
     * @return Gravatr
     */
    public Gravatar setSize(int sizeInPixels) {
        Validate.isTrue(sizeInPixels >= 1 && sizeInPixels <= 2048,
                "sizeInPixels needs to be between 1 and 512");
        this.size = sizeInPixels;
        return this;
    }

    /**
     * 设置头像等级
     * Gravatar将头像定义为GENERAL_AUDIENCES，PARENTAL_GUIDANCE_SUGGESTED，RESTRICTED，XPLICIT四个等级
     * 默认的等级为GENERAL_AUDIENCES。
     *
     * @param rating 头像等级
     * @return Gravar
     */
    public Gravatar setRating(GravatarRating rating) {
        Validate.notNull(rating, "rating");
        this.rating = rating;
        return this;
    }

    /**
     * 设置默认头像
     * Gravtar提供了GRAVATAR_ICON，MYSTERY_MEN，IDENTICON，WAVATAR等默认头像。如果所要加载的头像不存在，就会返回默认头像。
     * 默认头像默认被设置为HTTP-404
     * @param gravatar_provide_image Gravatar提供的默认头像
     * @return Gravtar
     */
    public Gravatar setDefaultImage(GravatarProvideImage gravatar_provide_image) {
        Validate.notNull(gravatar_provide_image, "gravatar_provide_image");
        this.default_image.setDefaultImage(DefaultImageStyle.GRAVATAR_PROVIDE, gravatar_provide_image);
        return this;
    }

    /**
     * 设置默认头像
     * 设置自定义的默认头像。如果所要加载的头像不存在，就会根据提供的URL加载对应的默认头像。
     * 默认头像对应的URL需要满足一下条件
     * 1、图片必须是能够公开访问的。
     * 2、只能使用HTTP和HTTPS协议，端口分别对应80和443
     * 3、图片只能那是jpg，jpeg，gif，png这几种格式
     * 4、不能包含请求代码
     * @param url 默认头像对应的URL
     * @return Gravatar
     */
    public Gravatar setDefaultImage(String url) {
        this.default_image.setDefaultImage(DefaultImageStyle.CUSTOM_DEFAULT_IMAGE_ON_INTERNET, url);
        return this;
    }

    /**
     * 设置是否强制加载默认头像
     * 默认设置为false
     * @param isForceLoadDefaultImage true，将会强制加载默认头像
     * @return Gravatar
     */
    public Gravatar setForceLoadDefaultImage(boolean isForceLoadDefaultImage) {
        this.isForceLoadDefaultImage = isForceLoadDefaultImage;
        return this;
    }

    /**
     * 设置Gravtar的服务器地址
     * 默认的地址为 GRAVATAR_GENERAL_SERVER http://www.gravatar.com
     * 但是如果你需要使用HTTPS加载头像，那么你可以使用 GRAVATAR_SECURE_SERVERR https://secure.gravatar.com 更安全的加载头像
     * @param gravatar_server Gravtar服务器
     * @return Gravtar
     */
    public Gravatar setGravatarServer(GravatarServer gravatar_server) {
        this.gravatar_server = gravatar_server;
        return this;
    }

    /**
     * 根据email获取头像对应的URL
     * @param email Email
     * @return URL地址
     */
    public String getUrlByEmail(String email) {
        Validate.notNull(email, "email");

        return getUrlByEmail(gravatar_server, email, size, default_image, rating, isForceLoadDefaultImage);
    }

    /**
     * 根据email获取头像对应的URL
     * @param server Gravatar服务器
     * @param email Email
     * @param size 头像大小
     * @param default_image 默认头像
     * @param rating 头像等级
     * @param isForceLoadDefaultImage 是否强制加载默认头像
     * @return URL地址
     */
    static public String getUrlByEmail(GravatarServer server, String email,
                                       Integer size, GravatarDefaultImage default_image, GravatarRating rating,
                                       Boolean isForceLoadDefaultImage) {
        String hash_code = getHashCodeByEmail(email);
        String params = formatUrlParmeters(size, default_image, rating, isForceLoadDefaultImage);
        return getUrl(server, hash_code, params);
    }

    /**
     * 根据HashCode获取头像对应URL
     * @param hash_code HashCode
     * @return URL地址
     */
    public String getUrlByHashCode(String hash_code) {
        Validate.notNull(hash_code, "hash_code");

        return getUrlByHashCode(gravatar_server, hash_code, size, default_image, rating, isForceLoadDefaultImage);
    }

    /**
     * 根据HashCode获取头像对应URL
     * @param server Gravatar服务器
     * @param hash_code HashCode
     * @param size 头像大小
     * @param default_image 默认头像
     * @param rating 头像等级
     * @param isForceLoadDefaultImage 是否强制加载默认头像
     * @return URL地址
     */
    static public String getUrlByHashCode(GravatarServer server, String hash_code,
                                          Integer size, GravatarDefaultImage default_image, GravatarRating rating,
                                          Boolean isForceLoadDefaultImage) {
        String params = formatUrlParmeters(size, default_image, rating, isForceLoadDefaultImage);
        return getUrl(server, hash_code, params);
    }

    /**
     * 根据头像对应URL地址获取HashCode
     * @param url URL
     * @return HashCode
     */
    static public String getHashCodeByUrl(String url) {
        url = url.replace(GravatarServer.GRAVATAR_GENERAL_SERVER.getServer(), "");
        url = url.replace(GravatarServer.GRAVATAR_SECURE_SERVERR.getServer(), "");
        int start = url.indexOf(".jpg");
        String usless = url.substring(start);
        String hash_code = url.replace(usless, "");
        return hash_code;
    }

    /**
     * 根据Email获取HashCode
     * @param email Email
     * @return HashCode
     */
    static public String getHashCodeByEmail(String email) {
        return MD5Util.md5Hex(email.toLowerCase().trim());
    }

    /**
     * 根据Email加载对应的头像
     * @param email Email
     * @return 头像对应的二进制文件
     */
    public byte[] downloadByEmail(String email) {
        return dowloadByUrl(getUrlByEmail(email));
    }

    /**
     * 根据HashCode加载对应的头像
     * @param hash_code HashCode
     * @return 头像对应的二进制文件
     */
    public byte[] downloadByHashCode(String hash_code) {
        return dowloadByUrl(getUrlByHashCode(hash_code));
    }

    /**
     * 根据Url加载对应的头像
     * @param address URL
     * @return 头像对应的二进制文件
     */
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
