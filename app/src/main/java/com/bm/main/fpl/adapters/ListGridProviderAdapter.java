package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bm.main.scm.R;
import com.bm.main.fpl.models.ProviderModel;
import com.bm.main.fpl.templates.AutoResizeTextView;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;

import java.util.ArrayList;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

public class ListGridProviderAdapter extends RecyclerView.Adapter<ListGridProviderAdapter.ViewHolder> implements Filterable {
    private ArrayList<ProviderModel.Response_value> data ;//= new ArrayList<>();
    private ArrayList<ProviderModel.Response_value> filterData;// = new ArrayList<>();
    private Context context;
    private OnClickProduk listener;
    @NonNull
    private final LayoutInflater inflater;
    public ListGridProviderAdapter(ArrayList<ProviderModel.Response_value> data, @NonNull Context context, OnClickProduk listener) {
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ListGridProviderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.list_produk_grid, viewGroup, false);
//        SolventViewHolders rcv = new SolventViewHolders(MaterialRippleLayout.on(v)
//                .rippleOverlay(true)
//                .rippleAlpha(0.2f)
//                //.rippleColor(0xFF585858)
//                .rippleColor(R.color.colorPrimary)
//                .rippleHover(true)
//                .create()
//        );
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListGridProviderAdapter.ViewHolder viewHolder, int i) {
        final ProviderModel.Response_value produk = filterData.get(i);
        viewHolder.textViewPlusNamaProduk.setText(produk.getProvider_name());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.produkCode, produk.getProvider_code());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.isGangguanProduk, produk.getIs_gangguan());
        if (produk.getIs_promo().equalsIgnoreCase("1")) {
            viewHolder.frame_ribbon.setVisibility(View.VISIBLE);
        }
//        Picasso. with(context)
//                .load(produk.getProvider_url()).into(viewHolder.imageViewProduk, new Callback() {
//            @Override
//            public void onSuccess() {
//                viewHolder.avi.setVisibility(View.GONE);
//              //  viewHolder.imageViewProduk.getDrawingCache(true);
//            }
//
//            @Override
//            public void onError() {
//                viewHolder.avi.setVisibility(View.GONE);
////                if (produk.getProduct_code().startsWith("WA")) {
////                    viewHolder.imageViewProduk.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_menu_pdam));
////                } else {
//                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
//                //  }
//            }
//
//        });

//
        viewHolder.linMainListProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickProduk(produk);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterData.size();
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

                    ArrayList<ProviderModel.Response_value> filteredList = new ArrayList<>();

                    for (ProviderModel.Response_value produk : data) {

                        if (produk.getProvider_code().toLowerCase().contains(charString) || produk.getProvider_name().toLowerCase().contains(charString)) {

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
                filterData = (ArrayList<ProviderModel.Response_value>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AutoResizeTextView textViewPlusNamaProduk;
        ImageView imageViewProduk;
        AVLoadingIndicatorView avi;
        FrameLayout frame_ribbon;
        RelativeLayout linMainListProduk;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            linMainListProduk = itemView.findViewById(R.id.linMainListProduk);
            MaterialRippleLayout.on(linMainListProduk).rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    //.rippleColor(0xFF585858)
                    .rippleColor(R.color.colorPrimary_ppob)
                    .rippleHover(true)
                    .create();
            frame_ribbon = itemView.findViewById(R.id.frame_ribbon);
            textViewPlusNamaProduk = itemView.findViewById(R.id.textViewPlusNamaProduk);
            imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
            avi = itemView.findViewById(R.id.avi);

        }
    }

    public interface OnClickProduk {
        void onClickProduk(ProviderModel.Response_value produk);
    }
}
