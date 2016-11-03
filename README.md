This library helps you to send the system log as an crash description to hockey app.

## Requirements
This library required devices with API 15

## Integration Into Your Own App

### send the log

just add the LogCrashManagerListener to the Hockey CrashManager


```java
CrashManager.register(this,"some hockey id", new LogCrashManagerListener(getApplicationContext(), Log.DEBUG));
```

The LogCrashManagerListener also overrides shouldAutoUploadCrashes
```java
@Override
public boolean shouldAutoUploadCrashes() {
    return true;
}
```
This is not a requirement and you can override it if you want

## Send Exceptions as warning

The WarningExceptionHandler provides a way to save Exceptions as warnings.

```java

WarningExceptionHandler.saveException(e, "more information about this warning");

```

Instead of the default:

```java

ExceptionHandler.saveException(e, listener);

```

All WarningExceptionHandler.saveException calls will run in an seperate Thread to avoid blocking the app.
The difference is that the WarningExceptionHandler will use all settings defined in your HockeyLifecycleHelper.
Warnings are shown at the HockeyApp-Website as:

WARNING: java.lang.RuntimeException: some reason
instead of
WARNING: java.lang.RuntimeException: some reason

This make it easier to distinguish between the two.

## HockeyLifecycleHelper

This Library contains an Helper to use this callbacks to check for updates, check for crashes and track the usage time
and avoid adding code to your Activities to use Hockey by using Application.ActivityLifecycleCallbacks.
     
The CombinedDescriptionListener can be used to collect and different information of the app.
    
In your Application.onCreate

```java

@Override
public void onCreate() {
    super.onCreate();

    //....

    CombinedDescriptionListener crashManagerListener = new CombinedDescriptionListener(this)
                    .addPart("Memory", new MemoryInfoCrashManagerListener())
                    .addPart("Log", new LogCrashManagerListener());
    
    HockeyLifecycleConfig lifecycleConfig = new HockeyLifecycleConfig.Builder()
            // your hockey app id
            .hockeyAppId("12345678901234567890123456789012")
            //enable updates only on debug and disable them in release
            .updateEnabled(BuildConfig.DEBUG)

            // when you want to restrict the checks on specific activities
            .activityWhereToCheckForUpdates(MainActivity.class)
    
            .crashManagerListener(crashManagerListener)
            .crashReportEnabled(true) //enable crash reporting
            // when you want to restrict the checks on specific activities
            .activityWhereToCheckForCrashes(MainActivity.class)
            //enable usage tracking and metrics
            .metricsEnabled(true)
            .trackingEnabled(true)
            .build();
    HockeyLifecycleHelper.init(this,lifecycleConfig);

    //....

}

```

## Install

```groovy

repositories {    
    // ...    
    maven { url "https://jitpack.io" }   
}   

dependencies {    
    compile 'com.github.timfreiheit:HockeyLogReportingHelper:vXXX'
}   

```