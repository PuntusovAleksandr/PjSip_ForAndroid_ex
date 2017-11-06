package com.aleksandr_p.pjsua2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aleksandr_p.pjsua2.App;
import com.aleksandr_p.pjsua2.R;
import com.aleksandr_p.pjsua2.activity.ContentPresenterImpl;
import com.aleksandr_p.pjsua2.pjsip.SipServer;
import com.aleksandr_p.pjsua2.utils.StorageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aleksandr_p.pjsua2.activity.ContentActivity.SIP_CALL_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class SipFragment extends BaseFragment {

    public static final String TAG = "SipFragment";

    @BindView(R.id.tv_display)
    TextView tvDisplay;
    @BindView(R.id.tv_dialer_0)
    TextView tvDialer0;
    @BindView(R.id.tv_dialer_1)
    TextView tvDialer1;
    @BindView(R.id.tv_dialer_2)
    TextView tvDialer2;
    @BindView(R.id.tv_dialer_3)
    TextView tvDialer3;
    @BindView(R.id.tv_dialer_4)
    TextView tvDialer4;
    @BindView(R.id.tv_dialer_5)
    TextView tvDialer5;
    @BindView(R.id.tv_dialer_6)
    TextView tvDialer6;
    @BindView(R.id.tv_dialer_7)
    TextView tvDialer7;
    @BindView(R.id.tv_dialer_8)
    TextView tvDialer8;
    @BindView(R.id.tv_dialer_9)
    TextView tvDialer9;
    @BindView(R.id.tv_dialer_dial)
    TextView tvDialerDial;
    @BindView(R.id.tv_dialer_del)
    TextView tvDialerDel;
    View rootView;


    public SipFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_sip, container, false);
        ButterKnife.bind(this, inflate);

        loadConfs();

        rootView = inflate;
        return inflate;
    }

    private void loadConfs() {
        String addr = StorageUtils.getString(App.instance.getApplicationContext(), "addr");
        String username = StorageUtils.getString(App.instance.getApplicationContext(), "username");
        String password = StorageUtils.getString(App.instance.getApplicationContext(), "password");
        if (addr == null || username == null || password == null) {
            saveConfs(ContentPresenterImpl.HOST, ContentPresenterImpl.ESER, ContentPresenterImpl.PASSWORD);
            loadConfs();
            return;
        }
        if (App.instance.getSipServer().getAcc() != null) {
            return;
        }
        App.instance.getSipServer().createAcc(addr, username, password);
    }


    private void saveConfs(String addr, String username, String password) {
        StorageUtils.createStorager(getActivity())
                .putString("addr", addr)
                .putString("username", username)
                .putString("password", password)
                .commit();
    }

    @OnClick({R.id.tv_dialer_dial,
            R.id.tv_dialer_del,
            R.id.tv_dialer_0,
            R.id.tv_dialer_1,
            R.id.tv_dialer_2,
            R.id.tv_dialer_3,
            R.id.tv_dialer_4,
            R.id.tv_dialer_5,
            R.id.tv_dialer_6,
            R.id.tv_dialer_8,
            R.id.tv_dialer_7,
            R.id.tv_dialer_9,
            R.id.tv_display})
    public void clicksiews(View v) {
        switch (v.getId()) {
            case R.id.tv_dialer_dial:
                performDial();
                break;
            case R.id.tv_dialer_del:
                performDel();
                break;
            case R.id.tv_display:
                break;
            default:
                if (v instanceof TextView) {
                    performDigit(((TextView) v).getText());
                }
        }
    }

    private void performDial() {
        if (tvDisplay.getText().length() <= 0) {
            getContentView().showMsg("Введите имя пользователя, которое вы хотите позвонить.");
            return;
        }
        SipServer sipServer = App.instance.getSipServer();
        if (sipServer.makeCall(tvDisplay.getText().toString())) {
            Log.d("SipCall_", "setFragment: __5__ ");
            if (sipServer == null || sipServer.getCurrentCall() == null) {
                getContentView().showMsg("Ошибка при вызове, повторите попытку.");
                return;
            }
            getContentView().switchFragment(SIP_CALL_KEY);
        } else {
            getContentView().showMsg("Ошибка при вызове, повторите попытку.");
        }
    }

    private void performDigit(CharSequence digit) {
        tvDisplay.append(digit);
    }

    private void performDel() {
        if (tvDisplay.getText().length() <= 0) {
            return;
        }
        tvDisplay.setText(tvDisplay.getText().subSequence(0, tvDisplay.getText().length() - 1));
    }
}
