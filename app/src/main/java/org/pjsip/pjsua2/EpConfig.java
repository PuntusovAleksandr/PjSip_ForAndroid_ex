/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.8
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class EpConfig extends PersistentObject {
    private transient long swigCPtr;

    protected EpConfig(long cPtr, boolean cMemoryOwn) {
        super(pjsua2JNI.EpConfig_SWIGUpcast(cPtr), cMemoryOwn);
        swigCPtr = cPtr;
    }

    protected static long getCPtr(EpConfig obj) {
        return (obj == null) ? 0 : obj.swigCPtr;
    }

    protected void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (swigCPtr != 0) {
            if (swigCMemOwn) {
                swigCMemOwn = false;
                pjsua2JNI.delete_EpConfig(swigCPtr);
            }
            swigCPtr = 0;
        }
        super.delete();
    }

    public EpConfig() {
        this(pjsua2JNI.new_EpConfig(), true);
    }

}
