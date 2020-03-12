package com.bm.main.fpl.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
//import androidx.appcompat.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.main.pos.R;
import com.bm.main.fpl.adapters.ListProdukAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.ProdukModel;
import com.bm.main.fpl.templates.DividerItemDecoration;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;

public class ListProductActivity extends BaseActivity implements ProgressResponseCallback, View.OnClickListener, ListProdukAdapter.OnClickProduk, SearchView.OnQueryTextListener, SearchView.OnCloseListener ,TextToSpeech.OnInitListener{
    private static final String TAG = ListProductActivity.class.getSimpleName();
    RecyclerView recyclerViewProduk;

    @NonNull
    ArrayList<ProdukModel.Response_value> data = new ArrayList<>();
    ProdukModel produkModel;
    String product;
    SearchView searchView;
    SearchManager searchManager;
    ListProdukAdapter adapter;
    String hint;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayout layout_no_connection, layout_data_empty;
    private AppCompatButton button_coba_lagi;


    LinearLayout linMain;
    //    private ProgressBar progressBar;
    TextView txtHeader, txtSub;
    Intent intent;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_ppob);
        //  overridePendingTransition(0,0);
        textToSpeech =new TextToSpeech(this, this);
        intent = getIntent();
        product = intent.getStringExtra("product");
        hint = intent.getStringExtra("hint");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("title"));
        init(1);
        //progressBar=findViewById(R.id.progressBar);
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
                if (recyclerViewProduk.getVisibility() == View.VISIBLE) {
                    recyclerViewProduk.setVisibility(View.GONE);
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
                requestDaftarProduk();
            }
        });

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView);

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setFocusable(false);
        searchView.setQueryHint(hint);
        recyclerViewProduk = findViewById(R.id.recyclerViewProduk);
//        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        searchAutoComplete.setTypeface(tfLight);
//        searchAutoComplete.setTextSize(14);
        //  AutoCompleteTextView searchText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        // searchText.setTextColor(ContextCompat.getColor(this, R.color.global_text_primary_inverse));
        // searchText.setHintTextColor(ContextCompat.getColor(this, R.color.global_text_secondary_inverse));
//searchText.fo
        // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextSize(14);

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        textView = searchView.findViewById(id);
        textView.setTextColor(Color.BLACK);
//        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
//        View searchPlate = searchView.findViewById(searchPlateId);
//        if (searchPlate!=null) {
//            searchPlate.setBackgroundColor(Color.DKGRAY);
//            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
//            if (searchText!=null) {
//                searchText.setTextColor(Color.WHITE);
//                searchText.setHintTextColor(Color.WHITE);
//            }
//        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewProduk.setHasFixedSize(false);
        recyclerViewProduk.setLayoutManager(linearLayoutManager);
        recyclerViewProduk.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new ListProdukAdapter(data, this, this);
        recyclerViewProduk.setAdapter(adapter);
        if (PreferenceClass.getJSONObject(product).length() > 0) {
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
            produkModel = gson.fromJson(PreferenceClass.getJSONObject(product).toString(), ProdukModel.class);

            data.clear();
            data.addAll(produkModel.getResponse_value());
            //  setData(gameModel.getData());
            adapter.notifyDataSetChanged();
            recyclerViewProduk.setVisibility(View.VISIBLE);
        }

        requestDaftarProduk();
    }

    private void requestDaftarProduk() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestListProduct(product));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_PRODUK, this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_voice, menu);
//        return true;
//    }

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
        closeKeyboard(this);
        // intent = new Intent();

//        setResult(RESULT_CANCELED, intent);
        setResult(RESULT_CANCELED);

        finish();
        //   overridePendingTransition(0,0);


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
//        mShimmerViewContainer.stopShimmerAnimation();
//        mShimmerViewContainer.setVisibility(View.GONE);

        if (layout_no_connection.getVisibility() == View.VISIBLE) {
            layout_no_connection.setVisibility(View.GONE);
        }
        if (linMain.getVisibility() == View.GONE) {
            linMain.setVisibility(View.VISIBLE);
        }
        if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
            mShimmerViewContainer.setVisibility(View.GONE);
            mShimmerViewContainer.stopShimmerAnimation();
        }

//        try {
//            if (response.getString("response_code").equals("03")) {
//                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//            } else {
        if (actionCode == ActionCode.LIST_PRODUK) {

            produkModel = gson.fromJson(response.toString(), ProdukModel.class);
            // data.clear();
            if (produkModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
//                data.addAll(produkModel.getData());
//                //  setData(gameModel.getData());
//                adapter.notifyDataSetChanged();
                // stop animating Shimmer and hide the layout
                JSONObject obj = PreferenceClass.getJSONObject(product);
                JSONArray array = new JSONArray();
                try {
                    array = obj.getJSONArray("response_value");
                } catch (JSONException e) {
                    //  e.printStackTrace();
                }

                if (array.length() != produkModel.getResponse_value().size()) {
                    PreferenceClass.putJSONObject(product, response);
                    data.clear();
                    data.addAll(produkModel.getResponse_value());
                    adapter.notifyDataSetChanged();
                }
                recyclerViewProduk.setVisibility(View.VISIBLE);
            } else if (produkModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", produkModel.getResponse_desc());
            } else {
                layout_data_empty.setVisibility(View.VISIBLE);
                txtHeader.setText("Data Produk");
                txtSub.setText("Tidak Ditemukan");
            }
        }

