package com.aleksandr_p.pjsua2.pjsip;

import org.pjsip.pjsua2.Account;
import org.pjsip.pjsua2.Endpoint;
import org.pjsip.pjsua2.OnIncomingCallParam;
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
}
