package com.example.dinda.Tabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dinda.Adapter.AdapterDashboard;
import com.example.dinda.Model.ModelDashboard;
import com.example.dinda.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {

    private long backPressTime;
    private Context mContext = DashboardActivity.this;
    private AdapterDashboard adapterDashboard;
    private List<ModelDashboard> dashboardList;

    @BindView(R.id.txtDate)
    TextView tvTgl;
    @BindView(R.id.rv_dash)
    RecyclerView rvDash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        //menentukan hari dan tanggal sekarang
        Locale locale = new Locale("in");
        Calendar c = Calendar.getInstance(locale);
        String tglNow = new SimpleDateFormat("EEEE, dd MMMM yyyy").format(c.getTime());
//        String bulan = new SimpleDateFormat("MMMM").format(c.getTime());
//        String hari = new SimpleDateFormat("EEEE").format(c.getTime());
//        String tgl = new SimpleDateFormat("dd").format(c.getTime());
//        String thn = new SimpleDateFormat("yyyy").format(c.getTime());
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

        dashboardList.add(new ModelDashboard(0, R.drawable.asset04, "PANEN"));
        dashboardList.add(new ModelDashboard(1, R.drawable.asset05, "RAWAT"));
        dashboardList.add(new ModelDashboard(2, R.drawable.asset011, "PROSES"));
        dashboardList.add(new ModelDashboard(3, R.drawable.asset013, "LOGISTIK"));
        dashboardList.add(new ModelDashboard(4, R.drawable.asset012, "INFRASTRUKTUR"));
        dashboardList.add(new ModelDashboard(5, R.drawable.asset09, "WORKSHOP"));
        dashboardList.add(new ModelDashboard(6, R.drawable.asset08, "MAINTENANCE"));
        dashboardList.add(new ModelDashboard(7, R.drawable.asset010, "OPERASIONAL"));
        dashboardList.add(new ModelDashboard(8, R.drawable.asset06, "PROFIL"));
        dashboardList.add(new ModelDashboard(9, R.drawable.asset07b, "PENGATURAN"));
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
