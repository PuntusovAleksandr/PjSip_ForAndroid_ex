package com.aleksandr_p.pjsua2.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aleksandr_p.pjsua2.MyApplication;
import com.aleksandr_p.pjsua2.R;
import com.aleksandr_p.pjsua2.activity.ContentPresenterImpl;
import com.aleksandr_p.pjsua2.pjsip.SipObservable;
import com.aleksandr_p.pjsua2.pjsip.SipServer;
import com.aleksandr_p.pjsua2.utils.StorageUtils;

import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.pjsip_status_code;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SipFragment extends BaseFragment implements SipObservable {

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
        return inflater.inflate(R.layout.fragment_sip, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        rootView = view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.instance.setSipServer(new SipServer(this));
        loadConfs();
    }

    private void loadConfs() {
        String addr = StorageUtils.getString(MyApplication.instance.getApplicationContext(), "addr");
        String username = StorageUtils.getString(MyApplication.instance.getApplicationContext(), "username");
        String password = StorageUtils.getString(MyApplication.instance.getApplicationContext(), "password");
        if (addr == null || username == null || password == null) {
            saveConfs(ContentPresenterImpl.HOST, ContentPresenterImpl.ESER, ContentPresenterImpl.PASSWORD);
            loadConfs();
            return;
        }
        MyApplication.instance.getSipServer().createAcc(addr, username, password);
    }


    private void saveConfs(String addr, String username, String password) {
        StorageUtils.createStorager(getActivity())
                .putString("addr", addr)
                .putString("username", username)
                .putString("password", password)
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApplication.instance.getSipServer().removeObserver(this);
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
            getContentView().showMsg("请输入要呼叫的用户名");
            return;
        }
        if (MyApplication.instance.getSipServer().makeCall(tvDisplay.getText().toString())) {
            getContentView().switchFragment(this, SipCallFragment.class, SipCallFragment.TAG);
        } else {
            getContentView().showMsg("呼叫错误，请重试");
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

    @Override
    public void notifyRegState(pjsip_status_code code, String reason, int expiration) {
        String msgStr = expiration == 0 ? "Unregistration" : "Registration";
        msgStr += code.swigValue() / 100 == 2 ? " successful" : (" failed: " + reason);
        Snackbar.make(rootView, msgStr, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void notifyIncomingCall(Call call) {
        Log.d("SipFragment", "notifyIncomingCall");
        try {
            CallOpParam prm = new CallOpParam();
            prm.setStatusCode(pjsip_status_code.PJSIP_SC_RINGING);
            call.answer(prm);
        } catch (Exception e) {
            Log.e("SipFragment", "notifyIncomingCall", e);
        }
        if (hasContentView()) {
            getContentView().switchFragment(this, SipCallFragment.class, SipCallFragment.TAG);
        }
    }

    @Override
    public void notifyCallState(Call call) {
        System.out.println("notifyCallState");
        System.out.println(call);
    }

    @Override
    public void notifyCallState(CallInfo callInfo) {
        System.out.println("notifyCallState");
        System.out.println(callInfo);
    }

    @Override
    public void notifyCallMediaState(Call call) {
        System.out.println("notifyCallMediaState");
        System.out.println(call);
    }
}
