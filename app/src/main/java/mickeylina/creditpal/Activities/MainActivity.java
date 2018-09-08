package mickeylina.creditpal.Activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.aesthetic.AestheticActivity;
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate;
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;

import io.mattcarroll.hover.overlay.OverlayPermission;
import mickeylina.creditpal.Fragments.AccountManagement;
import mickeylina.creditpal.Fragments.AdditionalServices;
import mickeylina.creditpal.Fragments.InternetPackages;
import mickeylina.creditpal.Fragments.PackageOffers;
import mickeylina.creditpal.Fragments.SettingsFragment;
import mickeylina.creditpal.Fragments.SmsPackages;
import mickeylina.creditpal.Fragments.UsageStats;
import mickeylina.creditpal.R;
import mickeylina.creditpal.Services.USSDCodeReader;
import mickeylina.creditpal.Utils;
import mickeylina.creditpal.test.UserDetails;
import mickeylina.creditpal.testInstantbuy.DemoHoverMenuService;

public class MainActivity extends AestheticActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnLocaleChangedListener,
        PackageOffers.OnPackageSelectedListener,UsageStats.OnDisableShadow {

    private static final String TAG = "";
    private boolean isHomeAsUp = false;
    private boolean doubleBackPress = false;
    private DrawerArrowDrawable homeDrawable;
    private static final int ANIM_DURATION_TOOLBAR = 250;
    private Toolbar toolbar;
    private LocalizationActivityDelegate localizationDelegate = new LocalizationActivityDelegate(this);
    private View shadow;
    private BottomSheetDialog dialog;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView mAdView;

    private static final int REQUEST_CODE_HOVER_PERMISSION = 1000;

    private boolean mPermissionsRequested = false;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localizationDelegate.addOnLocaleChangedListener(this);
        localizationDelegate.onCreate(savedInstanceState);
//        if (Aesthetic.isFirstTime()) {
//            Aesthetic.get()
//                    .colorPrimaryDarkRes(R.color.bluePrimaryDark)
//                    .textColorPrimaryRes(R.color.bluePrimary)
//                    .colorPrimaryDarkRes(R.color.bluePrimaryDark)
//                    .apply();
//        }
//        Aesthetic.get().colorAccentRes(R.color.colorPrimary).apply();
//        Aesthetic.get().colorPrimary().subscribe(color -> Aesthetic.get()
//                .colorIconTitleActive(color).colorIconTitleInactiveRes(R.color.unselected)
//                .apply());
//        Aesthetic.get().tabLayoutIndicatorMode(TabLayoutIndicatorMode.PRIMARY).apply();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        if (!Settings.canDrawOverlays(this)) {
//        Intent drawOverlaysSettingsIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//        drawOverlaysSettingsIntent.setData(Uri.parse("package:" + getPackageName()));
//        startActivity(drawOverlaysSettingsIntent);
//        }
//        }
        SharedPreferences notifPref = this.getSharedPreferences("NOTIFICATION_PREFS",MODE_PRIVATE);
        if(notifPref.getBoolean("widget_enabled",true)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (!mPermissionsRequested && !OverlayPermission.hasRuntimePermissionToDrawOverlay(this)) {
                    @SuppressWarnings("NewApi")
                    Intent myIntent = OverlayPermission.createIntentToRequestOverlayPermission(this);
                    startActivityForResult(myIntent, REQUEST_CODE_HOVER_PERMISSION);
                } else {
                    DemoHoverMenuService.showFloatingMenu(getApplicationContext());
                }
            } else {
                DemoHoverMenuService.showFloatingMenu(getApplicationContext());
            }
        }


        if(!isAccessibilitySettingsOn(getApplicationContext())){
            showAccessibilityWarning();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_main);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new Handler().postDelayed(() -> {
            AccountManagement accountManagement = new AccountManagement();
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_above, R.anim.exit)
                    .replace(R.id.fragment_holder, accountManagement)
                    .disallowAddToBackStack()
                    .commit();
        }, 150);

        /** setting phone number from SharedPreferences*/

        setHomeAsUp(false);

        homeDrawable = new DrawerArrowDrawable(toolbar.getContext());
        if (Build.VERSION.SDK_INT>=23) {
            toolbar.setNavigationIcon(homeDrawable);
        }else{ toolbar.setNavigationIcon(R.drawable.ic_menu); }

        toolbar.setNavigationOnClickListener
                (v -> {
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);

                    } else if (isHomeAsUp) {
                        onBackPressed();
                    } else {
                        drawer.openDrawer(GravityCompat.START);
                    }
                });
