package me.boger.pjsua2;

import android.app.Application;

import me.boger.pjsua2.pjsip.SipServer;

/**
 * Created by bogerchan on 2016/1/27.
 */
public class MyApplication extends Application {
    public static MyApplication instance;
    private SipServer sipServer;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public SipServer getSipServer() {
        return sipServer;
    }

    public void setSipServer(SipServer sipServer) {
        this.sipServer = sipServer;
    }
}
