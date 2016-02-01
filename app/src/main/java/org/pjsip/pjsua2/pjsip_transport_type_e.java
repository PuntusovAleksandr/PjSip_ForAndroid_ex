/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.8
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public final class pjsip_transport_type_e {
    public final static pjsip_transport_type_e PJSIP_TRANSPORT_UNSPECIFIED = new pjsip_transport_type_e("PJSIP_TRANSPORT_UNSPECIFIED");
    public final static pjsip_transport_type_e PJSIP_TRANSPORT_UDP = new pjsip_transport_type_e("PJSIP_TRANSPORT_UDP");
    public final static pjsip_transport_type_e PJSIP_TRANSPORT_TCP = new pjsip_transport_type_e("PJSIP_TRANSPORT_TCP");
    public final static pjsip_transport_type_e PJSIP_TRANSPORT_TLS = new pjsip_transport_type_e("PJSIP_TRANSPORT_TLS");
    public final static pjsip_transport_type_e PJSIP_TRANSPORT_SCTP = new pjsip_transport_type_e("PJSIP_TRANSPORT_SCTP");
    public final static pjsip_transport_type_e PJSIP_TRANSPORT_LOOP = new pjsip_transport_type_e("PJSIP_TRANSPORT_LOOP");
    public final static pjsip_transport_type_e PJSIP_TRANSPORT_LOOP_DGRAM = new pjsip_transport_type_e("PJSIP_TRANSPORT_LOOP_DGRAM");
    public final static pjsip_transport_type_e PJSIP_TRANSPORT_START_OTHER = new pjsip_transport_type_e("PJSIP_TRANSPORT_START_OTHER");
    public final static pjsip_transport_type_e PJSIP_TRANSPORT_IPV6 = new pjsip_transport_type_e("PJSIP_TRANSPORT_IPV6", pjsua2JNI.PJSIP_TRANSPORT_IPV6_get());
    public final static pjsip_transport_type_e PJSIP_TRANSPORT_UDP6 = new pjsip_transport_type_e("PJSIP_TRANSPORT_UDP6", pjsua2JNI.PJSIP_TRANSPORT_UDP6_get());
    public final static pjsip_transport_type_e PJSIP_TRANSPORT_TCP6 = new pjsip_transport_type_e("PJSIP_TRANSPORT_TCP6", pjsua2JNI.PJSIP_TRANSPORT_TCP6_get());
    public final static pjsip_transport_type_e PJSIP_TRANSPORT_TLS6 = new pjsip_transport_type_e("PJSIP_TRANSPORT_TLS6", pjsua2JNI.PJSIP_TRANSPORT_TLS6_get());

    public final int swigValue() {
        return swigValue;
    }

    public String toString() {
        return swigName;
    }

    private pjsip_transport_type_e(String swigName) {
        this.swigName = swigName;
        this.swigValue = swigNext++;
    }

    private pjsip_transport_type_e(String swigName, int swigValue) {
        this.swigName = swigName;
        this.swigValue = swigValue;
        swigNext = swigValue + 1;
    }

    private static pjsip_transport_type_e[] swigValues = {PJSIP_TRANSPORT_UNSPECIFIED, PJSIP_TRANSPORT_UDP, PJSIP_TRANSPORT_TCP, PJSIP_TRANSPORT_TLS, PJSIP_TRANSPORT_SCTP, PJSIP_TRANSPORT_LOOP, PJSIP_TRANSPORT_LOOP_DGRAM, PJSIP_TRANSPORT_START_OTHER, PJSIP_TRANSPORT_IPV6, PJSIP_TRANSPORT_UDP6, PJSIP_TRANSPORT_TCP6, PJSIP_TRANSPORT_TLS6};
    private static int swigNext = 0;
    private final int swigValue;
    private final String swigName;
}

