package com.bm.main.fpl.templates.topsheet;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * Created by andrea on 23/08/16.
 */
public class TopSheetDialogFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TopSheetDialog(getContext(), getTheme());
    }
}
