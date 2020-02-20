package com.example.dinda.Popup;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dinda.R;

public class LayoutPopupLoading {
    public ImageView ivLoading;
    public ImageView ivList;
    public Button btnClose;
    public LinearLayout llMessage;
    public TextView tvMessage;
    public Dialog dLoading;

    public LayoutPopupLoading(Activity activity) {
        dLoading = new Dialog(activity);
        dLoading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dLoading.setCancelable(false);
        dLoading.setContentView(R.layout.dialog_progress_bar);
        dLoading.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

//        final ProgressBar text = (ProgressBar) dLoading.findViewById(R.id.pb_loading);
//        tvLoading = dLoading.findViewById(R.id.tv_loading);
        ivLoading = (ImageView) dLoading.findViewById(R.id.iv_loading);
        Glide.with(activity).asGif().load(R.drawable.asset_loading).into(ivLoading);
        btnClose = (Button) dLoading.findViewById(R.id.btn_close);
        llMessage = (LinearLayout) dLoading.findViewById(R.id.ll_message);
        ivList = (ImageView) dLoading.findViewById(R.id.listImgAlert);
        tvMessage = (TextView) dLoading.findViewById(R.id.tv_message);
        llMessage.setVisibility(View.INVISIBLE);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
//        tvLoading.setText("0 %");

//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                statusData = 0;
//                persentase = 0;
//                dAll = 0;
//                dSuccess = 0;
//                dFailed = 0;
//                Glide.with(v).asGif().load(R.drawable.asset_loading).into(ivLoading);
//                dLoading.dismiss();
////                tvMessage.setText(Html.fromHtml("Tunggu sedang <br/>menyimpan ke server..."));
//                getPreview();
//                llMessage.setVisibility(View.INVISIBLE);
//            }
//        });

        Window window = dLoading.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void dismissDialog() {
        dLoading.dismiss();
    }

    public void showDialog() {
        dLoading.show();
    }
}
