package com.example.dinda.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dinda.Fragment.ProfileFragment;
import com.example.dinda.Model.ModelDashboard;
import com.example.dinda.R;
import com.example.dinda.Tabs.DashboardActivity;
import com.example.dinda.Tabs.HistoryActivity;
import com.example.dinda.Tabs.PanenActivity;
import com.example.dinda.Tabs.ProfileActivity;
import com.example.dinda.Tabs.ProsesActivity;
import com.example.dinda.Tabs.RawatActivity;

import java.util.List;

public class AdapterDashboard extends RecyclerView.Adapter<AdapterDashboard.HolderItem> {
    private Context context;
    private List<ModelDashboard> dashboardList;

    public AdapterDashboard(Context context, List<ModelDashboard> dashboardList) {
        this.context = context;
        this.dashboardList = dashboardList;
    }

    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_menu_dashboard, viewGroup, false);
        HolderItem holderItem = new HolderItem(itemView);
        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holderItem, int i) {
        final ModelDashboard x = dashboardList.get(i);
        holderItem.tvtitle.setText(x.getTitle());

        Glide.with(context)
                .load(x.getImg())
                .into(holderItem.imgMenu);

        holderItem.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (x.getId() == 8) {
//                    FragmentManager fragmentManager = ((DashboardActivity)context).getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                    ProfileFragment profileFragment = new ProfileFragment();
//
//                    fragmentTransaction.replace(R.id.frameLayout, profileFragment);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                }
//                else
                if (x.getId() < 90) {
                    Intent intent = new Intent(context, PanenActivity.class);
                    intent.putExtra("HEADER", x.getTitle());
                    context.startActivity(intent);
                }
//                if (x.getId() == 1) {
//                    Intent intent = new Intent(context, RawatActivity.class);
//                    context.startActivity(intent);
//                }
//                if (x.getId() == 2) {
//                    Intent intent = new Intent(context, ProsesActivity.class);
//                    context.startActivity(intent);
//                }
                if (x.getId() == 90) {
                    Intent intent = new Intent(context, HistoryActivity.class);
                    context.startActivity(intent);
                }
                if (x.getId() == 91) {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    context.startActivity(intent);
                }

//                else
//                if (x.getId().equals("id_akun")) {
//                    Intent intent = new Intent(context, DashboardActivity.class);
//                    context.startActivity(intent);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dashboardList.size();
    }

    public class HolderItem extends RecyclerView.ViewHolder {
        public TextView tvtitle;
        ImageView imgMenu;
//        CardView cdview;
        LinearLayout rlItem;

        public HolderItem(@NonNull View itemView) {
            super(itemView);

            tvtitle = (TextView) itemView.findViewById(R.id.tvmenutitle);

            imgMenu=(ImageView)itemView.findViewById(R.id.imgmenu);

//            cdview = (CardView) itemView.findViewById(R.id.cv_item);

            rlItem = (LinearLayout) itemView.findViewById(R.id.rl_item);
        }
    }
}
