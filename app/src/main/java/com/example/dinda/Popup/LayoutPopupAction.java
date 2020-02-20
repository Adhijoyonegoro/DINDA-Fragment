package com.example.dinda.Popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.dinda.R;


public class LayoutPopupAction extends AlertDialog.Builder {
    public TextView tvTitle, tvKeterangan;
    public Button btnDokumen, btnTambahAnggota, btnHapus, btnUbah, btnKirim;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    ImageButton ibClose;
//    ImageView ivTitle, ivList;


    public LayoutPopupAction(Context context) {
        super(context);
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = layoutInflater.inflate(R.layout.layout_popup_action_crud, null);

        ibClose = view.findViewById(R.id.ib_close);
        tvTitle = view.findViewById(R.id.tv_nama);
        tvKeterangan = view.findViewById(R.id.btn_tambah_anggota);
        btnDokumen = view.findViewById(R.id.btn_dokumen);
        btnTambahAnggota = view.findViewById(R.id.btn_tambah_anggota);
        btnHapus = view.findViewById(R.id.btn_hapus);
        btnUbah = view.findViewById(R.id.btn_ubah);
        btnKirim = view.findViewById(R.id.btn_kirim);
//        ivList = view.findViewById(R.id.listImgAlert);

//        Glide.with(context).asGif().load(R.drawable.asset_error).into(ivTitle);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            ivList.setBackground(context.getResources().getDrawable(R.color.color004));
//        }
//
//        ivList.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.color004) );


//        popup_title.setText("Pemberitahuan");
//        popup_keterangan.setText("Anda belum absen hari ini !");
//        popup_negative_action.setText("Nanti");
//        popup_positive_action.setText("Absen");

//        alertDialog.setCustomTitle(v1);
        builder.setView(view);
//        alertDialog.setCancelable(false);
        alertDialog = builder.create();

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
//        alertDialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void dismissDialog() {
        alertDialog.dismiss();
    }

    public void showDialog() {
        alertDialog.show();
    }
}
