package com.example.dinda.Tabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dinda.Adapter.AdapterDashboard;
import com.example.dinda.BuildConfig;
import com.example.dinda.Libraries.Config;
import com.example.dinda.Libraries.DatabaseHelper;
import com.example.dinda.Libraries.Imei;
import com.example.dinda.Libraries.UserSession;
import com.example.dinda.Libraries.Utils;
import com.example.dinda.Model.ModelDashboard;
import com.example.dinda.Popup.LayoutPopupError;
import com.example.dinda.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import okhttp3.internal.Util;

public class DashboardActivity extends AppCompatActivity {

    private long backPressTime;
    private Context mContext = DashboardActivity.this;
    private AdapterDashboard adapterDashboard;
    private List<ModelDashboard> dashboardList;
    DatabaseHelper db;

    @BindView(R.id.txtDate)
    TextView tvTgl;
    @BindView(R.id.rv_dash)
    RecyclerView rvDash;
    @BindView(R.id.txtNamaMandor)
    TextView tvMandor;
    @BindView(R.id.txtIdMandor)
    TextView tvNPK;
    @BindView(R.id.txtPosTitle)
    TextView tvPosTitle;
    @BindView(R.id.txtSitecode)
    TextView tvSitecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String url = Config.ApiURLProfileTemplate+"/"+Imei.getUniqueIMEIId(DashboardActivity.this)+"/"+ BuildConfig.VERSION_CODE;
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();
        db = new DatabaseHelper(DashboardActivity.this);
        Log.e("url", url);

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int responseCode, Header[] headers, byte[] responseBody) {
//                Log.e("responseCode", String.valueOf(responseCode));
                if (responseCode == 201) {
                    try {
                        JSONObject _responseObj = new JSONObject(new String(responseBody));
//                        Log.e("responseBody", _responseObj.toString()+"-"+responseCode);
                        JSONObject _statusArray = _responseObj.getJSONObject("status");
                        Log.e( "status", _statusArray.getString("profile" ));
                        if(  _statusArray.getString("profile" ).equals( "notfound" ) || _statusArray.getString("template" ).equals( "notfound" )) {
//                            Utils._alert( DashboardActivity.this,  );
                            LayoutPopupError lpe = new LayoutPopupError( DashboardActivity.this );
                            lpe.setCancelable(true);
                            lpe.showDialog();
                            lpe.tvTitle.setText("Kesalahan");
                            lpe.tvKeterangan.setText(Html.fromHtml( "File kegiatan dan profil tidak ditemukan atau gadget Anda tidak terdaftar." ));
                            lpe.btnPositive.setVisibility(View.GONE);
                            lpe.btnNegativie.setVisibility(View.VISIBLE);
                            lpe.btnNegativie.setText("TUTUP");
                            lpe.btnNegativie.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    lpe.dismissDialog();
//                                    finish();
                                }
                            });
                        }

                        JSONArray _profileArray = _responseObj.getJSONArray("profile");
                        Log.i( "profile:", Arrays.toString(new JSONArray[]{_profileArray}) );
                        JSONArray _templateArray = _responseObj.getJSONArray("template");
                        Log.e( "template:", Arrays.toString(new JSONArray[]{_templateArray}));
                        String[] _paramTemplate = new String[14];
                        db.deleteTemplate();
                        for (int i = 0; i < _templateArray.length(); i++) {
                            JSONObject _templateObj = _templateArray.getJSONObject(i);
//                            Log.i( "insert", String.valueOf(i));
//                            for( int j =0; j < _paramTemplate.length; j++ )
//                                _paramTemplate[j] = "ZZZ";
                            _paramTemplate[0] = _templateObj.getString("COMPANY_OFFICE");
                            _paramTemplate[1] = _templateObj.getString("KATEGORI1");
                            _paramTemplate[2] = _templateObj.getString("KATEGORI2");
                            _paramTemplate[3] = _templateObj.getString("AFD").equals( "null" ) ? null : _templateObj.getString("AFD");;
                            _paramTemplate[4] = _templateObj.getString("POS_CODE");
                            _paramTemplate[5] = _templateObj.getString("DI_ID");
                            _paramTemplate[6] = _templateObj.getString("UOM");
                            _paramTemplate[7] = _templateObj.getString("MAX_VALUE");
                            _paramTemplate[8] = _templateObj.getString("TYPE_OPERATION");
                            _paramTemplate[9] = _templateObj.getString("DESC_OPERATION");
                            _paramTemplate[10] = _templateObj.getString("DI_ID_REF");
                            _paramTemplate[11] = _templateObj.getString("DESC_ID_REF");
                            _paramTemplate[12] = _templateObj.getString("URUT");
                            _paramTemplate[13] = _templateObj.getString("CONDITIONAL");

                            db.insertTemplate(_paramTemplate);
//                            Log.i( "isiARR", Arrays.toString(_paramTemplate));
                        }

                        String[] _paramProfile = new String[5];
                        db.deleteProfile();
                        for (int i = 0; i < _profileArray.length(); i++) {
                            JSONObject _profileObj = _profileArray.getJSONObject(i);
//                            Log.i( "insert", String.valueOf(i));
//                            for( int j =0; j < _paramTemplate.length; j++ )
//                                _paramTemplate[j] = "ZZZ";
                            _paramProfile[0] = _profileObj.getString("PROFILE");
                            _paramProfile[1] = _profileObj.getString("MODUL");
                            _paramProfile[2] = _profileObj.getString("NORMA").equals( "null" ) ? null : _profileObj.getString("NORMA");
                            _paramProfile[3] = _profileObj.getString("UOM");
                            _paramProfile[4] = _profileObj.getString("URUT");

                            db.insertProfile(_paramProfile);
//                            Log.i( "isiARR", Arrays.toString(_paramTemplate));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Utils._alert( DashboardActivity.this, "Gagal Mengontak server. (code:"+statusCode+") <br>Daftar Kegiatan dan Profil akan berjalan sesuai kondisi terakhir." );
                Log.e("status code:", String.valueOf(statusCode));

            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        UserSession userSession = new UserSession(DashboardActivity.this);

        tvMandor.setText(userSession.getNama());
        tvNPK.setText(userSession.getNPK());
        tvPosTitle.setText(userSession.getPosTitle());
        tvSitecode.setText(userSession.getCompanyOffice());
        //menentukan hari dan tanggal sekarang
        Locale locale = new Locale("in");
        Calendar c = Calendar.getInstance(locale);
        String tglNow = new SimpleDateFormat("EEEE, dd MMMM yyyy").format(c.getTime());

        tvTgl.setText(tglNow);

        getMenu();
    }

    private void getMenu() {
        dashboardList = new ArrayList<>();
        adapterDashboard = new AdapterDashboard(mContext, dashboardList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 3);
        rvDash.setLayoutManager(mLayoutManager);
        rvDash.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(0), true));
        rvDash.setItemAnimator(new DefaultItemAnimator());
        rvDash.setAdapter(adapterDashboard);
        Cursor cursor = db.getTemplateMenu();
        int i=0;
        if (cursor.moveToFirst()) {
            int _icon;
            String[] _data = new String[3];
            do {
                _data[0] = cursor.getString(cursor.getColumnIndex(db.KEY_TEMPLATE_KATEGORI1));
                _data[1] = cursor.getString(cursor.getColumnIndex(db.KEY_TEMPLATE_COMPANY_OFFICE));
                _data[2] = cursor.getString(cursor.getColumnIndex(db.KEY_TEMPLATE_AFD)) == null ? "" : cursor.getString(cursor.getColumnIndex(db.KEY_TEMPLATE_AFD));
//                _data[2] = _data[2].equals( "null" )? "" :_data[2];
//                Log.i("kategori1:", _data[0]+" "+_data[1]+" "+_data[2]);

                switch(_data[0]) {
                    case "PANEN" :
                        _icon = R.drawable.asset04;
                        break;
                    case "RAWAT" :
                        _icon = R.drawable.asset05;
                        break;
                    case "PROSES" :
                        _icon = R.drawable.asset011;
                        break;
                    case "LOGISTIK" :
                        _icon = R.drawable.asset013;
                        break;
                    case "INFRASTRUKTUR" :
                        _icon = R.drawable.asset012;
                        break;
                    case "WORKSHOP" :
                        _icon = R.drawable.asset09;
                        break;
                    case "MAINTENANCE" :
                        _icon = R.drawable.asset08;
                        break;
                    case "TRANSPORT" :
                        _icon = R.drawable.asset010;
                        break;
                    default :
                        _icon = R.drawable.asset07b;
                        break;
                }
                dashboardList.add(new ModelDashboard(i, _icon, _data[0]+" "+_data[1]+" "+_data[2]));
                i++;
            }
            while (cursor.moveToNext());
        }
