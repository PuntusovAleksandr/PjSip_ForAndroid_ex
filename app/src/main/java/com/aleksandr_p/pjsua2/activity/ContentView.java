package com.aleksandr_p.pjsua2.activity;


import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by bogerchan on 2015/8/4.
 */
public interface ContentView {
    void replaceContent(Fragment fragment, String tag);

    void addContent(Fragment fragment, String tag);

    void removeContent(Fragment fragment, String tag);

    void hideContent(Fragment fragment, String tag);

    Context getContext();

    void showMsg(String msg);

    void switchFragment(Fragment from, Class to, String tagTo);
}
