package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.Nullable;

import com.bm.main.fpl.activities.BaseActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class tapcashgo775i {
    @Nullable
    private static tapcashgo775i tapcashgo775i = null;
    private SSLContext sslContext = SSLContext.getInstance("TLS");

    @Nullable
    public static synchronized tapcashgo775i tapcashgo775i_a() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        tapcashgo775i tapcashgo775I;
        synchronized (tapcashgo775i.class) {
            if (tapcashgo775i == null) {
                tapcashgo775i = new tapcashgo775i();
            }
            tapcashgo775I = tapcashgo775i;
        }
        return tapcashgo775I;
    }

    private tapcashgo775i() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        InputStream bufferedInputStream = new BufferedInputStream(BaseActivity.getInstanceBaseActivity().getAssets().open("mbankbni.bks"));
        KeyStore instance = KeyStore.getInstance("BKS");
        instance.load(bufferedInputStream, "n3wmob1le".toCharArray());
        TrustManagerFactory instance2 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        instance2.init(instance);
        this.sslContext.init(null, instance2.getTrustManagers(), null);
    }

    public SSLContext sslContext() {
        return this.sslContext;
    }
}
