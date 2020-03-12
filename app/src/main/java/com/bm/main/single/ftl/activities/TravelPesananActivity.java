package com.bm.main.single.ftl.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.google.android.material.tabs.TabLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.constants.TravelPath;
import com.bm.main.single.ftl.models.JSONModel;
import com.bm.main.single.ftl.models.PesananKapal;
import com.bm.main.single.ftl.models.PesananKereta;
import com.bm.main.single.ftl.models.PesananPesawat;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TravelPesananActivity extends BaseActivity implements ProgressResponseCallback {
    private static final String TAG =TravelPesananActivity.class.getSimpleName();
    private TabLayout tabLayout;

    SwipeRefreshLayout mSwipeRefreshLayout;
    @NonNull
    public String selectedProduct = "PESAWAT";
    BookAdapter adapter;
    RelativeLayout bottom_toolbar_calendar;
    LinearLayout linTanggalAwal, linTanggalAkhir;
    RelativeLayout relTampilkan;
    String startDate, endDate;
    TextView textViewTanggalAwal;
    TextView textViewTanggalAkhir;

    boolean isStillRunning = false;
    @NonNull
    private int[] tabIcons = {
            R.drawable.pesawat,
            R.drawable.kereta,
            R.drawable.kapal,


//            R.drawable.wisata,



    };
    private RecyclerView mRecyclerView;
    private int requestCode;

    LinearLayout tabPesawat, tabKereta, tabPelni;//, tabWisata;
    ImageView iconTabItemPesawat, iconTabItemKereta, iconTabItemPelni;//, iconTabItemWisata;
    TextView textTabItemPesawat, textTabItemKereta,textTabItemPelni;//, textTabItemWisata;
//
//    private String startDateFrom;
//    private String endDateFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_pesanan_activit);
      //  Intent i=getIntent();





        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pesanan");
        init(0);
        tabLayout = findViewById(R.id.tabs);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new BookAdapter(this);
        mRecyclerView.setAdapter(adapter);



        textViewTanggalAwal = findViewById(R.id.textViewTanggalAwal);

        textViewTanggalAkhir = findViewById(R.id.textViewTanggalAkhir);





            Date now = new Date();
//        SimpleDateFormat sdf=new SimpleDateFormat("EEEEEE,dd MMMM yyyy",config.locale);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", SBFApplication.config.locale);
            SimpleDateFormat formatterShow = new SimpleDateFormat("dd MMM yyyy", SBFApplication.config.locale);
           String showDateNow = formatterShow.format(now);
            String dateNow = formatter.format(now);
            startDate = dateNow;
            endDate = dateNow;



        textViewTanggalAwal.setText(showDateNow);
        textViewTanggalAkhir.setText(showDateNow);



        bottom_toolbar_calendar = findViewById(R.id.bottom_toolbar_calendar);
        linTanggalAwal = findViewById(R.id.linTanggalAwal);
        linTanggalAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCode = 1;
                Intent intent = new Intent(TravelPesananActivity.this, TravelTanggalPesananActivity.class);
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
                Intent intent = new Intent(TravelPesananActivity.this, TravelTanggalPesananActivity.class);
                intent.putExtra("initTanggal", "akhir");
                intent.putExtra("initValue", startDate);
                startActivityForResult(intent, requestCode);
                //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
            }
        });

        relTampilkan = findViewById(R.id.relTampilkan);
        relTampilkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshItems();
            }
        });
        CardView etiket = findViewById(R.id.card_etiket);
        etiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TravelPesananActivity.this, TravelDaftarPesananActivity.class);

                intent.putExtra("product", selectedProduct);
                startActivity(intent);
            }
        });
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });


        bindWidgetsWithAnEvent();
        setupTabLayout();

        Bundle b = new Bundle();
        b = getIntent().getBundleExtra("bundle");
        String namaProduk="";
        if(b!=null) {
//            startDateFrom = i.getStringExtra("valueDateAwal");
            //  intent.getStringExtra("valueDataShowAwal");
//            endDateFrom = i.getStringExtra("valueDateAkhir");
            //intent.getStringExtra("valueDataShowAkhir");
            namaProduk = b.getString("valueProduk");
            if ("KERETA API".equals(namaProduk)) {
                namaProduk = "KERETA";
                tabLayout.getTabAt(1).select();
                tabLayout.setSelected(true);

            } else if ("TIKET PELNI".equals(namaProduk)) {
                namaProduk = "KAPAL";
                tabLayout.getTabAt(2).select();
                tabLayout.setSelected(true);
            } else {
                namaProduk = "PESAWAT";
                tabLayout.getTabAt(0).select();
                tabLayout.setSelected(true);
            }


//            if(!namaProduk.equals("")){
            Bundle bundle = new Bundle();
            Intent intent = new Intent(TravelPesananActivity.this, TravelDaftarPesananActivity.class);
            bundle.putString("valueDateAwal", b.getString("valueDateAwal"));
            bundle.putString("valueDataShowAwal", b.getString("valueDataShowAwal"));
            bundle.putString("valueDateAkhir", b.getString("valueDateAkhir"));
            bundle.putString("valueDataShowAkhir", b.getString("valueDataShowAkhir"));
            //  intent.putExtra("valueProduk",namaProdukTravel);
            bundle.putString("product", namaProduk);
            intent.putExtra("bundle1",bundle);
            startActivity(intent);
//            }
        }



    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {
        if(tabIcons.length>3){
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        tabPesawat = (LinearLayout) LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.core_custom_tab, null);
        iconTabItemPesawat = tabPesawat.findViewById(R.id.iconTab);
        iconTabItemPesawat.setImageResource(tabIcons[0]);
        textTabItemPesawat = tabPesawat.findViewById(R.id.textTab);
        textTabItemPesawat.setText("Pesawat");
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabPesawat));

        tabKereta = (LinearLayout) LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.core_custom_tab, null);
        iconTabItemKereta = tabKereta.findViewById(R.id.iconTab);
        iconTabItemKereta.setImageResource(tabIcons[1]);
        textTabItemKereta = tabKereta.findViewById(R.id.textTab);
        textTabItemKereta.setText("Kereta");
        textTabItemKereta.setTag(1);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabKereta));

        tabPelni = (LinearLayout) LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.core_custom_tab, null);
        iconTabItemPelni = tabPelni.findViewById(R.id.iconTab);
        iconTabItemPelni.setImageResource(tabIcons[2]);
        textTabItemPelni = tabPelni.findViewById(R.id.textTab);
        textTabItemPelni.setText("Pelni");
        textTabItemKereta.setTag(2);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabPelni));


