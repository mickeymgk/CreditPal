package mickeylina.creditpal;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class SMSReceiver extends BroadcastReceiver {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("WrongConstant")
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String format = bundle.getString("format");
        String strMessage = "";
        String phoneNumber = "";
        String frometc = "ethio telecom.";
        String ReceivedCallMeBack = "please call back to";
        String balanceRecharged = "balance is";
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
        String lowDataPackage = "75% of your internet plan from mobile internet package";
        String afterReceivingCallMeBack = "please call back to";
        String finishedInternetPlan = "finished internet plan of your mobile internet package";


        this.sharedPreferences = context.getSharedPreferences("PACKAGES_PREF", 0);
        this.editor = this.sharedPreferences.edit();
        this.editor.apply();

//        if (bundle != null) {
//            Log.d("MICKEY", "smsReceiver : Reading Bundle");
//
//            Object[] pdus = (Object[])bundle.get("pdus");
//            SmsMessage sms = SmsMessage.createFromPdu((byte[])pdus[0]);
////            String smss = sms.getMessageBody();
////            Toast.makeText(context, smss, Toast.LENGTH_SHORT).show();
//
//            if(sms.getMessageBody().contains("Enable")){
//                Intent myIntent = new Intent(context,MainActivity.class);
//                myIntent.putExtra("mySMS", bundle);
//                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(myIntent);
//            }
//        }
        float previousAmount;
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
        String dataExpiresAt;
        String purchasedOn;
        Calendar expiresOnCalendar;
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
        String smsExpiresAt;
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
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];

        for (int i = 0; i < messages.length; i++) {
            if (Build.VERSION.SDK_INT >= 23) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            phoneNumber = messages[i].getOriginatingAddress();
            Log.d("MESSAGE_PHONE_NO", phoneNumber);
            strMessage = String.valueOf(strMessage) + messages[i].getMessageBody().toLowerCase();
            Log.d("MESSAGE_BODY", strMessage);
        }

        if (strMessage.contains(ReceivedCallMeBack)) {
            try {
                String phoneNo = strMessage.substring((strMessage.indexOf(ReceivedCallMeBack) +
                        ReceivedCallMeBack.length()) + 1, (strMessage.indexOf(ReceivedCallMeBack) +
                        ReceivedCallMeBack.length()) + 11).replace(" ", BuildConfig.FLAVOR);
                Log.d("Call Me Back Request", phoneNo);
                if (this.sharedPreferences.getBoolean("callMeBackNotification", true)) {
                    String notificationMessage = context.getResources().getString(R.string.notification_from) + Utils.getContactNameFromNumber(phoneNo, context.getContentResolver());
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    builder.setAutoCancel(true).setDefaults(-1);
                    builder.setContentTitle(context.getResources().getString(R.string.call_me_back)).setContentText(notificationMessage);
                    builder.setSmallIcon(R.mipmap.ic_cp).setTicker(notificationMessage);
                    builder.setPriority(0).setStyle(new NotificationCompat.BigTextStyle().bigText(notificationMessage));
                    builder.setContentIntent(PendingIntent.getActivity(context, 0,
                            new Intent("android.intent.action.DIAL", Uri.parse("tel:" + phoneNo)), 0));
                    ((NotificationManager) context.getSystemService("notification")).notify((int)
                            (Math.random() * 10.0d), builder.build());
                }
            } catch (Exception e) {
                Log.e("##CALL ME BACK##", "ERROR WHILE MAKING THE CMB NOTIFICATION");
            }
        }

        if (strMessage.contains(afterEnquireData)) {
            deletePrevious();
            if (strMessage.contains("telecom.")) {
                while (strMessage.contains(afterEnquireData)) {
                    dataMessage = strMessage.substring(strMessage.indexOf(afterEnquireData),
                            strMessage.indexOf(afterEnquireData));
                    str = dataMessage;
                    dataMessage = str.substring(0, dataMessage.indexOf(";") + 1);
                    Log.d("Data Message", dataMessage);
                    packageAmount2 = dataMessage.substring((dataMessage.indexOf(afterEnquireData) +
                            afterEnquireData.length()) + 1, dataMessage.indexOf(" for "));
                    if (packageAmount2.contains("gb")) {
                        packageAmount2 = String.valueOf(Double.parseDouble(packageAmount2.replaceAll(" ",
                                BuildConfig.FLAVOR).replaceAll("gb", BuildConfig.FLAVOR)) * 1000.0d);
                    } else {
                        try {
                            packageAmount2 = packageAmount2.replaceAll(" ",
                                    BuildConfig.FLAVOR).replaceAll("mb", BuildConfig.FLAVOR);
                        } catch (Exception e42222) {
                            try {
                                Log.e(" Data Enquire", BuildConfig.FLAVOR + e42222.getMessage());
                                Log.e("Data Enquire", strMessage.substring(strMessage.indexOf(afterEnquireData),
                                        strMessage.indexOf(afterEnquireData)));
                            } catch (Exception f3) {
                                Log.e("Data Failed", "I failed at failing. " + f3.getMessage());
                            }
                        }
                    }
                    packageAvailable = dataMessage.substring(dataMessage.indexOf("is") + 3,
                            dataMessage.indexOf("with")).replaceAll("mb",
                            BuildConfig.FLAVOR).replaceAll(" ",
                            BuildConfig.FLAVOR);
                    str = dataMessage;
                    dataExpiresOn = str.substring(dataMessage.indexOf("on ") + 3,
                            dataMessage.indexOf(" at")).replaceAll("-", "/");

                    dataExpiresAt = str.substring(dataMessage.indexOf("at ") + 3,
                            dataMessage.indexOf(";"));
                    purchasedOn = dataMessage.substring(dataMessage.indexOf("for") + 4,
                            dataMessage.indexOf("is"));
                    if (purchasedOn.contains("days")) {
                        purchasedOn = purchasedOn.replaceAll(" ",
                                BuildConfig.FLAVOR).replaceAll("days",
                                BuildConfig.FLAVOR);
                    } else {
                        purchasedOn = "1";
                    }
                    if (this.sharedPreferences.getBoolean("onDataPackage", false)) {
                        previousAmount = this.sharedPreferences.getFloat("dataPackageAvailable", 0.0f);
                        float previousPackageMax = this.sharedPreferences.getFloat("dataPackageMax", 0.0f);
                        this.editor.putFloat("dataPackageAvailable", Float.parseFloat(packageAvailable) + previousAmount);
                        this.editor.putFloat("dataPackageMax", Float.parseFloat(packageAmount2) + previousPackageMax);
                    } else {
                        this.editor.putFloat("dataPackageAvailable", Float.parseFloat(packageAvailable));
                        this.editor.putString("dataExpiresOn", dataExpiresOn);
                        this.editor.putString("dataBoughtOn", purchasedOn);
                        this.editor.putString("dataExpiresAt", dataExpiresAt);
                        this.editor.putFloat("dataPackageMax", Float.parseFloat(packageAmount2));
                        this.editor.putBoolean("onDataPackage", true);
                    }
                    this.editor.commit();
                    strMessage = strMessage.replaceFirst(dataMessage, BuildConfig.FLAVOR);
                }
            }
        }
        if (strMessage.contains(afterEnquireData2)) {
            deletePrevious();
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
                            dataMessage.indexOf(" at")).replaceAll("-", "/");

                    dataExpiresAt = str.substring(dataMessage.indexOf("at ") + 3,
                            dataMessage.indexOf(";"));

                    purchasedOn = dataMessage.substring(dataMessage.indexOf("after") + 6,
                            dataMessage.indexOf("is"));
                    if (purchasedOn.contains("days")) {
                        purchasedOn = purchasedOn.replaceAll(" ",
                                BuildConfig.FLAVOR).replaceAll("days",
                                BuildConfig.FLAVOR);
                    } else {
                        purchasedOn = "1";
                    }
                    if (this.sharedPreferences.getBoolean("onDataPackage", false)) {
                        this.editor.putFloat("dataPackageAvailable", Float.parseFloat(packageAvailable));
                        this.editor.putFloat("dataPackageMax", Float.parseFloat(packageAmount2));
                    } else {
                        this.editor.putFloat("dataPackageAvailable", Float.parseFloat(packageAvailable));
                        this.editor.putBoolean("onDataPackage", true);
                        this.editor.putFloat("dataPackageMax", Float.parseFloat(packageAmount2));
                        this.editor.putString("dataExpiresOn", dataExpiresOn);
                        this.editor.putString("dataExpiresAt", dataExpiresAt);
                        this.editor.putString("dataBoughtOn", purchasedOn);
                    }
                    this.editor.commit();
                    strMessage = strMessage.replaceAll(dataMessage, BuildConfig.FLAVOR);
                }
            }
        }

        if (strMessage.contains(finishedInternetPlan)) {
            String finishedDataMessage = context.getResources().getString(R.string.finished_package_internet) +
                    strMessage.substring(strMessage.indexOf(finishedInternetPlan) + finishedInternetPlan.length(),
                            strMessage.indexOf(" to")).toUpperCase() + context.getResources().getString(R.string.internet_plan);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setAutoCancel(true).setDefaults(-1);
            builder.setContentTitle(context.getResources().getString(R.string.notification_finished_package))
                    .setContentText(finishedDataMessage);
            builder.setSmallIcon(R.mipmap.ic_cp).setTicker(finishedDataMessage);
            builder.setPriority(0).setStyle(new NotificationCompat.BigTextStyle().bigText(finishedDataMessage));
//add an empty intent
        }
