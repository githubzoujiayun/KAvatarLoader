package com.kohoh.kavatarloader;

/**
 * Created by kohoh on 14-7-29.
 */
public interface BindListener {
    public void onBindFinished(RESULT_CODE result_code);

    public enum RESULT_CODE {SUCCESS, FAIL;}
}
