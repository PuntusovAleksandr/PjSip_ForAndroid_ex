package me.boger.pjsua2.pjsip;

import android.util.Log;

import org.pjsip.pjsua2.Account;
import org.pjsip.pjsua2.AccountConfig;
import org.pjsip.pjsua2.AuthCredInfo;
import org.pjsip.pjsua2.AuthCredInfoVector;
import org.pjsip.pjsua2.Buddy;
import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.Endpoint;
import org.pjsip.pjsua2.EpConfig;
import org.pjsip.pjsua2.StringVector;
import org.pjsip.pjsua2.TransportConfig;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsip_status_code;
import org.pjsip.pjsua2.pjsip_transport_type_e;

/**
 * Created by bogerchan on 2016/1/27.
 */
public class SipServer implements SipObservable {

    private Endpoint ep = new Endpoint();
    public SipObservable observer;
    private EpConfig epConfig = new EpConfig();
    private TransportConfig sipTpConfig = new TransportConfig();
    private final int SIP_PORT = 6000;
    private Account acc;
    private AccountConfig accCfg;
    private SipCall currentCall;

    static {
        System.loadLibrary("pjsua2");
        Log.d("SipServer", "Library loaded");
    }

    public SipServer(SipObservable observable) {
        this.observer = observable;
        init();
    }

    private void init() {
        try {
            ep.libCreate();
            ep.libInit(epConfig);
            ep.transportCreate(pjsip_transport_type_e.PJSIP_TRANSPORT_UDP,
                    sipTpConfig);
            ep.transportCreate(pjsip_transport_type_e.PJSIP_TRANSPORT_TCP,
                    sipTpConfig);
        } catch (Exception e) {
            Log.e("SipServer", "init", e);
            return;
        }
        sipTpConfig.setPort(SIP_PORT);
        //TODO load confs
        try {
            ep.libStart();
        } catch (Exception e) {
            Log.e("SipServer", "init", e);
        }
    }

    public void createAcc(String addr, String user, String pwd) {
        acc = new SipAccount(this);
        accCfg = new AccountConfig();
        modifyAccCfg(addr, user, pwd, null);
        try {
            acc.create(accCfg);
        } catch (Exception e) {
            Log.e("SipServer", "createAcc", e);
        }
    }

    public void modifyAcc(String addr, String user, String pwd) {
        if (acc == null || accCfg == null) {
            createAcc(addr, user, pwd);
            return;
        }
        modifyAccCfg(addr, user, pwd, null);
        try {
            acc.modify(accCfg);
        } catch (Exception e) {
            Log.e("SipServer", "modifyAcc", e);
        }
    }

    private AccountConfig modifyAccCfg(String addr, String user, String pwd, String proxy) {
        accCfg.setIdUri(String.format("sip:%1$s@%2$s", user, addr));
        accCfg.getRegConfig().setRegistrarUri(String.format("sip:%1$s", addr));
        AuthCredInfoVector creds = accCfg.getSipConfig().
                getAuthCreds();
        creds.clear();
        if (user.length() != 0) {
            creds.add(new AuthCredInfo("Digest", "*", user, 0,
                    pwd));
        }
        StringVector proxies = accCfg.getSipConfig().getProxies();
        proxies.clear();
        if (proxy != null && proxy.length() != 0) {
            proxies.add(proxy);
        }
        /* Enable ICE */
        accCfg.getNatConfig().setIceEnabled(true);
        return accCfg;
    }

    public void notifyCallState(SipCall call) {
        if (currentCall == null || call.getId() != currentCall.getId())
            return;

        CallInfo ci;
        try {
            ci = call.getInfo();
        } catch (Exception e) {
            ci = null;
        }
        notifyCallState(ci);
        if (ci != null &&
                ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED) {
            currentCall = null;
        }
    }

    public AccountConfig getAccountConfig() {
        return accCfg;
    }

    public String getServerAddress() {
        if (accCfg == null || accCfg.getIdUri() == null) {
            return "";
        }
        return accCfg.getIdUri().substring(accCfg.getIdUri().indexOf('@') + 1);
    }

    public String getUsername() {
        if (accCfg == null || accCfg.getSipConfig().getAuthCreds().size() <= 0) {
            return "";
        }
        return accCfg.getSipConfig().getAuthCreds().get(0).getUsername();
    }

    public String getPassword() {
        if (accCfg == null || accCfg.getSipConfig().getAuthCreds().size() <= 0) {
            return "";
        }
        return accCfg.getSipConfig().getAuthCreds().get(0).getData();
    }

    public boolean makeCall(String user) {
        if (currentCall != null) {
            Log.d("SipServer", "makeCall-current call is not null!");
            return false;
        }
        SipCall call = new SipCall(acc, -1, this, ep);
        CallOpParam prm = new CallOpParam(true);

        try {
            call.makeCall(String.format("sip:%1$s@%2$s", user, accCfg.getIdUri()), prm);
        } catch (Exception e) {
            call.delete();
            return false;
        }
        currentCall = call;
        return true;
    }

    public boolean hangupCurrentCall() {
        if (currentCall == null) {
            return true;
        }
        CallOpParam prm = new CallOpParam();
        prm.setStatusCode(pjsip_status_code.PJSIP_SC_DECLINE);
        try {
            currentCall.hangup(prm);
        } catch (Exception e) {
            Log.e("SipServer", "hangupCurrentCall", e);
            return false;
        }
        return true;
    }

    @Override
    public void notifyRegState(pjsip_status_code code, String reason, int expiration) {
        observer.notifyRegState(code, reason, expiration);
    }

    @Override
    public void notifyIncomingCall(Call call) {
        if (currentCall != null) {
            call.delete();
            Log.d("ProxyObserver", "notifyIncomingCall-current call is not null!");
            return;
        }
        observer.notifyIncomingCall(call);
    }

    @Override
    public void notifyCallState(Call call) {
        observer.notifyCallState(call);
        try {
            CallInfo ci = call.getInfo();
            if (ci.getState() ==
                    pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED) {
                call.delete();
            }
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void notifyCallState(CallInfo callInfo) {
        observer.notifyCallState(callInfo);
    }

    @Override
    public void notifyCallMediaState(Call call) {
        observer.notifyCallMediaState(call);
    }

    @Override
    public void notifyBuddyState(Buddy buddy) {
        observer.notifyBuddyState(buddy);
        notifyCallState(currentCall);
    }
}
