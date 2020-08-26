package com.cardnfc.lib.bninfc.tapcashgo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.bm.main.scm.R;
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.utils.PreferenceClass;
import com.cardnfc.lib.bninfc.tapcashgo.card.Card;
import com.cardnfc.lib.bninfc.tapcashgo.provider.CardProvider;

import java.util.Date;

public class ReadingTagActivity extends BaseActivity {

    private static final String TAG =ReadingTagActivity.class.getSimpleName() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_tag);
        a(getIntent());
    }
    public void onNewIntent(@NonNull Intent intent) {
        a(intent);
    }
     static Tag tag;
     static byte[] byteArrayExtra;
     static boolean z;
     @Nullable
     static String string;
    private void a(@NonNull Intent paramIntent) {
        try
        {
             tag = paramIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byteArrayExtra = paramIntent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
//            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
           z = PreferenceClass.getBoolean("update", false);
            string = PreferenceClass.getString("host_address", "");
//            new AsyncTask<Void, String, Uri>()
//            {
//
//                 ReadingTagActivity f2295e;
//                private Exception exception;
//
//
//
//                protected Uri doInBackground(Void... paramAnonymousVarArgs)
//                {
//                    try {
//                        Card a = Card.card(byteArrayExtra, tag, z, string);
//                        String a2 = tapcashgo783m.tapcashgo783m_a(a.Cardelement_f().getOwnerDocument());
//                        Log.i("cardXml", "card is " + a2);
//                        String a3 = tapcashgo783m.tapcashgo783m_a(a.Cardbyte_b());
//                        Log.i("tagId", "tagID " + a3);
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put("type", Integer.valueOf(a.mo712a().Cardint_a()));
//                        contentValues.put("serial", a3);
//                        contentValues.put("data", a2);
//                        contentValues.put("scanned_at", Long.valueOf(a.Carddate_c().getTime()));
//                        Uri insert = this.f2295e.getContentResolver().insert(CardProvider.CardProvideruri, contentValues);
//                        Editor edit = PreferenceManager.getDefaultSharedPreferences(this.f2295e).edit();
//                        edit.putString("last_read_id", a3);
//                        edit.putLong("last_read_at", new Date().getTime());
//                        edit.commit();
//                        return insert;
//                    } catch (Exception e) {
//                        this.exception = e;
//                        return null;
//                    }
//                }
//
//                protected void onPostExecute(Uri paramAnonymousUri)
//                {
//                    Log.i("post", "execute");
//                    if (this.exception == null) {
//                        this.f2295e.startActivity(new Intent("android.intent.action.VIEW", paramAnonymousUri));
//                        this.f2295e.finish();
//                    } else if (this.exception instanceof tapcashgo779l) {
//                        new AlertDialog.Builder(this.f2295e).setTitle("Unsupported Tag").setMessage(((tapcashgo779l) this.exception).getMessage()).setCancelable(false).setPositiveButton("OK",  new DialogInterface.OnClickListener(){
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                f2295e.finish();
//                            }
//                        }).show();
//                    } else {
//                        tapcashgo783m.tapcashgo783m_b(this.f2295e, this.exception);
//                    }
//                }
//            }.execute(new Void[0]);
new AsyncUpdateBalanceBNI(this).execute();
        }
        catch (Exception e)
        {
            tapcashgo783m.tapcashgo783m_b(this, e);
        }
    }


    static class AsyncUpdateBalanceBNI extends AsyncTask<Void, String, Uri> {

//        final boolean z = PreferenceClass.getBoolean("update", false);
//        final String string = PreferenceClass.getString("host_address", "");
//        final byte[] byteArrayExtra = tag.getId();
        //protected AsyncUpdateBalanceBNI(){
//
//}
        Context context;
        public AsyncUpdateBalanceBNI(Context mContext){
            this.context=mContext;

        }
        private Exception exception;

        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(MainActivity.this,
//                    "ProgressDialog",
//                    "Wait for "+time.getText().toString()+ " seconds");
        }

        @Nullable
        @Override
        protected Uri doInBackground(Void... voids) {
            try {
                Card a = Card.card(byteArrayExtra, tag, z, string);
                assert a != null;
                String a2 = tapcashgo783m.tapcashgo783m_a(a.Cardelement_f().getOwnerDocument());
                Log.i("cardXml", "card is " + a2);
                String a3 = tapcashgo783m.tapcashgo783m_a(a.Cardbyte_b());
                Log.i("tagId", "tagID " + a3);
                ContentValues contentValues = new ContentValues();
                contentValues.put("type", a.cardenum_a().Cardint_a());
                contentValues.put("serial", a3);
                contentValues.put("data", a2);
                contentValues.put("scanned_at", a.Carddate_c().getTime());
                Uri insert = context.getContentResolver().insert(CardProvider.CardProvideruri, contentValues);

               PreferenceClass.putString("last_read_id", a3);
                PreferenceClass.putLong("last_read_at", new Date().getTime());

                return insert;
            } catch (Exception e) {
                this.exception = e;
                Log.e(TAG, "doInBackground: ", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Uri result) {
            // execution of result of Long time consuming operation
//            progressDialog.dismiss();
//            finalResult.setText(result);
            Log.i("post", "execute");
            if (this.exception == null) {
                context.startActivity(new Intent("android.intent.action.VIEW", result));
                ((ReadingTagActivity)context).finish();
            } else if (this.exception instanceof tapcashgo779l) {
                new AlertDialog.Builder(context).setTitle("Unsupported Tag").setMessage(this.exception.getMessage()).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((ReadingTagActivity)context).finish();
                    }
                }).show();
            } else {
                tapcashgo783m.tapcashgo783m_b(  ((ReadingTagActivity)context), this.exception);
            }
        }
    }
}
