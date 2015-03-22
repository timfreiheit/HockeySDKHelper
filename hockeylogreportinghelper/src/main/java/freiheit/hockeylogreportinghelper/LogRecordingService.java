package freiheit.hockeylogreportinghelper;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

/**
 * records all logs of the own process
 *
 * Created by timfreiheit on 22.03.15.
 */
public class LogRecordingService extends IntentService {

    private LogcatReader mReader;
    private boolean killed = false;
    private static final int linesUntilFlush = 50;

    private static final String TAG = LogRecordingService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public LogRecordingService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        record(intent);
    }

    /**
     * start logcat recording
     * @param intent start intent
     */
    private void record(Intent intent){

        Log.d(TAG,"//----------------------------   start Recording   ----------------------------//");

        LogRecordingConfig config = LogRecordingManager.CONFIG;
        if(config == null){
            config = new LogRecordingConfig();
        }

        boolean logLevelAcceptsEverything = config.logLevel == Log.INFO;

        StringBuilder stringBuilder = new StringBuilder();

        try {

            initializeReader(config);

            String line;
            int lineCount = 0;

            while ((line = mReader.readLine()) != null && !killed) {

                // filter
                if (!logLevelAcceptsEverything) {
                    if (!checkLogLine(line, config.logLevel)) {
                        continue;
                    }
                }

                stringBuilder.append(line).append("\n");

                // write the logs to file every {linesUntilFlush}. lines
                if (++lineCount % linesUntilFlush == 0) {
                    // avoid OutOfMemoryErrors; flush now
                    LogFileHelper.saveLog(getApplication(), stringBuilder);
                    stringBuilder.delete(0, stringBuilder.length()); // clear
                }
            }
        } catch (Exception e) {
            // log reporting should never crash the application
            e.printStackTrace();
        } finally {
            killProcess();
            boolean logSaved = LogFileHelper.saveLog(getApplication(), stringBuilder);
            Log.d(TAG,"Log Saved: "+logSaved);
        }
    }

    private void killProcess() {
        if ( mReader != null ) {
            mReader.killProcess();
        }
    }

    private boolean checkLogLine(String line, int logLevelLimit) {
        LogLine logLine = LogLine.newLogLine(line);
        return logLine.getLogLevel() >= logLevelLimit;
    }

    private void initializeReader(LogRecordingConfig config) throws IOException, InterruptedException {
        mReader = new LogcatReader(config);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //stop logging and save task
        killed = true;
        super.onTaskRemoved(rootIntent);
    }
}
