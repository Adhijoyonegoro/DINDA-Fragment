package com.example.dinda.Tabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.dinda.Adapter.AdapterHistory;
import com.example.dinda.Adapter.AdapterProfile;
import com.example.dinda.Libraries.ApiStatus;
import com.example.dinda.Libraries.Config;
import com.example.dinda.Libraries.DatabaseHelper;
import com.example.dinda.Libraries.Imei;
import com.example.dinda.Libraries.UserSession;
import com.example.dinda.Libraries.Utils;
import com.example.dinda.Model.History;
import com.example.dinda.Model.Profile;
import com.example.dinda.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class HistoryActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView recyclerview_history;
    private List<History> ls_history;
    ArrayList<History> dataModels;
//    AdapterProfile myprofileAdapter;
    AdapterHistory historyAdapter;
    @BindView(R.id.btnSend)
    TextView tvTgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_history);
        db = new DatabaseHelper(HistoryActivity.this);
//        Cursor _cursor = db.getKPISyncStatus();
//        Log.e( "getKPISyncStatus:", DatabaseUtils.dumpCursorToString(_cursor) );
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar now = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.add(Calendar.DATE, -6);
        end.add(Calendar.DATE, +1);
        System.out.println(end.getTime());
        System.out.println(start.getTime());

        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            String tdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            db.insertDate( tdate );
            System.out.println(tdate);
        }
        String strNow = new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        db = new DatabaseHelper(HistoryActivity.this.getApplicationContext());
        recyclerview_history = findViewById(R.id.recyclerview_list_history);
        try {
            ls_history = new ArrayList<>();
            historyAdapter = new AdapterHistory(HistoryActivity.this,ls_history);
            recyclerview_history.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
            recyclerview_history.setAdapter(historyAdapter);

            try {
                DatabaseHelper DB_Helper = new DatabaseHelper(HistoryActivity.this);
                SQLiteDatabase db = DB_Helper.getReadableDatabase();
                final Cursor cur = db.rawQuery(
                        "SELECT TDATE as TDATE FROM M_DATE WHERE TDATE <= '"+strNow+"' ORDER BY TDATE DESC LIMIT 5", null);
                cur.moveToPosition(0);
                dataModels = new ArrayList<>();
                for (int i = 0; i < cur.getCount(); i++) {
                    ls_history.add(
                            new History(cur.getString(0),
                                    cur.getString(0),
                                    cur.getString(0),
                                    cur.getString(0),
                                    cur.getString(0),
                                    cur.getString(0))
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
//        ButterKnife.bind(this);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        ProfileFragment profileFragment = new ProfileFragment();
//
//        fragmentTransaction.replace(R.id.fl_profile, profileFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

    }

    @OnClick(R.id.btnSend)
    public void onViewClickedSend() {
        String _imei = Imei.getUniqueIMEIId(HistoryActivity.this);
        String url = Config.ApiURLKPI;
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();
        try {
            UserSession userSession = new UserSession(HistoryActivity.this);
            params.put("npk", userSession.getNPK());
            params.put("imei", _imei);
        } catch (Exception e){
            e.printStackTrace();
            Log.e("error", String.valueOf(e instanceof Exception));
        }
        Log.e("url:", url);
        Log.e("params:", Arrays.toString(new RequestParams[]{params}));

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int responseCode, Header[] headers, byte[] responseBody) {
//                    Log.e("status code", new String(responseBody));
//                    Log.e("status code", String.valueOf(statusCode));
                if (responseCode == 201) {
                    try {
                        String _status="success";
                        JSONObject _responseObj = new JSONObject(new String(responseBody));
//                            Log.e("responseBody", _responseObj.toString()+"-"+responseCode);
                        String _statusCode = _responseObj.getString("status");
//                            Log.e( "status", _statusCode );
                        Utils._alert( HistoryActivity.this, _statusCode );
                        if( _statusCode.equals("success")) {
                            JSONObject data = _responseObj.getJSONObject("data");
                        }
                        else {
                            switch (_statusCode) {
                                case "imei_ganda":
                                    _status = ApiStatus.imei_ganda;
                                    break;
                                case "invalid":
                                    _status = ApiStatus.register_invalid;
                                    break;
                            }
                            Utils._alert(HistoryActivity.this, _status);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("status code", String.valueOf(statusCode));
            }
        });
        Utils._alertClose( HistoryActivity.this, "Data Terkirim" );
    }

}
