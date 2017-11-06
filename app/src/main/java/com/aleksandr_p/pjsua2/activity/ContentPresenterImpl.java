package com.aleksandr_p.pjsua2.activity;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.aleksandr_p.pjsua2.MyApplication;
import com.aleksandr_p.pjsua2.R;
import com.aleksandr_p.pjsua2.fragment.SipFragment;
import com.aleksandr_p.pjsua2.pjsip.SipServer;
import com.aleksandr_p.pjsua2.utils.StorageUtils;

/**
 * Created by boger on 2015/8/4.
 */
public class ContentPresenterImpl implements ContentPresenter {

    public static final String HOST = "80.209.239.116";
    public static final String PORT = "5060";
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

    @Override
    public void init() {
        Fragment mainFragment = new SipFragment();
        contentView.addContent(mainFragment, SipFragment.TAG);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void openConfDialog() {
        if (MyApplication.instance.getSipServer() == null) {
            return;
        }
        View view = LayoutInflater.from(contentView.getContext()).inflate(R.layout.layout_conf, null);
        final EditText edtServer = (EditText) view.findViewById(R.id.edt_server_address);
        final EditText edtUsername = (EditText) view.findViewById(R.id.edt_username);
        final EditText edtPassword = (EditText) view.findViewById(R.id.edt_password);
        //load Conf
        SipServer server = MyApplication.instance.getSipServer();
        edtServer.setText(server.getServerAddress());
        edtUsername.setText(server.getUsername());
        edtPassword.setText(server.getPassword());
        new AlertDialog.Builder(contentView.getContext())
                .setTitle("PjSip Configuration")
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyApplication.instance.getSipServer().modifyAcc(edtServer.getText().toString(), edtUsername.getText().toString(), edtPassword.getText().toString());
                        saveConfs(edtServer.getText().toString(), edtUsername.getText().toString(), edtPassword.getText().toString());
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void saveConfs(String addr, String username, String password) {
        StorageUtils.createStorager(contentView.getContext())
                .putString("addr", addr)
                .putString("username", username)
                .putString("password", password)
                .commit();
    }

    @Override
    public void closeSipServer() {
        if (MyApplication.instance.getSipServer() == null) {
            return;
        }
        MyApplication.instance.getSipServer().deinit();
        contentView.showMsg("服务已经关闭");
    }
}
