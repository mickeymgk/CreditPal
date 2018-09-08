package mickeylina.creditpal.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.afollestad.aesthetic.AestheticActivity;

/**
 * Created by Mickey on 2.25.18.
 */

public class SplashActivity extends AestheticActivity {
//    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            launchHomeScreen();
//        if (prefManager.isFirstTimeLaunch()) {
//            startActivity(new Intent(this, AppIntroActivity.class));
//            prefManager.setIsFirstTimeLaunch(false);
//            finish();}
    }
    private void launchHomeScreen() {
//        prefManager.setIsFirstTimeLaunch(false);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }}