//        locking and unlocking the navigation drawer based on the fragments displayed and also animating the home d
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (Build.VERSION.SDK_INT >= 23) {
                boolean back = getSupportFragmentManager().getBackStackEntryCount() == 0;
                ObjectAnimator.ofFloat(homeDrawable, "progress", back ? 0 : 1).setDuration(250).start();
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    toolbar.setTitle(R.string.app_name);
                } else {
                    animateTitleChangeToRight();
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
            } else {
                toolbar.findViewById(R.id.toolbar);
                boolean back = getSupportFragmentManager().getBackStackEntryCount()==0;
                if(back){
                   toolbar.setNavigationIcon(R.drawable.ic_menu);
                    toolbar.setTitle(R.string.app_name);
                    animateTitleChangeToRight();
                }else {
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
                    animateTitleChangeToRight();
                }
            }
        });

//        adding back-to-stack gesture to the view by tacking touch motion
//        GestureOverlayView gview = findViewById(R.id.touch);
//        gview.setOnTouchListener(new SwipeBackStack(this));
        SharedPreferences data = getSharedPreferences("user_info", 0);
        String userno = data.getString("phoneNumber", "").replace("251", "0");
        data.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
            if(key.equals("phoneNumber")){
                String usernoupdated =data.getString("phoneNumber","").replace("251","0");
                View header = navigationView.getHeaderView(0);
                TextView userNumber = header.findViewById(R.id.user_number);
                userNumber.setText(usernoupdated);
           }

        });

        setPhoneNumber();
        View header = navigationView.getHeaderView(0);
        header.setOnClickListener(v -> {
            drawer.closeDrawers();
            new Handler().postDelayed(() -> {
            UserDetails.newInstance().show(getSupportFragmentManager(), "user_dialog");
        }, 300);
        });

    }

    private void showAccessibilityWarning() {
        if (!isAccessibilitySettingsOn(getApplicationContext())) {
            View modalbottomsheet = this.getLayoutInflater().inflate(R.layout.enable_accessibility, null);
            TextView enable = modalbottomsheet.findViewById(R.id.access);
            enable.setOnClickListener(v -> {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                dialog.dismiss();
            });
            dialog = new BottomSheetDialog(this);
            dialog.setContentView(modalbottomsheet);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);
            dialog.getWindow().setDimAmount(0.1f);
            dialog.show();
        }else{ dialog.dismiss();}
    }

    private void setPhoneNumber() {
        SharedPreferences data = getSharedPreferences("user_info", 0);
        String userno = data.getString("phoneNumber", "").replace("251", "0");
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView userNumber = header.findViewById(R.id.user_number);
        userNumber.setText(userno);
        data.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> setPhoneNumber());
    }

    private void animateTitleChangeToRight() {
        final View view = getToolbarTitle();
        if (view instanceof TextView) {
//            fade in right
            YoYo.with(Techniques.FadeIn).duration(500).playOn(getToolbarTitle());
        }
    }

    public final void animateTitleChangeToLeft() {
        final View view = getToolbarTitle();
        if (view instanceof TextView) {
//            Fade in left
            YoYo.with(Techniques.FadeInLeft).interpolate(new AccelerateInterpolator()).duration(180).playOn(getToolbarTitle());
        }
    }

    private View getToolbarTitle() {
        toolbar = findViewById(R.id.toolbar);
        int childCount = toolbar.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = toolbar.getChildAt(i);
            if (child instanceof TextView) {
                return child;
            }
        }
        return new View(this);
    }

    /**
     * animating the hamburger icon based on the boolean
     */
    private void setHomeAsUp(boolean isHomeAsUp) {
            if (this.isHomeAsUp != isHomeAsUp) {
                this.isHomeAsUp = isHomeAsUp;
//                ValueAnimator anim = isHomeAsUp ? ValueAnimator.ofFloat(0, 1) : ValueAnimator.ofFloat(1, 0);
//                anim.addUpdateListener(valueAnimator -> {
//                    float slideOffset = (Float) valueAnimator.getAnimatedValue();
//                    homeDrawable.setProgress(slideOffset);
//                });
//                    anim.setInterpolator(new DecelerateInterpolator());
//                    anim.setDuration(200);
//                    anim.start();
            }
        }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
