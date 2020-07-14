package com.example.dinda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinda.Libraries.DatabaseHelper;
import com.example.dinda.Model.Profile;
import com.example.dinda.R;

import java.util.List;


public class AdapterProfileDetail extends RecyclerView.Adapter<AdapterProfileDetail.MyProfileHolder>{

    Context mContext;
    List<Profile> mData;
    DatabaseHelper db;

    private int i = -1;

    public AdapterProfileDetail(Context mContext, List<Profile> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View penggarap;
        penggarap = LayoutInflater.from(mContext).inflate(R.layout.item_menu_profile_detail, parent, false);
        MyProfileHolder pgrHolder = new MyProfileHolder(penggarap);
        db = new DatabaseHelper(mContext);
        return pgrHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyProfileHolder viewHolder, final int position) {

        final Profile dataModel = mData.get(position);

        final String str_modul = dataModel.getModule();
        final String str_norma = dataModel.getNorma();
        viewHolder.txt_norma.setText(str_modul);
        viewHolder.txt_profile.setText(str_modul+" "+str_norma);



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyProfileHolder extends RecyclerView.ViewHolder {

        TextView txt_profile;
        TextView txt_norma;
        RecyclerView recyclerview_myprofile_detail;


        public MyProfileHolder(@NonNull View convertView) {
            super(convertView);

            txt_profile = convertView.findViewById(R.id.txt_profile);
            txt_norma = convertView.findViewById(R.id.txt_norma);

        }
    }

}
