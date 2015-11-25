package de.timfreiheit.hockey.listeners;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;

import net.hockeyapp.android.CrashManagerListener;

/**
 * Basic CrashManagerListener to enable {@link CrashManagerListener#shouldAutoUploadCrashes()}
 * Created by timfreiheit on 08.08.15.
 */
public class BaseCrashManagerListener extends CrashManagerListener{

    private Context mContext;

    public BaseCrashManagerListener(){

    }

    /**
     * the context is needed to read the ANDROID_ID as USER_ID
     * @param context Context
     */
    public BaseCrashManagerListener(Context context){
        if (context != null && !(context instanceof Application)) {
            context = context.getApplicationContext();
        }
        mContext = context;
    }

    public Context getContext(){
        return mContext;
    }

    @Override
    public boolean shouldAutoUploadCrashes() {
        return true;
    }

    public String getUserID() {
        if (mContext == null) {
            return super.getUserID();
        }

        try {
            return Settings.Secure.getString(mContext.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }catch (Exception e){
            // just to make sure
            return super.getUserID();
        }

    }
}
