package com.bm.main.fpl.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.activities.BaseActivity;
//import com.bm.main.fpl.activities.DepositActivity;
import com.bm.main.fpl.models.BankVAModel;
import com.bm.main.fpl.models.BankVaNewModelResponse_valueVa_desc;
import com.bm.main.fpl.models.BankVaNewModelResponse_valueVa_descSteper_value;
import com.bm.main.fpl.templates.CurrencyEditText;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.templates.RadioGroupPlus;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListBankVANewAdapter extends RecyclerView.Adapter<ListBankVANewAdapter.ViewHolder> implements Filterable {
    @NonNull
    String TAG= ListBankVANewAdapter.class.getSimpleName();
    ArrayList<BankVAModel.Response_value> data = new ArrayList<>();
    ArrayList<BankVAModel.Response_value> filterData = new ArrayList<>();
    Context context;
    ListBankSubVAAdapter adapterBankSubVA;
    OnClickProdukVA listener;
    SparseBooleanArray selectedItems;
    @NonNull
    public ArrayList<BankVaNewModelResponse_valueVa_desc> dataSub = new ArrayList<>();
    @NonNull
    public ArrayList<BankVaNewModelResponse_valueVa_descSteper_value> stepper=new ArrayList<>();

    public ListBankVANewAdapter(ArrayList<BankVAModel.Response_value> data, Context context, OnClickProdukVA listener) {
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
        selectedItems=new SparseBooleanArray();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View v;
         v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_bank_va, viewGroup, false);
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
        final BankVAModel.Response_value produk = filterData.get(i);
        //   viewHolder.textViewPlusNamaProduk.setText(produk.getVa_bank_name());
        //viewHolder.imageViewProduk.setTag(R.id.produkCode, produk.getVa_bank_code());
        viewHolder.textViewNomorVA.setText(produk.getVa_no());
        viewHolder.textViewNoVa.setText(produk.getVa_no());

        viewHolder.textViewHeaderVA.setText(produk.getVa_header());
        viewHolder.linHeaderVA.setTag(produk.getVa_bank_code());

                viewHolder.radioGroupBank24.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(@NonNull RadioGroupPlus group, @IdRes int checkedId) {
                        Log.d("TAG", checkedId + "is checked");
                        int selectedId = group.getCheckedRadioButtonId();
                        //  if (selectedId == R.id.radioGroupTabs) {
                        viewHolder.radioButtonNominalBank24 = viewHolder.itemView.findViewById(selectedId);
                       // viewHolder.radioButtonNominalBank24.setSelected(true);
                        viewHolder.nominal24 = Integer.parseInt(viewHolder.radioButtonNominalBank24.getTag().toString());
                        Log.d(TAG, "onCheckedChanged: " + viewHolder.nominal24);
                        viewHolder.materialEditTextNominal24.setText(String.valueOf(viewHolder.nominal24));
//                        if(!viewHolder.radioButtonNominalBank24.isChecked()) {
//                            viewHolder.radioButtonNominalBank24.setChecked(true);
//                        }
                    }
                });


        Glide.with(context).asBitmap().load(produk.getProduct_url()).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).override(50,50).into(new BitmapImageViewTarget(viewHolder.imageViewProduk) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation)  {
                // here it's similar to RequestListener, but with less information (e.g. no model available)
                super.onResourceReady(resource, animation);
                viewHolder.avi.setVisibility(View.GONE);
                // here you can be sure it's already set
            }
            // +++++ OR +++++
            @Override protected void setResource(Bitmap resource) {
                // this.getView().setImageDrawable(resource); is about to be called
                super.setResource(resource);
                viewHolder.avi.setVisibility(View.GONE);
                viewHolder.imageViewProduk.setScaleType(ImageView.ScaleType.FIT_CENTER);
             //   viewHolder.imageViewProduk.setTag(produk.getVa_bank_code());
                // here you can be sure it's already set
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                viewHolder.avi.setVisibility(View.GONE);
              //  Glide.with(context).load( R.mipmap.ic_launcher).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageViewProduk);
                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.ic_launcher));
            }
        });

//
        if (i == expandedPosition) {
            viewHolder.imageViewHeader.setImageResource(R.drawable.ic_up_arrow);
           if(produk.getVa_bank_type().equals("VA")) {
                viewHolder.linHeaderVA.setVisibility(View.VISIBLE);

          }else {
               viewHolder.radioButtonNominalBank24=viewHolder.linHeaderNonVA.findViewById(R.id.radioButton2_24);
//
                viewHolder.radioButtonNominalBank24.setChecked(true);

                viewHolder.nominal24 = Integer.parseInt(viewHolder.radioButtonNominalBank24.getTag().toString());
                viewHolder.materialEditTextNominal24.setText(String.valueOf(viewHolder.nominal24));
                viewHolder.linHeaderNonVA.setVisibility(View.VISIBLE);
               viewHolder.materialEditTextNominal24.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                   }
               });

           }
        }else{
            viewHolder.imageViewHeader.setImageResource(R.drawable.ic_down_arrow);
            if(produk.getVa_bank_type().equals("VA")) {
                viewHolder.linHeaderVA.setVisibility(View.GONE);

            }else {
                viewHolder.linHeaderNonVA.setVisibility(View.GONE);
                viewHolder.textViewNoVa.setVisibility(View.GONE);
                viewHolder.imageViewCopyVAHeader.setVisibility(View.GONE);
                viewHolder.textViewLabelVa.setVisibility(View.GONE);
            }
        }

        viewHolder.linProdukVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


