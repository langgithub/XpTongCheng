package com.anjuke.mobile.sign;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class TrustAllCerts implements X509TrustManager {

    @Override    
    public void checkServerTrusted(X509Certificate[] chain, String authType) {}

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { }

    @Override
    public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}    
}