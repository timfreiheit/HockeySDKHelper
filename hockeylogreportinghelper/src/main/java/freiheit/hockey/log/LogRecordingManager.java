package freiheit.hockey.log;

import android.app.Application;
import android.content.Intent;

/**
 *
 * Created by timfreiheit on 22.03.15.
 */
public class LogRecordingManager {

    static final String LOG_FILE_NAME_LAST_RUN = "log_last_run.txt";
    static final String LOG_FILE_NAME = "log.txt";

    static Application APPLICATION_INSTANCE;
    static LogRecordingConfig CONFIG;

    /**
     * init the log recording service
     * must be called in {@link Application#onCreate()} to provide the best result
     */
    public static void init(Application app, LogRecordingConfig config){
        if(APPLICATION_INSTANCE != null){
            return;
        }
        APPLICATION_INSTANCE = app;
        if(config == null){
            config = new LogRecordingConfig();
        }

        CONFIG = config;
        LogFileHelper.initLogFileOnNewAppStart(app);

        Intent intent = new Intent(app,LogRecordingService.class);
        app.startService(intent);
    }

}
