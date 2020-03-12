package com.bm.main.fpl.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.bm.main.fpl.constants.PreferenceKey;
import com.bm.main.fpl.constants.RConfig;
import com.bm.main.fpl.models.AjakBisnisModel;
import com.bm.main.fpl.models.PanduanAjakBisnisModel;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONObject;

//import android.support.design.widget.TabLayout;

//public class LinkMarketingActivity extends BaseActivity implements ProgressResponseCallback,ListBinaanAdapter.OnClickProduk {
public class LinkMarketingActivity extends BaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final String TAG = LinkMarketingActivity.class.getSimpleName();

    TabLayout tabLayout;
    //    LinearLayout tabDownline, tabLink, tabPanduan;
    LinearLayout tabLink, tabPanduan;
    //    TextView textTabItemDownline, textTabItemLink, textTabItemPanduan;
    TextView textTabItemLink, textTabItemPanduan;
    private int selectedTab;
    private TextView textViewLinkWebReplika, textViewLinkPlaystore;
    TextView textViewHeaderLinkAffiliasi;
    //ScrollView layout_affiliasi_downline,layout_affiliasi_link,layout_affiliasi_promosi,layout_affiliasi_panduan;
    ScrollView layout_affiliasi_link, layout_affiliasi_panduan;
    //LinearLayout layout_affiliasi_downline;
    private Context mContext;

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    AppCompatButton button_selangkapnya;
    //   RecyclerView recyclerViewBinaan;
//    ExpandableListView exp_listViewBinaan;
    //ParentLevelAdapter parentLevelAdapter ;
    //  ListBinaanAdapter adapter;
    //  ArrayList<DownlineModel.Response_value> dataDownline = new ArrayList<>();
    //ArrayList<JumlahDownlineModel> dataJumlahDownline = new ArrayList<>();
    // ArrayList<ArrayList<DownlineModelResponse_valueLevel2>> dataDownlineLevel1 = new ArrayList<>();

    //LinearLayout linMainListBinaan0;
//ImageView imageViewDrop0,imageViewDrop0Empty;
//    TextView textViewJmlLevel1,textViewJmlLevel2,textViewJmlTotal,textViewKomisiDownline;


//    TextView textViewNamaBinaan0,textViewJumlahBinaan0,textViewIdBinaan0;

//    ExpandListDownlineAdapter ExpAdapter;
//    ArrayList<DownlineGroupModel> list = new ArrayList<DownlineGroupModel>();
//    ArrayList<DownlineChildModel> ch_list;

    @Nullable
    private YouTubePlayerFragment youTubeView;
    private View viewItemTabLink, viewItemTabPanduan;

    private TextView textViewHeaderPanduan, textViewBasicValue, textViewProValue, textViewEnterpriseValue;
    private String linkYouTube = "";
    private String linkSelengkapnya = "";
    TextView textViewKeteranganPanduan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affilasi);
        //  youTubeView.onSaveInstanceState(savedInstanceState);
        mContext = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Link Ajak Bisnis");
        init(0);

//        layout_affiliasi_downline = findViewById(R.id.layout_affiliasi_downline);
//        layout_affiliasi_downline.setVisibility(View.GONE);
        layout_affiliasi_link = findViewById(R.id.layout_affiliasi_link);
        //  layout_affiliasi_promosi = findViewById(R.id.layout_affiliasi_promosi);
        layout_affiliasi_panduan = findViewById(R.id.layout_affiliasi_panduan);


        tabLayout = findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(0);
        ViewGroup parent = findViewById(R.id.linCustomTabProduk);
//        tabDownline = (LinearLayout) View.inflate(this,R.layout.custom_tab_produk, parent);
        tabLink = (LinearLayout) View.inflate(this, R.layout.custom_tab_produk, parent);
        //    tabAlatPromosi = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_produk, null);
        tabPanduan = (LinearLayout) View.inflate(this, R.layout.custom_tab_produk, parent);

