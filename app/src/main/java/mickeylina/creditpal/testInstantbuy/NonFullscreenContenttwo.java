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
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.mattcarroll.hover.Content;
import mickeylina.creditpal.R;

/**
 * A Hover menu screen that does not take up the entire screen.
 */
public class NonFullscreenContenttwo  implements Content {

    private final Context mContext;
    private View mContent;
    DialogFragment  dialogFragment;
    TextView internet;
    TextView sms;
    TextView voice;

    public NonFullscreenContenttwo(@NonNull Context context) {
        mContext= context.getApplicationContext();

    }

    @NonNull
    @Override
    public View getView() {
        if (null == mContent) {
            mContent = LayoutInflater.from(mContext).inflate(R.layout.instant_package, null);
            mContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

        }
        return mContent;
    }


    @Override
    public boolean isFullscreen() {
        return true;
    }

    @Override
    public void onShown() {
    }

    @Override
    public void onHidden() {
    }
}
