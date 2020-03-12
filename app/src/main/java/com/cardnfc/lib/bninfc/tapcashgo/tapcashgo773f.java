package com.cardnfc.lib.bninfc.tapcashgo;

//import android.support.v7.p022a.C0503a.C0502j;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import org.json.JSONObject;

import java.net.URL;
import java.security.GeneralSecurityException;

//import a.p005b.p006a.tapcashgo15j;
//import a.p005b.tapcashgo17b;
//import a.p005b.p006a.tapcashgo15j;
//import a.p005b.p006a.tapcashgo18l;
//import a.p005b.p007b.tapcashgo25c;
//import a.p005b.p007b.tapcashgo26d;


public class tapcashgo773f {
    @NonNull
    public static String url_BNIMBankService_new = "https://newmb.bni.co.id/BPMbankservice/BNIMBankService.asmx";
    private final String url_BNIMBankService = "http://BNIMBankService.bni.co.id/";

    private class tapcashgo773f_classa {
        final tapcashgo773f tapcashgo773f;
        private String b;
        private tapcashgo15j tapcashgo15j;
        private int d = 40000;
        private String e;

        @NonNull
        private final tapcashgo18l tapcashgo18l_a(tapcashgo15j tapcashgo15J) {
          //  tapcashgo18l tapcashgo18L = new tapcashgo18l(C0502j.AppCompatTheme_ratingBarStyleSmall);
            tapcashgo18l tapcashgo18L = new tapcashgo18l(110);
           // tapcashgo18l tapcashgo18L = new tapcashgo18l();
            tapcashgo18L.f64o = true;
            tapcashgo18L.f62m = true;
            tapcashgo18L.tapcashgo18l_voida(false);
            tapcashgo18L.tapcashgo17b_voida((Object) tapcashgo15J);
            return tapcashgo18L;
        }

        public tapcashgo773f_classa(tapcashgo773f tapcashgo773F, String str, tapcashgo15j tapcashgo15J, String str2) {
            this.tapcashgo773f = tapcashgo773F;
            this.b = str;
            this.tapcashgo15j = tapcashgo15J;
            this.e = str2;
        }

        @Nullable
        private final tapcashgo26d tapcashgo26d() {
            tapcashgo26d tapcashgo26D;
            Exception exception;
            try {
                URL url = new URL(this.b);
                tapcashgo775i a = tapcashgo775i.tapcashgo775i_a();
                tapcashgo26d tapcashgo26D2 = new tapcashgo26d(url.getHost(), 443, url.getPath(), this.d);
                try {
                    ((tapcashgo25c) tapcashgo26D2.mo27a()).tapcashgo25c_voida(a.sslContext().getSocketFactory());
                    return tapcashgo26D2;
                } catch (Exception e) {
                    Exception exception2 = e;
                    tapcashgo26D = tapcashgo26D2;
                    exception = exception2;
                    Log.i("httpstransportError", exception.getMessage());
                    return tapcashgo26D;
                }
            } catch (Exception e2) {
                exception = e2;
                tapcashgo26D = null;
                Log.i("httpstransportError", exception.getMessage());
                return tapcashgo26D;
            }
        }
//
        @NonNull
        protected String tapcashgo773f_stringa() {
            String str = "{\"responseCode\":\"FAIL\",\"OKContent\":\"\",\"FAILContent\":{\"soaFailCode\":\"91\",\"soaFailDescription\":\"Host Timeout\"}}";
            try {
                tapcashgo26d b = tapcashgo26d();
                tapcashgo17b a = tapcashgo18l_a(this.tapcashgo15j);
                b.tapcashgo22g_voida(this.e, a);
                str = tapcashgo752a.tapcashgo752a_stringb(((tapcashgo15j) a.tapcashgo17b_objecta).b_(0).toString());
                Log.i("resultService", str);
                return str;
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.d("ExceptionTrace", "" + e2.getMessage());
                return "{\"responseCode\":\"FAIL\",\"OKContent\":\"\",\"FAILContent\":{\"soaFailCode\":\"\",\"soaFailDescription\":\"Check Your Connection\"}}";
            }
        }
    }
//
    @NonNull
    public String tapcashgo773f_stringa(@NonNull JSONObject jSONObject, String str) {
        Log.d("tapcashgo773f_stringa", "tapcashgo773f_stringa: "+jSONObject+" "+str);
        try {
            getClass();
           // return str;
            tapcashgo15j tapcashgo15J = new tapcashgo15j("http://BNIMBankService.bni.co.id/", str);
            tapcashgo15J.tapcashgo15j("parameters", tapcashgo752a.tapcashgo752a_stringa(jSONObject.toString()));
            String str2 = url_BNIMBankService_new;
            StringBuilder stringBuilder = new StringBuilder();
            getClass();
            return new tapcashgo773f_classa(this, str2, tapcashgo15J, stringBuilder.append("http://BNIMBankService.bni.co.id/").append(str).toString()).tapcashgo773f_stringa();
        } catch (GeneralSecurityException e) {
            Log.i("SecurityException", e.getMessage());
            return "{\"responseCode\":\"FAIL\",\"OKContent\":\"\",\"FAILContent\":{\"soaFailCode\":\"\",\"soaFailDescription\":\"No Encrypt \"}}";
        } catch (Exception e2) {
            return "{\"responseCode\":\"FAIL\",\"OKContent\":\"\",\"FAILContent\":{\"soaFailCode\":\"\",\"soaFailDescription\":\"No Respon \"}}";
        }
    }
}
