package com.bm.main.fpl.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
//import androidx.core.view.PagerAdapter;
//import androidx.core.view.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bm.main.scm.R;
import com.bm.main.fpl.adapters.OnBoard_Adapter;
import com.bm.main.fpl.models.OnBoardModel;
import com.bm.main.fpl.utils.PreferenceClass;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;


//import com.fastaccess.permission_WRITE_EXTERNAL_STORAGE.base.activity.BasePermissionActivity;
//import com.fastaccess.permission_WRITE_EXTERNAL_STORAGE.base.model.PermissionModel;
//import com.fastaccess.permission_WRITE_EXTERNAL_STORAGE.base.model.PermissionModelBuilder;
//
//import com.fastaccess.permission_WRITE_EXTERNAL_STORAGE.base.callback.OnPermissionCallback;

public class IntroductionActivity extends AppCompatActivity {// extends BasePermissionActivity {
@NonNull
String TAG=IntroductionActivity.class.getSimpleName();
//    public static final int PERMISSIONS_REQUESTS = 20;
//public static final String[] permissions = new String[]{
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.INTERNET,
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.READ_PHONE_STATE,
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.READ_CONTACTS,
//
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.ACCESS_FINE_LOCATION,
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.ACCESS_COARSE_LOCATION,
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.RECEIVE_SMS,
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.CAMERA,
////            Manifest.permission_WRITE_EXTERNAL_STORAGE.SEND_SMS,
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.READ_SMS,
//
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.WRITE_EXTERNAL_STORAGE,
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.CHANGE_NETWORK_STATE,
//
//
//
////            Manifest.permission_WRITE_EXTERNAL_STORAGE.CALL_PHONE,
//
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.SYSTEM_ALERT_WINDOW,
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.ACCESS_WIFI_STATE,
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.CHANGE_WIFI_STATE,
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.ACCESS_NETWORK_STATE,
//        Manifest.permission_WRITE_EXTERNAL_STORAGE.SYSTEM_ALERT_WINDOW,
//};
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private Button btnSkip, btnNext;
//   PreferenceClass preferenceClass;

//    private SharedPrefManager prefManager;
//    private Context context;

    private ArrayList<String> permissionsToRequest;
    @NonNull
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    @NonNull
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    public static DisplayMetrics displayMetrics;
    public Configuration config;
    private int current;

    private int dotsCount;
    private ImageView[] dots;
    private OnBoard_Adapter mAdapter;
    @NonNull
    ArrayList<OnBoardModel> onBoardItems=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runMemory();
//        context = getApplicationContext();
     //   prefManager = new SharedPrefManager(context);
        setContentView(R.layout.activity_introduction);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectAll().permitAll().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        //  client.detectFileUriExposure();
        StrictMode.setVmPolicy(builder.build());

