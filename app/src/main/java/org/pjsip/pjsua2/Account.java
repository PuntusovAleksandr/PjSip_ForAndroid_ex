/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.8
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

import java.io.Serializable;

public class Account implements Serializable {
    private transient long swigCPtr;
    protected transient boolean swigCMemOwn;

    protected Account(long cPtr, boolean cMemoryOwn) {
        swigCMemOwn = cMemoryOwn;
        swigCPtr = cPtr;
    }

    public static long getCPtr(Account obj) {
        return (obj == null) ? 0 : obj.swigCPtr;
    }

    protected void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (swigCPtr != 0) {
            if (swigCMemOwn) {
                swigCMemOwn = false;
                pjsua2JNI.delete_Account(swigCPtr);
            }
            swigCPtr = 0;
        }
    }

    public Account() {
        this(pjsua2JNI.new_Account(), true);
        pjsua2JNI.Account_director_connect(this, swigCPtr, swigCMemOwn, true);
    }

    public void create(AccountConfig cfg) throws java.lang.Exception {
        pjsua2JNI.Account_create__SWIG_1(swigCPtr, this, AccountConfig.getCPtr(cfg), cfg);
    }

    public void modify(AccountConfig cfg) throws java.lang.Exception {
        pjsua2JNI.Account_modify(swigCPtr, this, AccountConfig.getCPtr(cfg), cfg);
    }

    public int getId() {
        return pjsua2JNI.Account_getId(swigCPtr, this);
    }

    public void onIncomingCall(OnIncomingCallParam prm) {
    }

    public void onRegState(OnRegStateParam prm) {

    }
}
