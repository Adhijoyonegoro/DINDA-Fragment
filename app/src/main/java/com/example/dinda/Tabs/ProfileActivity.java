package com.example.dinda.Tabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.dinda.Fragment.ProfileFragment;
import com.example.dinda.Libraries.DatabaseHelper;
import com.example.dinda.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    DatabaseHelper db;
    @BindView(R.id.txtHeader)
    TextView tvHeader;
//    @BindView(R.id.fl_profile)
//    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
