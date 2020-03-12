package com.bm.main.single.ftl.activities;

import android.Manifest;
import android.app.SearchManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.activities.KeyboardListenerActivity;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.utils.FileUtils;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.adapters.EtiketFlightAdapter;
import com.bm.main.single.ftl.adapters.EtiketShipAdapter;
import com.bm.main.single.ftl.adapters.EtiketTrainAdapter;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.constants.TravelPath;
import com.bm.main.single.ftl.models.EtiketFlightModel;
import com.bm.main.single.ftl.models.EtiketShipModel;
import com.bm.main.single.ftl.models.EtiketTrainModel;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;
//import com.google.common.cache.LocalCache;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.zxing.client.android.Intents;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class TravelDaftarPesananActivity extends BaseActivity implements  ProgressResponseCallback, SearchView.OnQueryTextListener, SearchView.OnCloseListener , EtiketFlightAdapter.OnClickProdukFlight, EtiketTrainAdapter.OnClickProdukTrain, EtiketShipAdapter.OnClickProdukShip {
//public class TravelDaftarPesananActivity extends BaseActivity implements JsonObjectResponseCallback, SearchView.OnQueryTextListener, SearchView.OnCloseListener , EtiketFlightAdapter.OnClickProdukFlight, EtiketTrainAdapter.OnClickProdukTrain, EtiketShipAdapter.OnClickProdukShip {
    private static final String TAG = TravelDaftarPesananActivity.class.getSimpleName();
    SwipeRefreshLayout mSwipeRefreshLayout;
//    BookAdapter bookAdapter;
//    String selectedProduct = "PESAWAT";
    String selectedProduct = "";
    LinearLayout linTanggalAwal, linTanggalAkhir;
    RelativeLayout relTampilkan;
    String startDate, endDate;
    TextView textViewTanggalAwal;
    TextView textViewTanggalAkhir;
    private int requestCode;
    RelativeLayout bottom_toolbar_calendar;
    private String finalUrl_struk;
    private String finalUrl_pdf;
    private String finalTransaction_id;
//    private JSONObject finalJsonObject;
    private String finalJsonObject;
    private View subMenuView;
    private int isSendETiket = 0;
    private String finalproduk;
    private String finalnamaProduk;
    private String finalnama;
    private String finalkode;

    SearchView searchView;
    SearchManager searchManager;

    private TextView textView;
    private SearchView.SearchAutoComplete searchAutoComplete;
    ArrayList<EtiketFlightModel.Data> dataFlightMaster= new ArrayList<>();
    ArrayList<EtiketTrainModel.Data> dataTrainMaster= new ArrayList<>();
    ArrayList<EtiketShipModel.Data> dataShipMaster= new ArrayList<>();
//    private EtiketFlightModel.Data finalPesananPesawatModel;
    private EtiketFlightAdapter etiketFlightAdapter;
    private EtiketTrainAdapter etiketTrainAdapter;
    private EtiketShipAdapter etiketShipAdapter;


    RelativeLayout rel_EmptyViewItem1;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_daftar_pesanan_activity);
        Intent intent=this.getIntent();




        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("E-tiket");
        toolbar.setSubtitle(selectedProduct);
        init(0);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver,
                new IntentFilter("BROADCAST_PRINTING")
        );
        rel_EmptyViewItem1 = findViewById(R.id.rel_EmptyViewItem1);
        textViewTanggalAwal = findViewById(R.id.textViewTanggalAwal);

        textViewTanggalAkhir = findViewById(R.id.textViewTanggalAkhir);


        Bundle b = new Bundle();
        b = getIntent().getBundleExtra("bundle1");
        String namaProduk="";
        if(b!=null) {
           // Log.d(TAG, "onCreate: "+b.getString("valueDateAwal"));
//            if (b.getString("valueDateAwal").equals("") || b.getString("valueDateAkhir").equals("")) {
                selectedProduct = b.getString("product");
                startDate = b.getString("valueDateAwal");


                endDate = b.getString("valueDateAkhir");
                textViewTanggalAwal.setText(b.getString("valueDataShowAwal"));
                textViewTanggalAkhir.setText(b.getString("valueDataShowAkhir"));
//            }
        }else{
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", SBFApplication.config.locale);
            DateTimeFormatter  formatterShow = DateTimeFormatter.ofPattern("dd MMM yyyy",SBFApplication.config.locale);
//        SimpleDateFormat sdf=new SimpleDateFormat("EEEEEE,dd MMMM yyyy",config.locale);
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", BaseActivity.config.locale);
//                SimpleDateFormat formatterShow = new SimpleDateFormat("dd MMM yyyy", BaseActivity.config.locale);
            String showDateNow = formatterShow.format(currentDate);
            String dateNow = formatter.format(currentDate);
            startDate = dateNow;
            endDate = dateNow;
            textViewTanggalAwal.setText(showDateNow);
            textViewTanggalAkhir.setText(showDateNow);
            selectedProduct=intent.getStringExtra("product");
        }


        bottom_toolbar_calendar = findViewById(R.id.bottom_toolbar_calendar);
        linTanggalAwal = findViewById(R.id.linTanggalAwal);
        linTanggalAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCode = 1;
                Intent intent = new Intent(TravelDaftarPesananActivity.this, TravelTanggalPesananActivity.class);
                intent.putExtra("initTanggal", "awal");
                intent.putExtra("initValue", startDate);
                startActivityForResult(intent, requestCode);
                //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
            }
        });
        linTanggalAkhir = findViewById(R.id.linTanggalAkhir);
        linTanggalAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCode = 2;
                Intent intent = new Intent(TravelDaftarPesananActivity.this, TravelTanggalPesananActivity.class);
                intent.putExtra("initTanggal", "akhir");
                intent.putExtra("initValue", startDate);
                startActivityForResult(intent, requestCode);
                //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
            }
        });

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView);
        searchView.setFocusable(false);
        searchAutoComplete = searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setTypeface(tfLight);
        searchAutoComplete.setTextSize(14);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Cari Kode Booking");
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

//        searchView.setIconifiedByDefault(false);
//        searchView.setOnQueryTextListener(this);
//        searchView.setOnCloseListener(this);
//        searchView.setFocusable(false);
//        searchView.setQueryHint("Cari Kode Booking");
//
//        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
//        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
//        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
//        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
//        autoComplete.setTextSize(14);
//
//        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//        textView = searchView.findViewById(id);
//        textView.setTextColor(Color.BLACK);
//

        relTampilkan = findViewById(R.id.relTampilkan);
        relTampilkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshItems();
            }
        });

        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
         recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
//        bookAdapter = new BookAdapter(this);

