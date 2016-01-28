package me.boger.pjsua2.activity;

/**
 * Created by boger on 2015/8/4.
 */
public interface ContentPresenter {
    void init();

    void onResume();

    void openConfDialog();

    void closeSipServer();
}
