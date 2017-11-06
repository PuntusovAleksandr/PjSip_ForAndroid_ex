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
import com.aleksandr_p.pjsua2.pjsip.SipServer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aleksandr_p.pjsua2.activity.ContentActivity.SIP_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class SipCallFragment extends BaseFragment{

    @BindView(R.id.tv_call_display)
    public TextView tvDisplay;
    @BindView(R.id.tv_call_answer)
    public TextView tvAnswer;
    @BindView(R.id.tv_call_reject)
    public TextView tvReject;

    private SipServer server;

    public static final String TAG = "SipCallFragment";

    public SipCallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sip_call, container, false);
        ButterKnife.bind(this, view);

        server = App.instance.getSipServer();
        if (server == null || server.getCurrentCall() == null) {
            Log.d("SipCall_", "setFragment: __7__ ");
            popBackStack();
            return null;
        }

        return view;
    }

    @OnClick({R.id.tv_call_answer, R.id.tv_call_reject})
    public void onClicksCiews22(View v) {
        switch (v.getId()) {
            case R.id.tv_call_answer:
                performAnswer(v);
                break;
            case R.id.tv_call_reject:
                performReject();
        }
    }

    public void performReject() {
        if (server.hangupCurrentCall()) {
            Log.d("SipCall_", "setFragment: __6__ ");
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

    public void popBackStack() {
        if (hasContentView()) {
            Log.d("SipCall_", "setFragment: __4__ ");
            getContentView().switchFragment(SIP_KEY);
        }
    }

}
