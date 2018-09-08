package mickeylina.creditpal;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.aesthetic.AestheticActivity;

public class Debug_Activity extends AestheticActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sharedPreferences = this.getSharedPreferences("DEBUG_PACKAGES_PREF", 0);
        this.editor = this.sharedPreferences.edit();
        setContentView(R.layout.activity_debug);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button buttonCMB = findViewById(R.id.cmb_notification);
        buttonCMB.setOnClickListener(v -> {
            SharedPreferences notifPref = this.getSharedPreferences("NOTIFICATION_PREFS", MODE_PRIVATE);

//TODO: Replace the value of phoneNo with a valid phone number from your contacts to get logs and the notification
            if (notifPref.getBoolean("notification_enabled", true)) {
                String phoneNo = "012345678";
                String notificationMessage = "from " + Utils.getContactNameFromNumber(phoneNo,
                        getApplicationContext().getContentResolver()) + " tap to call directly";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setAutoCancel(true).setDefaults(-1);
                builder.setContentTitle("Call Me Back Request").setContentText(notificationMessage);
                builder.setSmallIcon(R.mipmap.ic_cp).setTicker(notificationMessage);
                builder.setPriority(0).setStyle(new NotificationCompat.BigTextStyle().bigText(notificationMessage));
                builder.setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0,
                        new Intent("android.intent.action.DIAL", Uri.parse("tel:" + phoneNo)), 0));
                ((NotificationManager) getApplicationContext().getSystemService("notification"))
                        .notify((int) (Math.random() * 10.0d), builder.build());
            } else Toast.makeText(this, "notifications not enabled", Toast.LENGTH_SHORT).show();
        });

