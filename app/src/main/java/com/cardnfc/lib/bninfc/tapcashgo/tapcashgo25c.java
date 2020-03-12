package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
//import p000a.p005b.tapcashgo20a;

public class tapcashgo25c implements tapcashgointerface24e {
    private HttpsURLConnection f84a;

    public tapcashgo25c(@Nullable Proxy proxy, String str, int i, String str2, int i2) throws MalformedURLException,IOException {
        if (proxy == null) {
            this.f84a = (HttpsURLConnection) new URL("https", str, i, str2).openConnection();
        } else {
            this.f84a = (HttpsURLConnection) new URL("https", str, i, str2).openConnection(proxy);
        }
        tapcashgo25c_voidb(i2);
    }

    private void tapcashgo25c_voidb(int i) {
        this.f84a.setConnectTimeout(i);
        this.f84a.setReadTimeout(i);
        this.f84a.setUseCaches(false);
        this.f84a.setDoOutput(true);
        this.f84a.setDoInput(true);
    }

    public void tapcashgointerface24e_voida() {
        this.f84a.disconnect();
    }

    @NonNull
    public List tapcashgointerface24e_listb() {
        Map headerFields = this.f84a.getHeaderFields();
        Set<String> keySet = headerFields.keySet();
        List linkedList = new LinkedList();
        for (String str : keySet) {
            List list = (List) headerFields.get(str);
            for (int i = 0; i < list.size(); i++) {
                linkedList.add(new tapcashgo20a(str, (String) list.get(i)));
            }
        }
        return linkedList;
    }

    public int tapcashgointerface24e_intc() throws IOException {
        return this.f84a.getResponseCode();
    }

    public void tapcashgointerface24e_voida(String str, String str2) {
        this.f84a.setRequestProperty(str, str2);
    }

    public void tapcashgointerface24e_voida(String str) throws ProtocolException {
        this.f84a.setRequestMethod(str);
    }

    public void tapcashgointerface24e_voida(int i) {
        this.f84a.setFixedLengthStreamingMode(i);
    }

    public OutputStream tapcashgointerface24e_outputstreamd() throws IOException {
        return this.f84a.getOutputStream();
    }

    public InputStream tapcashgointerface24e_inputstreame() throws IOException {
        return this.f84a.getInputStream();
    }

    public InputStream tapcashgointerface24e_inputstreamf() {
        return this.f84a.getErrorStream();
    }

    public void tapcashgo25c_voida(SSLSocketFactory sSLSocketFactory) {
        this.f84a.setSSLSocketFactory(sSLSocketFactory);
    }
}
