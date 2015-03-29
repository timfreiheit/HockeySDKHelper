This library helps you to send the system log as an crash description to hockey app.

## Requirements
At the moment this library required devices with API 15

## Integration Into Your Own App

### init recording

Create an custom Application and override the onCreate-Method

```java

@Override
public void onCreate() {
    super.onCreate();

    LogRecordingConfig config = new LogRecordingConfig.Builder()
            .setLogLevel(Log.VERBOSE)
            .setMaxLines(1000)
            .clearLogBeforeRecording(false)
            .build();
    LogRecordingManager.init(this, config);

}

```

### send the log

just add the LogCrashManagerListener to the Hockey CrashManager


```java
CrashManager.register(this,"some hockey id", new LogCrashManagerListener());
```

The LogCrashManager also overrides shouldAutoUploadCrashes
```java
@Override
public boolean shouldAutoUploadCrashes() {
    return true;
}
```
This is not a requirement and you can override it if you want