        Resources res = this.getResources();
        config = res.getConfiguration();
        displayMetrics = res.getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        switch (displayMetrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                Log.d(TAG, "onCreate: base density low font "+config.fontScale+" screen"+config.screenLayout);
                config.fontScale = 0.93f;

                break;
            case DisplayMetrics.DENSITY_MEDIUM:

                if (displayMetrics.widthPixels >= 600) {
                    Log.d(TAG, "onCreate: base density medium font "+config.fontScale+" screen"+config.screenLayout);
                    config.fontScale = 1.3f;

                } else {
                    Log.d(TAG, "onCreate: base density medium font "+config.fontScale+" screen"+config.screenLayout);
                    config.fontScale = 0.9f;

                }


                break;
            case DisplayMetrics.DENSITY_HIGH:
                if (displayMetrics.widthPixels >= 600) {
                    Log.d(TAG, "onCreate: base density high font "+config.fontScale+" screen"+config.screenLayout);
                    config.fontScale = 1.7f;

                } else {
                    Log.d(TAG, "onCreate: base density high font "+config.fontScale+" screen"+config.screenLayout);
                    config.fontScale = 0.6f;

                }

                break;
            case DisplayMetrics.DENSITY_XHIGH:
                Log.d(TAG, "onCreate: base density xhigh font "+config.fontScale+" screen"+config.screenLayout);
                config.fontScale = 0.95f;


                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                Log.d(TAG, "onCreate: base density xxhigh font "+config.fontScale+" screen"+config.screenLayout);
                config.fontScale = 0.98f;


                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                Log.d(TAG, "onCreate: base density xxxhigh font "+config.fontScale+" screen"+config.screenLayout);
                config.fontScale = 1.0f;


            case DisplayMetrics.DENSITY_TV:
                if (displayMetrics.widthPixels >= 600) {
                    Log.d(TAG, "onCreate: base density TV font "+config.fontScale+" screen"+config.screenLayout);
                    config.fontScale = 1.4f;

                } else {
                    config.fontScale = 1.0f;

                }

                break;

            default:
                if (displayMetrics.widthPixels >= 600) {
                    Log.d(TAG, "onCreate: base density else font "+config.fontScale+" screen"+config.screenLayout);
                    config.fontScale = 17.5f;

                } else {
                    Log.d(TAG, "onCreate: base density else font "+config.fontScale+" screen"+config.screenLayout);
                    config.fontScale = 1.0f;
                }

        }
        config.setTo(getResources().getConfiguration());
        //  Configuration.setTo(getResources().getConfiguration());
        config.locale = new Locale("in", "ID");
        onConfigurationChanged(config);



        // Making notification bar transparent
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
//        preferenceClass=new PreferenceClass(this);

        viewPager = findViewById(R.id.view_pager);
        dotsLayout =  findViewById(R.id.layoutDots);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext =  findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                R.layout.welcome_slide5,
               // R.layout.welcome_slide6,
                R.layout.welcome_slide7,
                R.layout.welcome_slide8
        };

        // adding bottom dots
        addBottomDots();

        // making notification bar transparent
        changeStatusBarColor();

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

//        btnSkip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                launchHomeScreen();
//            }
//        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                Runtime.getRuntime().gc();
              //  Runtime.getRuntime().runFinalization();

                current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });




    }

    private void runMemory() {
        int size =(1024*1024* 40);//Integer.MAX_VALUE;
        int factor = 10;
//        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
//        for (int i=1; i <= 100; i++) {
        try {
            while (true) {
                try {
                    Log.d(TAG,"Trying to allocate " + getFileSize(size) );
//                    byte[] bytes = new byte[size];

                    Runtime.getRuntime().gc();
                    Log.d(TAG,"Succeed! "+getFileSize(Runtime.getRuntime().freeMemory()));
                    break;
                } catch (Exception e) {
//                e.printStackTrace();
                    Log.d(TAG, "runOutOfMemory: "+e.toString());
                } catch (OutOfMemoryError e) {
                    Log.d(TAG, "runOutOfMemory: OOME .. Trying again with 10x less "+getFileSize(size /= factor));
                    size /= factor;
//                       byte[] bytes = new byte[size];
                    Runtime.getRuntime().gc();
//                MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
//                long maxMemory = heapUsage.getMax() / MEGABYTE;
//                long usedMemory = heapUsage.getUsed() / MEGABYTE;
//                System.out.println(i + " : Memory Use :" + usedMemory + "M/" + maxMemory + "M");
                }
            }
        }catch (RuntimeException e){

            Log.d(TAG, "runOutOfMemory: "+e.toString());
            Log.d(TAG, "runOutOfMemory: ampun memory habis");
        }
    }


    private void addBottomDots() {
        dotsCount = layouts.length;//MyViewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];


//        TextView[] dots = new TextView[layouts.length];
//        //dots.setId(currentPage);
//
//        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
//        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
//
//        int[] colorsActive={"#efa022"};
//        int[] colorsInactive={"#95d8c6"};

//        dotsLayout.removeAllViews();
//        for (int i = 0; i < dots.length; i++) {
//            dots[i] = new TextView(this);
//            dots[i].setText(Html.fromHtml("&#8226;"));
//            dots[i].setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
//            dots[i].setTextSize(20);
//            dots[i].setTextColor(colorsInactive[currentPage]);
//
//            dotsLayout.addView(dots[i]);
//        }
//
//        if (dots.length > 0)
//            dots[currentPage].setTextColor(colorsActive[currentPage]);


        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(IntroductionActivity.this, R.drawable.non_selected_item_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(6, 0, 6, 0);

            dotsLayout.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(IntroductionActivity.this, R.drawable.selected_item_dot));

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
       // prefManager.setFirstTimeLaunch(false);
       // SavePref.getInstance(IntroductionActivity.this).setFirstTimeLaunch(false);
        //myViewPagerAdapter.mShimmerViewContainer.stopShimmerAnimation();
        //PreferenceClass.putBoolean("firstStart", false);
      //  prefManager.setFirstTimeLaunch(false);
        PreferenceClass.putBoolean("firstStart", false);
        startActivity(new Intent(IntroductionActivity.this, LoginActivity.class));
        finish();

    }

    //	viewpager change listener
    @NonNull
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            for (int i = 0; i < dotsCount; i++) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(IntroductionActivity.this, R.drawable.non_selected_item_dot));
            }

            dots[position].setImageDrawable(ContextCompat.getDrawable(IntroductionActivity.this, R.drawable.selected_item_dot));



            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
               // btnNext.setText(getString(R.string.start));
                btnNext.setText("Mulai");
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText("Berikutnya");
                btnSkip.setVisibility(View.GONE);
            }
