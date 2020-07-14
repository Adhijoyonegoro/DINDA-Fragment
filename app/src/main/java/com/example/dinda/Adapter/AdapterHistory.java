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
import com.example.dinda.Model.History;
import com.example.dinda.R;

import java.util.ArrayList;
import java.util.List;


public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.HistoryHolder>{

    Context mContext;
    List<History> mData;
    DatabaseHelper db;

    private List<History> ls_history;
    ArrayList<History> dataModels;
    AdapterHistoryDetail historyAdapter;
    public static RecyclerView recyclerview_history_detail;
    
    private int i = -1;

    public AdapterHistory(Context mContext, List<History> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View penggarap;
        penggarap = LayoutInflater.from(mContext).inflate(R.layout.item_menu_history, parent, false);
        HistoryHolder pgrHolder = new HistoryHolder(penggarap);
        db = new DatabaseHelper(mContext);
        return pgrHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder viewHolder, final int position) {
        final History dataModel = mData.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
//        final String no_invoice = db.getDataDetail(InvoiceTiketSchema.TABLE_NAME, InvoiceTiketSchema._ID, id_invoice, InvoiceTiketSchema.INVOICE_NUMBER);
//        final String jml_amount = db.getDataDetail(InvoiceTiketSchema.TABLE_NAME, InvoiceTiketSchema._ID, id_invoice, InvoiceTiketSchema.TOTAL_PRICE);

        final String strTdate = dataModel.getTdate();
        viewHolder.txt_module.setText(strTdate);
//        viewHolder.txt_amount.setText(harga_rupiah);
//        viewHolder.txt_tanggal.setText(tanggal);
//        viewHolder.txt_jam.setText(jam.substring(0, 5));

        try {
            ls_history = new ArrayList<>();
            historyAdapter = new AdapterHistoryDetail(mContext,ls_history);
            recyclerview_history_detail.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerview_history_detail.setAdapter(historyAdapter);

            try {

                DatabaseHelper DB_Helper = new DatabaseHelper(mContext);
                SQLiteDatabase db = DB_Helper.getReadableDatabase();
                String sql = "SELECT TDATE, KATEGORI1, COMPANY_OFFICE, AFD, " +
                        "(SELECT COUNT(*) FROM T_KPI WHERE TDATE=A.TDATE AND KATEGORI1=A.KATEGORI1 AND COMPANY_OFFICE=A.COMPANY_OFFICE AND POS_CODE=A.POS_CODE) AS SAVE, " +
                        "(SELECT SENT FROM T_KPI WHERE TDATE=A.TDATE AND KATEGORI1=A.KATEGORI1 AND COMPANY_OFFICE=A.COMPANY_OFFICE AND POS_CODE=A.POS_CODE ) AS SENT " +
                        "FROM ( " +
                        "SELECT DISTINCT TDATE, A.KATEGORI1, A.COMPANY_OFFICE, A.AFD, POS_CODE FROM T_KPI A " +
                        "WHERE TDATE='"+strTdate+"' " +
                        "UNION " +
                        "SELECT DISTINCT '"+strTdate+"', A.KATEGORI1, A.COMPANY_OFFICE, A.AFD, POS_CODE FROM M_TEMPLATE A" +
                        ") A";
                System.out.println( sql );
                final Cursor cur = db.rawQuery( sql,null );
                cur.moveToPosition(0);
                dataModels = new ArrayList<>();
                for (int i = 0; i < cur.getCount(); i++) {
                    ls_history.add(
                            new History(cur.getString(0),
                                    cur.getString(1),
                                    cur.getString(2),
                                    cur.getString(3),
                                    cur.getString(4),
                                    cur.getString(5)
                                    )
                    );
                    cur.moveToNext();
                }

                cur.close();
                db.close();
            } catch (Exception ex) {
                Log.i("HISTORY", ex.toString());
            }

        } catch (Exception ex) {
            Log.i("HISTORY", ex.toString());
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder {

        TextView txt_module;
        //        RecyclerView recyclerview_myprofile_detail;

//        TextView txt_amount;
//        TextView txt_tanggal;
//        TextView txt_jam;
//        TextView txt_status;
//        ImageView title_from_to_dots;
//        ImageView img_status;
//        CardView cek_detail;


        public HistoryHolder(@NonNull View convertView) {
            super(convertView);

            txt_module = convertView.findViewById(R.id.txt_module);
            recyclerview_history_detail = convertView.findViewById(R.id.recyclerview_list_history_detail);

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
