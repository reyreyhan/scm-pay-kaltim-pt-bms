package com.bm.main.fpl.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
//import androidx.appcompat.widget.CardView;
//import androidx.appcompat.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.interfaces.OnLoadMoreListener;
import com.bm.main.fpl.models.GrosirModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

//import com.bm.main.fpl.models.ProdukFMCGModel;
//import com.bm.main.fpl.templates.EndlessRecyclerViewScrollListener;

/**
 * Created by sarifhidayat on 10/10/18.
 **/
public class GrosirAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    @NonNull
    String TAG = GrosirAdapter.class.getSimpleName();
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    OnBottomReachedListener onBottomReachedListener;
    // private Activity activity;
//        private List<ProdukFMCGModel> fmcgModels;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private List<GrosirModel> data;
    List<GrosirModel> filterData = new ArrayList<>();
    private Context context;
    private OnClickProdukFMCG listener;
    LayoutInflater inflater;
    GridLayoutManager linearLayoutManager;

    Bitmap smallBitmap;
    final BitmapFactory.Options options = new BitmapFactory.Options();

    // public GrosirAdapter(Context context, RecyclerView recyclerView, List<GrosirModel> itemList, OnClickProdukFMCG listener) {
    public GrosirAdapter(Context context, List<GrosirModel> itemList, OnClickProdukFMCG listener) {
        this.context = context;
        this.data = itemList;
        this.filterData = data;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
        options.inJustDecodeBounds = true;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {

        this.onBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProdukViewHolder(MaterialRippleLayout.on(inflater.inflate(R.layout.solvent_list_produk_fmcg, parent, false))
                .rippleOverlay(true)
                .rippleAlpha(0.2f)
                .rippleColor(R.color.colorPrimary_ppob)
                .rippleHover(true)
                .create()
        );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final GrosirModel produk = filterData.get(position);
        Log.d(TAG, "onBindViewHolder: " + (filterData.size() - 1) + " " + position);
        if (position == filterData.size() - 1) {
            onBottomReachedListener.onBottomReached(position);
        }

        final ProdukViewHolder produkViewHolder = (ProdukViewHolder) viewHolder;
        produkViewHolder.textViewProduk.setText(produk.getNama());
        produkViewHolder.harga.setText("Rp. " + FormatString.CurencyIDR(produk.getHarga()));
        produkViewHolder.harga_coret.setPaintFlags(produkViewHolder.harga_coret.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        try {
            produkViewHolder.harga_coret.setText("Rp. " + FormatString.CurencyIDR(produk.getHarga_coret()));
        } catch (Exception e) {
            produkViewHolder.harga_coret.setVisibility(View.INVISIBLE);
        }

        Glide.with(context).asBitmap().load(produk.getFoto())
                .fitCenter().encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).placeholder(ContextCompat.getDrawable(context, R.drawable.logo_tomo_gray_big)).error(ContextCompat.getDrawable(context, R.drawable.logo_tomo_gray_big))
                .diskCacheStrategy(DiskCacheStrategy.NONE).override(100, 75).into(new BitmapImageViewTarget(produkViewHolder.imageViewProduk) {
            //                    .dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).override(100,75).into(new GlideDrawableImageViewTarget(produkViewHolder.imageViewProduk) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                // here it's similar to RequestListener, but with less information (e.g. no model available)
                super.onResourceReady(resource, animation);
                produkViewHolder.avi.setVisibility(View.GONE);
            }

            // +++++ OR +++++
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                produkViewHolder.avi.setVisibility(View.GONE);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                produkViewHolder.avi.setVisibility(View.GONE);
            }
        });


        produkViewHolder.lin_view_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    ((BaseActivity) context).new_popup_alert(v.getContext(), "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");

                } else {
                    listener.onClickProdukFMCG(produk);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterData == null ? 0 : filterData.size();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @NonNull
            @Override
            protected FilterResults performFiltering(@NonNull CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    filterData = data;
                } else {

                    ArrayList<GrosirModel> filteredList = new ArrayList<>();

                    for (GrosirModel produk : data) {

                        if (produk.getNama().toLowerCase().contains(charString)) {

                            filteredList.add(produk);
                        }
                    }

                    filterData = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, @NonNull FilterResults filterResults) {
                filterData = (ArrayList<GrosirModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        //public ProgressBar progressBar;
        AVLoadingIndicatorView aviLoading;

        public LoadingViewHolder(@NonNull View view) {
            super(view);
            aviLoading = view.findViewById(R.id.aviLoading);
        }
    }

    public class ProdukViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProduk, url, harga_coret;
        TextView komisi;
        TextView harga, labelKomisi;
        ImageView imageViewProduk;
        //            SimpleDraweeView imageViewProduk;
        AVLoadingIndicatorView avi;
        FrameLayout frame_ribbon;
        RelativeLayout frameIcon;
        CardView card_view;
        LinearLayout linKomisiOutlet, lin_view_produk;

        public ProdukViewHolder(@NonNull View view) {
            super(view);
            lin_view_produk = itemView.findViewById(R.id.lin_view_produk);
            card_view = itemView.findViewById(R.id.card_view);
            frame_ribbon = itemView.findViewById(R.id.frame_ribbon);
            frameIcon = itemView.findViewById(R.id.frameIcon);
            textViewProduk = itemView.findViewById(R.id.textViewNamaProduk);
            imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
            harga = itemView.findViewById(R.id.textViewHargaTomo);
            harga_coret = itemView.findViewById(R.id.textViewHargaCoretTomo);

            komisi = itemView.findViewById(R.id.textViewKomisiTomo);
            labelKomisi = itemView.findViewById(R.id.textViewlabelKomisi);
            linKomisiOutlet = itemView.findViewById(R.id.linKomisiOutlet);

            avi = itemView.findViewById(R.id.avi);
        }
    }

    public interface OnClickProdukFMCG {
        void onClickProdukFMCG(GrosirModel produk);
    }

    public interface OnBottomReachedListener {
        void onBottomReached(int position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
