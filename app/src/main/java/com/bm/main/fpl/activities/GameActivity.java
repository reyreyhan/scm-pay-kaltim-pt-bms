package com.bm.main.fpl.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.scm.R;
import com.bm.main.fpl.adapters.ListGridGameAdapter;
import com.bm.main.fpl.adapters.ListGridProdukPulsaAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.GameModel;
import com.bm.main.fpl.models.PayModel;
import com.bm.main.fpl.models.ProdukModel;
import com.bm.main.fpl.models.ProdukPulsaModel;
import com.bm.main.fpl.templates.TextViewPlus;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.templates.toast.MyDynamicToast;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.materialedittext.OnCutCopyPasteListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.crowdfire.cfalertdialog.CFAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;

//import com.bm.main.fpl.utils.ExceptionHandler;
//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

public class GameActivity extends KeyboardListenerActivity implements JsonObjectResponseCallback, ProgressResponseCallback, ListGridGameAdapter.OnClickProduk, ListGridProdukPulsaAdapter.OnClickProduk, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private static final String TAG = GameActivity.class.getSimpleName();
    private static final int PICK_CONTACT = 1;
    private static final String TAGHP = "GameHP";
    private static final String TAGID = "GameID";
    RecyclerView recyclerViewProduk;

    @NonNull
    ArrayList<GameModel.Response_value> data = new ArrayList<>();
    GameModel gameModel;

    SearchView searchView;
    SearchManager searchManager;
    ListGridGameAdapter adapter;
    //    StaggeredGridLayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManagerProduk;
    //StaggeredGridLayoutManager mLayoutManagerProduk;
    @Nullable
    String kodeProduk, namaProduk, urlProduk, is_flag;
    boolean isBayar = false;

    LinearLayout linList;
    LinearLayout relPay;

    ImageView imageViewProduk;
    TextView textViewPlusNamaProduk;
    RecyclerView recyclerViewNominalProduk;
    @NonNull
    ArrayList<ProdukPulsaModel.Response_value> dataProduk = new ArrayList<>();
    ProdukPulsaModel produkModel;
    ListGridProdukPulsaAdapter adapterProduk;
    MaterialEditText materialEditTextNoHandphone;
    AVLoadingIndicatorView avi;
    ImageView imageViewAddressBook;
    @Nullable
    String kodeProdukPilihan = "";
    AppCompatButton appCompatButtonBayar;
    TextView textViewPlusSukses;
    @Nullable
    String namaProdukPilihan;
    // private LinearLayout bottom_toolbar;

    LinearLayout linMain, layout_no_connection;
    int reqTag = 0;
    @Nullable
    private String produkName;
    @NonNull
    private String product = "Game_Online";
    // private String nominal;
    ShimmerFrameLayout shimmer_view_container;
    private TextView textView;
    // private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_game);
        //  getWindow().setBackgroundDrawable(null);
        logEventFireBase(ProdukGroup.GAMEONLINE, ProdukGroup.GAMEONLINE, EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Voucher Game Online");
        init(0);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        shimmer_view_container = findViewById(R.id.shimmer_view_container);
        //    nestedScrollView = findViewById(R.id.nestedScrollView);
        linMain = findViewById(R.id.linMain);
        layout_no_connection = findViewById(R.id.layout_no_connection);
        AppCompatButton button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reqTag == 0) {
                    requestDaftarProduk();
                } else if (reqTag == 1) {
                    requestNominalProduk();

                } else {

                        requestPayment();

                }
            }
        });

        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));

        linList = findViewById(R.id.linList);
        relPay = findViewById(R.id.relPay);
        imageViewProduk = findViewById(R.id.imageViewProduk);
        textViewPlusNamaProduk = findViewById(R.id.textViewPlusNamaProduk);

        textViewPlusSukses = findViewById(R.id.textViewPlusSukses);
        appCompatButtonBayar = findViewById(R.id.appCompatButtonBayar);
        imageViewAddressBook = findViewById(R.id.imageViewAddressBook);
        materialEditTextNoHandphone = findViewById(R.id.materialEditTextNoHandphone);
        materialEditTextNoHandphone.setOnCutCopyPasteListener(new OnCutCopyPasteListener() {


            @Override
            public void onCut() {
                // Do your onCut reactions
            }

            @Override
            public void onCopy() {
                // Do your onCopy reactions
            }

            @Override
            public void onPaste() {
                String selectedNumber =materialEditTextNoHandphone.getEditableText().toString();
                // selectedNumber= selectedNumber.replaceAll("....", "$0 ");
                selectedNumber = selectedNumber.replace("-", "");
                //  phoneInput.setText(selectedNumber);
                selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                if (selectedNumber.startsWith("62")) {
                    if (!selectedNumber.startsWith("9")) {
                        selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                    }
                }
                materialEditTextNoHandphone.setText("");
//                materialEditTextNoHandphone.setCharacterDelay(10);
//                materialEditTextNoHandphone.animateText(selectedNumber);
//                materialEditTextNoHandphone.setMaskedText("**** **** **** ****");
                materialEditTextNoHandphone.setText(selectedNumber);
                // materialEditTextNoHandphone.setSelection(selectedNumber.length());
              //  requestPrefixRegular(selectedNumber);
                // Do your onPaste reactions
            }
        });
