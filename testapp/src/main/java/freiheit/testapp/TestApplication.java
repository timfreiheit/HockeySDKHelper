package freiheit.testapp;

import android.util.Log;

import de.timfreiheit.hockey.log.LogRecordingConfig;
import de.timfreiheit.hockey.log.LogRecordingManager;

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

    }
}