//        textTabItemDownline = tabDownline.findViewById(R.id.textTab);
        textTabItemLink = tabLink.findViewById(R.id.textTab);
        //  textTabItemAlatPromosi = tabAlatPromosi.findViewById(R.id.textTab);
        textTabItemPanduan = tabPanduan.findViewById(R.id.textTab);
        viewItemTabLink = tabLink.findViewById(R.id.viewItemTabProduk);
        viewItemTabPanduan = tabPanduan.findViewById(R.id.viewItemTabProduk);
//        textViewKomisiDownline = findViewById(R.id.textViewKomisiDownline);
//         textViewJmlLevel1 = findViewById(R.id.textViewJmlLevel1);
//         textViewJmlLevel2 = findViewById(R.id.textViewJmlLevel2);
//         textViewJmlTotal = findViewById(R.id.textViewJmlTotal);
//
//
//        textViewNamaBinaan0=findViewById(R.id.textViewNamaBinaan0);
//
//        textViewJumlahBinaan0=findViewById(R.id.textViewJumlahBinaan0);
//        textViewIdBinaan0=findViewById(R.id.textViewIdBinaan0);
//
//        imageViewDrop0=findViewById(R.id.imageViewDrop0);
//        imageViewDrop0Empty=findViewById(R.id.imageViewDrop0Empty);
//

        // recyclerViewBinaan = findViewById(R.id.recyclerViewBinaan);


//        exp_listViewBinaan = findViewById(R.id.exp_listViewBinaan);
//        ExpAdapter = new ExpandListDownlineAdapter(
//                LinkMarketingActivity.this, list);
//        exp_listViewBinaan.setAdapter(ExpAdapter);
//        imageViewDrop0=findViewById(R.id.imageViewDrop0);
//        exp_listViewBinaan.setVisibility(View.GONE);
//        linMainListBinaan0=findViewById(R.id.linMainListBinaan0);
//        linMainListBinaan0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if(recyclerViewBinaan.getVisibility()==View.GONE){
////                    recyclerViewBinaan.setVisibility(View.VISIBLE);
////                    imageViewDrop0.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_down_arrow));
//                    if(exp_listViewBinaan.getVisibility()==View.GONE){
//                        exp_listViewBinaan.setVisibility(View.VISIBLE);
//                    imageViewDrop0.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_down_arrow));
//                }else{
//                        exp_listViewBinaan.setVisibility(View.GONE);
//                    imageViewDrop0.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_right_arrow));
////                    recyclerViewBinaan.setVisibility(View.GONE);
////                    imageViewDrop0.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_right_arrow));
//                }
//            }
//        });
//       // exp_listViewBinaan.setG
//
//
//        exp_listViewBinaan.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                // Toast.makeText(getApplicationContext(),
//                // "Group Clicked " + listDataHeader.get(groupPosition),
//                // Toast.LENGTH_SHORT).show();
//                if(list.get(groupPosition).getJumlah_downline().equals("0")) {
//                    return true;
//                }else{
//                    return false;
//                }
//            }
//        });
//
//        exp_listViewBinaan.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//             //   Toast.makeText(getApplicationContext(),
////                        list.get(groupPosition).getJumlah_downline() + " List Expanded.",
////                        Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//
//        exp_listViewBinaan.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
////                Toast.makeText(getApplicationContext(),
////                        list.get(groupPosition) + " List Collapsed.",
////                        Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        exp_listViewBinaan.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
////                Toast.makeText(getApplicationContext(),
////                        list.get(groupPosition)+ " -> "+ ch_list.get(childPosition), Toast.LENGTH_SHORT
////                ).show();
//                return false;
//            }
//        });

//        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
//
//        recyclerViewBinaan.setHasFixedSize(false);
//        recyclerViewBinaan.setLayoutManager(linearLayoutManager);
//        recyclerViewBinaan.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        adapter = new ListBinaanAdapter(dataDownline, this, this);
//        recyclerViewBinaan.setAdapter(adapter);


        // if (recyclerViewBinaan != null) {