//        User has just recharged his/her balance
        Button justRecharged = findViewById(R.id.after_Recharge);
        justRecharged.setOnClickListener(v -> {
            String creditFinalAmount;
            String balanceAfterRecharge = "balance is";
            String message="dear customer, your prepaid account has been recharged succesfully.your " +
                    "recharged balance is 100.00 birr.your  balance is 105.00 birr.with this" +
                    " balance your account will expire on 24/12/2018.ethio telecom";
            String afterRecharge = "recharged balance is";
            double rAmount;
            if(message.contains(afterRecharge)) {
                    String rechargedAmount = message.substring((message.indexOf(afterRecharge)
                            + afterRecharge.length()) + 1, message.indexOf("birr"));
                    creditFinalAmount = message.substring((message.lastIndexOf(balanceAfterRecharge)
                            + balanceAfterRecharge.length()) + 1, message.lastIndexOf("birr.with"));

                    String creditExpiresOn = message.substring(message.lastIndexOf("on ") + 3,
                            message.lastIndexOf(".ethio"));

                    Log.d("Credit Expires In", creditExpiresOn);
                    Log.d("Gebeta_CreditFinal", creditFinalAmount);
                    Log.d("Gebeta_RechargedAmount", rechargedAmount);

                    rAmount = Double.parseDouble(rechargedAmount);
                    double creditAmount = Double.parseDouble(creditFinalAmount);
                    editor.putFloat("lastRechargedAmount", (float) rAmount);
                    editor.putFloat("currentBalance", (float) creditAmount);
                    if (rAmount > creditAmount) {
                        editor.putFloat("creditMax", (float) rAmount);
                        Log.d("Gebeta", "CreditMax is recharged amount");
                    } else {
                        editor.putFloat("creditMax", (float) creditAmount);
                    }
                    editor.putString("rechargedOnCredit", "TODAY");
                    editor.putString("expiresOnCredit", creditExpiresOn);
                    editor.apply();
                    Toast.makeText(Debug_Activity.this,rAmount
                            +creditAmount
                            +creditFinalAmount, Toast.LENGTH_SHORT).show();
            }

        });

        Button buttonAppend = findViewById(R.id.button_add);
        buttonAppend.setOnClickListener(new View.OnClickListener() {

            String afterEnquireData2 = "from mobile internet package";
            String strMessage = "dear customer, your remaining amount from mobile internet package" +
                    " 1gb to be expired after 30 days is 1024.00 mb with expiry date on 2018-09-24 " +
                    "at 16:19:37; " +
//                    " from mobile internet package 45mb to be expired after 24 hours " +
//                    "is 21.179 mb with expiry date on 2018-08-26 at 14:23:35; " +
                    "thank you, ethio telecom.";
            String dataMessage;
            String str;
            String packageAmount2;
            String packageAvailable;
            String dataExpiresOn;
            String purchasedOn;
            float previousAmount;

            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferencesf;
                SharedPreferences.Editor editorf;
                sharedPreferencesf = getSharedPreferences("DEBUG_PACKAGES_PREF", 0);
                editorf = sharedPreferences.edit();
                editorf.apply();
                if (strMessage.contains("telecom.")) {
                    while (strMessage.contains(afterEnquireData2)) {
                        dataMessage = strMessage.substring(strMessage.indexOf(afterEnquireData2));
                        str = dataMessage;
                        dataMessage = str.substring(dataMessage.indexOf(afterEnquireData2),
                                dataMessage.indexOf(";") + 1);
                        Log.d("Data Message", dataMessage);
                        packageAmount2 = dataMessage.substring((dataMessage.indexOf(afterEnquireData2) +
                                        afterEnquireData2.length()) + 1,
                                dataMessage.indexOf(" to be "));
                        if (packageAmount2.contains("gb")) {
                            packageAmount2 = String.valueOf(Double.parseDouble(packageAmount2.replaceAll(" ",
                                    BuildConfig.FLAVOR).replaceAll("gb",
                                    BuildConfig.FLAVOR)) * 1000.0d);
                        } else {
                            try {
                                packageAmount2 = packageAmount2.replaceAll(" ",
                                        BuildConfig.FLAVOR).replaceAll("mb",
                                        BuildConfig.FLAVOR);
                            } catch (Exception e422222) {
                                Log.e("Data", "DATA Package message parsing error " +
                                        e422222.getMessage());
                            }
                        }
                        packageAvailable = dataMessage.substring(dataMessage.indexOf("is") + 3,
                                dataMessage.lastIndexOf("with")).replaceAll("mb",
                                BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR);
                        str = dataMessage;
                        dataExpiresOn = str.substring(dataMessage.indexOf("on ") + 3,
                                dataMessage.indexOf(";") - 2).replaceAll(" at ",
                                BuildConfig.FLAVOR).replaceAll(" ",
                                BuildConfig.FLAVOR).replaceAll("-",
                                BuildConfig.FLAVOR).replaceAll(":", BuildConfig.FLAVOR);
                        purchasedOn = dataMessage.substring(dataMessage.indexOf("after") + 6,
                                dataMessage.indexOf("is"));
                        if (purchasedOn.contains("days")) {
                            purchasedOn = purchasedOn.replaceAll(" ",
                                    BuildConfig.FLAVOR).replaceAll("days",
                                    BuildConfig.FLAVOR);
                        } else {
                            purchasedOn = "1";
                        }
                        if (sharedPreferencesf.getBoolean("onDataPackage", false)) {
                            previousAmount = sharedPreferencesf.getFloat("dataPackageAvailable", 0.0f);
                            float previousMax2 = sharedPreferencesf.getFloat("dataPackageMax", 0.0f);
                            editorf.putFloat("dataPackageAvailable", Float.parseFloat(packageAvailable) + previousAmount);
                            editorf.putFloat("dataPackageMax", Float.parseFloat(packageAmount2) + previousMax2);
                        } else {
                            editorf.putFloat("dataPackageAvailable", Float.parseFloat(packageAvailable));
                            editorf.putBoolean("onDataPackage", true);
                            editorf.putFloat("dataPackageMax", Float.parseFloat(packageAmount2));
                            editorf.putString("dataExpiresOn", dataExpiresOn);
                            editorf.putString("dataBoughtOn", purchasedOn);
                        }
                        editorf.apply();
                        strMessage = strMessage.replaceAll(dataMessage, BuildConfig.FLAVOR);
                    }
                }
            }
        });

        Button lowPackNotif = findViewById(R.id.lowpack);
        lowPackNotif.setOnClickListener(v -> {
            String strMMessage = "dear customer you have finished internet plan of your mobile internet" +
                    " package 1gb to be";
            String lowDataPackage = "finished internet plan of your mobile internet package";
            if(strMMessage.contains(lowDataPackage)){
                String lowDataMessage = "internet plan";
                 String pack = "You have used 75% of your"+
                         strMMessage.substring(strMMessage.indexOf(lowDataPackage)+
                                 lowDataPackage.length(),
                                strMMessage.indexOf((" to"))).toUpperCase()+" Internet Plan";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setAutoCancel(true).setDefaults(-1);
                builder.setContentTitle("Low Internet Package").setContentText(pack);
                builder.setSmallIcon(R.mipmap.ic_cp).setTicker(pack);
                builder.setPriority(0).setStyle(new NotificationCompat.BigTextStyle().bigText(pack));
                builder.setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0,
                        new Intent("android.intent.action.DIAL", Uri.parse("tel:" + "0912345678")), 0));
                ((NotificationManager) getApplicationContext().getSystemService("notification"))
                        .notify((int) (Math.random() * 10.0d), builder.build());

            } else Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
