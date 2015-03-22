package freiheit.hockeylogreportinghelper;

import net.hockeyapp.android.CrashManagerListener;

/**
 * reports last logfile to hockey if possible
 * Created by timfreiheit on 22.03.15.
 */
public class LogCrashManagerListener extends CrashManagerListener {

    @Override
    public boolean shouldAutoUploadCrashes() {
        return true;
    }

    @Override
    public String getDescription() {
        if(LogRecordingManager.CONFIG != null){
            return LogFileHelper.readLastLogFile(LogRecordingManager.APPLICATION_INSTANCE, LogRecordingManager.CONFIG.maxLinesPerLog);
        }
        return LogFileHelper.readLastLogFile(LogRecordingManager.APPLICATION_INSTANCE, 1000);
    }
}
