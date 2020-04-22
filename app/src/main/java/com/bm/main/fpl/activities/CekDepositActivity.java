package com.bm.main.fpl.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;

import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.PreferenceKey;
import com.bm.main.fpl.constants.RConfig;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.BaseObject;
import com.bm.main.fpl.models.ListBankModel;
import com.bm.main.fpl.models.SaldoModel;
import com.bm.main.fpl.templates.CurrencyEditText;
import com.bm.main.fpl.templates.easyphotopicker.DefaultCallback;
import com.bm.main.fpl.templates.easyphotopicker.EasyImage;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.Helper;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.fpl.utils.StringJson;
import com.bm.main.fpl.utils.Validate;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import timber.log.Timber;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR;

@Keep
public class CekDepositActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener,
        AdapterView.OnItemClickListener, ProgressResponseCallback, TextToSpeech.OnInitListener {

    private static final String TAG = CekDepositActivity.class.getSimpleName();
    private TextView mTextViewSaldo;
    private TextView mTextViewRefresh;
    private ImageView mImageViewUpload;
    private LinearLayout mUploadLin;
    private MaterialEditText mMaterialEditTextCatatan;
    private AppCompatButton mAppCompatButtonKonfirm;
    private LinearLayout mLinMain;
    private int isTap = 0;
    private String saldo = "0";
    private static final int REQUEST_EXTERNAL_STORAGE = 931;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private StorageReference storageRef, folderRef, imageRef;
    private TextView mTextView;
    String nameFile = "";
    String namaPathImage;
    private FirebaseStorage storage;
    private String[] list;
    private ListPopupWindow lpw;
    private MaterialEditText mTextViewBankName;
    private CurrencyEditText mMaterialEditTextNominal;
    private ListBankModel listBankModel;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_deposit);
        initView();
        init(1);
        textToSpeech = new TextToSpeech(this, this);
        logEventFireBase("Visit", "Cek Deposit", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        cekSaldo();
        if (savedInstanceState != null) {
            photos = (ArrayList<File>) savedInstanceState.getSerializable(PHOTOS_KEY);
        }

        EasyImage.configuration(this)
                .setImagesFolderName("FastPay")
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(true)
                .setAllowMultiplePickInGallery(false);

        storage = FirebaseStorage.getInstance("gs://toko-modern-fastpay");
        storageRef = storage.getReference();
        folderRef = storageRef.child("All_Image_Uploads_Bukti");
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cek Deposit");
        mTextView = findViewById(R.id.textView);
        mTextViewSaldo = (TextView) findViewById(R.id.textViewSaldo);
        mTextViewRefresh = (TextView) findViewById(R.id.textViewRefresh);
        mTextViewRefresh.setOnClickListener(this);
        mImageViewUpload = (ImageView) findViewById(R.id.imageViewUpload);
        mUploadLin = (LinearLayout) findViewById(R.id.lin_upload);
        mUploadLin.setOnClickListener(this);
        mMaterialEditTextCatatan = (MaterialEditText) findViewById(R.id.materialEditTextCatatan);
        mAppCompatButtonKonfirm = (AppCompatButton) findViewById(R.id.appCompatButtonKonfirm);
        mAppCompatButtonKonfirm.setOnClickListener(this);
        findViewById(R.id.button_upload_resume).setOnClickListener(this);
        mLinMain = (LinearLayout) findViewById(R.id.linMain);
        mTextViewBankName = (MaterialEditText) findViewById(R.id.textViewBankName);
        mMaterialEditTextNominal = (CurrencyEditText) findViewById(R.id.materialEditTextNominal);
        mTextViewBankName.setOnTouchListener(this);
        loadDataBank();

        //  list = new String[]{"Pilih Nama"};
        // listTitle = new String[]{"Tn", "Ny", "Nona"};
        lpw = new ListPopupWindow(this);
        lpw.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list));
        lpw.setAnchorView(mUploadLin);
        lpw.setModal(true);
        lpw.setOnItemClickListener(this);


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void loadDataBank() {
        listBankModel = gson.fromJson(PreferenceClass.getString(RConfig.ListBank, ""), ListBankModel.class);
        // list.addAll(listBankModel.getResponse_value());
        list = new String[listBankModel.getResponse_value().size()];

        for (int i = 0; i < listBankModel.getResponse_value().size(); i++) {
            list[i] = listBankModel.getResponse_value().get(i).getNama_bank();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewRefresh:
                runOnUiThread(() -> {
                    isTap = 1;
                    mProgressBar.setVisibility(View.VISIBLE);
                    cekSaldo();
                });
                break;
            case R.id.button_upload_resume:
                Helper.mProgressDialog.show();
                mUploadTask.resume();
                break;
            case R.id.lin_upload:
                if (verifyStoragePermissions()) {
                    EasyImage.openChooserWithGallery(CekDepositActivity.this, "Pilih Sumber Photo", 0);
                } else {
                    ActivityCompat.requestPermissions(CekDepositActivity.this, PERMISSIONS_STORAGE, RequestCode.ActionCode_GROUP_STORAGE);
                }
                break;
            case R.id.appCompatButtonKonfirm:
                if (nameFile.equals("")) {
                    mUploadLin.setAnimation(BaseActivity.animShake);
                    mUploadLin.startAnimation(BaseActivity.animShake);
                    showToast("Silahkan Upload Bukti Deposit Anda");
                    Device.vibrate(this);
                    return;
                }
                if (!Validate.checkEmptyEditText(mTextViewBankName, "Tidak Boleh Kosong")) {
                    mTextViewBankName.setAnimation(animShake);
                    mTextViewBankName.startAnimation(animShake);
                    Device.vibrate(this);
                    return;
                }
                if (mMaterialEditTextNominal.getText().toString().isEmpty()) {

                    mMaterialEditTextNominal.setAnimation(animShake);
                    mMaterialEditTextNominal.startAnimation(animShake);

                    mMaterialEditTextNominal.setError("Nominal Deposit Tidak Boleh Kosong");
                    Device.vibrate(this);
                    return;

                }
                if (mMaterialEditTextNominal.getText().equals("0") || mMaterialEditTextNominal.getText().equals("0,00")) {

                    mMaterialEditTextNominal.setAnimation(animShake);
                    mMaterialEditTextNominal.startAnimation(animShake);

                    mMaterialEditTextNominal.setError("Nominal Deposit Tidak Boleh Nol");
                    Device.vibrate(this);
                    return;
                }
                konfirmasi();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RequestCode.ActionCode_GROUP_STORAGE) {
            if (verifyStoragePermissions()) {
                EasyImage.openChooserWithGallery(CekDepositActivity.this, "Pilih Sumber Photo", 0);
            } else {
                showToast("Ijin tidak diberikan");
            }
        }
    }

    private void konfirmasi() {
        logEventFireBase("Konfirmasi", "Konfirmasi Cek Deposit " + mTextViewBankName.getText().toString() + " " + mMaterialEditTextNominal.getText().toString(), EventParam.EVENT_ACTION_REQUEST_KONFIRMASI, EventParam.EVENT_SUCCESS, TAG);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestDataUploadBukti(nameFile, mTextViewBankName.getText().toString(), mMaterialEditTextNominal.getText().toString().replace(".", "").replace(",00", ""), mMaterialEditTextCatatan.getText().toString()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(CekDepositActivity.this, jsonObject, ActionCode.REQUEST_KONFIRMASI_UPLOAD, this);
        ViewGroup parent = findViewById(R.id.contentHost);
        @SuppressLint("InflateParams") final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
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
    }

    private void cekSaldo() {
        StringJson stringJson = new StringJson(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestCekSaldo());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.CEK_SALDO, this);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Timber.d("onSuccess: %s", response.toString());

        if (actionCode == ActionCode.CEK_SALDO) {
            Timber.d("onSuccess: %s", response.toString());
            SaldoModel saldoModel = gson.fromJson(response.toString(), SaldoModel.class);
            if (saldoModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                saldo = saldoModel.getResponse_value();

                PreferenceClass.putString("saldo", FormatString.CurencyIDR(saldo));
                mTextViewSaldo.setText("Rp " + PreferenceClass.getString("saldo", "0"));
            }

            if (isTap == 1) {
                mProgressBar.setVisibility(View.GONE);
                if (PreferenceClass.getBoolean(PreferenceKey.isVoiceAssistant, true)) {
                    textToSpeech.speak("Saldo Anda sebesar " + saldo + " Rupiah. Tingkatkan terus transaksi Anda"
                            , TextToSpeech.QUEUE_FLUSH, null);
                    if (!textToSpeech.isSpeaking()) {
                        textToSpeech = new TextToSpeech(this, this);
                        System.out.println("tts restarted");
                        textToSpeech.speak("Saldo Anda sebesar " + saldo + " Rupiah. Tingkatkan terus transaksi Anda", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
        } else if (actionCode == ActionCode.REQUEST_KONFIRMASI_UPLOAD) {

            closeProgressBarDialog();
            BaseObject baseObject = gson.fromJson(response.toString(), BaseObject.class);
            if (baseObject.getResponse_code().equals(ResponseCode.SUCCESS)) {
                logEventFireBase("Konfirmasi", "Konfirmasi Deposit", EventParam.EVENT_ACTION_RESULT_KONFIRMASI, EventParam.EVENT_SUCCESS, TAG);

                new_popup_alertFinish(this, "Informasi", baseObject.getResponse_desc());
            } else {
                new_popup_alert(this, "Informasi", baseObject.getResponse_desc());
            }
        }
    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        Timber.d("onFailure: " + responseCode + " " + responseDescription);
        if (actionCode == ActionCode.REQUEST_KONFIRMASI_UPLOAD) {
            logEventFireBase("Konfirmasi", "Konfirmasi Deposit", EventParam.EVENT_ACTION_RESULT_KONFIRMASI, EventParam.EVENT_NOT_SUCCESS, TAG);

            closeProgressBarDialog();
        } else if (actionCode == ActionCode.CEK_SALDO) {
            if (isTap == 1) {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int ttsLang = textToSpeech.setLanguage(new Locale("id", "ID"));
            Timber.d("onInit: %s", ttsLang);
            if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                    || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                Timber.d("The Language is not supported!" + TextToSpeech.LANG_MISSING_DATA + " " + TextToSpeech.LANG_NOT_SUPPORTED);
                textToSpeech.setLanguage(Locale.US);
            } else {
                Timber.d("Language Supported.");
                textToSpeech.setLanguage(new Locale("id", "ID"));
            }
            Timber.d("Initialization success.");
        } else {
            Toast.makeText(this, "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private static final String PHOTOS_KEY = "easy_image_photos_list";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(@NotNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                Timber.d("onImagesPicked: %s", source.toString());
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA_IMAGE) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(CekDepositActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    void new_popup_alertFinish(@NonNull final Context context, String title, String pesan) {
        ViewGroup parent = findViewById(R.id.contentHost);
        @SuppressLint("InflateParams")
        View v = View.inflate(context, R.layout.dialog_header_response_layout, parent);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay_dark))
                .setTitle(title)
                .setMessage(pesan)
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(true);

        builder.addButton("Tutup", -1, ContextCompat.getColor(this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.cancel();
                ((AppCompatActivity) context).finish();

            }
        });

        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            builder.show();
        }
    }

    private void onPhotosReturned(List<File> returnedPhotos) {
        photos.clear();
        photos.addAll(returnedPhotos);

        Uri[] results;
        Bitmap bm = rotateBitmapOrientation(photos.get(0).getAbsolutePath());
        results = new Uri[]{getImageUri(bm, photos.get(0))};
        Timber.d("onPhotosReturned: %s", getFilePath(results[0]));
        namaPathImage = getFilePath(results[0]);
        uploadFromFile(getFilePath(results[0]));
    }

    private String getFilePath(Uri uri) {
        String filePath = null;

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            Cursor cursor = getContentResolver().query(uri,
                    new String[]{MediaStore.Images.Media.DATA}, null, null, null);

            if (null == cursor) {
                return null;
            }

            try {
                if (cursor.moveToNext()) {
                    filePath = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                }
            } finally {
                cursor.close();
            }
        }

        if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }

        return filePath;
    }

    private UploadTask mUploadTask;

    private void uploadFromFile(String path) {
        Uri file = Uri.fromFile(new File(path));
        final StorageReference imageRef = folderRef.child(file.getLastPathSegment());
        Timber.d("uploadFromFile: " + file + "  " + file.getLastPathSegment());
        nameFile = file.getLastPathSegment();
        StorageMetadata metadata = new StorageMetadata.Builder().setContentType("image/jpg").build();

        mUploadTask = imageRef.putFile(file, metadata);

        Helper.initProgressDialog(this);
        Helper.mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (dialogInterface, i) -> mUploadTask.cancel());
        Helper.mProgressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Pause", (dialogInterface, i) -> mUploadTask.pause());
        Helper.mProgressDialog.show();

        mUploadTask.addOnFailureListener(exception -> {
            Helper.dismissProgressDialog();
            showToast(String.format("Failure: %s", exception.getMessage()));
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Helper.dismissProgressDialog();
                findViewById(R.id.button_upload_resume).setVisibility(View.GONE);
                Timber.d("onSuccess: %s", imageRef.getDownloadUrl().toString());

                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mTextView.setText("Upload Sukses,Lanjutkan Konfirmasi");
                        Timber.d("onSuccess: %s", uri.toString());
                        Glide.with(CekDepositActivity.this).asBitmap().load(uri).sizeMultiplier(1).fitCenter().encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).override(150, 160).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NotNull Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                                mImageViewUpload.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                            }
                        });
                    }
                });
            }
        }).addOnProgressListener(taskSnapshot -> {
            int progress = (int) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
            Helper.setProgress(progress);
        }).addOnPausedListener(taskSnapshot -> {
            findViewById(R.id.button_upload_resume).setVisibility(View.VISIBLE);
            mTextView.setText(R.string.upload_paused);
        });
    }

    public Bitmap rotateBitmapOrientation(String photoFilePath) {
        int requiredSize = 800;
        Timber.d("rotateBitmapOrientation: %s", photoFilePath);
        // Create and configure BitmapFactory
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.LATITUDE, Double.parseDouble(PreferenceClass.getString("lat", "0")));
        values.put(MediaStore.Images.Media.LONGITUDE, Double.parseDouble(PreferenceClass.getString("long", "0")));
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.ORIENTATION, SCREEN_ORIENTATION_FULL_SENSOR);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, photoFilePath);

        getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);

        int width_tmp = bounds.outWidth, height_tmp = bounds.outHeight;
        int scale = 1;

        while (width_tmp / 2 >= requiredSize && height_tmp / 2 >= requiredSize) {
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = scale;
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        // Read EXIF Data
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        return Bitmap.createBitmap(bm, 0, 0, width_tmp, height_tmp, matrix, true);
    }

    private Uri getImageUri(Bitmap inImage, File file) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "photo", null);

        return Uri.parse(path);
    }

    private boolean verifyStoragePermissions() {
        int writePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        return writePermission == PackageManager.PERMISSION_GRANTED
                && readPermission == PackageManager.PERMISSION_GRANTED
                && cameraPermission == PackageManager.PERMISSION_GRANTED;
    }

    private ArrayList<File> photos = new ArrayList<>();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PHOTOS_KEY, photos);
    }

    @Override
    protected void onDestroy() {
        // Clear any configuration that was done!
        EasyImage.clearConfiguration(this);
        super.onDestroy();
        Helper.dismissProgressDialog();
        Helper.dismissDialog();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            lpw.show();
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = list[position];
        mTextViewBankName.setText(item);
        lpw.dismiss();
    }
}