//           parentLevelAdapter = new ParentLevelAdapter(this, dataDownline);
//            recyclerViewBinaan.setAdapter(parentLevelAdapter);
        // display only one expand item
//            recyclerViewBinaan.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//                int previousGroup = -1;
//                @Override
//                public void onGroupExpand(int groupPosition) {
//                    if (groupPosition != previousGroup)
//                        recyclerViewBinaan.collapseGroup(previousGroup);
//                    previousGroup = groupPosition;
//                }
//            });
        //  }

        //  youTubeView =(YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_view_panduan_link);
//        youTubeView=YouTubePlayerSupportFragment.newInstance();
//
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.youtube_view_panduan_link, youTubeView);
//        transaction.commit();
        // youTubeView.setMinimumHeight(lp.height);
        youTubeView = (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.youtube_view_panduan_link);
//        if (youTubeView != null) {
        youTubeView.initialize(PreferenceClass.getString(RConfig.API_YOU_TUBE, ""), this);
//        }
        button_selangkapnya = findViewById(R.id.button_selangkapnya);
        button_selangkapnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String THE_URL="https://www.fpkita.com:9191/index.php/modulorder/link_affiliasi";
//                Intent browserIntent = new Intent();
//                browserIntent.setAction(Intent.ACTION_VIEW);
//                browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
//               // browserIntent.setData(Uri.parse(THE_URL));
//                browserIntent.setData(Uri.parse(PreferenceClass.getUser().getAffiliasi()));
//                startActivity(browserIntent);
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(LinkMarketingActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {

                    CustomTabsIntent customTabsIntent = buildCustomTabsIntent();
                    //    customTabsIntent.launchUrl(LinkMarketingActivity.this, Uri.parse(PreferenceClass.getUser().getAffiliasi()));
                    customTabsIntent.launchUrl(LinkMarketingActivity.this, Uri.parse(linkSelengkapnya));
                }
            }
        });

        textViewHeaderLinkAffiliasi = findViewById(R.id.textViewHeaderLinkAffiliasi);
//        textViewHeaderLinkAffiliasi.setText(FormatString.htmlString(getString(R.string.link_affiliasi)));

        textViewLinkWebReplika = findViewById(R.id.textViewLinkWebReplika);
        textViewLinkWebReplika.setText(String.format(getString(R.string.link_web_replika), PreferenceClass.getLoggedUser().getId()));

        // DownlineModel downlineModel;
        AppCompatButton button_copy_link_web = findViewById(R.id.button_copy_link_web);
        button_copy_link_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(LinkMarketingActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", textViewLinkWebReplika.getText().toString().replaceAll("-", ""));
                    clipboard.setPrimaryClip(clip);
                    showToast("Link Ajak Bisnis Anda Telah Disalin");
                }
            }
        });

        textViewLinkPlaystore = findViewById(R.id.textViewLinkPlaystore);
        //  textViewLinkPlaystore.setText(String.format(getString(R.string.link_refferal), "id=com.bm.main.fpl&referrer=utm_medium=android&utm_content=registrasi%2520dengan%2520upline%2520"+PreferenceClass.getLoggedUser().getId()+"&utm_term=upline&utm_source="+PreferenceClass.getLoggedUser().getId()+"&utm_campaign=fastpay_co_id"));
        textViewLinkPlaystore.setText(String.format(getString(R.string.link_refferal), "id=com.bm.main.fpl&referrer=utm_medium=android&utm_content=registrasi%2520dengan%2520ajakbisnis%2520" + PreferenceClass.getLoggedUser().getId() + "&utm_term=ajakbisnis&utm_source=" + PreferenceClass.getLoggedUser().getId() + "&utm_campaign=fastpay_co_id"));

        AppCompatButton button_copy_link_playstore = findViewById(R.id.button_copy_link_playstore);
        button_copy_link_playstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(LinkMarketingActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", textViewLinkPlaystore.getText().toString().replaceAll("-", ""));
                    clipboard.setPrimaryClip(clip);
                    showToast("Link Ajak Bisnis Playstore Telah Disalin");
                }
            }
        });

        textViewHeaderPanduan = findViewById(R.id.textViewHeaderPanduan);
        textViewKeteranganPanduan = findViewById(R.id.textViewKeteranganPanduan);
        textViewBasicValue = findViewById(R.id.textViewBasicValue);
        textViewProValue = findViewById(R.id.textViewProValue);
        textViewEnterpriseValue = findViewById(R.id.textViewEnterpriseValue);

