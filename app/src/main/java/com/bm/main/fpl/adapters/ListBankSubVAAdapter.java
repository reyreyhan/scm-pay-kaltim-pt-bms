package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.models.BankVaNewModelResponse_valueVa_desc;

import java.util.ArrayList;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListBankSubVAAdapter extends RecyclerView.Adapter<ListBankSubVAAdapter.ViewHolder> {
    @NonNull
    String TAG=ListBankSubVAAdapter.class.getSimpleName();
    ArrayList<BankVaNewModelResponse_valueVa_desc> data = new ArrayList<>();
    ArrayList<BankVaNewModelResponse_valueVa_desc> filterData = new ArrayList<>();
   // public ArrayList<BankVaNewModelResponse_valueVa_descSteper_value> steper=new ArrayList<>();
    Context context;
    OnClickProdukSubVA listener;
    SparseBooleanArray selectedItems;

    public ListBankSubVAAdapter(ArrayList<BankVaNewModelResponse_valueVa_desc> data, Context context, OnClickProdukSubVA listener) {
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener =  listener;
        selectedItems=new SparseBooleanArray();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_bank_sub_va, viewGroup, false);
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
    public int expandedPosition = -1;
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final BankVaNewModelResponse_valueVa_desc produk = filterData.get(i);
//if(!produk.getType().equals("")) {
    viewHolder.textViewSubHeaderVA.setText(produk.getType());
    viewHolder.textViewNotesVA.setText(Html.fromHtml(produk.getNote()));
//    if(produk.getSteper_value().length>0&&produk.){
//
//    }
//        for(int x=0;x<produk.getSteper_value().length;x++){
//            steper.add(produk.getSteper_value()[x]);
//        }


    Log.d(TAG, "onBindViewHolderSUBVa: " + produk.getBank_name()+" "+produk.getType());
        if (i == expandedPosition) {
        //if(viewHolder.linInetVA.getVisibility()==View.GONE){
            viewHolder.linInetVA.setVisibility(View.VISIBLE);
            viewHolder.linSubHeaderVA.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary_ppob));
            viewHolder.imageView.setImageResource(R.drawable.ic_up_arrow_white);
        }else {
            viewHolder.linInetVA.setVisibility(View.GONE);
            viewHolder.linSubHeaderVA.setBackgroundColor(ContextCompat.getColor(context,R.color.md_grey_500));
            viewHolder.imageView.setImageResource(R.drawable.ic_down_arrow_white);
        }
    viewHolder.linSubHeaderVA.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listener.onClickProdukSubVA(produk, viewHolder);
        }
    });
//}
        //   viewHolder.textViewPlusNamaProduk.setText(produk.getVa_bank_name());
        //viewHolder.imageViewProduk.setTag(R.id.produkCode, produk.getVa_bank_code());
//        viewHolder.textViewNomorVA.setText(produk.getVa_no());
//        viewHolder.textViewHeaderVA.setText(produk.getVa_header());
//        viewHolder.linHeaderVA.setTag(produk.getVa_bank_code());

//        for(int x=0;x<produk.getVa_desc().length;x++){
//            Log.d(TAG, "onBindViewHolder: "+produk.getVa_desc()[x].getType());
//            BankVaNewModelResponse_valueVa_descSteper_value[] stepper = produk.getVa_desc()[x].getSteper_value();
//            viewHolder.textViewSubHeaderVA.setText(produk.getVa_desc()[x].getType());
////            for(int y=0;y<stepper.length;y++){
////                Log.d(TAG, "onBindViewHolder: "+stepper[y].getDesc_number()+" "+stepper[y].getDesc_name());
////            }
//        }



        //if(produk.getVa_bank_type().equals("24JAM")){
           // viewHolder.materialEditTextNominal24.setText("");
                // layout_transfer_bank24.invalidate();
                // layout_transfer_bank24.requestLayout();
        //viewHolder.nominal24 = 500000;

        //viewHolder.materialEditTextNominal24.setText(String.valueOf(viewHolder.nominal24));

