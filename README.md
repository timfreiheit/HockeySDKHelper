This library helps you to send the system log as an crash description to hockey app.

## Requirements
At the moment this library required devices with API 15

## Integration Into Your Own App

### send the log

just add the LogCrashManagerListener to the Hockey CrashManager


```java
CrashManager.register(this,"some hockey id", new LogCrashManagerListener(getApplicationContext(), Log.DEBUG));
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

    HockeyLifecycleConfig lifecycleConfig = new HockeyLifecycleConfig.Builder(this)
        // your hockey app id
        .hockeyAppId("12345678901234567890123456789012")

        //enable updates only on debug and disable them in release
        .updateEnabled(BuildConfig.DEBUG)

        // when you want to restrict the checks on specific activities
        .activityWhereToCheckForUpdates(MainActivity.class)

        .sendLogAsCrashDescription(BuildConfig.DEBUG, Log.DEBUG)

        .crashReportEnabled(true) //enable crash reporting
        // when you want to restrict the checks on specific activities
        .activityWhereToCheckForCrashes(MainActivity.class)

        .trackingEnabled(true) //enable usage tracking
        .build();
    HockeyLifecycleHelper.init(this,lifecycleConfig);

    //....

}

```

