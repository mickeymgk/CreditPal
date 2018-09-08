package mickeylina.creditpal.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.aesthetic.Aesthetic;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Objects;

import at.grabner.circleprogress.CircleProgressView;
import io.reactivex.disposables.Disposable;
import mickeylina.creditpal.R;

public class UsageStats extends Fragment {

    private UsageStats.OnDisableShadow mShadowCallBack;
    private TextView textBalance;
    private TextView textEXPdate;
    private Disposable aestheticDisposable;

    private SharedPreferences userBalance;
    private SharedPreferences balanceEdate;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private TextView textDataOn;
    private TextView textDataAt;
     private  CircleProgressView dataProgress;
     private CircleProgressView voiceProgress;
     private  CircleProgressView smsProgress;

    private ConstraintLayout cl;

    public UsageStats(){}


    // Container Activity must implement this interface
    public interface OnDisableShadow {
        void onDisable(Boolean disable);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mShadowCallBack = (UsageStats.OnDisableShadow) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPackageSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_usage,container,false);
        this.sharedPreferences = getContext().getSharedPreferences("PACKAGES_PREF", 0);
        this.editor = this.sharedPreferences.edit();
        this.editor.apply();
        textDataOn =  rootView.findViewById(R.id.data_expires);
        textDataAt =  rootView.findViewById(R.id.data_at);
        dataProgress = rootView.findViewById(R.id.circleProgressView);
        voiceProgress = rootView.findViewById(R.id.progress_voice);
        smsProgress = rootView.findViewById(R.id.progress_sms);

        aestheticDisposable = Aesthetic.get().colorPrimary().subscribe( color -> {
            dataProgress.setBarColor(color);
            dataProgress.setTextColor(color);
            dataProgress.setUnitColor(color);
            voiceProgress.setBarColor(color);
            voiceProgress.setTextColor(color);
            voiceProgress.setUnitColor(color);
            smsProgress.setBarColor(color);
            smsProgress.setTextColor(color);
            smsProgress.setUnitColor(color);
        });


        cl = rootView.findViewById(R.id.constraintlayout);
        textBalance= rootView.findViewById(R.id.textView9);
        textEXPdate= rootView.findViewById(R.id.textView3);

        mShadowCallBack.onDisable(true);

        userBalance = Objects.requireNonNull(getContext()).getSharedPreferences("user_balance",0);
        String balance = userBalance.getString("remainingBalance","00.00");
        textBalance.setText(balance);