//        Formatter fmt=new Formatter();
//        fmt.format(getString(R.string.link_refferal),PreferenceClass.getLoggedUser().getId());
//        textViewLinkPlaystore.setText(fmt.toString());
        bindWidgetsWithAnEvent();
        setupTabLayout();

        requestAjakBisnis();
        requestPanduan();

    }

    private void requestPanduan() {
        JSONObject panduanBisnis = PreferenceClass.getJSONObject(PreferenceKey.panduanBisnis);
        PanduanAjakBisnisModel panduanAjakBisnisModel = gson.fromJson(panduanBisnis.toString(), PanduanAjakBisnisModel.class);
        textViewHeaderPanduan.setText(FormatString.htmlString(panduanAjakBisnisModel.getHeaderPanduan()));
        textViewKeteranganPanduan.setText(new SpannableString(FormatString.htmlString(panduanAjakBisnisModel.getKeteranganPanduan())));
        textViewBasicValue.setText(panduanAjakBisnisModel.getBasic_value());
        textViewProValue.setText(panduanAjakBisnisModel.getPro_value());
        textViewEnterpriseValue.setText(panduanAjakBisnisModel.getEnterprise_value());
        linkYouTube = panduanAjakBisnisModel.getYoutubePanduan();
        linkSelengkapnya = panduanAjakBisnisModel.getSelangkapnyaPanduan();
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject = new JSONObject(stringJson.requestGetContenPanduanAjakBisnis());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.REQUEST_PANDUAN_AJAK_BISNIS, this);
    }

    private void requestAjakBisnis() {
        JSONObject ajakBisnis = PreferenceClass.getJSONObject(PreferenceKey.ajakBisnis);
        AjakBisnisModel ajakBisnisModel = gson.fromJson(ajakBisnis.toString(), AjakBisnisModel.class);
        textViewHeaderLinkAffiliasi.setText(new SpannableString(FormatString.htmlString(ajakBisnisModel.getKeteranganAjakBisnis())));


//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject = new JSONObject(stringJson.requestGetContenAjakBisnis());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.REQUEST_AJAK_BISNIS, this);
    }

    //    @Override
//    public void onSuccess(int actionCode, JSONObject response) {
//
//        if (actionCode == ActionCode.REQUEST_AJAK_BISNIS) {
//            AjakBisnisModel ajakBisnisModel=gson.fromJson(response.toString(), AjakBisnisModel.class);
//            if(ajakBisnisModel.getResponse_code().equals(ResponseCode.SUCCESS)){
//                textViewHeaderLinkAffiliasi.setText(new SpannableString(FormatString.htmlString(ajakBisnisModel.getKeteranganAjakBisnis())));
//            }
//        }else if (actionCode == ActionCode.REQUEST_PANDUAN_AJAK_BISNIS) {
//            PanduanAjakBisnisModel panduanAjakBisnisModel=gson.fromJson(response.toString(), PanduanAjakBisnisModel.class);
//            if(panduanAjakBisnisModel.getResponse_code().equals(ResponseCode.SUCCESS)){
//                textViewHeaderPanduan.setText(FormatString.htmlString(panduanAjakBisnisModel.getHeaderPanduan()));
//                textViewKeteranganPanduan.setText(new SpannableString(FormatString.htmlString(panduanAjakBisnisModel.getKeteranganPanduan())));
//                textViewBasicValue.setText(panduanAjakBisnisModel.getBasic_value());
//                textViewProValue.setText(panduanAjakBisnisModel.getPro_value());
//                textViewEnterpriseValue.setText(panduanAjakBisnisModel.getEnterprise_value());
//                linkYouTube=panduanAjakBisnisModel.getYoutubePanduan();
//                linkSelengkapnya=panduanAjakBisnisModel.getSelangkapnyaPanduan();
//            }
//        }
//
//    }
//
//
//    @Override
//    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
//
//    }
    private void setupTabLayout() {
        Log.d(TAG, "setupTabLayout: ");
        setupTab();
        // tabDownline.setSelected(true);
        tabLink.setSelected(true);

    }

    private void setupTab() {

//        textTabItemDownline.setText("Mitra Binaan");
//
//        textTabItemDownline.setTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
//        tabLayout.addTab(tabLayout.newTab().setCustomView(tabDownline));

        textTabItemLink.setText("Link Ajak Bisnis");
        viewItemTabLink.setBackground(ContextCompat.getDrawable(LinkMarketingActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
        tabLink.setBackground(ContextCompat.getDrawable(LinkMarketingActivity.this, R.drawable.selector_tab_btn_first));
        textTabItemLink.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.colorPrimary_ppob));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabLink));
