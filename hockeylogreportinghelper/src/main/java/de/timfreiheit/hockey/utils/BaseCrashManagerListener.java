package de.timfreiheit.hockey.utils;

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

    public BaseCrashManagerListener(Context context){
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
