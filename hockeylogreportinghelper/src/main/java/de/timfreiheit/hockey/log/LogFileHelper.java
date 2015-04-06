package de.timfreiheit.hockey.log;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;

/**
 * takes care of writing and reading loggiles
 * Created by timfreiheit on 22.03.15.
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
class LogFileHelper {

    static int BUFFER_SIZE = 4096;

    /**
     * rename the log file from last app start if possible
     * remove the unused log files
     */
    static void initLogFileOnNewAppStart(Context context) {
        File oldLogFile = new File(context.getCacheDir(), LogRecordingManager.LOG_FILE_NAME_LAST_RUN);
        File newLogFile = new File(context.getCacheDir(), LogRecordingManager.LOG_FILE_NAME);
        if (oldLogFile.exists()) {
            oldLogFile.delete();
        }

        if (newLogFile.exists()) {
            newLogFile.renameTo(oldLogFile);
        }

        newLogFile = new File(context.getCacheDir(), LogRecordingManager.LOG_FILE_NAME);
        if(newLogFile.exists()){
            newLogFile.delete();
        }
    }

    /**
     * save the logString by appending it to the existing file if possible
     */
    static boolean saveLog(Context context, CharSequence logString) {

        File newFile = new File(context.getCacheDir(), LogRecordingManager.LOG_FILE_NAME);
        try {
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
        } catch (IOException ex) {
            return false;
        }
        PrintStream out = null;
        try {

            out = new PrintStream(new BufferedOutputStream(new FileOutputStream(newFile, true), BUFFER_SIZE));

            if (logString != null) {
                out.print(logString);
            }

        } catch (FileNotFoundException ex) {
            return false;
        } finally {
            if (out != null) {
                out.close();
            }
        }

        return true;
    }

    /**
     * reads log from last app run
     */
    static String readLastLogFile(Context context, int maxLines) {
        if(context == null){
            return null;
        }
        File oldLogFile = new File(context.getCacheDir(), LogRecordingManager.LOG_FILE_NAME_LAST_RUN);

        if (!oldLogFile.exists()) {
            return null;
        }

        LinkedList<String> logLines = new LinkedList<>();

        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(oldLogFile)), BUFFER_SIZE);

            while (bufferedReader.ready()) {
                logLines.add(bufferedReader.readLine());
                if (logLines.size() > maxLines) {
                    logLines.removeFirst();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for (String s : logLines) {
            builder.append(s);
            builder.append("\n");
        }
        return builder.toString();
    }
}
