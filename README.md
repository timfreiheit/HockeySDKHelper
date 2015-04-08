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

    //....

    LogRecordingConfig config = new LogRecordingConfig.Builder()
            .setLogLevel(Log.VERBOSE)
            .setMaxLines(1000)
            .clearLogBeforeRecording(false)
            .build();
    LogRecordingManager.init(this, config);

    //....
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


## Small Helper

Since Api 14 it is possible to use the Application.ActivityLifecycleCallbacks to react on all activity lifecycle events.
This Library contains an Helper to use this callbacks to check for updates, check for crashes and track the usage time
and avoid adding code to your Activities to use Hockey.

In your Application.onCreate

```java

@Override
public void onCreate() {
    super.onCreate();

    //....
    
    HockeyLifecycleConfig lifecycleConfig = new HockeyLifecycleConfig.Builder()
            .hockeyAppId("12345678901234567890123456789012") // your hockey app id
            .updateEnabled(BuildConfig.DEBUG) //enable updates only on debug and disable them in release
                // when you want to restrict the checks on specific activities
            .activityWhereToCheckForUpdates(MainActivity.class)
            .updateManagerListener(...) //default is null

            .crashReportEnabled(true) //enable crash reporting
                // when you want to restrict the checks on specific activities
            .activityWhereToCheckForCrashes(MainActivity.class)
            .crashManagerListener(...) // default is the LogCrashManagerListener

            .trackingEnabled(true) //enable usage tracking
            .build();
    HockeyLifecycleHelper.init(this,lifecycleConfig);

    //....

}

```