//        SharedPreferences settings = this.getSharedPreferences(PREFS_NAME,0);
//        string = settings.getString("preference",string);

        if (getCurrentLanguage().toString().equals("am")) {
            menu.findItem(R.id.action_lang_2).setChecked(true);
        } else {
            menu.findItem(R.id.action_lang_1).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
//        editor = settings.edit();
//        string = settings.getString("preference",string);
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_lang_1:
                setLanguage("");
                break;
            case R.id.action_lang_2:
                setLanguage("am");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startIntroAnimation() {
        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize * 2);
        getToolbar().animate()
                .translationY(0)
                .setDuration(350);
        getAppBarLayout().setTranslationY(-actionbarSize * 2);
        getAppBarLayout().animate()
                .translationY(0)
                .setDuration(450);
    }

    @Override
    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount()>0){
//            setHomeAsUp(true);
//            super.onBackPressed();
//        }
//        if (getSupportFragmentManager().getBackStackEntryCount()<=0){
//            setHomeAsUp(false);
//        }
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        }

        if (doubleBackPress || getSupportFragmentManager().getBackStackEntryCount() != 0) {
            setHomeAsUp(true);
            super.onBackPressed();
        }if (doubleBackPress || getSupportFragmentManager().getBackStackEntryCount() == 0) {
            setHomeAsUp(false);
        }
        this.doubleBackPress = true;
//        Toast.makeText(this, "press back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> doubleBackPress = false, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_refresh).setVisible(false);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
//        displaySelectedScreen(item.getItemId());
        int id = item.getItemId();
        if (id == R.id.nav_account) {
            AccountManagement accountManagement = new AccountManagement();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_holder, accountManagement)
                    .commit();
            setHomeAsUp(false);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "NAVIGATION");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "CLICK");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        } else if (id == R.id.nav_additional) {
            AdditionalServices additionalServices = new AdditionalServices();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left, R.anim.slide_out_to_right)
                    .replace(R.id.fragment_holder, additionalServices)
                    .addToBackStack(null).commit();
            setHomeAsUp(true);
            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.navigation_additional);
//            animateTitleChangeToRight();
        } else if (id == R.id.nav_package) {
            PackageOffers packageOffers = new PackageOffers();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_right,
                            R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left,
                            R.anim.slide_out_to_right)
                    .replace(R.id.fragment_holder, packageOffers)
                    .addToBackStack(null).commit();
            setHomeAsUp(true);
            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.navigation_Package_offers);
