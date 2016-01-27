package me.boger.pjsua2.pjsip;

import android.util.Log;

import org.pjsip.pjsua2.Account;
import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.OnIncomingCallParam;
import org.pjsip.pjsua2.OnInstantMessageParam;
import org.pjsip.pjsua2.OnRegStateParam;

/**
 * Created by bogerchan on 2016/1/27.
 */
public class SipAccount extends Account {

    private SipObservable observable;

    public SipAccount(long cPtr, boolean cMemoryOwn, SipObservable observable) {
        super(cPtr, cMemoryOwn);
        this.observable = observable;
    }

    public SipAccount(SipObservable observable) {
        super();
        this.observable = observable;
    }

    @Override
    public void onIncomingCall(OnIncomingCallParam prm) {
        Call call = new Call(this, prm.getCallId());
        observable.notifyIncomingCall(call);
    }

    @Override
    public void onRegState(OnRegStateParam prm) {
        observable.notifyRegState(prm.getCode(), prm.getReason(),
                prm.getExpiration());
    }

    @Override
    public void onInstantMessage(OnInstantMessageParam prm) {
        Log.d("onInstantMessage", "======== Incoming pager ======== ");
        Log.d("onInstantMessage", "From     : " + prm.getFromUri());
        Log.d("onInstantMessage", "To       : " + prm.getToUri());
        Log.d("onInstantMessage", "Contact  : " + prm.getContactUri());
        Log.d("onInstantMessage", "Mimetype : " + prm.getContentType());
        Log.d("onInstantMessage", "Body     : " + prm.getMsgBody());
    }
}
