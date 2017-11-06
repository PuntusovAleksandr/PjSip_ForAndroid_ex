package com.aleksandr_p.pjsua2.pjsip;

import org.pjsip.pjsua2.Account;
import org.pjsip.pjsua2.AudioMedia;
import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallMediaInfo;
import org.pjsip.pjsua2.CallMediaInfoVector;
import org.pjsip.pjsua2.Endpoint;
import org.pjsip.pjsua2.Media;
import org.pjsip.pjsua2.OnCallMediaStateParam;
import org.pjsip.pjsua2.OnCallStateParam;
import org.pjsip.pjsua2.pjmedia_type;
import org.pjsip.pjsua2.pjsua_call_media_status;

/**
 * Created by bogerchan on 2016/1/27.
 */
public class SipCall extends Call {

    private SipObservable observer;
    private Endpoint ep;

    public SipCall(Account acc, int call_id, SipObservable observer, Endpoint ep) {
        super(acc, call_id);
        this.observer = observer;
        this.ep = ep;
    }

    @Override
    public void onCallState(OnCallStateParam prm) {
        observer.notifyCallState(this);
    }

    @Override
    public void onCallMediaState(OnCallMediaStateParam prm) {
        CallInfo ci;
        try {
            ci = getInfo();
        } catch (Exception e) {
            return;
        }

        CallMediaInfoVector cmiv = ci.getMedia();

        for (int i = 0; i < cmiv.size(); i++) {
            CallMediaInfo cmi = cmiv.get(i);
            if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_AUDIO &&
                    (cmi.getStatus() ==
                            pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE ||
                            cmi.getStatus() ==
                                    pjsua_call_media_status.PJSUA_CALL_MEDIA_REMOTE_HOLD)) {
                // unfortunately, on Java too, the returned Media cannot be
                // downcasted to AudioMedia
                Media m = getMedia(i);
                AudioMedia am = AudioMedia.typecastFromMedia(m);

                // connect ports
                try {
                    ep.audDevManager().getCaptureDevMedia().
                            startTransmit(am);
                    am.startTransmit(ep.audDevManager().
                            getPlaybackDevMedia());
                } catch (Exception e) {
                    continue;
                }
            }
        }
        observer.notifyCallMediaState(this);
    }
}
