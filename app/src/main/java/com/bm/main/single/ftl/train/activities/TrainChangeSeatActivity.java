package com.bm.main.single.ftl.train.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.scm.R;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.train.constants.TrainKeyPreference;
import com.bm.main.single.ftl.train.constants.TrainPath;
import com.bm.main.single.ftl.train.models.TrainSeatLayoutItemModel;
import com.bm.main.single.ftl.train.models.TrainSeatLayoutModel;
import com.bm.main.single.ftl.train.models.TrainSeatModel;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrainChangeSeatActivity extends BaseActivity implements ProgressResponseCallback {

    private static final String TAG = TrainChangeSeatActivity.class.getSimpleName();
    @NonNull
    ArrayList<TrainSeatModel> seatModels = new ArrayList<>();
    @NonNull
    HeaderSeatAdapter headerSeatAdapter = new HeaderSeatAdapter(this);
    @NonNull
    SeatAdapter seatAdapter = new SeatAdapter(this);

    int selectedGerbong = 0;
    int wagonNumber = 0;
    String wagonCode = "";
    String selectedWagonCode = "";
    int selectedSeatOrder = 0;

    RelativeLayout vwHeaderEks, vwHeaderEko;
    TextView tvTextGerbong, tvTextSeat;
    ListView listView;
    RecyclerView headerView;

    class SelectedSeat {
        private String wagonCode, column;
        private int wagonNumber, row;

        public String getWagonCode() {
            return wagonCode;
        }

        public void setWagonCode(String wagonCode) {
            this.wagonCode = wagonCode;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public int getWagonNumber() {
            return wagonNumber;
        }

        public void setWagonNumber(int wagonNumber) {
            this.wagonNumber = wagonNumber;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public boolean isEqual(@NonNull TrainSeatLayoutItemModel seatModelLayoutItem, @NonNull TrainSeatModel seatModel) {
            return this.column.equals(seatModelLayoutItem.getColumn()) && this.row == seatModelLayoutItem.getRow() && this.wagonCode.equals(seatModel.getWagonCode()) && this.wagonNumber == seatModel.getWagonNumber();

        }
    }

    @NonNull
    List<SelectedSeat> selectedSeatList = new ArrayList<>();
    String bookingCode = "", transactionID = "";
    @Nullable
    String kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_change_seat_activity);
        headerView = findViewById(R.id.headerView);
        headerView.setAdapter(headerSeatAdapter);

        listView = findViewById(R.id.listView);
        listView.setAdapter(seatAdapter);

        vwHeaderEks = findViewById(R.id.vwHeaderEks);
        vwHeaderEko = findViewById(R.id.vwHeaderEko);
        tvTextGerbong = findViewById(R.id.tvTextGerbong);
        tvTextSeat = findViewById(R.id.tvTextSeat);
        kelas = PreferenceClass.getString(TrainKeyPreference.classes, "");
        JSONObject jsonobjek = new JSONObject();
        try {
            jsonobjek.put("productCode", "TKAI");
            jsonobjek.put("origin", PreferenceClass.getString(TrainKeyPreference.stationKodeAsal, ""));
            jsonobjek.put("destination", PreferenceClass.getString(TrainKeyPreference.stationKodeTujuan, ""));
            jsonobjek.put("date", PreferenceClass.getString(TrainKeyPreference.departureDateTrain, ""));
            jsonobjek.put("trainNumber", PreferenceClass.getString(TrainKeyPreference.trainNumber, ""));
            jsonobjek.put("token", PreferenceClass.getToken());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("INFO", jsonobjek.toString());

        FrameLayout loadingView =(FrameLayout) View.inflate(this,R.layout.loading_bar_full_dialog, null);
        TextView text = loadingView.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, Sedang memuat halaman kursi");
        openProgressBarDialog(TrainChangeSeatActivity.this, loadingView);
        RequestUtilsTravel.transportWithProgressResponse(this, TrainPath.GETSEAT, jsonobjek, TravelActionCode.LAYOUTKURSI, this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pindah Kursi");
        init(0);

        String trainSeat = getIntent().getStringExtra("trainSeat");
        bookingCode = getIntent().getStringExtra("bookingCode");
        transactionID = getIntent().getStringExtra("transactionID");

        try {
            JSONArray trainSeatArray = new JSONArray(trainSeat);
            for (int i = 0; i < trainSeatArray.length(); i++) {
                JSONArray jsonArray = trainSeatArray.getJSONArray(i);
                SelectedSeat selectedSeat = new SelectedSeat();
                if (!jsonArray.getString(0).equals("") && !jsonArray.getString(1).equals("") && !jsonArray.getString(2).equals("") && !jsonArray.getString(3).equals("")) {
                    selectedSeat.setWagonCode(jsonArray.getString(0));
                    selectedSeat.setWagonNumber(Integer.valueOf(jsonArray.getString(1)));
                    selectedSeat.setRow(Integer.valueOf(jsonArray.getString(2)));
                    selectedSeat.setColumn(jsonArray.getString(3));

                    selectedSeatList.add(selectedSeat);

                    wagonCode = selectedSeat.getWagonCode();
                    wagonNumber = selectedSeat.getWagonNumber();

                    selectedWagonCode = wagonCode;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        refreshInfo();

        AppCompatButton btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (SelectedSeat selectedSeat : selectedSeatList) {
                    if (!selectedSeat.getWagonCode().equals(wagonCode) || selectedSeat.getWagonNumber() != wagonNumber) {
//                        showToastCustom(ChangeSeatTrain.this, 1, "Mohon pilih kursi sesuai gerbong yang dipilih!");
                        snackBarCustomAction(findViewById(R.id.rootLayout), 0, "Mohon pilih kursi sesuai gerbong yang dipilih!", 1);
                        return;
                    }
                }

                JSONObject jsonobjek = new JSONObject();
                try {
                    jsonobjek.put("productCode", "TKAI");
                    jsonobjek.put("bookingCode", bookingCode);
                    jsonobjek.put("transactionId", transactionID);
                    jsonobjek.put("wagonCode", wagonCode);
                    jsonobjek.put("wagonNumber", wagonNumber);
                    JSONArray seats = new JSONArray();
                    for (SelectedSeat selectedSeat : selectedSeatList) {
                        JSONObject seat = new JSONObject();
                        seat.put("row", selectedSeat.getRow());
                        seat.put("column", selectedSeat.getColumn());

                        seats.put(seat);
                    }
                    jsonobjek.put("seats", seats);
                    jsonobjek.put("token", PreferenceClass.getToken());

                    Log.d("INFO", jsonobjek.toString());

                    FrameLayout loadingView =(FrameLayout) View.inflate(TrainChangeSeatActivity.this,R.layout.loading_bar_full_dialog, null);
                    TextView text = loadingView.findViewById(R.id.textContentProgressBar);
                    text.setText("Mohon Tunggu, Proses perpindahan kursi sedang diproses");
                    openProgressBarDialog(TrainChangeSeatActivity.this, loadingView);
                    Log.d(TAG, "onClick: change_seat " + jsonobjek);
                    RequestUtilsTravel.transportWithProgressResponse(TrainChangeSeatActivity.this, TrainPath.CHANGESEAT, jsonobjek, TravelActionCode.GANTIKURSI, TrainChangeSeatActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    String id_trx_ganti_kursi;

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        closeProgressBarDialog();
        try {
            String rc = response.getString("rc");
            final String rd = response.getString("rd");
            if (rc.equals(ResponseCode.SUCCESS)) {
                if (actionCode == TravelActionCode.LAYOUTKURSI) {
                    //   closeProgressBarDialog();

                    JSONArray seattrain = response.getJSONArray("data");
                    seatModels = new ArrayList<>();
                    for (int d = 0; d < seattrain.length(); d++) {
                        JSONObject jsonObject = seattrain.getJSONObject(d);
                        TrainSeatModel seatModel = new TrainSeatModel(jsonObject);
                        seatModels.add(seatModel);

                        if (seatModel.getWagonCode().equals(wagonCode) && seatModel.getWagonNumber() == wagonNumber) {
                            selectedGerbong = d;
                        }
                    }

//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
                    headerSeatAdapter.notifyDataSetChanged();
                    seatAdapter.notifyDataSetChanged();

                    if (seatModels.get(selectedGerbong).is4Row()) {
                        vwHeaderEko.setVisibility(View.GONE);
                        vwHeaderEks.setVisibility(View.VISIBLE);
                    } else {
                        vwHeaderEks.setVisibility(View.GONE);
                        vwHeaderEko.setVisibility(View.VISIBLE);
                    }

                    headerView.scrollToPosition(selectedGerbong);
//                        }
//                    });

                } else if (actionCode == TravelActionCode.GANTIKURSI) {
                    closeProgressBarDialog();
                    //JSONObject oParent = response.getJSONObject("data");
                    id_trx_ganti_kursi = response.getString("transactionId");
                    JSONArray result = new JSONArray();
                    for (SelectedSeat selectedSeat : selectedSeatList) {
                        JSONArray seat = new JSONArray();
                        seat.put(selectedSeat.getWagonCode());
                        seat.put(selectedSeat.getWagonNumber());
                        seat.put(selectedSeat.getRow());
                        seat.put(selectedSeat.getColumn());

                        result.put(seat);
                    }

                    Intent intent = new Intent();

                    intent.putExtra("id_trx_ganti_kurisi", id_trx_ganti_kursi);
                    intent.putExtra("result", result.toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            } else {
//                if(actionCode==TravelActionCode.GANTIKURSI){
//                    closeProgressBarDialog();
//                }

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                showToastCustom(ChangeSeatTrain.this,2,rd);
                snackBarCustomAction(findViewById(R.id.rootLayout), 0, rd, 2);
//                    }
//                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, final String responseDescription, Throwable throwable) {
        closeProgressBarDialog();
        if (responseCode.equals("99")) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//            showToastCustom(ChangeSeatTrain.this, 1, responseDescription);
            snackBarCustomAction(findViewById(R.id.rootLayout), 0, responseDescription, 1);
//                }
//            });

        }
    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {

    }

    public class SeatAdapter extends BaseAdapter {
        private Context mContext;

        public SeatAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return seatModels.size() > 0 ? seatModels.get(selectedGerbong).getModelLayoutList().size() : 0;
        }

        @Nullable
        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);

            if (seatModels.size() == 0) {
                return new View(mContext);
            }

            final TrainSeatModel seatModel = seatModels.get(selectedGerbong);
            final TrainSeatLayoutModel seatModelLayout = seatModel.getModelLayoutList().get(position);

            if (seatModel.is4Row()) {
                View view = inflater.inflate(R.layout.train_item_seat_eks, null);
                TextView tvEksNo = view.findViewById(R.id.tvEksNo);
                ImageView ivEksA = view.findViewById(R.id.ivEksA);
                ImageView ivEksB = view.findViewById(R.id.ivEksB);
                ImageView ivEksC = view.findViewById(R.id.ivEksC);
                ImageView ivEksD = view.findViewById(R.id.ivEksD);

                if (seatModelLayout.getColA() == null) {
                    ivEksA.setVisibility(View.GONE);
                } else {
                    if (isSeatSame(seatModelLayout.getColA())) {
                        ivEksA.setImageResource(R.drawable.seat_choose);
                        //  }else if(seatModelLayout.getColA().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode)) {
                    } else if (seatModelLayout.getColA().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode) || !seatModelLayout.getColA().getKelas().equals(PreferenceClass.getString(TrainKeyPreference.classes, ""))) {
                        ivEksA.setImageResource(R.drawable.seat_filled);
                    } else {
                        ivEksA.setImageResource(R.drawable.seat_empty);
                        ivEksA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                seatClicked(seatModelLayout, "A");
                            }
                        });
                    }
                }

                if (seatModelLayout.getColB() == null) {
                    ivEksB.setVisibility(View.GONE);
                } else {
                    if (isSeatSame(seatModelLayout.getColB())) {
                        ivEksB.setImageResource(R.drawable.seat_choose);
//                    }else if(seatModelLayout.getColB().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode)) {
                    } else if (seatModelLayout.getColB().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode) || !seatModelLayout.getColB().getKelas().equals(PreferenceClass.getString(TrainKeyPreference.classes, ""))) {
                        ivEksB.setImageResource(R.drawable.seat_filled);
                    } else {
                        ivEksB.setImageResource(R.drawable.seat_empty);
                        ivEksB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                seatClicked(seatModelLayout, "B");
                            }
                        });
                    }
                }

                if (seatModelLayout.getColC() == null) {
                    ivEksC.setVisibility(View.GONE);
                } else {
                    if (isSeatSame(seatModelLayout.getColC())) {
                        ivEksC.setImageResource(R.drawable.seat_choose);
//                    }else if(seatModelLayout.getColC().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode)) {
                    } else if (seatModelLayout.getColC().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode) || !seatModelLayout.getColC().getKelas().equals(PreferenceClass.getString(TrainKeyPreference.classes, ""))) {
                        ivEksC.setImageResource(R.drawable.seat_filled);
                    } else {
                        ivEksC.setImageResource(R.drawable.seat_empty);
                        ivEksC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                seatClicked(seatModelLayout, "C");
                            }
                        });
                    }
                }

                if (seatModelLayout.getColD() == null) {
                    ivEksD.setVisibility(View.GONE);
                } else {
                    if (isSeatSame(seatModelLayout.getColD())) {
                        ivEksD.setImageResource(R.drawable.seat_choose);
//                    }else if(seatModelLayout.getColD().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode)) {
                    } else if (seatModelLayout.getColD().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode) || !seatModelLayout.getColD().getKelas().equals(PreferenceClass.getString(TrainKeyPreference.classes, ""))) {
                        ivEksD.setImageResource(R.drawable.seat_filled);
                    } else {
                        ivEksD.setImageResource(R.drawable.seat_empty);
                        ivEksD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                seatClicked(seatModelLayout, "D");
                            }
                        });
                    }
                }

                tvEksNo.setText(String.valueOf(seatModel.getModelLayoutList().get(position).getRow()));


                return view;
            } else {
                View view = inflater.inflate(R.layout.train_item_seat_eko, null);
                TextView tvEkoNo = view.findViewById(R.id.tvEkoNo);
                ImageView ivEkoA = view.findViewById(R.id.ivEkoA);
                ImageView ivEkoB = view.findViewById(R.id.ivEkoB);
                ImageView ivEkoC = view.findViewById(R.id.ivEkoC);
                ImageView ivEkoD = view.findViewById(R.id.ivEkoD);
                ImageView ivEkoE = view.findViewById(R.id.ivEkoE);

                if (seatModelLayout.getColA() == null) {
                    ivEkoA.setVisibility(View.GONE);
                } else {
                    if (isSeatSame(seatModelLayout.getColA())) {
                        ivEkoA.setImageResource(R.drawable.seat_choose);
//                    }else if(seatModelLayout.getColA().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode)) {
                    } else if (seatModelLayout.getColA().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode) || !seatModelLayout.getColA().getKelas().equals(PreferenceClass.getString(TrainKeyPreference.classes, ""))) {
                        ivEkoA.setImageResource(R.drawable.seat_filled);
                    } else {
                        ivEkoA.setImageResource(R.drawable.seat_empty);
                        ivEkoA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                seatClicked(seatModelLayout, "A");
                            }
                        });
                    }
                }

                if (seatModelLayout.getColB() == null) {
                    ivEkoB.setVisibility(View.GONE);
                } else {
                    if (isSeatSame(seatModelLayout.getColB())) {
                        ivEkoB.setImageResource(R.drawable.seat_choose);
//                    }else if(seatModelLayout.getColB().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode)) {
                    } else if (seatModelLayout.getColB().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode) || !seatModelLayout.getColB().getKelas().equals(PreferenceClass.getString(TrainKeyPreference.classes, ""))) {
                        ivEkoB.setImageResource(R.drawable.seat_filled);
                    } else {
                        ivEkoB.setImageResource(R.drawable.seat_empty);
                        ivEkoB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                seatClicked(seatModelLayout, "B");
                            }
                        });
                    }
                }

                if (seatModelLayout.getColC() == null) {
                    ivEkoC.setVisibility(View.GONE);
                } else {
                    if (isSeatSame(seatModelLayout.getColC())) {
                        ivEkoC.setImageResource(R.drawable.seat_choose);
//                    }else if(seatModelLayout.getColC().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode)) {
                    } else if (seatModelLayout.getColC().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode) || !seatModelLayout.getColC().getKelas().equals(PreferenceClass.getString(TrainKeyPreference.classes, ""))) {
                        ivEkoC.setImageResource(R.drawable.seat_filled);
                    } else {
                        ivEkoC.setImageResource(R.drawable.seat_empty);
                        ivEkoC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                seatClicked(seatModelLayout, "C");
                            }
                        });
                    }
                }

                if (seatModelLayout.getColD() == null) {
                    ivEkoD.setVisibility(View.GONE);
                } else {
                    if (isSeatSame(seatModelLayout.getColD())) {
                        ivEkoD.setImageResource(R.drawable.seat_choose);
//                    }else if(seatModelLayout.getColD().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode) ) {
                    } else if (seatModelLayout.getColD().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode) || !seatModelLayout.getColD().getKelas().equals(PreferenceClass.getString(TrainKeyPreference.classes, ""))) {
                        ivEkoD.setImageResource(R.drawable.seat_filled);
                    } else {
                        ivEkoD.setImageResource(R.drawable.seat_empty);
                        ivEkoD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                seatClicked(seatModelLayout, "D");
                            }
                        });
                    }
                }

                if (seatModelLayout.getColE() == null) {
                    ivEkoE.setVisibility(View.GONE);
                } else {
                    if (isSeatSame(seatModelLayout.getColE())) {
                        ivEkoE.setImageResource(R.drawable.seat_choose);
//                    }else if(seatModelLayout.getColE().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode)) {
                    } else if (seatModelLayout.getColE().isFillled() || !seatModel.getWagonCode().equals(selectedWagonCode) || !seatModelLayout.getColE().getKelas().equals(PreferenceClass.getString(TrainKeyPreference.classes, ""))) {
                        ivEkoE.setImageResource(R.drawable.seat_filled);
                    } else {
                        ivEkoE.setImageResource(R.drawable.seat_empty);
                        ivEkoE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                seatClicked(seatModelLayout, "E");
                            }
                        });
                    }
                }

                tvEkoNo.setText(String.valueOf(seatModel.getModelLayoutList().get(position).getRow()));
                return view;
            }
        }
    }

    public class HeaderSeatAdapter extends RecyclerView.Adapter<HeaderSeatAdapter.MyViewHolder> {

        Context context;

        public HeaderSeatAdapter(Context context) {
            this.context = context;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView txtview;
            View vwBack;

            public MyViewHolder(@NonNull View view) {
                super(view);
                vwBack = view.findViewById(R.id.vwBack);
                txtview = view.findViewById(R.id.txtview);
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_item_header_seat, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            final TrainSeatModel seatModel = seatModels.get(position);
            if (selectedGerbong == position) {
                holder.vwBack.setBackgroundResource(R.drawable.selector_button_red_pressed);
            } else {
                holder.vwBack.setBackgroundResource(R.drawable.selector_button_green_pressed);
            }
            holder.txtview.setText(seatModel.getWagonCode() + " " + seatModel.getWagonNumber());
            holder.vwBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedGerbong = position;
                    wagonNumber = seatModel.getWagonNumber();
                    wagonCode = seatModel.getWagonCode();

                    notifyDataSetChanged();
                    seatAdapter.notifyDataSetChanged();

                    if (seatModel.is4Row()) {
                        vwHeaderEko.setVisibility(View.GONE);
                        vwHeaderEks.setVisibility(View.VISIBLE);
                    } else {
                        vwHeaderEks.setVisibility(View.GONE);
                        vwHeaderEko.setVisibility(View.VISIBLE);
                    }

                    listView.setSelectionAfterHeaderView();
                    refreshInfo();
                }
            });
        }

        @Override
        public int getItemCount() {
            return seatModels.size();
        }
    }

    private boolean isSeatSame(@NonNull TrainSeatLayoutItemModel seatModelLayoutItem) {
        for (SelectedSeat selectedSeat : selectedSeatList) {
            if (selectedSeat.isEqual(seatModelLayoutItem, seatModels.get(selectedGerbong)))
                return true;
        }

        return false;
    }

    private void seatClicked(@NonNull TrainSeatLayoutModel seatModelLayout, String kelas) {
        SelectedSeat selectedSeat = selectedSeatList.get(selectedSeatOrder);
        selectedSeat.setColumn(kelas);
        selectedSeat.setRow(seatModelLayout.getRow());
        selectedSeat.setWagonNumber(wagonNumber);
        selectedSeat.setWagonCode(wagonCode);

        selectedSeatOrder = selectedSeatOrder + 1 == selectedSeatList.size() ? 0 : selectedSeatOrder + 1;

        seatAdapter.notifyDataSetChanged();
        refreshInfo();
    }

    private void refreshInfo() {
        tvTextGerbong.setText("Gerbong Saat ini : " + wagonCode + "-" + wagonNumber);

        String kursi = "";
        for (SelectedSeat selectedSeat : selectedSeatList) {
            kursi += selectedSeat.getWagonCode() + "-" + selectedSeat.getWagonNumber() + "/" + selectedSeat.getRow() + selectedSeat.getColumn() + ",";

        }
        tvTextSeat.setText("Gerbong dan Kursi terpilih : " + kursi.substring(0, kursi.length() - 1));
    }
}
