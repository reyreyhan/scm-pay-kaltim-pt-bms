package com.bm.main.single.ftl.ship.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bm.main.scm.R;

/**
 * Created by sarifhidayat on 2019-06-27.
 **/
public class ShipCalendarAdapter extends RecyclerView.Adapter<ShipCalendarAdapter.ViewHolder>  {
    private static final String TAG = ShipCalendarAdapter.class.getSimpleName();
    //    ArrayList<ShipDestinationModel.Data> originalData = new ArrayList<>();
//    ArrayList<ShipDestinationModel.Data>filterData = new ArrayList<>();
    @NonNull
    String[] months = new String[] {
            "Januari", "Februari", "Maret",
            "April", "Mei", "Juni",
            "Juli", "Agustus", "September",
            "Oktober", "November", "Desember"
    };
    Context context;
    OnClickJadwalKeberangkatan listener;
    @NonNull
    private final LayoutInflater inflater;
    public int selectedIndex = 0;
    public int selectedYear = 0;
//    int selectedMonth = 0;
public int currentYear = 0;
    public int currentMonth = 0;

    public ShipCalendarAdapter(@NonNull Context context, OnClickJadwalKeberangkatan listener){

        this.context = context;
        this.listener = listener;
        // options.inJustDecodeBounds = false;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ShipCalendarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =inflater.inflate(R.layout.ship_item_month,viewGroup,false);
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
    public void onBindViewHolder(@NonNull ShipCalendarAdapter.ViewHolder viewHolder, final int position) {
//        final ShipDestinationModel.Data port = filterData.get(i);
        final String data=months[position];
        viewHolder.tvMonth.setText(data);
        if(selectedYear * 100 + position < currentYear * 100 + currentMonth) {
            viewHolder.tvMonth.setTextColor(ContextCompat.getColor(context, R.color.md_white_1000));
//                view.setBackgroundResource(R.color.md_grey_400);
            viewHolder.tvMonth.setBackgroundResource(R.drawable.selector_button_grey_pressed);
            }else {
                if(selectedIndex == position) {
                    viewHolder.tvMonth.setTextColor(ContextCompat.getColor(context, R.color.md_blue_500));
                    viewHolder.tvMonth.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                    //view.setBackgroundResource(R.color.colorPrimary);
                }else{
//                    tvMonth.setTextColor(Color.BLACK);
////                    view.setBackgroundResource(R.color.md_white_1000);
                    viewHolder.tvMonth.setTextColor(ContextCompat.getColor(context, R.color.md_orange_500));
//                view.setBackgroundResource(R.color.md_grey_400);
                    viewHolder.tvMonth.setBackgroundResource(R.drawable.selector_button_orange_white_pressed_grid);
                }
            }
        viewHolder.tvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickJadwalKeberangkatan(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return months.length;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMonth;
        // LinearLayout linMainListPort;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonth = itemView.findViewById(R.id.tvMonth);
//            MaterialRippleLayout.on(tvMonth).rippleOverlay(true)
//                    .rippleAlpha(0.2f)
//                    //.rippleColor(0xFF585858)
//                    .rippleColor(R.color.colorPrimary)
//                    .rippleHover(true)
//                    .create();
            //  textName = itemView.findViewById(R.id.textName);

        }
    }

    public interface OnClickJadwalKeberangkatan {
        public void onClickJadwalKeberangkatan(int position);
    }
}
