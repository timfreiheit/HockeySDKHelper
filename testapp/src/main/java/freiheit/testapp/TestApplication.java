package freiheit.testapp;

import android.util.Log;

import de.timfreiheit.hockey.log.LogRecordingConfig;
import de.timfreiheit.hockey.log.LogRecordingManager;
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

        LogRecordingConfig config = new LogRecordingConfig.Builder()
                .setLogLevel(Log.VERBOSE)
                .setMaxLines(1000)
                .clearLogBeforeRecording(false)
                .build();
        LogRecordingManager.init(this, config);

        HockeyLifecycleConfig lifecycleConfig = new HockeyLifecycleConfig.Builder()
                .hockeyAppId("12345678901234567890123456789012") // your hockey app id
                .updateEnabled(BuildConfig.DEBUG) //enable updates only on debug and disable them in release
                    // when you want to restrict the checks on specific activities
                .activityWhereToCheckForUpdates(MainActivity.class)

                .crashReportEnabled(true) //enable crash reporting
                    // when you want to restrict the checks on specific activities
                .activityWhereToCheckForCrashes(MainActivity.class)

                .trackingEnabled(true) //enable usage tracking
                .build();
        HockeyLifecycleHelper.init(this,lifecycleConfig);
    }
}
