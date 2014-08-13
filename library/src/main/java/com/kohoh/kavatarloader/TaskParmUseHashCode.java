package com.kohoh.kavatarloader;

/**
 * Created by kohoh on 14-8-12.
 */
public class TaskParmUseHashCode extends TaskParm {
    public String getHashCode() {
        return hash_code;
    }

    public TaskParmUseHashCode setHashCode(String hash_code) {
        this.hash_code = hash_code;
        return this;
    }

    private String hash_code;
}