//        int adapterPos = viewHolder.getAdapterPosition();
//        if (adapterPos != RecyclerView.NO_POSITION) {
//            if (listener != null) {
//                clearSelections();
//                toggleSelection(adapterPos);
//
//                listener.onClickProduk(produk,adapterPos);
//            }
//        }


//                viewHolder.radioGroupBank24.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroupPlus group, @IdRes int checkedId) {
//                        Log.d("TAG", checkedId + "is checked");
//                        int selectedId = group.getCheckedRadioButtonId();
//                        //  if (selectedId == R.id.radioGroupTabs) {
//                        viewHolder.radioButtonNominalBank24 = viewHolder.itemView.findViewById(selectedId);
//                        viewHolder.nominal24 = Integer.parseInt(viewHolder.radioButtonNominalBank24.getTag().toString());
//                        Log.d(TAG, "onCheckedChanged: " + viewHolder.nominal24);
//                        viewHolder.materialEditTextNominal24.setText(String.valueOf(viewHolder.nominal24));
//                    }
//                });

       // }
        //viewHolder.textViewPlusNamaProduk.setTag(R.id.isGangguanProduk,produk.getIs_gangguan());
//        if (produk.getIs_promo().equalsIgnoreCase("1")) {
//            viewHolder.frame_ribbon.setVisibility(View.VISIBLE);
//        }
//        viewHolder.linHeaderVA.setTag(i,R.id.id_header);

//        Picasso.with(context).load(produk.getProduct_url()).into(viewHolder.imageViewProduk, new Callback() {
//            @Override
//            public void onSuccess() {
//                viewHolder.avi.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError() {
//                viewHolder.avi.setVisibility(View.GONE);
//
//                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logo_color));
//
//            }
//
//        });
//        viewHolder.imageViewProduk.setTag(produk.getVa_bank_code());
////
//
//        viewHolder.linHeaderVA.setVisibility(View.GONE);
//        viewHolder.linHeaderNonVA.setVisibility(View.GONE);
//
////        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                boolean shouldExpand = viewHolder.linProdukVA.getVisibility() == View.GONE;
////
////                ChangeBounds transition = new ChangeBounds();
////                transition.setDuration(125);
////
////                if (shouldExpand) {
////                    viewHolder.linProdukVA.setVisibility(View.VISIBLE);
////                  //  viewHolder.imageView_toggle.setImageResource(R.drawable.collapse_symbol);
////                } else {
////                    viewHolder.linProdukVA.setVisibility(View.GONE);
////                  // viewHolder.imageView_toggle.setImageResource(R.drawable.expand_symbol);
////                }
////
////                TransitionManager.beginDelayedTransition(recyclerView, transition);
////                viewHolder.itemView.setActivated(shouldExpand);
////            }
////        });
//        viewHolder.linSubHeaderVA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClickProdukDescVA(produk, viewHolder);
//            }
//        });
//
//
//
//        viewHolder.linProdukVA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                listener.onClickProdukVA(produk, viewHolder);
//            }
//        });
//
//
//        viewHolder.button_lanjutkan24.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClickProdukLanjutkanNonVA(produk, viewHolder);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return filterData.size();
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//
//                String charString = charSequence.toString();
//
//                if (charString.isEmpty()) {
//
//                    filterData = data;
//                } else {
//
//                    ArrayList<BankVAModel.Response_value> filteredList = new ArrayList<>();
//
//                    for (BankVAModel.Response_value produk : data) {
//
//                        if (produk.getVa_bank_code().toLowerCase().contains(charString) || produk.getVa_bank_name().toLowerCase().contains(charString)) {
//
//                            filteredList.add(produk);
//                        }
//                    }
//
//                    filterData = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filterData;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                filterData = (ArrayList<BankVAModel.Response_value>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }


    public class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView textViewHeaderVA, textViewNomorVA;