//        materialEditTextNoHandphone.addTextChangedListener(new PhoneNumberTextWatcher(materialEditTextNoHandphone));
        recyclerViewNominalProduk = findViewById(R.id.recyclerViewNominalProduk);
        avi = findViewById(R.id.avi);

        imageViewAddressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//
//                startActivityForResult(intent, ActionCode.PICK_CONTACT);
                showContacts();
            }
        });
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setFocusable(false);
        searchView.setQueryHint("Game Online");
        recyclerViewProduk = findViewById(R.id.recyclerViewProduk);
        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextSize(14);

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        textView = searchView.findViewById(id);
        textView.setTextColor(Color.BLACK);


        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewProduk.setHasFixedSize(false);
//        recyclerViewProduk.setNestedScrollingEnabled(false);
         ViewCompat.setNestedScrollingEnabled(recyclerViewProduk, false);
        //recyclerViewPropinsi.setItemViewCacheSize(1024);
        recyclerViewProduk.setLayoutManager(mLayoutManager);


        //recyclerViewPropinsi.setLayoutManager(linearLayoutManager);
//        recyclerViewPropinsi.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // mLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);

        adapter = new ListGridGameAdapter(data, this, this);

        recyclerViewProduk.setAdapter(adapter);

        if (PreferenceClass.getJSONObject(product).length() > 0) {
            if (layout_no_connection.getVisibility() == View.VISIBLE) {
                layout_no_connection.setVisibility(View.GONE);
            }
//            if (layout_data_empty.getVisibility() == View.VISIBLE) {
//                layout_data_empty.setVisibility(View.GONE);
//            }
            if (linList.getVisibility() == View.GONE) {
                linList.setVisibility(View.VISIBLE);
            }
            if (shimmer_view_container.getVisibility() == View.GONE) {
                shimmer_view_container.setVisibility(View.VISIBLE);
                shimmer_view_container.startShimmerAnimation();
            }
            gameModel = gson.fromJson(PreferenceClass.getJSONObject(product).toString(), GameModel.class);
            if (gameModel.getResponse_value().size() > 0) {
                data.clear();
                data.addAll(gameModel.getResponse_value());
                //  setData(gameModel.getData());
                adapter.notifyDataSetChanged();
                if (shimmer_view_container.getVisibility() == View.VISIBLE) {
                    shimmer_view_container.setVisibility(View.GONE);
                    shimmer_view_container.stopShimmerAnimation();
                }
                //nestedScrollView.setVisibility(View.VISIBLE);
            }
        }
        requestDaftarProduk();


        mLayoutManagerProduk = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewNominalProduk.setHasFixedSize(false);
