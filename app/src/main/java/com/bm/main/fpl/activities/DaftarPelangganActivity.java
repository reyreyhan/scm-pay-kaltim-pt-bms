package com.bm.main.fpl.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.adapters.PelangganAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.PelangganModel;
import com.bm.main.fpl.templates.DividerItemDecoration;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.crowdfire.cfalertdialog.CFAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;

public class DaftarPelangganActivity extends BaseActivity implements ProgressResponseCallback, PelangganAdapter.OnClickNotification, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private static final String TAG = DaftarPelangganActivity.class.getSimpleName();
    RecyclerView recyclerViewPelanggan;

    @NonNull
    ArrayList<PelangganModel.Response_value> data = new ArrayList<>();
    PelangganModel pelangganModel;
    String product;
    String prefProduct;
    int hint;
    SearchView searchView;
    SearchManager searchManager;
    PelangganAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayout layout_no_connection, layout_data_empty;
    private AppCompatButton button_coba_lagi;
    LinearLayout linMain;
    TextView txtHeader, txtSub;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pelanggan);
        intent = getIntent();
        product = intent.getStringExtra("product");
        prefProduct = "Pelanggan" + product;
        hint = intent.getIntExtra("hint", -1);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("title"));
        init(1);
        linMain = findViewById(R.id.linMain);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        layout_no_connection = findViewById(R.id.layout_no_connection);
        layout_data_empty = findViewById(R.id.layout_data_empty);
        txtHeader = layout_data_empty.findViewById(R.id.txtHeader);
        txtSub = layout_data_empty.findViewById(R.id.txtSub);
        button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerViewPelanggan.getVisibility() == View.VISIBLE) {
                    recyclerViewPelanggan.setVisibility(View.GONE);
                }
                if (layout_no_connection.getVisibility() == View.VISIBLE) {
                    layout_no_connection.setVisibility(View.GONE);
                }
                if (linMain.getVisibility() == View.GONE) {
                    linMain.setVisibility(View.VISIBLE);

                }
                if (mShimmerViewContainer.getVisibility() == View.GONE) {
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    mShimmerViewContainer.startShimmerAnimation();

                }
                requestDaftarPelanggan();
            }
        });

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView);
        searchView.setFocusable(false);
        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setTypeface(tfLight);
        searchAutoComplete.setTextSize(14);
        recyclerViewPelanggan = findViewById(R.id.recyclerViewPelanggan);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.hint_cari_pelanggan));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewPelanggan.setHasFixedSize(false);
        recyclerViewPelanggan.setLayoutManager(linearLayoutManager);
        recyclerViewPelanggan.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new PelangganAdapter(data, this, this);
        recyclerViewPelanggan.setAdapter(adapter);

        if (PreferenceClass.getJSONObject(prefProduct).length() > 0) {
            if (layout_no_connection.getVisibility() == View.VISIBLE) {
                layout_no_connection.setVisibility(View.GONE);
            }
            if (layout_data_empty.getVisibility() == View.VISIBLE) {
                layout_data_empty.setVisibility(View.GONE);
            }
            if (linMain.getVisibility() == View.GONE) {
                linMain.setVisibility(View.VISIBLE);
            }
            if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmerAnimation();
            }
            pelangganModel = gson.fromJson(PreferenceClass.getJSONObject(prefProduct).toString(), PelangganModel.class);

            data.clear();
            data.addAll(pelangganModel.getResponse_value());
            //  setData(gameModel.getData());
            adapter.notifyDataSetChanged();
            recyclerViewPelanggan.setVisibility(View.VISIBLE);
        }

        requestDaftarPelanggan();
    }

    private void requestDaftarPelanggan() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestListPelanggan(product));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_PELANGGAN, this);
    }


    private void requestHapusDaftarPelanggan(String id_history) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestHapusListPelanggan(id_history, product));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PreferenceClass.putJSONObject(prefProduct, new JSONObject());
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_PELANGGAN, this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        closeKeyboard(this);
        Intent intent = new Intent();

        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        if (layout_no_connection.getVisibility() == View.VISIBLE) {
            layout_no_connection.setVisibility(View.GONE);
        }
        if (linMain.getVisibility() == View.GONE) {
            linMain.setVisibility(View.VISIBLE);
        }
        if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
            mShimmerViewContainer.stopShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.GONE);
        }

        if (actionCode == ActionCode.LIST_PELANGGAN) {
            Log.d(TAG, "onSuccess: " + response.toString());
            pelangganModel = gson.fromJson(response.toString(), PelangganModel.class);

            switch (pelangganModel.getResponse_code()) {
                case ResponseCode.SUCCESS:
                    JSONObject obj = PreferenceClass.getJSONObject(prefProduct);
                    JSONArray array = new JSONArray();
                    try {
                        array = obj.getJSONArray("response_value");
                    } catch (JSONException e) {
                    }
                    if (array.length() != pelangganModel.getResponse_value().size()) {
                        data.clear();
                        PreferenceClass.putJSONObject(prefProduct, response);
                        data.addAll(pelangganModel.getResponse_value());
                        adapter.notifyDataSetChanged();
                    }
                    recyclerViewPelanggan.setVisibility(View.VISIBLE);
                    if (pelangganModel.getResponse_value().size() == 0) {
                        layout_data_empty.setVisibility(View.VISIBLE);
                        txtHeader.setText("Data Pelanggan");
                        txtSub.setText("Tidak Ditemukan");
                        recyclerViewPelanggan.setVisibility(View.GONE);
                    }
                    break;
                case "03":
                    new_popup_alert_session(this, "Informasi", pelangganModel.getResponse_desc());
                    break;
                default:
                    layout_data_empty.setVisibility(View.VISIBLE);
                    txtHeader.setText("Data Pelanggan");
                    txtSub.setText("Tidak Ditemukan");
                    break;
            }
        }
    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        if (responseCode.equals(NETWORK_ERROR)) {
            if (actionCode == ActionCode.LIST_PELANGGAN) {
                if (PreferenceClass.getJSONObject(product).length() > 0) {

                } else {
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    linMain.setVisibility(View.GONE);
                    layout_data_empty.setVisibility(View.GONE);
                    layout_no_connection.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onUpdate(int actionCode, long bytesRead, long totalSize, boolean done) {

    }

    @Override
    public void onClickNotif(@NonNull PelangganModel.Response_value pelanggan) {
        closeKeyboard(this);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("id_pel", pelanggan.getId_pelanggan_1());
        intent.putExtra("no_sambungan", pelanggan.getId_pelanggan_2());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClickNotifTrash(@NonNull PelangganModel.Response_value notif) {

        new_popup_alert_two_button_yesno("Information", "Apakah Anda yakin akan menghapus data pelanggan " + notif.getNama_pelanggan() + " ?", notif);
    }

    @Override
    public boolean onClose() {
        adapter.getFilter().filter("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        closeKeyboard(this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onDestroy();
    }

    public void new_popup_alert_two_button_yesno(String title, String pesan, @NonNull final PelangganModel.Response_value notif) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(this, R.layout.dialog_header_response_layout, parent);


        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay_dark))
                .setTitle(title)
                .setMessage(pesan)
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(true);

        builder.addButton("Ya", -1, ContextCompat.getColor(this, R.color.md_green_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                requestHapusDaftarPelanggan(notif.getId_history());
                dialog.cancel();
            }
        });

        builder.addButton("Tidak", -1, ContextCompat.getColor(this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        if (!this.isFinishing()) {
            builder.show();
        }
    }
}
