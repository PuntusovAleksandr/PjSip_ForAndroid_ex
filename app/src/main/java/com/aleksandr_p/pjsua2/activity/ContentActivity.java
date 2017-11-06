package com.aleksandr_p.pjsua2.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aleksandr_p.pjsua2.App;
import com.aleksandr_p.pjsua2.R;
import com.aleksandr_p.pjsua2.fragment.SipCallFragment;
import com.aleksandr_p.pjsua2.fragment.SipFragment;
import com.aleksandr_p.pjsua2.pjsip.SipObservable;
import com.aleksandr_p.pjsua2.pjsip.SipServer;

import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsip_status_code;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentActivity extends FragmentActivity
        implements ContentView,
        SipObservable {

    private ContentPresenter mPresenter;
    private FragmentManager fragmentManager;

    private SipFragment mSipFragment;
    private SipCallFragment mSipCallFragment;
    public static final int SIP_KEY = 1;
    public static final int SIP_CALL_KEY = 2;

    @BindView(R.id.tv_conf)
    TextView tvConf;
    @BindView(R.id.tv_close)
    TextView tvClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        this.mPresenter = new ContentPresenterImpl(this);
        fragmentManager = getSupportFragmentManager();
        Log.d("SipCall_", "setFragment: __2__ ");
        setFragment(SIP_KEY);

        App.instance.setSipServer(new SipServer(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.instance.getSipServer().removeObserver(this);
        App.instance.getSipServer().deinit();
    }

    @Override
    public void showMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void switchFragment(final int key) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("SipCall_", "setFragment: __3__ ");
                setFragment(key);
            }
        });
    }

    public void setFragment(int key) {
        Log.d("SipCall_", "setFragment: ____ " + key);
        setDefFragment();
        switch (key) {
            case SIP_KEY:
                mSipFragment = (SipFragment) fragmentManager.findFragmentByTag(SipFragment.TAG);
                if (mSipFragment == null) {
                    mSipFragment = new SipFragment();
                }
                addContent(mSipFragment, SipFragment.TAG);
                break;
            case SIP_CALL_KEY:
                mSipCallFragment = (SipCallFragment) fragmentManager.findFragmentByTag(SipCallFragment.TAG);
                if (mSipCallFragment == null) {
                    mSipCallFragment = new SipCallFragment();
                }
                addContent(mSipCallFragment, SipCallFragment.TAG);
                break;
        }
    }

    public void addContent(Fragment fragment, String tag) {
        if (null == fragment) {
            throw new IllegalArgumentException("WTF,Fragment is empty!!");
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        enableAnimation(ft);
        ft.replace(R.id.fl_content, fragment, tag)
                .commit();
    }

    private void setDefFragment() {
        if (mSipFragment != null) {
            fragmentManager.beginTransaction().remove(mSipFragment).commit();
            mSipFragment = null;
        }
        if (mSipCallFragment != null) {
            fragmentManager.beginTransaction().remove(mSipCallFragment).commit();
            mSipCallFragment = null;
        }
    }

    private void enableAnimation(FragmentTransaction transaction) {
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        showMsg("跑到了Activity了...");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick({R.id.tv_conf, R.id.tv_close})
    public void onClicksCiews2(View v) {
        switch (v.getId()) {
            case R.id.tv_conf:
//                mPresenter.openConfDialog();
                App.instance.getSipServer().modifyAcc(
                        ContentPresenterImpl.HOST,
                        ContentPresenterImpl.ESER,
                        ContentPresenterImpl.PASSWORD);
                break;
            case R.id.tv_close:
                mPresenter.closeSipServer();
                break;

        }
    }

//    =====================================================

    @Override
    public void notifyRegState(pjsip_status_code code, String reason, int expiration) {
        Log.d("SipCall_", "SipObservable: _SIP_FRAGMENT_ " + String.valueOf(mSipFragment));
        Log.d("SipCall_", "SipObservable: notifyRegState code " + code);
        Log.d("SipCall_", "SipObservable: notifyRegState reason " + reason);
        Log.d("SipCall_", "SipObservable: notifyRegState expiration " + expiration);
        String msgStr = expiration == 0 ? "Unregistration" : "Registration";
        msgStr += code.swigValue() / 100 == 2 ? " successful" : (" failed: " + reason);
        final String finalMsgStr = msgStr;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("SipCall_", "SipObservable: Unregistration/Registration " + finalMsgStr);
                showMsg(finalMsgStr);
            }
        });
    }

    @Override
    public void notifyIncomingCall(final Call call) {
        Log.d("SipCall_", "SipObservable: notifyIncomingCall call " + call.toString());
        if (mSipFragment != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CallOpParam prm = new CallOpParam();
                        prm.setStatusCode(pjsip_status_code.PJSIP_SC_RINGING);
                        call.answer(prm);
                    } catch (Exception e) {
                        Log.d("SipCall_", " ERROR notifyIncomingCall " + e.getMessage());
                    }
                    setFragment(SIP_CALL_KEY);
                }
            });
        }
    }

    @Override
    public void notifyCallState(Call call) {
        Log.d("SipCall_", "SipObservable: notifyCallState call " + call.getId());
        System.out.println("notifyCallState");
        System.out.println(call);
    }

    @Override
    public void notifyCallState(final CallInfo callInfo) {
        Log.d("SipCall_", "SipObservable: notifyCallState callInfo " + callInfo.getId());
        System.out.println("notifyCallState");
        System.out.println(callInfo);
        Log.d("SipCall_", "SipObservable: notifyCallState--- " + callInfo.getState().toString());

        if (mSipCallFragment != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (callInfo.getState() == pjsip_inv_state.PJSIP_INV_STATE_EARLY) {
                        mSipCallFragment.tvAnswer.setVisibility(View.VISIBLE);
                        mSipCallFragment.tvDisplay.setText(callInfo.getRemoteUri());
                        Log.d("SipCall_", "SipObservable: callInfo " + callInfo.getRemoteUri());
                    } else if (callInfo.getState() == pjsip_inv_state.PJSIP_INV_STATE_CONFIRMED) {
                        Log.d("SipCall_", "SipObservable: callInfo " + callInfo.getState());
                        showMsg("Phone CONFIRMED");
                    } else if (callInfo.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED) {
                        mSipCallFragment.performReject();
                        Log.d("SipCall_", "SipObservable: callInfo " + callInfo.getState());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mSipCallFragment != null) {
                                    Log.d("SipCall_", "setFragment: __1__ ");
                                    setFragment(SIP_KEY);
                                }
                            }
                        }, 500);
                    } else {
                        Log.d("SipCall_", "SipObservable: ELSE " + callInfo.getState());
                        showMsg(String.valueOf(callInfo.getState()));
                    }
                }
            });
        }
    }

    @Override
    public void notifyCallMediaState(Call call) {
        Log.d("SipCall_", "SipObservable: notifyCallMediaState call " + call.toString());
        System.out.println("notifyCallMediaState");
        System.out.println(call);
    }
}