//        recyclerViewNominalProduk.setNestedScrollingEnabled(false);
        //  ViewCompat.setNestedScrollingEnabled(recyclerViewNominalProduk, false);
        recyclerViewNominalProduk.setLayoutManager(mLayoutManagerProduk);

        adapterProduk = new ListGridProdukPulsaAdapter(dataProduk, this, this);
        recyclerViewNominalProduk.setAdapter(adapterProduk);



        appCompatButtonBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kodeProdukPilihan.isEmpty()) {
                    recyclerViewNominalProduk.setAnimation(BaseActivity.animShake);
                    recyclerViewNominalProduk.startAnimation(BaseActivity.animShake);

                    MyDynamicToast.warningMessage(GameActivity.this, getResources().getString(R.string.warning_nominal_produk));
                    Device.vibrate(GameActivity.this);
                    return;
                } else if (materialEditTextNoHandphone.getEditableText().toString().isEmpty()) {
                    materialEditTextNoHandphone.setAnimation(BaseActivity.animShake);
                    materialEditTextNoHandphone.startAnimation(BaseActivity.animShake);
                    if (is_flag.equals("ID")) {
                        materialEditTextNoHandphone.setError("Id Mobile Legend Tidak Boleh Kosong");
                    } else {
                        materialEditTextNoHandphone.setError("No Handphone Tidak Boleh Kosong");
                    }
                    Device.vibrate(GameActivity.this);
                    return;
                }
                ViewGroup parent = findViewById(R.id.contentHost);
                View v = View.inflate(GameActivity.this, R.layout.dialog_header_pulsa_layout, parent);
                final ImageView imageViewProvider=v.findViewById(R.id.imageViewProvider);
                Glide.with(GameActivity.this).load(urlProduk).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(new DrawableImageViewTarget(imageViewProvider) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> animation) {
                        // here it's similar to RequestListener, but with less information (e.g. no model available)
                        super.onResourceReady(resource, animation);
                      //  avi.setVisibility(View.GONE);
                        // here you can be sure it's already set
                    }

                    // +++++ OR +++++
                    @Override
                    protected void setResource(Drawable resource) {
                        // this.getView().setImageDrawable(resource); is about to be called
                        super.setResource(resource);
                        imageViewProvider.setVisibility(View.VISIBLE);
                        imageViewProvider.setScaleType(ImageView.ScaleType.FIT_XY);
                       // avi.setVisibility(View.GONE);
                        // here you can be sure it's already set
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                        imageViewProvider.setVisibility(View.GONE);
                       // avi.setVisibility(View.GONE);
//
//                        imageViewProvider.setImageDrawable(ContextCompat.getDrawable(GameActivity.this, R.drawable.ic_menu_game_online));
                    }
                });

                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(GameActivity.this);
                builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                        .setBackgroundColor(ContextCompat.getColor(GameActivity.this, R.color.black_overlay))
                        .setTitle("Pembelian Voucher Game")
                        .setMessage("Apakah Anda yakin akan membeli vaucher " + produkName + " ?")
                        .setTextGravity(Gravity.CENTER)
                        .setHeaderView(v)
                        .setCancelable(true);

//                ImageView imageViewClosed = v.findViewById(R.id.imageViewClosed);
//                imageViewClosed.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.dismiss();
//                        // adapterProduk.removeData();
//                    }
//                });

                builder.addButton("Beli Sekarang", -1, ContextCompat.getColor(GameActivity.this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        // Toast.makeText(BaseActivity.this, "Neutral", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        requestPayment();

                    }
                });
                builder.onDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        adapterProduk.clearSelections();
                    }
                });
//                client.addButton("Tidak", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Toast.makeText(BaseActivity.this, "Neutral", Toast.LENGTH_SHORT).show();
//                        alertDialog.dismiss();
//                    }
//                });
                //  alertDialog =
                builder.show();
            }


        });

        requestPopUpPromo("GAME_ONLINE");
        attachKeyboardListeners();