//        recyclerView.setAdapter(bookAdapter);

        switch (selectedProduct) {
            case "PESAWAT":
                etiketFlightAdapter = new EtiketFlightAdapter(this, dataFlightMaster, this);
                recyclerView.setAdapter(etiketFlightAdapter);
                break;
            case "KERETA":
                etiketTrainAdapter = new EtiketTrainAdapter(this, dataTrainMaster, this);
                recyclerView.setAdapter(etiketTrainAdapter);
                break;
            case "KAPAL":
                etiketShipAdapter = new EtiketShipAdapter(this, dataShipMaster, this);
                recyclerView.setAdapter(etiketShipAdapter);
                break;
        }

        refreshItems();

    }

    void refreshItems() {
//        Date now=new Date();
//        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(now);
//        cal.add(Calendar.DATE, -30);
//        String dateBefore30Days = dateFormat.format(cal.getTime());
//        String date = dateFormat.format(now);

//
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product", selectedProduct);
            jsonObject.put("startDate", startDate);
            jsonObject.put("endDate", endDate);
            jsonObject.put("token", PreferenceClass.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "showList: " + jsonObject.toString());
        mSwipeRefreshLayout.setRefreshing(true);
//        bookAdapter.type = selectedProduct;


//        bookAdapter.dataFlight.clear();
//        bookAdapter.filterDataFlight.clear();

//        bookAdapter.notifyDataSetChanged();
        RequestUtilsTravel.transportWithProgressResponse(TravelDaftarPesananActivity.this, TravelPath.TRANSACTIONLIST, jsonObject, TravelActionCode.TRANSACTIONLIST, TravelDaftarPesananActivity.this);
       // RequestUtilsTravel.transportWithJSONObjectResponse(TravelDaftarPesananActivity.this, TravelPath.TRANSACTIONLIST, jsonObject, TravelActionCode.TRANSACTIONLIST, TravelDaftarPesananActivity.this);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {

        //System.out.println( "onSuccess: " + response.toString());
//        Log.d(TAG, "onSuccess: " + response.toString());
      //  Log.i(TAG, "onSuccess: " + response.toString());
      //  BaseObject baseObject = gson.fromJson(response.toString(), BaseObject.class);
//        try {
        if (actionCode == TravelActionCode.TRANSACTIONLIST ) {


//            try {
//                JSONArray data = response.getJSONArray("data");
                if ("PESAWAT".equals(selectedProduct)) {
                    EtiketFlightModel    etiketFlightModel = gson.fromJson(response.toString(), EtiketFlightModel.class);
                    if (etiketFlightModel.getRc().equals(ResponseCode.SUCCESS)) {
//                        JSONObject obj = PreferenceClass.getJSONObject(selectedProduct);
//                        JSONArray array = new JSONArray();
//                        try {
//                            array = obj.getJSONArray("data");
//                        } catch (JSONException e) {
//                            //  e.printStackTrace();
//                        }

//                        if (array.length() != etiketFlightModel.getData().size()) {
                        if (etiketFlightModel.getData().size()>0) {
                            PreferenceClass.putJSONObject(selectedProduct, response);
                            dataFlightMaster.clear();
                            dataFlightMaster.addAll(etiketFlightModel.getData());
                            etiketFlightAdapter.notifyDataSetChanged();
                            rel_EmptyViewItem1.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            searchView.setVisibility(View.VISIBLE);
                        }else{
                            rel_EmptyViewItem1.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            searchView.setVisibility(View.GONE);
                        }


//                    bookAdapter.dataFlight.clear();
//                    bookAdapter.filterDataFlight.clear();
//                    if (data.length() > 0) {
//
//
//                        for (int i = 0; i < data.length(); i++) {
//                            bookAdapter.dataFlight.addAll(data.getJSONObject(i)));
//                            bookAdapter.filterDataFlight.add(data.getJSONObject(i)));
//                        }
////                } else if (selectedProduct.equals("HOTEL")) {
////                    for (int i = 0; i < data.length(); i++) {
////                        bookAdapter.data.add(new PesananHotel(data.getJSONObject(i)));
////                    }
//                        searchView.setVisibility(View.VISIBLE);
//                    } else {
//                        searchView.setVisibility(View.GONE);
//                    }




                    }
                } else if ("KERETA".equals(selectedProduct)) {

                    EtiketTrainModel    etiketTrainModel = gson.fromJson(response.toString(), EtiketTrainModel.class);
                    if (etiketTrainModel.getRc().equals(ResponseCode.SUCCESS)) {
                        Log.d(TAG, "onSuccess: KERETA");
//                        ArrayList<EtiketTrainModel.Data> dataTrainMaster= new ArrayList<>();
//                      //  Toast.makeText(this, "onSuccess: " + response.length()+" "+selectedProduct+" "+response.toString(), Toast.LENGTH_LONG).show();
//                        etiketTrainAdapter = new EtiketTrainAdapter(this, dataTrainMaster, this);
//                        recyclerView.setAdapter(etiketTrainAdapter);
//                        JSONObject obj = PreferenceClass.getJSONObject(selectedProduct);
//                        JSONArray array = new JSONArray();
//                        try {
//                            array = obj.getJSONArray("data");
//                        } catch (JSONException e) {
//                            //  e.printStackTrace();
//                        }
//
//                        if (array.length() != etiketTrainModel.getData().size() ) {
                        if (etiketTrainModel.getData().size()>0 ) {
//                            PreferenceClass.putJSONObject(selectedProduct, response);
                            dataTrainMaster.clear();
                            dataTrainMaster.addAll(etiketTrainModel.getData());

                            etiketTrainAdapter.notifyDataSetChanged();
                            rel_EmptyViewItem1.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            searchView.setVisibility(View.VISIBLE);
                        }else {
                            rel_EmptyViewItem1.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            searchView.setVisibility(View.GONE);
                        }
                    }
//                    bookAdapter.dataTrain.clear();
//                    bookAdapter.filterDataTrain.clear();
//                    if (data.length() > 0) {
//                        for (int i = 0; i < data.length(); i++) {
//                            bookAdapter.dataTrain.add(new PesananKereta(data.getJSONObject(i)));
//                            bookAdapter.filterDataTrain.add(new PesananKereta(data.getJSONObject(i)));
//                        }
//                        searchView.setVisibility(View.VISIBLE);
//                    } else {
//                        searchView.setVisibility(View.GONE);
//                    }
                } else if ("KAPAL".equals(selectedProduct)) {
                    EtiketShipModel    etiketShipModel = gson.fromJson(response.toString(), EtiketShipModel.class);
                    if (etiketShipModel.getRc().equals(ResponseCode.SUCCESS)) {
                        Log.d(TAG, "onSuccess: "+selectedProduct);
//                        JSONObject obj = PreferenceClass.getJSONObject(selectedProduct);
//                        JSONArray array = new JSONArray();
//                        try {
//                            array = obj.getJSONArray("data");
//                        } catch (JSONException e) {
//                            //  e.printStackTrace();
//                        }

                       // if (array.length() != etiketShipModel.getData().size() ) {
                        if (etiketShipModel.getData().size()>0 ) {
//                            PreferenceClass.putJSONObject(selectedProduct, response);
                            dataShipMaster.clear();
                            dataShipMaster.addAll(etiketShipModel.getData());
                            etiketShipAdapter.notifyDataSetChanged();
                            rel_EmptyViewItem1.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            searchView.setVisibility(View.VISIBLE);
                        }else {
                            rel_EmptyViewItem1.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            searchView.setVisibility(View.GONE);
                        }
                    }
//                    bookAdapter.dataShip.clear();
//                    bookAdapter.filterDataShip.clear();
//                    if (data.length() > 0) {
//                        for (int i = 0; i < data.length(); i++) {
//                            bookAdapter.dataShip.add(new PesananKapal(data.getJSONObject(i)));
//                            bookAdapter.filterDataShip.add(new PesananKapal(data.getJSONObject(i)));
//                        }
//                        searchView.setVisibility(View.VISIBLE);
//                    } else {
//                        searchView.setVisibility(View.GONE);
//                    }
                    //                    case "WISATA":
//                        for (int i = 0; i < data.length(); i++) {
//                            bookAdapter.data.add(new PesananWisata(data.getJSONObject(i)));
//                            bookAdapter.filterData.add(new PesananWisata(data.getJSONObject(i)));
//                        }
//                        break;
                }
//            } catch (JSONException jsone) {
////
//            }
////               runOnUiThread(new Runnable() {
//                   @Override
//                   public void run() {
            mSwipeRefreshLayout.setRefreshing(false);
            //bookAdapter.notifyDataSetChanged();
//            if(!searchAutoComplete.getText().toString().isEmpty()){
//                searchAutoComplete.setText("");
//            }

            //  textView.setText("");
//                   }
//               });

        }

//        }catch (JSONException jsone){
//
//        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, final String responseDescription, Throwable throwable) {
        if (actionCode == TravelActionCode.TRANSACTIONLIST) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
            mSwipeRefreshLayout.setRefreshing(false);
            // bookAdapter.notifyDataSetChanged();
            //  showToastCustom(DaftarPesananActivity.this, 1, responseDescription);
            snackBarCustomAction(findViewById(R.id.rootLayout), 0, responseDescription, 1);
//                }
//            });
        }
    }

