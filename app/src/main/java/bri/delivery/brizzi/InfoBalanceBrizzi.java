package bri.delivery.brizzi;

import android.content.Intent;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

public class InfoBalanceBrizzi {
    @Nullable
    private brizziparsea f31f;

    private void getBalance(@NonNull Intent intent)
    {
        String action = intent.getAction();
        if (("android.nfc.action.TAG_DISCOVERED".equals(action)) || ("android.nfc.action.TECH_DISCOVERED".equals(action)))
        {
            Tag paramIntent = intent.getParcelableExtra("android.nfc.extra.TAG");
            String[] localObject = paramIntent.getTechList();
            String str = IsoDep.class.getName();
            IsoDep isoDep = null;

            int i = 0;
            while (i < localObject.length)
            {
                if (str.equals(localObject[i])) {
                    isoDep = IsoDep.get(paramIntent);
                }
                try
                {
                    isoDep.connect();
                    if (!isoDep.isConnected()) {
                        return;
                    }
                    byte[] localObjectByte = isoDep.getTag().getId();
                    this.f31f = new brizziparsea(isoDep);
                    if (this.f31f.brizziparsea_booleana())
                    {
                        this.f31f.brizziparsea_bytea(localObjectByte);
                        boolean bool = this.f31f.brizziparsea_booleanb().booleanValue();
                        if (bool) {
                            return;
                        }
                        return;
                    }
                    return;
                }
                catch (IOException ioe) {}
                i += 1;
            }
        }
        return;
    }

}