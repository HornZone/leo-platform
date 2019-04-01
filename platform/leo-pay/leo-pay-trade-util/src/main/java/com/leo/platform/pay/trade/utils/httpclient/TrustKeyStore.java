package com.leo.platform.pay.trade.utils.httpclient;

import javax.net.ssl.TrustManagerFactory;

/**
 * <b>功能说明: </b>
 */
public class TrustKeyStore {
    private TrustManagerFactory trustManagerFactory;

    TrustKeyStore(TrustManagerFactory trustManagerFactory) {
        this.trustManagerFactory = trustManagerFactory;
    }

    TrustManagerFactory getTrustManagerFactory() {
        return trustManagerFactory;
    }
}
