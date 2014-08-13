package com.kohoh.kavatarloader;

/**
 * Created by kohoh on 14-7-29.
 */
public interface BindListener {
    /**
     * 当完成绑定任务后会回调该方法。
     *
     * @param result_code 绑定结果
     */
    public void onBindFinished(RESULT_CODE result_code);

    public enum RESULT_CODE {SUCCESS, FAIL;}
}