//            animateTitleChangeToRight();
        } else if (id == R.id.nav_usage) {
            UsageStats usageStats = new UsageStats();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left, R.anim.slide_out_to_right)
                    .replace(R.id.fragment_holder, usageStats,"USAGE")
                    .addToBackStack(null).commit();
            setHomeAsUp(true);
            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.navigation_usage_stats);

        } else if (id == R.id.nav_settings) {
            SettingsFragment settingsFragment = new SettingsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_right,
                            R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left,
                            R.anim.slide_out_to_right)
                    .replace(R.id.fragment_holder, settingsFragment)
                    .addToBackStack(null).commit();
            setHomeAsUp(true);
            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.navigation_Package_offers);

        } else if (id == R.id.nav_about) {
//            startActivity(new Intent(this, Main2Activity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private View getToolbar() {
        return this.<Toolbar>findViewById(R.id.toolbar);
    }

    private View getAppBarLayout() {
        return this.<AppBarLayout>findViewById(R.id.appbar);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        localizationDelegate.onResume(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(localizationDelegate.attachBaseContext(newBase));
    }

    @Override
    public Context getApplicationContext() {
        return localizationDelegate.getApplicationContext(super.getApplicationContext());
    }

    @Override
    public Resources getResources() {
        return localizationDelegate.getResources(super.getResources());
    }

    private void setLanguage(String language) {
        localizationDelegate.setLanguage(this, language);
    }

    public final void setLanguage(Locale locale) {
        localizationDelegate.setLanguage(this, locale);
    }

    public final void setDefaultLanguage(String language) {
        localizationDelegate.setDefaultLanguage(language);
    }

    public final void setDefaultLanguage(Locale locale) {
        localizationDelegate.setDefaultLanguage(locale);
    }

    private Locale getCurrentLanguage() {
        return localizationDelegate.getLanguage(this);
    }

    // Just override method locale change event
    @Override
    public void onBeforeLocaleChanged() {
    }

    @Override
    public void onAfterLocaleChanged() {
    }

    @Override
    public void onPackageSelected(int position) {
        if (position == 0) {
            InternetPackages internetFragment = new InternetPackages();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_right,
                            R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left,
                            R.anim.slide_out_to_right)
                    .replace(R.id.fragment_holder, internetFragment)
                    .addToBackStack(null)
                    .commit();

            setHomeAsUp(true);
            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.internet_fragment);
        }if (position == 1) {
            SmsPackages smsFragment = new SmsPackages();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_right,
                            R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left,
                            R.anim.slide_out_to_right)
                    .replace(R.id.fragment_holder, smsFragment)
                    .addToBackStack(null)
                    .commit();

            setHomeAsUp(true);
            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.sms_fragment);
        }
    }

    @Override
    public void onDisable(Boolean disable) {
        shadow = findViewById(R.id.toolbar_shadow);
        if (disable) {
            shadow.setVisibility(View.GONE);
//            MenuItem menu = getMenuInflater().
//            menu.findItem(R.id.action_refresh).setEnabled(false);
        }
        if (!disable) {
            shadow.setVisibility(View.VISIBLE);
        }

    }

    //    public MenuItem getInboxMenuItem() {
//        return inboxMenuItem;
//    }

//    private  class SwipeBackStack implements View.OnTouchListener {
//        GestureDetector swipeBackStack;
//                public SwipeBackStack(final MainActivity mainActivity) {
//                swipeBackStack = new GestureDetector(mainActivity, new GestureDetector.SimpleOnGestureListener() {
//                    @Override
//                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                        if (Math.abs(velocityX) > Math.abs(velocityY)) {
//                            if (velocityX < 0) {
////  if velocity is equal to zero touch nw malet nw gn ke zero kebele weym
////  kanese swipe or gesture nw malet nw demo +tive kehone swipeu wede right nw malet new
////  -ive kehone yaw wede gera nw malet ne ehen ersa aluh demo ante duz
//                                return true;
//                            } else if (velocityX > 0) {
////                                this is a Right swipe meaning we will do some logic here
////                                for testing let us show a toast :)
//                                Toast.makeText(mainActivity, "Yeah I " +
//                                        "responded to the right swipe", Toast.LENGTH_SHORT).show();
//                                onBackPressed();
//                                return true;
//                            }
//                        }
//                        return false;
//                    }
//                });
//            }
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//
//            return swipeBackStack.onTouchEvent(event);
//        }
//    }

    private boolean isAccessibilitySettingsOn( Context mContext) {
        int accessibilityEnabled = 0 ;
        final String service = getPackageName() + "/" + USSDCodeReader.class.getCanonicalName();
        try { accessibilityEnabled = Settings .Secure .getInt(
                    mContext.getApplicationContext().getContentResolver(), android.provider. Settings . Secure .ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch ( Settings . SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new
                TextUtils .SimpleStringSplitter (':' );
        if (accessibilityEnabled == 1 ) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------" );
            String settingValue = Settings.Secure.getString(
                            mContext.getApplicationContext().getContentResolver(),
                            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    Log .v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase
                            (service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!" );
                        return true ;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***" );
        }
        return false ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE_HOVER_PERMISSION == requestCode) {
            mPermissionsRequested = true;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}