//        passed
        if (strMessage.contains(lowDataPackage)) {
            String lowDataMessage =
                    context.getResources().getString(R.string.internet_message) + strMessage.substring(strMessage.indexOf(lowDataPackage) +
                            lowDataPackage.length(), strMessage.indexOf((" to"))).toUpperCase() +
                            context.getResources().getString(R.string.internet_plan);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setAutoCancel(true).setDefaults(-1);
            builder.setContentTitle(context.getResources().getString(R.string.low_internet_package))
                    .setContentText(lowDataMessage);
            builder.setSmallIcon(R.mipmap.ic_cp).setTicker(lowDataMessage);
            builder.setPriority(0).setStyle(new NotificationCompat.BigTextStyle().bigText(lowDataMessage));

        }
        if (strMessage.contains(afterEnquireSms)) {
            deletePrevious();
            if (strMessage.contains(afterEnquireSms)) {
                isString = "is";
                smsMessage = strMessage.substring(strMessage.indexOf(afterEnquireSms), strMessage.indexOf(afterEnquireSms));
                str = smsMessage;
                smsMessage = str.substring(smsMessage.indexOf(afterEnquireSms), smsMessage.indexOf(";") + 1);
                String smsPackageOriginal = smsMessage.substring((smsMessage.indexOf(afterEnquireSms)
                        + afterEnquireSms.length()) + 1, smsMessage.lastIndexOf(" sms to be"));
                String smsPackageRemaining = smsMessage.substring((smsMessage.indexOf(isString)
                        + isString.length()) + 1, smsMessage.lastIndexOf(" sms with"));
                smsRemaining = Integer.parseInt(smsPackageRemaining);
                smsMax = Integer.parseInt(smsPackageOriginal);
                str = smsMessage;
//                smsExpiryDate = str.substring(smsMessage.indexOf("on ") + 3, smsMessage.indexOf(";") - 2)
//                        .replaceAll(" at ",
//                                BuildConfig.FLAVOR).replaceAll(" ",
//                                BuildConfig.FLAVOR).replaceAll("-",
//                                BuildConfig.FLAVOR).replaceAll(":",
//                                BuildConfig.FLAVOR);
                smsExpiryDate = str.substring(smsMessage.indexOf("on ") + 3, smsMessage.indexOf(" at"))
                        .replaceAll("-", "/");
                smsExpiresAt = str.substring(smsMessage.indexOf("at ") + 3,
                        smsMessage.indexOf(";"));

                purchasedOn = smsMessage.substring(smsMessage.indexOf("after") + 6,
                        smsMessage.indexOf("is"));
                if (purchasedOn.contains("days")) {
                    purchasedOn = purchasedOn.replaceAll(" ",
                            BuildConfig.FLAVOR).replaceAll("days",
                            BuildConfig.FLAVOR);
                } else {
                    try {
                        purchasedOn = "1";
                    } catch (Exception e4222222222) {
                    }
                }
                if (this.sharedPreferences.getBoolean("onSmsPackage", false)) {
                    this.editor.putInt("smsPackageAvailable", this.sharedPreferences.getInt("smsPackageAvailable", 0));
                    this.editor.putInt("smsPackageMax", this.sharedPreferences.getInt("smsPackageMax", 0));
                } else {
                    this.editor.putInt("smsPackageAvailable", smsRemaining);
                    this.editor.putInt("smsPackageMax", smsMax);
                }
                this.editor.putBoolean("onSmsPackage", true);
                this.editor.putString("smsPackageExpiresOn", smsExpiryDate);
                this.editor.putString("smsPackageExpiresAt", smsExpiresAt);
                this.editor.putString("smsPackageBoughtOn", purchasedOn);
                this.editor.commit();
                strMessage = strMessage.replaceFirst(smsMessage, BuildConfig.FLAVOR);
            }
        }

        if (strMessage.contains(afterEnquireSms2)) {
            deletePrevious();
            isString = "is";
            smsMessage = strMessage.substring(strMessage.indexOf(afterEnquireSms2), strMessage.indexOf(afterEnquireSms2));
            str = smsMessage;
            smsMessage = str.substring(smsMessage.indexOf(afterEnquireSms2), smsMessage.indexOf(";") + 1);
            smsPackageMax = smsMessage.substring((smsMessage.indexOf(afterEnquireSms2) + afterEnquireSms2.length()) + 1,
                    smsMessage.indexOf(" sms for"));
            smsPackageAvailable = smsMessage.substring((smsMessage.indexOf(isString) + isString.length()) + 1,
                    smsMessage.lastIndexOf(" sms"));
            str = smsMessage;
//            smsExpiryDate = str.substring(smsMessage.indexOf("on ") + 3,
//                    smsMessage.indexOf(";") - 2).replaceAll(" at ",
//                    BuildConfig.FLAVOR).replaceAll(" ",
//                    BuildConfig.FLAVOR).replaceAll("-",
//                    BuildConfig.FLAVOR).replaceAll(":",
//                    BuildConfig.FLAVOR);
            smsExpiryDate = str.substring(smsMessage.indexOf("on ") + 3, smsMessage.indexOf(" at"))
                    .replaceAll("-", "/");

            smsExpiresAt = str.substring(smsMessage.indexOf("at ") + 3,
                    smsMessage.indexOf(";"));

            purchasedOn = smsMessage.substring(smsMessage.indexOf("for") + 4,
                    smsMessage.indexOf("is"));
            if (purchasedOn.contains("days")) {
                purchasedOn = purchasedOn.replaceAll(" ",
                        BuildConfig.FLAVOR).replaceAll("days",
                        BuildConfig.FLAVOR);
            } else {
                try {
                    purchasedOn = "1";
                } catch (Exception e42222222222) {
                }
            }
            smsRemaining = Integer.parseInt(smsPackageAvailable);
            smsMax = Integer.parseInt(smsPackageMax);
            if (this.sharedPreferences.getBoolean("onSmsPackage", false)) {
                this.editor.putInt("smsPackageAvailable", this.sharedPreferences.getInt("smsPackageAvailable", 0));
                this.editor.putInt("smsPackageMax", this.sharedPreferences.getInt("smsPackageMax", 0));
            } else {
                this.editor.putInt("smsPackageAvailable", smsRemaining);
                this.editor.putInt("smsPackageMax", smsMax);
            }
            this.editor.putString("smsPackageExpiresOn", smsExpiryDate);
            this.editor.putString("smsPackageExpiresAt",smsExpiresAt);
            this.editor.putString("smsPackageBoughtOn", purchasedOn);
            this.editor.commit();
            strMessage = strMessage.replaceFirst(smsMessage, BuildConfig.FLAVOR);
        }
        if (strMessage.contains(afterEnquireVoice)) {
            deletePrevious();
//            TODO: PLEASE FIX THIS SHIT BASED ON THE ABOVE METHOD I DON'T KNOW WHY ETC GIVES EXTRA DATA BOUNTY FOR EVERY VOICE PACKAGE SUBSCRIBED
            try {
                do {
                    isString = "is";
                    andString = "and";
                    str = strMessage;
                    voiceMessage = str.substring(strMessage.indexOf(afterEnquireVoice), strMessage.indexOf(";") + 1);
                    Log.d("Voice Message", voiceMessage);
                    voicePackageMax = voiceMessage.substring((voiceMessage.indexOf(afterEnquireVoice) +
                            afterEnquireVoice.length()) + 1, voiceMessage.indexOf(" minutes is"));
                    voiceAvailable = (Float.parseFloat(voiceMessage.substring((voiceMessage.indexOf(isString) +
                            isString.length()) + 1, voiceMessage.indexOf(" minute and"))) * 60.0f) +
                            Float.parseFloat(voiceMessage.substring((voiceMessage.indexOf(andString) +
                                    andString.length()) + 1, voiceMessage.indexOf(" second")));
                    if (this.sharedPreferences.getBoolean("onVoicePackage", true)) {
                        this.editor.putFloat("voicePackageMax", Float.parseFloat(voicePackageMax) +
                                this.sharedPreferences.getFloat("voicePackageMax", 0.0f));
                        this.editor.putFloat("voicePackageAvailable",
                                this.sharedPreferences.getFloat("voicePackageAvailable",
                                        0.0f) + voiceAvailable);
                    } else {
                        try {
                            this.editor.putFloat("voicePackageMax", Float.parseFloat(voicePackageMax));
                            this.editor.putFloat("voicePackageAvailable", voiceAvailable);
                        } catch (Exception e4222222222222222222) {
                            return;
                        }
                    }
                    this.editor.putBoolean("onVoicePackage", true);
                    this.editor.apply();
                    f = voiceAvailable;
                    strMessage = strMessage.replaceFirst(voiceMessage, BuildConfig.FLAVOR);
                } while (strMessage.contains(afterEnquireVoice));
            } catch (Exception BORED) {
//                tired of this shit I don't even know if it works or not, tired of debugging huuuuh! my eyes huts a lot I need help woooooooooooo!!!!!!!!!!!!!1
//                lets fake an error message and see what the users are going to do maybe I'll fix it in the future sorry for the
//                voice package users after all I'm tired and I wrote this before the eve of new year 2011 we'll see what the new year will bring
//                I'll join the university oh mickey you're grown up now you'll never get time for coding and playing with your PC its time to get your hobby to the garbage can
//                I feel sorry about that It was really a fun experience learning java and knowing how android apps work really I'll miss this thing
//                walllh! just chill mike its just a hobby, who knows what you'll learn in 2011, you may find the university fun, or even not
//                woo my typing skills are fired up maybe I'll be a secretory in an office, just stop wondering about your future, your future is in God's
//                hand man yeah! yeah chill chill chill
                Toast.makeText(context, "error 68 Report this to the developer", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deletePrevious() {
        this.editor.remove("dataPackageMax");
        this.editor.remove("dataPackageAvailable");
        this.editor.remove("onDataPackage");
        this.editor.remove("dataExpiresOn");
        this.editor.remove("dataExpiresAt");
        this.editor.remove("smsPackageAvailable");
        this.editor.remove("smsPackageBoughtOn");
        this.editor.remove("smsPackageExpiresOn");
        this.editor.remove("smsPackageExpiresAt");
        this.editor.remove("smsPackageMax");
        this.editor.remove("voicePackageMax");
        this.editor.remove("voicePackageAvailable");
        this.editor.commit();
    }
}
