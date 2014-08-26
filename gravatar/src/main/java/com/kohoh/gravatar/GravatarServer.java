package com.kohoh.gravatar;

/**
 * Created by kohoh on 14-8-26.
 */
public enum GravatarServer {
    GRAVATAR_GENERAL_SERVER("http://www.gravatar.com/avatar/"),
    GRAVATAR_SECURE_SERVERR("https://secure.gravatar.com/avatar/");

    private String server;

    private GravatarServer(String server) {
        this.server = server;
    }

    public String getServer() {
        return server;
    }
}
