package mickeylina.creditpal.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.aesthetic.Aesthetic;

import java.util.Objects;

import io.mattcarroll.hover.overlay.OverlayPermission;
import mickeylina.creditpal.Debug_Activity;
import mickeylina.creditpal.R;
import mickeylina.creditpal.testInstantbuy.DemoHoverMenuService;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {

    private RadioGroup radioGroup;
    private static final int REQUEST_CODE_HOVER_PERMISSION = 1000;
    private boolean mPermissionsRequested = false;
//    @Override
//    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.action_settings);
         SharedPreferences pref = getActivity().getSharedPreferences("COLOR_PREFS",MODE_PRIVATE);
        radioGroup = view.findViewById(R.id.rd);
        radioGroup.check(pref.getInt("lang_btn_id",R.id.blue));
        Log.d("CHECKED","chill you code is working as charm");
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("COLOR_PREFS",MODE_PRIVATE).edit();

            switch (checkedId){
                case R.id.blue:
                    editor.putInt("color_code",0);
                    editor.putString("color_name","blue");
                    Log.d("CHECKED","chill you code is working as charm");
                    Aesthetic.get()
                            .colorPrimaryRes(R.color.bluePrimary)
                            .colorAccentRes(R.color.bluePrimary)
                            .colorStatusBarRes(R.color.bluePrimaryDark)
                            .colorPrimaryDarkRes(R.color.bluePrimaryDark)
                            .apply();
                    break;
                case R.id.red:
                    editor.putInt("color_code",1);
                    editor.putString("color_name","red");
                    Aesthetic.get()
                            .colorPrimaryRes(R.color.redPrimary)
                            .colorAccentRes(R.color.redPrimary)
                            .colorStatusBarRes(R.color.redPrimaryDark)
                            .colorPrimaryDarkRes(R.color.redPrimaryDark)
                            .apply();
                    break;
                case R.id.orange:
                    editor.putInt("color_code",2);
                    editor.putString("color_name","orange");
                    Aesthetic.get()
                            .colorPrimaryRes(R.color.orangePrimary)
                            .colorAccentRes(R.color.orangePrimary)
                            .colorStatusBarRes(R.color.orangePrimaryDark)
                            .colorPrimaryDarkRes(R.color.orangePrimaryDark)
                            .apply();
                    break;
                case R.id.green:
                    editor.putInt("color_code",3);
                    editor.putString("color_name","green");
                    Aesthetic.get()
                            .colorPrimaryRes(R.color.greenPrimary)
                            .colorAccentRes(R.color.greenPrimary)
                            .colorStatusBarRes(R.color.greenPrimaryDark)
                            .colorPrimaryDarkRes(R.color.greenPrimaryDark)
                            .apply();
                    break;
                    case R.id.pink:
                        editor.putInt("color_code",4);
                        editor.putString("color_name","pink");
                    Aesthetic.get()
                            .colorPrimaryRes(R.color.pinkPrimary)
                            .colorAccentRes(R.color.pinkPrimary)
                            .colorStatusBarRes(R.color.pinkPrimaryDark)
                            .colorPrimaryDarkRes(R.color.pinkPrimaryDark)
                            .apply();
                    break;
                case R.id.purple:
                    editor.putInt("color_code",5);
                    editor.putString("color_name","purple");
                    Aesthetic.get().colorPrimaryRes(R.color.purplePrimary)
                            .colorAccentRes(R.color.purplePrimary)
                            .colorStatusBarRes(R.color.purplePrimaryDark)
                            .colorPrimaryDarkRes(R.color.purplePrimaryDark)
                            .apply();
            }
            editor.putInt("lang_btn_id",radioGroup.getCheckedRadioButtonId());
            editor.commit();
        });
        SwitchCompat switchNotification = view.findViewById(R.id.switch_notification);
        SharedPreferences notifPref = getActivity().getSharedPreferences("NOTIFICATION_PREFS",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("NOTIFICATION_PREFS",
                MODE_PRIVATE).edit();
        switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                editor.putBoolean("notification_enabled",true);
                editor.commit();
            }else {
                editor.putBoolean("notification_enabled",false);
                editor.commit();
            }
        });
        if(notifPref.getBoolean("notification_enabled",true)){
            switchNotification.setChecked(true);
        }else {
            switchNotification.setChecked(false);
        }



        Button debugButton = view.findViewById(R.id.debug_button);
        debugButton.setOnLongClickListener(v -> {
            startActivity(new Intent(getContext(), Debug_Activity.class));
            return false;
        });
        debugButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Check for updates from my telegram channel", Toast.LENGTH_SHORT).show();
        });
        SwitchCompat switchLow = view.findViewById(R.id.low_package);
        switchLow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editor.putBoolean("low_notification_enabled",true);
                editor.commit();
            }else {
                editor.putBoolean("low_notification_enabled",false);
                editor.commit();
            }
        });
        if(notifPref.getBoolean("low_notification_enabled",true)){
            switchLow.setChecked(true);
        }else {
            switchLow.setChecked(false);
        }
//        widget switch
        SwitchCompat switchWidget = view.findViewById(R.id.switch_widget);
        switchWidget.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (!mPermissionsRequested && !OverlayPermission.hasRuntimePermissionToDrawOverlay(getActivity())) {
                        @SuppressWarnings("NewApi")
                        Intent myIntent = OverlayPermission.createIntentToRequestOverlayPermission(getActivity());
                        startActivityForResult(myIntent, REQUEST_CODE_HOVER_PERMISSION);
                    } else { DemoHoverMenuService.showFloatingMenu(getContext()); }
                } else {
                    DemoHoverMenuService.showFloatingMenu(getContext());
                }
                editor.putBoolean("widget_enabled",true);
                editor.commit();
            }else {
                Objects.requireNonNull(getContext()).stopService((new Intent(getContext(), DemoHoverMenuService.class)));
                editor.putBoolean("widget_enabled",false);
                editor.commit();
            }
        });
        if(notifPref.getBoolean("widget_enabled",true)){
            switchWidget.setChecked(true);
        }else {
            switchWidget.setChecked(false);
        }
//        vibration switch
        SwitchCompat switchVibration = view.findViewById(R.id.switch_vibrations);
        switchVibration.setOnCheckedChangeListener((buttonView, isChecked) -> {
          if (isChecked){
              editor.putBoolean("vibrations_enabled",true);
              editor.commit();
          }else {
              editor.putBoolean("vibrations_enabled",false);
              editor.commit();
          }
        });
        if(notifPref.getBoolean("vibrations_enabled",true)){
            switchVibration.setChecked(true);
        }else {
            switchVibration.setChecked(false);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.navigation_account);
        super.onDestroyView();
    }
}
