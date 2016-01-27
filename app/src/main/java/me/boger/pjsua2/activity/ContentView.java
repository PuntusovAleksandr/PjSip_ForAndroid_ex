package me.boger.pjsua2.activity;


import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by bogerchan on 2015/8/4.
 */
public interface ContentView {
    public void replaceContent(Fragment fragment, String tag);

    public void addContent(Fragment fragment, String tag);

    public void removeContent(Fragment fragment, String tag);

    public void hideContent(Fragment fragment, String tag);

    public Context getContext();

}
