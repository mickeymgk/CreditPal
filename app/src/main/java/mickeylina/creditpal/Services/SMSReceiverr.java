package mickeylina.creditpal.Services;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Calendar;

import mickeylina.creditpal.BuildConfig;
import mickeylina.creditpal.R;
import mickeylina.creditpal.Utils;


public class SMSReceiverr extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";
    private String date;
    private Editor editor;
    Calendar rightNow;
    private SharedPreferences sharedPreferences;
    private String time;
    private String today;

    public void onReceive(Context context, Intent intent) {
        final Context context1 = context;
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle myBundle = intent.getExtras();
            String strMessage = BuildConfig.FLAVOR;
            String phoneNumber = BuildConfig.FLAVOR;
            String format = myBundle.getString("format");
            if (myBundle != null) {
                Object[] pdus = (Object[]) myBundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < messages.length; i++) {
                    if (VERSION.SDK_INT >= 23) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    } else {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    phoneNumber = messages[i].getOriginatingAddress();
                    Log.d("PHONE NUMBER", phoneNumber);
                    strMessage = new StringBuilder(String.valueOf(strMessage)).append(messages[i].getMessageBody()).toString();
                    Log.d("MESSAGE", strMessage);
                    renderText(phoneNumber, strMessage, context);
                    Intent intent2 = new Intent("tk.nybapps.gebeta.USER_ACTION");
                    intent2 = new Intent("tk.nybapps.gebeta.UPDATE_WIDGET");
                    context.getApplicationContext().sendBroadcast(intent2);
                    context.sendBroadcast(intent2);
//                    context.startService(new Intent(context, GebetaService.class));
                }
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
//                    context1.startService(new Intent(context1, GebetaService.class));
                }
            }, 2000);
        }
    }

    @SuppressLint("WrongConstant")
    private void renderText(String phoneNumber, String message, Context context) {
        int phoneNumberLength = phoneNumber.length();
        message = message.toLowerCase();
        if (phoneNumberLength < 25) {
            String afterTransfer = "fee is 0.30 birr. your balance is";
            String afterRecharge = "recharged balance is";
            String afterRechargeFromOther = "been recharged with";
            String balanceAfterRecharge = "balance is";
            String afterReceivedTransfer = "for you.your balance is";
            String afterReceivedDataPackages = "received gift mobile internet package";
            String afterEnquireData = "from gift mobile internet package";
            String afterEnquireData2 = "from mobile internet package";
            String afterDataBought = "new service offer mobile internet package";
            String afterDataSent = "sent gift mobile internet package";
            String afterSmsBought = "new service offer mobile sms package";
            String afterSmsReceived = "received gift mobile sms package";
            String afterEnquireSms = "from mobile sms package";
            String afterEnquireSms2 = "from gift mobile sms package";
            String afterSmsSent = "sent gift mobile sms package";
            String afterVoiceReceived = "received gift mobile night voice package";
            String afterVoiceBought = "new service offer mobile night voice package";
            String afterMonVoiceReceived = "have received gift monthly voice";
            String afterMonVoiceEnquire = "from monthly voice package";
            String afterMonVoiceEnquire2 = "from gift monthly voice";
            String afterEnquireVoice = "from mobile night voice package";
            String afterEnquireVoice2 = "from gift mobile night voice package";
            String afterVoiceSent = "sent gift mobile night voice package";
            String afterMonVoiceSent = "sent gift monthly voice";
            String afterMonVoiceBought = "the new service offer monthly voice package";
            String isExpired = "is expired.";
            String expiredDataPackage = "your gift mobile internet package";
            String finishedDataPackage = "have finished your gift mobile internet package";
            String afterReceivingCallMeBack = "please call back to";
            this.sharedPreferences = context.getSharedPreferences("gebeta_pref", 0);
            this.editor = this.sharedPreferences.edit();
            if (!message.contains("75%")) {
                String creditFinalAmount;
                double rAmount;
//                DBHelper dBHelper;
                SQLiteDatabase sqLiteDatabase;
                ContentValues contentValues;
                ContentValues contentValues2;
                String package_duration;
                Calendar calendar;
                String boughtOnText;
                double price;
                float packageAmount;
                String package_amount;
                String dataMessage;
                String str;
                String packageAmount2;
                String packageAvailable;
                String dataExpiresOn;
                String purchasedOn;
                Calendar expiresOnCalendar;
                float previousAmount;
//                ActivePackage activePackage;
                String package_to;
                int smsRemaining;
                String boughtOn;
                String expiresOn;
                int previousAvailable;
                int previousMax;
                String smsPackageMax;
                String isString;
                String smsMessage;
                int smsMax;
                String smsExpiryDate;
                String smsPackageAvailable;
                Double price2;
                String voicePackage;
                Calendar expiresOnCal;
                Calendar date;
                float voicePackageMonMax;
                boolean isOnSms;
                float availableData;
                String smsAvailable;
                int smsPackageAvailable2;
                float secondsAvailable;
                float previousSecondsAvailable;
                String andString;
                String voiceMessage;
                String voicePackageMax;
                float voiceAvailable;
                String smsBoughtOn;
                String dataAvailable;
                boolean isOnData;
                String dataBoughtOn;
                String minuteAvailable;
                String voiceExpiresOn;
                String voiceBoughOn;
                float availableSeconds;
                float f;
                if (message.contains(afterEnquireData) || message.contains(afterEnquireData2) ||
                        message.contains(afterEnquireSms) || message.contains(afterEnquireSms2) ||
                        message.contains(afterEnquireVoice2) || message.contains(afterMonVoiceEnquire2)
                        || message.contains(afterEnquireVoice) || message.contains(afterMonVoiceEnquire)) {
                    this.editor.putBoolean("onVoiceMonPackage", false);
                    this.editor.putBoolean("onVoicePackage", false);
                    this.editor.putBoolean("onSmsPackage", false);
                    this.editor.putBoolean("onDataPackage", false);
                    this.editor.apply();
                }
                if (message.contains(afterReceivingCallMeBack)) {
                    try {
                        String phoneNo = message.substring((message.indexOf(afterReceivingCallMeBack) +
                                afterReceivingCallMeBack.length()) + 1, (message.indexOf(afterReceivingCallMeBack) +
                                afterReceivingCallMeBack.length()) + 11).replace(" ", BuildConfig.FLAVOR);
                        Log.d("Call Me Back Request", phoneNo);
                        if (this.sharedPreferences.getBoolean("callMeBackNotification", true)) {
                            String notificationMessage = "From " + Utils.getContactNameFromNumber(phoneNo, context.getContentResolver());
                            Builder builder = new Builder(context);
                            builder.setAutoCancel(true).setDefaults(-1);
                            builder.setContentTitle("Call Me Back Request").setContentText(notificationMessage);
                            builder.setSmallIcon(R.mipmap.ic_cp).setTicker(notificationMessage);
                            builder.setPriority(0).setStyle(new BigTextStyle().bigText(notificationMessage));
                            builder.setContentIntent(PendingIntent.getActivity(context, 0,
                                    new Intent("android.intent.action.DIAL", Uri.parse("tel:" + phoneNo)), 0));
                            ((NotificationManager) context.getSystemService("notification")).notify((int)
                                    (Math.random() * 10.0d), builder.build());
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "ERROR WHILE MAKING THE CMB NOTIFICATION");
                    }
                }
                if (message.contains(afterRecharge)) {
                    try {
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
                        this.editor.putFloat("lastRechargedAmount", (float) rAmount);
                        this.editor.putFloat("currentBalance", (float) creditAmount);
                        if (rAmount > creditAmount) {
                            this.editor.putFloat("creditMax", (float) rAmount);
                            Log.d("Gebeta", "CreditMax is recharged amount");
                        } else {
                            this.editor.putFloat("creditMax", (float) creditAmount);
                        }
                        this.editor.putString("rechargedOnCredit", this.today);
                        this.editor.putString("expiresOnCredit", creditExpiresOn);
                        this.editor.apply();
                    } catch (Exception e2) {
                        Log.e("SMS RECEIVE", "Something went after recharge");
                    }
                }
//                if (message.contains(afterRechargeFromOther)) {
//                    try {
//                        rAmount = message.substring((message.indexOf(afterRechargeFromOther) + afterRechargeFromOther.length()) + 1, message.indexOf("birr")).replaceAll(" ", BuildConfig.FLAVOR);
//                        this.editor.putFloat("currentBalance", Float.parseFloat(rAmount) + this.sharedPreferences.getFloat("currentBalance", 0.0f));
//                        this.editor.putFloat("creditMax", this.sharedPreferences.getFloat("currentBalance", 0.0f) + Float.parseFloat(rAmount));
//                        this.editor.putFloat("lastRechargedAmount", Float.parseFloat(rAmount));
//                        this.editor.apply();
//                    } catch (Exception e3) {
//                        Log.e("AfterRechargeFromO", "Error Parsing from after recharge from other");
//                    }
//                }
//                if (message.contains(afterTransfer)) {
//                    try {
//                        String youTransfer = "you transfer";
//                        String creditMaxAmount = message.substring((message.indexOf(youTransfer) + youTransfer.length()) + 1, message.lastIndexOf("birr to the"));
//                        String transferredTo = message.substring(message.indexOf("number ") + 7, message.indexOf(" successfully"));
//                        creditFinalAmount = message.substring((message.indexOf(afterTransfer) + afterTransfer.length()) + 1, message.lastIndexOf("birr"));
//                        Log.d("After Transfer", creditFinalAmount);
//                        float creditFinal = (float) Double.parseDouble(creditFinalAmount);
//                        float transferredAmount = (float) Double.parseDouble(creditMaxAmount);
//                        this.editor.putFloat("creditMax", creditFinal + transferredAmount);
//                        this.editor.putFloat("currentBalance", creditFinal);
//                        this.editor.apply();
//                        if (message.length() >= Opcodes.I2B) {
//                            dBHelper = new DBHelper(context.getApplicationContext());
//                            sqLiteDatabase = dBHelper.getWritableDatabase();
//                            contentValues = new ContentValues();
//                            contentValues.put("type", Integer.valueOf(10));
//                            contentValues.put("amount", Float.valueOf(transferredAmount));
//                            contentValues.put("price", Double.valueOf(((double) transferredAmount) + 0.3d));
//                            contentValues.put("too", "0" + transferredTo);
//                            contentValues.put("peak", Integer.valueOf(0));
//                            contentValues.put("date", Long.valueOf(Long.parseLong(this.date)));
//                            contentValues2 = contentValues;
//                            contentValues2.put("time", this.time);
//                            sqLiteDatabase.insert("gebeta_log", "type", contentValues);
//                            sqLiteDatabase.close();
//                            dBHelper.close();
//                        } else {
//                            return;
//                        }
//                    } catch (Exception e4) {
//                        Log.e("SMS Receiver", BuildConfig.FLAVOR + e4.getMessage());
//                    }
//                }
//                if (message.contains(afterReceivedTransfer)) {
//                    try {
//                        creditFinalAmount = message.substring((message.indexOf(afterReceivedTransfer) + afterReceivedTransfer.length()) + 1, message.lastIndexOf("birr"));
//                        Log.d("After Receive Transfer", creditFinalAmount);
//                        this.editor.putFloat("currentBalance", (float) Double.parseDouble(creditFinalAmount));
//                        this.editor.putFloat("creditMax", (float) Double.parseDouble(creditFinalAmount));
//                        this.editor.apply();
//                    } catch (Exception e42) {
//                        Log.e("SMS Receiver", BuildConfig.FLAVOR + e42.getMessage());
//                    }
//                }
                if (message.contains(afterDataBought)) {
                    try {
                        if (message.contains("telecom.")) {
                            String dataPackageOriginal = message.substring((message.indexOf(afterDataBought) +
                                    afterDataBought.length()) + 1, message.lastIndexOf("to be"));
                            package_duration = message.substring(message.indexOf("after ") + 6,
                                    message.indexOf(" is added"));
                            if (package_duration.contains("hours")) {
                                package_duration = "1";
                            } else {
                                package_duration = package_duration.replace("days",
                                        BuildConfig.FLAVOR).replace(" ", BuildConfig.FLAVOR);
                            }
                            if (dataPackageOriginal.contains("mb")) {
                                dataPackageOriginal = dataPackageOriginal.replace("mb",
                                        BuildConfig.FLAVOR);
                            } else {
                                dataPackageOriginal = String.valueOf(Integer.parseInt(dataPackageOriginal.replace("gb", BuildConfig.FLAVOR)) * 1000);
                            }
                            Log.d("Gebeta Data", dataPackageOriginal);
                            double availableData2 = Double.parseDouble(dataPackageOriginal);
                            if (this.sharedPreferences.getBoolean("onDataPackage", false)) {
                                this.editor.putFloat("dataPackageAvailable", ((float) availableData2) +
                                        this.sharedPreferences.getFloat("dataPackageAvailable", 0.0f));
                                this.editor.putFloat("dataPackageMax", ((float) availableData2) +
                                        this.sharedPreferences.getFloat("dataPackageMax", 0.0f));
                            } else {
                                this.editor.putFloat("dataPackageAvailable", (float) availableData2);
                                this.editor.putBoolean("onDataPackage", true);
                                this.editor.putFloat("dataPackageMax", (float) availableData2);
                            }
                            calendar = Calendar.getInstance();
                            calendar.add(5, Integer.parseInt(package_duration));
//                            boughtOnText = GebetaUtils.calendarToString(Calendar.getInstance(), true);
//                            String expireOnText = GebetaUtils.calendarToString(calendar, true);
//                            this.editor.putString("dataBoughtOn", boughtOnText);
//                            this.editor.putString("dataExpiresOn", expireOnText);
                            this.editor.apply();
//                            dBHelper = new DBHelper(context.getApplicationContext());
//                            price = dBHelper.getPrice(package_duration, dataPackageOriginal, context.getApplicationContext(), 1);
//                            Log.d("Gebeta Data Price", String.valueOf(price));
//                            this.editor.putFloat("currentBalance", (float) (((double) this.sharedPreferences.getFloat("currentBalance", 0.0f)) - price));
                            this.editor.apply();
//                            sqLiteDatabase = dBHelper.getWritableDatabase();
                            contentValues = new ContentValues();
                            contentValues.put("type", Integer.valueOf(7));
                            contentValues.put("amount", Double.valueOf(Double.parseDouble(dataPackageOriginal)));
//                            contentValues.put("price", Double.valueOf(price));
                            contentValues.put("too", "-");
                            contentValues.put("peak", Integer.valueOf(0));
                            contentValues.put("date", Long.valueOf(Long.parseLong(this.date)));
                            contentValues2 = contentValues;
                            contentValues2.put("time", this.time);
//                            sqLiteDatabase.insert("gebeta_log", "type", contentValues);
//                            sqLiteDatabase.close();
                            packageAmount = Float.parseFloat(dataPackageOriginal);
                            float f2 = packageAmount;
//                            dBHelper = dBHelper;
//                            dBHelper.addActivePackage(new ActivePackage(1, packageAmount, f2, Long.parseLong(boughtOnText), Long.parseLong(expireOnText)));
//                            dBHelper.close();
                        } else {
                            return;
                        }
                    } catch (Exception e422) {
                        Log.e(TAG, "Error parsing data package bought message - " + e422.getMessage());
                    }
                }
            }
        }
    }
}
//                if (message.contains(afterReceivedDataPackages)) {
//                    if (message.contains("telecom.")) {
//                        try {
//                            int package_duration2;
//                            package_amount = message.substring((message.indexOf(afterReceivedDataPackages) + afterReceivedDataPackages.length()) + 1, message.lastIndexOf("for"));
//                            package_duration = message.substring(message.indexOf("for") + 4, message.indexOf(" from"));
//                            package_amount = package_amount.replace("mb", BuildConfig.FLAVOR);
//                            Log.d("Data Received Parsed", package_amount);
//                            double dataPackageReceived = Double.parseDouble(package_amount);
//                            if (package_duration.contains("hours")) {
//                                package_duration2 = 1;
//                            } else {
//                                package_duration2 = Integer.parseInt(package_duration.replaceAll(" ", BuildConfig.FLAVOR).replace("days", BuildConfig.FLAVOR));
//                            }
//                            Calendar expiryDate = Calendar.getInstance();
//                            expiryDate.add(5, package_duration2);
//                            String expiresOnText = GebetaUtils.calendarToString(expiryDate, true);
//                            boughtOnText = GebetaUtils.calendarToString(Calendar.getInstance(), true);
//                            if (this.sharedPreferences.getBoolean("onDataPackage", false)) {
//                                this.editor.putFloat("dataPackageAvailable", ((float) dataPackageReceived) + this.sharedPreferences.getFloat("dataPackageAvailable", 0.0f));
//                                this.editor.putFloat("dataPackageMax", ((float) dataPackageReceived) + this.sharedPreferences.getFloat("dataPackageMax", 0.0f));
//                            } else {
//                                this.editor.putFloat("dataPackageAvailable", (float) dataPackageReceived);
//                                this.editor.putBoolean("onDataPackage", true);
//                                this.editor.putFloat("dataPackageMax", (float) dataPackageReceived);
//                            }
//                            this.editor.putString("dataExpiresOn", expiresOnText);
//                            this.editor.putBoolean("onDataPackage", true);
//                            this.editor.putString("dataBoughtOn", boughtOnText);
//                            this.editor.apply();
//                            packageAmount = (float) dataPackageReceived;
//                            ActivePackage activePackage2 = new ActivePackage(1, packageAmount, packageAmount, Long.parseLong(boughtOnText), Long.parseLong(expiresOnText));
//                            dBHelper = new DBHelper(context.getApplicationContext());
//                            dBHelper.addActivePackage(activePackage2);
//                            dBHelper.close();
//                        } catch (Exception e4222) {
//                            Log.e(TAG, "Error parsing gift data package received message " + e4222.getMessage());
//                        }
//                    }
//                }
//                if (message.contains(afterEnquireData)) {
//                    if (message.contains("telecom.")) {
//                        while (message.contains(afterEnquireData)) {
//                            dataMessage = message.substring(message.indexOf(afterEnquireData), message.indexOf(afterEnquireData) + CodeEmitter.NEG);
//                            str = dataMessage;
//                            dataMessage = str.substring(0, dataMessage.indexOf(";") + 1);
//                            Log.d("Data Message", dataMessage);
//                            packageAmount2 = dataMessage.substring((dataMessage.indexOf(afterEnquireData) + afterEnquireData.length()) + 1, dataMessage.indexOf(" for "));
//                            if (packageAmount2.contains("gb")) {
//                                packageAmount2 = String.valueOf(Double.parseDouble(packageAmount2.replaceAll(" ", BuildConfig.FLAVOR).replaceAll("gb", BuildConfig.FLAVOR)) * 1000.0d);
//                            } else {
//                                try {
//                                    packageAmount2 = packageAmount2.replaceAll(" ", BuildConfig.FLAVOR).replaceAll("mb", BuildConfig.FLAVOR);
//                                } catch (Exception e42222) {
//                                    try {
//                                        Log.e(" Data Enquire", BuildConfig.FLAVOR + e42222.getMessage());
//                                        Log.e("Data Enquire", message.substring(message.indexOf(afterEnquireData), message.indexOf(afterEnquireData) + CodeEmitter.NEG));
//                                    } catch (Exception f3) {
//                                        Log.e("Data Failed", "I failed at failing. " + f3.getMessage());
//                                    }
//                                }
//                            }
//                            packageAvailable = dataMessage.substring(dataMessage.indexOf("is") + 3, dataMessage.indexOf("with")).replaceAll("mb", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR);
//                            str = dataMessage;
//                            dataExpiresOn = str.substring(dataMessage.indexOf("on ") + 3, dataMessage.indexOf(";") - 2).replaceAll(" at ", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR).replaceAll("-", BuildConfig.FLAVOR).replaceAll(":", BuildConfig.FLAVOR);
//                            purchasedOn = dataMessage.substring(dataMessage.indexOf("for") + 4, dataMessage.indexOf("is"));
//                            if (purchasedOn.contains("days")) {
//                                purchasedOn = purchasedOn.replaceAll(" ", BuildConfig.FLAVOR).replaceAll("days", BuildConfig.FLAVOR);
//                            } else {
//                                purchasedOn = "1";
//                            }
//                            expiresOnCalendar = GebetaUtils.stringToCalendar(dataExpiresOn, true);
//                            expiresOnCalendar.add(5, Integer.parseInt(purchasedOn) * -1);
//                            purchasedOn = GebetaUtils.calendarToString(expiresOnCalendar, true);
//                            if (this.sharedPreferences.getBoolean("onDataPackage", false)) {
//                                previousAmount = this.sharedPreferences.getFloat("dataPackageAvailable", 0.0f);
//                                float previousPackageMax = this.sharedPreferences.getFloat("dataPackageMax", 0.0f);
//                                this.editor.putFloat("dataPackageAvailable", Float.parseFloat(packageAvailable) + previousAmount);
//                                this.editor.putFloat("dataPackageMax", Float.parseFloat(packageAmount2) + previousPackageMax);
//                            } else {
//                                this.editor.putFloat("dataPackageAvailable", Float.parseFloat(packageAvailable));
//                                this.editor.putString("dataExpiresOn", dataExpiresOn);
//                                this.editor.putString("dataBoughtOn", purchasedOn);
//                                this.editor.putFloat("dataPackageMax", Float.parseFloat(packageAmount2));
//                                this.editor.putBoolean("onDataPackage", true);
//                            }
//                            activePackage = new ActivePackage(1, Float.parseFloat(packageAvailable), Float.parseFloat(packageAmount2), Long.parseLong(purchasedOn), Long.parseLong(dataExpiresOn));
//                            dBHelper = new DBHelper(context.getApplicationContext());
//                            dBHelper.addActivePackage(activePackage);
//                            dBHelper.close();
//                            this.editor.apply();
//                            message = message.replaceFirst(dataMessage, BuildConfig.FLAVOR);
//                        }
//                    }
//                }
//                if (message.contains(afterEnquireData2)) {
//                    if (message.contains("telecom.")) {
//                        while (message.contains(afterEnquireData2)) {
//                            dataMessage = message.substring(message.indexOf(afterEnquireData2), message.indexOf(afterEnquireData2) + CodeEmitter.OR);
//                            str = dataMessage;
//                            dataMessage = str.substring(dataMessage.indexOf(afterEnquireData2), dataMessage.indexOf(";") + 1);
//                            Log.d("Data Message", dataMessage);
//                            packageAmount2 = dataMessage.substring((dataMessage.indexOf(afterEnquireData2) + afterEnquireData2.length()) + 1, dataMessage.indexOf(" to be "));
//                            if (packageAmount2.contains("gb")) {
//                                packageAmount2 = String.valueOf(Double.parseDouble(packageAmount2.replaceAll(" ", BuildConfig.FLAVOR).replaceAll("gb", BuildConfig.FLAVOR)) * 1000.0d);
//                            } else {
//                                try {
//                                    packageAmount2 = packageAmount2.replaceAll(" ", BuildConfig.FLAVOR).replaceAll("mb", BuildConfig.FLAVOR);
//                                } catch (Exception e422222) {
//                                    Log.e(TAG, "DATA Package message parsing error " + e422222.getMessage());
//                                }
//                            }
//                            packageAvailable = dataMessage.substring(dataMessage.indexOf("is") + 3, dataMessage.lastIndexOf("with")).replaceAll("mb", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR);
//                            str = dataMessage;
//                            dataExpiresOn = str.substring(dataMessage.indexOf("on ") + 3, dataMessage.indexOf(";") - 2).replaceAll(" at ", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR).replaceAll("-", BuildConfig.FLAVOR).replaceAll(":", BuildConfig.FLAVOR);
//                            purchasedOn = dataMessage.substring(dataMessage.indexOf("after") + 6, dataMessage.indexOf("is"));
//                            if (purchasedOn.contains("days")) {
//                                purchasedOn = purchasedOn.replaceAll(" ", BuildConfig.FLAVOR).replaceAll("days", BuildConfig.FLAVOR);
//                            } else {
//                                purchasedOn = "1";
//                            }
//                            expiresOnCalendar = GebetaUtils.stringToCalendar(dataExpiresOn, true);
//                            expiresOnCalendar.add(5, Integer.parseInt(purchasedOn) * -1);
//                            purchasedOn = GebetaUtils.calendarToString(expiresOnCalendar, true);
//                            if (this.sharedPreferences.getBoolean("onDataPackage", false)) {
//                                previousAmount = this.sharedPreferences.getFloat("dataPackageAvailable", 0.0f);
//                                float previousMax2 = this.sharedPreferences.getFloat("dataPackageMax", 0.0f);
//                                this.editor.putFloat("dataPackageAvailable", Float.parseFloat(packageAvailable) + previousAmount);
//                                this.editor.putFloat("dataPackageMax", Float.parseFloat(packageAmount2) + previousMax2);
//                            } else {
//                                this.editor.putFloat("dataPackageAvailable", Float.parseFloat(packageAvailable));
//                                this.editor.putBoolean("onDataPackage", true);
//                                this.editor.putFloat("dataPackageMax", Float.parseFloat(packageAmount2));
//                                this.editor.putString("dataExpiresOn", dataExpiresOn);
//                                this.editor.putString("dataBoughtOn", purchasedOn);
//                            }
//                            activePackage = new ActivePackage(1, Float.parseFloat(packageAvailable), Float.parseFloat(packageAmount2), Long.parseLong(purchasedOn), Long.parseLong(dataExpiresOn));
//                            dBHelper = new DBHelper(context.getApplicationContext());
//                            dBHelper.addActivePackage(activePackage);
//                            dBHelper.close();
//                            this.editor.apply();
//                            message = message.replaceAll(dataMessage, BuildConfig.FLAVOR);
//                        }
//                    }
//                }
//                if (message.contains(finishedDataPackage)) {
//                    try {
//                        Log.d(TAG, "Package expired message received");
//                        new USSD(context).remainingBalance();
//                    } catch (Exception e5) {
//                        Log.e(TAG, "Error in parsing package expired message");
//                    }
//                }
//                if (message.contains(afterDataSent)) {
//                    try {
//                        package_amount = message.substring((message.indexOf(afterDataSent) + afterDataSent.length()) + 1, message.indexOf(" for"));
//                        package_duration = message.substring(message.indexOf("for") + 4, message.indexOf(" to "));
//                        package_to = message.substring(message.indexOf("to ") + 3, message.indexOf(" .thank you."));
//                        if (package_amount.contains("mb")) {
//                            package_amount = package_amount.replace("mb", BuildConfig.FLAVOR);
//                        } else {
//                            package_amount = String.valueOf(Integer.parseInt(package_amount.replace("gb", BuildConfig.FLAVOR)) * 1000);
//                        }
//                        if (package_duration.contains("hours")) {
//                            package_duration = "1";
//                        } else {
//                            if (package_duration.contains("days")) {
//                                package_duration = package_duration.replace("days", BuildConfig.FLAVOR).replace(" ", BuildConfig.FLAVOR);
//                            }
//                        }
//                        dBHelper = new DBHelper(context);
//                        price = dBHelper.getPrice(package_duration, package_amount, context, 1);
//                        Log.d("Gebeta Data Price", String.valueOf(price));
//                        this.editor.putFloat("currentBalance", (float) (((double) this.sharedPreferences.getFloat("currentBalance", 0.0f)) - price));
//                        this.editor.apply();
//                        sqLiteDatabase = dBHelper.getWritableDatabase();
//                        contentValues = new ContentValues();
//                        contentValues.put("type", Integer.valueOf(4));
//                        contentValues.put("amount", Double.valueOf(Double.parseDouble(package_amount)));
//                        contentValues.put("price", Double.valueOf(price));
//                        contentValues.put("too", package_to);
//                        contentValues.put("peak", Integer.valueOf(0));
//                        contentValues.put("date", Long.valueOf(Long.parseLong(this.date)));
//                        contentValues2 = contentValues;
//                        contentValues2.put("time", this.time);
//                        sqLiteDatabase.insert("gebeta_log", "type", contentValues);
//                        sqLiteDatabase.close();
//                        dBHelper.close();
//                    } catch (Exception e4222222) {
//                        Log.e("SMS Receiver", BuildConfig.FLAVOR + e4222222.getMessage());
//                    }
//                }
//                if (message.contains(afterSmsBought)) {
//                    if (message.contains("telecom.")) {
//                        try {
//                            package_amount = message.substring((message.indexOf(afterSmsBought) + afterSmsBought.length()) + 1, message.lastIndexOf(" sms to be"));
//                            package_duration = message.substring(message.indexOf("after ") + 6, message.indexOf(" is added"));
//                            if (package_duration.contains("hours")) {
//                                package_duration = "1";
//                            } else {
//                                package_duration = package_duration.replace("days", BuildConfig.FLAVOR).replace(" ", BuildConfig.FLAVOR);
//                            }
//                            smsRemaining = Integer.parseInt(package_amount);
//                            calendar = Calendar.getInstance();
//                            calendar.add(5, Integer.parseInt(package_duration));
//                            boughtOn = GebetaUtils.calendarToString(Calendar.getInstance(), true);
//                            expiresOn = GebetaUtils.calendarToString(calendar, true);
//                            Log.d(TAG, "New SMS package - " + package_amount);
//                            if (this.sharedPreferences.getBoolean("onSmsPackage", false)) {
//                                previousAvailable = this.sharedPreferences.getInt("smsPackageAvailable", 0);
//                                previousMax = this.sharedPreferences.getInt("smsPackageMax", 0);
//                                this.editor.putInt("smsPackageAvailable", smsRemaining + previousAvailable);
//                                this.editor.putInt("smsPackageMax", smsRemaining + previousMax);
//                            } else {
//                                this.editor.putInt("smsPackageAvailable", smsRemaining);
//                                this.editor.putInt("smsPackageMax", smsRemaining);
//                                this.editor.putString("smsPackageBoughtOn", boughtOn);
//                                this.editor.putString("smsPackageExpiresOn", expiresOn);
//                                this.editor.putBoolean("onSmsPackage", true);
//                            }
//                            this.editor.apply();
//                            dBHelper = new DBHelper(context.getApplicationContext());
//                            price = dBHelper.getPrice(package_duration, package_amount, context.getApplicationContext(), 2);
//                            Log.d("Gebeta SMS Price", String.valueOf(price));
//                            this.editor.putFloat("currentBalance", (float) (((double) this.sharedPreferences.getFloat("currentBalance", 0.0f)) - price));
//                            this.editor.apply();
//                            sqLiteDatabase = dBHelper.getWritableDatabase();
//                            contentValues = new ContentValues();
//                            contentValues.put("type", Integer.valueOf(8));
//                            contentValues.put("amount", Double.valueOf(Double.parseDouble(package_amount)));
//                            contentValues.put("price", Double.valueOf(price));
//                            contentValues.put("too", "-");
//                            contentValues.put("peak", Integer.valueOf(0));
//                            contentValues.put("date", Long.valueOf(Long.parseLong(this.date)));
//                            contentValues2 = contentValues;
//                            contentValues2.put("time", this.time);
//                            sqLiteDatabase.insert("gebeta_log", "type", contentValues);
//                            sqLiteDatabase.close();
//                            dBHelper.addActivePackage(new ActivePackage(2, (float) smsRemaining, (float) smsRemaining, Long.parseLong(boughtOn), Long.parseLong(expiresOn)));
//                            dBHelper.close();
//                        } catch (Exception e42222222) {
//                            Log.e("SMS Receiver", BuildConfig.FLAVOR + e42222222.getMessage());
//                        }
//                    }
//                }
//                if (message.contains(afterSmsReceived)) {
//                    if (message.contains("telecom.")) {
//                        try {
//                            smsPackageMax = message.substring((message.indexOf(afterSmsReceived) + afterSmsReceived.length()) + 1, message.indexOf(" for"));
//                            String packageDuration = message.substring(message.indexOf("for") + 4, message.indexOf(" from"));
//                            if (packageDuration.contains("hours")) {
//                                packageDuration = "1";
//                            } else {
//                                packageDuration = packageDuration.replaceAll(" ", BuildConfig.FLAVOR).replaceAll("days", BuildConfig.FLAVOR);
//                            }
//                            calendar = Calendar.getInstance();
//                            calendar.add(5, Integer.parseInt(packageDuration));
//                            int smsPackage = Integer.parseInt(smsPackageMax.replace("sms", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR));
//                            boughtOn = GebetaUtils.calendarToString(Calendar.getInstance(), true);
//                            expiresOn = GebetaUtils.calendarToString(calendar, true);
//                            if (this.sharedPreferences.getBoolean("onSmsPackage", false)) {
//                                previousAvailable = this.sharedPreferences.getInt("smsPackageAvailable", 0);
//                                previousMax = this.sharedPreferences.getInt("smsPackageMax", 0);
//                                this.editor.putInt("smsPackageAvailable", smsPackage + previousAvailable);
//                                this.editor.putInt("smsPackageMax", smsPackage + previousMax);
//                            } else {
//                                this.editor.putInt("smsPackageAvailable", smsPackage);
//                                this.editor.putInt("smsPackageMax", smsPackage);
//                                this.editor.putString("smsPackageBoughtOn", boughtOn);
//                                this.editor.putString("smsPackageExpiresOn", expiresOn);
//                                this.editor.putBoolean("onSmsPackage", true);
//                            }
//                            this.editor.apply();
//                            activePackage = new ActivePackage(2, (float) smsPackage, (float) smsPackage, Long.parseLong(boughtOn), Long.parseLong(expiresOn));
//                            dBHelper = new DBHelper(context.getApplicationContext());
//                            dBHelper.addActivePackage(activePackage);
//                            dBHelper.close();
//                        } catch (Exception e422222222) {
//                            Log.e(TAG, "Gift SMS package message parsing error " + e422222222.getMessage());
//                        }
//                    }
//                }
//                if (message.contains(afterEnquireSms)) {
//                    do {
//                        isString = "is";
//                        smsMessage = message.substring(message.indexOf(afterEnquireSms), message.indexOf(afterEnquireSms) + Opcodes.ISHR);
//                        str = smsMessage;
//                        smsMessage = str.substring(smsMessage.indexOf(afterEnquireSms), smsMessage.indexOf(";") + 1);
//                        Log.d(TAG, "After Enquire Message: " + smsMessage);
//                        String smsPackageOriginal = smsMessage.substring((smsMessage.indexOf(afterEnquireSms) + afterEnquireSms.length()) + 1, smsMessage.lastIndexOf(" sms to be"));
//                        String smsPackageRemaining = smsMessage.substring((smsMessage.indexOf(isString) + isString.length()) + 1, smsMessage.lastIndexOf(" sms with"));
//                        Log.d("Package Before", smsPackageOriginal);
//                        Log.d("Package After", smsPackageRemaining);
//                        smsRemaining = Integer.parseInt(smsPackageRemaining);
//                        smsMax = Integer.parseInt(smsPackageOriginal);
//                        str = smsMessage;
//                        smsExpiryDate = str.substring(smsMessage.indexOf("on ") + 3, smsMessage.indexOf(";") - 2).replaceAll(" at ", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR).replaceAll("-", BuildConfig.FLAVOR).replaceAll(":", BuildConfig.FLAVOR);
//                        purchasedOn = smsMessage.substring(smsMessage.indexOf("after") + 6, smsMessage.indexOf("is"));
//                        if (purchasedOn.contains("days")) {
//                            purchasedOn = purchasedOn.replaceAll(" ", BuildConfig.FLAVOR).replaceAll("days", BuildConfig.FLAVOR);
//                        } else {
//                            try {
//                                purchasedOn = "1";
//                            } catch (Exception e4222222222) {
//                                Log.e(TAG, "Error parsing bought sms enquire " + e4222222222.getMessage());
//                            }
//                        }
//                        expiresOnCalendar = GebetaUtils.stringToCalendar(smsExpiryDate, true);
//                        expiresOnCalendar.add(5, Integer.parseInt(purchasedOn) * -1);
//                        purchasedOn = GebetaUtils.calendarToString(expiresOnCalendar, true);
//                        if (this.sharedPreferences.getBoolean("onSmsPackage", false)) {
//                            this.editor.putInt("smsPackageAvailable", this.sharedPreferences.getInt("smsPackageAvailable", 0) + smsRemaining);
//                            this.editor.putInt("smsPackageMax", this.sharedPreferences.getInt("smsPackageMax", 0) + smsMax);
//                        } else {
//                            this.editor.putInt("smsPackageAvailable", smsRemaining);
//                            this.editor.putInt("smsPackageMax", smsMax);
//                        }
//                        this.editor.putBoolean("onSmsPackage", true);
//                        this.editor.putString("smsPackageExpiresOn", smsExpiryDate);
//                        this.editor.putString("smsPackageBoughtOn", purchasedOn);
//                        this.editor.apply();
//                        activePackage = new ActivePackage(2, (float) smsRemaining, (float) smsMax, Long.parseLong(purchasedOn), Long.parseLong(smsExpiryDate));
//                        dBHelper = new DBHelper(context.getApplicationContext());
//                        dBHelper.addActivePackage(activePackage);
//                        dBHelper.close();
//                        message = message.replaceFirst(smsMessage, BuildConfig.FLAVOR);
//                    } while (message.contains(afterEnquireSms));
//                }
//                if (message.contains(afterEnquireSms2)) {
//                    do {
//                        isString = "is";
//                        smsMessage = message.substring(message.indexOf(afterEnquireSms2), message.indexOf(afterEnquireSms2) + R.styleable.AppCompatTheme_windowFixedWidthMajor);
//                        str = smsMessage;
//                        smsMessage = str.substring(smsMessage.indexOf(afterEnquireSms2), smsMessage.indexOf(";") + 1);
//                        Log.d(TAG, "SMS Message after gift sms enquire: " + smsMessage);
//                        smsPackageMax = smsMessage.substring((smsMessage.indexOf(afterEnquireSms2) + afterEnquireSms2.length()) + 1, smsMessage.indexOf(" sms for"));
//                        smsPackageAvailable = smsMessage.substring((smsMessage.indexOf(isString) + isString.length()) + 1, smsMessage.lastIndexOf(" sms"));
//                        str = smsMessage;
//                        smsExpiryDate = str.substring(smsMessage.indexOf("on ") + 3, smsMessage.indexOf(";") - 2).replaceAll(" at ", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR).replaceAll("-", BuildConfig.FLAVOR).replaceAll(":", BuildConfig.FLAVOR);
//                        purchasedOn = smsMessage.substring(smsMessage.indexOf("for") + 4, smsMessage.indexOf("is"));
//                        if (purchasedOn.contains("days")) {
//                            purchasedOn = purchasedOn.replaceAll(" ", BuildConfig.FLAVOR).replaceAll("days", BuildConfig.FLAVOR);
//                        } else {
//                            try {
//                                purchasedOn = "1";
//                            } catch (Exception e42222222222) {
//                                Log.e(TAG, "Error parsing gift sms enquire message " + e42222222222.getMessage());
//                            }
//                        }
//                        expiresOnCalendar = GebetaUtils.stringToCalendar(smsExpiryDate, true);
//                        expiresOnCalendar.add(5, Integer.parseInt(purchasedOn) * -1);
//                        purchasedOn = GebetaUtils.calendarToString(expiresOnCalendar, true);
//                        smsRemaining = Integer.parseInt(smsPackageAvailable);
//                        smsMax = Integer.parseInt(smsPackageMax);
//                        if (this.sharedPreferences.getBoolean("onSmsPackage", false)) {
//                            this.editor.putInt("smsPackageAvailable", this.sharedPreferences.getInt("smsPackageAvailable", 0) + smsRemaining);
//                            this.editor.putInt("smsPackageMax", this.sharedPreferences.getInt("smsPackageMax", 0) + smsMax);
//                        } else {
//                            this.editor.putInt("smsPackageAvailable", smsRemaining);
//                            this.editor.putInt("smsPackageMax", smsMax);
//                        }
//                        this.editor.putString("smsPackageExpiresOn", smsExpiryDate);
//                        this.editor.putString("smsPackageBoughtOn", purchasedOn);
//                        this.editor.apply();
//                        activePackage = new ActivePackage(2, (float) smsRemaining, (float) smsMax, Long.parseLong(purchasedOn), Long.parseLong(smsExpiryDate));
//                        dBHelper = new DBHelper(context.getApplicationContext());
//                        dBHelper.addActivePackage(activePackage);
//                        dBHelper.close();
//                        message = message.replaceFirst(smsMessage, BuildConfig.FLAVOR);
//                    } while (message.contains(afterEnquireSms2));
//                }
//                if (message.contains(afterSmsSent)) {
//                    try {
//                        package_amount = message.substring((message.indexOf(afterSmsSent) + afterSmsSent.length()) + 1, message.lastIndexOf(" sms"));
//                        package_duration = message.substring(message.indexOf("for") + 4, message.indexOf(" to "));
//                        package_to = message.substring(message.indexOf("to ") + 3, message.indexOf(" .thank you."));
//                        Log.d("Package To", package_to);
//                        if (package_duration.contains("hours")) {
//                            package_duration = "1";
//                        } else {
//                            if (package_duration.contains("days")) {
//                                package_duration = package_duration.replaceAll(" ", BuildConfig.FLAVOR).replaceAll("days", BuildConfig.FLAVOR);
//                            }
//                        }
//                        dBHelper = new DBHelper(context.getApplicationContext());
//                        price2 = Double.valueOf(dBHelper.getPrice(package_duration, package_amount, context.getApplicationContext(), 2));
//                        Log.d("Gebeta Sent Package", "SMS Package Sent, Price: " + String.valueOf(price2));
//                        this.editor.putFloat("currentBalance", (float) (((double) this.sharedPreferences.getFloat("currentBalance", 0.0f)) - price2.doubleValue()));
//                        this.editor.apply();
//                        sqLiteDatabase = dBHelper.getWritableDatabase();
//                        contentValues = new ContentValues();
//                        contentValues.put("type", Integer.valueOf(5));
//                        contentValues.put("amount", Double.valueOf(Double.parseDouble(package_amount)));
//                        contentValues.put("price", price2);
//                        contentValues.put("too", package_to);
//                        contentValues.put("peak", Integer.valueOf(0));
//                        contentValues.put("date", Long.valueOf(Long.parseLong(this.date)));
//                        contentValues2 = contentValues;
//                        contentValues2.put("time", this.time);
//                        sqLiteDatabase.insert("gebeta_log", "type", contentValues);
//                        sqLiteDatabase.close();
//                        dBHelper.close();
//                    } catch (Exception e422222222222) {
//                        Log.e("SMS Receiver", BuildConfig.FLAVOR + e422222222222.getMessage());
//                    }
//                }
//                if (message.contains(afterVoiceReceived)) {
//                    try {
//                        voicePackage = message.substring((message.indexOf(afterVoiceReceived) + afterVoiceReceived.length()) + 1, message.indexOf(" minutes"));
//                        Log.d("Gebeta Voice Package", voicePackage);
//                        float voicePackageMax2 = Float.parseFloat(voicePackage);
//                        this.editor.putFloat("voicePackageMax", voicePackageMax2);
//                        voicePackageMax2 *= 60.0f;
//                        this.editor.putFloat("voicePackageAvailable", voicePackageMax2);
//                        this.editor.putString("voicePackageDate", GebetaUtils.calendarToString(Calendar.getInstance(), true));
//                        this.editor.putBoolean("onVoicePackage", true);
//                        this.editor.apply();
//                        boughtOn = GebetaUtils.calendarToString(Calendar.getInstance(), true);
//                        expiresOnCal = Calendar.getInstance();
//                        expiresOnCal.set(11, 6);
//                        if (Long.parseLong(GebetaUtils.calendarToString(Calendar.getInstance(), true)) > Long.parseLong(GebetaUtils.calendarToString(expiresOnCal, true))) {
//                            expiresOnCal.add(5, 1);
//                        }
//                        activePackage = new ActivePackage(4, voicePackageMax2, voicePackageMax2 / 60.0f, Long.parseLong(boughtOn), Long.parseLong(GebetaUtils.calendarToString(expiresOnCal, true)));
//                        dBHelper = new DBHelper(context.getApplicationContext());
//                        dBHelper.addActivePackage(activePackage);
//                        dBHelper.close();
//                    } catch (Exception e4222222222222) {
//                        Log.e(TAG, "Error parsing message after receiving night voice package" + e4222222222222.getMessage());
//                    }
//                }
//                if (message.contains(afterMonVoiceBought)) {
//                    if (message.contains("telecom.")) {
//                        try {
//                            voicePackage = message.substring((message.indexOf(afterMonVoiceBought) + afterMonVoiceBought.length()) + 1, message.indexOf("min")).replaceAll(" ", BuildConfig.FLAVOR);
//                            dBHelper = new DBHelper(context.getApplicationContext());
//                            price2 = Double.valueOf(dBHelper.getPrice("30", voicePackage, context.getApplicationContext(), 3));
//                            Log.d(TAG, "Bought MonVoicePackage, Price: " + String.valueOf(price2));
//                            this.editor.putFloat("currentBalance", (float) (((double) this.sharedPreferences.getFloat("currentBalance", 0.0f)) - price2.doubleValue()));
//                            this.editor.apply();
//                            sqLiteDatabase = dBHelper.getWritableDatabase();
//                            contentValues = new ContentValues();
//                            contentValues.put("type", Integer.valueOf(9));
//                            contentValues.put("amount", Double.valueOf(Double.parseDouble(voicePackage)));
//                            contentValues.put("price", price2);
//                            contentValues.put("too", "-");
//                            contentValues.put("peak", Integer.valueOf(0));
//                            contentValues.put("date", Long.valueOf(Long.parseLong(this.date)));
//                            contentValues2 = contentValues;
//                            contentValues2.put("time", this.time);
//                            sqLiteDatabase.insert("gebeta_log", "type", contentValues);
//                            sqLiteDatabase.close();
//                            date = Calendar.getInstance();
//                            purchasedOn = GebetaUtils.calendarToString(date, true);
//                            date.add(5, 30);
//                            expiresOn = GebetaUtils.calendarToString(date, true);
//                            voicePackageMonMax = Float.parseFloat(voicePackage);
//                            isOnSms = this.sharedPreferences.getBoolean("onSmsPackage", false);
//                            if (this.sharedPreferences.getBoolean("onDataPackage", false)) {
//                                availableData = this.sharedPreferences.getFloat("dataPackageAvailable", 0.0f) + 5.0f;
//                                this.editor.putFloat("dataPackageMax", this.sharedPreferences.getFloat("dataPackageMax", 0.0f) + 5.0f);
//                                this.editor.putFloat("dataPackageAvailable", availableData);
//                                this.editor.apply();
//                            } else {
//                                this.editor.putBoolean("onDataPackage", true);
//                                this.editor.putString("dataExpiresOn", expiresOn);
//                                this.editor.putString("dataBoughtOn", purchasedOn);
//                                this.editor.putFloat("dataPackageAvailable", 5.0f);
//                                this.editor.putFloat("dataPackageMax", 5.0f);
//                                this.editor.apply();
//                            }
//                            dBHelper.addActivePackage(new ActivePackage(1, 5.0f, 5.0f, Long.parseLong(purchasedOn), Long.parseLong(expiresOn)));
//                            if (voicePackageMonMax > 170.0f) {
//                                smsAvailable = 50;
//                            } else {
//                                int smsAvailable2 = 30;
//                            }
//                            smsMax = smsAvailable;
//                            if (isOnSms) {
//                                smsPackageAvailable2 = this.sharedPreferences.getInt("smsPackageAvailable", 0) + smsAvailable;
//                                this.editor.putInt("smsPackageMax", this.sharedPreferences.getInt("smsPackageMax", 0) + smsMax);
//                                this.editor.putInt("smsPackageAvailable", smsPackageAvailable2);
//                                this.editor.apply();
//                            } else {
//                                this.editor.putInt("smsPackageAvailable", smsAvailable);
//                                this.editor.putInt("smsPackageMax", smsMax);
//                                this.editor.putBoolean("onSmsPackage", true);
//                                this.editor.putString("smsPackageBoughtOn", purchasedOn);
//                                this.editor.putString("smsPackageExpiresOn", expiresOn);
//                                this.editor.apply();
//                            }
//                            dBHelper.addActivePackage(new ActivePackage(2, (float) smsAvailable, (float) smsMax, Long.parseLong(purchasedOn), Long.parseLong(expiresOn)));
//                            secondsAvailable = voicePackageMonMax * 60.0f;
//                            if (this.sharedPreferences.getBoolean("onVoiceMonPackage", false)) {
//                                previousSecondsAvailable = this.sharedPreferences.getFloat("voicePackageMonAvailable", 0.0f);
//                                this.editor.putFloat("voicePackageMonMax", this.sharedPreferences.getFloat("voicePackageMonMax", 0.0f) + voicePackageMonMax);
//                                this.editor.putFloat("voicePackageMonAvailable", previousSecondsAvailable + secondsAvailable);
//                            } else {
//                                this.editor.putFloat("voicePackageMonAvailable", secondsAvailable);
//                                this.editor.putFloat("voicePackageMonMax", voicePackageMonMax);
//                                this.editor.putString("voicePackageMonDate", purchasedOn);
//                                this.editor.putString("voicePackageMonExpDate", expiresOn);
//                                this.editor.putBoolean("onVoiceMonPackage", true);
//                            }
//                            this.editor.apply();
//                            dBHelper = dBHelper;
//                            dBHelper.addActivePackage(new ActivePackage(3, secondsAvailable, voicePackageMonMax, Long.parseLong(purchasedOn), Long.parseLong(expiresOn)));
//                            dBHelper.close();
//                        } catch (Exception e42222222222222) {
//                            Log.e(TAG, "Error parsing Bought Monthly Voice Package message +" + e42222222222222.getMessage());
//                        }
//                    }
//                }
//                if (message.contains(afterMonVoiceReceived)) {
//                    voicePackage = message.substring((message.indexOf(afterMonVoiceReceived) + afterMonVoiceReceived.length()) + 1, message.indexOf(" min"));
//                    voicePackageMonMax = Float.parseFloat(voicePackage);
//                    dBHelper = new DBHelper(context.getApplicationContext());
//                    date = Calendar.getInstance();
//                    purchasedOn = GebetaUtils.calendarToString(date, true);
//                    date.add(5, 30);
//                    expiresOn = GebetaUtils.calendarToString(date, true);
//                    isOnSms = this.sharedPreferences.getBoolean("onSmsPackage", false);
//                    if (this.sharedPreferences.getBoolean("onDataPackage", false)) {
//                        availableData = this.sharedPreferences.getFloat("dataPackageAvailable", 0.0f) + 5.0f;
//                        this.editor.putFloat("dataPackageMax", this.sharedPreferences.getFloat("dataPackageMax", 0.0f) + 5.0f);
//                        this.editor.putFloat("dataPackageAvailable", availableData);
//                        this.editor.apply();
//                    } else {
//                        this.editor.putBoolean("onDataPackage", true);
//                        this.rightNow = Calendar.getInstance();
//                        this.rightNow.add(5, 30);
//                        this.editor.putString("dataExpiresOn", GebetaUtils.calendarToString(this.rightNow, true));
//                        this.editor.putString("dataBoughtOn", GebetaUtils.calendarToString(Calendar.getInstance(), true));
//                        this.editor.putFloat("dataPackageAvailable", 5.0f);
//                        this.editor.putFloat("dataPackageMax", 5.0f);
//                        this.editor.apply();
//                    }
//                    dBHelper.addActivePackage(new ActivePackage(1, 5.0f, 5.0f, Long.parseLong(purchasedOn), Long.parseLong(expiresOn)));
//                    if (voicePackageMonMax > 170.0f) {
//                        smsRemaining = 50;
//                        smsMax = 50;
//                    } else {
//                        smsRemaining = 30;
//                        smsMax = 30;
//                    }
//                    if (isOnSms) {
//                        smsPackageAvailable2 = this.sharedPreferences.getInt("smsPackageAvailable", 0) + smsRemaining;
//                        this.editor.putInt("smsPackageMax", this.sharedPreferences.getInt("smsPackageMax", 0) + smsMax);
//                        this.editor.putInt("smsPackageAvailable", smsPackageAvailable2);
//                        this.editor.apply();
//                    } else {
//                        this.editor.putInt("smsPackageAvailable", smsRemaining);
//                        this.editor.putInt("smsPackageMax", smsMax);
//                        this.editor.putBoolean("onSmsPackage", true);
//                        this.editor.putString("smsPackageBoughtOn", purchasedOn);
//                        this.editor.putString("smsPackageExpiresOn", expiresOn);
//                        this.editor.apply();
//                    }
//                    dBHelper.addActivePackage(new ActivePackage(2, (float) smsRemaining, (float) smsMax, Long.parseLong(purchasedOn), Long.parseLong(expiresOn)));
//                    secondsAvailable = voicePackageMonMax * 60.0f;
//                    if (this.sharedPreferences.getBoolean("onVoiceMonPackage", false)) {
//                        previousSecondsAvailable = this.sharedPreferences.getFloat("voicePackageMonAvailable", 0.0f);
//                        this.editor.putFloat("voicePackageMonMax", this.sharedPreferences.getFloat("voicePackageMonMax", 0.0f) + voicePackageMonMax);
//                        this.editor.putFloat("voicePackageMonAvailable", previousSecondsAvailable + secondsAvailable);
//                    } else {
//                        this.editor.putFloat("voicePackageMonAvailable", secondsAvailable);
//                        this.editor.putFloat("voicePackageMonMax", voicePackageMonMax);
//                        this.editor.putString("voicePackageMonDate", purchasedOn);
//                        this.editor.putString("voicePackageMonExpDate", expiresOn);
//                        this.editor.putBoolean("onVoiceMonPackage", true);
//                    }
//                    dBHelper = dBHelper;
//                    dBHelper.addActivePackage(new ActivePackage(3, secondsAvailable, voicePackageMonMax, Long.parseLong(purchasedOn), Long.parseLong(expiresOn)));
//                    this.editor.apply();
//                    dBHelper.close();
//                    Log.d(TAG, "Monthly Voice Package Received - " + voicePackage);
//                }
//                if (message.contains(afterEnquireVoice2)) {
//                    try {
//                        isString = "is";
//                        andString = "and";
//                        str = message;
//                        voiceMessage = str.substring(message.indexOf(afterEnquireVoice2), message.indexOf(";") + 1);
//                        Log.d(TAG, "Message After Enquire Gift Night Voice Package" + voiceMessage);
//                        voicePackageMax = voiceMessage.substring((voiceMessage.indexOf(afterEnquireVoice2) + afterEnquireVoice2.length()) + 1, voiceMessage.indexOf(" minutes is"));
//                        voiceAvailable = (Float.parseFloat(voiceMessage.substring((voiceMessage.indexOf(isString) + isString.length()) + 1, voiceMessage.indexOf(" minute and"))) * 60.0f) + Float.parseFloat(voiceMessage.substring((voiceMessage.indexOf(andString) + andString.length()) + 1, voiceMessage.indexOf(" second")));
//                        this.editor.putFloat("voicePackageMax", Float.parseFloat(voicePackageMax));
//                        this.editor.putFloat("voicePackageAvailable", voiceAvailable);
//                        this.editor.putBoolean("onVoicePackage", true);
//                        this.editor.apply();
//                        boughtOn = GebetaUtils.calendarToString(Calendar.getInstance(), true);
//                        expiresOnCal = Calendar.getInstance();
//                        expiresOnCal.set(11, 6);
//                        if (Long.parseLong(GebetaUtils.calendarToString(Calendar.getInstance(), true)) > Long.parseLong(GebetaUtils.calendarToString(expiresOnCal, true))) {
//                            expiresOnCal.add(5, 1);
//                        }
//                        new DBHelper(context.getApplicationContext()).addActivePackage(new ActivePackage(4, voiceAvailable, Float.parseFloat(voicePackageMax), Long.parseLong(boughtOn), Long.parseLong(GebetaUtils.calendarToString(expiresOnCal, true))));
//                        message = message.replaceFirst(voiceMessage, BuildConfig.FLAVOR);
//                    } catch (Exception e422222222222222) {
//                        Log.e(TAG, "Error parsing message after enquiring gift night voice package" + e422222222222222.getMessage());
//                    }
//                }
//                if (message.contains(afterMonVoiceEnquire)) {
//                    if (message.contains("telecom.")) {
//                        do {
//                            voiceMessage = message.substring(message.indexOf(afterMonVoiceEnquire), message.indexOf(afterMonVoiceEnquire) + R.styleable.AppCompatTheme_windowFixedHeightMajor);
//                            str = voiceMessage;
//                            voiceMessage = str.substring(0, voiceMessage.indexOf(";") + 1);
//                            Log.d("Voice Message", voiceMessage);
//                            dBHelper = new DBHelper(context.getApplicationContext());
//                            if (voiceMessage.contains("sms")) {
//                                smsAvailable = voiceMessage.substring(voiceMessage.indexOf("is ") + 3, voiceMessage.indexOf(" sms"));
//                                Log.d(TAG, "SMS package from monthly voice package - " + smsAvailable);
//                                message = message.replaceFirst(voiceMessage, BuildConfig.FLAVOR);
//                                str = voiceMessage;
//                                smsExpiryDate = str.substring(voiceMessage.indexOf("on ") + 3, voiceMessage.indexOf(";") - 2).replaceAll(" at ", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR).replaceAll("-", BuildConfig.FLAVOR).replaceAll(":", BuildConfig.FLAVOR);
//                                calendar = GebetaUtils.stringToCalendar(smsExpiryDate, true);
//                                calendar.add(5, -30);
//                                smsBoughtOn = GebetaUtils.calendarToString(calendar, true);
//                                smsRemaining = Integer.parseInt(smsAvailable);
//                                if (smsRemaining <= 30) {
//                                    smsMax = 30;
//                                } else {
//                                    smsMax = 50;
//                                }
//                                if (this.sharedPreferences.getBoolean("onSmsPackage", false)) {
//                                    smsPackageAvailable2 = this.sharedPreferences.getInt("smsPackageAvailable", 0);
//                                    int smsPackageMax2 = this.sharedPreferences.getInt("smsPackageMax", 0);
//                                    this.editor.putInt("smsPackageAvailable", smsPackageAvailable2 + smsRemaining);
//                                    this.editor.putInt("smsPackageMax", smsMax + smsPackageMax2);
//                                } else {
//                                    try {
//                                        this.editor.putInt("smsPackageMax", smsMax);
//                                        this.editor.putInt("smsPackageAvailable", smsRemaining);
//                                        this.editor.putBoolean("onSmsPackage", true);
//                                        this.editor.putString("smsPackageExpiresOn", smsExpiryDate);
//                                        this.editor.putString("smsPackageBoughtOn", smsBoughtOn);
//                                    } catch (Exception e6) {
//                                        Log.d(TAG, "Error parsing message after enquire monthly voice package");
//                                    }
//                                }
//                                dBHelper.addActivePackage(new ActivePackage(2, (float) smsRemaining, (float) smsMax, Long.parseLong(smsBoughtOn), Long.parseLong(smsExpiryDate)));
//                                this.editor.apply();
//                            } else {
//                                if (voiceMessage.contains("mb")) {
//                                    dataAvailable = voiceMessage.substring(voiceMessage.indexOf("is ") + 3, voiceMessage.indexOf(" mb"));
//                                    Log.d("Voice Data", dataAvailable);
//                                    message = message.replaceFirst(voiceMessage, BuildConfig.FLAVOR);
//                                    isOnData = this.sharedPreferences.getBoolean("onDataPackage", false);
//                                    str = voiceMessage;
//                                    dataExpiresOn = str.substring(voiceMessage.indexOf("on ") + 3, voiceMessage.indexOf(";") - 2).replaceAll(" at ", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR).replaceAll("-", BuildConfig.FLAVOR).replaceAll(":", BuildConfig.FLAVOR);
//                                    calendar = GebetaUtils.stringToCalendar(dataExpiresOn, true);
//                                    calendar.add(5, -30);
//                                    dataBoughtOn = GebetaUtils.calendarToString(calendar, true);
//                                    if (isOnData) {
//                                        float maxData = this.sharedPreferences.getFloat("dataPackageMax", 0.0f) + 5.0f;
//                                        this.editor.putFloat("dataPackageAvailable", this.sharedPreferences.getFloat("dataPackageAvailable", 0.0f) + Float.parseFloat(dataAvailable));
//                                        this.editor.putFloat("dataPackageMax", maxData);
//                                        this.editor.apply();
//                                    } else {
//                                        this.editor.putBoolean("onDataPackage", true);
//                                        this.editor.putString("dataExpiresOn", dataExpiresOn);
//                                        this.editor.putString("dataBoughtOn", dataBoughtOn);
//                                        this.editor.putFloat("dataPackageAvailable", Float.parseFloat(dataAvailable));
//                                        this.editor.putFloat("dataPackageMax", 5.0f);
//                                        this.editor.apply();
//                                    }
//                                    dBHelper.addActivePackage(new ActivePackage(1, Float.parseFloat(dataAvailable), 5.0f, Long.parseLong(dataBoughtOn), Long.parseLong(dataExpiresOn)));
//                                } else {
//                                    if (voiceMessage.contains("minute")) {
//                                        minuteAvailable = voiceMessage.substring(voiceMessage.indexOf("is ") + 3, voiceMessage.indexOf(" minute"));
//                                        String secondsAvailable2 = voiceMessage.substring(voiceMessage.indexOf("and ") + 4, voiceMessage.indexOf(" second"));
//                                        voicePackageMax = voiceMessage.substring(afterMonVoiceEnquire.length() + 1, voiceMessage.indexOf(" min")).replaceAll(" ", BuildConfig.FLAVOR);
//                                        str = voiceMessage;
//                                        voiceExpiresOn = str.substring(voiceMessage.indexOf("on ") + 3, voiceMessage.indexOf(";") - 2).replaceAll(" at ", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR).replaceAll("-", BuildConfig.FLAVOR).replaceAll(":", BuildConfig.FLAVOR);
//                                        calendar = GebetaUtils.stringToCalendar(voiceExpiresOn, true);
//                                        calendar.add(5, -30);
//                                        voiceBoughOn = GebetaUtils.calendarToString(calendar, true);
//                                        message = message.replaceFirst(voiceMessage, BuildConfig.FLAVOR);
//                                        availableSeconds = Float.parseFloat(secondsAvailable2) + (Float.parseFloat(minuteAvailable) * 60.0f);
//                                        if (this.sharedPreferences.getBoolean("onVoiceMonPackage", false)) {
//                                            this.editor.putFloat("voicePackageMonAvailable", this.sharedPreferences.getFloat("voicePackageMonAvailable", 0.0f) + availableSeconds);
//                                            this.editor.putFloat("voicePackageMonMax", Float.parseFloat(voicePackageMax) + this.sharedPreferences.getFloat("voicePackageMonMax", 0.0f));
//                                        } else {
//                                            this.editor.putBoolean("onVoiceMonPackage", true);
//                                            this.editor.putFloat("voicePackageMonAvailable", availableSeconds);
//                                            this.editor.putFloat("voicePackageMonMax", Float.parseFloat(voicePackageMax));
//                                            this.editor.putString("voicePackageMonExpDate", voiceExpiresOn);
//                                            this.editor.putString("voicePackageMonDate", voiceBoughOn);
//                                        }
//                                        this.editor.apply();
//                                        dBHelper.addActivePackage(new ActivePackage(3, availableSeconds, Float.parseFloat(voicePackageMax), Long.parseLong(voiceBoughOn), Long.parseLong(voiceExpiresOn)));
//                                    }
//                                }
//                            }
//                            dBHelper.close();
//                        } while (message.contains(afterMonVoiceEnquire));
//                    }
//                }
//                if (message.contains(afterMonVoiceEnquire2)) {
//                    if (message.contains("telecom.")) {
//                        do {
//                            voiceMessage = message.substring(message.indexOf(afterMonVoiceEnquire2), message.indexOf(afterMonVoiceEnquire2) + R.styleable.AppCompatTheme_toolbarStyle);
//                            str = voiceMessage;
//                            voiceMessage = str.substring(0, voiceMessage.indexOf(";") + 1);
//                            dBHelper = new DBHelper(context.getApplicationContext());
//                            if (voiceMessage.contains("sms")) {
//                                smsAvailable = voiceMessage.substring(voiceMessage.indexOf("is ") + 3, voiceMessage.indexOf(" sms"));
//                                Log.d(TAG, "SMS package from monthly voice package - " + smsAvailable);
//                                message = message.replaceFirst(voiceMessage, BuildConfig.FLAVOR);
//                                str = voiceMessage;
//                                smsExpiryDate = str.substring(voiceMessage.indexOf("on ") + 3, voiceMessage.indexOf(";") - 2).replaceAll(" at ", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR).replaceAll("-", BuildConfig.FLAVOR).replaceAll(":", BuildConfig.FLAVOR);
//                                calendar = GebetaUtils.stringToCalendar(smsExpiryDate, true);
//                                calendar.add(5, -30);
//                                smsBoughtOn = GebetaUtils.calendarToString(calendar, true);
//                                smsRemaining = Integer.parseInt(smsAvailable);
//                                if (smsRemaining <= 30) {
//                                    smsMax = 30;
//                                } else {
//                                    smsMax = 50;
//                                }
//                                if (this.sharedPreferences.getBoolean("onSmsPackage", false)) {
//                                    smsPackageAvailable = this.sharedPreferences.getInt("smsPackageAvailable", 0);
//                                    smsPackageMax = this.sharedPreferences.getInt("smsPackageMax", 0);
//                                    this.editor.putInt("smsPackageAvailable", smsPackageAvailable + smsRemaining);
//                                    this.editor.putInt("smsPackageMax", smsMax + smsPackageMax);
//                                } else {
//                                    try {
//                                        this.editor.putInt("smsPackageMax", smsMax);
//                                        this.editor.putInt("smsPackageAvailable", smsRemaining);
//                                        this.editor.putBoolean("onSmsPackage", true);
//                                        this.editor.putString("smsPackageExpiresOn", smsExpiryDate);
//                                        this.editor.putString("smsPackageBoughtOn", smsBoughtOn);
//                                    } catch (Exception e7) {
//                                        Log.e(TAG, "MonVoiceEnquire2 - Message parsing failed!");
//                                    }
//                                }
//                                dBHelper.addActivePackage(new ActivePackage(2, (float) smsRemaining, (float) smsMax, Long.parseLong(smsBoughtOn), Long.parseLong(smsExpiryDate)));
//                                this.editor.apply();
//                            } else {
//                                if (voiceMessage.contains("mb")) {
//                                    dataAvailable = voiceMessage.substring(voiceMessage.indexOf("is ") + 3, voiceMessage.indexOf(" mb"));
//                                    message = message.replaceFirst(voiceMessage, BuildConfig.FLAVOR);
//                                    isOnData = this.sharedPreferences.getBoolean("onDataPackage", false);
//                                    str = voiceMessage;
//                                    dataExpiresOn = str.substring(voiceMessage.indexOf("on ") + 3, voiceMessage.indexOf(";") - 2).replaceAll(" at ", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR).replaceAll("-", BuildConfig.FLAVOR).replaceAll(":", BuildConfig.FLAVOR);
//                                    calendar = GebetaUtils.stringToCalendar(dataExpiresOn, true);
//                                    calendar.add(5, -30);
//                                    dataBoughtOn = GebetaUtils.calendarToString(calendar, true);
//                                    if (isOnData) {
//                                        float dataPackageMax = (float) (((double) this.sharedPreferences.getFloat("dataPackageMax", 0.0f)) + 5.0d);
//                                        this.editor.putFloat("dataPackageAvailable", this.sharedPreferences.getFloat("dataPackageAvailable", 0.0f) + Float.parseFloat(dataAvailable));
//                                        this.editor.putFloat("dataPackageMax", dataPackageMax);
//                                        this.editor.apply();
//                                    } else {
//                                        this.editor.putBoolean("onDataPackage", true);
//                                        this.editor.putString("dataExpiresOn", dataExpiresOn);
//                                        this.editor.putString("dataBoughtOn", dataBoughtOn);
//                                        this.editor.putFloat("dataPackageAvailable", Float.parseFloat(dataAvailable));
//                                        this.editor.putFloat("dataPackageMax", 5.0f);
//                                        this.editor.apply();
//                                    }
//                                    dBHelper.addActivePackage(new ActivePackage(1, Float.parseFloat(dataAvailable), 5.0f, Long.parseLong(dataBoughtOn), Long.parseLong(dataExpiresOn)));
//                                } else {
//                                    if (voiceMessage.contains("minute")) {
//                                        minuteAvailable = voiceMessage.substring(voiceMessage.indexOf("is ") + 3, voiceMessage.indexOf(" minute"));
//                                        secondsAvailable = voiceMessage.substring(voiceMessage.indexOf("and ") + 4, voiceMessage.indexOf(" second"));
//                                        voicePackageMax = voiceMessage.substring(afterMonVoiceEnquire2.length() + 1, voiceMessage.indexOf(" min")).replaceAll(" ", BuildConfig.FLAVOR);
//                                        str = voiceMessage;
//                                        voiceExpiresOn = str.substring(voiceMessage.indexOf("on ") + 3, voiceMessage.indexOf(";") - 2).replaceAll(" at ", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR).replaceAll("-", BuildConfig.FLAVOR).replaceAll(":", BuildConfig.FLAVOR);
//                                        calendar = GebetaUtils.stringToCalendar(voiceExpiresOn, true);
//                                        calendar.add(5, -30);
//                                        voiceBoughOn = GebetaUtils.calendarToString(calendar, true);
//                                        message = message.replaceFirst(voiceMessage, BuildConfig.FLAVOR);
//                                        availableSeconds = Float.parseFloat(secondsAvailable) + (Float.parseFloat(minuteAvailable) * 60.0f);
//                                        if (this.sharedPreferences.getBoolean("onVoiceMonPackage", false)) {
//                                            this.editor.putFloat("voicePackageMonAvailable", this.sharedPreferences.getFloat("voicePackageMonAvailable", 0.0f) + availableSeconds);
//                                            this.editor.putFloat("voicePackageMonMax", Float.parseFloat(voicePackageMax) + this.sharedPreferences.getFloat("voicePackageMonMax", 0.0f));
//                                        } else {
//                                            this.editor.putBoolean("onVoiceMonPackage", true);
//                                            this.editor.putFloat("voicePackageMonAvailable", availableSeconds);
//                                            this.editor.putFloat("voicePackageMonMax", Float.parseFloat(voicePackageMax));
//                                            this.editor.putString("voicePackageMonExpDate", voiceExpiresOn);
//                                            this.editor.putString("voicePackageMonDate", voiceBoughOn);
//                                        }
//                                        dBHelper.addActivePackage(new ActivePackage(3, availableSeconds, Float.parseFloat(voicePackageMax), Long.parseLong(voiceBoughOn), Long.parseLong(voiceExpiresOn)));
//                                    }
//                                }
//                            }
//                            message = message.replaceFirst(voiceMessage, BuildConfig.FLAVOR);
//                            this.editor.apply();
//                            dBHelper.close();
//                        } while (message.contains(afterMonVoiceEnquire2));
//                    }
//                }
//                if (message.contains(afterVoiceSent)) {
//                    try {
//                        package_amount = message.substring((message.indexOf(afterVoiceSent) + afterVoiceSent.length()) + 1, message.indexOf(" minutes"));
//                        package_to = message.substring(message.indexOf("to ") + 3, message.indexOf(" .thank you."));
//                        dBHelper = new DBHelper(context.getApplicationContext());
//                        price = dBHelper.getPrice("1", package_amount, context.getApplicationContext(), 3);
//                        Log.d("Gebeta NVP Price", String.valueOf(price));
//                        this.editor.putFloat("currentBalance", (float) (((double) this.sharedPreferences.getFloat("currentBalance", 0.0f)) - price));
//                        this.editor.apply();
//                        sqLiteDatabase = dBHelper.getWritableDatabase();
//                        contentValues = new ContentValues();
//                        contentValues.put("type", Integer.valueOf(6));
//                        contentValues.put("amount", Double.valueOf(Double.parseDouble(package_amount)));
//                        contentValues.put("price", Double.valueOf(price));
//                        contentValues.put("too", package_to);
//                        contentValues.put("peak", Integer.valueOf(0));
//                        contentValues.put("date", Long.valueOf(Long.parseLong(this.date)));
//                        contentValues2 = contentValues;
//                        contentValues2.put("time", this.time);
//                        sqLiteDatabase.insert("gebeta_log", "type", contentValues);
//                        sqLiteDatabase.close();
//                        dBHelper.close();
//                    } catch (Exception e4222222222222222) {
//                        Log.e("SMS Receiver", BuildConfig.FLAVOR + e4222222222222222.getMessage());
//                    }
//                }
//                if (message.contains(afterMonVoiceSent)) {
//                    try {
//                        package_amount = message.substring((message.indexOf(afterMonVoiceSent) + afterMonVoiceSent.length()) + 1, message.indexOf(" min"));
//                        package_to = message.substring(message.indexOf("to ") + 3, message.indexOf(" .thank"));
//                        dBHelper = new DBHelper(context.getApplicationContext());
//                        price = dBHelper.getPrice("30", package_amount, context.getApplicationContext(), 3);
//                        Log.d("Gebeta NVP Price", String.valueOf(price));
//                        this.editor.putFloat("currentBalance", (float) (((double) this.sharedPreferences.getFloat("currentBalance", 0.0f)) - price));
//                        this.editor.apply();
//                        sqLiteDatabase = dBHelper.getWritableDatabase();
//                        contentValues = new ContentValues();
//                        contentValues.put("type", Integer.valueOf(6));
//                        contentValues.put("amount", Double.valueOf(Double.parseDouble(package_amount)));
//                        contentValues.put("price", Double.valueOf(price));
//                        contentValues.put("too", package_to);
//                        contentValues.put("peak", Integer.valueOf(0));
//                        contentValues.put("date", Long.valueOf(Long.parseLong(this.date)));
//                        contentValues2 = contentValues;
//                        contentValues2.put("time", this.time);
//                        sqLiteDatabase.insert("gebeta_log", "type", contentValues);
//                        sqLiteDatabase.close();
//                        dBHelper.close();
//                    } catch (Exception e42222222222222222) {
//                        Log.e("SMS Receiver_GMNVP", BuildConfig.FLAVOR + e42222222222222222.getMessage());
//                    }
//                }
//                if (message.contains(afterVoiceBought)) {
//                    if (message.contains("telecom.")) {
//                        try {
//                            package_amount = message.substring((message.indexOf(afterVoiceBought) + afterVoiceBought.length()) + 1, message.indexOf(" minutes"));
//                            dBHelper = new DBHelper(context.getApplicationContext());
//                            price = dBHelper.getPrice("1", package_amount, context.getApplicationContext(), 3);
//                            Log.d("Gebeta NVP Price", String.valueOf(price));
//                            this.editor.putFloat("currentBalance", (float) (((double) this.sharedPreferences.getFloat("currentBalance", 0.0f)) - price));
//                            sqLiteDatabase = dBHelper.getWritableDatabase();
//                            contentValues = new ContentValues();
//                            contentValues.put("type", Integer.valueOf(9));
//                            contentValues.put("amount", Double.valueOf(Double.parseDouble(package_amount)));
//                            contentValues.put("price", Double.valueOf(price));
//                            contentValues.put("too", "-");
//                            contentValues.put("peak", Integer.valueOf(0));
//                            contentValues.put("date", Long.valueOf(Long.parseLong(this.date)));
//                            contentValues2 = contentValues;
//                            contentValues2.put("time", this.time);
//                            sqLiteDatabase.insert("gebeta_log", "type", contentValues);
//                            sqLiteDatabase.close();
//                            secondsAvailable = Float.parseFloat(package_amount) * 60.0f;
//                            packageAmount = Float.parseFloat(package_amount);
//                            if (this.sharedPreferences.getBoolean("onVoicePackage", true)) {
//                                this.editor.putFloat("voicePackageMax", this.sharedPreferences.getFloat("voicePackageMax", 0.0f) + packageAmount);
//                                this.editor.putFloat("voicePackageAvailable", this.sharedPreferences.getFloat("voicePackageAvailable", 0.0f) + secondsAvailable);
//                            } else {
//                                this.editor.putFloat("voicePackageMax", packageAmount);
//                                this.editor.putFloat("voicePackageAvailable", secondsAvailable);
//                            }
//                            this.editor.putBoolean("onVoicePackage", true);
//                            boughtOn = GebetaUtils.calendarToString(Calendar.getInstance(), true);
//                            expiresOnCal = Calendar.getInstance();
//                            expiresOnCal.set(11, 6);
//                            if (Long.parseLong(GebetaUtils.calendarToString(Calendar.getInstance(), true)) > Long.parseLong(GebetaUtils.calendarToString(expiresOnCal, true))) {
//                                expiresOnCal.add(5, 1);
//                            }
//                            f = secondsAvailable;
//                            float f4 = packageAmount;
//                            dBHelper = dBHelper;
//                            dBHelper.addActivePackage(new ActivePackage(4, f, f4, Long.parseLong(boughtOn), Long.parseLong(GebetaUtils.calendarToString(expiresOnCal, true))));
//                            this.editor.apply();
//                            dBHelper.close();
//                        } catch (Exception e422222222222222222) {
//                            Log.e(TAG, "Error parsing message after buying night voice package" + e422222222222222222.getMessage());
//                        }
//                    }
//                }
//                if (message.contains(afterEnquireVoice)) {
//                    do {
//                        isString = "is";
//                        andString = "and";
//                        str = message;
//                        voiceMessage = str.substring(message.indexOf(afterEnquireVoice), message.indexOf(";") + 1);
//                        Log.d("Voice Message", voiceMessage);
//                        voicePackageMax = voiceMessage.substring((voiceMessage.indexOf(afterEnquireVoice) + afterEnquireVoice.length()) + 1, voiceMessage.indexOf(" minutes is"));
//                        voiceAvailable = (Float.parseFloat(voiceMessage.substring((voiceMessage.indexOf(isString) + isString.length()) + 1, voiceMessage.indexOf(" minute and"))) * 60.0f) + Float.parseFloat(voiceMessage.substring((voiceMessage.indexOf(andString) + andString.length()) + 1, voiceMessage.indexOf(" second")));
//                        if (this.sharedPreferences.getBoolean("onVoicePackage", true)) {
//                            this.editor.putFloat("voicePackageMax", Float.parseFloat(voicePackageMax) + this.sharedPreferences.getFloat("voicePackageMax", 0.0f));
//                            this.editor.putFloat("voicePackageAvailable", this.sharedPreferences.getFloat("voicePackageAvailable", 0.0f) + voiceAvailable);
//                        } else {
//                            try {
//                                this.editor.putFloat("voicePackageMax", Float.parseFloat(voicePackageMax));
//                                this.editor.putFloat("voicePackageAvailable", voiceAvailable);
//                            } catch (Exception e4222222222222222222) {
//                                Log.e(TAG, "Error parsing message after enquiring night voice package" + e4222222222222222222.getMessage());
//                                return;
//                            }
//                        }
//                        this.editor.putBoolean("onVoicePackage", true);
//                        this.editor.apply();
//                        boughtOn = GebetaUtils.calendarToString(Calendar.getInstance(), true);
//                        expiresOnCal = Calendar.getInstance();
//                        expiresOnCal.set(11, 6);
//                        if (Long.parseLong(GebetaUtils.calendarToString(Calendar.getInstance(), true)) > Long.parseLong(GebetaUtils.calendarToString(expiresOnCal, true))) {
//                            expiresOnCal.add(5, 1);
//                        }
//                        f = voiceAvailable;
//                        new DBHelper(context.getApplicationContext()).addActivePackage(new ActivePackage(4, f, Float.parseFloat(voicePackageMax), Long.parseLong(boughtOn), Long.parseLong(GebetaUtils.calendarToString(expiresOnCal, true))));
//                        message = message.replaceFirst(voiceMessage, BuildConfig.FLAVOR);
//                    } while (message.contains(afterEnquireVoice));
//                }
//            }
//        }
//    }
