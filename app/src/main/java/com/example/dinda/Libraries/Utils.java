package com.example.dinda.Libraries;

import android.app.Activity;
import android.text.Html;
import android.view.View;

import com.example.dinda.Popup.LayoutPopupError;
import com.example.dinda.Popup.LayoutPopupWarning;

public class Utils {

    //Fragments Tags
    public static final String LoginFragment = "Login_Fragment";


    public static void _alert(Activity activity, String _message) {
        LayoutPopupError lpe = new LayoutPopupError(activity);
        lpe.setCancelable(true);
        lpe.showDialog();
        lpe.tvTitle.setText("Kesalahan");
        lpe.tvKeterangan.setText(Html.fromHtml(_message));
        lpe.btnPositive.setVisibility(View.GONE);
        lpe.btnNegativie.setVisibility(View.VISIBLE);
        lpe.btnNegativie.setText("TUTUP");
        lpe.btnNegativie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lpe.dismissDialog();
            }
        });
    }

    public static void _alertClose(Activity activity, String _message) {
        LayoutPopupWarning lpe = new LayoutPopupWarning(activity);
        lpe.setCancelable(true);
        lpe.showDialog();
        lpe.tvTitle.setText("Informasi");
        lpe.tvKeterangan.setText(Html.fromHtml(_message));
        lpe.btnPositive.setVisibility(View.GONE);
        lpe.btnNegativie.setVisibility(View.VISIBLE);
        lpe.btnNegativie.setText("TUTUP");
        lpe.btnNegativie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lpe.dismissDialog();
                activity.finish();
            }
        });
    }
    
}
