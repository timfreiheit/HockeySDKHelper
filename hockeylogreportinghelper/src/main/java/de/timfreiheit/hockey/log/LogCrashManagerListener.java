package de.timfreiheit.hockey.log;

import android.util.Log;

import net.hockeyapp.android.CrashManagerListener;

/**
 * reports last logfile to hockey if possible
 * Created by timfreiheit on 22.03.15.
 */
public class LogCrashManagerListener extends CrashManagerListener {

    private static final String TAG = LogCrashManagerListener.class.getSimpleName();

    @Override
    public boolean shouldAutoUploadCrashes() {
        return true;
    }

    @Override
    public String getDescription() {
        if(LogRecordingManager.APPLICATION_INSTANCE == null){
            Log.e(TAG,"LogRecordingManager.init not called");
            return null;
        }
        String log;
        if (LogRecordingManager.CONFIG != null) {
            log = LogFileHelper.readLastLogFile(LogRecordingManager.APPLICATION_INSTANCE, LogRecordingManager.CONFIG.maxLinesPerLog);
        } else {
            log = LogFileHelper.readLastLogFile(LogRecordingManager.APPLICATION_INSTANCE, 1000);
        }
        return log;
    }
}