//    @Override
//    public void onUpdate(int actionCode, long bytesRead, long totalSize, boolean done) {
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_rumah, menu);
        getMenuInflater().inflate(R.menu.menu_scanner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right_drawer) {

            openTopDialog(false);
        } else if (id == R.id.action_right_scanner) {

            //openScanner(CicilanActivity.this);
            startScan(this,this::onResult);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onResult(Barcode barcode) {
//        materialEditTextIdPelanggan.setText(barcode.rawValue);
        try {
          //  String idPel = data.getStringExtra(Intents.Scan.RESULT);

            searchAutoComplete.setText(barcode.rawValue);
            //    bookAdapter.getFilter().filter(idPel);
        }catch (Exception e){

        }
    }
    @Override
    public void onBackPressed() {
        closeKeyboard(this);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onClose() {
       // bookAdapter.getFilter().filter("");
        switch (selectedProduct) {
            case "PESAWAT":
                etiketFlightAdapter.getFilter().filter("");
                break;
            case "KERETA":
                etiketTrainAdapter.getFilter().filter("");
                break;
            case "KAPAL":
                etiketShipAdapter.getFilter().filter("");
                break;
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
       // bookAdapter.getFilter().filter(query);
        switch (selectedProduct) {
            case "PESAWAT":
                etiketFlightAdapter.getFilter().filter(query);
                break;
            case "KERETA":
                etiketTrainAdapter.getFilter().filter(query);
                break;
            case "KAPAL":
                etiketShipAdapter.getFilter().filter(query);
                break;
        }
        closeKeyboard(this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
       // bookAdapter.getFilter().filter(newText);
        switch (selectedProduct) {
            case "PESAWAT":
                etiketFlightAdapter.getFilter().filter(newText);
                break;
            case "KERETA":
                etiketTrainAdapter.getFilter().filter(newText);
                break;
            case "KAPAL":
                etiketShipAdapter.getFilter().filter(newText);
                break;
        }
        return false;
    }


    protected void onShowKeyboard(int keyboardHeight) {
        // do things when keyboard is shown

        Log.d(TAG, "onShowKeyboard: " + keyboardHeight);
//        if(bottom_toolbar.getVisibility()==View.VISIBLE) {
        bottom_toolbar_calendar.setVisibility(View.GONE);
//        }
    }


    protected void onHideKeyboard() {
        Log.d(TAG, "onHideKeyboard: ");
        // do things when keyboard is hidden
        bottom_toolbar_calendar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickProdukDetail(EtiketFlightModel.Data produk) {
        Intent intent = new Intent(TravelDaftarPesananActivity.this, TravelTiketPesananActivity.class);
        intent.putExtra("url_struk", produk.getUrl_struk());
        intent.putExtra("url_pdf", produk.getUrl_etiket());
        intent.putExtra("id_transaksi", String.valueOf(produk.getId_transaksi()));
//                intent.putExtra("data", finalJsonObject);
        intent.putExtra("data", produk);
        intent.putExtra("product", selectedProduct);

        startActivity(intent);
    }

    @Override
    public void onClickProdukPopupMenu(final EtiketFlightModel.Data produk, EtiketFlightAdapter.ViewHolder viewHolder) {
finalTransaction_id=String.valueOf(produk.getId_transaksi());
        finalUrl_pdf=produk.getUrl_etiket();
       finalproduk="Pesawat";
       finalnamaProduk=produk.getNama_maskapai();
       finalkode=produk.getKode_booking();
//        for(int i = 0; i < produk.getPenumpang().length; i++) {
//                            nama = produk.getPenumpang()[0].getTitle() + " " + produk.getPenumpang()[0].getNama();
//                        }
////
                        finalnama = produk.getPenumpang()[0].getTitle() + " " + produk.getPenumpang()[0].getNama();

//        openSendVia(subMenuView, Uri.fromFile(file), finalproduk, finalnamaProduk, finalkode, finalnama, file);
        subMenuView=viewHolder.ivMenu;
        PopupMenu popup = new PopupMenu(this, viewHolder.ivMenu);
            popup.getMenuInflater().inflate(R.menu.core_popup_menu_list_pesanan, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.action_pop1) {
                        Intent intent = new Intent(TravelDaftarPesananActivity.this, TravelTiketPesananActivity.class);
                        intent.putExtra("url_struk", produk.getUrl_struk());
                        intent.putExtra("url_pdf", produk.getUrl_etiket());
                        intent.putExtra("id_transaksi",String.valueOf(produk.getId_transaksi()));
                        intent.putExtra("data", produk);
                        intent.putExtra("product", selectedProduct);
                        startActivity(intent);




                    } else if (id == R.id.action_pop2) {

                        isSendETiket = 1;
                        checkPermisionStorage();

                    } else if (id == R.id.action_pop3) {
                        kodeBooking = produk.getKode_booking();
                        showToast("Printing......");
                         getStruk(produk.getUrl_struk(), 1, TravelDaftarPesananActivity.this);
                    }
                    return true;
                }
            });
            popup.show();
    }

    @Override
    public void onClickProdukDetail(EtiketTrainModel.Data produk) {
        Intent intent = new Intent(TravelDaftarPesananActivity.this, TravelTiketPesananActivity.class);
        intent.putExtra("url_struk", produk.getUrl_struk());
        intent.putExtra("url_pdf", produk.getUrl_etiket());
        intent.putExtra("id_transaksi", String.valueOf(produk.getId_transaksi()));
//                intent.putExtra("data", finalJsonObject);
        intent.putExtra("data", produk);
        intent.putExtra("product", selectedProduct);

        startActivity(intent);

    }

    @Override
    public void onClickProdukPopupMenu(final EtiketTrainModel.Data produk, EtiketTrainAdapter.ViewHolder viewHolder) {
        subMenuView=viewHolder.ivMenu;
        finalTransaction_id=String.valueOf(produk.getId_transaksi());
        finalUrl_pdf=produk.getUrl_etiket();
        finalproduk="Kereta";
        finalnamaProduk=produk.getNama_kereta();
        finalkode=produk.getKode_booking();
//        for(int i = 0; i < produk.getPenumpang().length; i++) {
//                            nama = produk.getPenumpang()[0].getTitle() + " " + produk.getPenumpang()[0].getNama();
//                        }
////
        finalnama = produk.getPenumpang()[0].getNama();
        PopupMenu popup = new PopupMenu(this, viewHolder.ivMenu);
        popup.getMenuInflater().inflate(R.menu.core_popup_menu_list_pesanan, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_pop1) {
                    Intent intent = new Intent(TravelDaftarPesananActivity.this, TravelTiketPesananActivity.class);
                    intent.putExtra("url_struk", produk.getUrl_struk());
                    intent.putExtra("url_pdf", produk.getUrl_etiket());
                    intent.putExtra("id_transaksi", String.valueOf(produk.getId_transaksi()));
                    intent.putExtra("data", produk);
                    intent.putExtra("product", selectedProduct);
                    startActivity(intent);
                } else if (id == R.id.action_pop2) {

                    isSendETiket = 1;
                    checkPermisionStorage();

                } else if (id == R.id.action_pop3) {
                    kodeBooking = produk.getKode_booking();
                    showToast("Printing......");
                    getStruk(produk.getUrl_struk(), 1, TravelDaftarPesananActivity.this);
                }
                return true;
            }
        });
        popup.show();
    }


    @Override
    public void onClickProdukDetail(EtiketShipModel.Data produk) {
        Intent intent = new Intent(TravelDaftarPesananActivity.this, TravelTiketPesananActivity.class);
        intent.putExtra("url_struk", produk.getUrl_struk());
        intent.putExtra("url_pdf", produk.getUrl_etiket());
        intent.putExtra("id_transaksi", String.valueOf(produk.getId_transaksi()));
//                intent.putExtra("data", finalJsonObject);
        intent.putExtra("data", produk);
        intent.putExtra("product", selectedProduct);

        startActivity(intent);
    }

    @Override
    public void onClickProdukPopupMenu(final EtiketShipModel.Data produk, EtiketShipAdapter.ViewHolder viewHolder) {
        subMenuView=viewHolder.ivMenu;
        finalTransaction_id=String.valueOf(produk.getId_transaksi());
        finalUrl_pdf=produk.getUrl_etiket();
        finalproduk="Pelni";
        finalnamaProduk=produk.getNama_kapal();
        finalkode=produk.getKode_booking();
//        for(int i = 0; i < produk.getPenumpang().length; i++) {
//                            nama = produk.getPenumpang()[0].getTitle() + " " + produk.getPenumpang()[0].getNama();
//                        }
////
        finalnama = produk.getPenumpang()[0].getNama();
        PopupMenu popup = new PopupMenu(this, viewHolder.ivMenu);
        popup.getMenuInflater().inflate(R.menu.core_popup_menu_list_pesanan, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_pop1) {
                    Intent intent = new Intent(TravelDaftarPesananActivity.this, TravelTiketPesananActivity.class);
                    intent.putExtra("url_struk", produk.getUrl_struk());
                    intent.putExtra("url_pdf", produk.getUrl_etiket());
                    intent.putExtra("id_transaksi", String.valueOf(produk.getId_transaksi()));
                    intent.putExtra("data", produk);
                    intent.putExtra("product", selectedProduct);
                    startActivity(intent);
                } else if (id == R.id.action_pop2) {

                    isSendETiket = 1;
                    checkPermisionStorage();

                } else if (id == R.id.action_pop3) {
                    kodeBooking = produk.getKode_booking();
                    showToast("Printing......");
                    getStruk(produk.getUrl_struk(), 1, TravelDaftarPesananActivity.this);
                }
                return true;
            }
        });
        popup.show();
    }

