package de.timfreiheit.hockey.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.Tracking;
import net.hockeyapp.android.UpdateManager;

/**
 *
 * LifeCycleListener to enable the Hockey functionality without adding code in your activities
 *
 * Created by timfreiheit on 29.03.15.
 */
public class HockeyLifecycleHelper implements Application.ActivityLifecycleCallbacks{

    private static HockeyLifecycleHelper INSTANCE;

    HockeyLifecycleConfig mConfig;

    private HockeyLifecycleHelper(HockeyLifecycleConfig config){
        if(config == null){
            config = new HockeyLifecycleConfig();
        }
        mConfig = config;
    }

    /**
     * call this in {@link Application#onCreate()} to start the usage tracking
     * @param app the base application
     */
    public static void init(Application app, HockeyLifecycleConfig config){
        if(INSTANCE == null){
            INSTANCE = new HockeyLifecycleHelper(config);
            app.registerActivityLifecycleCallbacks(INSTANCE);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if(isUpdatedEnabledForActivity(activity)){
            if( TextUtils.isEmpty(mConfig.getHockeyAppId()) ){
                return;
            }
            UpdateManager.register(activity, mConfig.getHockeyAppId(), mConfig.getUpdateManagerListener(), mConfig.isUpdateDialogRequired());
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

        if(isCrashEnabledForActivity(activity)){
            CrashManager.register(activity, mConfig.getHockeyAppId(), mConfig.getCrashManagerListener());
        }

        if(mConfig.isTrackingEnabled()) {
            Tracking.startUsage(activity);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

        if(isUpdatedEnabledForActivity(activity)){
            UpdateManager.unregister();
        }

        if(mConfig.isTrackingEnabled()) {
            Tracking.stopUsage(activity);
        }

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    /**
     * check if we should check for updates in the given activity
     */
    private boolean isUpdatedEnabledForActivity(Activity activity){
        if(activity == null) {
            return false;
        }
        Class<? extends Activity> clazz = mConfig.getActivityWhereToCheckForUpdate();
        return mConfig.isUpdateEnabled() && (clazz == null || clazz.isInstance(activity));
    }

    /**
     * check if we should check for crashes in the given activity
     */
    private boolean isCrashEnabledForActivity(Activity activity){
        if(activity == null) {
            return false;
        }
        Class<? extends Activity> clazz = mConfig.getActivityWhereToCheckForCrashes();
        return mConfig.isCrashReportEnabled() && (clazz == null || clazz.isInstance(activity));
    }

}
