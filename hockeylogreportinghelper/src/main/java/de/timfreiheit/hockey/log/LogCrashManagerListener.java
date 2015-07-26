package de.timfreiheit.hockey.log;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import net.hockeyapp.android.CrashManagerListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * reports last logfile to hockey if possible
 * Created by timfreiheit on 22.03.15.
 */
public class LogCrashManagerListener extends CrashManagerListener {

    private static final String TAG = LogCrashManagerListener.class.getSimpleName();

    private static final String START_MESSAGE = "//----   AppStart: "+System.currentTimeMillis()+" - language: "+ Locale.getDefault().toString() +"  ----//";

    private Context context;
    private int logLevel;

    /**
     *
     * @param loglevel should be any of
     *                 [{@link Log#VERBOSE},
     *                 {@link Log#DEBUG},
     *                 {@link Log#INFO},
     *                 {@link Log#WARN},
     *                 {@link Log#ERROR}}]
     */
    public LogCrashManagerListener(Context context, int loglevel){
        this.context = context;
        this.logLevel = loglevel;

        Log.i(TAG,START_MESSAGE);
    }

    @Override
    public boolean shouldAutoUploadCrashes() {
        return true;
    }

    public String getUserID() {
        try {
            return Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }catch (Exception e){
            // just to make sure
            return super.getUserID();
        }
    }

    @Override
    public String getDescription() {
        String description = "";

        try {

            Process process = Runtime.getRuntime().exec(logcatParamsFromConfig());
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder log = new StringBuilder();
            String line;

            // skip all messages until messages starts form the current application process
            boolean startRecord = false;
            while ((line = bufferedReader.readLine()) != null) {

                //skip first lines when we are not starting to record
                if(!startRecord){
                    //check if we should start recording now
                    if(line.contains(START_MESSAGE)){
                        startRecord = true;
                    }else{
                        continue;
                    }
                }

                log.append(line);
                log.append(System.getProperty("line.separator"));
            }
            bufferedReader.close();

            description = log.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return description;
    }


    private String[] logcatParamsFromConfig(){

        String logLevel = "*:V";
        switch (this.logLevel){
            case Log.VERBOSE:
                logLevel = "*:V";
                break;
            case Log.DEBUG:
                logLevel = "*:D";
                break;
            case Log.INFO:
                logLevel = "*:I";
                break;
            case Log.WARN:
                logLevel = "*:W";
                break;
            case Log.ERROR:
                logLevel = "*.E";
                break;
        }

        String includeLogManagerListenerTagInLogcat = TAG+":I";
        return new String[]{"logcat", "-d", includeLogManagerListenerTagInLogcat ,logLevel};
    }
}
