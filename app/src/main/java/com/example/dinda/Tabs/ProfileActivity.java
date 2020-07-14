package com.example.dinda.Tabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.dinda.Adapter.AdapterProfile;
import com.example.dinda.Libraries.DatabaseHelper;
import com.example.dinda.Model.Profile;
import com.example.dinda.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
   // DatabaseHelper db;
   // @BindView(R.id.txtHeader)
    //TextView tvHeader;
//    @BindView(R.id.fl_profile)
//    FrameLayout frameLayout;

    private DatabaseHelper db;
    private RecyclerView recyclerview_myprofile;
    private List<Profile> ls_myprofile;
    ArrayList<Profile> dataModels;
    AdapterProfile myprofileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = new DatabaseHelper(ProfileActivity.this.getApplicationContext());
        recyclerview_myprofile = findViewById(R.id.recyclerview_list_myprofile);
        try {
            ls_myprofile = new ArrayList<>();
            myprofileAdapter = new AdapterProfile(ProfileActivity.this,ls_myprofile);
            recyclerview_myprofile.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
            recyclerview_myprofile.setAdapter(myprofileAdapter);

            try {

                DatabaseHelper DB_Helper = new DatabaseHelper(ProfileActivity.this);
                SQLiteDatabase db = DB_Helper.getReadableDatabase();
                final Cursor cur = db.rawQuery(
                        "SELECT DISTINCT(MODUL) as MODUL FROM M_PROFILE", null);

                cur.moveToPosition(0);
                dataModels = new ArrayList<>();
                for (int i = 0; i < cur.getCount(); i++) {
                    ls_myprofile.add(
                            new Profile(cur.getString(0),
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
                Log.i("MY_PRILFE", ex.toString());
            }

        } catch (Exception ex) {
            Log.i("MY_PRILFE", ex.toString());
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
}
