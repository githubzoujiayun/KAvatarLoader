package com.kohoh.kavatarloader;

/**
 * Created by kohoh on 14-8-12.
 */
public class TaskParmUseHashCode extends TaskParm {
    public String getHashCode() {
        return hash_code;
    }

    public void setHashCode(String hash_code) {
        this.hash_code = hash_code;
    }

    private String hash_code;
}
