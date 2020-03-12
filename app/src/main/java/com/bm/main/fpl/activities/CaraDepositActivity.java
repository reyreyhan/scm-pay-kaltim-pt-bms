package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.core.widget.NestedScrollView;

import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.CaraDepositModel;
import com.bm.main.fpl.templates.htmltextview.HtmlTextView;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CaraDepositActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener,
        AdapterView.OnItemClickListener, ProgressResponseCallback {

    private static final String TAG = CaraDepositActivity.class.getSimpleName();
    private MaterialEditText mTextViewNamaKategori;
    private HtmlTextView mTextViewShowContent;
    private NestedScrollView mNestedScrollView;
    private AppCompatButton mAppCompatButtonDeposit;
    private CaraDepositModel caraDepositModel;
    private ArrayList<CaraDepositModel.Response_value> listCaraDeposit = new ArrayList<>();
    private TextView mTextViewJudul;
    private String[] list;
    private ListPopupWindow lpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cara_deposit);
        initView();
        init(1);
        logEventFireBase("Visit", "Cara Deposit", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);


    }

    private void loadData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestCaraDeposit());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(CaraDepositActivity.this, jsonObject, ActionCode.REQUEST_KONFIRMASI_UPLOAD, this);
        ViewGroup parent = findViewById(R.id.contentHost);
        @SuppressLint("InflateParams") final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cara Deposit");
        mTextViewNamaKategori = (MaterialEditText) findViewById(R.id.textViewNamaKategori);
        mTextViewShowContent = (HtmlTextView) findViewById(R.id.textViewShowContent);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        mAppCompatButtonDeposit = (AppCompatButton) findViewById(R.id.appCompatButtonDeposit);
        mAppCompatButtonDeposit.setOnClickListener(this);
        mTextViewJudul = (TextView) findViewById(R.id.textViewJudul);
        mTextViewNamaKategori.setOnTouchListener(this);
        loadData();
        lpw = new ListPopupWindow(this);

        lpw.setAnchorView(mTextViewJudul);
        lpw.setModal(true);
        lpw.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonDeposit:
                // TODO 19/10/22
                Intent intent = new Intent(CaraDepositActivity.this, DepositActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
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
//
        finish();
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        super.onSuccess(actionCode, response);
        closeProgressBarDialog();
        caraDepositModel = gson.fromJson(response.toString(), CaraDepositModel.class);
        listCaraDeposit.addAll(caraDepositModel.getResponse_value());
        list=new String[caraDepositModel.getResponse_value().size()];
        for(int i=0;i<caraDepositModel.getResponse_value().size();i++ ){
            list[i]=caraDepositModel.getResponse_value().get(i).getDesc_header();
        }
        lpw.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list));
        mTextViewNamaKategori.setText(caraDepositModel.getResponse_value().get(0).getDesc_header());
        mTextViewShowContent.setText(caraDepositModel.getResponse_value().get(0).getDesc_content());


    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        super.onFailure(actionCode, responseCode, responseDescription, throwable);
        closeProgressBarDialog();
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
        mTextViewNamaKategori.setText(item);
//        titleTemp = itemTemp;
        lpw.dismiss();
        mTextViewShowContent.setText(caraDepositModel.getResponse_value().get(position).getDesc_content());


    }
}