        String expireDate = getResources().getString(R.string.birr_expires) + userBalance.getString("expires_on",
                "dd/mm/yy");
        textEXPdate.setText(expireDate);
        setDataProgress();
        return rootView;
    }

    private void setDataProgress() {
        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("PACKAGES_PREF", 0);
        Float f = sharedPreferences.getFloat("dataPackageMax",0);
        Float m = sharedPreferences.getFloat("dataPackageAvailable", 0);
        dataProgress.setMaxValue(f);
        dataProgress.setValue(m);
        String date = getContext().getResources().getString(R.string.expires_on) + sharedPreferences.getString("dataExpiresOn","");
        textDataOn.setText(date);
        String time = getContext().getResources().getString(R.string.expires_at) + sharedPreferences.getString("dataExpiresAt","");
        textDataAt.setText(time);
        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if(key.equals("dataPackageAvailable")){
                Float updated = sharedPreferences.getFloat("dataPackageAvailable",0);
                dataProgress.setValue(updated);
                setDataProgress();
            }if (key.equals("dataPackageMax")){
                Float updatedMax = sharedPreferences.getFloat("dataPackageMax",0);
                dataProgress.setValue(updatedMax);
            }if (key.equals("dataExpiresOn")){
                String updated = sharedPreferences.getString("dataExpiresOn","");
                textDataOn.setText(updated);
            }if (key.equals("dataExpiresAt")){
                String updateAt = sharedPreferences.getString("dataExpiresAt","");
                textDataAt.setText(updateAt);
            }
        });
    }

    private void setSmsProgress() {
        this.sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("PACKAGES_PREF", 0);
        Float f = sharedPreferences.getFloat("smsPackageMax",0);
        Float m = sharedPreferences.getFloat("smsPackageAvailable", 0);
        smsProgress.setMaxValue(f);
        smsProgress.setValue(m);
        String date = getContext().getResources().getString(R.string.expires_on) +
                sharedPreferences.getString("smsPackageExpiresOn","");
        textDataOn.setText(date);
        String time = getContext().getResources().getString(R.string.expires_at) +
                sharedPreferences.getString("smsPackageExpiresAt","");
        textDataAt.setText(time);
        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
                if(key.equals("smsPackageAvailable")){
                    Float updated = sharedPreferences.getFloat("smsPackageAvailable",0);
                    dataProgress.setValue(updated);
                    setDataProgress();
                }if (key.equals("smsPackageMax")){
                    Float updatedMax = sharedPreferences.getFloat("smsPackageMax",0);
                    dataProgress.setValue(updatedMax);
                }if (key.equals("smsPackageExpiresOn")){
                    String updated = sharedPreferences.getString("smsPackageExpiresOn","");
                    textDataOn.setText(updated);
                }if (key.equals("smsPackageExpiresAt")){
                    String updateAt = sharedPreferences.getString("smsPackageExpiresAt","");
                    textDataAt.setText(updateAt);
                }
        });

    }

    private void setVoiceProgress() {
        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("PACKAGES_PREF", 0);
        Float f = sharedPreferences.getFloat("voicePackageMax",0);
        Float m = sharedPreferences.getFloat("voicePackageAvailable", 0);
        smsProgress.setMaxValue(f);
        smsProgress.setValue(m);
        String date = getContext().getResources().getString(R.string.expires_on) +
                sharedPreferences.getString("voicePackageExpiresOn","");
        textDataOn.setText(date);
        String time = getContext().getResources().getString(R.string.expires_at) +
                sharedPreferences.getString("voicePackageExpiresAt","");
        textDataAt.setText(time);
        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if(key.equals("voicePackageAvailable")){
                Float updated = sharedPreferences.getFloat("voicePackageAvailable",0);
                dataProgress.setValue(updated);
                setDataProgress();
            }if (key.equals("voicePackageMax")){
                Float updatedMax = sharedPreferences.getFloat("voicePackageMax",0);
                dataProgress.setValue(updatedMax);
            }if (key.equals("voicePackageExpiresOn")){
                String updated = sharedPreferences.getString("voicePackageExpiresOn","");
                textDataOn.setText(updated);
            }if (key.equals("voicePackageExpiresAt")){
                String updateAt = sharedPreferences.getString("voicePackageExpiresAt","");
                textDataAt.setText(updateAt);
            }
        });
    }

    @Override
    public void onDestroyView() {
        YoYo.with(Techniques.SlideOutUp).duration(150).playOn(cl);
        mShadowCallBack.onDisable(false);
//        Disposing the disposable before it dispose an error on the next fragment :-)
        aestheticDisposable.dispose();
        super.onDestroyView();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_lang).setVisible(false);
        menu.findItem(R.id.action_refresh).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String pre= "*804%23";
        String dial = "tel:" + pre;
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        updateBalance();
        setDataProgress();
        setSmsProgress();
        setVoiceProgress();
        switch (item.getItemId()) {
            case R.id.action_refresh:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateBalance() {
        textBalance= getView().findViewById(R.id.textView9);
        textEXPdate= getView().findViewById(R.id.textView3);
        userBalance = getActivity().getApplicationContext().getSharedPreferences("user_balance",0);
        userBalance.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if(key.equals("remainingBalance")){
                String balanceRef= userBalance.getString("remainingBalance","00.00");
                textBalance.setText(balanceRef);
            }
        });
        balanceEdate =getActivity().getApplicationContext().getSharedPreferences("user_balance",0);
        balanceEdate.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if(key.equals("expires_on")){
                String dateRefreshed=getResources().getString(R.string.birr_expires)
                        + balanceEdate.getString("expires_on","dd/mm/yy");
//                String date=textEXPdate.getText().toString().replace("date",dateRefreshed);
                textEXPdate.setText(dateRefreshed);
            }
        });
    }

}
