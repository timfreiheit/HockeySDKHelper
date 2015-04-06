package de.timfreiheit.hockey.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import net.hockeyapp.android.Tracking;

/**
 * uses the Hockey-Statistics to track the usage time
 * Created by timfreiheit on 29.03.15.
 */
public class HockeyActivityUsageTrackingHelper implements Application.ActivityLifecycleCallbacks{

    private static HockeyActivityUsageTrackingHelper INSTANCE;

    /**
     * call this in {@link Application#onCreate()} to start the usage tracking
     * @param app
     */
    public static void init(Application app){
        if(INSTANCE == null){
            INSTANCE = new HockeyActivityUsageTrackingHelper();
            app.registerActivityLifecycleCallbacks(INSTANCE);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Tracking.startUsage(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Tracking.stopUsage(activity);
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
}
