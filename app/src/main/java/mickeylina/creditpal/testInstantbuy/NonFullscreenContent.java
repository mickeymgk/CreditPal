/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mickeylina.creditpal.testInstantbuy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Objects;

import at.grabner.circleprogress.CircleProgressView;
import io.mattcarroll.hover.Content;
import me.itangqi.waveloadingview.WaveLoadingView;
import mickeylina.creditpal.R;

/**
 * A Hover menu screen that take up the entire screen.
 */
class NonFullscreenContent implements Content {

    private final Context mContext;
    private View mContent;
    private AdView mAdView;
    private CircleProgressView dataProgress;
    private CircleProgressView smsProgress;
    private CircleProgressView voiceProgress;
    private WaveLoadingView balance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences userBalance;
    private SharedPreferences.Editor editor;


    public NonFullscreenContent(@NonNull Context context) {
        mContext= context.getApplicationContext();
        this.sharedPreferences = mContext.getSharedPreferences("PACKAGES_PREF", 0);
        this.editor = this.sharedPreferences.edit();
        this.editor.apply();

    }

    @NonNull
    @Override
    public View getView() {
        if (null == mContent) {
            mContent = LayoutInflater.from(mContext).inflate(R.layout.instant_check, null);
            mContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            this.sharedPreferences = mContext.getSharedPreferences("PACKAGES_PREF", 0);
            this.editor = this.sharedPreferences.edit();
            this.editor.apply();
            mAdView = mContent.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            dataProgress=mContent.findViewById(R.id.internet_progress);
            smsProgress=mContent.findViewById(R.id.sms_progress);
            voiceProgress=mContent.findViewById(R.id.voice_progress);
            balance=mContent.findViewById(R.id.progress_balance);

//            coloring the content

//                    });

            dataProgress.setOnClickListener(v -> DemoHoverMenuService.Refresh(mContext.getApplicationContext()));
            smsProgress.setOnClickListener(v -> DemoHoverMenuService.Refresh(mContext.getApplicationContext()));
            balance.setOnClickListener(v -> DemoHoverMenuService.Refresh(mContext.getApplicationContext()));
            voiceProgress.setOnClickListener(v -> DemoHoverMenuService.Refresh(mContext.getApplicationContext()));
            setDataProgress();
            setBalance();
            setColor();
        }
        return mContent;
    }

    private void setColor() {
        int color= mContext.getResources().getColor(R.color.colorPrimary);
        dataProgress.setBarColor(color);
        dataProgress.setTextColor(color);
        dataProgress.setUnitColor(color);
        voiceProgress.setBarColor(color);
        voiceProgress.setTextColor(color);
        voiceProgress.setUnitColor(color);
        smsProgress.setBarColor(color);
        smsProgress.setTextColor(color);
        smsProgress.setUnitColor(color);
        balance.setBackgroundColor(color);
        balance.setWaveBgColor(color);
    }

    private void setBalance() {
        userBalance = mContext.getSharedPreferences("user_balance",0);
        String remBalance = userBalance.getString("remainingBalance","");
        balance.setCenterTitle(remBalance);
        userBalance.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if(key.equals("remainingBalance")){
                String balanceRef= userBalance.getString("remainingBalance","00.00");
               balance.setCenterTitle(balanceRef);
            }
        });

    }

    private void request() {
        String pre= "*804%23";
        String dial = "tel:" + pre;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(dial));
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
    }

    private void setDataProgress() {
        this.sharedPreferences = Objects.requireNonNull(mContext).getSharedPreferences("PACKAGES_PREF", 0);
        Float f = sharedPreferences.getFloat("dataPackageMax",0);
        Float m = sharedPreferences.getFloat("dataPackageAvailable", 0);
        this.dataProgress.setMaxValue(f);
        this.dataProgress.setValue(m);
        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if(key.equals("dataPackageAvailable")){
                Float updated = sharedPreferences.getFloat("dataPackageAvailable",0);
                dataProgress.setValue(updated);
                setDataProgress();
            }if (key.equals("dataPackageMax")){
                Float updatedMax = sharedPreferences.getFloat("dataPackageMax",0);
                dataProgress.setValue(updatedMax);
            }
        });
    }

    private void setSmsProgress() {
        this.sharedPreferences = Objects.requireNonNull(mContext).getSharedPreferences("PACKAGES_PREF", 0);
        Float f = sharedPreferences.getFloat("smsPackageMax",0);
        Float m = sharedPreferences.getFloat("smsPackageAvailable", 0);
        smsProgress.setMaxValue(f);
        smsProgress.setValue(m);
        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if(key.equals("smsPackageAvailable")){
                Float updated = sharedPreferences.getFloat("smsPackageAvailable",0);
                dataProgress.setValue(updated);
                setDataProgress();
            }if (key.equals("smsPackageMax")){
                Float updatedMax = sharedPreferences.getFloat("smsPackageMax",0);
                dataProgress.setValue(updatedMax);
            }
        });

    }

    private void setVoiceProgress() {
        this.sharedPreferences = Objects.requireNonNull(mContext).getSharedPreferences("PACKAGES_PREF", 0);
        Float f = sharedPreferences.getFloat("voicePackageMax",0);
        Float m = sharedPreferences.getFloat("voicePackageAvailable", 0);
        voiceProgress.setMaxValue(f);
        voiceProgress.setValue(m);
        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if(key.equals("voicePackageAvailable")){
                Float updated = sharedPreferences.getFloat("voicePackageAvailable",0);
                dataProgress.setValue(updated);
                setDataProgress();
            }if (key.equals("voicePackageMax")){
                Float updatedMax = sharedPreferences.getFloat("voicePackageMax",0);
                dataProgress.setValue(updatedMax);
            }
        });
    }

    @Override
    public boolean isFullscreen() {
        return true;
    }

    @Override
    public void onShown() {
        setVoiceProgress();
        setSmsProgress();
        setDataProgress();
    }

    @Override
    public void onHidden() {
    }
}
