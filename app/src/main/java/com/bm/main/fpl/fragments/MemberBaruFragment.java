package com.bm.main.fpl.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import androidx.core.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.AppCompatDialog;
//import android.support.v7.widget.AppCompatButton;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.DialogUtils;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.activities.HomeActivity;
import com.bm.main.fpl.activities.ListKabupatenActivity;
import com.bm.main.fpl.activities.ListPropinsiActivity;
import com.bm.main.fpl.adapters.ListGridPaketAdapter;
import com.bm.main.fpl.adapters.ListPaketOutletAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.PaketModel;
import com.bm.main.fpl.models.PaketModelModelResponse_valueTipe_loket_detail;
import com.bm.main.fpl.models.PaketModelModelResponse_valueTipe_loket_footer;
import com.bm.main.fpl.templates.AutoScaleTextView;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.templates.toast.MyDynamicToast;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.fpl.utils.StringJson;
import com.bm.main.fpl.utils.saring_karakter;
import com.bm.main.materialedittext.MaterialEditText;
import com.bumptech.glide.Glide;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//import com.bm.main.fpl.activities.BaseActivity;


public class MemberBaruFragment extends Fragment implements ProgressResponseCallback, ListGridPaketAdapter.OnClickProduk {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = MemberBaruFragment.class.getSimpleName();
    private AppCompatDialog mPromoDialog;
    @Nullable
    String paketCode = "", paketName = "", propCode = "", kabCode = "", paketAlias = "", paketUrl = "", paketHarga = "";
    PaketModel paketModel;
    RecyclerView recyclerViewPaketOutlet;
    @Nullable
    ListGridPaketAdapter listGridPaketAdapter;
    RecyclerView.LayoutManager mLayoutManagerPaket;
    @NonNull
    ArrayList<PaketModel.Response_value> dataPaket = new ArrayList<>();
    TextView textViewNominalOutlet, textViewTypeOutlet;

    LinearLayout linPaket;
    private long mLastClickTime = 0;
    MaterialEditText materialEditTextNama, materialEditTextNoHp, materialEditTextEmail, materialEditTextAlamat, materialEditTextPropinsi, materialEditTextKota, materialEditTextKodePos;
    AppCompatButton button_registrasi;

    ListPaketOutletAdapter listPaketOutletAdapter;
    @NonNull
    ArrayList<PaketModelModelResponse_valueTipe_loket_detail> dataDetailPaket = new ArrayList<>();
    @NonNull
    ArrayList<PaketModelModelResponse_valueTipe_loket_footer> dataDetailPaketFooter = new ArrayList<>();

    TextView imageInfoPaket;
    TextView textViewErrorPaket;
    //  int propinsi = 1;
    //  int kabupaten = 2;
    // TODO: Rename and change types of parameters
    @Nullable
    private String mParam1;
    @Nullable
    private String mParam2;
    public View rootView;
    private Context context;

    public ImageView img_notification, img_menu;
    public FrameLayout frame_bagde_count_notif;
    public TextView textViewCountNotif;
    public ImageView imageViewRatingOutlet;
    public TextView textViewTypeOutletToolbar;
    public LinearLayout linGradeOutlet;
    @NonNull
    String product = "list_paket_ajak_bisnis";
    private AVLoadingIndicatorView avi;
    private int check = 0;
    private RelativeLayout relNotif;

    public MemberBaruFragment() {
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_membar_baru, container, false);
        context = getActivity();
        ((BaseActivity) getActivity()).logEventFireBase("Home", "Ajak Bisnis", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);

        frame_bagde_count_notif = rootView.findViewById(R.id.frame_bagde_count_notif);
        textViewCountNotif = rootView.findViewById(R.id.textViewCountNotif);
        imageViewRatingOutlet = rootView.findViewById(R.id.imageViewRatingOutlet);
        textViewTypeOutletToolbar = rootView.findViewById(R.id.textViewTypeOutletToolbar);
        linGradeOutlet = rootView.findViewById(R.id.linGradeOutlet);

        linGradeOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) context).toggleGradeOutlet(context);
            }
        });

        img_menu = rootView.findViewById(R.id.img_menu);
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) context).openTopDialog(true);
            }
        });
        img_notification = rootView.findViewById(R.id.img_notification);
        relNotif = rootView.findViewById(R.id.relNotif);

        relNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) context).toggleNotification();
            }
        });

        textViewErrorPaket = rootView.findViewById(R.id.textViewErrorPaket);
        avi = rootView.findViewById(R.id.avi);
        materialEditTextNama = rootView.findViewById(R.id.materialEditTextNama);
        materialEditTextNoHp = rootView.findViewById(R.id.materialEditTextNoHp);
        materialEditTextEmail = rootView.findViewById(R.id.materialEditTextEmail);
        materialEditTextAlamat = rootView.findViewById(R.id.materialEditTextAlamat);
        materialEditTextPropinsi = rootView.findViewById(R.id.materialEditTextPropinsi);
        materialEditTextPropinsi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(@NonNull View v, boolean hasFocus) {
                Log.d("member", "onFocusChange: " + hasFocus + " " + v.getId());
                if (hasFocus) {
                    Intent intent = new Intent(context, ListPropinsiActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, ActionCode.LIST_PROPINSI);
                }
            }
        });

        materialEditTextPropinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListPropinsiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, ActionCode.LIST_PROPINSI);
            }
        });

        materialEditTextKota = rootView.findViewById(R.id.materialEditTextKota);
        materialEditTextKota.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (materialEditTextPropinsi.getEditableText().toString().isEmpty()) {
                        materialEditTextPropinsi.setAnimation(HomeActivity.animShake);
                        materialEditTextPropinsi.startAnimation(HomeActivity.animShake);
                        materialEditTextPropinsi.setError("Propinsi Tidak Boleh Kosong");
                        materialEditTextKota.requestFocus();
                        Device.vibrate(materialEditTextKota.getContext());
                        return;
                    }
                    Intent intent = new Intent(context, ListKabupatenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("propCode", propCode);
                    startActivityForResult(intent, ActionCode.LIST_KABUPATEN);
                }

            }
        });

        materialEditTextKota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materialEditTextPropinsi.getEditableText().toString().isEmpty()) {
                    materialEditTextPropinsi.setAnimation(HomeActivity.animShake);
                    materialEditTextPropinsi.startAnimation(HomeActivity.animShake);
                    materialEditTextPropinsi.setError("Propinsi Tidak Boleh Kosong");
                    Device.vibrate(materialEditTextKota.getContext());

                    return;
                }
                Intent intent = new Intent(context, ListKabupatenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("propCode", propCode);
                startActivityForResult(intent, ActionCode.LIST_KABUPATEN);
            }
        });

        materialEditTextKodePos = rootView.findViewById(R.id.materialEditTextKodePos);

        recyclerViewPaketOutlet = rootView.findViewById(R.id.recyclerViewPaketOutlet);
        textViewNominalOutlet = rootView.findViewById(R.id.textViewNominalOutlet);
        textViewTypeOutlet = rootView.findViewById(R.id.textViewTypeOutlet);
        linPaket = rootView.findViewById(R.id.linPaket);

        button_registrasi = rootView.findViewById(R.id.button_registrasi);
        button_registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAddMember();
            }
        });

        mLayoutManagerPaket = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewPaketOutlet.setHasFixedSize(false);
        recyclerViewPaketOutlet.setLayoutManager(mLayoutManagerPaket);

        listGridPaketAdapter = new ListGridPaketAdapter(dataPaket, getActivity(), this);
        recyclerViewPaketOutlet.setAdapter(listGridPaketAdapter);

        requestListPaket();

        imageInfoPaket = rootView.findViewById(R.id.imageInfoPaket);
        imageInfoPaket.setOnClickListener(view -> {
            @SuppressLint("InflateParams")
            ViewGroup parent = rootView.findViewById(R.id.linPromo);
            final View viewx = View.inflate(context, R.layout.popup_detail_paket, parent);
            DialogUtils.openPaketDetailDialog(context, viewx, dataDetailPaket, dataDetailPaketFooter, paketAlias, paketUrl, paketHarga, listPaketOutletAdapter);
        });

        clearText();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        getTypeOutletToolbarMember(context);
        if (PreferenceClass.getInt("notifSize", 0) > 0) {
            frame_bagde_count_notif.setVisibility(View.VISIBLE);
        } else {
            frame_bagde_count_notif.setVisibility(View.GONE);
        }
    }

    public void getTypeOutletToolbarMember(@NonNull Context context) {

        textViewTypeOutletToolbar.setText(PreferenceClass.getUser().getAlias().toUpperCase());
        switch (PreferenceClass.getUser().getRating()) {
            case "1":
                Glide.with(this).load(R.drawable.ic_grade_satu).into(imageViewRatingOutlet);
                break;
            case "3":
                Glide.with(this).load(R.drawable.ic_grade_tiga).into(imageViewRatingOutlet);
                break;
            case "5":
                Glide.with(this).load(R.drawable.ic_grade_lima).into(imageViewRatingOutlet);
                break;
        }
    }

    private void callAddMember() {
        if (materialEditTextNama.getEditableText().toString().trim().isEmpty() || materialEditTextNama.getEditableText().toString().trim().equals("")) {
            materialEditTextNama.setAnimation(HomeActivity.animShake);
            materialEditTextNama.startAnimation(HomeActivity.animShake);
            materialEditTextNama.setError("Nama Tidak Boleh Kosong");
            Device.vibrate(materialEditTextNama.getContext());
            return;
        }

        if (paketCode.isEmpty()) {
            recyclerViewPaketOutlet.setAnimation(HomeActivity.animShake);
            recyclerViewPaketOutlet.startAnimation(HomeActivity.animShake);
            if (textViewErrorPaket.getVisibility() == View.GONE) {
                textViewErrorPaket.setVisibility(View.VISIBLE);

            }
            Device.vibrate(textViewErrorPaket.getContext());
            return;
        }

        if (materialEditTextNoHp.getEditableText().toString().trim().isEmpty() || materialEditTextNoHp.getEditableText().toString().trim().equals("")) {
            materialEditTextNoHp.setAnimation(HomeActivity.animShake);
            materialEditTextNoHp.startAnimation(HomeActivity.animShake);
            materialEditTextNoHp.setError("No Handphone Tidak Boleh Kosong");
            Device.vibrate(materialEditTextNoHp.getContext());
            return;
        }

        boolean isValid = new saring_karakter().isEmailValid(materialEditTextEmail.getEditableText().toString());
        if (!isValid) {
            // requestAddMember();

            materialEditTextEmail.setAnimation(HomeActivity.animShake);
            materialEditTextEmail.startAnimation(HomeActivity.animShake);
            materialEditTextEmail.setError("Format email salah, Pastikan format email : contoh@sbf.com");
            Device.vibrate(materialEditTextEmail.getContext());
            return;
        }

        if (materialEditTextAlamat.getEditableText().toString().trim().isEmpty() || materialEditTextAlamat.getEditableText().toString().trim().equals("")) {
            materialEditTextAlamat.setAnimation(HomeActivity.animShake);
            materialEditTextAlamat.startAnimation(HomeActivity.animShake);
            materialEditTextAlamat.setError("Alamat Tidak Boleh Kosong");
            Device.vibrate(materialEditTextAlamat.getContext());
            return;
        }

        if (materialEditTextPropinsi.getEditableText().toString().trim().isEmpty() || materialEditTextPropinsi.getEditableText().toString().trim().equals("") || propCode.equals("")) {
            materialEditTextPropinsi.setAnimation(HomeActivity.animShake);
            materialEditTextPropinsi.startAnimation(HomeActivity.animShake);
            materialEditTextPropinsi.setError("Propinsi Tidak Boleh Kosong");
            Device.vibrate(materialEditTextPropinsi.getContext());
            return;
        }

        if (materialEditTextKota.getEditableText().toString().trim().isEmpty() || materialEditTextKota.getEditableText().toString().trim().equals("") || kabCode.equals("")) {
            materialEditTextKota.setAnimation(HomeActivity.animShake);
            materialEditTextKota.startAnimation(HomeActivity.animShake);
            materialEditTextKota.setError("Kota/Kabupaten Tidak Boleh Kosong");
            Device.vibrate(materialEditTextKota.getContext());
            return;
        }

        if (check == 0) {
            requestAddMember();
        }
    }

    private void requestAddMember() {
        check++;
        ((BaseActivity) context).logEventFireBase("Ajak Bisnis", "Ajak Bisnis", EventParam.EVENT_ACTION_REQUEST_AJAKBISNIS, EventParam.EVENT_SUCCESS, TAG);
        StringJson stringJson = new StringJson(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestPendaftaran(
                    materialEditTextNoHp.getEditableText().toString(), materialEditTextNama.getEditableText().toString(), materialEditTextAlamat.getEditableText().toString(), propCode, kabCode, materialEditTextKodePos.getEditableText().toString(), paketCode));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse((AppCompatActivity) getActivity(), jsonObject, ActionCode.REQUEST_MEMBER, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = rootView.findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(context, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        ((HomeActivity) context).openProgressBarDialog(context, view);
    }

    private void requestListPaket() {
        StringJson stringJson = new StringJson(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestListTypetLocket());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse((AppCompatActivity) getActivity(), jsonObject, ActionCode.LIST_PRODUK, this);
    }


    @Override
    public void onSuccess(int actionCode, @NonNull final JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        if (actionCode == ActionCode.LIST_PRODUK) {
            if (avi.getVisibility() == View.VISIBLE) {
                avi.setVisibility(View.GONE);
            }
            paketModel = ((BaseActivity) context).gson.fromJson(response.toString(), PaketModel.class);
            if (paketModel.getResponse_code().equals(ResponseCode.SUCCESS)) {

                if (recyclerViewPaketOutlet.getVisibility() == View.GONE) {
                    recyclerViewPaketOutlet.setVisibility(View.VISIBLE);
                }

                dataPaket.clear();
                dataPaket.addAll(paketModel.getResponse_value());

                listGridPaketAdapter.notifyDataSetChanged();
            } else {
                if (recyclerViewPaketOutlet.getVisibility() == View.VISIBLE) {
                    recyclerViewPaketOutlet.setVisibility(View.GONE);
                }
                if (textViewErrorPaket.getVisibility() == View.GONE) {
                    textViewErrorPaket.setVisibility(View.VISIBLE);
                    textViewErrorPaket.setText(paketModel.getResponse_desc());
                }
            }
        } else if (actionCode == ActionCode.REQUEST_MEMBER) {
            ((HomeActivity) context).closeProgressBarDialog();
            check = 0;
            try {
                if (response.getString("response_code").equals(ResponseCode.SUCCESS)) {
                    ((BaseActivity) context).logEventFireBase("Ajak Bisnis", "Ajak Bisnis", EventParam.EVENT_ACTION_RESULT_AJAKBISNIS, EventParam.EVENT_SUCCESS, TAG);
                    clearText();
                    alertDialog(response.getString("response_desc"));
                    HashMap<String, String> eventMap = new HashMap<>();
                    eventMap.put("upline", PreferenceClass.getId());
                    eventMap.put("is_register", "0");
                    eventMap.put("paket", paketName);
                    eventMap.put("email", materialEditTextEmail.getEditableText().toString());
                    eventMap.put("nama", materialEditTextNama.getEditableText().toString());
                    eventMap.put("no_handphone", materialEditTextNoHp.getEditableText().toString());
                    eventMap.put("kabupaten", materialEditTextKota.getEditableText().toString());
                    eventMap.put("propinsi", materialEditTextPropinsi.getEditableText().toString());
                    SBFApplication.sendEvent(FirebaseAnalytics.Event.SIGN_UP, eventMap);
                } else {
                    ((BaseActivity) context).logEventFireBase("Ajak Bisnis", "Ajak Bisnis", EventParam.EVENT_ACTION_RESULT_AJAKBISNIS, EventParam.EVENT_NOT_SUCCESS, TAG);
                    ((BaseActivity) context).new_popup_alert(getActivity(), "Info", response.getString("response_desc"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearText() {
        check = 0;
        materialEditTextNama.setText("");
        materialEditTextNama.refreshDrawableState();
        materialEditTextNoHp.setText("");
        materialEditTextNoHp.refreshDrawableState();
        materialEditTextEmail.setText("");
        materialEditTextEmail.refreshDrawableState();
        materialEditTextAlamat.setText("");
        materialEditTextAlamat.refreshDrawableState();
        materialEditTextPropinsi.setText("");
        materialEditTextPropinsi.refreshDrawableState();
        materialEditTextKota.setText("");
        materialEditTextKota.refreshDrawableState();
        paketCode = "";
        paketAlias = "";
        paketHarga = "";
        paketUrl = "";
        paketName = "";
        propCode = "";
        kabCode = "";
        listGridPaketAdapter.mSelectedItem = -1;
        listGridPaketAdapter.notifyDataSetChanged();
        linPaket.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        check = 0;
        if (actionCode == ActionCode.REQUEST_MEMBER) {
            MyDynamicToast.errorMessage(getActivity(), responseDescription);
            ((HomeActivity) context).closeProgressBarDialog();
        }
    }

    @Override
    public void onUpdate(int actionCode, long bytesRead, long totalSize, boolean done) {
    }

    @Override
    public void onClickProduk(@NonNull PaketModel.Response_value produk) {
        paketUrl = produk.getTipe_loket_url();
        paketAlias = produk.getTipe_loket_alias();
        paketCode = produk.getTipe_loket_code();
        paketName = produk.getTipe_loket_alias();
        paketHarga = "Rp " + FormatString.CurencyIDR(produk.getTipe_loket_harga());
        textViewNominalOutlet.setText(paketHarga);
        textViewTypeOutlet.setText(produk.getTipe_loket_name());

        switch (paketAlias) {
            case "BASIC":
                imageInfoPaket.setText("Lihat Kelengkapan Paket Basic");
                break;
            case "PROFESSIONAL":
                imageInfoPaket.setText("Lihat Kelengkapan Paket Pro");
                break;
            case "ENTERPRISE":
                imageInfoPaket.setText("Lihat Kelengkapan Paket Enterprise");
                break;
        }

        dataDetailPaket.clear();
        for (int x = 0; x < produk.getTipe_loket_detail().length; x++) {
            dataDetailPaket.add(produk.getTipe_loket_detail()[x]);
        }

        dataDetailPaketFooter.clear();
        for (int x = 0; x < produk.getTipe_loket_detail_footer().length; x++) {
            dataDetailPaketFooter.add(produk.getTipe_loket_detail_footer()[x]);
        }

        listPaketOutletAdapter = new ListPaketOutletAdapter(dataDetailPaket, context);

        if (linPaket.getVisibility() == View.GONE) {
            linPaket.setVisibility(View.VISIBLE);
            linPaket.animate().translationY(linPaket.getHeight());
        }

        if (textViewErrorPaket.getVisibility() == View.VISIBLE) {
            textViewErrorPaket.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == ActionCode.LIST_PROPINSI) {
                Log.d(TAG, "onActivityResult: " + data.getStringExtra("kodePropinsi"));
                propCode = data.getStringExtra("kodePropinsi");
                materialEditTextPropinsi.setText(data.getStringExtra("namaPropinsi").toString());

            } else if (requestCode == ActionCode.LIST_KABUPATEN) {
                kabCode = data.getStringExtra("kodeKabupaten");
                materialEditTextKota.setText(data.getStringExtra("namaKabupaten").toString());
            }
        }
    }

    private void alertDialog(String response) {
        Log.d(TAG, "alertDialog: " + response);
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity());
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(((BaseActivity) context), R.color.black_overlay))
                .setTitle("Berhasil")
                .setMessage(response)
                .setTextGravity(Gravity.CENTER_HORIZONTAL)
                .setHeaderView(R.layout.dialog_header_layout)
                .setCancelable(false);

        builder.addButton("Tutup", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, ((BaseActivity) context).getButtonGravity(1), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.cancel();
                ((HomeActivity) context).onBackPressed();
            }
        });
        builder.show();
    }
}