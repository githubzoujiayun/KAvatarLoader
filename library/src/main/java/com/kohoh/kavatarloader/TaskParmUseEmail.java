package com.kohoh.kavatarloader;

/**
 * Created by kohoh on 14-8-12.
 */
public class TaskParmUseEmail extends TaskParm {
    public String getEmail() {
        return email;
    }

    public TaskParmUseEmail setEmail(String email) {
        this.email = email;
        return this;
    }

    private String email;
}
