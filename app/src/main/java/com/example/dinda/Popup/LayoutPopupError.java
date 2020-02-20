package com.example.dinda.Popup;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.dinda.R;


public class LayoutPopupError extends AlertDialog.Builder {
    public TextView tvTitle, tvKeterangan;
    public Button btnNegativie, btnPositive;
    AlertDialog.Builder alertDialog;
    AlertDialog alertDialog1;
    ImageView ivTitle, ivList;


    public LayoutPopupError(Context context) {
        super(context);
        alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(false);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v1 = layoutInflater.inflate(R.layout.layout_popup_title, null);
        View v2 = layoutInflater.inflate(R.layout.layout_popup_action, null);

        tvTitle = v1.findViewById(R.id.popup_title);
        tvKeterangan = v2.findViewById(R.id.popup_keterangan);
        btnNegativie = v2.findViewById(R.id.popup_negative_action);
        btnPositive = v2.findViewById(R.id.popup_positive_action);
        ivTitle = v1.findViewById(R.id.imgAlert);
        ivList = v1.findViewById(R.id.listImgAlert);

        Glide.with(context).asGif().load(R.drawable.asset_error).into(ivTitle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ivList.setBackground(context.getResources().getDrawable(R.color.color004));
        }

        ivList.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.color004) );


//        popup_title.setText("Pemberitahuan");
//        popup_keterangan.setText("Anda belum absen hari ini !");
//        popup_negative_action.setText("Nanti");
//        popup_positive_action.setText("Absen");

        alertDialog.setCustomTitle(v1);
        alertDialog.setView(v2);
//        alertDialog.setCancelable(false);
        alertDialog1 = alertDialog.create();
//        alertDialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void dismissDialog() {
        alertDialog1.dismiss();
    }

    public void showDialog() {
        alertDialog1.show();
    }
}
