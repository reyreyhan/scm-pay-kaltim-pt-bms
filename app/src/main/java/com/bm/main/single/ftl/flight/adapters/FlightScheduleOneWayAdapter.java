package com.bm.main.single.ftl.flight.adapters;

import android.content.Context;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bm.main.fpl.templates.ticketview.TicketView;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.flight.models.FlightDataModelClasses;
import com.bm.main.single.ftl.utils.utilBand;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//import com.bm.main.single.ftl.core_template.buttons.FancyButton;

public class FlightScheduleOneWayAdapter extends RecyclerView.Adapter<FlightScheduleOneWayAdapter.ViewHolder> implements Filterable {
    public static final String TAG = FlightScheduleOneWayAdapter.class.getSimpleName();
    private Context context;
    public ArrayList<FlightDataModelClasses> mDisplayList;//= new ArrayList<>();//= Collections.emptyList();;
    public ArrayList<FlightDataModelClasses> mDisplayListFilter;// = new ArrayList<>();
    private final LayoutInflater inflater;
    public boolean check;

    OnClickListener listener;
    private int checked;

    View view;
    ViewHolder holder;

//     public FlightScheduleOneWayAdapter(){
//        // super();
//     }

    public FlightScheduleOneWayAdapter(Context context, ArrayList<FlightDataModelClasses> itemList, OnClickListener listener) {
//    public FlightScheduleOneWayAdapter(Context context, ArrayList<FlightDataModelClasses> itemList) {
        this.context = context;
        this.mDisplayList = itemList;
        this.mDisplayListFilter = itemList;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.flight_schedule_list_item, parent, false);

        holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final FlightDataModelClasses data = mDisplayListFilter.get(position);
//        String str = data.getAirlineName();
//        String[] strArray = str.split(" ");
//        StringBuilder builder = new StringBuilder();
//        for (String s : strArray) {
//            String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
//            builder.append(cap + " ");
//        }
//        holder.airLineName.setText(builder.toString());
        try {
               Log.d(TAG, "onBindViewHolder transit false: "+data.getDetailTitle().getJSONObject(0).getString("flightName"));
            String str;
            switch (data.getDetailTitle().getJSONObject(0).getString("flightCode").substring(0, 2)) {
                case "SL":
                    Log.d(TAG, "getSchedule: SL");
                    str="Thai Lion Air";
//                    flightDetailModel.setFlightCode("SL");
//                    flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPSL.png");
                    Glide.with(context).load("http://static.scash.bz/fastravel/asset/maskapai/TPSL.png").override(24, 24).fitCenter().into(holder.airlineLogo);
                    break;
                case "OD":
                    Log.d(TAG, "getSchedule: OD");
                    str="Malindo Air";
//                    flightDetailModel.setFlightCode("OD");
//                    flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPOD.png");
                    Glide.with(context).load("http://static.scash.bz/fastravel/asset/maskapai/TPOD.png").override(24, 24).fitCenter().into(holder.airlineLogo);
                    break;
                case "ID":
//                    flightDetailModel.setFlightName("Batik Air");
                    str="Batik Air";
//                    flightDetailModel.setFlightCode("ID");
//                    flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPID.png");
                    Glide.with(context).load("http://static.scash.bz/fastravel/asset/maskapai/TPID.png").override(24, 24).fitCenter().into(holder.airlineLogo);
                    break;
                case "IW":
//                    flightDetailModel.setFlightCode("IW");
                  //  flightDetailModel.setFlightName("Wings Air");
                    str="Wings Air";
//                    flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPIW.png");
                    Glide.with(context).load("http://static.scash.bz/fastravel/asset/maskapai/TPIW.png").override(24, 24).fitCenter().into(holder.airlineLogo);
                    break;
                case "JT":
//                    flightDetailModel.setFlightCode("JT");
//                    flightDetailModel.setFlightName("Lion Air");
                    str="Lion Air";
//                    flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPJT.png");
                    Glide.with(context).load("http://static.scash.bz/fastravel/asset/maskapai/TPJT.png").override(24, 24).fitCenter().into(holder.airlineLogo);
                    break;
                default:
                    Glide.with(context).load(data.getDetailTitle().getJSONObject(0).getString("flightIcon")).override(24, 24).fitCenter().into(holder.airlineLogo);
//                    flightDetailModel.setFlightIcon(data.getString("flightIcon"));
//                    flightDetailModel.setFlightName(data.getString("flightName"));
                    str=data.getDetailTitle().getJSONObject(0).getString("flightName");
//                    flightDetailModel.setFlightCode(data.getString("flightCode"));

                    break;
            }


            String[] strArray = str.split(" ");
            StringBuilder builder = new StringBuilder();
            for (String s : strArray) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
                builder.append(cap + " ");
            }
            holder.airLineName.setText(builder.toString());
            //  holder.airLineName.setTag(R.id.posisi, position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.airLineName.setTag(R.id.posisi, position);
        holder.durasi.setText(data.getDuration());
        //  Log.d(TAG, "onBindViewHolder: "+data.getFlightCode());

