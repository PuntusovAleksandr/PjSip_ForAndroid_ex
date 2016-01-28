package me.boger.pjsua2.activity;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import me.boger.pjsua2.MyApplication;
import me.boger.pjsua2.R;
import me.boger.pjsua2.fragment.SipFragment;
import me.boger.pjsua2.pjsip.SipServer;
import me.boger.pjsua2.utils.StorageUtils;

/**
 * Created by boger on 2015/8/4.
 */
public class ContentPresenterImpl implements ContentPresenter {

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
