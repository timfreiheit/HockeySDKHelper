package de.timfreiheit.hockey.listeners;

import android.content.Context;

import net.hockeyapp.android.CrashManagerListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Combines the description of different CrashManagerListeners
 *
 * Created by Tim on 25.11.2015.
 */
public class CombinedDescriptionListener extends BaseCrashManagerListener {

    public CombinedDescriptionListener(){

    }

    /**
     * the context is needed to read the ANDROID_ID as USER_ID
     * @param context Context
     */
    public CombinedDescriptionListener(Context context){
        super(context);
    }

    protected Map<String, CrashManagerListener> mParts = new HashMap<>();

    public CombinedDescriptionListener addPart(String name, CrashManagerListener listener){
        mParts.put(name, listener);
        return this;
    }

    @Override
    public String getDescription() {
        StringBuilder b = new StringBuilder(mParts.size() * 100);
        for (String key : mParts.keySet()) {
            b.append(key);
            b.append(":\n");
            try {
                String description = mParts.get(key).getDescription();
                b.append(description);
            } catch (Exception e){
                b.append(e.getMessage());
            }
            b.append("\n");
        }
        return b.toString();
    }
}
