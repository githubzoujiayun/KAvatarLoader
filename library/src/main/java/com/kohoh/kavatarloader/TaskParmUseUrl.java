package com.kohoh.kavatarloader;

/**
 * Created by kohoh on 14-8-12.
 */
public class TaskParmUseUrl extends TaskParm {
    public String getUrl() {
        return url;
    }

    public TaskParmUseUrl setUrl(String url) {
        this.url = url;
        return this;
    }

    private String url;
}

