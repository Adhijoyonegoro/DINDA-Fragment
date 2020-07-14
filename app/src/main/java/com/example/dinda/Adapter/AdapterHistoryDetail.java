package com.example.dinda.Adapter;

import android.content.Context;
import android.opengl.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinda.Libraries.DatabaseHelper;
import com.example.dinda.Model.History;
import com.example.dinda.R;
import com.google.android.material.internal.VisibilityAwareImageButton;

import java.util.List;


public class AdapterHistoryDetail extends RecyclerView.Adapter<AdapterHistoryDetail.HistoryHolder>{

    Context mContext;
    List<History> mData;
    DatabaseHelper db;

    private int i = -1;

    public AdapterHistoryDetail(Context mContext, List<History> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public AdapterHistoryDetail.HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View penggarap;
        penggarap = LayoutInflater.from(mContext).inflate(R.layout.item_menu_history_detail, parent, false);
        HistoryHolder pgrHolder = new HistoryHolder(penggarap);
        db = new DatabaseHelper(mContext);
        return pgrHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder viewHolder, final int position) {

        final History dataModel = mData.get(position);
        final String str_date = dataModel.getTdate();
        final String str_kategori1 = dataModel.getKategori1();
        final String str_company = dataModel.getCompany_office();
        final String str_afd = dataModel.getAfd() == null ? "":dataModel.getAfd();
        final String str_save = dataModel.getSave();
        final String str_sent = dataModel.getSent();

        viewHolder.txt_kategori1.setText(str_date+" " +str_kategori1+" " +str_company+" " +str_afd);
        if( Integer.valueOf(str_save) > 0  ) {
            if( Integer.valueOf(str_sent) == 1 ) {
                viewHolder.txt_imgdone.setVisibility(View.GONE);
                viewHolder.txt_imgundone.setVisibility(View.GONE);
                viewHolder.txt_imgsent.setVisibility(View.VISIBLE);
            }else {
                viewHolder.txt_imgdone.setVisibility(View.VISIBLE);
                viewHolder.txt_imgundone.setVisibility(View.GONE);
                viewHolder.txt_imgsent.setVisibility(View.GONE);
            }
        }else {
            System.out.println(str_kategori1+" X "+str_date+" X "+str_save+" X "+str_sent);
            viewHolder.txt_imgdone.setVisibility(View.GONE);
            viewHolder.txt_imgundone.setVisibility(View.VISIBLE);
            viewHolder.txt_imgsent.setVisibility(View.GONE);
        }


//        viewHolder.txt_profile.setText(str_norma);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder {

        TextView txt_module;
        TextView txt_kategori1;
        ImageView txt_imgsent;
        ImageView txt_imgdone;
        ImageView txt_imgundone;
//        RecyclerView recyclerview_myprofile_detail;


        public HistoryHolder(@NonNull View convertView) {
            super(convertView);
            txt_module = convertView.findViewById(R.id.txt_module);
            txt_kategori1 = convertView.findViewById(R.id.txt_kategori1);
            txt_imgsent = convertView.findViewById(R.id.img_sent);
            txt_imgdone = convertView.findViewById(R.id.img_done);
            txt_imgundone = convertView.findViewById(R.id.img_undone);


        }
    }

}
