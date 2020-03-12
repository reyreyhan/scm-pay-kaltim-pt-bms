/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.bm.main.pos.services.backgroundlocationupdates;

import android.content.Context;
import android.preference.PreferenceManager;


class LocationRequestHelper {

 final static String KEY_LOCATION_UPDATES_REQUESTED = "location-updates-requested";
//    final static String KEY_LOCATION_UPDATES_REQUESTED = "location";
//    final static String KEY_PLACE_UPDATES_REQUESTED = "place";

    static void setRequestingCoordinate(Context context, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_LOCATION_UPDATES_REQUESTED, value)
                .apply();
    }

    static boolean getRequestingCoordinate(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_LOCATION_UPDATES_REQUESTED, false);
    }
//    static void setRequestingPlace(Context context, boolean value) {
//        PreferenceManager.getDefaultSharedPreferences(context)
//                .edit()
//                .putBoolean(KEY_PLACE_UPDATES_REQUESTED, value)
//                .apply();
//    }
//
//    static boolean getRequestingPlace(Context context) {
//        return PreferenceManager.getDefaultSharedPreferences(context)
//                .getBoolean(KEY_PLACE_UPDATES_REQUESTED, false);
//    }
}