//
//        textTabItemAlatPromosi.setText("Alat Promosi");
//
//        textTabItemAlatPromosi.setTextColor(ContextCompat.getColor(this, R.color.md_blue_100));
//        tabLayout.addTab(tabLayout.newTab().setCustomView(tabAlatPromosi));
//


        textTabItemPanduan.setText("Panduan");
        viewItemTabPanduan.setBackground(ContextCompat.getDrawable(LinkMarketingActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
        tabPanduan.setBackground(ContextCompat.getDrawable(LinkMarketingActivity.this, R.drawable.unselector_tab_btn_last));
        textTabItemPanduan.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.md_white_1000));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabPanduan));

        //   requestListDownline();
//        requestJumlahDownline();
//        requestKomisiDownline();
    }

    private void bindWidgetsWithAnEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView textTab;
                ImageView imageTab;
                switch (position) {
//                    case 0:
//                        textTab = tabDownline.findViewById(R.id.textTab);
//                        textTab.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.md_white_1000));
//
//                        textTab.setTypeface(tfRegular, Typeface.NORMAL);
//
//                        selectedTab = 0;
//                        // type = "REGULAR";
//
//                        break;
                    case 0:

                        viewItemTabLink.setBackground(ContextCompat.getDrawable(LinkMarketingActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        tabLink.setBackground(ContextCompat.getDrawable(LinkMarketingActivity.this, R.drawable.selector_tab_btn_first));
                        textTabItemLink.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.colorPrimary_ppob));
//                        textTab = tabLink.findViewById(R.id.textTab);
//
//                        textTab.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.md_white_1000));
//
//                        textTab.setTypeface(tfRegular, Typeface.NORMAL);

                        selectedTab = 0;
                        // type = "INTERNET";
                        break;
//                    case 2:
//
//                        textTab = tabAlatPromosi.findViewById(R.id.textTab);
//                        textTab.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.md_white_1000));
//
//                        textTab.setTypeface(tfRegular, Typeface.NORMAL);
//
//                        selectedTab = 2;
//                        // type = "INTERNET";
//                        break;
                    case 1:

                        viewItemTabPanduan.setBackground(ContextCompat.getDrawable(LinkMarketingActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        tabPanduan.setBackground(ContextCompat.getDrawable(LinkMarketingActivity.this, R.drawable.selector_tab_btn_last));
                        textTabItemPanduan.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.colorPrimary_ppob));
//                        textTab = tabPanduan.findViewById(R.id.textTab);
//                        textTab.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.md_white_1000));
//
//                        textTab.setTypeface(tfRegular, Typeface.NORMAL);

                        selectedTab = 1;
                        // type = "INTERNET";
                        break;


                }
                requestLayout();// call activity here for daftar id pelanggan

            }

            @Override
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {

                Log.d(TAG, "onTabUnselected: " + tab.getPosition());
                int position = tab.getPosition();
                TextView textTab;
                ImageView imageTab;
                switch (position) {
//                    case 0:
//
//                        textTab = tabDownline.findViewById(R.id.textTab);
//                        textTab.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.md_blue_100));
//                        textTab.setTypeface(tfLight, Typeface.NORMAL);
////                        if (nominal24 != 0) {
////                            nominal24 = 0;
////                            radioButtonNominalBank24.setChecked(false);
////                        }
//
//                        break;

                    case 0:
                        viewItemTabLink.setBackground(ContextCompat.getDrawable(LinkMarketingActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        tabLink.setBackground(ContextCompat.getDrawable(LinkMarketingActivity.this, R.drawable.unselector_tab_btn_first));
                        textTabItemLink.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.md_white_1000));
//
//                        textTab = tabLink.findViewById(R.id.textTab);
//                        textTab.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.md_blue_100));
//                        textTab.setTypeface(tfLight, Typeface.NORMAL);


                        break;
//                    case 2:
//                        textTab = tabAlatPromosi.findViewById(R.id.textTab);
//                        textTab.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.md_blue_100));
//                        textTab.setTypeface(tfLight, Typeface.NORMAL);
//
//
//                        break;
                    case 1:

//                        textTab = tabPanduan.findViewById(R.id.textTab);
//                        textTab.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.md_blue_100));
//                        textTab.setTypeface(tfLight, Typeface.NORMAL);
                        viewItemTabPanduan.setBackground(ContextCompat.getDrawable(LinkMarketingActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        tabPanduan.setBackground(ContextCompat.getDrawable(LinkMarketingActivity.this, R.drawable.unselector_tab_btn_last));
                        textTabItemPanduan.setTextColor(ContextCompat.getColor(LinkMarketingActivity.this, R.color.md_white_1000));

                        break;


                }


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void requestLayout() {
        Log.d(TAG, "requestLayout: " + selectedTab);


        switch (selectedTab) {
//            case 0:
//
//                layout_affiliasi_downline.setVisibility(View.VISIBLE);
//                layout_affiliasi_link.setVisibility(View.GONE);
//               // layout_affiliasi_promosi.setVisibility(View.GONE);
//                layout_affiliasi_panduan.setVisibility(View.GONE);
//
//
//                break;
            case 0:
//                layout_affiliasi_downline.setVisibility(View.GONE);
                layout_affiliasi_link.setVisibility(View.VISIBLE);
                //  layout_affiliasi_promosi.setVisibility(View.GONE);
                layout_affiliasi_panduan.setVisibility(View.GONE);


                break;
//            case 2:
//
//                layout_affiliasi_downline.setVisibility(View.GONE);
//                layout_affiliasi_link.setVisibility(View.GONE);
//              //  layout_affiliasi_promosi.setVisibility(View.VISIBLE);
//                layout_affiliasi_panduan.setVisibility(View.GONE);
//
//
//                break;
            case 1:

//                layout_affiliasi_downline.setVisibility(View.GONE);
                layout_affiliasi_link.setVisibility(View.GONE);
                //   layout_affiliasi_promosi.setVisibility(View.GONE);
                layout_affiliasi_panduan.setVisibility(View.VISIBLE);

                break;


        }

        Log.d(TAG, "requestLayout: " + selectedTab);


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
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, @NonNull YouTubePlayer player,
                                        boolean wasRestored) {
        Log.d(TAG, "onInitializationSuccess: " + linkYouTube);
        if (!wasRestored) {
            player.cueVideo(linkYouTube);
        }
        if (player.isPlaying()) {
            player.setFullscreen(true);
        }
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        @NonNull YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "YouTubePlayer Error,Pastikan Applikasi Youtube Anda telah terUpdate",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            getYouTubePlayerProvider().initialize(PreferenceClass.getString(RConfig.API_YOU_TUBE, ""), this);
        }
    }

    @NonNull
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view_panduan_link);
    }
}
