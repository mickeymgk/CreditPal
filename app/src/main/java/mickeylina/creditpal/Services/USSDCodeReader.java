package mickeylina.creditpal.Services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;


public class USSDCodeReader extends AccessibilityService {
    private static final String TAG = "CPUSSDReaderService";
    private static boolean phoneNumberRequested;


    public void onAttach(Activity activity) {
//        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
    }

    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.d(TAG, "On Accessibility Event Called!!!");
        try {
            String message = (String) accessibilityEvent.getText().get(0);
            Log.d(TAG, message);
            parseMessage(message);
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        } catch (IndexOutOfBoundsException exception2) {
            exception2.printStackTrace();
        }
    }

    public void onInterrupt() {
    }

    public void onServiceConnected() {
        Log.d(TAG, "Accessibility Feature Enabled");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = 32;
        info.notificationTimeout = 100;
        info.packageNames = new String[]{"com.android.phone"};
        info.feedbackType = 8;
        setServiceInfo(info);
    }

    private void parseMessage(String message) {
        SharedPreferences userInfo = getApplicationContext().getSharedPreferences("user_info",0);
        SharedPreferences userBalance = getApplicationContext().getSharedPreferences("user_balance",0);
        Editor infoEditor = userInfo.edit();
        Editor balanceEditor =userBalance.edit();
        try {
            message = message.toLowerCase();
            if(message.contains("ethio telecom")){
                String remainingBalance = message.substring(message.indexOf("is ") + 3, message.indexOf("birr"));
                String expiresOn = message.substring(message.indexOf("on") + 3, message.indexOf(". ethio"));
                balanceEditor.putString("remainingBalance",remainingBalance);
                balanceEditor.putString("expires_on",expiresOn);
                balanceEditor.commit();
                performGlobalAction(GLOBAL_ACTION_BACK);
            }
            if (message.contains("msisdn:")){
                if (Build.VERSION.SDK_INT >= 17) { performGlobalAction(1); }
                infoEditor.putString("phoneNumber",message.replace("msisdn:",""));
                infoEditor.commit();
                Log.d(TAG,message);
                phoneNumberRequested=true;
                performGlobalAction(GLOBAL_ACTION_BACK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