//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }

    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        //  Log.d(TAG, "onFailure: "+responseCode+" "+responseDescription+" "+throwable.toString());
        if (responseCode.equals(NETWORK_ERROR)) {
            if (actionCode == ActionCode.LIST_PRODUK) {
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
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {
//        System.out.println(bytesRead);
//                System.out.println(totalSize);
//                System.out.println(done);
//                System.out.format("%d%% done\n", (100 * bytesRead) / totalSize);
        int progress = (int) ((100 * bytesRead) / totalSize);

        // Enable if you want to see the progress with logcat
        Log.d(TAG, "Progress: " + progress + "%");
        // progressBar.setProgress(progress);
        if (done) {
            Log.d(TAG, "Done loading");
        }
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

//    public void setData(ArrayList<ProdukModel.Response_value> data) {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerViewPropinsi.setHasFixedSize(false);
//        recyclerViewPropinsi.setLayoutManager(linearLayoutManager);
//        recyclerViewPropinsi.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        recyclerViewPropinsi.setAdapter(new ListProdukAdapter(data,this,this));
//
//    }


    @Override
    protected void onDestroy() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.removeAllViews();
//        recyclerViewProduk.removeAllViews();
        Glide.get(this).clearMemory();
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onClickProduk(@NonNull ProdukModel.Response_value produk) {
        closeKeyboard(this);
        Intent intent = new Intent();
        intent.putExtra("kodeProduk", produk.getProduct_code());
        intent.putExtra("namaProduk", produk.getProduct_name());
        setResult(RESULT_OK, intent);
        finish();
        //   overridePendingTransition(0,0);
        //overridePendingTransition(R.anim.popup_show,R.anim.popup_hide);

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
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        if (requestCode == RequestCode.ActionCode_VOICE_RECOGNITION && resultCode == RESULT_OK) {
            // onClose();
            ArrayList matches = dataIntent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            ArrayList<ProdukModel.Response_value> filteredList = new ArrayList<>();
//            Log.d(TAG, "onActivityResult: " + Arrays.asList(matches));
            //for (int i = 0; i < matches.size(); i++) {
                String voiceNumber = matches.get(0).toString().trim();


//            for (ProdukModel.Response_value produk : adapter.data) {
//
            for(int i=0;i<adapter.getItemCount();i++){
                if (adapter.data.get(i).getProduct_name().toLowerCase().contains(voiceNumber.toLowerCase()) || adapter.data.get(i).getProduct_name().toLowerCase().contains(voiceNumber.toLowerCase()) ) {
                    Log.d(TAG, "onActivityResult: "+voiceNumber.toLowerCase());
                   // searchView.setQuery(voiceNumber.toLowerCase(), true);
                    adapter.getFilter().filter(voiceNumber);
                    textToSpeech.speak(adapter.data.get(i).getProduct_name()+" di temukan", TextToSpeech.QUEUE_FLUSH, null);
                    if(!textToSpeech.isSpeaking()) {
                        textToSpeech = new TextToSpeech(ListProductActivity.this, ListProductActivity.this);
                        System.out.println("tts restarted");
                        textToSpeech.speak(adapter.data.get(i).getProduct_name()+" di temukan", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
//                else if(voiceNumber.toLowerCase().equals("jancok")){
//                    textToSpeech.speak(voiceNumber.toLowerCase()+"raii mu dewe", TextToSpeech.QUEUE_FLUSH, null);
//                    if(!textToSpeech.isSpeaking()) {
//                        textToSpeech = new TextToSpeech(ListProductActivity.this, ListProductActivity.this);
//                        System.out.println("tts restarted");
//                        textToSpeech.speak(voiceNumber.toLowerCase()+"raii mu dewe", TextToSpeech.QUEUE_FLUSH, null);
//                    }
//                }
            }
                // searchView..setText("");
//                if (!matches.contains(adapter.getOriginalData().get(i).getProduct_name())) {
//                    searchView.setQuery(voiceNumber.toLowerCase(), true);
//                }

//                if (adapter.getFilter().filter(voiceNumber)) {


//                }

           }






            //  onQueryTextChange(voiceNumber.toLowerCase());

//        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
//            int ttsLang = textToSpeech.setLanguage(Locale.getDefault());

            int ttsLang = textToSpeech.setLanguage(new Locale("id","ID"));
            Log.d(TAG, "onInit: "+ttsLang);
            if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                    || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.d(TAG, "The Language is not supported!"+TextToSpeech.LANG_MISSING_DATA+" "+TextToSpeech.LANG_NOT_SUPPORTED);
                textToSpeech.setLanguage(Locale.US);
            } else {
                Log.d(TAG, "Language Supported.");
                textToSpeech.setLanguage(new Locale("id","ID"));
            }
            Log.d(TAG, "Initialization success.");


        } else {
            Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
        }
    }

}