int i=viewHolder.getAdapterPosition();
                Log.d(TAG, "onClick: "+i);
                listener.onClickProdukVA(produk, viewHolder);
            }
        });
        viewHolder.imageViewCopyVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", viewHolder.textViewNomorVA.getText().toString().replaceAll("-",""));
                clipboard.setPrimaryClip(clip);
                ((BaseActivity)context).showToast("No Virtual Account Telah Disalin");
            }
        });

        viewHolder.imageViewCopyVAHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", viewHolder.textViewNoVa.getText().toString().replaceAll("-",""));
                clipboard.setPrimaryClip(clip);
                ((BaseActivity)context).showToast("No Virtual Account Telah Disalin");
            }
        });

        viewHolder.button_lanjutkan24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickProdukLanjutkanNonVA(produk, viewHolder);
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

                    ArrayList<BankVAModel.Response_value> filteredList = new ArrayList<>();

                    for (BankVAModel.Response_value produk : data) {

                        if (produk.getVa_bank_code().toLowerCase().contains(charString) || produk.getVa_bank_name().toLowerCase().contains(charString)) {

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
                filterData = (ArrayList<BankVAModel.Response_value>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHeaderVA, textViewNomorVA,textViewNoVa;
        public RecyclerView recyclerStepperSubVA;
        public ImageView imageViewProduk, imageViewCopyVA, imageViewHeader,imageViewCopyVAHeader;
        AVLoadingIndicatorView avi;
        FrameLayout frame_ribbon;
        public LinearLayout linMainListProduk, linInetVA, linSubHeaderVA;
        public LinearLayout linHeaderVA,linHeaderNonVA;
        LinearLayout linProdukVA;
        //public View layout_subheader_va;
      //  ExpandablePanel linProdukVA;

        AppCompatButton button_lanjutkan24;

public CurrencyEditText materialEditTextNominal24;
 public       RadioGroupPlus radioGroupBank24;
 public       RadioButton radioButtonNominalBank24;

       public int nominal24 = 0;
public LinearLayout linMainVa;
TextView textViewSubHeaderVA,textViewLabelVa;


      //  ListBankSubVAAdapter adapterBankSubVA;
public RecyclerView recyclerViewSubVa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linMainListProduk = itemView.findViewById(R.id.linMainListProduk);
            linProdukVA = itemView.findViewById(R.id.linProdukVA);

            linHeaderVA = itemView.findViewById(R.id.linHeaderVA);
            linHeaderNonVA = itemView.findViewById(R.id.linHeaderNonVA);
            linInetVA = itemView.findViewById(R.id.linInetVA);
textViewNoVa=itemView.findViewById(R.id.textViewNoVa);
            MaterialRippleLayout.on(linProdukVA).rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    //.rippleColor(0xFF585858)
                    .rippleColor(R.color.colorPrimary_ppob)
                    .rippleHover(true)
                    .create();
            frame_ribbon = itemView.findViewById(R.id.frame_ribbon);
            textViewHeaderVA = itemView.findViewById(R.id.textViewHeaderVA);
            textViewNomorVA = itemView.findViewById(R.id.textViewNomorVA);
            imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
            imageViewCopyVA = itemView.findViewById(R.id.imageViewCopyVA);
            textViewLabelVa = itemView.findViewById(R.id.textViewLabelVa);
            imageViewCopyVAHeader = itemView.findViewById(R.id.imageViewCopyVAHeader);
            imageViewHeader = itemView.findViewById(R.id.imageViewHeader);
            avi = itemView.findViewById(R.id.avi);
            recyclerStepperSubVA = itemView.findViewById(R.id.recyclerStepperSubVA);


            button_lanjutkan24=itemView.findViewById(R.id.button_lanjutkan24);
            materialEditTextNominal24=itemView.findViewById(R.id.materialEditTextNominal24);

            radioGroupBank24=itemView.findViewById(R.id.radioGroupBank24);
            linMainVa=itemView.findViewById(R.id.linMainVa);

            recyclerViewSubVa=itemView.findViewById(R.id.recyclerViewSubVa);









        }


    }

    public interface OnClickProdukVA {
        void onClickProdukVA(BankVAModel.Response_value produk, ViewHolder viewHolder);


        void onClickProdukLanjutkanNonVA(BankVAModel.Response_value produk, ViewHolder viewHolder);
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }


}
