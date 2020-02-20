package com.example.dinda.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dinda.Popup.LayoutPopupError;
import com.example.dinda.R;
import com.example.dinda.ServerSide.SetKeyboard;
import com.example.dinda.ServerSide.URLApk;
import com.example.dinda.Tabs.DashboardActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment {


    @BindView(R.id.tiedt_name)
    TextInputEditText etName;
    @BindView(R.id.til_name)
    TextInputLayout tilName;
    @BindView(R.id.tv_tgl_lahir)
    TextView tvTglLahir;
//    @BindView(R.id.til_pass)
//    TextInputLayout tilPass;
    //    @BindView(R.id.cb_show_hide_pass)
//    CheckBox cbShowHidePass;
//    @BindView(R.id.tvc_forgot_pass)
//    TextView tvcForgotPass;
    @BindView(R.id.btn_login)
    Button btnLogin;
//    @BindView(R.id.rl_tgl_lahir)
//    RelativeLayout rlTglLahir;

    SimpleDateFormat simpleDateFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_login)
    public void onViewClickedLogin() {
        if (etName.getText().toString().isEmpty() && tvTglLahir.getText().toString().isEmpty()) {
            LayoutPopupError lpeLogin = new LayoutPopupError(getActivity());
            lpeLogin.setCancelable(true);
            lpeLogin.showDialog();
            lpeLogin.tvTitle.setText("Kesalahan");
            lpeLogin.tvKeterangan.setText(Html.fromHtml("Username dan password <br/>tidak boleh kosong."));
            lpeLogin.btnPositive.setVisibility(View.GONE);
            lpeLogin.btnNegativie.setVisibility(View.VISIBLE);
            lpeLogin.btnNegativie.setText("TUTUP");
            lpeLogin.btnNegativie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lpeLogin.dismissDialog();
                }
            });
        } else {

            Intent intent = new Intent(getActivity(), DashboardActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.rl_tgl_lahir)
    public void onViewClickedDate() {
        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();
        /**
         * Initiate DatePicker dialog
         */
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.datepicker, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tvTglLahir.setText(simpleDateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();

        //hide keyboard
//        SetKeyboard.hideKeyboard(getActivity());
    }
}
