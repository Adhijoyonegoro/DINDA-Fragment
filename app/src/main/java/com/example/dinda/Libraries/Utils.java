package com.example.dinda.Libraries;

import android.app.Activity;
import android.text.Html;
import android.view.View;

import com.example.dinda.Popup.LayoutPopupError;
import com.example.dinda.Popup.LayoutPopupWarning;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class Utils {
    static boolean choose = false;

    //Fragments Tags
    public static final String LoginFragment = "Login_Fragment";


    public static void _alert(Activity activity, String _message) {
        LayoutPopupError lpe = new LayoutPopupError(activity);
        lpe.setCancelable(true);
        lpe.showDialog();
        lpe.tvTitle.setText("Kesalahan");
        lpe.tvKeterangan.setText(Html.fromHtml(_message));
        lpe.btnPositive.setVisibility(View.GONE);
        lpe.btnNegativie.setVisibility(View.VISIBLE);
        lpe.btnNegativie.setText("TUTUP");
        lpe.btnNegativie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lpe.dismissDialog();
            }
        });
    }

    public static void _alertClose(Activity activity, String _message) {
        LayoutPopupWarning lpe = new LayoutPopupWarning(activity);
        lpe.setCancelable(true);
        lpe.showDialog();
        lpe.tvTitle.setText("Informasi");
        lpe.tvKeterangan.setText(Html.fromHtml(_message));
        lpe.btnPositive.setVisibility(View.GONE);
        lpe.btnNegativie.setVisibility(View.VISIBLE);
        lpe.btnNegativie.setText("TUTUP");
        lpe.btnNegativie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lpe.dismissDialog();
                activity.finish();
            }
        });
    }

    public static boolean _confirm(Activity activity, String _message) {
        LayoutPopupWarning lpe = new LayoutPopupWarning(activity);
        lpe.setCancelable(true);
        lpe.showDialog();
        lpe.tvTitle.setText("Konfirmasi");
        lpe.tvKeterangan.setText(Html.fromHtml(_message));
        lpe.btnPositive.setVisibility(View.VISIBLE);
        lpe.btnNegativie.setVisibility(View.VISIBLE);
        lpe.btnPositive.setText("YA");
        lpe.btnNegativie.setText("TIDAK");
        lpe.btnNegativie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = false;
                lpe.dismissDialog();
//                activity.finish();
            }
        });
        lpe.btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = true;
                lpe.dismissDialog();
            }
        });
        return choose;
    }

    /**
     * Get IP address from first non-localhost interface
     * @param useIPv4   true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return "";
    }

}
