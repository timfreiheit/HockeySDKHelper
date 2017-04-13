package de.timfreiheit.hockey.listeners;

import net.hockeyapp.android.CrashManagerListener;

/**
 * wraps a {@link CrashManagerListener} by delegating all methods
 */
public class WrappedCrashManagerListener extends CrashManagerListener {

    private CrashManagerListener wrapped;

    public WrappedCrashManagerListener(CrashManagerListener wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean ignoreDefaultHandler() {
        if (wrapped == null) {
            return super.ignoreDefaultHandler();
        }
        return wrapped.ignoreDefaultHandler();
    }

    @Override
    public boolean includeDeviceData() {
        if (wrapped == null) {
            return super.includeDeviceData();
        }
        return wrapped.includeDeviceData();
    }

    @Override
    public boolean includeDeviceIdentifier() {
        if (wrapped == null) {
            return super.includeDeviceIdentifier();
        }
        return wrapped.includeDeviceIdentifier();
    }

    @Override
    public boolean includeThreadDetails() {
        if (wrapped == null) {
            return super.includeThreadDetails();
        }
        return wrapped.includeThreadDetails();
    }

    @Override
    public String getContact() {
        if (wrapped == null) {
            return super.getContact();
        }
        return wrapped.getContact();
    }

    @Override
    public String getDescription() {
        if (wrapped == null) {
            return super.getDescription();
        }
        return wrapped.getDescription();
    }

    @Override
    public String getUserID() {
        if (wrapped == null) {
            return super.getUserID();
        }
        return wrapped.getUserID();
    }

    @Override
    public boolean onCrashesFound() {
        if (wrapped == null) {
            return super.onCrashesFound();
        }
        return wrapped.onCrashesFound();
    }

    @Override
    public boolean shouldAutoUploadCrashes() {
        if (wrapped == null) {
            return super.shouldAutoUploadCrashes();
        }
        return wrapped.shouldAutoUploadCrashes();
    }

    @Override
    public void onNewCrashesFound() {
        if (wrapped != null) {
            wrapped.onNewCrashesFound();
        }
    }

    @Override
    public void onConfirmedCrashesFound() {
        if (wrapped != null) {
            wrapped.onConfirmedCrashesFound();
        }
    }

    @Override
    public void onCrashesSent() {
        if (wrapped != null) {
            wrapped.onCrashesSent();
        }
    }

    @Override
    public void onCrashesNotSent() {
        if (wrapped != null) {
            wrapped.onCrashesNotSent();
        }
    }

    @Override
    public void onUserDeniedCrashes() {
        if (wrapped != null) {
            wrapped.onUserDeniedCrashes();
        }
    }

    @Override
    public int getMaxRetryAttempts() {
        if (wrapped == null) {
            return super.getMaxRetryAttempts();
        }
        return wrapped.getMaxRetryAttempts();
    }

    @Override
    public boolean onHandleAlertView() {
        if (wrapped == null) {
            return super.onHandleAlertView();
        }
        return wrapped.onHandleAlertView();
    }

}
