package me.boger.pjsua2.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.pjsip.pjsua2.Buddy;
import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.pjsip_status_code;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.boger.pjsua2.MyApplication;
import me.boger.pjsua2.R;
import me.boger.pjsua2.pjsip.SipObservable;
import me.boger.pjsua2.pjsip.SipServer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SipFragment extends BaseFragment implements View.OnClickListener, SipObservable {

    public static final String TAG = "SipFragment";

    @Bind(R.id.tv_display)
    TextView tvDisplay;
    @Bind(R.id.tv_dialer_1)
    TextView tvDialer1;
    @Bind(R.id.tv_dialer_2)
    TextView tvDialer2;
    @Bind(R.id.tv_dialer_3)
    TextView tvDialer3;
    @Bind(R.id.tv_dialer_4)
    TextView tvDialer4;
    @Bind(R.id.tv_dialer_5)
    TextView tvDialer5;
    @Bind(R.id.tv_dialer_6)
    TextView tvDialer6;
    @Bind(R.id.tv_dialer_7)
    TextView tvDialer7;
    @Bind(R.id.tv_dialer_8)
    TextView tvDialer8;
    @Bind(R.id.tv_dialer_9)
    TextView tvDialer9;
    @Bind(R.id.tv_dialer_dial)
    TextView tvDialerDial;
    @Bind(R.id.tv_dialer_del)
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
        init();
        rootView = view;
    }

    private void init() {
        MyApplication.instance.setSipServer(new SipServer(this));
        tvDialer1.setOnClickListener(this);
        tvDialer2.setOnClickListener(this);
        tvDialer3.setOnClickListener(this);
        tvDialer4.setOnClickListener(this);
        tvDialer5.setOnClickListener(this);
        tvDialer6.setOnClickListener(this);
        tvDialer7.setOnClickListener(this);
        tvDialer8.setOnClickListener(this);
        tvDialer9.setOnClickListener(this);
        tvDialerDial.setOnClickListener(this);
        tvDialerDel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialer_dial:
                break;
            case R.id.tv_dialer_del:
                performDel();
                break;
            default:
                if (v instanceof TextView) {
                    performDigit(((TextView) v).getText());
                }
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
        //TODO
        System.out.println("notifyIncomingCall");
        System.out.println(call);
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

    @Override
    public void notifyBuddyState(Buddy buddy) {
        System.out.println("notifyBuddyState");
        System.out.println(buddy);
    }
}
