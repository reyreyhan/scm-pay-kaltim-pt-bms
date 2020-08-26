package com.bm.main.single.ftl.ship.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatButton;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bm.main.fpl.templates.ticketview.TicketView;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.scm.R;
import com.bm.main.scm.SBFApplication;
import com.bm.main.single.ftl.ship.constants.ShipKeyPreference;
import com.bm.main.single.ftl.ship.models.ShipFareModel;
import com.bm.main.single.ftl.ship.models.ShipModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by sarifhidayat on 2019-06-26.
 **/
public class ShipScheduleAdapter extends RecyclerView.Adapter<ShipScheduleAdapter.ViewHolder> implements Filterable {
    public static final String TAG = ShipScheduleAdapter.class.getSimpleName();
    public ArrayList<ShipFareModel> mDisplayList = new ArrayList<>();//= Collections.emptyList();;
    public ArrayList<ShipFareModel> mDisplayListFilter = new ArrayList<>();
    LayoutInflater inflater;
    private ViewHolder mHolder;
    Context context;

    private OnClickScheduleShip listener;

    public ShipScheduleAdapter(Context context, ArrayList<ShipFareModel> itemList, OnClickScheduleShip listener) {
        this.context = context;
        this.mDisplayList = itemList;
        this.mDisplayListFilter = itemList;
        this.listener = listener;
        inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView;

        itemView = inflater.inflate(R.layout.ship_schedule_list_item_old, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final ShipFareModel shipFareModel = mDisplayListFilter.get(position);
        final ShipModel shipModel = shipFareModel.getShipModel();


        viewHolder.relRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shipFareModel.setExpand(!shipFareModel.isExpand());
                notifyDataSetChanged();
            }
        });
         Log.d(TAG, "onBindViewHolder isSelected : "+shipFareModel.isSelected()+" "+shipFareModel.getTagButton());
        if (shipFareModel.isSelected()) {
            // String tagString = (String) viewHolder.btnPesan.getTag();
            if (shipFareModel.getTagButton().equals("H")) {
                viewHolder.btnPesan.setText("TIDAK TERSEDIA");
                shipFareModel.setTagButton("H");
//        viewHolder.btnPesan.setTag(R.id.gender_ship_avail, "H");
                viewHolder.btnPesan.setBackground(ContextCompat.getDrawable(context, R.drawable.selector_button_grey_small_pressed));
            } else if (shipFareModel.getTagButton().equals("A")) {
                viewHolder.btnPesan.setText("TERSEDIA");
                // viewHolder.btnPesan.setTag("A");
                shipFareModel.setTagButton("A");
                viewHolder.btnPesan.setBackground(ContextCompat.getDrawable(context, R.drawable.selector_button_green_small_pressed));
            } else if (shipFareModel.getTagButton().equals("M")) {
                viewHolder.btnPesan.setText("TERSEDIA");
                viewHolder.btnPesan.setBackground(ContextCompat.getDrawable(context, R.drawable.selector_button_green_small_pressed));
                viewHolder.textViewInfoAvail.setVisibility(View.VISIBLE);
                viewHolder.textViewInfoAvail.setText("untuk penumpang pria");
                shipFareModel.setTagButton("M");
            } else if (shipFareModel.getTagButton().equals("F")) {
                viewHolder.btnPesan.setText("TERSEDIA");
//                    viewHolder.btnPesan.setTag("F");
                shipFareModel.setTagButton("F");
                viewHolder.btnPesan.setBackground(ContextCompat.getDrawable(context, R.drawable.selector_button_green_small_pressed));
                viewHolder.textViewInfoAvail.setVisibility(View.VISIBLE);
                viewHolder.textViewInfoAvail.setText("untuk penumpang wanita");


            }

        } else {
           //Log.d(TAG, "onBindViewHolder: " + shipFareModel.isSelected());
        }
        if (shipFareModel.isExpand()) {
            viewHolder.imageViewDown.setVisibility(View.VISIBLE);
            viewHolder.textViewRoute.setVisibility(View.VISIBLE);
            viewHolder.imageViewNext.setVisibility(View.GONE);
        } else {
            viewHolder.imageViewDown.setVisibility(View.GONE);
            viewHolder.textViewRoute.setVisibility(View.GONE);
            viewHolder.imageViewNext.setVisibility(View.VISIBLE);
        }
        viewHolder.textViewShipName.setText(shipModel.getSHIP_NAME() + " (" + shipModel.getSHIP_NO() + ") " + shipFareModel.getCLASS() + " / " + shipFareModel.getSUBCLASS());
