package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.models.BankVaNewModelResponse_valueVa_descSteper_value;

import java.util.List;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListStepperBankVaAdapter extends RecyclerView.Adapter<ListStepperBankVaAdapter.ViewHolder> {
    private static final String TAG = ListStepperBankVaAdapter.class.getSimpleName();
    @NonNull
    private final LayoutInflater inflater;
    List<BankVaNewModelResponse_valueVa_descSteper_value> data ;//= new ArrayList<>();

    Context context;

    public ListStepperBankVaAdapter(List<BankVaNewModelResponse_valueVa_descSteper_value> data, @NonNull Context context){
        this.data = data;

        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public ListStepperBankVaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.vertical_stepper_item_view_layout,viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListStepperBankVaAdapter.ViewHolder viewHolder, int i) {
        final BankVaNewModelResponse_valueVa_descSteper_value produk = data.get(i);
       // for(int x=0;x<produk.getMerchant_desc().size();x++) {
            Log.d(TAG, "onBindViewHolder: "+produk.getDesc_number());
            viewHolder.stepper_number.setText(produk.getDesc_number());
            viewHolder.stepper_description.setText(Html.fromHtml(produk.getDesc_name()));
       // }
if(i==getItemCount()-1){
    viewHolder.stepper_line.setVisibility(View.GONE);
}

//

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stepper_description;
        TextView stepper_number;
View stepper_line;
       // LinearLayout linMainListProduk;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            linMainListProduk = itemView.findViewById(R.id.linMainListProduk);
//            MaterialRippleLayout.on(linMainListProduk).rippleOverlay(true)
//                    .rippleAlpha(0.2f)
//                    //.rippleColor(0xFF585858)
//                    .rippleColor(R.color.colorPrimary)
//                    .rippleHover(true)
//                    .create();
            stepper_line=itemView.findViewById(R.id.stepper_line);
            stepper_number = itemView.findViewById(R.id.stepper_number);
            stepper_description = itemView.findViewById(R.id.stepper_description);


        }
    }

}
