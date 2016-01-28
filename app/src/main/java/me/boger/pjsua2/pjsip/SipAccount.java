package me.boger.pjsua2.pjsip;

import android.util.Log;

import org.pjsip.pjsua2.Account;
import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.Endpoint;
import org.pjsip.pjsua2.OnIncomingCallParam;
import org.pjsip.pjsua2.OnInstantMessageParam;
import org.pjsip.pjsua2.OnRegStateParam;

/**
 * Created by bogerchan on 2016/1/27.
 */
public class SipAccount extends Account {

    private SipObservable observable;
    private Endpoint ep;

    public SipAccount(long cPtr, boolean cMemoryOwn, SipObservable observable, Endpoint ep) {
        super(cPtr, cMemoryOwn);
        this.observable = observable;
        this.ep = ep;
    }

    public SipAccount(SipObservable observable, Endpoint ep) {
        super();
        this.observable = observable;
        this.ep = ep;
    }

    @Override
    public void onIncomingCall(OnIncomingCallParam prm) {
        SipCall call = new SipCall(this, prm.getCallId(), observable, ep);
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