//        viewHolder.textViewShipName.setText("Kelas "+shipFareModel.getCLASS() + " / " + shipFareModel.getSUBCLASS());

        String arvTime = shipModel.getARV_TIME();
        viewHolder.textViewArvTime.setText(arvTime.substring(0, 2) + ":" + arvTime.substring(2, 4));

        String depTime = shipModel.getDEP_TIME();
        viewHolder.textViewDepTime.setText(depTime.substring(0, 2) + ":" + depTime.substring(2, 4));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", SBFApplication.config.locale);
        SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", SBFApplication.config.locale);

        viewHolder.textViewPriceAdult.setText("Rp. " + String.format("%,.0f", shipFareModel.getFareDetailA().getTOTAL()));
//            tvPriceAnak.setText("Rp. " + String.format("%,.0f", shipFareModel.getFareDetailC().getTOTAL()));
        viewHolder.textViewPriceInfant.setText("Rp. " + String.format("%,.0f", shipFareModel.getFareDetailI().getTOTAL()));

        String[] routes = shipModel.getROUTE().split("/");

        try {
            String routeText = "";
            JSONObject obj = PreferenceClass.getJSONObject(ShipKeyPreference.portListData);

            JSONArray destinationJson = new JSONArray();
            // JSONArray destinationJson = new JSONArray(SavePref.getInstance(getApplicationContext()).getString("shipDestinationList"));
            try {

                destinationJson = obj.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < routes.length; i++) {
                String route = routes[i];
                if (route.length() < 3)
                    continue;

                if (route.length() > 3) {
                    route = route.split("-")[1];
                }

                for (int j = 0; j < destinationJson.length(); j++) {
                    JSONObject jsonObject = destinationJson.getJSONObject(j);
                    String code = jsonObject.getString("CODE");
                    String name = jsonObject.getString("NAME");

                    if (code.equals(route)) {
                        routeText += name + " - ";
                        break;
                    }
                }
            }
            viewHolder.textViewRoute.setText(routeText.substring(0, routeText.length() - 2));
        } catch (JSONException e) {
            e.printStackTrace();
            viewHolder.textViewRoute.setText("-");
        }


        try {
            viewHolder.textViewArvDate.setText(odf.format(sdf.parse(shipModel.getARV_DATE())));
            viewHolder.textViewDepDate.setText(odf.format(sdf.parse(shipModel.getDEP_DATE())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (shipFareModel.getShipAvailabilityF().getF().equals("0") && shipFareModel.getShipAvailabilityM().getM().equals("0")) {
            viewHolder.btnPesan.setText("TIDAK TERSEDIA");
            shipFareModel.setTagButton("H");
//        viewHolder.btnPesan.setTag(R.id.gender_ship_avail, "H");
            viewHolder.btnPesan.setBackground(ContextCompat.getDrawable(context, R.drawable.selector_button_grey_pressed));
        }
        listener.onClickScheduleShipFilter(shipModel,shipFareModel);
//        viewHolder.lin_main_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClickScheduleShip(shipFareModel);
//            }
//        });
        viewHolder.btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickScheduleShip(shipFareModel, shipModel, viewHolder);

            }
        });
        viewHolder.linMainTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickScheduleShip(shipFareModel, shipModel, viewHolder);

            }
        });
        viewHolder.relMainTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickScheduleShip(shipFareModel, shipModel, viewHolder);

            }
        });

    }

    @Override
    public int getItemCount() {
        if (mDisplayListFilter != null) {
            return mDisplayListFilter.size();
        }
        return 0;
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

                    mDisplayListFilter = mDisplayList;
                } else {

                    ArrayList<ShipFareModel> filteredList = new ArrayList<>();

                    for (ShipFareModel port : mDisplayList) {

                        if (port.getShipModel().getDEP_DATE().equals(charString)) {

                            filteredList.add(port);
                        }
                    }

                    mDisplayListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDisplayListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, @NonNull FilterResults filterResults) {
                mDisplayListFilter = (ArrayList<ShipFareModel>) filterResults.values;

                notifyDataSetChanged();


            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        // ViewGroup.LayoutParams params;
//             private final RelativeLayout.LayoutParams lp;
        TextView textViewShipName;
        TextView textViewArvDate;
        TextView textViewArvTime;
        TextView textViewDepDate;
        TextView textViewDepTime;
        TextView textViewPriceAdult;
        //        TextView textViewPriceAnak ;
        TextView textViewPriceInfant;
        TextView textViewRoute;
        public TextView textViewInfoAvail;
        //
        ImageView imageViewNext;
        ImageView imageViewDown;

        RelativeLayout relRoute;

        public LinearLayout lin_main_item;
       // public Constran cv;
        public AppCompatButton btnPesan;

        public FrameLayout frame_icon;

        LinearLayout linMainTouch;
        RelativeLayout relMainTouch;

        public ProgressBar progressBar;
        TicketView ticketView;

        //int posisi;
        public ViewHolder(@NonNull final View view) {
            super(view);
            this.setIsRecyclable(false);
          //  cv = view.findViewById(R.id.cv);
            ticketView = view.findViewById(R.id.ticketView);
            linMainTouch = view.findViewById(R.id.linMainTouch);
            relMainTouch = view.findViewById(R.id.relMainTouch);
            progressBar = view.findViewById(R.id.progressAvailShip);
            lin_main_item = view.findViewById(R.id.lin_main_item);



//            float factor = view.getResources().getDisplayMetrics().density;
//            int heightDp = (int)(view.getResources().getDisplayMetrics().heightPixels*factor);
////
//            lp = (RelativeLayout.LayoutParams) lin_main_item.getLayoutParams();
//           // params=()
//////        lpx = (CollapsingToolbarLayout.LayoutParams) lin_header.getLayoutParams();
////
////
//            lp.height = heightDp;
////            // lpx.height = heightDpx;
////
////            // carouselView.setMinimumHeight(lp.height);
////            ticketView.setMinimumHeight(lp.height);
////            float factor = view.getResources().getDisplayMetrics().density;
////            params.width = (int)(lin_main_item.getWidth() * factor);
////            params.height = (int)(lin_main_item.getHeight() * factor);
//            // params.gravity = Gravity.TOP;
//
//            ticketView.setMinimumHeight(lp.height);
//            MaterialRippleLayout.on(lin_main_item).rippleOverlay(true)
//                    .rippleAlpha(0.2f)
//                    //.rippleColor(0xFF585858)
//                    .rippleColor(R.color.colorPrimary)
//                    .rippleHover(true)
//                    .create();
            btnPesan = view.findViewById(R.id.btnPesan);
            frame_icon = view.findViewById(R.id.frame_icon);
            textViewShipName = view.findViewById(R.id.textViewShipName);
            textViewArvDate = view.findViewById(R.id.textViewArvDate);
            textViewArvTime = view.findViewById(R.id.textViewArvTime);
            textViewDepDate = view.findViewById(R.id.textViewDepDate);
            textViewDepTime = view.findViewById(R.id.textViewDepTime);
            textViewPriceAdult = view.findViewById(R.id.textViewPriceAdult);
//             textViewPriceAnak = view.findViewById(R.id.textViewPriceAnak);
            textViewPriceInfant = view.findViewById(R.id.textViewPriceInfant);
            textViewRoute = view.findViewById(R.id.textViewRoute);
            textViewInfoAvail = view.findViewById(R.id.textViewInfoAvail);
//
            imageViewNext = view.findViewById(R.id.imageViewNext);
            imageViewDown = view.findViewById(R.id.imageViewDown);

            relRoute = view.findViewById(R.id.relRoute);


        }
    }

    public void updateList(ArrayList<ShipFareModel> list) {

        mDisplayListFilter = list;
        mDisplayList = list;

        notifyDataSetChanged();
    }


    public ArrayList<ShipFareModel> getData() {
        return mDisplayList;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnClickScheduleShip {
        void onClickScheduleShip(ShipFareModel dataSchedule, ShipModel shipModel, ViewHolder viewHolder);

        void onClickScheduleShipFilter(ShipModel dataSchedule,ShipFareModel dataFareSchedule);
    }





}
