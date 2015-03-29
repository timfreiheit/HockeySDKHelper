package freiheit.testapp;

import android.util.Log;

import freiheit.hockey.log.LogRecordingConfig;
import freiheit.hockey.log.LogRecordingManager;

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
                .clearLogBeforeRecording(false)
                .build();
        LogRecordingManager.init(this, config);

    }
}
