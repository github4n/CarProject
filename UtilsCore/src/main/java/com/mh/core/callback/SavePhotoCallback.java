package com.mh.core.callback;

/**
 * Created by MH on 2017/1/7.
 */
public interface SavePhotoCallback extends MHCallBack{

    void onSaveSuccess(String path);
    void onSaveFailure();
}