//        tabHotel = (LinearLayout) LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.core_custom_tab, null);
//        iconTabItemHotel = tabHotel.findViewById(R.id.iconTab);
//        iconTabItemHotel.setImageResource(tabIcons[3]);
//        textTabItemHotel = tabHotel.findViewById(R.id.textTab);
//        textTabItemHotel.setText("Hotel");
//        textTabItemKereta.setTag(3);
//        tabLayout.addTab(tabLayout.newTab().setCustomView(tabHotel));


//        tabWisata = (LinearLayout) LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.core_custom_tab, null);
//        iconTabItemWisata = tabWisata.findViewById(R.id.iconTab);
//        iconTabItemWisata.setImageResource(tabIcons[3]);
//        textTabItemWisata = tabWisata.findViewById(R.id.textTab);
//        textTabItemWisata.setText("Paket Wisata");
//        textTabItemKereta.setTag(3);
//        tabLayout.addTab(tabLayout.newTab().setCustomView(tabWisata));

//        tabRental = (LinearLayout) LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.core_custom_tab, null);
//        iconTabItemRental = tabRental.findViewById(R.id.iconTab);
//        iconTabItemRental.setImageResource(tabIcons[5]);
//        textTabItemRental = tabRental.findViewById(R.id.textTab);
//        textTabItemRental.setText("Pembayaran");
//        textTabItemKereta.setTag(5);
//        tabLayout.addTab(tabLayout.newTab().setCustomView(tabRental));
//
//
//        tabUmroh = (LinearLayout) LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.core_custom_tab, null);
//        iconTabItemUmroh = tabUmroh.findViewById(R.id.iconTab);
//        iconTabItemUmroh.setImageResource(tabIcons[6]);
//        textTabItemUmroh = tabUmroh.findViewById(R.id.textTab);
//        textTabItemUmroh.setText("Umroh");
//        textTabItemKereta.setTag(6);
//        tabLayout.addTab(tabLayout.newTab().setCustomView(tabUmroh));
//
//        tabTravelBus = (LinearLayout) LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.core_custom_tab, null);
//        iconTabItemTravelBus = tabTravelBus.findViewById(R.id.iconTab);
//        iconTabItemTravelBus.setImageResource(tabIcons[7]);
//        textTabItemTravelBus = tabTravelBus.findViewById(R.id.textTab);
//        textTabItemTravelBus.setText("Travel & Bus");
//        textTabItemKereta.setTag(7);
//        tabLayout.addTab(tabLayout.newTab().setCustomView(tabTravelBus));
//
//
//        tabRailink = (LinearLayout) LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.core_custom_tab, null);
//        iconTabItemRailink = tabRailink.findViewById(R.id.iconTab);
//        iconTabItemRailink.setImageResource(tabIcons[8]);
//        textTabItemRailink = tabRailink.findViewById(R.id.textTab);
//        textTabItemRailink.setText("Railink");
//        textTabItemRailink.setTag(8);
//        tabLayout.addTab(tabLayout.newTab().setCustomView(tabRailink));
    }

    private void setupTabLayout() {
        Log.d(TAG, "setupTabLayout: ");
        setupTabIcons();
    }

    private void bindWidgetsWithAnEvent() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView textTab;
                switch (position) {
                    case 0:
                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.iconMenuPesawat));
                        textTab = tabPesawat.findViewById(R.id.textTab);
                        textTab.setTextColor(getResources().getColor(R.color.iconMenuPesawat));
                        selectedProduct = "PESAWAT";
                        break;
                    case 1:
                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.iconMenuKereta));
                        textTab = tabKereta.findViewById(R.id.textTab);
                        textTab.setTextColor(getResources().getColor(R.color.iconMenuKereta));
                        selectedProduct = "KERETA";
                        break;
                    case 2:
                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.iconMenuPelni));
                        textTab = tabPelni.findViewById(R.id.textTab);
                        textTab.setTextColor(getResources().getColor(R.color.iconMenuPelni));
                        selectedProduct = "KAPAL";
                        break;
