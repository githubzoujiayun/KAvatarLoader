package com.kohoh.gravatar.parser;

/**
 * Created by kohoh on 14-8-16.
 */
public class GravatarInfo {
    private String email;
    private String hash_code;
    private String url;

    public GravatarInfo(String email, String hash_code, String url) {
        this.email = email;
        this.hash_code = hash_code;
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashCode() {
        return hash_code;
    }

    public void setHashCode(String hash_code) {
        this.hash_code = hash_code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
