package com.example.dinda.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinda.Libraries.DatabaseHelper;
import com.example.dinda.Model.Profile;
import com.example.dinda.R;

import java.util.ArrayList;
import java.util.List;


public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.MyProfileHolder>{

    Context mContext;
    List<Profile> mData;
    DatabaseHelper db;

    private List<Profile> ls_myprofile;
    ArrayList<Profile> dataModels;
    AdapterProfileDetail myprofileAdapter;
    public static RecyclerView recyclerview_myprofile_detail;


    private int i = -1;

    public AdapterProfile(Context mContext, List<Profile> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View penggarap;
        penggarap = LayoutInflater.from(mContext).inflate(R.layout.item_menu_profile, parent, false);
        MyProfileHolder pgrHolder = new MyProfileHolder(penggarap);
        db = new DatabaseHelper(mContext);
        return pgrHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyProfileHolder viewHolder, final int position) {
        final Profile dataModel = mData.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
//        final String no_invoice = db.getDataDetail(InvoiceTiketSchema.TABLE_NAME, InvoiceTiketSchema._ID, id_invoice, InvoiceTiketSchema.INVOICE_NUMBER);
//        final String jml_amount = db.getDataDetail(InvoiceTiketSchema.TABLE_NAME, InvoiceTiketSchema._ID, id_invoice, InvoiceTiketSchema.TOTAL_PRICE);

        final String str_modul = dataModel.getModule();
        viewHolder.txt_module.setText(str_modul);
//        viewHolder.txt_amount.setText(harga_rupiah);
//        viewHolder.txt_tanggal.setText(tanggal);
//        viewHolder.txt_jam.setText(jam.substring(0, 5));

        try {
            ls_myprofile = new ArrayList<>();
            myprofileAdapter = new AdapterProfileDetail(mContext,ls_myprofile);
            recyclerview_myprofile_detail.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerview_myprofile_detail.setAdapter(myprofileAdapter);

            try {

                DatabaseHelper DB_Helper = new DatabaseHelper(mContext);
                SQLiteDatabase db = DB_Helper.getReadableDatabase();
                final Cursor cur = db.rawQuery(
                        "SELECT PROFILE,NORMA FROM M_PROFILE " +
                                " WHERE MODUL " + " =  " + "'" + str_modul + "' ORDER BY URUT" ,null);

                cur.moveToPosition(0);
                dataModels = new ArrayList<>();
                for (int i = 0; i < cur.getCount(); i++) {
                    ls_myprofile.add(
                            new Profile(cur.getString(0),
                                    cur.getString(1),
                                    cur.getString(0),
                                    cur.getString(0),
                                    cur.getString(0))
                    );
                    cur.moveToNext();
                }

                cur.close();
                db.close();
            } catch (Exception ex) {
                Log.i("MY_PRILFE", ex.toString());
            }

        } catch (Exception ex) {
            Log.i("MY_PRILFE", ex.toString());
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyProfileHolder extends RecyclerView.ViewHolder {

        TextView txt_module;
        //        RecyclerView recyclerview_myprofile_detail;

//        TextView txt_amount;
//        TextView txt_tanggal;
//        TextView txt_jam;
//        TextView txt_status;
//        ImageView title_from_to_dots;
//        ImageView img_status;
//        CardView cek_detail;


        public MyProfileHolder(@NonNull View convertView) {
            super(convertView);

            txt_module = convertView.findViewById(R.id.txt_module);
            recyclerview_myprofile_detail = convertView.findViewById(R.id.recyclerview_list_myprofile_detail);

//            txt_amount = convertView.findViewById(R.id.txt_amount);
//            txt_tanggal = convertView.findViewById(R.id.txt_tanggal);
//            txt_jam = convertView.findViewById(R.id.txt_jam);
//            txt_status = convertView.findViewById(R.id.txt_status);
//            title_from_to_dots = convertView.findViewById(R.id.title_from_to_dots);
//            img_status = convertView.findViewById(R.id.img_status);
//            cek_detail = convertView.findViewById(R.id.cek_detail_transaksi_new);
        }
    }

}
