package de.timfreiheit.hockey.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import net.hockeyapp.android.CrashManagerListener;
import net.hockeyapp.android.UpdateManagerListener;
import net.hockeyapp.android.metrics.MetricsManager;

import de.timfreiheit.hockey.listeners.BaseCrashManagerListener;
import de.timfreiheit.hockey.listeners.LogCrashManagerListener;

public class HockeyLifecycleConfig {

    protected String hockeyAppId;
    protected boolean trackingEnabled = false;
    protected boolean updateEnabled = false;
    protected boolean metricsEnabled = false;

    protected boolean updateDialogRequired = true;
    protected UpdateManagerListener updateManagerListener;
    protected Class<? extends Activity> activityWhereCheckForUpdate;

    protected boolean crashReportEnabled = false;

    protected CrashManagerListener crashManagerListener;
    protected Class<? extends Activity> activityWhereCheckForCrashes;

    public String getHockeyAppId() {
        return hockeyAppId;
    }

    public boolean isMetricsEnabled() {
        return metricsEnabled;
    }

    public boolean isTrackingEnabled() {
        return trackingEnabled;
    }

    public boolean isUpdateEnabled() {
        return updateEnabled;
    }

    public boolean isUpdateDialogRequired() {
        return updateDialogRequired;
    }

    public boolean isCrashReportEnabled() {
        return crashReportEnabled;
    }

    public UpdateManagerListener getUpdateManagerListener() {
        return updateManagerListener;
    }

    public CrashManagerListener getCrashManagerListener() {
        return crashManagerListener;
    }

    public Class<? extends Activity> getActivityWhereToCheckForUpdate() {
        return activityWhereCheckForUpdate;
    }

    public Class<? extends Activity> getActivityWhereToCheckForCrashes() {
        return activityWhereCheckForCrashes;
    }

    public static class Builder {

        private String hockeyAppId;
        private boolean trackingEnabled = false;
        private boolean updateEnabled = false;
        private boolean updateDialogRequired = true;
        private boolean metricsEnabled = false;
        private UpdateManagerListener updateManagerListener;
        private Class<? extends Activity> activityWhereCheckForUpdate;

        private boolean crashReportEnabled = false;
        private CrashManagerListener crashManagerListener;
        private Class<? extends Activity> activityWhereCheckForCrashes;

        /**
         * Builds configured {@link HockeyLifecycleConfig} object
         */
        public HockeyLifecycleConfig build() {
            HockeyLifecycleConfig config = new HockeyLifecycleConfig();
            config.hockeyAppId = hockeyAppId;
            config.trackingEnabled = trackingEnabled;
            config.updateEnabled = updateEnabled;
            config.metricsEnabled = metricsEnabled;
            config.updateDialogRequired = updateDialogRequired;
            config.crashReportEnabled = crashReportEnabled;
            config.updateManagerListener = updateManagerListener;
            if (crashManagerListener != null) {
                config.crashManagerListener = crashManagerListener;
            } else {
                config.crashManagerListener = new BaseCrashManagerListener();
            }
            config.activityWhereCheckForCrashes = activityWhereCheckForCrashes;
            config.activityWhereCheckForUpdate = activityWhereCheckForUpdate;
            return config;
        }

        /**
         * the hockey app id used to send crash reports and check for updates
         * not needed when doing this manually and only use the tracking function
         * with this helper
         */
        public Builder hockeyAppId(String hockeyAppId) {
            this.hockeyAppId = hockeyAppId;
            return this;
        }

        /**
         * automatically track the usage time of the app
         * default is false
         */
        public Builder trackingEnabled(boolean enabled) {
            this.trackingEnabled = enabled;
            return this;
        }

        /**
         * check for update on every {@link Activity#onCreate(Bundle)}
         * default is false
         */
        public Builder updateEnabled(boolean enabled) {
            this.updateEnabled = enabled;
            return this;
        }

        /**
         * @see @{@link MetricsManager#register(Context, Application)}
         */
        public Builder metricsEnabled(boolean metricsEnabled) {
            this.metricsEnabled = metricsEnabled;
            return this;
        }

        /**
         * if false, no alert dialog is shown
         * default is true
         */
        public Builder updateDialogRequired(boolean required) {
            this.updateDialogRequired = required;
            return this;
        }

        /**
         * check and send crashed on {@link Activity#onResume()}
         * default is false
         */
        public Builder crashReportEnabled(boolean enabled) {
            this.crashReportEnabled = enabled;
            return this;
        }

        /**
         * only check for updates when an specific Activity is created
         */
        public Builder activityWhereToCheckForUpdates(Class<? extends Activity> clazz) {
            this.activityWhereCheckForUpdate = clazz;
            return this;
        }

        /**
         * only check for crashes when an specific Activity is created
         */
        public Builder activityWhereToCheckForCrashes(Class<? extends Activity> clazz) {
            this.activityWhereCheckForCrashes = clazz;
            return this;
        }

        /**
         * custom {@link UpdateManagerListener}
         * default is null
         */
        public Builder updateManagerListener(UpdateManagerListener listener) {
            this.updateManagerListener = listener;
            return this;
        }

        /**
         * custom {@link CrashManagerListener}
         */
        public Builder crashManagerListener(CrashManagerListener listener) {
            this.crashManagerListener = listener;
            return this;
        }
    }
}