//    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> implements Filterable {
//        Context context;
//        LayoutInflater inflater;
//        @NonNull
////        List<JSONModel> data = new ArrayList<>();
//        ArrayList<EtiketFlightModel.Data> dataFlight = dataFlightMaster;//new ArrayList<>();
//        ArrayList<EtiketFlightModel.Data> filterDataFlight = dataFlightMaster;
//        List<PesananKereta> dataTrain = new ArrayList<>();
//        List<PesananKereta> filterDataTrain = new ArrayList<>();
//        List<PesananKapal> dataShip = new ArrayList<>();
//        List<PesananKapal> filterDataShip = new ArrayList<>();
//        public String type = "PESAWAT";
//
//
//        public BookAdapter(Context context) {
//            this.context = context;
//            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
//        @NonNull
//        @Override
//        public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View itemView = inflater.inflate(R.layout.list_item_daftar_pesanan, parent, false);
//            return new BookAdapter.ViewHolder(itemView);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull final BookAdapter.ViewHolder holder, final int position) {
//            String transaction_id = "";
//            String url_struk = "";
//            String url_pdf = "";
//            String produk = "";
//            String namaProduk = "";
//
//            String nama = "";
//            String kode = "";
//            String jsonObject = null;
//           // EtiketFlightModel.Data pesananPesawatModel;
//
//            switch (type) {
//                case "PESAWAT": {
//
//                    Log.d(TAG, "onBindViewHolder: "+filterDataFlight.size());
//
//                    if (!mSwipeRefreshLayout.isRefreshing() && filterDataFlight.size() == 0 && position == 0) {
//                        holder.dataView.setVisibility(View.GONE);
//                        holder.emptyView.setVisibility(View.VISIBLE);
//                    } else if (filterDataFlight.size() > 0 && position > 0) {
//                        //PesananPesawat pesananPesawat =  filterDataFlight.get(position - 1);
//                        EtiketFlightModel.Data pesananPesawat=filterDataFlight.get(position);
//                        holder.dataView.setVisibility(View.VISIBLE);
//                        holder.emptyView.setVisibility(View.GONE);
//                        holder.tvInfo2.setVisibility(View.VISIBLE);
//                        holder.tvInfo1.setVisibility(View.VISIBLE);
//                        holder.ivIcon.setVisibility(View.VISIBLE);
//                        holder.ivInfoSeparation.setVisibility(View.VISIBLE);
//
//                        holder.tvInfo1.setText(pesananPesawat.getOrigin());
//                        holder.tvInfo2.setText(pesananPesawat.getDestination());
//                        holder.tvTanggalWaktuBerangkat.setText(pesananPesawat.getHari_keberangkatan() + ", " + pesananPesawat.getTanggal_keberangkatan() + " - " + pesananPesawat.getJam_keberangkatan());
//                        holder.tvNama.setText(pesananPesawat.getNama_maskapai().toUpperCase());
//                        holder.tvCode.setText(pesananPesawat.getKode_booking());
//                        Log.d(TAG, "onBindViewHolder: "+pesananPesawat.getKode_booking());
//                        Glide.with(context).load(pesananPesawat.getAirlineIcon()).into(holder.ivIcon);
//                        transaction_id = String.valueOf(pesananPesawat.getId_transaksi());
//                        url_struk = pesananPesawat.getUrl_struk();
//                        finalUrl_struk =pesananPesawat.getUrl_struk();
//                        url_pdf = pesananPesawat.getUrl_etiket();
//                        produk = "Pesawat";
//                        namaProduk = pesananPesawat.getNama_maskapai();
//                        kode = pesananPesawat.getKode_booking();
//                        finalkode=pesananPesawat.getKode_booking();
//                        for(int i = 0; i < pesananPesawat.getPenumpang().length; i++) {
//                            nama = pesananPesawat.getPenumpang()[0].getTitle() + " " + pesananPesawat.getPenumpang()[0].getNama();
//                        }
//
//                      //  nama = pesananPesawat.getPenumpangList().get(0).getTitle() + " " + pesananPesawat.getPenumpangList().get(0).getNama();
////                        nama = pesananPesawat.getPenumpang().get(0).getTitle() + " " + pesananPesawat.getPenumpangList().get(0).getNama();
//                      //  jsonObject = pesananPesawat.getJsonObject();
//                       // jsonObject= String.valueOf(pesananPesawat);//new GsonBuilder().create().toJson(this, EtiketFlightModel.class);
//                      //  pesananPesawatModel = pesananPesawat;
//                        finalPesananPesawatModel=pesananPesawat;
//                        //jsonObject=gson.toJson(pesananPesawat);
//
//
////                        Log.d(TAG, "onBindViewHolder: " + pesananPesawat.getPenumpangList().get(0).getTitle());
////                        Log.d(TAG, "onBindViewHolder: " + pesananPesawat.getPenumpangList().get(0).getNama());
//                      //  searchView.setVisibility(View.VISIBLE);
//                    } else {
//                     //   searchView.setVisibility(View.GONE);
//                        holder.dataView.setVisibility(View.GONE);
//                        holder.emptyView.setVisibility(View.GONE);
//                    }
//
//                    holder.ivMenu.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(@NonNull final View view) {
//                            subMenuView = view;
//                            PopupMenu popup = new PopupMenu(context, holder.ivMenu);
//                            popup.getMenuInflater().inflate(R.menu.core_popup_menu_list_pesanan, popup.getMenu());
//                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                                public boolean onMenuItemClick(@NonNull MenuItem item) {
//                                    int id = item.getItemId();
//                                    if (id == R.id.action_pop1) {
//                                        Intent intent = new Intent(TravelDaftarPesananActivity.this, TravelTiketPesananActivity.class);
//                                        intent.putExtra("url_struk", finalUrl_struk);
//                                        intent.putExtra("url_pdf", finalUrl_pdf);
//                                        intent.putExtra("id_transaksi", finalTransaction_id);
//                                        intent.putExtra("data", finalJsonObject.toString());
//                                        intent.putExtra("product", selectedProduct);
//                                        startActivity(intent);
//                                    } else if (id == R.id.action_pop2) {
//
//                                        isSendETiket = 1;
//                                        checkPermisionStorage();
//
//                                    } else if (id == R.id.action_pop3) {
//                                        kodeBooking = finalkode;
//                                        showToast("Printing......"+finalPesananPesawatModel.getKode_booking());
//                                        // getStruk(finalUrl_struk, 1, TravelDaftarPesananActivity.this);
//                                    }
//                                    return true;
//                                }
//                            });
//                            popup.show();
//                        }
//                    });
//
//                    holder.linMain.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            checkPermisionStorage();
////
//
//                        }
//                    });
//                    holder.btnETiket.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            checkPermisionStorage();
////
//
//                        }
//                    });
//
//                    break;
//                }
//
//                case "KERETA": {
//
//                    if (!mSwipeRefreshLayout.isRefreshing() && filterDataTrain.size() == 0 && position == 0) {
//                        holder.dataView.setVisibility(View.GONE);
//                        holder.emptyView.setVisibility(View.VISIBLE);
//                    } else if (filterDataTrain.size() > 0 && position > 0) {
//                        holder.dataView.setVisibility(View.VISIBLE);
//                        holder.emptyView.setVisibility(View.GONE);
//                        holder.tvInfo2.setVisibility(View.VISIBLE);
//                        holder.tvInfo1.setVisibility(View.VISIBLE);
//                        holder.ivIcon.setVisibility(View.VISIBLE);
//                        holder.ivInfoSeparation.setVisibility(View.VISIBLE);
//                        PesananKereta pesananKereta =  filterDataTrain.get(position - 1);
//                        holder.tvInfo1.setText(pesananKereta.getOrigin());
//                        holder.tvInfo2.setText(pesananKereta.getDestination());
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
//                        SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy - HH:mm", config.locale);
//                        try {
//                            Date checkInDate = sdf.parse(pesananKereta.getTanggal_keberangkatan() + pesananKereta.getJam_keberangkatan());
//                            holder.tvTanggalWaktuBerangkat.setText(pesananKereta.getHari_keberangkatan() + ", " + odf.format(checkInDate));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        holder.tvNama.setText(pesananKereta.getNama_kereta().toUpperCase());
//                        holder.tvCode.setText(pesananKereta.getKode_booking());
////                        holder.ivIcon.setVisibility(View.GONE);
//                        Glide.with(context).load(R.drawable.ic_kai).into(holder.ivIcon);
//                        transaction_id = pesananKereta.getId_transaksi();
//                        url_struk = pesananKereta.getUrl_struk();
//                        finalUrl_struk =pesananKereta.getUrl_struk();
//                        url_pdf = pesananKereta.getUrl_etiket();
//                        produk = "Kereta";
//                        namaProduk = pesananKereta.getNama_kereta();
//                        kode = pesananKereta.getKode_booking();
//                        finalkode=pesananKereta.getKode_booking();
//                        nama = pesananKereta.getPenumpangList().get(0).getNama();
//                       // jsonObject = pesananKereta.getJsonObject();
//                     //   searchView.setVisibility(View.VISIBLE);
//                    } else {
//                   //     searchView.setVisibility(View.GONE);
//                        holder.dataView.setVisibility(View.GONE);
//                        holder.emptyView.setVisibility(View.GONE);
//                    }
//
//
//                    holder.ivMenu.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(@NonNull final View view) {
//                            subMenuView = view;
//                            PopupMenu popup = new PopupMenu(context, holder.ivMenu);
//                            popup.getMenuInflater().inflate(R.menu.core_popup_menu_list_pesanan, popup.getMenu());
//                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                                public boolean onMenuItemClick(@NonNull MenuItem item) {
//                                    int id = item.getItemId();
//                                    if (id == R.id.action_pop1) {
//                                        Intent intent = new Intent(TravelDaftarPesananActivity.this, TravelTiketPesananActivity.class);
//                                        intent.putExtra("url_struk", finalUrl_struk);
//                                        intent.putExtra("url_pdf", finalUrl_pdf);
//                                        intent.putExtra("id_transaksi", finalTransaction_id);
//                                        intent.putExtra("data", finalJsonObject.toString());
//                                        intent.putExtra("product", selectedProduct);
//                                        startActivity(intent);
//                                    } else if (id == R.id.action_pop2) {
//
//                                        isSendETiket = 1;
//                                        checkPermisionStorage();
//
//                                    } else if (id == R.id.action_pop3) {
//                                        kodeBooking = finalkode;
//                                        showToast("Printing......");
//                                       // getStruk(finalUrl_struk, 1, TravelDaftarPesananActivity.this);
//                                    }
//                                    return true;
//                                }
//                            });
//                            popup.show();
//                        }
//                    });
//
//                    holder.linMain.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            checkPermisionStorage();
////
//
//                        }
//                    });
//                    holder.btnETiket.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            checkPermisionStorage();
////
//
//                        }
//                    });
//
//
//                    break;
//                }
////                else if (type.equals("HOTEL")) {
////                    PesananHotel pesananHotel = (PesananHotel) data.get(position - 1);
////                    holder.tvNama.setText(pesananHotel.getNama_hotel());
////                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////                    SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", config.locale);
////                    try {
////                        Date checkInDate = sdf.parse(pesananHotel.getCheck_in());
////                        Date checkOutDate = sdf.parse(pesananHotel.getCheck_out());
////                        holder.tvTanggalWaktuBerangkat.setText(odf.format(checkInDate) + " - " + odf.format(checkOutDate));
////
////                    } catch (ParseException e) {
////                        e.printStackTrace();
////                    }
////                    holder.tvInfo2.setVisibility(View.GONE);
////                    holder.tvInfo1.setVisibility(View.GONE);
////                    holder.ivIcon.setVisibility(View.GONE);
////                    holder.ivInfoSeparation.setVisibility(View.GONE);
////                    transaction_id = pesananHotel.getId_transaksi();
////                    url_struk = pesananHotel.getUrl_struk();
////                    url_pdf = pesananHotel.getUrl_etiket();
////                    produk="Hotel";
////                    namaProduk=pesananHotel.getNama_hotel();
////                    kode=pesananHotel.getKode_booking();
////
////                    nama=pesananHotel.getTamuList().get(0).getNama_depan()+" "+pesananHotel.getTamuList().get(0).getNama_belakang();
////                    jsonObject = pesananHotel.getJsonObject();
////                }
//                case "KAPAL": {
//
//                    if (!mSwipeRefreshLayout.isRefreshing() && filterDataShip.size() == 0 && position == 0) {
//                        holder.dataView.setVisibility(View.GONE);
//                        holder.emptyView.setVisibility(View.VISIBLE);
//                    } else if (filterDataShip.size() > 0 && position > 0) {
//                        holder.dataView.setVisibility(View.VISIBLE);
//                        holder.emptyView.setVisibility(View.GONE);
//                        holder.tvInfo2.setVisibility(View.VISIBLE);
//                        holder.tvInfo1.setVisibility(View.VISIBLE);
//                        holder.ivIcon.setVisibility(View.VISIBLE);
//                        holder.ivInfoSeparation.setVisibility(View.VISIBLE);
//                        PesananKapal pesananKapal =  filterDataShip.get(position - 1);
//                        holder.tvInfo1.setText(pesananKapal.getOrigin());
//                        holder.tvInfo2.setText(pesananKapal.getDestination());
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//                        SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", config.locale);
//                        try {
//                            Date checkInDate = sdf.parse(pesananKapal.getTanggal_keberangkatan());
//                            holder.tvTanggalWaktuBerangkat.setText(pesananKapal.getHari_keberangkatan() + ", " + odf.format(checkInDate) + " - " + pesananKapal.getJam_keberangkatan());
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        holder.tvNama.setText(pesananKapal.getNama_kapal().toUpperCase());
//                        holder.tvCode.setText(pesananKapal.getKode_booking());
////                        holder.ivIcon.setVisibility(View.GONE);
//                        Glide.with(context).load(R.drawable.ic_pelni).into(holder.ivIcon);
//                        transaction_id = pesananKapal.getId_transaksi();
//                        url_struk = pesananKapal.getUrl_struk();
//                        finalUrl_struk =pesananKapal.getUrl_struk();
//                        url_pdf = pesananKapal.getUrl_etiket();
//                        produk = "Pelni";
//                        namaProduk = pesananKapal.getNama_kapal();
//                        kode = pesananKapal.getKode_booking();
//                        finalkode=pesananKapal.getKode_booking();
//                        nama = pesananKapal.getPenumpangList().get(0).getNama();
//                        //jsonObject = pesananKapal.getJsonObject();
//                     //   searchView.setVisibility(View.VISIBLE);
//                    } else {
//                     //   searchView.setVisibility(View.GONE);
//                        holder.dataView.setVisibility(View.GONE);
//                        holder.emptyView.setVisibility(View.GONE);
//                    }
//                    break;
//                }
////                    case "WISATA": {
////                        PesananWisata pesananWisata = (PesananWisata) filterData.get(position - 1);
////                        holder.tvNama.setText(pesananWisata.getDestination());
////                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////                        SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", config.locale);
////                        try {
////                            Date checkInDate = sdf.parse(pesananWisata.getTanggal_mulai());
////                            Date checkOutDate = sdf.parse(pesananWisata.getTangal_selesai());
////                            long difference = Math.abs(checkInDate.getTime() - checkOutDate.getTime());
////                            long differenceDates = difference / (24 * 60 * 60 * 1000);
////                            holder.tvInfo1.setText(odf.format(checkInDate) + " - " + odf.format(checkOutDate));
////                            holder.tvTanggalWaktuBerangkat.setText(differenceDates + " Hari");
////                        } catch (ParseException e) {
////                            e.printStackTrace();
////                        }
////                        holder.tvInfo2.setVisibility(View.GONE);
////                        holder.ivIcon.setVisibility(View.GONE);
////                        holder.ivInfoSeparation.setVisibility(View.GONE);
////                        transaction_id = pesananWisata.getId_transaksi();
////                        url_struk = pesananWisata.getUrl_struk();
////                        url_pdf = pesananWisata.getUrl_etiket();
////                        produk = "Paket Wisata";
////                        namaProduk = pesananWisata.getDestination();
////                        kode = pesananWisata.getKode_booking();
////
////                        nama = pesananWisata.getNama_peserta();
////                        jsonObject = pesananWisata.getJsonObject();
////                    } else {
////                        holder.dataView.setVisibility(View.GONE);
////                        holder.emptyView.setVisibility(View.GONE);
////                    }
////                        break;
////                    }
//            }
//
//            finalTransaction_id = transaction_id;
////            finalUrl_struk = url_struk;
//            finalUrl_pdf = url_pdf;
//            finalproduk = produk;
//            finalnamaProduk = namaProduk;
//
//            finalnama = nama;
//          //  finalkode = kode;
//          //  finalJsonObject = jsonObject;
//
//
//        }
//
//        @Override
//        public int getItemCount() {
//int count=0;
//            if ("PESAWAT".equals(type)) {
//                count= filterDataFlight== null ? 0 :filterDataFlight.size() + 1;
//            } else if ("KERETA".equals(type)) {
//                count=  filterDataTrain == null ? 0 :filterDataTrain.size() + 1;
//            } else if ("KAPAL".equals(type)) {
//                count=  filterDataShip == null ? 0 :filterDataShip.size() + 1;
//            }
//return count;
//        }
//
//        @Override
//        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//            super.onAttachedToRecyclerView(recyclerView);
//        }
//
//        @NonNull
//        @Override
//        public Filter getFilter() {
//            return new Filter() {
//               // FilterResults filterResults = new FilterResults();
//
//                @NonNull
//                @Override
//                protected FilterResults performFiltering(@NonNull CharSequence charSequence) {
//                    Log.d(TAG, "performFiltering: "+charSequence);
//                    String charString = charSequence.toString().toLowerCase();
//
//                    if (charString.isEmpty()) {
//                        if ("PESAWAT".equals(type)) {
//                            filterDataFlight = dataFlight;
//                        } else if ("KERETA".equals(type)) {
//                            filterDataTrain = dataTrain;
//                        } else if ("KAPAL".equals(type)) {
//                            filterDataShip = dataShip;
//                        }
//
//                    } else {
//
//
//                        if ("PESAWAT".equals(type)) {
//                            ArrayList<EtiketFlightModel.Data> filteredList = new ArrayList<>();
////                                PesananPesawat pesananPesawat = (PesananPesawat)
//                            for (EtiketFlightModel.Data produk : dataFlight) {
//
//                                if (produk.getKode_booking().toLowerCase().contains(charString)) {
//
//                                    filteredList.add(produk);
//                                }
//                            }
//                            filterDataFlight = filteredList;
//                            // filterResults.values = filterDataFlight;
//                            // return filterResults;
//                        } else if ("KERETA".equals(type)) {
//                            List<PesananKereta> filteredList = new ArrayList<>();
//                            for (PesananKereta produk : dataTrain) {
//
//                                if (produk.getKode_booking().toLowerCase().contains(charString)) {
//
//                                    filteredList.add(produk);
//                                }
//                            }
//                            filterDataTrain = filteredList;
//                            //  filterResults.values = filterDataTrain;
//                            //  return filterResults;
//                        } else if ("KAPAL".equals(type)) {
//                            List<PesananKapal> filteredList = new ArrayList<>();
//                            for (PesananKapal produk : dataShip) {
//
//                                if (produk.getKode_booking().toLowerCase().contains(charString)) {
//
//                                    filteredList.add(produk);
//                                }
//                            }
//                            filterDataShip = filteredList;
//                        }
//
//
//                    }
//
//                    FilterResults filterResults = new FilterResults();
//                    if ("PESAWAT".equals(type)) {
//                        filterResults.values = filterDataFlight;
//                    } else if ("KERETA".equals(type)) {
//                        filterResults.values = filterDataTrain;
//                    } else if ("KAPAL".equals(type)) {
//                        filterResults.values = filterDataShip;
//                    }
//
//
//
//                    return filterResults;
//                }
//
//
//                @Override
//                protected void publishResults(CharSequence charSequence, @NonNull FilterResults
//                        filterResults) {
//                    if ("PESAWAT".equals(type)) {
//                        filterDataFlight = (ArrayList<EtiketFlightModel.Data>) filterResults.values;
//                    } else if ("KERETA".equals(type)) {
//                        filterDataTrain = (List<PesananKereta>) filterResults.values;
//                    } else if ("KAPAL".equals(type)) {
//                        filterDataShip = (List<PesananKapal>) filterResults.values;
//                    }
//
//
//                    notifyDataSetChanged();
//                }
//            };
//        }
//
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            LinearLayout dataView, linMain;
//            RelativeLayout emptyView;
//            TextView tvInfo1, tvInfo2, tvTanggalWaktuBerangkat, tvNama, tvCode;
//            ImageView ivInfoSeparation, ivIcon, ivMenu;
//            AppCompatButton btnETiket;
//
//            public ViewHolder(@NonNull View view) {
//                super(view);
//                linMain = view.findViewById(R.id.linMain);
//                dataView = view.findViewById(R.id.rel_dataView);
//                emptyView = view.findViewById(R.id.rel_EmptyViewItem1);
//                tvInfo1 = view.findViewById(R.id.tvInfo1);
//                tvInfo2 = view.findViewById(R.id.tvInfo2);
//                tvTanggalWaktuBerangkat = view.findViewById(R.id.tvTanggalWaktuBerangkat);
//                tvNama = view.findViewById(R.id.tvNama);
//                tvCode = view.findViewById(R.id.tvCode);
//                ivInfoSeparation = view.findViewById(R.id.ivInfoSeparation);
//                ivIcon = view.findViewById(R.id.ivIcon);
//                ivMenu = view.findViewById(R.id.ivMenu);
//                btnETiket = view.findViewById(R.id.btnETiket);
//            }
//        }
//    }

    private void checkPermisionStorage() {
        if (ContextCompat.checkSelfPermission(TravelDaftarPesananActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(TravelDaftarPesananActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //    Log.i("Permission is require first time", "...OK...getPermission() method!..if");
            ActivityCompat.requestPermissions(TravelDaftarPesananActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    RequestCode.ActionCode_GROUP_STORAGE);
            // return;
        } else {
            //if (isSendETiket == 1) {
                sendETiket();
//            } else {
//                showDetailPesanan();
//            }
        }

    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RequestCode.ActionCode_GROUP_STORAGE) {
            if ((grantResults.length > 0) && (grantResults[0] + grantResults[1])
                    == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
//                if (isSendETiket == 1) {
                    sendETiket();
//                } else {
//                    showDetailPesanan();
//                }
            } else {
                new_popup_alert_two_buttonPermision(this, "Peringatan", R.string.content_alert_group_storage);

            }
        }
    }

    private void sendETiket() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FileUtils.doCekPDF(finalTransaction_id + ".pdf", finalUrl_pdf);
              //  doCekPDF(finalTransaction_id + ".pdf", finalUrl_pdf);
                // doCekPDF(finalTransaction_id , finalUrl_pdf);
                String path = Environment.getExternalStorageDirectory().toString();
//                                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                File dir = new File(path, "/FastPay/struk/pdfs/");
                final File file = new File(dir, finalTransaction_id + ".pdf");
                //   final File file = new File(dir, finalTransaction_id);
                // Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                if (file.exists()) {
//                                            intentShareFile.setType("application/pdf");
//                                            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));
//                                            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
//                                                    "Sharing File...");
//                                            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
//                                            startActivity(Intent.createChooser(intentShareFile, "Share File"));
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            //   openSendVia(view, Uri.parse("file://" + file.getAbsolutePath()), finalproduk, finalnamaProduk, finalkode, finalnama);
                            openSendVia(subMenuView, Uri.fromFile(file), finalproduk, finalnamaProduk, finalkode, finalnama, file);
                        }
                    });
                } else {
//                                            showToastCustom(DaftarPesananActivity.this, 1, "File tidak ditemukan!");
                    snackBarCustomAction(findViewById(R.id.rootLayout), 0, "File tidak ditemukan!", 1);
                }
            }
        });
    }

