package com.kohoh.gravatar;

public class GravatarDefaultImage {
    private DefaultImageStyle style;
    private String customDefaultImageOnInternet;
    private GravatarProvideImage gravatarProvideImage;

    public GravatarDefaultImage setDefaultImage(DefaultImageStyle style, Object default_image) {
        this.style = style;
        switch (style) {
            case GRAVATAR_PROVIDE:
                this.customDefaultImageOnInternet = null;
                this.gravatarProvideImage = (GravatarProvideImage) default_image;
                break;
            case CUSTOM_DEFAULT_IMAGE_ON_INTERNET:
                this.gravatarProvideImage = null;
                this.customDefaultImageOnInternet = (String) default_image;
                break;
        }
        return this;
    }

    public DefaultImageStyle getStyle() {
        return style;
    }

    public String getCustomDefaultImageOnInternet() {
        return customDefaultImageOnInternet;
    }

    public GravatarProvideImage getGravatarProvideImage() {
        return gravatarProvideImage;
    }

    enum DefaultImageStyle {
        GRAVATAR_PROVIDE, CUSTOM_DEFAULT_IMAGE_ON_INTERNET;
    }

    public enum GravatarProvideImage {

        GRAVATAR_ICON(""),

        IDENTICON("identicon"),

        MONSTERID("monsterid"),

        WAVATAR("wavatar"),

        HTTP_404("404"),

        MYSTERY_MEN("mystery_men"),

        RETRO("retro"),

        BLANK("blank");

        private String code;

        private GravatarProvideImage(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
