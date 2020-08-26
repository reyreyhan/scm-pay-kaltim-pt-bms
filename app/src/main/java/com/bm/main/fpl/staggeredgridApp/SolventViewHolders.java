package com.bm.main.fpl.staggeredgridApp;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.activities.HomeActivity;
import com.bm.main.fpl.constants.MenuActionCode;
import com.bm.main.fpl.listener.ShowModuleOnClickListener;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.shimmer;
//import com.squareup.picasso.Picasso;


public class SolventViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView countryName;
    ImageView countryPhoto, image_ribbon;
    //static SavePref savePref;
    public ShimmerFrameLayout frame_ribbon;


    public SolventViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        image_ribbon = itemView.findViewById(R.id.image_ribbon);
        countryName = itemView.findViewById(R.id.country_name);
        countryPhoto = itemView.findViewById(R.id.country_photo);
        frame_ribbon = itemView.findViewById(R.id.frame_ribbon);
        frame_ribbon.stopShimmerAnimation();
        shimmer.selectPreset(0, frame_ribbon);
    }

    @Override
    public void onClick(@NonNull View view) {
       // savePref = SavePref.getInstance(view.getContext());

        HomeActivity x = (HomeActivity) view.getContext();
        //Log.d("GRID  =>", "onClick: "+getPosition().toString());
        new Intent();
        Intent intent;
        // FragmentManager fm = x.getSupportFragmentManager();
        // FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (getAdapterPosition()) {
            case 0:
                Log.d("MENU", "onClick:" + PreferenceClass.getUser().getBelanja() + PreferenceClass.getAuth());
//                 intent = new Intent(view.getContext(), SBFWebActivity.class);
//                intent.putExtra("urlValue",PreferenceClass.getUser().getBelanja()+PreferenceClass.getAuth());
//                intent.putExtra("titleValue","Penjualan Barang");
//                intent.setData(Uri.parse(PreferenceClass.getUser().getBelanja()+PreferenceClass.getAuth()));
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    x.new_popup_alert(view.getContext(), "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {

//                    intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(PreferenceClass.getUser().getBelanja() + PreferenceClass.getAuth()));
//                    x.jualBarang();
                    new ShowModuleOnClickListener(x,MenuActionCode.JUAL_BARANG).onClick(view);
                    break;
                }
            case 1:
//                intent = new Intent(view.getContext(), PlnActivity.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.PLN).onClick(view);
                break;

            case 2:
//                intent = new Intent(view.getContext(), PulsaActivity.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.PULSA).onClick(view);
                break;


            case 3:

//                intent = new Intent(view.getContext(), PdamActivity.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//              //  intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.PDAM).onClick(view);
                break;

            case 4:
//dfvdfvd
//                intent = new Intent(view.getContext(), SBFWebActivity.class);
//                intent.putExtra("urlValue",PreferenceClass.getUser().getFastravel()+PreferenceClass.getAuth());
//                intent.putExtra("titleValue","Tiket & Travel");
//                intent.setData(Uri.parse(PreferenceClass.getUser().getFastravel()+PreferenceClass.getAuth()));

//                intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(PreferenceClass.getUser().getFastravel()+PreferenceClass.getAuth()));
//                String appPackage="com.fastravel.android";
//                String loginActivity=appPackage+".activities.LoginActivity";
//                boolean installed = x.checkAppInstall(appPackage);
//                if (installed) {
//                    intent = new Intent(loginActivity);
//                    intent.putExtra("id_outlet_from_sbf", PreferenceClass.getId());
//                    intent.putExtra("pin_outlet_from_sbf", PreferenceClass.getPin());
//                    intent.putExtra("key_outlet_from_sbf", PreferenceClass.getKey());
//
//                    intent.setPackage(appPackage);
//                   // startActivity(intent);
//                } else {
//                    intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="+appPackage));
//                    //startActivity(marketIntent);
//                }
               // x.callSBFFastravel();
               new ShowModuleOnClickListener(x, MenuActionCode.TIKET_TRAVEL).onClick(view);

                break;

            case 5:

//                intent = new Intent(view.getContext(), BpjsActivity.class);
//               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.BPJS).onClick(view);
                break;

            case 6:
//                intent = new Intent(view.getContext(), IndiHomeActivity.class);
//               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.INDIHOME).onClick(view);
                break;


            case 7:

//                intent = new Intent(view.getContext(), TeleponActivity.class);
//              //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.TELKOM).onClick(view);
                break;


            case 8:
//                intent = new Intent(view.getContext(), PascaBayarActivity.class);
//               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.PASCABAYAR).onClick(view);
                break;

            case 9:

//                intent = new Intent(view.getContext(), TvKabelActivity.class);
//               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.TVBERLANGGAN).onClick(view);
                break;
            case 10:

//                intent = new Intent(view.getContext(), CicilanActivity.class);
//               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.CICILAN).onClick(view);
                break;
            case 11:

//                intent = new Intent(view.getContext(), GameActivity.class);
//              //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.GAMEONLINE).onClick(view);
                break;

            case 12:
//                intent = new Intent(view.getContext(), TopUpEmoneyActivity.class);
//               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.EMONEY).onClick(view);
                break;
            case 13:
//                intent = new Intent(view.getContext(), AsuransiActivity.class);
//              //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.ASURANSI).onClick(view);
                break;
            case 14:

//                intent = new Intent(view.getContext(), KartuKreditActivity.class);
//             //   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.KARTUKREDIT).onClick(view);
                break;


            case 15:
//                intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(PreferenceClass.getUser().getPerbankan()+PreferenceClass.getAuth()));
                //   Log.d("MENU", "onClick:"+PreferenceClass.getUser().getPerbankan()+PreferenceClass.getAuth());
//                intent = new Intent(view.getContext(), SBFWebActivity.class);
//                intent.putExtra("urlValue",PreferenceClass.getUser().getPerbankan()+PreferenceClass.getAuth());
//                intent.putExtra("titleValue","Layanan Perbankan");

//                    intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(PreferenceClass.getUser().getPerbankan() + PreferenceClass.getAuth()));
//                    x.perbankan();
                    new ShowModuleOnClickListener(x, MenuActionCode.PERBANKAN).onClick(view);
                    break;


            case 16://PGN

//                intent = new Intent(view.getContext(), PgnActivity.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (intent.resolveActivity(x.getPackageManager()) != null) {
//                    x.startActivity(intent);
//
//                }
                new ShowModuleOnClickListener(x, MenuActionCode.PGN).onClick(view);
                break;



            case 17://PAJAK

                x.new_popup_alert(view.getContext(), "Info", "Akan Segera Hadir");
                return;


            case 18://SAMSAT

                x.new_popup_alert(view.getContext(), "Info", "Akan Segera Hadir");
                return;


            case 19:// BAYAR ONLINE

                x.new_popup_alert(view.getContext(), "Info", "Akan Segera Hadir");
                return;


                case 20:
                //   Log.d("MENU", "onClick:"+PreferenceClass.getUser().getLion_parcel()+PreferenceClass.getAuth());
//                intent = new Intent(view.getContext(), SBFWebActivity.class);
//                intent.putExtra("urlValue",PreferenceClass.getUser().getLion_parcel()+PreferenceClass.getAuth());
//                intent.putExtra("titleValue","Layanan Ekspedisi");
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    x.new_popup_alert(view.getContext(), "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
//                    intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(PreferenceClass.getUser().getLion_parcel() + PreferenceClass.getAuth()));

                  //  x.expedisi();
                    new ShowModuleOnClickListener(x, MenuActionCode.EXPEDISI).onClick(view);
                    break;
                }
            case 21:
                //  Log.d("MENU pinjam dana", "onClick:"+PreferenceClass.getUser().getPerbankan()+PreferenceClass.getAuth());
//                intent = new Intent(view.getContext(), SBFWebActivity.class);
//                intent.putExtra("urlValue",PreferenceClass.getUser().getPinjaman_dana()+PreferenceClass.getAuth());
//                intent.putExtra("titleValue","Pinjaman Dana");
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    x.new_popup_alert(view.getContext(), "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
//                    intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(PreferenceClass.getUser().getPinjaman_dana() + PreferenceClass.getAuth()));
//x.startActivity(intent);
                  //  x.pinjamDana();
                    new ShowModuleOnClickListener(x, MenuActionCode.PINJAMDANA).onClick(view);

                    break;
                }
            case 22:
                //  Log.d("MENU keagenan", "onClick:"+PreferenceClass.getUser().getKeagenan()+PreferenceClass.getAuth());
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    x.new_popup_alert(view.getContext(), "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
//                    intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(PreferenceClass.getUser().getKeagenan() + PreferenceClass.getAuth()));

                   // x.keagenan();
                    new ShowModuleOnClickListener(x, MenuActionCode.KEAGENAN).onClick(view);

//                intent = new Intent(view.getContext(), SBFWebActivity.class);
//                intent.putExtra("urlValue",PreferenceClass.getUser().getKeagenan()+PreferenceClass.getAuth());
//                intent.putExtra("titleValue","Keagenan");
                    break;
                }


            case 23:
                //   Log.d("MENU bayar barang", "onClick:"+PreferenceClass.getUser().getBayar()+PreferenceClass.getAuth());
//                             intent = new Intent(view.getContext(), SBFWebActivity.class);
//                intent.putExtra("urlValue",PreferenceClass.getUser().getBayar()+PreferenceClass.getAuth());
//                intent.putExtra("titleValue","Bayar Barang");
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    x.new_popup_alert(view.getContext(), "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
//                    intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(PreferenceClass.getUser().getBayar() + PreferenceClass.getAuth()));

                    //x.bayarBarang();
                    new ShowModuleOnClickListener(x, MenuActionCode.BAYARBARANG).onClick(view);
                    break;
                }
//            case 19:
////                intent = new Intent(Intent.ACTION_VIEW);
////                intent.setData(Uri.parse(PreferenceClass.getUser().getEdukasi()));
//                intent = new Intent(view.getContext(), SBFWebActivity.class);
//                intent.putExtra("urlValue",PreferenceClass.getUser().getEdukasi());
//                intent.putExtra("titleValue","Edukasi");
//                break;
            default:
                intent = new Intent(view.getContext(), HomeActivity.class);
                if (intent.resolveActivity(x.getPackageManager()) != null) {
                    x.startActivity(intent);

                }
                break;
        }

        //x.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