//                    case 3:
//                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.iconMenuHotel));
//                        textTab = tabHotel.findViewById(R.id.textTab);
//                        textTab.setTextColor(getResources().getColor(R.color.iconMenuHotel));
//                        selectedProduct = "HOTEL";
//                        break;
//                    case 3:
//                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.iconMenuWisata));
//                        textTab = tabWisata.findViewById(R.id.textTab);
//                        textTab.setTextColor(getResources().getColor(R.color.iconMenuWisata));
//                        selectedProduct = "WISATA";
//                        break;
//                    case 5:
//                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.iconMenuRental));
//                        textTab = tabRental.findViewById(R.id.textTab);
//                        textTab.setTextColor(getResources().getColor(R.color.iconMenuRental));
//                        selectedProduct = "RENTAL";
//                        break;
//                    case 6:
//                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.iconMenuUmroh));
//                        textTab = tabUmroh.findViewById(R.id.textTab);
//                        textTab.setTextColor(getResources().getColor(R.color.iconMenuUmroh));
//                        selectedProduct = "UMROH";
//                        break;
//                    case 7:
//                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.iconMenuTravel));
//                        textTab = tabTravelBus.findViewById(R.id.textTab);
//                        textTab.setTextColor(getResources().getColor(R.color.iconMenuTravel));
//                        selectedProduct = "TRAVEL";
//                        break;
//                    case 8:
//                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.iconMenuRailink));
//                        textTab = tabRailink.findViewById(R.id.textTab);
//                        textTab.setTextColor(getResources().getColor(R.color.iconMenuRailink));
//                        selectedProduct = "RAILINK";
//                        break;


                }
                if (isStillRunning) {
                    RequestUtilsTravel.cancelTravel();
                }
                refreshItems();
            }

            @Override
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {
                if (isStillRunning) {
                    RequestUtilsTravel.cancelTravel();
                }
                Log.d(TAG, "onTabUnselected: " + tab.getPosition());
                int position = tab.getPosition();
                TextView textTab;
                switch (position) {
                    case 0:
                        textTab = tabPesawat.findViewById(R.id.textTab);
                        textTab.setTextColor(getResources().getColor(R.color.md_grey_500));
                        break;

                    case 1:

                        textTab = tabKereta.findViewById(R.id.textTab);
                        textTab.setTextColor(getResources().getColor(R.color.md_grey_500));
                        break;
                    case 2:

                        textTab = tabPelni.findViewById(R.id.textTab);
                        textTab.setTextColor(getResources().getColor(R.color.md_grey_500));
                        break;
//                    case 3:
//
//                        textTab = tabHotel.findViewById(R.id.textTab);
//                        textTab.setTextColor(getResources().getColor(R.color.md_grey_500));
//                        break;

//                    case 3:
//
//                        textTab = tabWisata.findViewById(R.id.textTab);
//                        textTab.setTextColor(getResources().getColor(R.color.md_grey_500));
//                        break;


//                    case 5:
//
//                        textTab = tabRental.findViewById(R.id.textTab);
//                        textTab.setTextColor(getResources().getColor(R.color.md_grey_500));
//                        break;
//                    case 6:
//
//                        textTab = tabUmroh.findViewById(R.id.textTab);
//                        textTab.setTextColor(getResources().getColor(R.color.md_grey_500));
//                        break;
//                    case 7:
//
//                        textTab = tabTravelBus.findViewById(R.id.textTab);
//                        textTab.setTextColor(getResources().getColor(R.color.md_grey_500));
//                        break;
//                    case 8:
//
//                        textTab = tabRailink.findViewById(R.id.textTab);
//                        textTab.setTextColor(getResources().getColor(R.color.md_grey_500));
//                        break;

                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    void refreshItems() {
//        Date now=new Date();
//        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(now);
//        cal.add(Calendar.DATE, -30);
//        String dateBefore30Days = dateFormat.format(cal.getTime());
//        String date = dateFormat.format(now);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product", selectedProduct);
            jsonObject.put("startDate", startDate);
            jsonObject.put("endDate", endDate);
            jsonObject.put("token", PreferenceClass.getToken());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "showList: app/transaction_book_list" + jsonObject.toString());

        mSwipeRefreshLayout.setRefreshing(true);
        isStillRunning = true;
        adapter.type = selectedProduct;
        adapter.data.clear();
        adapter.notifyDataSetChanged();


        RequestUtilsTravel.transportWithProgressResponse(TravelPesananActivity.this,TravelPath.TRANSACTIONBOOKLIST, jsonObject, TravelActionCode.TRANSACTIONBOOKLIST,this);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        try {
            if (actionCode == TravelActionCode.TRANSACTIONBOOKLIST) {
                JSONArray data = response.getJSONArray("data");
                switch (selectedProduct) {
                    case "PESAWAT":
                        for (int i = 0; i < data.length(); i++) {
                            adapter.data.add(new PesananPesawat(data.getJSONObject(i)));
                        }
//                } else if (selectedProduct.equals("HOTEL")) {
//                    for (int i = 0; i < data.length(); i++) {
//                        adapter.data.add(new PesananHotel(data.getJSONObject(i)));
//                    }
                        break;
                    case "KERETA":
                        for (int i = 0; i < data.length(); i++) {
                            adapter.data.add(new PesananKereta(data.getJSONObject(i)));
                        }
                        break;
                    case "KAPAL":
                        for (int i = 0; i < data.length(); i++) {
                            adapter.data.add(new PesananKapal(data.getJSONObject(i)));
                        }
                        break;
//                    case "WISATA":
//                        for (int i = 0; i < data.length(); i++) {
//                            adapter.data.add(new PesananWisata(data.getJSONObject(i)));
//                        }
//                        break;
                }

                try {
//                    this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                    isStillRunning = false;
//                        }
//                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
               closeProgressBarDialog();
                Intent intent = new Intent(TravelPesananActivity.this, TravelDetailPesananActivity.class);

                intent.putExtra("data", response.toString());
                startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);
            }

        } catch (JSONException jsone) {

        }

    }

    @Override
    public void onFailure(final int actionCode, String responseCode, final String responseDescription, Throwable throwable) {
//        Log.d(TAG, "onFailure: " + throwable.toString());

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (actionCode == TravelActionCode.TRANSACTIONLIST) {

                    mSwipeRefreshLayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                    isStillRunning = false;
                    // ((HomeActivity) this).showToastCustom(this, 1, responseDescription);
                } else {
                 //  closeProgressBarDialog();
                    // ((HomeActivity) this).showToastCustom(this, 1, responseDescription);
                }
            }
        });


    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {

    }


    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
        Context context;
        LayoutInflater inflater;
        @NonNull
        List<JSONModel> data = new ArrayList<>();
        @NonNull
        public String type = "PESAWAT";

        public BookAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView;
            itemView = inflater.inflate(R.layout.list_item_pesanan, parent, false);
            return new ViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            String transaction_id = "";

            int isExpired=0;

            if (!mSwipeRefreshLayout.isRefreshing() && data.size() == 0 && position == 0) {
                holder.dataView.setVisibility(View.GONE);
                holder.emptyView.setVisibility(View.VISIBLE);
            } else if (data.size() > 0 && position > 0) {
                holder.dataView.setVisibility(View.VISIBLE);
                holder.emptyView.setVisibility(View.GONE);

                holder.tvInfo2.setVisibility(View.VISIBLE);
                holder.tvNama.setVisibility(View.VISIBLE);
                holder.ivIcon.setVisibility(View.VISIBLE);
                holder.ivInfoSeparation.setVisibility(View.VISIBLE);

                switch (type) {
                    case "PESAWAT":
                        PesananPesawat pesananPesawat = (PesananPesawat) data.get(position - 1);
                        holder.tvInfo1.setText(pesananPesawat.getOrigin());
                        holder.tvInfo2.setText(pesananPesawat.getDestination());

                        // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        // SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", ((BaseActivity) this).config.locale);
//                        try {
//                            Date checkInDate = sdf.parse(pesananPesawat.getTanggal_keberangkatan());
//                            holder.tvTanggalWaktuBerangkat.setText(pesananPesawat.getHari_keberangkatan() + ", " + odf.format(checkInDate) + " - " + pesananPesawat.getJam_keberangkatan());
//
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
                        holder.tvTanggalWaktuBerangkat.setText(pesananPesawat.getHari_keberangkatan() + ", " + pesananPesawat.getTanggal_keberangkatan() + " - " + pesananPesawat.getJam_keberangkatan());
                        holder.tvNama.setText(pesananPesawat.getNama_maskapai().toUpperCase());

                        Glide.with(context).load(pesananPesawat.getAirlineIcon())
                                .into(holder.ivIcon);

                        transaction_id = pesananPesawat.getId_transaksi();
                        Log.d(TAG, "onBindViewHolder form server: " + getLongTime(pesananPesawat.getExpiredDate()));
                        Log.d(TAG, "onBindViewHolder local: " + getLongTime(getCurrentTimeUsingDate()));
                        if (getLongTime(pesananPesawat.getExpiredDate()) < getLongTime(getCurrentTimeUsingDate())) {
                            holder.tvPembayaran.setText("Pesanan Kadaluarsa");
                            isExpired = 1;
                        } else {
                            holder.tvPembayaran.setText("Lanjutkan Pembayaran");
                            isExpired = 0;
                        }

                        break;
                    case "KERETA": {
                        PesananKereta pesananKereta = (PesananKereta) data.get(position - 1);
                        holder.tvInfo1.setText(pesananKereta.getOrigin());
                        holder.tvInfo2.setText(pesananKereta.getDestination());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm", SBFApplication.config.locale);
                        SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy - HH:mm", SBFApplication.config.locale);
                        try {
                            Date checkInDate = sdf.parse(pesananKereta.getTanggal_keberangkatan() + pesananKereta.getJam_keberangkatan());
                            holder.tvTanggalWaktuBerangkat.setText(pesananKereta.getHari_keberangkatan() + ", " + odf.format(checkInDate));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        holder.tvNama.setText(pesananKereta.getNama_kereta().toUpperCase());

                        holder.ivIcon.setVisibility(View.GONE);

                        transaction_id = pesananKereta.getId_transaksi();
                        if (getLongTime(pesananKereta.getExpiredDate()) < getLongTime(getCurrentTimeUsingDate())) {
                            holder.tvPembayaran.setText("Pesanan Kadaluarsa");
                            isExpired = 1;
                        } else {
                            isExpired = 0;
                        }
//                } else if (type.equals("HOTEL")) {
//                    PesananHotel pesananHotel = (PesananHotel) data.get(position - 1);
//                    holder.tvInfo1.setText(pesananHotel.getNama_hotel());
//
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", ((BaseActivity) this).config.locale);
//                    try {
//                        Date checkInDate = sdf.parse(pesananHotel.getCheck_in());
//                        // Calendar calendar = Calendar.getInstance();
//                        // calendar.setTime(checkInDate);
//                        //  calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(pesananHotel.getPax()));
//
////                            Date checkOutDate = calendar.getTime();
//                        Date checkOutDate = sdf.parse(pesananHotel.getCheck_out());
//                        holder.tvTanggalWaktuBerangkat.setText(odf.format(checkInDate) + " - " + odf.format(checkOutDate));
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    holder.tvInfo2.setVisibility(View.GONE);
//                    holder.tvNama.setVisibility(View.GONE);
//                    holder.ivIcon.setVisibility(View.GONE);
//                    holder.ivInfoSeparation.setVisibility(View.GONE);
//
//                    transaction_id = pesananHotel.getId_transaksi();
//                    if(getLongTime(pesananHotel.getExpiredDate())<getLongTime(getCurrentTimeUsingDate())){
//                        holder.tvPembayaran.setText("Pesanan Kadaluarsa");
//                        isExpired=1;
//                    }else{
//                        isExpired=0;
//                    }

                        break;
                    }
                    case "KAPAL": {
                        PesananKapal pesananKapal = (PesananKapal) data.get(position - 1);
                        holder.tvInfo1.setText(pesananKapal.getOrigin());
                        holder.tvInfo2.setText(pesananKapal.getDestination());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", SBFApplication.config.locale);
                        try {
                            Date checkInDate = sdf.parse(pesananKapal.getTanggal_keberangkatan());
                            holder.tvTanggalWaktuBerangkat.setText(pesananKapal.getHari_keberangkatan() + ", " + odf.format(checkInDate) + " - " + pesananKapal.getJam_keberangkatan());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        holder.tvNama.setText(pesananKapal.getNama_kapal().toUpperCase());

                        holder.ivIcon.setVisibility(View.GONE);

                        transaction_id = pesananKapal.getId_transaksi();
                        if (getLongTime(pesananKapal.getExpiredDate()) < getLongTime(getCurrentTimeUsingDate())) {
                            holder.tvPembayaran.setText("Pesanan Kadaluarsa");
                            isExpired = 1;
                        } else {
                            isExpired = 0;
                        }

                        break;
                    }
//                    case "WISATA": {
//                        PesananWisata pesananWisata = (PesananWisata) data.get(position - 1);
//                        holder.tvInfo1.setText(pesananWisata.getDestination());
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                        SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", config.locale);
//                        try {
//                            Date checkInDate = sdf.parse(pesananWisata.getTanggal_mulai());
//                            Date checkOutDate = sdf.parse(pesananWisata.getTangal_selesai());
//
//                            long difference = Math.abs(checkInDate.getTime() - checkOutDate.getTime());
//                            long differenceDates = difference / (24 * 60 * 60 * 1000);
//
//                            holder.tvTanggalWaktuBerangkat.setText(odf.format(checkInDate) + " - " + odf.format(checkOutDate));
//                            holder.tvNama.setText(differenceDates + " Hari");
//
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//
//                        holder.tvInfo2.setVisibility(View.GONE);
////                    holder.tvNama.setVisibility(View.GONE);
//                        holder.ivIcon.setVisibility(View.GONE);
//                        holder.ivInfoSeparation.setVisibility(View.GONE);
//
//                        transaction_id = pesananWisata.getId_transaksi();
//                        if (getLongTime(pesananWisata.getExpiredDate()) < getLongTime(getCurrentTimeUsingDate())) {
//                            holder.tvPembayaran.setText("Pesanan Kadaluarsa");
//                            isExpired = 1;
//                        } else {
//                            isExpired = 0;
//                        }
//                        break;
//                    }
                }

            } else {
                holder.dataView.setVisibility(View.GONE);
                holder.emptyView.setVisibility(View.GONE);
            }

            final String finalTransaction_id = transaction_id;
            if(isExpired==0) {
                holder.tvPembayaran.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("product", selectedProduct);
                            jsonObject.put("transaction_id", finalTransaction_id);
                            jsonObject.put("token", PreferenceClass.getToken());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("INFO", "REQUEST : " + jsonObject);

                        FrameLayout frameLayout =(FrameLayout) View.inflate(context,R.layout.loading_bar_full_dialog, null);
                        TextView text = frameLayout.findViewById(R.id.textContentProgressBar);
                        text.setText("Mohon Tunggu ...");
                        openProgressBarDialog(TravelPesananActivity.this, frameLayout);
                        RequestUtilsTravel.transportWithProgressResponse(TravelPesananActivity.this, TravelPath.TRANSACTIONINFO, jsonObject, TravelActionCode.TRANSACTIONINFO,TravelPesananActivity.this);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return data.size() + 1;
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            LinearLayout dataView;
            RelativeLayout emptyView;
            TextView tvInfo1, tvInfo2, tvTanggalWaktuBerangkat, tvNama, tvPembayaran;
            ImageView ivInfoSeparation, ivIcon;
//            View view;
            public ViewHolder(@NonNull View view) {
                super(view);

                dataView = view.findViewById(R.id.linDataView);
                emptyView = view.findViewById(R.id.rel_EmptyViewItem);
                tvInfo1 = view.findViewById(R.id.tvInfo1);
                tvInfo2 = view.findViewById(R.id.tvInfo2);
                tvTanggalWaktuBerangkat = view.findViewById(R.id.tvTanggalWaktuBerangkat);
                tvPembayaran = view.findViewById(R.id.tvPembayaran);
                tvNama = view.findViewById(R.id.tvNama);
                ivInfoSeparation = view.findViewById(R.id.ivInfoSeparation);
                ivIcon = view.findViewById(R.id.ivIcon);
//                this.view =  view;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            //    searchView.setFocusable(false);
            // relTampilkan.requestFocus();
//            BaseActivity.getInstance().closeKeyboard(this);
            startDate = data.getStringExtra("date");
            textViewTanggalAwal.setText(data.getStringExtra("dateShow"));
        } else if (requestCode == 2 && resultCode == AppCompatActivity.RESULT_OK) {
            //  searchView.setFocusable(false);
            // relTampilkan.requestFocus();
            //  BaseActivity.getInstance().closeKeyboard(this);
            endDate = data.getStringExtra("date");
            textViewTanggalAkhir.setText(data.getStringExtra("dateShow"));
        }else if (requestCode == TravelActionCode.IS_FROM_PAY && resultCode == RESULT_OK) {

            //   Intent intent = new Intent(FlightReviewBookActivity.this, FlightSearchActivity.class);
            if (data != null && data.getAction() != null) {
                Intent intent = new Intent();
                Log.d(TAG, "onActivityResult: " + data.getAction());
                switch (data.getAction()) {
                    case TravelActionCode.MENU_TRAVEL:

                        intent.setAction(TravelActionCode.MENU_TRAVEL);
                        break;
                    case TravelActionCode.MENU_PESAWAT:

                        intent.setAction(TravelActionCode.MENU_PESAWAT);
                        break;
                    case TravelActionCode.MENU_PELNI:

                        intent.setAction(TravelActionCode.MENU_PELNI);
                        break;
                    case TravelActionCode.MENU_KERETA:

                        intent.setAction(TravelActionCode.MENU_KERETA);
                        break;
                }
                setResult(RESULT_OK, intent);
            } else {
                setResult(RESULT_OK);
            }

            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
    @NonNull
    public String getCurrentLocalDateTimeStamp() {
        //  Log.d(TAG, "getCurrentLocalDateTimeStamp: "+String.valueOf(android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date())));
//        return LocalDateTime.now()
//                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return String.valueOf(android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date()));
    }
    public String  getCurrentTimeUsingDate() {
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat, Locale.getDefault());
        String formattedDate= dateFormat.format(date);
        System.out.println("Current time of the day using Date - 12 hour format: " + formattedDate);
        return formattedDate;
    }
    public long getLongTime(String dateString){
        // Log.d(TAG, "getLongTime: "+dateString);
        try {
            // String dateString = "30/09/2014";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
            Date date = sdf.parse(dateString);
            Log.d(TAG, "getLongTime: "+date.getTime());
            return date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
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
        finish();
    }


}