//            if(position==4){
//
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                    // Show alert dialog to the user saying da separate permission_WRITE_EXTERNAL_STORAGE is needed
//                    // Launch the settings activity if the user prefers
//                    Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                    startActivity(myIntent);
//                }
//            }
//            if(position==5){
//
//                request();
//            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            Log.d(TAG, "onPageScrolled: "+arg0+" "+arg1+" "+arg2);
            runMemory();
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
           // Runtime.getRuntime().gc();
          //  Runtime.getRuntime().runFinalization();
            Log.d(TAG, "onPageScrollStateChanged: "+arg0);
            runMemory();
                    

        }
    };


    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
       // public ShimmerFrameLayout mShimmerViewContainer;
       MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
           // mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
           // mShimmerViewContainer.startShimmerAnimation();
            FrameLayout linCurve=view.findViewById(R.id.linCurve);
            float heightDp = (getResources().getDisplayMetrics().heightPixels * 2) / 6;
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) linCurve.getLayoutParams();


            lp.height = (int) heightDp;
            linCurve.setMinimumHeight(lp.height);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }


    }

private void request(){
//    int permission_WRITE_EXTERNAL_STORAGE = ActivityCompat.checkSelfPermission(this, Manifest.permission_WRITE_EXTERNAL_STORAGE.READ_PHONE_STATE);
//
//    int permission_ACCESS_FINE_LOCATION = ActivityCompat.checkSelfPermission(this, Manifest.permission_WRITE_EXTERNAL_STORAGE.ACCESS_FINE_LOCATION);
//    int permission_ACCESS_COARSE_LOCATION = ActivityCompat.checkSelfPermission(this, Manifest.permission_WRITE_EXTERNAL_STORAGE.ACCESS_COARSE_LOCATION);
//    int permission_READ_EXTERNAL_STORAGE = 0;
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//        permission_READ_EXTERNAL_STORAGE = ActivityCompat.checkSelfPermission(this, Manifest.permission_WRITE_EXTERNAL_STORAGE.READ_EXTERNAL_STORAGE);
//    }
//    int permission_READ_SMS = ActivityCompat.checkSelfPermission(this, Manifest.permission_WRITE_EXTERNAL_STORAGE.READ_SMS);
//    int permission_READ_PHONE_STATE = ActivityCompat.checkSelfPermission(this, Manifest.permission_WRITE_EXTERNAL_STORAGE.WRITE_EXTERNAL_STORAGE);
//    int permission_BLUETOOTH_PRIVILEGED = 0;
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//        permission_BLUETOOTH_PRIVILEGED = ActivityCompat.checkSelfPermission(this, Manifest.permission_WRITE_EXTERNAL_STORAGE.BLUETOOTH_PRIVILEGED);
//    }
//    int permission_READ_CONTACTS = ActivityCompat.checkSelfPermission(this, Manifest.permission_WRITE_EXTERNAL_STORAGE.READ_CONTACTS);
//    int permission_CAMERA = ActivityCompat.checkSelfPermission(this, Manifest.permission_WRITE_EXTERNAL_STORAGE.CAMERA);
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//
//            && permission_WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED
//            && permission_ACCESS_FINE_LOCATION != PackageManager.PERMISSION_GRANTED
//            && permission_ACCESS_COARSE_LOCATION != PackageManager.PERMISSION_GRANTED
//            && permission_READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED
//            && permission_READ_SMS != PackageManager.PERMISSION_GRANTED
//            && permission_READ_PHONE_STATE != PackageManager.PERMISSION_GRANTED
//            && permission_BLUETOOTH_PRIVILEGED != PackageManager.PERMISSION_GRANTED
//            && permission_READ_CONTACTS != PackageManager.PERMISSION_GRANTED
//            && permission_CAMERA != PackageManager.PERMISSION_GRANTED
//
//            ) {
//        //  checkDrawOverlayPermission();
//        requestPermissions(permissions, PERMISSIONS_REQUESTS);
//
//
//    }
//
//
//
//}

    //permissions.add(Manifest.permission_WRITE_EXTERNAL_STORAGE.SYSTEM_ALERT_WINDOW);
//    permissions.add(Manifest.permission.READ_PHONE_STATE);
//    permissions.add(Manifest.permission.CAMERA);
//    permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//    permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//    permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//    permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//    permissions.add(Manifest.permission.SEND_SMS);
//    permissions.add(Manifest.permission.CALL_PHONE);
//    permissions.add(Manifest.permission.READ_SMS);
//    permissions.add(Manifest.permission.RECEIVE_SMS);
//    permissions.add(Manifest.permission.GET_ACCOUNTS);

//    permissions.add(Manifest.permission.READ_CONTACTS);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        permissions.add(Manifest.permission.BLUETOOTH_PRIVILEGED);
    }


   // requestPermissions(permissions, PERMISSIONS_REQUESTS);

    permissionsToRequest = findUnAskedPermissions(permissions);
    //get the permissions we have asked for before but are not granted..
    //we will store this in da global list to access later.


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        if (checkDrawOverlayPermission()) {
//            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
//        }

        if (permissionsToRequest.size() > 0)
            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
//        if(!hasPermissions(this, permissionsToRequest.toArray(new String[permissionsToRequest.size()]))){
//            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
//        }

    }

}
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;
//    private void checkDrawOverlayPermission() {
//        if (checkDrawOverlayPermission()) {
//            startService(new Intent(this, PowerButtonService.class));
//        }
//    }

    public boolean checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }
    @NonNull
    private ArrayList<String> findUnAskedPermissions(@NonNull ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    //@TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel(
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    requestPermissions(permissionsRejected.toArray(new String[0]), ALL_PERMISSIONS_RESULT);
                                                    break;
//                                                case DialogInterface.BUTTON_NEGATIVE:
//                                                    // proceed with logic by disabling the related features or quit the app.
//                                                    break;
                                            }

                                        }
                                    });
                            return ;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(IntroductionActivity.this)
                .setMessage("Untuk kelancaran aplikasi. Mohon ijinkan semua akses.").setCancelable(true)
                .setPositiveButton("OK", okListener)
                //.setNegativeButton("Cancel", null)
                .create()
                .show();
    }


//    @TargetApi(Build.VERSION_CODES.M)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
//            if (!Settings.canDrawOverlays(this)) {
//                // You don't have permission_WRITE_EXTERNAL_STORAGE
//                checkPermission();
//
//            }
//            else
//            {
//                //do as per your logic
//            }
//
//        }
//
//    }
//    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;
//    public void checkPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(this)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
//            }
//        }
//    }

    public static boolean hasPermissions(@Nullable Context context, @Nullable String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @NonNull
    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}