//        Crashlytics.getInstance().crash();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmer_view_container.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        shimmer_view_container.stopShimmerAnimation();
        super.onPause();
    }

    private void requestPayment() {
        logEventFireBase(ProdukGroup.GAMEONLINE, produkName, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestPaymentGame(kodeProdukPilihan, materialEditTextNoHandphone.getEditableText().toString().replace(" ","")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponsePayment(this, jsonObject, ActionCode.PAY_PULSA, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view =(FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }


    private void requestDaftarProduk() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestProviderGame());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_PRODUK_GAME, this);
    }

    private void requestNominalProduk() {
        reqTag = 1;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestProductGame(kodeProduk));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_NOMINAL_PRODUK, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view =(FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
//        TextView text = view.findViewById(R.id.textContentProgressBar);
//        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_voice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_right_voice) {
            startVoice();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startVoice() {

        PackageManager pm = getPackageManager();
        List activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        Intent intent = new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    "Fastpay Voice Command");
            startActivityForResult(intent, RequestCode.ActionCode_VOICE_RECOGNITION);
        } else {
            showToast("Maaf,Fastpay Command Voice tidak di support di Handphone Anda");
        }


    }

    @Override
    public void onBackPressed() {
        if (bottom_toolbar.getVisibility() == View.GONE) {
            closeKeyboard(this);
        }


        if (!isBayar) {

            finish();
        } else {
            reqTag = 0;
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            isBayar = false;
            toolbar.setTitle("Voucher Game Online");
            linList.setVisibility(View.VISIBLE);
            relPay.setVisibility(View.GONE);
        }

        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


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

//    @Override
//    public void onClick(View view) {
//
//    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        if (layout_no_connection.getVisibility() == View.VISIBLE) {
            layout_no_connection.setVisibility(View.GONE);
        }
        if (linList.getVisibility() == View.GONE) {
            linList.setVisibility(View.VISIBLE);
        }
        if (shimmer_view_container.getVisibility() == View.VISIBLE) {
            shimmer_view_container.setVisibility(View.GONE);
            shimmer_view_container.stopShimmerAnimation();
        }
