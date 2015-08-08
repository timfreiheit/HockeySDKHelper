package de.timfreiheit.hockey.utils;

import android.text.TextUtils;
import android.util.Log;

import net.hockeyapp.android.Constants;
import net.hockeyapp.android.CrashManagerListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.UUID;

/**
 * save an exception as a warning.
 * a warning is an exception which to not crash the app but should be notified by someone to fix it
 * <p/>
 * Created by timfreiheit on 08.08.15.
 */
public class WarningExceptionHandler {

    /**
     * @see {@link #saveException(Throwable, String)}
     */
    public static void saveException(Throwable e) {
        saveException(e, "");
    }

    /**
     * uses the configs in {@link HockeyLifecycleHelper} to get all information on how to save the exception
     *
     * @param e    throwable to report
     * @param info some information to display before all other crash description
     */
    public static void saveException(Throwable e, String info) {
        if (HockeyLifecycleHelper.getInstance() != null) {
            saveException(e, HockeyLifecycleHelper.getInstance().getConfig().getCrashManagerListener(), info);
        } else {
            saveException(e, null, info);
        }
    }

    /**
     * @see {@link #saveException(Throwable, CrashManagerListener, String)}
     */
    public static void saveException(Throwable e, CrashManagerListener listener) {
        saveException(e, listener, null);
    }


    /**
     * @param exception throwable to report
     * @param listener  the CrashManagerListener to get more information from
     * @param info      some information to display before all other crash description
     */
    public static void saveException(final Throwable exception, final CrashManagerListener listener, final String info) {
        // saving the exception can take some time
        // the user or the app should not notice this
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveExceptionSync(exception, listener, info);
            }
        }).start();
    }

    /**
     * @param exception throwable to report
     * @param listener  the CrashManagerListener to get more information from
     * @param info      some information to display before all other crash description
     */
    private static void saveExceptionSync(Throwable exception, CrashManagerListener listener, String info) {

        final Date now = new Date();
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        printWriter.append("WARNING: ");

        exception.printStackTrace(printWriter);

        try {
            // Create filename from a random uuid
            String filename = UUID.randomUUID().toString();
            String path = Constants.FILES_PATH + "/" + filename + ".stacktrace";
            Log.d(Constants.TAG, "Writing unhandled exception to: " + path);

            // Write the stacktrace to disk
            BufferedWriter write = new BufferedWriter(new FileWriter(path));

            // HockeyApp expects the package name in the first line!
            write.write("Package: " + Constants.APP_PACKAGE + "\n");
            write.write("Version Code: " + Constants.APP_VERSION + "\n");
            write.write("Version Name: " + Constants.APP_VERSION_NAME + "\n");

            if ((listener == null) || (listener.includeDeviceData())) {
                write.write("Android: " + Constants.ANDROID_VERSION + "\n");
                write.write("Manufacturer: " + Constants.PHONE_MANUFACTURER + "\n");
                write.write("Model: " + Constants.PHONE_MODEL + "\n");
            }

            if (Constants.CRASH_IDENTIFIER != null && (listener == null || listener.includeDeviceIdentifier())) {
                write.write("CrashReporter Key: " + Constants.CRASH_IDENTIFIER + "\n");
            }

            write.write("Date: " + now + "\n");
            write.write("\n");
            write.write(result.toString());
            write.flush();
            write.close();

            if (listener != null) {
                writeValueToFile(limitedString(listener.getUserID()), filename + ".user");
                writeValueToFile(limitedString(listener.getContact()), filename + ".contact");
            }
            writeDescriptionFile(filename, listener, info);
        } catch (Exception another) {
            Log.e(Constants.TAG, "Error saving exception stacktrace!\n", another);
        }
    }

    /**
     * builds and save the crash description
     */
    private static void writeDescriptionFile(String filename, CrashManagerListener listener, String info) {

        if (listener == null && TextUtils.isEmpty(info)) {
            return;
        }
        String path = Constants.FILES_PATH + "/" + filename + ".description";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            if (info != null) {
                writer.write("Information:");
                writer.write("\n");
                writer.write(info);
                writer.write("\n\n");
            }
            writer.flush();
            if (listener != null) {
                writer.write(listener.getDescription());
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            // we can not do anything when this failed but it is important not to crash the app
        }
    }

    private static void writeValueToFile(String value, String filename) {
        try {
            String path = Constants.FILES_PATH + "/" + filename;
            if (value.trim().length() > 0) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write(value);
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            // we can not do anything when this failed but it is important not to crash the app
        }
    }

    private static String limitedString(String string) {
        if ((string != null) && (string.length() > 255)) {
            string = string.substring(0, 255);
        }
        return string;
    }

}
