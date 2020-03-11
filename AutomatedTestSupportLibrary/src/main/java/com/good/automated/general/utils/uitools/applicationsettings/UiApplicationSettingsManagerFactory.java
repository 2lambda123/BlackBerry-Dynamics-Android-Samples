/* Copyright (c) 2017 - 2020 BlackBerry Limited.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/

package com.good.automated.general.utils.uitools.applicationsettings;

import android.util.Log;

import com.good.automated.general.utils.uitools.UiPlatformManager;

import java.util.Arrays;

/**
 * Factory for creating platform specific {@link UiAndroidApplicationSettingsManager} instances.
 */
public class UiApplicationSettingsManagerFactory {


    private static UiApplicationSettingsManager managerInstance;
    private static final String INSTANTIATION_ERROR_MASSAGE = "Failed to instantiate manager for this platform. Manufacturer: %s; Model: %s; Hardware: %s; API level: %d";
    private static final String TAG = UiApplicationSettingsManagerFactory.class.getSimpleName();

    /**
     * Returns an instance of {@link UiApplicationSettingsManager} for current Android platform.
     * Usage of components which interacts with the UI is highly relays on the UI structure.
     * So for the support of any new Android version or for the specific vendor of the Android devices
     * this method should be revisited and if it is necessary new subclasses of {@link UiApplicationSettingsManager}
     * created.
     *
     * @return platform-specific {@link UiApplicationSettingsManager}
     *
     */
    public static UiApplicationSettingsManager getManager() {
        if (managerInstance != null) {
            return managerInstance;
        }

        UiPlatformManager platformManager = UiPlatformManager.getInstance();

        if (platformManager.isSamsungDevice() && platformManager.satisfiesApiLevel(Arrays.asList(22,23,24,25,26)) ) {
            Log.d(TAG, "Instantiating manager for Samsung devices ");
            //default manager works fine in this case
            return managerInstance = new UiSamsungApplicationSettingsManager();
        }
        else if (platformManager.isLGDevice() && platformManager.satisfiesApiLevel(Arrays.asList(21,22,23,24,25))) {
            Log.d(TAG, "Instantiating manager for LG devices ");
            return managerInstance = new UiLGApplicationSettingsManager();
        }
        else
        {
            Log.d(TAG, "Instantiating default manager");
            return managerInstance = new UiAndroidApplicationSettingsManager();
        }

    }


}
