package com.aleksandr_p.pjsua2;

import android.app.Application;
import android.content.Context;

import com.aleksandr_p.pjsua2.pjsip.SipServer;

/**
 * Created by bogerchan on 2016/1/27.
 */
public class App extends Application {

    public static App instance;
    private SipServer sipServer;

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sContext = this.getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }

    public SipServer getSipServer() {
        return sipServer;
    }

    public void setSipServer(SipServer sipServer) {
        this.sipServer = sipServer;
    }
}
