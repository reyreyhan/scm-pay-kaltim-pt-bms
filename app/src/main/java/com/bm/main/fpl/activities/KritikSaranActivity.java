package com.bm.main.fpl.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.scm.R;
import com.bm.main.fpl.adapters.ListGridSaranAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.SaranKategoriModel;
import com.bm.main.fpl.templates.toast.MyDynamicToast;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KritikSaranActivity extends BaseActivity implements JsonObjectResponseCallback, ListGridSaranAdapter.OnClickProduk {
    private static final String TAG = KritikSaranActivity.class.getSimpleName();
    RecyclerView recycler_list_komplain;
    ListGridSaranAdapter listGridSaranAdapter;
    @NonNull
    ArrayList<SaranKategoriModel.Response_value> data = new ArrayList<>();
    SaranKategoriModel saranKategoriModel;
    AppCompatButton button_lanjutkan;
    MaterialEditText materialEditTextSaran;
    RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    String kategoriSaran = null;
    LinearLayout lin_content;
    FrameLayout loading_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kritik_saran);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Komplain");
        init(0);
        loading_bar = findViewById(R.id.loading_bar);
        lin_content = findViewById(R.id.lin_content);
        materialEditTextSaran = findViewById(R.id.materialEditTextSaran);
        button_lanjutkan = findViewById(R.id.button_lanjutkan);
        button_lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(KritikSaranActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    if (kategoriSaran == null) {

                        recycler_list_komplain.setAnimation(animShake);
                        recycler_list_komplain.startAnimation(animShake);
                        Device.vibrate(KritikSaranActivity.this);
                        MyDynamicToast.warningMessage(KritikSaranActivity.this, "Kategori Saran & Kritik Belum Dipilih");
                        return;
                    }

                    if (materialEditTextSaran.getText().toString().isEmpty()) {

                        materialEditTextSaran.setAnimation(animShake);
                        materialEditTextSaran.startAnimation(animShake);

                        materialEditTextSaran.setError("Saran & Kritik Tidak Boleh Kosong");
                        Device.vibrate(KritikSaranActivity.this);
                        return;

                    }
                    requestKomplain();
                }
            }
        });
        recycler_list_komplain = findViewById(R.id.recycler_list_komplain);
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recycler_list_komplain.setHasFixedSize(false);
        recycler_list_komplain.setLayoutManager(mLayoutManager);

        listGridSaranAdapter = new ListGridSaranAdapter(data, this, this);
        recycler_list_komplain.setAdapter(listGridSaranAdapter);
        requestListKategori();
    }

    private void requestKomplain() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestInfo(materialEditTextSaran.getText().toString(), kategoriSaran));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.REQUEST_SARAN, this);
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view =(FrameLayout) View.inflate(this,R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    private void requestListKategori() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestTypeKomplain());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.LIST_KATEGORI_SARAN, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rumah, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right_drawer) {

            openTopDialog(false);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        closeKeyboard(this);

        finish();
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());


//        try {
//            if (response.getString("response_code").equals("03")) {
//                if (actionCode == ActionCode.LIST_KATEGORI_SARAN) {
//                    loading_bar.setVisibility(View.GONE);
//                } else if (actionCode == ActionCode.REQUEST_SARAN) {
//                    closeProgressBarDialog();
//                }
//                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//            } else {
//

                if (actionCode == ActionCode.LIST_KATEGORI_SARAN) {
                    loading_bar.setVisibility(View.GONE);
                    recycler_list_komplain.setVisibility(View.VISIBLE);
                    data.clear();
                    saranKategoriModel = gson.fromJson(response.toString(), SaranKategoriModel.class);
                    if (saranKategoriModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                        //ArrayList<ProdukPulsaRegularModel.Response_value> dataProduk = new ArrayList<>();
                        data.addAll(saranKategoriModel.getResponse_value());


                        listGridSaranAdapter.notifyDataSetChanged();


                    }
                } else if (actionCode == ActionCode.REQUEST_SARAN) {
                    closeProgressBarDialog();
                    try {
                        if (response.getString("response_code").equals(ResponseCode.SUCCESS)) {
                            new_popup_alert(KritikSaranActivity.this, "SUKSES", response.getString("response_desc"));

                        } else {
                            new_popup_alert(KritikSaranActivity.this, "INFO", response.getString("response_desc"));
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "JSONException: " + e.toString());
                    }
                    listGridSaranAdapter.clearSelections();
                    materialEditTextSaran.setText("");
                }
//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }

    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        if (actionCode == ActionCode.REQUEST_SARAN) {
            closeProgressBarDialog();
        }
    }

    @Override
    public void onClickProduk(@NonNull SaranKategoriModel.Response_value produk) {
        Log.d(TAG, "onClickProduk: " + produk.getId_kategori());
        kategoriSaran = produk.getId_kategori();
    }
}
