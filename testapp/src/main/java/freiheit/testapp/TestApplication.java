package freiheit.testapp;

import de.timfreiheit.hockey.listeners.CombinedDescriptionListener;
import de.timfreiheit.hockey.listeners.LogCrashManagerListener;
import de.timfreiheit.hockey.listeners.MemoryInfoCrashManagerListener;
import de.timfreiheit.hockey.utils.HockeyLifecycleConfig;
import de.timfreiheit.hockey.utils.HockeyLifecycleHelper;

/**
 *
 * Created by timfreiheit on 29.03.15.
 */
public class TestApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CombinedDescriptionListener crashManagerListener = new CombinedDescriptionListener(this)
                .addPart("Memory", new MemoryInfoCrashManagerListener())
                .addPart("Log", new LogCrashManagerListener());

        HockeyLifecycleConfig lifecycleConfig = new HockeyLifecycleConfig.Builder()
                // your hockey app id
                .hockeyAppId("12345678901234567890123456789012")
                //enable updates only on debug and disable them in release
                .updateEnabled(BuildConfig.DEBUG)

                // when you want to restrict the checks on specific activities
                .activityWhereToCheckForUpdates(MainActivity.class)

                .crashManagerListener(crashManagerListener)
                .crashReportEnabled(true) //enable crash reporting
                    // when you want to restrict the checks on specific activities
                .activityWhereToCheckForCrashes(MainActivity.class)

                .trackingEnabled(true) //enable usage tracking
                .build();
        HockeyLifecycleHelper.init(this,lifecycleConfig);
    }
}
