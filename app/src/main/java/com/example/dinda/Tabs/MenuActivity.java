package com.example.dinda.Tabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.dinda.Fragment.LoginFragment;
import com.example.dinda.R;
import com.example.dinda.ServerSide.Utils;

public class MenuActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.bglogin);
        setContentView(R.layout.activity_menu);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new LoginFragment(),
                            Utils.LoginFragment).commit();

        }
    }
}
