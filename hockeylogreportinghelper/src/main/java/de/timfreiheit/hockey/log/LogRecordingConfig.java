package de.timfreiheit.hockey.log;

import android.util.Log;

import java.io.Serializable;

/**
 * represents the available configurations to record the log and report it to Hockey when using {@link LogCrashManagerListener}
 * Created by timfreiheit on 22.03.15.
 */
public class LogRecordingConfig implements Serializable {

    public boolean clearBeforeStartRecording = false;
    public int logLevel = Log.VERBOSE;
    public int maxLinesPerLog = 1000;

    /**
     * provides an builder for {@link LogRecordingConfig}
     */
    public static class Builder {

        private boolean clearBeforeStartRecording = false;
        public int logLevel = Log.VERBOSE;
        public int maxLinesPerLog = 1000;

        /**
         * Builds configured {@link LogRecordingConfig} object
         */
        public LogRecordingConfig build() {
            LogRecordingConfig config = new LogRecordingConfig();
            config.clearBeforeStartRecording = clearBeforeStartRecording;
            config.logLevel = logLevel;
            config.maxLinesPerLog = maxLinesPerLog;
            return config;
        }

        /**
         * sets the minimum log level
         * default value is {@link Log#VERBOSE}
         * <p/>
         * every log value >= logLevel will be shown in the result log
         *
         * @param logLevel should be any of
         *                 [{@link Log#VERBOSE},
         *                 {@link Log#DEBUG},
         *                 {@link Log#INFO},
         *                 {@link Log#WARN},
         *                 {@link Log#ERROR},
         *                 {@link Log#ASSERT}]
         */
        public Builder setLogLevel(int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        /**
         * sets the maximum lines the manager will send to Hockey
         * default value is 1000
         *
         * @param maxLines > 0 && < 100000
         */
        public Builder setMaxLines(int maxLines) {
            this.maxLinesPerLog = Math.max(maxLines, 1);
            this.maxLinesPerLog = Math.min(maxLines, 100000);
            return this;
        }

        /**
         * clears the every log from last app launch
         * maybe cutting the first logs of the current launch
         * due to the small starting delay of the log recording service
         * <p/>
         * default value is false
         */
        public Builder clearLogBeforeRecording(boolean clear) {
            this.clearBeforeStartRecording = clear;
            return this;
        }

    }
}
