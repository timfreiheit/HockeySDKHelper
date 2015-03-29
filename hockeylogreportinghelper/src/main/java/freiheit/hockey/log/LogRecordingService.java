package freiheit.hockey.log;

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

    /**
     * small helper to manage the logcat process
     */
    private LogcatReader mReader;

    /**
     * indicates if the service should be stopped
     */
    private boolean killed = false;

    /**
     * flush the buffer every 50 lines
     */
    private static final int linesUntilFlush = 50;

    /**
     * unique message to show on app start
     */
    private static final String START_MESSAGE = "//----   start Recording: "+System.currentTimeMillis()+"  ----//";

    private static final String TAG = LogRecordingService.class.getSimpleName();

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

        LogRecordingConfig config = LogRecordingManager.CONFIG;
        if(config == null){
            config = new LogRecordingConfig();
        }

        boolean logLevelAcceptsEverything = config.logLevel == Log.VERBOSE;

        StringBuilder stringBuilder = new StringBuilder();

        //no not start recording when we should clear the log first
        boolean startRecord = !config.clearBeforeStartRecording;

        try {

            //start logcat
            mReader = initializeReader(config);

            //this log indicates that the recording is started
            //needed to check when to start the recording
            Log.d(TAG, START_MESSAGE);

            String line;
            int lineCount = 0;

            while ((line = mReader.readLine()) != null && !killed) {

                //skip first lines when we are not starting to record
                if(!startRecord){
                    //check if we should start recording now
                    if(line.contains(START_MESSAGE)){
                       startRecord = true;
                    }else{
                        continue;
                    }
                }


                // filter
                // no not parse the line if not needed
                if (!logLevelAcceptsEverything) {
                    if (!checkLogLine(line, config.logLevel)) {
                        continue;
                    }
                }

                stringBuilder.append(line).append("\n");

                // write the logs to file every {linesUntilFlush}. lines
                if (++lineCount % linesUntilFlush == 0) {
                    // avoid OutOfMemoryErrors: flush now
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

    /**
     * parse the line to check the lines loglevel
     */
    private boolean checkLogLine(String line, int logLevelLimit) {
        LogLine logLine = LogLine.newLogLine(line);
        return logLine.getLogLevel() >= logLevelLimit;
    }

    /**
     * creates and start an new logcat reader
     */
    protected LogcatReader initializeReader(LogRecordingConfig config) throws IOException, InterruptedException {
        return new LogcatReader(config);
    }

    /**
     * stop the recording when the app is removed
     */
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //stop logging and save task
        killed = true;
        super.onTaskRemoved(rootIntent);
    }
}
