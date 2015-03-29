package freiheit.hockey.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * read SystemLog using logcat
 * @see {http://developer.android.com/tools/debugging/debugging-log.html}
 */
class LogcatReader {

	private Process logcatProcess;
	private BufferedReader bufferedReader;
	
	public LogcatReader(LogRecordingConfig config) throws IOException, InterruptedException {

		if(config != null && config.clearBeforeStartRecording){
			clearLog();
		}

		init();
	}

	private void init() throws IOException {
		// use the "time" log so we can see what time the logs were logged at
		logcatProcess = getLogcatProcess();
		
		bufferedReader = new BufferedReader(new InputStreamReader(logcatProcess
				.getInputStream()), 8192);
	}

	public void clearLog() throws IOException, InterruptedException {
		Process clearProcess = Runtime.getRuntime().exec(new String[]{"logcat", "-c"});
		clearProcess.waitFor();
	}

	public void killProcess() {
		if (logcatProcess != null) {
			logcatProcess.destroy();
		}
	}

	public String readLine() throws IOException {
		return bufferedReader.readLine();
	}

	public static Process getLogcatProcess() throws IOException {
		return Runtime.getRuntime().exec(new String[]{"logcat", "-v", "time"});
	}
}