//        public RecyclerView recyclerStepperSubVA;
//        public ImageView imageViewProduk, imageViewCopyVA, imageViewHeader;
//        AVLoadingIndicatorView avi;
//        FrameLayout frame_ribbon;
//        public LinearLayout linMainListProduk, linInetVA, linSubHeaderVA;
//        public LinearLayout linHeaderVA,linHeaderNonVA;
//        LinearLayout linProdukVA;
//        //public View layout_subheader_va;
//      //  ExpandablePanel linProdukVA;
//
//        AppCompatButton button_lanjutkan24;
//
//public CurrencyEditText materialEditTextNominal24;
// public       RadioGroupPlus radioGroupBank24;
// public       RadioButton radioButtonNominalBank24;
//
//       public int nominal24 = 0;
//public LinearLayout linMainVa;
public TextView textViewSubHeaderVA;
public TextView textViewNotesVA;
public LinearLayout linSubHeaderVA;
public LinearLayout linInetVA;
public RecyclerView recyclerStepperSubVA;
public ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            View v=LayoutInflater.from(itemView.getContext()).inflate(R.layout.list_bank_sub_va, null);
//            textViewSubHeaderVA=v.findViewById(R.id.textViewSubHeaderVA);
//          //  layout_subheader_va = LayoutInflater.from(itemView.getContext()).inflate(R.layout.layout_subheader_va, null);
//
//            linMainListProduk = itemView.findViewById(R.id.linMainListProduk);
//            linProdukVA = itemView.findViewById(R.id.linProdukVA);
//
//            linHeaderVA = itemView.findViewById(R.id.linHeaderVA);
//            linHeaderNonVA = itemView.findViewById(R.id.linHeaderNonVA);
//            linInetVA = itemView.findViewById(R.id.linInetVA);
//            linSubHeaderVA = v.findViewById(R.id.linSubHeaderVA);
//            MaterialRippleLayout.on(linProdukVA).rippleOverlay(true)
//                    .rippleAlpha(0.2f)
//                    //.rippleColor(0xFF585858)
//                    .rippleColor(R.color.colorPrimary)
//                    .rippleHover(true)
//                    .create();
//            frame_ribbon = itemView.findViewById(R.id.frame_ribbon);
//            textViewHeaderVA = itemView.findViewById(R.id.textViewHeaderVA);
//            textViewNomorVA = itemView.findViewById(R.id.textViewNomorVA);
//            imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
//            imageViewCopyVA = itemView.findViewById(R.id.imageViewCopyVA);
//            imageViewHeader = itemView.findViewById(R.id.imageViewHeader);
//            avi = itemView.findViewById(R.id.avi);
//            recyclerStepperSubVA = itemView.findViewById(R.id.recyclerStepperSubVA);
//
//
//            button_lanjutkan24=itemView.findViewById(R.id.button_lanjutkan24);
//            materialEditTextNominal24=itemView.findViewById(R.id.materialEditTextNominal24);
//            radioGroupBank24=itemView.findViewById(R.id.radioGroupBank24);
//            linMainVa=itemView.findViewById(R.id.linMainVa);
            linSubHeaderVA=itemView.findViewById(R.id.linSubHeaderVA);
            linInetVA=itemView.findViewById(R.id.linInetVA);
            imageView=itemView.findViewById(R.id.imageView);
            textViewNotesVA=itemView.findViewById(R.id.textViewNotesVA);
            textViewSubHeaderVA=itemView.findViewById(R.id.textViewSubHeaderVA);
            recyclerStepperSubVA=itemView.findViewById(R.id.recyclerStepperSubVA);
        }


    }

    public interface OnClickProdukSubVA {
        void onClickProdukSubVA(BankVaNewModelResponse_valueVa_desc produk, ViewHolder viewHolder);
//
//
//        void onClickProdukDescVA(BankVAModel.Response_value produk, ViewHolder viewHolder);
//
//
//        void onClickProdukLanjutkanNonVA(BankVAModel.Response_value produk, ViewHolder viewHolder);
    }
//
//    public void toggleSelection(int pos) {
//        if (selectedItems.get(pos, false)) {
//            selectedItems.delete(pos);
//        }
//        else {
//            selectedItems.put(pos, true);
//        }
//        notifyItemChanged(pos);
//    }
//
//    public void clearSelections() {
//        selectedItems.clear();
//        notifyDataSetChanged();
//    }


}
