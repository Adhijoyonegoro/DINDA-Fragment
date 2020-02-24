package com.example.dinda.Fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.dinda.Libraries.ApiStatus;
import com.example.dinda.Libraries.Imei;
import com.example.dinda.Model.PostRegister;
import com.example.dinda.Popup.LayoutPopupError;
import com.example.dinda.R;
import com.example.dinda.Rest.ApiClient;
import com.example.dinda.Rest.ApiInterface;
import com.example.dinda.Tabs.DashboardActivity;
import com.example.dinda.Tabs.MenuActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Boolean.TRUE;

public class LoginFragment extends Fragment {

    ApiInterface mApiInterface;
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

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    SimpleDateFormat simpleDateFormat;
    String imei= null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ButterKnife.bind(this, view);

        loadIMEI();

        return view;
    }

    public void loadIMEI() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission();
        } else {
            // READ_PHONE_STATE permission is already been granted.
            Log.e("imei", Imei.getUniqueIMEIId(getActivity()));
        }
    }

    private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission Request")
                    .setMessage("tidak dapat permission")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                        }
                    })
                    .setIcon(R.drawable.asset01)
                    .show();
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            Log.e("imei", Imei.getUniqueIMEIId(getActivity()));
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has been granted, proceed with displaying IMEI Number
                //alertAlert(getString(R.string.permision_available_read_phone_state));
                Log.e("imei", Imei.getUniqueIMEIId(getActivity()));
            } else {
                alertAlert("tidak dapat permission");
            }
        }
    }

    private void alertAlert(String msg) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Permission Request")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do somthing here
                    }
                })
                .setIcon(R.drawable.asset01)
                .show();
    }



    public void _alert(String _message) {
        LayoutPopupError lpeLogin = new LayoutPopupError(getActivity());
        lpeLogin.setCancelable(true);
        lpeLogin.showDialog();
        lpeLogin.tvTitle.setText("Kesalahan");
        lpeLogin.tvKeterangan.setText(Html.fromHtml(_message));
        lpeLogin.btnPositive.setVisibility(View.GONE);
        lpeLogin.btnNegativie.setVisibility(View.VISIBLE);
        lpeLogin.btnNegativie.setText("TUTUP");
        lpeLogin.btnNegativie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lpeLogin.dismissDialog();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.btn_login)
    public void onViewClickedLogin() {
        String _message = "";
        if (etName.getText().toString().length() != 6) {
            _message = "NPK harus 6 digit.<br>";
        }
        if (tvTglLahir.getText().toString().isEmpty()) {
            _message += "Tanggal Lahir harus diisi.<br>";
        }
        if (_message.length() > 0) {
            _alert(_message);
        } else {
            imei = Imei.getUniqueIMEIId(getActivity());
            Log.i("imei", imei);
            mApiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<PostRegister> postRegisterCall = mApiInterface.postRegister(etName.getText().toString(), tvTglLahir.getText().toString(),imei );
            postRegisterCall.enqueue(new Callback<PostRegister>() {

                @Override
                public void onResponse(Call<PostRegister> call, Response<PostRegister> response) {
//                    MainActivity.ma.refresh();
//                    finish();
                    Log.i("api_register", response.body().getData().toString());
                    String _result = response.body().getStatus();
                    if( _result != ApiStatus.register_success ) {
                        _alert( _result );
                    }
                    else {
                        Intent intent = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<PostRegister> call, Throwable t) {
                    _alert(t.getMessage());
                }
            });
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

        // Set the Calendar new date as minimum date of date picker
        newCalendar.add(Calendar.YEAR, -60);
        datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
        newCalendar.add(Calendar.YEAR, 43);
        datePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());


        datePickerDialog.show();

        //hide keyboard
//        SetKeyboard.hideKeyboard(getActivity());
    }
}
