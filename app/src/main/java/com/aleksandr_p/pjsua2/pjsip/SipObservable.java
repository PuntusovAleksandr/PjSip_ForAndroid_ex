package com.aleksandr_p.pjsua2.pjsip;

import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.pjsip_status_code;

/**
 * Created by bogerchan on 2016/1/27.
 */
public interface SipObservable {
    void notifyRegState(pjsip_status_code code, String reason,
                        int expiration);

    void notifyIncomingCall(Call call);

    void notifyCallState(Call call);

    void notifyCallState(CallInfo callInfo);

    void notifyCallMediaState(Call call);
}