//        dashboardList.add(new ModelDashboard(0, R.drawable.asset04, "PANEN"));
//        dashboardList.add(new ModelDashboard(1, R.drawable.asset05, "RAWAT"));
//        dashboardList.add(new ModelDashboard(2, R.drawable.asset011, "PROSES"));
//        dashboardList.add(new ModelDashboard(3, R.drawable.asset013, "LOGISTIK"));
//        dashboardList.add(new ModelDashboard(4, R.drawable.asset012, "INFRASTRUKTUR"));
//        dashboardList.add(new ModelDashboard(5, R.drawable.asset09, "WORKSHOP"));
//        dashboardList.add(new ModelDashboard(6, R.drawable.asset08, "MAINTENANCE"));
//        dashboardList.add(new ModelDashboard(7, R.drawable.asset010, "OPERASIONAL"));

        dashboardList.add(new ModelDashboard(90, R.drawable.asset019a, "STATUS TRANSAKSI"));
        dashboardList.add(new ModelDashboard(91, R.drawable.asset06, "PROFIL"));
//        dashboardList.add(new ModelDashboard(10, R.drawable.asset07b, "PENGATURAN"));
//        dashboardList.add(new ModelDashboard("id_info_resto", R.drawable.ic_resto_green,"Info Restoran"));

        adapterDashboard.notifyDataSetChanged();
    }

    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press again to exit the application", Toast.LENGTH_SHORT).show();
        }

        backPressTime = System.currentTimeMillis();

    }
}
