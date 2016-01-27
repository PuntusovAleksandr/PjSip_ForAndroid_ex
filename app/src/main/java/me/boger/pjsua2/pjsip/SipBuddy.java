package me.boger.pjsua2.pjsip;

import org.pjsip.pjsua2.Buddy;

/**
 * Created by bogerchan on 2016/1/27.
 */
public class SipBuddy extends Buddy {

    private SipObservable observaber;

    public SipBuddy(long cPtr, boolean cMemoryOwn, SipObservable observaber) {
        super(cPtr, cMemoryOwn);
        this.observaber = observaber;
    }

    public SipBuddy(SipObservable observaber) {
        this.observaber = observaber;
    }

    @Override
    public void onBuddyState() {
        observaber.notifyBuddyState(this);
    }
}
