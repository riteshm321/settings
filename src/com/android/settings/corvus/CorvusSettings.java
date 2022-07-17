/*
 * Copyright (C) 2020-2022 CorvusOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.corvus;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.support.SupportPreferenceController;
import com.android.settingslib.search.SearchIndexable;
import com.android.settingslib.core.instrumentation.Instrumentable;

@SearchIndexable(forTarget = SearchIndexable.ALL & ~SearchIndexable.ARC)
public class CorvusSettings extends DashboardFragment implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private static final String TAG = "CorvusSettings";

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.corvus_settings;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setVerticalScrollBarEnabled(false);
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.CORVUS;
    }

    @Override
    public Fragment getCallbackFragment() {
        return this;
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        new SubSettingLauncher(getActivity())
                .setDestination(pref.getFragment())
                .setArguments(pref.getExtras())
                .setSourceMetricsCategory(caller instanceof Instrumentable
                        ? ((Instrumentable) caller).getMetricsCategory()
                        : Instrumentable.METRICS_CATEGORY_UNKNOWN)
                .setTitleRes(-1)
                .launch();
        return true;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        super.onCreatePreferences(savedInstanceState, rootKey);
        final PreferenceScreen screen = getPreferenceScreen();
        // Tint the homepage icons
        final int tintColor = Utils.getHomepageIconColor(getContext());
        final int count = screen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            final Preference preference = screen.getPreference(i);
            if (preference == null) {
                break;
            }
            final Drawable icon = preference.getIcon();
            if (icon != null) {
                icon.setTint(tintColor);
            }
        }
    }

    private void setPreferenceLayout() {
        final PreferenceScreen screen = getPreferenceScreen();
        final int count = screen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            final Preference preference = screen.getPreference(i);

        String key = preference.getKey();

            if (key != null) {
                if (key.equals("game_settings")){
                    preference.setLayoutResource(R.layout.top_preference_layout);
                }
                if (key.equals("theming_fragment")){
                    preference.setLayoutResource(R.layout.middle_preference_layout);
                }
                if (key.equals("statusbar_fragment")){
                    preference.setLayoutResource(R.layout.middle_preference_layout);
                }
                if (key.equals("qs_fragment")){
                    preference.setLayoutResource(R.layout.middle_preference_layout);
                }
                if (key.equals("lockscreen_fragment")){
                    preference.setLayoutResource(R.layout.middle_preference_layout);
                }
                if (key.equals("battery_fragment")){
                    preference.setLayoutResource(R.layout.middle_preference_layout);
                }
                if (key.equals("notification_fragment")){
                    preference.setLayoutResource(R.layout.middle_preference_layout);
                }
                if (key.equals("button_fragment")){
                    preference.setLayoutResource(R.layout.middle_preference_layout);
                }
                if (key.equals("misc_fragment")){
                    preference.setLayoutResource(R.layout.bottom_preference_layout);
                }
            }
	    }
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.corvus_settings);
}