//    private void showDetailPesanan() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(TravelDaftarPesananActivity.this, TravelTiketPesananActivity.class);
//                intent.putExtra("url_struk", finalUrl_struk);
//                intent.putExtra("url_pdf", finalUrl_pdf);
//                intent.putExtra("id_transaksi", finalTransaction_id);
////                intent.putExtra("data", finalJsonObject);
//                intent.putExtra("data", (Parcelable) finalPesananPesawatModel);
//                intent.putExtra("product", selectedProduct);
//
//                startActivity(intent);
//            }
//        });
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            //    searchView.setFocusable(false);
            // relTampilkan.requestFocus();
//            BaseActivity.getInstance().closeKeyboard(getActivity());
            startDate = data.getStringExtra("date");
            textViewTanggalAwal.setText(data.getStringExtra("dateShow"));
        } else if (requestCode == 2 && resultCode == AppCompatActivity.RESULT_OK) {
            //  searchView.setFocusable(false);
            // relTampilkan.requestFocus();
            //  BaseActivity.getInstance().closeKeyboard(getActivity());
            endDate = data.getStringExtra("date");
            textViewTanggalAkhir.setText(data.getStringExtra("dateShow"));
        } else if (requestCode == ActionCode.BARCODE) {
try {
    String idPel = data.getStringExtra(Intents.Scan.RESULT);

    searchAutoComplete.setText(idPel);
    //    bookAdapter.getFilter().filter(idPel);
}catch (Exception e){

}

        }else if(resultCode == AppCompatActivity.RESULT_CANCELED){

        }
    }

    BluetoothDevice bDevice;
    @NonNull
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            // Get the received random number
            boolean receivedNumber = intent.getBooleanExtra("finish", false);
            boolean receivedSocket = intent.getBooleanExtra("socket", false);
            String deviceName = intent.getStringExtra("device_name");
            if (receivedNumber == true) {
//                buttonCetak.setFocusableInTouchMode(true);
//                buttonCetak.setEnabled(true);
//                buttonCetak.setText(R.string.cetak);
            } else if (receivedSocket == false) {
                snackBarCustomAction(findViewById(R.id.bottom_toolbar), 0, "Tidak dapat terhubung ke printer bluetooth " + deviceName + "\n Pastikan printer bluetooth " + deviceName + "anda telah menyala", WARNING);
                bDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                IntentFilter filter = new IntentFilter();

//        String action = "android.bleutooth.device.action.UUID";
                filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);


                registerReceiver(mReceiver, filter);
