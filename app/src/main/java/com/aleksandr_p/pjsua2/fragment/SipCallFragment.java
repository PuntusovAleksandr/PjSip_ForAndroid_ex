package com.aleksandr_p.pjsua2.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsip_status_code;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.aleksandr_p.pjsua2.MyApplication;
import com.aleksandr_p.pjsua2.R;
import com.aleksandr_p.pjsua2.pjsip.SipObservable;
import com.aleksandr_p.pjsua2.pjsip.SipServer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SipCallFragment extends BaseFragment implements SipObservable {

    @BindView(R.id.tv_call_display)
    TextView tvDisplay;
    @BindView(R.id.tv_call_answer)
    TextView tvAnswer;
    @BindView(R.id.tv_call_reject)
    TextView tvReject;

    private SipServer server;

    public static final String TAG = "SipCallFragment";

    public SipCallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sip_call, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        server = MyApplication.instance.getSipServer();
        if (server == null || server.getCurrentCall() == null) {
            popBackStack();
            return;
        }
        server.addObserver(this);
    }


    @OnClick({R.id.tv_call_answer, R.id.tv_call_reject})
    public void onClicksCiews22(View v) {
        switch (v.getId()) {
            case R.id.tv_call_answer:
                performAnswer(v);
                break;
            case R.id.tv_call_reject:
                performReject(v);
        }
    }

    private void performReject(View sender) {
        if (server.hangupCurrentCall()) {
            if (hasContentView()) {
                getContentView().showMsg("The call has hung up");
                popBackStack();
            }
        }
    }

    private void performAnswer(View sender) {
        if (server.answerCurrentCall()) {
            if (hasContentView()) {
                sender.setVisibility(View.INVISIBLE);
                getContentView().showMsg("The call has accepted");
            }
        }
    }

    @Override
    public void notifyRegState(pjsip_status_code code, String reason, int expiration) {

    }

    @Override
    public void notifyIncomingCall(Call call) {

    }

    @Override
    public void notifyCallState(Call call) {

    }

    @Override
    public void notifyCallState(CallInfo callInfo) {
        Log.d("SipCallFragment", "notifyCallState-" + callInfo.getState().toString());
        if (callInfo.getState() == pjsip_inv_state.PJSIP_INV_STATE_EARLY) {
            tvAnswer.setVisibility(View.VISIBLE);
            tvDisplay.setText(callInfo.getRemoteUri());
        } else if (callInfo.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED) {
            popBackStack();
        } else if (callInfo.getState() == pjsip_inv_state.PJSIP_INV_STATE_CONFIRMED) {
            if (hasContentView()) {
                getContentView().showMsg("电话已接通");
            }
        }
    }

    private void popBackStack() {
        if (hasContentView()) {
            getContentView().switchFragment(this, SipFragment.class, SipFragment.TAG);
        }
    }

    @Override
    public void notifyCallMediaState(Call call) {

    }
}