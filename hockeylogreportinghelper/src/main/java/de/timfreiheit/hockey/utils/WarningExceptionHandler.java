package de.timfreiheit.hockey.utils;

import net.hockeyapp.android.CrashManagerListener;
import net.hockeyapp.android.ExceptionHandler;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import de.timfreiheit.hockey.listeners.WrappedCrashManagerListener;

/**
 * save an exception as a warning.
 * a warning is an exception which to not crash the app but should be notified by someone to fix it
 * <p/>
 * Created by timfreiheit on 08.08.15.
 */
public class WarningExceptionHandler {

    /**
     * @see #saveException(Throwable, String)
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
     * @see #saveException(Throwable, CrashManagerListener, String)
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
    private static void saveExceptionSync(Throwable exception, final CrashManagerListener listener, final String info) {
        ExceptionHandler.saveException(new WarningException(exception), null, new WrappedCrashManagerListener(listener) {
            @Override
            public String getDescription() {
                final String infoText = "Warning Information:\n" + info;
                if (listener == null) {
                    return infoText;
                }
                return infoText + "\n\n" + listener.getDescription();
            }
        });
    }

    /**
     * exception wrapper which helps indicating that this exception was not a crash
     */
    private static class WarningException extends Exception {

        private Throwable throwable;

        WarningException(Throwable throwable) {
            this.throwable = throwable;
        }

        @Override
        public String toString() {
            return "WARNING: " +  throwable.toString();
        }

        @Override
        public void printStackTrace() {
            throwable.printStackTrace();
        }

        @Override
        public void printStackTrace(PrintStream err) {
            try {
                err.write("WARNING: ".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            throwable.printStackTrace(err);
        }

        @Override
        public void printStackTrace(PrintWriter err) {
            err.write("WARNING: ");
            throwable.printStackTrace(err);
        }
    }

}