//                buttonCetak.setFocusableInTouchMode(true);
//                buttonCetak.setClic(true);
//                buttonCetak.setText(R.string.cetak);
            }
            // Display da notification that the broadcast received
            //     Toast.makeText(context,"Received : " + receivedNumber,Toast.LENGTH_SHORT).show();
        }
    };
    // @Override
//    public void callbackReturn(boolean finish) {
//      //  textResult.setText("Callback function called");
//    }
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, @NonNull Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                try {
                    UUID BPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    //  bluetoothAdapter.getRemoteDevice(device.getAddress());
                    //  mmDevice=bluetoothAdapter.getRemoteDevice(device.getAddress());
                    //  socket = createBluetoothSocket(device);
                    try {
                        socket = bDevice.createRfcommSocketToServiceRecord(BPP);
                        // socket = createBluetoothSocket(device);
//
//                    } catch (IOException e) {
//
//                        showToast("Tidak dapat melakukan koneksi awal dengan printer bluetooth"); // Unable
//                    }
                        //  Method m = device.getClass().getMethod("createBond", int.class);
//                    Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
//                    try {
//
                        // Method m = device.getClass().getMethod("createRfcommSocket", int.class);
                        //     Method m = device.getClass().getMethod("createInsecureRfcommSocket", int.class);

                        // socket = (BluetoothSocket) m.invoke(device, 1);
                        // socket = (BluetoothSocket) m.invoke(device, BPP);
                        Method m = bDevice.getClass().getMethod("createRfcommSocket", int.class);
                        socket = (BluetoothSocket) m.invoke(bDevice, Integer.valueOf(1));
                        socket.connect();
                        // cetak(InqueryResultActivity.this);
                        Log.d(TAG, "Connected 1 " + socket.isConnected());
                    } catch (Exception e) {
                        Log.d(TAG, "onReceive: " + e.toString());
                    }
                } catch (Exception e) {


                    Log.d(TAG, "print exception: " + e.toString());


//                Intent intent = new Intent(this, SettingPrinterActivity.class);
//                startActivity(intent);

                }
            }
        }
    };


}
