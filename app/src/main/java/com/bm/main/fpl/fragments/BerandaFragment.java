package com.bm.main.fpl.fragments;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.activities.HomeActivity;
import com.bm.main.fpl.activities.LoginActivity;
import com.bm.main.fpl.adapters.GrosirAdapter;
import com.bm.main.fpl.adapters.WebBannerAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.FCMConstants;
import com.bm.main.fpl.constants.RConfig;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ClickListener;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.listener.RecyclerTouchListener;
import com.bm.main.fpl.models.AdditionalMenuModel;
import com.bm.main.fpl.models.GrosirModel;
import com.bm.main.fpl.models.LoginPromo;
import com.bm.main.fpl.models.ProdukFMCGModel;
import com.bm.main.fpl.models.ProdukTomoModel;
import com.bm.main.fpl.staggeredgridApp.ItemObjectsMenuBeranda;
import com.bm.main.fpl.staggeredgridApp.SolventRecyclerViewAdapter;
import com.bm.main.fpl.staggeredgridApp.SolventRecyclerViewProdukAdapter;
import com.bm.main.fpl.templates.ColorAnimator;
import com.bm.main.fpl.templates.banner.BannerLayout;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.templates.slider.library.SliderTypes.BaseSliderView;
import com.bm.main.fpl.utils.DetectConnection;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.fpl.utils.StringJson;
import com.bm.main.fpl.utils.shimmer;
import com.bm.main.scm.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class BerandaFragment extends Fragment implements ProgressResponseCallback, SolventRecyclerViewProdukAdapter.OnClickProduk, GrosirAdapter.OnClickProdukFMCG,
        BaseSliderView.OnSliderClickListener, BannerLayout.OnBannerScrollListener {

    private static final String TAG = BerandaFragment.class.getSimpleName();
    private RecyclerView recycler_menu;
    private RecyclerView recycler_view_produk;
    private RecyclerView recycler_view_grosir;
    private SolventRecyclerViewProdukAdapter mAdapterProduk;
    private GrosirAdapter mAdapterProdukFMCG;
    @NonNull
    private ArrayList<ProdukTomoModel.Response_value> itemListProduk = new ArrayList<>();
    @NonNull
    private List<GrosirModel> itemListProdukFmcg = new ArrayList<>();
    @NonNull
    private List<String> image = new ArrayList<>();
    @NonNull
    private List<String> imageTarget = new ArrayList<>();
    private LoginPromo loginPromo;
    public static Animation slideUp;

    @NonNull
    private int[] menuIcons = {
            R.drawable.ic_menu_pln,
            R.drawable.ic_menu_pulsa,
            R.drawable.ic_menu_pdam,
            R.drawable.ic_menu_bpjs,

            R.drawable.ic_menu_topup_emoney,
            R.drawable.ic_menu_indihome,
            R.drawable.ic_menu_pesawat,
            R.drawable.ic_menu_kereta,

            R.drawable.ic_menu_telkom,
            R.drawable.ic_menu_cicilan,
            R.drawable.ic_menu_pasca_bayar,
            R.drawable.ic_menu_tv_kabel,

            R.drawable.ic_menu_pelni,
            R.drawable.ic_menu_perbankan,
            R.drawable.ic_menu_game_online,
            R.drawable.ic_menu_expedisi,

            R.drawable.ic_menu_asuransi,
            R.drawable.ic_menu_kartu_kredit,
            R.drawable.ic_menu_pinjamdana,
            R.drawable.ic_menu_jual_barang,

            R.drawable.ic_menu_gas,
            R.drawable.ic_menu_pajak_bumi,
            R.drawable.ic_menu_samsat,
            R.drawable.ic_menu_agen,

            R.drawable.ic_menu_bayar_barang,
            R.drawable.ic_menu_bayar_online,
            R.drawable.ic_admin_cek_nfc,
    };
    @NonNull
    private String[] menuTitle = {
            "PLN",
            "Pulsa Seluler",
            "PDAM",
            "BPJS",

            "Topup Emoney",
            "IndiHome",
            "Reservasi Pesawat",
            "Reservasi Kereta",

            "Telkom",
            "Cicilan Kredit",
            "Pascabayar",
            "TV Berlangganan",

            "Reservasi Pelni",
            "Perbankan",
            "Game Online",
            "Ekspedisi",

            "Asuransi",
            "Kartu kredit",
            "Pinjaman Dana",
            "Jual Barang",

            "GAS",
            "Pajak",
            "Samsat",
            "Keagenan",

            "Bayar Barang",
            "Bayar Online",
            "Update Saldo Emoney",
    };
    public View rootView;
    private Context context;

    private TextView textViewHeaderProdukTomo;
    private AppBarLayout appBarLayout;
    private RadioButton linItemTabPpob;
    private RadioButton linItemTabGrosir;
    private NestedScrollView nestedScrollView;

    public LinearLayout lin_header;

    public ImageView img_notification, img_menu;
    public FrameLayout frame_bagde_count_notif;
    public TextView textViewCountNotif;
    public ImageView imageViewRatingOutlet;
    public TextView textViewTypeOutletToolbar;
    public LinearLayout linGradeOutlet;

    public ShimmerFrameLayout frame_ribbon;
    private GridLayoutManager mLayoutManager1;
    private LinearLayout loadItemsLayout_recyclerView;
    private Toolbar toolbar;
    LinearLayout.LayoutParams lp;
    private int position;
    RelativeLayout linToolbar;

    private ShimmerFrameLayout shimmer_view_container;
    private BannerLayout recyclerBanner;
    private CollapsingToolbarLayout collapsing_toolbar;
    private WebBannerAdapter webBannerAdapter;

    private TextView btn_pos, btn_grocery;

    public BerandaFragment() {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_beranda_new, container, false);
        context = getActivity();

        ((BaseActivity) getActivity()).logEventFireBase("Home", "Beranda", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        Timber.d("promo 2");

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        toolbar = rootView.findViewById(R.id.toolbar);
        linToolbar = rootView.findViewById(R.id.linToolbar);
        frame_bagde_count_notif = rootView.findViewById(R.id.frame_bagde_count_notif);
        textViewCountNotif = rootView.findViewById(R.id.textViewCountNotif);
        imageViewRatingOutlet = rootView.findViewById(R.id.imageViewRatingOutlet);
        textViewTypeOutletToolbar = rootView.findViewById(R.id.textViewTypeOutletToolbar);
        linGradeOutlet = rootView.findViewById(R.id.linGradeOutlet);

        linGradeOutlet.setOnClickListener(v -> ((HomeActivity) context).toggleGradeOutlet(context));

        img_menu = rootView.findViewById(R.id.img_menu);
        img_menu.setOnClickListener(v -> ((BaseActivity) context).openTopDialog(true));
        img_notification = rootView.findViewById(R.id.img_notification);

        rootView.findViewById(R.id.relNotif).setOnClickListener(v -> ((HomeActivity) context).toggleNotification());

        appBarLayout = rootView.findViewById(R.id.appBarLayout);
        lin_header = rootView.findViewById(R.id.lin_header);

        textViewHeaderProdukTomo = rootView.findViewById(R.id.textViewHeaderProdukTomo);
        recycler_menu = rootView.findViewById(R.id.recycler_view_app);
        recycler_view_produk = rootView.findViewById(R.id.recycler_view_produk);
        recycler_view_grosir = rootView.findViewById(R.id.recycler_view_grosir);
        shimmer_view_container = rootView.findViewById(R.id.shimmer_view_container);
        nestedScrollView = rootView.findViewById(R.id.nestedscrollView);

        frame_ribbon = rootView.findViewById(R.id.frame_ribbon);
        btn_grocery = rootView.findViewById(R.id.btn_grocery);
        btn_pos = rootView.findViewById(R.id.btn_pos);
        linItemTabGrosir = rootView.findViewById(R.id.linItemTabGrosir);
        linItemTabGrosir.setButtonDrawable(new StateListDrawable());
        linItemTabPpob = rootView.findViewById(R.id.linItemTabPpob);
        linItemTabPpob.setButtonDrawable(new StateListDrawable());
        collapsing_toolbar = rootView.findViewById(R.id.collapsing_toolbar);
        recyclerBanner = rootView.findViewById(R.id.recycler);

        int heightDp = (rootView.getResources().getDisplayMetrics().heightPixels * 2) / 7;
        lp = (LinearLayout.LayoutParams) recyclerBanner.getLayoutParams();

        lp.height = heightDp;

        recyclerBanner.setMinimumHeight(lp.height);

        int paddingDp = 30;
        float density = context.getResources().getDisplayMetrics().density;
        int paddingPixel = (int) (paddingDp * density);
        lin_header.setPadding(0, paddingPixel, 0, 0);

        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            //  Vertical offset == 0 indicates appBar is fully expanded.
            appBarLayout.postDelayed(new Runnable() {
                @Override
                public void run() {

                    final float alpha = (float) Math.abs(verticalOffset) / (lp.height - toolbar.getHeight());


                    if (verticalOffset == 0) {
                        toolbar.setBackgroundColor(0);
                        recyclerBanner.setPlaying(true);
                    } else {
                        toolbar.setBackgroundColor(getColorWithAlpha(ContextCompat.getColor(context, R.color.colorPrimary_ppob), alpha));
                        if (alpha >= 1.0) {
                            recyclerBanner.setPlaying(false);
                        }
                    }
                }
            }, 100);
        });

        webBannerAdapter = new WebBannerAdapter(BerandaFragment.this, context, image);
        requestSliderHome();
        setMenu();
        setMenuProduk();
        startBlinkingAnimation();

        String isCoverGrosir = PreferenceClass.getUser().getIsCoverGrosir();
        if (isCoverGrosir.equals("0")) {
            linItemTabGrosir.setText("BELANJA BARANG");
            btn_grocery.setText("Belanja");
        }

        linItemTabGrosir.setOnClickListener(v -> {
            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                ((BaseActivity) context).new_popup_alert(context, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                        "Daftar & Aktifasi sekarang juga ID Anda");
            } else {
                ((HomeActivity) context).runOnUiThread(() -> {
                    if (!DetectConnection.isOnline(context)) {
                        ((BaseActivity) context).new_popup_alert_failure(context, "Cek Koneksi Internet Anda");
                    } else {
                        ((BaseActivity) context).logEventFireBase("Home", PreferenceClass.getUser().getId_outlet(), "New FMCG", EventParam.EVENT_SUCCESS, context.getClass().getSimpleName());
                        CustomTabsIntent customTabsIntent = ((BaseActivity) context).buildCustomTabsIntent();
                        customTabsIntent.launchUrl(context, Uri.parse(PreferenceClass.getUser().getGrosir() + PreferenceClass.getAuth()));
                    }

                });
            }
        });
        btn_grocery.setOnClickListener(v -> {
            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                ((BaseActivity) getActivity()).new_popup_alert(context, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                        "Daftar & Aktifasi sekarang juga ID Anda");
            } else {
                getActivity().runOnUiThread(() -> {
                    if (!DetectConnection.isOnline(context)) {
                        ((BaseActivity) context).new_popup_alert_failure(context, "Cek Koneksi Internet Anda");
                    } else {
                        String apps = "com.android.chrome";
                        boolean installed = Device.checkAppInstall(context, apps);
                        if (installed) {
                            ((BaseActivity) context).logEventFireBase("Home", PreferenceClass.getUser().getId_outlet(), "New FMCG", EventParam.EVENT_SUCCESS, context.getClass().getSimpleName());
                            CustomTabsIntent customTabsIntent = ((BaseActivity) context).buildCustomTabsIntent();

                            customTabsIntent.launchUrl(getActivity(), Uri.parse(PreferenceClass.getUser().getGrosir() + PreferenceClass.getAuth()));
                        } else {
                            Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                            context.startActivity(webIntent);
                        }
                    }

                });
            }
        });
        btn_pos.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        textViewTypeOutletToolbar.setText(PreferenceClass.getUser().getAlias().toUpperCase());
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (frame_ribbon != null)
            shimmer.selectPreset(0, frame_ribbon);
    }

    private List<String> menuComingSoon = Arrays.asList("bayar online", "update saldo emoney");

    private void setMenu() {
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recycler_menu.setLayoutManager(mLayoutManager);
        List<ItemObjectsMenuBeranda> menuList = new ArrayList<>();
        for (int i = 0; i < menuIcons.length; i++) {
            ItemObjectsMenuBeranda itemObjects = new ItemObjectsMenuBeranda(menuTitle[i], menuIcons[i], 0);
            itemObjects.isComingSoon = menuComingSoon.contains(itemObjects.getName().toLowerCase());
            menuList.add(itemObjects);
        }

        AdditionalMenuModel additionalMenuModel = ((BaseActivity) context).gson.fromJson(PreferenceClass.getString(RConfig.ADD_MENU, ""), AdditionalMenuModel.class);
        if (additionalMenuModel != null && !additionalMenuModel.getResponse_value().isEmpty()) {
            ArrayList<AdditionalMenuModel.Response_value> addAdditonalItems = additionalMenuModel.getResponse_value();

            for (AdditionalMenuModel.Response_value item : addAdditonalItems) {
                ItemObjectsMenuBeranda bItem = new ItemObjectsMenuBeranda(item.getProduct_name(), item.getProduct_image_url(), 1);
                bItem.isComingSoon = item.getIs_coming_soon() != null && item.getIs_coming_soon().equals("1");
                bItem.isHot = item.getIs_hot() != null && item.getIs_hot().equals("1");
                bItem.isNew = item.getIs_new() != null && item.getIs_new().equals("1");
                bItem.isPromo = item.getIs_promo() != null && item.getIs_promo().equals("1");
                bItem.isUseIdOutlet = item.getIs_use_id_outlet() != null && item.getIs_promo().equals("1");
                bItem.urlProduk = item.getProduct_url();
                menuList.add(bItem);
            }
        }

        recycler_menu.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recycler_menu, new ClickListener() {
            @Override
            public void onClick(final View view, final int position) {
                ((HomeActivity) context).getMenuBeranda(view, position, menuList.get(position));
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        SolventRecyclerViewAdapter mAdapter = new SolventRecyclerViewAdapter(getActivity(), menuList);
        mAdapter.notifyDataSetChanged();

        recycler_menu.setAdapter(mAdapter);
    }

    private void setMenuProduk() {
        RecyclerView.LayoutManager mLayoutManager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recycler_view_produk.setLayoutManager(mLayoutManager1);

        mAdapterProduk = new SolventRecyclerViewProdukAdapter(context, itemListProduk, this);

        StringJson stringJson = new StringJson(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestTomoProdukList());
        } catch (JSONException e) {
            Timber.e(e);
        }

        HomeActivity activity = (HomeActivity) getActivity();
        if (activity != null) {
            RequestUtils.transportWithProgressResponse(activity, jsonObject, ActionCode.LIST_PRODUK_TOMO, this);
        }
    }

    @Override
    public void onResume() {
        Timber.d("onResume: ");
        super.onResume();
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                ((HomeActivity) getActivity()).openShowCaseBeranda(context);
                getTypeOutletToolbarBeranda();
            });
        }

        if (PreferenceClass.getInt("notifSize", 0) > 0) {
            frame_bagde_count_notif.setVisibility(View.VISIBLE);
        } else {
            frame_bagde_count_notif.setVisibility(View.GONE);
        }
    }

    public void getTypeOutletToolbarBeranda() {
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

    @Override
    public void onStop() {
        super.onStop();
        if (recyclerBanner.isPlaying()) {
            recyclerBanner.setPlaying(false);
        }
    }

    private void setPromo() {
        image.clear();
        imageTarget.clear();

        for (LoginPromo.Response_value val : loginPromo.getResponse_value()) {
            image.add(val.getUrl_image());
            imageTarget.add(val.getTarget_link());
        }

        webBannerAdapter.setOnBannerItemClickListener(position -> {
            String link = imageTarget.get(position);

            try {
                String apps = "com.android.chrome";
                boolean installed = Device.checkAppInstall(context, apps);
                if (installed) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(link));
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                    context.startActivity(webIntent);
                }
            } catch (Exception e) {
                Timber.e(e);
            }
        });

        recyclerBanner.setAdapter(webBannerAdapter, this);
        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            Glide.with(context).asBitmap().load(image.get(0))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                                ObjectAnimator animator = ColorAnimator.ofBackgroundColor(appBarLayout, R.color.colorPrimary_ppob);
                                animator.start();
                            }
                        }

                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                                Palette.from(resource).generate(palette -> {
                                    if (palette != null) {
                                        int vibrantColor = palette.getLightVibrantColor(R.color.colorPrimary_ppob);
                                        ObjectAnimator animator = ColorAnimator.ofBackgroundColor(appBarLayout, vibrantColor);
                                        try {
                                            animator.start();
                                        } catch (Exception ignored) {
                                        }
                                    }
                                });

                                Palette.from(resource).generate(palette -> {
                                    if (palette != null) {
                                        int vibrantColor = palette.getVibrantColor(R.color.colorPrimary_ppob);
                                        collapsing_toolbar.setContentScrimColor(vibrantColor);
                                        collapsing_toolbar.setStatusBarScrimColor(R.color.black_trans80);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                                ObjectAnimator animator = ColorAnimator.ofBackgroundColor(appBarLayout, R.color.colorPrimary_ppob);
                                animator.start();
                            }
                        }
                    });
            recyclerBanner.setAutoPlaying(true);
        }
    }


    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Timber.d("onSuccess: %s", response.toString());
        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            ProdukFMCGModel produkFMCGModel;
            if (actionCode == ActionCode.SLIDE_HOME) {
                loginPromo = ((BaseActivity) getActivity()).gson.fromJson(response.toString(), LoginPromo.class);
                if (loginPromo.getResponse_code().equals(ResponseCode.SUCCESS)) {
                    setPromo();
                } else {
                    HomeActivity x = (HomeActivity) rootView.getContext();
                    x.requestLogout();
                    PreferenceClass.setLogOut();
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    x.startActivity(intent);
                    x.finish();
                }
            } else if (actionCode == ActionCode.LIST_PRODUK_TOMO) {
                ProdukTomoModel produkTomoModel = ((BaseActivity) getActivity()).gson.fromJson(response.toString(), ProdukTomoModel.class);

                if (produkTomoModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                    recycler_view_produk.setAdapter(mAdapterProduk);
                    itemListProduk.addAll(produkTomoModel.getResponse_value());
                    mAdapterProduk.notifyDataSetChanged();
                }
            } else if (actionCode == ActionCode.LIST_PRODUK_FMCG) {
                Timber.d("onSuccess FMCG: %s", response.toString());
                produkFMCGModel = ((BaseActivity) getActivity()).gson.fromJson(response.toString(), ProdukFMCGModel.class);

                shimmer_view_container.stopShimmerAnimation();
                shimmer_view_container.setVisibility(View.GONE);

                if (produkFMCGModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                    itemListProdukFmcg.clear();
                    loadItemsLayout_recyclerView.setVisibility(View.GONE);
                    if (produkFMCGModel.getResponse_value().size() > 0) {
                        for (int i = 0; i < produkFMCGModel.getResponse_value().size(); i++) {
                            GrosirModel grosirModel = new GrosirModel();
                            grosirModel.setNama(produkFMCGModel.getResponse_value().get(i).getNama());
                            grosirModel.setFoto(produkFMCGModel.getResponse_value().get(i).getFoto());
                            grosirModel.setHarga(produkFMCGModel.getResponse_value().get(i).getHarga());
                            grosirModel.setHarga_coret(produkFMCGModel.getResponse_value().get(i).getHarga_coret());
                            grosirModel.setIs_cover(produkFMCGModel.getResponse_value().get(i).getIs_cover());
                            grosirModel.setUrl(produkFMCGModel.getResponse_value().get(i).getUrl());
                            itemListProdukFmcg.add(grosirModel);
                        }

                        mAdapterProdukFMCG = new GrosirAdapter(getActivity(), itemListProdukFmcg, this);
                        recycler_view_grosir.setAdapter(mAdapterProdukFMCG);
                        mAdapterProdukFMCG.setOnBottomReachedListener(position -> {
                            Timber.d("onBottomReached begin: %s", position);
                        });
                        recycler_view_grosir.setVisibility(View.VISIBLE);
                    } else {
                        Timber.d("onSuccess: empty");
                    }

                } else {
                    ((BaseActivity) getActivity()).new_popup_alert(getActivity(), "Info", "Barang yang Anda cari saat ini tidak dapat ditemukan");
                }
            } else if (actionCode == ActionCode.LIST_PRODUK_FMCG_LOAD_MORE) {
                Timber.d("onSuccess FMCG more: %s", response.toString());
                produkFMCGModel = ((BaseActivity) getActivity()).gson.fromJson(response.toString(), ProdukFMCGModel.class);
                loadItemsLayout_recyclerView.setVisibility(View.GONE);
                if (produkFMCGModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                    if (produkFMCGModel.getResponse_value().size() > 0) {
                        for (int i = 0; i < produkFMCGModel.getResponse_value().size(); i++) {
                            GrosirModel grosirModel = new GrosirModel();
                            grosirModel.setNama(produkFMCGModel.getResponse_value().get(i).getNama());
                            grosirModel.setFoto(produkFMCGModel.getResponse_value().get(i).getFoto());
                            grosirModel.setHarga(produkFMCGModel.getResponse_value().get(i).getHarga());
                            grosirModel.setHarga_coret(produkFMCGModel.getResponse_value().get(i).getHarga_coret());
                            grosirModel.setIs_cover(produkFMCGModel.getResponse_value().get(i).getIs_cover());
                            grosirModel.setUrl(produkFMCGModel.getResponse_value().get(i).getUrl());
                            itemListProdukFmcg.add(grosirModel);
                        }
                        mAdapterProdukFMCG.notifyDataSetChanged();
                        mAdapterProdukFMCG.setOnBottomReachedListener(position -> {
                            Timber.d("onBottomReached: %s", position);
                            nestedScrollView.pageScroll(View.FOCUS_DOWN);
                        });
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        if (actionCode == ActionCode.SLIDE_HOME) {
            (rootView.findViewById(R.id.avi)).setVisibility(View.GONE);
        }
    }

    @Override
    public void onUpdate(int actionCode, long bytesRead, long totalSize, boolean done) {
    }

    private void requestSliderHome() {
        StringJson stringJson = new StringJson(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestSliderHome());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse((AppCompatActivity) getActivity(), jsonObject, ActionCode.SLIDE_HOME, this);
    }

    public static int getColorWithAlpha(int baseColor, float alpha) {
        int a = Math.min(255, Math.max((int) (alpha * 255), 0)) << 24;
        int rgb = baseColor & 0x00ffffff;
        return rgb + a;
    }

    @Override
    public void onClickProduk(@NonNull ProdukTomoModel.Response_value produk) {
        if (!DetectConnection.isOnline(context)) {
            ((BaseActivity) context).new_popup_alert_failure(context, "Cek Koneksi Internet Anda");
        } else {
            String apps = "com.android.chrome";
            boolean installed = Device.checkAppInstall(context, apps);
            if (installed) {
                CustomTabsIntent customTabsIntent = ((BaseActivity) context).buildCustomTabsIntent();
                customTabsIntent.launchUrl(context, Uri.parse(produk.getUrl() + PreferenceClass.getAuth()));
            } else {
                Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                startActivity(webIntent);
            }
        }
    }

    private void startBlinkingAnimation() {
        textViewHeaderProdukTomo.setTextColor(ContextCompat.getColor(context, R.color.md_red_500));
        Animation startAnimation = AnimationUtils.loadAnimation(context, R.anim.blinking_animation);
        textViewHeaderProdukTomo.startAnimation(startAnimation);
    }

    @Override
    public void onPause() {
        Timber.d("onPause: %s", FCMConstants.isStillRunningRequest);
        if (recyclerBanner.isPlaying()) {
            recyclerBanner.setPlaying(false);
        }
        super.onPause();
    }

    @Override
    public void onClickProdukFMCG(@NonNull GrosirModel produk) {
        Timber.d("onClickProduk: %s", Uri.parse(produk.getUrl() + PreferenceClass.getAuth()));
        if (!DetectConnection.isOnline(context)) {
            ((BaseActivity) context).new_popup_alert_failure(context, "Cek Koneksi Internet Anda");
        } else {
            ((BaseActivity) context).logEventFireBase("Home", "FMCG Produk " + produk.getNama(), EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
            CustomTabsIntent customTabsIntent = ((BaseActivity) context).buildCustomTabsIntent();
            customTabsIntent.launchUrl(context, Uri.parse(produk.getUrl() + PreferenceClass.getAuth()));
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        String link = imageTarget.get(position);

        try {
            String apps = "com.android.chrome";
            boolean installed = Device.checkAppInstall(context, apps);
            if (installed) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);
            } else {
                Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                context.startActivity(webIntent);
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void onBannerScrollListener(int currentIndex) {
        Timber.d("onBannerScrollListener: %s", currentIndex);
        try {
            Glide.with(context).asBitmap().load(image.get(currentIndex)).into(new SimpleTarget<Bitmap>() {

                @SuppressLint("ResourceAsColor")
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                        try {
                            Palette.from(resource).generate(palette -> {
                                int vibrantColor = palette.getLightVibrantColor(R.color.colorPrimary_ppob);
                                ObjectAnimator animator = ColorAnimator.ofBackgroundColor(appBarLayout, vibrantColor);
                                animator.start();
                            });
                        } catch (Exception ignored) {
                        }
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                        ObjectAnimator animator = ColorAnimator.ofBackgroundColor(appBarLayout, R.color.colorPrimary_ppob);
                        animator.start();
                    }
                }
            });
        } catch (Exception ignored) {
        }
    }
}