        if (!data.isTransit()) {
            holder.transit.setText("Langsung");
            holder.cityTransit.setVisibility(View.INVISIBLE);

//            try {
//                Log.d(TAG, "onBindViewHolder transit false: "+data.getDetailTitle().getJSONObject(0).getString("flightName"));
//                Glide.with(context).load(data.getDetailTitle().getJSONObject(0).getString("flightIcon")).override(30,30).fitCenter().into(holder.airlineLogo);
//                String str = data.getDetailTitle().getJSONObject(0).getString("flightName");
//                String[] strArray = str.split(" ");
//                StringBuilder builder = new StringBuilder();
//                for (String s : strArray) {
//                    String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
//                    builder.append(cap + " ");
//                }
//                holder.airLineName.setText(builder.toString());
//              //  holder.airLineName.setTag(R.id.posisi, position);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        } else {
            holder.transit.setText("Transit");
            holder.cityTransit.setVisibility(View.VISIBLE);
            holder.cityTransit.setText("(" + data.getCityTransit() + ")");
            // holder.airlineLogo.setVisibility(View.GONE);

//            try {
//                Log.d(TAG, "onBindViewHolder transit true: "+data.getDetailTitle().getJSONObject(0).getString("flightName"));
//               // Glide.with(context).load(data.getDetailTitle().getJSONObject(0).getString("flightIcon")).override(30,30).fitCenter().into(holder.airlineLogo);
//                Glide.with(context).load(data.getDetailTitle().getJSONObject(0).getString("flightIcon")).override(30,30).fitCenter().into(holder.airlineLogo);
//                String str = data.getDetailTitle().getJSONObject(0).getString("flightName");
//                String[] strArray = str.split(" ");
//                StringBuilder builder = new StringBuilder();
//                for (String s : strArray) {
//                    String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
//                    builder.append(cap + " ");
//                }
//                holder.airLineName.setText(builder.toString());
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
//        holder.airLineName.setTag(R.id.posisi, position);
//        Glide.with(context).load(data.getAirlineIcon()).override(30,30).fitCenter().into(holder.airlineLogo);
        final String[] seat = new String[data.getClassArr().length()];
        for (int i = 0; i < data.getDetailTitle().length(); i++) {
            try {
                holder.departureTime.setText(data.getDetailTitle().getJSONObject(0).getString("depart"));
                holder.arrivalTime.setText(data.getDetailTitle().getJSONObject(data.getDetailTitle().length() - 1).getString("arrival"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (data.getPrice() > 0) {
            check = false;
            holder.price.setVisibility(View.VISIBLE);
            holder.labelPrice.setVisibility(View.VISIBLE);
            holder.checkPrice.setVisibility(View.GONE);
            holder.price.setText(utilBand.formatRupiah(data.getPrice()).replace(",00", ""));
        } else {
            check = true;
            holder.price.setVisibility(View.GONE);
            holder.labelPrice.setVisibility(View.GONE);
            holder.checkPrice.setVisibility(View.VISIBLE);
            holder.checkPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // holder.ticketView.setBackground(ContextCompat.getDrawable(context,R.drawable.selector_button_grey_item_pressed));
                    listener.onClickCheckPrice(data, seat, check, holder.departureTime.getText().toString(), holder.arrivalTime.getText().toString());
                }
            });
        }
        //    LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;
        for (int i = 0; i < data.getClassArr().length(); i++) {
            try {
                seat[i] = data.getClassArr().getJSONArray(i).getJSONObject(0).getString("seat").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        final String[] seat = new String[data.getClasses().length];
//        for (int i = 0; i < data.getDetailTitle().length; i++) {
//            FlightDetailTitleModel.DetailTitle detail_title = data.getDetailTitle()[i].getDetailTitles().get(i);
////            try {
//                holder.departureTime.setText(detail_title.getDepart());
//                holder.arrivalTime.setText(detail_title.getArrival());
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
//        }
//        int price=data.getClasses()[position].getClasses().get(position).getPrice();
//        if (price > 0) {
//            check = false;
//            holder.price.setVisibility(View.VISIBLE);
//            holder.labelPrice.setVisibility(View.VISIBLE);
//            holder.checkPrice.setVisibility(View.GONE);
//            holder.price.setText(utilBand.formatRupiah(price).replace(",00", ""));
//        }
//        else {
//            check = true;
//            holder.price.setVisibility(View.GONE);
//            holder.labelPrice.setVisibility(View.GONE);
//            holder.checkPrice.setVisibility(View.VISIBLE);
//        }
//        for (int i = 0; i < data.getClasses().length; i++) {
//          //  try {
//                seat[i] = data.getClasses()[i].getClasses().get(0).getSeat();
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
//        }
        holder.linMainTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((ScheduleFlightActivity) context).requestCheckPrice(c.getAirlineCode(), c.getAirlineName(), c.getFlightCode(), c.getDepartureDate(), c.getArrivalDate(), seat, check, position, c.getPrice(), holder.departureTime.getText().toString(), holder.arrivalTime.getText().toString(), c.getIsTransit(), c.getDetailTitle());
             // holder.ticketView.setBackground(ContextCompat.getDrawable(context,R.drawable.selector_button_grey_item_pressed));
                listener.onClickCheckPrice(data, seat, check, holder.departureTime.getText().toString(), holder.arrivalTime.getText().toString());
            }
        });
        holder.relDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onClickDetail(data.getDetailTitle(),holder.relDetail.getTag().toString(),data, seat, holder.departureTime.getText().toString(), holder.arrivalTime.getText().toString());
            }
        });
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @NonNull
            @Override
            protected FilterResults performFiltering(@NonNull CharSequence charSequence) {

                String charString = charSequence.toString();
                int price;
                String nama = "";
                int lowPrice = 0;
                int highPrice = 0;
                Log.d(TAG, "performFiltering: charString" + charString);
                if (charString.isEmpty()) {

                    mDisplayListFilter = mDisplayList;
                } else {


                    ArrayList<FlightDataModelClasses> filteredList = new ArrayList<>();
                    int index = 0;
                    for (FlightDataModelClasses produk : mDisplayList) {
                        price = produk.getPriceTemp();

                        try {
                            String[] cariValue = charString.split("-");
                            //  Log.d(TAG, "performFiltering: try " + cariValue.length);
                            if (cariValue.length == 3) {
//                           if(FormatUtil.checkNumberFromString(cariValue[0])) {
                                // Log.d(TAG, "performFiltering: masuk lenght 3");
                                lowPrice = Integer.parseInt(cariValue[1]);
//                           }else if(FormatUtil.checkNumberFromString(cariValue[1])){
                                highPrice = Integer.parseInt(cariValue[2]);
//                           }else if(FormatUtil.checkNumberFromString(cariValue[0])) {
                                nama = cariValue[0];

//                           }

                                if ((produk.getAirlineName().contentEquals(nama)) && (price >= lowPrice && price <= highPrice)) {
                                    filteredList.add(index, produk);
                                    Log.d(TAG, "performFiltering: masuk 3 " + nama + "&& " + lowPrice + "&&" + highPrice);
                                    index++;
                                }
                            }

                            if (cariValue.length == 2) {
                                //      Log.d(TAG, "performFiltering: masuk lenght 2");
                                lowPrice = Integer.parseInt(cariValue[0]);

                                highPrice = Integer.parseInt(cariValue[1]);
                                if (price >= lowPrice && price <= highPrice) {
                                    filteredList.add(index, produk);
                                    Log.d(TAG, "performFiltering: masuk 2 " + lowPrice + "&&" + highPrice);
                                    index++;
                                }
                            }

                            if (cariValue.length == 1) {
                                nama = cariValue[0];
                                if (produk.getAirlineName().contentEquals(nama)) {
                                    filteredList.add(index, produk);
                                    Log.d(TAG, "performFiltering: masuk 1 " + nama);
                                    index++;
                                }
                            }

                        } catch (Exception e) {
                            Log.d(TAG, "performFiltering: er " + e.toString());
                            nama = charString;
                            if (produk.getAirlineName().contentEquals(nama)) {
                                filteredList.add(index, produk);
                                index++;
                            }
                        }
//                        if ((produk.getAirlineName().equals(nama))|| (price>=lowPrice&&price<=highPrice) ) {
//                            filteredList.add(produk);
//                            Log.d(TAG, "performFiltering: masuk");
//                        }

//                        try {
//                            nama = produk.getDetailTitle().getJSONObject(0).getString("flightName");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                        if (nama.equals(charString)) {
//
//                            filteredList.add(produk);
//                        } else if (Integer.toString(price).equals(charString)) {
//                            if (price <= Integer.parseInt(charString)) {
//                                filteredList.add(produk);
//                            }
//                        } else if ((nama + "-" + price).equals(charString)) {
//                            Log.d(TAG, "performFiltering: masuk");
//                            if (price <= Integer.parseInt(charString.split("-")[1])) {
//                                Log.d(TAG, "performFiltering: masuk lagi" + charString.split("-")[1]);
//                                filteredList.add(produk);
//                            }
//                        }

                    }

                    mDisplayListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDisplayListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, @NonNull FilterResults filterResults) {
                Log.d(TAG, "publishResults: publishResults");
                mDisplayListFilter = (ArrayList<FlightDataModelClasses>) filterResults.values;
//                if (checked == 1) {
                //  updateList(clearListFromDuplicatePrice(mDisplayListFilter));
//                } else if (checked == 2) {
//                    updateList(clearListFromDuplicateTerpagi(mDisplayListFilter));
//                } else if (checked == 3) {
//                    updateList(clearListFromDuplicateDurasi(mDisplayListFilter));
//                }
                // notifyDataSetChanged();
                //  updateList(mDisplayListFilter);
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linMainTouch;
        public LinearLayout rlItemList;
        public RelativeLayout relDetail;
        public ImageView airlineLogo;
        public TextView airLineName;
        public TextView transit;
        public TextView cityTransit;
        public TextView departureTime, arrivalTime;
        public TextView durasi;
        public TextView price;
        public TextView labelPrice;
        public AppCompatButton checkPrice;
        TicketView ticketView;

        public ViewHolder(@NonNull final View view) {
            super(view);
            ticketView = view.findViewById(R.id.ticketView);
            rlItemList = view.findViewById(R.id.rlItemList);
            linMainTouch = view.findViewById(R.id.linMainTouch);
//            MaterialRippleLayout.on(ticketView).rippleOverlay(true)
//                    .rippleAlpha(0.2f).rippleDuration(100)
//                    .rippleColor(R.color.colorPrimary)
//                    .rippleHover(true)
//                    .create();
            airlineLogo = view.findViewById(R.id.airlinesLogo);
            airLineName = view.findViewById(R.id.airlinesName);
            departureTime = view.findViewById(R.id.departureTime);
            arrivalTime = view.findViewById(R.id.arrivalTime);
            durasi = view.findViewById(R.id.durasi);
            transit = view.findViewById(R.id.transit);
            cityTransit = view.findViewById(R.id.cityTransit);
            price = view.findViewById(R.id.airlinesPrice);
            labelPrice = view.findViewById(R.id.labelAirlinesPrice);
            checkPrice = view.findViewById(R.id.airlinesCheckPrice);
            relDetail = view.findViewById(R.id.relDetail);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void updateList(ArrayList<FlightDataModelClasses> list) {
        mDisplayListFilter = list;
        //mDisplayList = list;
//        if (checked == 1) {
//           // Collections.sort(mDisplayListFilter, hargaTermurah);
//           // notifyDataSetChanged();
//            /// updateList(clearListFromDuplicatePrice(mDisplayListFilter));
//        } else if (checked == 2) {
//            Collections.sort(mDisplayListFilter, waktuBerangkatTerpagi);
//            //  updateList(clearListFromDuplicateTerpagi(mDisplayListFilter));
//            //notifyDataSetChanged();
//        }
        notifyDataSetChanged();
    }


    public void getFilterList() {

        if (checked == 1) {
            Collections.sort(mDisplayListFilter, hargaTermurah);
            notifyDataSetChanged();
           /// updateList(clearListFromDuplicatePrice(mDisplayListFilter));
        } else if (checked == 2) {
            Collections.sort(mDisplayListFilter, waktuBerangkatTerpagi);
          //  updateList(clearListFromDuplicateTerpagi(mDisplayListFilter));
            notifyDataSetChanged();
        }
//        else if (checked == 3) {
//            Collections.sort(mDisplayListFilter, hargaTermurah);
//                    updateList(clearListFromDuplicateDurasi(mDisplayListFilter));
//                }
    }

        public ArrayList<FlightDataModelClasses> getData() {
        return this.mDisplayList;
    }

    public ArrayList<FlightDataModelClasses> getDataFilter() {
        return this.mDisplayListFilter;
    }
    public void checked(int checked) {
        this.checked = checked;
    }


    @Override
    public int getItemCount() {
        //if (mDisplayListFilter != null) {
        return mDisplayListFilter.size();
        // }
        // return 0;
    }


    public interface OnClickListener {
        void onClickCheckPrice(FlightDataModelClasses data, String[] seat, boolean check, String departureTime, String arrivalTime);
        void onClickDetail(JSONArray detailTitle, String tag, FlightDataModelClasses data, String[] seat, String departureTime, String arrivalTime);
    }



    @NonNull
    public Comparator<FlightDataModelClasses> waktuBerangkatTerpagi = new Comparator<FlightDataModelClasses>() {
        public int compare(@NonNull FlightDataModelClasses app1, @NonNull FlightDataModelClasses app2) {
            String stringName1 = "", stringName2 = "";


            try {
                stringName1 = app1.getDetailTitle().getJSONObject(0).getString("depart").replace(":", "");
                stringName2 = app2.getDetailTitle().getJSONObject(0).getString("depart").replace(":", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                Log.d(TAG, "adapter compare waktuBerangkatTerpagi: " + stringName1 + " " + stringName2+" "+app1.getDetailTitle().getJSONObject(0).getString("flightCode"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return stringName1.compareTo(stringName2);
//            Log.d(TAG, "compare waktuBerangkatTerpagi: " + stringName1 + " " + stringName2);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    return Integer.compare(Integer.parseInt(stringName1), Integer.parseInt(stringName2));
//                } else {
//                    return ((Integer) Integer.parseInt(stringName1)).compareTo(Integer.parseInt(stringName2));
//                }

        }
    };

    @NonNull
    public Comparator<FlightDataModelClasses> hargaTermurah = new Comparator<FlightDataModelClasses>() {
        public int compare(@NonNull FlightDataModelClasses app1, @NonNull FlightDataModelClasses app2) {
            String stringName1 = String.valueOf(app1.getPriceTemp());
            String stringName2 = String.valueOf(app2.getPriceTemp());
            Log.d(TAG, "compare hargaTermurah: " + stringName1 + " " + stringName2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return Integer.compare(Integer.parseInt(stringName1), Integer.parseInt(stringName2));
            } else {
                return ((Integer) Integer.parseInt(stringName1)).compareTo(Integer.parseInt(stringName2));
            }
        }

    };

}