//        try {
//            if (response.getString("response_code").equals("03")) {
//                if (actionCode == ActionCode.LIST_NOMINAL_PRODUK || actionCode == ActionCode.PAY_PULSA) {
//                    closeProgressBarDialog();
//                }
//                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//            } else {
        if (actionCode == ActionCode.LIST_PRODUK_GAME) {

            gameModel = gson.fromJson(response.toString(), GameModel.class);
            if (gameModel.getResponse_code().equals(ResponseCode.SUCCESS)) {

                JSONObject obj = PreferenceClass.getJSONObject(product);
                JSONArray array = new JSONArray();
                try {
                    array = obj.getJSONArray("response_value");
                } catch (JSONException e) {
                    //  e.printStackTrace();
                }

                if (array.length() != gameModel.getResponse_value().size()) {
                    PreferenceClass.putJSONObject(product, response);
                    data.clear();
                    data.addAll(gameModel.getResponse_value());
                    adapter.notifyDataSetChanged();
                }
                recyclerViewProduk.setVisibility(View.VISIBLE);

            } else {

            }
            // reqTag=1;
        }
        if (actionCode == ActionCode.LIST_NOMINAL_PRODUK) {
            closeProgressBarDialog();
            dataProduk.clear();
            kodeProdukPilihan = "";
            namaProdukPilihan = "";
            produkModel = gson.fromJson(response.toString(), ProdukPulsaModel.class);
            if (produkModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                dataProduk.addAll(produkModel.getResponse_value());

                adapterProduk.clearSelections();

                toolbar.setTitle("Nominal Voucher Game Online");
                isBayar = true;
                reqTag = 2;
                linList.setVisibility(View.GONE);
                relPay.setVisibility(View.VISIBLE);
                if (textViewPlusSukses.getVisibility() == View.VISIBLE) {
                    textViewPlusSukses.setVisibility(View.GONE);
                }

                textViewPlusNamaProduk.setText(namaProduk);

                Glide.with(this).load(urlProduk).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(new DrawableImageViewTarget(imageViewProduk) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> animation) {
                        // here it's similar to RequestListener, but with less information (e.g. no model available)
                        super.onResourceReady(resource, animation);
                        avi.setVisibility(View.GONE);
                        // here you can be sure it's already set
                    }

                    // +++++ OR +++++
                    @Override
                    protected void setResource(Drawable resource) {
                        // this.getView().setImageDrawable(resource); is about to be called
                        super.setResource(resource);
                        avi.setVisibility(View.GONE);
                        // here you can be sure it's already set
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                        avi.setVisibility(View.GONE);
//
                        imageViewProduk.setImageDrawable(ContextCompat.getDrawable(GameActivity.this, R.drawable.ic_menu_game_online));
                    }
                });

            } else if (produkModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", produkModel.getResponse_desc());
            }
        }

        if (actionCode == ActionCode.PAY_PULSA) {
            closeProgressBarDialog();
            PayModel payModel = gson.fromJson(response.toString(), PayModel.class);
            if (payModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                logEventFireBase(ProdukGroup.GAMEONLINE, produkName, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                reqTag = 0;
                // try {
                PreferenceClass.putString("saldo", FormatString.CurencyIDR(payModel.getSaldo()));
                navigationBottomCustom(bottom_toolbar, FormatString.CurencyIDR(payModel.getSaldo()));

                onBackPressed();
                materialEditTextNoHandphone.setText("");
                textViewPlusSukses.setVisibility(View.VISIBLE);
                textViewPlusSukses.startAnimation(slideUp);

                textViewPlusSukses.setText("Pembelian voucher " + namaProdukPilihan + " Berhasil!");

                // MyDynamicToast.informationMessage(GameActivity.this, R.string.sukses_terbayar, "Pembelian voucher " + namaProdukPilihan + " Berhasil!");
                new_popup_alert(GameActivity.this, getString(R.string.sukses_terbayar), "Pembelian voucher " + namaProdukPilihan + " Berhasil!");
            } else if (payModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", payModel.getResponse_desc());
            } else if (payModel.getResponse_code().equals("06")) {
                new_popup_alert_session_deposit(this, "Informasi", payModel.getResponse_desc());

            } else {
                logEventFireBase(ProdukGroup.GAMEONLINE, produkName, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_NOT_SUCCESS, TAG);
                materialEditTextNoHandphone.setError(payModel.getResponse_desc());
//                        MyDynamicToast.errorMessage(GameActivity.this, payModel.getResponse_desc());
                new_popup_alert(GameActivity.this, "Info", payModel.getResponse_desc());
            }


        }
//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }
//       throw new RuntimeException("This is a crash");
        //Crashlytics.getInstance().crash();

    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        //    Log.d(TAG, "onFailure: " +responseCode+" "+ responseDescription);
//        if (actionCode == ActionCode.PAY_PULSA) {
//            MyDynamicToast.errorMessage(GameActivity.this, responseDescription);
//            closeProgressBarDialog();
//        }else {
        if (responseCode.equals(NETWORK_ERROR)) {
            if (actionCode == ActionCode.PAY_PULSA || actionCode == ActionCode.LIST_NOMINAL_PRODUK) {
                closeProgressBarDialog();
                new_popup_alert_failure(GameActivity.this, responseDescription);
                //   MyDynamicToast.errorMessage(GameActivity.this, "Cek Koneksi Internet Anda Atau Coba Kembali");
            } else if (actionCode == ActionCode.LIST_PRODUK_GAME) {
//                if (PreferenceClass.getJSONObject(product).length() > 0) {
//                    linMain.setVisibility(View.VISIBLE);
//                    layout_no_connection.setVisibility(View.GONE);
//                } else {
                shimmer_view_container.setVisibility(View.GONE);
                shimmer_view_container.stopShimmerAnimation();
                linList.setVisibility(View.GONE);
                //  layout_data_empty.setVisibility(View.GONE);
                layout_no_connection.setVisibility(View.VISIBLE);
//                }
            }
//                if (PreferenceClass.getJSONObject(product).length() == 0) {
//                    linMain.setVisibility(View.GONE);
//                    layout_no_connection.setVisibility(View.VISIBLE);
//                }
//            }
//            if (actionCode == ActionCode.LIST_NOMINAL_PRODUK) {
//                new_popup_alert_failure(GameActivity.this, responseDescription);
//            }

        } else {
            if (actionCode == ActionCode.PAY_PULSA || actionCode == ActionCode.LIST_NOMINAL_PRODUK) {
                closeProgressBarDialog();
            }
            //MyDynamicToast.errorMessage(GameActivity.this, responseDescription);
            new_popup_alert_failure(GameActivity.this, responseDescription);
        }


    }

    @Override
    public void onClickProduk(@NonNull GameModel.Response_value produk) {
        kodeProduk = produk.getGame_code();
        namaProduk = produk.getGame_name();
        urlProduk = produk.getGame_url();
        is_flag = produk.getIs_flag();
        materialEditTextNoHandphone.setText("");
        if (is_flag.equals("ID")) {

            materialEditTextNoHandphone.setHint("Id Mobile Legend");
            materialEditTextNoHandphone.setFloatingLabelText("Id Mobile Legend");
            imageViewAddressBook.setVisibility(View.GONE);
            showCaseID();
        } else {
            materialEditTextNoHandphone.setHint("No Handphone Pembeli");
            materialEditTextNoHandphone.setFloatingLabelText("No Handphone Pembeli");
            imageViewAddressBook.setVisibility(View.VISIBLE);
            showCaseHP();
        }
        logEventFireBase(ProdukGroup.GAMEONLINE, namaProduk, EventParam.EVENT_ACTION_CHOICE, EventParam.EVENT_SUCCESS, TAG);
        requestNominalProduk();

    }

    @Override
    public void onClickProduk(@NonNull ProdukPulsaModel.Response_value produk, int adapterPos) {
        Log.d(TAG, "onClickProduk: " + produk.getProduct_code() + " " + produk.getProduct_name() + " " + produk.getProduct_nominal() + " " + produk.getProduct_nominal().replace("Rp", "").replace(".", ""));


        produkName = produk.getProduct_name();
        adapterProduk.clearSelections();
        adapterProduk.toggleSelection(adapterPos);
        kodeProdukPilihan = produk.getProduct_code();
        namaProdukPilihan = produk.getProduct_name();
        String nominal = produk.getProduct_nominal().replace("Rp ", "").replace(".", "");
        int ownSaldo = Integer.parseInt(PreferenceClass.getString("saldo", "0").replace(".", ""));
        int nominalFrom = Integer.parseInt(nominal.trim());
        // int nominal_adminFrom=Integer.parseInt(nominal_admin.replace(".",""));
        int nominalAll = nominalFrom;

//        if(nominalAll>ownSaldo){
////            startBlinkingAnimation(true);
////           // return;
//            new_popup_alert_session_deposit(this, "Informasi", "Saldo Anda Tidak Mencukupi.");
//          //  return;
//        }else{
//
//                stopBlinkingAnimation();
//
            appCompatButtonBayar.performClick();
//        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
//        try {
//            switch (reqCode) {
//                case (PICK_CONTACT):
//                    if (resultCode == Activity.RESULT_OK) {
//                        Uri contactData = data.getData();
//                        Cursor cur = managedQuery(contactData, null, null, null, null);
//                        ContentResolver contect_resolver = getContentResolver();
//
//                        if (cur.moveToFirst()) {
//                            String id = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
//                            String airLineName = "";
//                            String no = "";
//
//                            Cursor phoneCur = contect_resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
//
//                            if (phoneCur.moveToFirst()) {
//                                airLineName = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                                no = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            }
//
//                            Log.d(TAG, airLineName + " : " + no);
//                            materialEditTextNoHandphone.setText("");
//                            materialEditTextNoHandphone.setCharacterDelay(50);
//                            materialEditTextNoHandphone.animateText(no.replaceAll("[^0-9]", "").replaceFirst("62", "0"));
//
//                            // txt.append(airLineName + " : " + no + "\n");
//
//                            id = null;
//                            airLineName = null;
//                            no = null;
//                            phoneCur = null;
//                        }
//                        contect_resolver = null;
//                        cur = null;
//                        //                      populateContacts();
//                    }
//            }
//        } catch (IllegalArgumentException e) {
//            // e.printStackTrace();
//            Log.d(TAG, e.toString());
//        } catch (Exception e) {
//            Log.d(TAG, e.toString());
//            //  e.printStackTrace();
//            //   Log.e("Error :: ", e.toString());
//        }

        if (resultCode == RESULT_OK) {
            if (requestCode == ActionCode.PICK_CONTACT) {// final EditText phoneInput = (EditText) findViewById(R.id.phoneNumberInput);
                Cursor cursor = null;
                String phoneNumber = "";
                final List<String> allNumbers = new ArrayList<>();
                int phoneIdx = 0;
                try {
                    Uri result = dataIntent.getData();
                    String id = result.getLastPathSegment();
                    cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
                    phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    if (cursor.moveToFirst()) {
                        while (cursor.isAfterLast() == false) {
                            phoneNumber = cursor.getString(phoneIdx);
                            allNumbers.add(phoneNumber);
                            cursor.moveToNext();
                        }
                    } else {
                        //no results actions
                    }
                } catch (Exception e) {
                    //error actions
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                    HashSet hs = new HashSet();
                    hs.addAll(allNumbers);
                    allNumbers.clear();
                    allNumbers.addAll(hs);
//                        final CharSequence[] items = allNumbers.toArray(new String[allNumbers.size()]);
                    final String[] items = allNumbers.toArray(new String[allNumbers.size()]);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

//                        LayoutInflater inflater = this.getLayoutInflater();
                    ViewGroup parent = (ViewGroup) findViewById(R.id.contentHost);
                    View dialogView = View.inflate(this, R.layout.dialog_custom_contact, parent);

                    builder.setView(dialogView);
                    TextView textViewTitleDialog = dialogView.findViewById(R.id.textViewTitleDialog);
                    textViewTitleDialog.setText("Pilih Nomor Handphone Tujuan");
                    //  TextView textViewItemDialog=dialogView.findViewById(R.id.textViewItemDialog);

                    //Prepare ListView in dialog
                    ListView listViewPhoneNumber = (ListView) dialogView.findViewById(R.id.listViewPhoneNumber);
                    final AlertDialog alert = builder.create();

                    ArrayAdapter<String> adapter
                            = new ArrayAdapter<String>(this,
                            R.layout.layout_item_phonenumber, R.id.textViewItemPhone, items);
                    listViewPhoneNumber.setAdapter(adapter);
                    listViewPhoneNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            // TODO Auto-generated method stub
//                                Toast.makeText(AndroidCustomDialogActivity.this,
//                                        parent.getItemAtPosition(position).toString() + " clicked",
//                                        Toast.LENGTH_LONG).show();
                            //   dismissDialog(CUSTOM_DIALOG_ID);
                            String selectedNumber = items[position].toString();
                            selectedNumber = selectedNumber.replace("-", "");
                            //  phoneInput.setText(selectedNumber);
                            selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                            if (selectedNumber.startsWith("62")) {
                                if (!selectedNumber.startsWith("9")) {
                                    selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                                }
                            }
                            materialEditTextNoHandphone.setText("");
//                                materialEditTextNoHandphone.setCharacterDelay(50);
//                                materialEditTextNoHandphone.animateText(selectedNumber);
                            materialEditTextNoHandphone.setText(selectedNumber);
                            alert.dismiss();

                        }
                    });

                    if (allNumbers.size() > 1) {
                        alert.show();
//                            alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        alert.getWindow().setBackgroundDrawableResource(R.drawable.alert_dialog_edge);
                    } else {
                        String selectedNumber = phoneNumber.toString();
                        selectedNumber = selectedNumber.replace("-", "");
//                            phoneInput.setText(selectedNumber);
                        selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                        if (selectedNumber.startsWith("62")) {
                            if (!selectedNumber.startsWith("9")) {
                                selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                            }
                        }
                        materialEditTextNoHandphone.setText("");
//                            materialEditTextNoHandphone.setCharacterDelay(50);
//                            materialEditTextNoHandphone.animateText(selectedNumber);
                        materialEditTextNoHandphone.setText(selectedNumber);
                    }

                    if (phoneNumber.length() == 0) {
                        //no numbers found actions
                    }
                }
            }else if (requestCode == RequestCode.ActionCode_VOICE_RECOGNITION && resultCode == RESULT_OK) {
                // onClose();
                ArrayList matches = dataIntent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                ArrayList<ProdukModel.Response_value> filteredList = new ArrayList<>();
                Log.d(TAG, "onActivityResult: " + Arrays.asList(matches));
                for (int i = 0; i < matches.size(); i++) {
                    String voiceNumber = matches.get(i).toString().trim();


                    for (GameModel.Response_value produk : data) {

                        if (produk.getGame_code().toLowerCase().contains(voiceNumber.toLowerCase()) || produk.getGame_name().toLowerCase().contains(voiceNumber.toLowerCase()) ) {
                            Log.d(TAG, "onActivityResult: "+voiceNumber.toLowerCase());
                            searchView.setQuery(voiceNumber.toLowerCase(), true);
                        }
                    }
                    // searchView..setText("");
//                if (!matches.contains(adapter.getOriginalData().get(i).getProduct_name())) {
//                    searchView.setQuery(voiceNumber.toLowerCase(), true);
//                }

//                if (adapter.getFilter().filter(voiceNumber)) {


//                }

                }






                //  onQueryTextChange(voiceNumber.toLowerCase());

            }
        } else {
            //activity result error actions
        }

    }

    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        // do things when keyboard is shown
        Log.d(TAG, "onShowKeyboard: " + keyboardHeight);
