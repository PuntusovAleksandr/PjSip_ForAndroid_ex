package com.aleksandr_p.pjsua2.activity;

import com.aleksandr_p.pjsua2.App;
import com.aleksandr_p.pjsua2.utils.StorageUtils;

/**
 * Created by boger on 2015/8/4.
 */
public class ContentPresenterImpl implements ContentPresenter {

    public static final String PORT = "5060";
    public static final String HOST = "80.209.239.116:"+PORT;
    public static final String ESER = "1021";
    public static final String PASSWORD = "il9ei1h9eS8ieR";
//    sip_host: 80.209.239.116
// sip_port: 5060
// sip_login: 1021
// sip_pass: il9ei1h9eS8ieR
// sip_host: 80.209.239.116
// sip_port: 5060
// sip_login: 1022
// sip_pass: Ahg3Ai35ueil9e

    private ContentView contentView;

    public ContentPresenterImpl(ContentView contentView) {
        if (null == contentView) {
            throw new IllegalArgumentException("WTF,contentView can not be null!!");
        }
        this.contentView = contentView;
    }

    private void saveConfs(String addr, String username, String password) {
        StorageUtils.createStorager(App.getContext())
                .putString("addr", addr)
                .putString("username", username)
                .putString("password", password)
                .commit();
    }

    @Override
    public void closeSipServer() {
        if (App.instance.getSipServer() == null) {
            return;
        }
        App.instance.getSipServer().deinit();
        contentView.showMsg("Служба закрыта");
    }
}
