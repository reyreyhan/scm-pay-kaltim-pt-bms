package com.bm.main.fpl.adapters;

import android.content.Context;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.activities.FeePesawatActivity;
import com.bm.main.fpl.models.FeeFlightModel;

import com.bm.main.fpl.utils.MoneyTextWatcher;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.single.ftl.utils.FormatUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sarif Hidayat on 05/06/2017.
 */

public class Fee_List_FlightAdapter extends RecyclerView.Adapter<Fee_List_FlightAdapter.ViewHolder> {
    private Context context;
    public List<FeeFlightModel> mList;//= Collections.emptyList();;
    public List<FeeFlightModel> mListstoredRowItems = new ArrayList<FeeFlightModel>();
    LayoutInflater inflater;
    RecyclerView mRecyclerView;

    public Fee_List_FlightAdapter(Context context, List<FeeFlightModel> itemList) {
        this.context = context;
        this.mList = itemList;
        this.mListstoredRowItems = itemList;
        inflater = LayoutInflater.from(context);


    }

    public List<FeeFlightModel> getList() {
        return this.mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = inflater.inflate(R.layout.fee_flight_list_item, parent, false);
        mRecyclerView = (RecyclerView) parent;
        return new ViewHolder(itemView);
    }

    boolean check;

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        final FlightDataModelClasses c = mList.get(position);
        final int posisi = position;
        final FeeFlightModel c = mList.get(position);

        holder.textFee.setText(FormatUtil.FormatMoneySymbol(c.getCustomAdmin()));
        holder.textFee.setTag(R.id.idfee, c.getAirline());
        holder.textFee.setHint(c.getAirlineName());
        holder.textFee.setFloatingLabelAlwaysShown(true);
        Log.d("adapter", "onBindViewHolder: " + c.getAirline());
        InputMethodManager imm = (InputMethodManager) holder.itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(holder.itemView.getWindowToken(), 0);
        Glide.with(context).load(c.getIcon())
                //  .override(40,40)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.icon);
        mRecyclerView.getChildAt(holder.getAdapterPosition() - 1);
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public MaterialEditText textFee;
        public ImageView icon;
        AppCompatButton btnSet;

        public ViewHolder(View view) {
            super(view);

            textFee = view.findViewById(R.id.textFeeFlight);
            icon = view.findViewById(R.id.imageViewIcon);
            textFee.addTextChangedListener(new MoneyTextWatcher(view.getContext(), textFee));
            btnSet = view.findViewById(R.id.btn_simpanFee);
            btnSet.setOnClickListener(v -> {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                ((FeePesawatActivity) context).getRequestFromAdapter(textFee);
            });

            textFee.setOnEditorActionListener(new DoneOnEditorActionListener());
        }

        class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //return true;
                    //  btnSelesai.performClick();
                    btnSet.performClick();
                }
                return false;
            }
        }
    }

}