//        if(bottom_toolbar.getVisibility()==View.VISIBLE) {
        bottom_toolbar.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void onHideKeyboard() {
        Log.d(TAG, "onHideKeyboard: ");
        // do things when keyboard is hidden
        bottom_toolbar.setVisibility(View.VISIBLE);
    }

    private void startBlinkingAnimation(boolean isShowMessage) {
        //  LinearLayout linearLayout = findViewById(R.id.bottom_toolbar);
        if (isShowMessage) {
//            textViewPlusSukses.setVisibility(View.VISIBLE);
//            textViewPlusSukses.startAnimation(slideUp);
//
//            textViewPlusSukses.setText("Maaf, Saldo Anda Tidak Cukup");
            MyDynamicToast.informationMessage(this, -1, "Maaf, Saldo Anda Tidak Cukup");
        }
        TextViewPlus textViewPlusSaldoBottom = bottom_toolbar.findViewById(R.id.TextViewPlusSaldoBottom);
        textViewPlusSaldoBottom.setTextColor(ContextCompat.getColor(this, R.color.md_red_500));
        textViewPlusSaldoBottom.setTypeface(textViewPlusSaldoBottom.getTypeface(), Typeface.BOLD);
        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation);
        textViewPlusSaldoBottom.startAnimation(startAnimation);


    }

    private void stopBlinkingAnimation() {
        //  LinearLayout linearLayout = findViewById(R.id.bottom_toolbar);

        TextViewPlus textViewPlusSaldoBottom = bottom_toolbar.findViewById(R.id.TextViewPlusSaldoBottom);
        textViewPlusSaldoBottom.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
        textViewPlusSaldoBottom.setTypeface(tfLight);
        //  Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation);
        textViewPlusSaldoBottom.clearAnimation();

    }

    @Override
    protected void onDestroy() {
//        recyclerViewProduk.removeAllViews();
        Glide.get(this).clearMemory();
        super.onDestroy();
    }


    private void showCaseHP() {
        if (PreferenceClass.getInt(TAGHP, 0) != 1) {

            showCaseFirst(this, "", "Klik Icon untuk memilih Kontak yang ada di handphone", imageViewAddressBook);
            // client = new GuideView.Builder(PdamActivity.this);
            mGbuilder.setGuideListener(new GuideView.GuideListener() {
                @Override
                public void onDismiss(View view) {

                    PreferenceClass.putInt(TAGHP, 1);


                }
            });
            mGuideView = mGbuilder.build();
            mGuideView.show();
        }
    }

    private void showCaseID() {
        if (PreferenceClass.getInt(TAGID, 0) != 1) {

            showCaseFirst(this, "", "Masukkan ID Game Online pelanggan", materialEditTextNoHandphone);
            // client = new GuideView.Builder(PdamActivity.this);
            mGbuilder.setGuideListener(new GuideView.GuideListener() {
                @Override
                public void onDismiss(View view) {

                    PreferenceClass.putInt(TAGID, 1);


                }
            });
            mGuideView = mGbuilder.build();
            mGuideView.show();
        }
    }


    public void showContacts() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "showContacts: ");
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, RequestCode.ActionCode_READ_CONTACTS);
        } else {

            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));

            startActivityForResult(intent, ActionCode.PICK_CONTACT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestCode.ActionCode_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                new_popup_alert_two_buttonPermision(GameActivity.this, "Peringatan", R.string.content_alert_kontak);
            }
        }
    }



}
