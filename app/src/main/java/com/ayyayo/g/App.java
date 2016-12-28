package com.ayyayo.g;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.View;

import com.ayyayo.g.common.SharedPreferencesUtility;
import com.crashlytics.android.Crashlytics;
import com.ayyayo.g.dagger.component.AppComponent;
import com.ayyayo.g.dagger.component.InternetComponent;
import com.ayyayo.g.dagger.component.StorageComponent;
import com.ayyayo.g.dagger.component.DaggerAppComponent;
import com.ayyayo.g.dagger.component.DaggerInternetComponent;
import com.ayyayo.g.dagger.component.DaggerStorageComponent;
import com.ayyayo.g.dagger.module.AppModule;

import javax.inject.Inject;

import io.branch.referral.Branch;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class App extends Application {



    private static InternetComponent internetComponent;
    private static StorageComponent storageComponent;

    public static InternetComponent getInternetComponent() {
        return internetComponent;
    }

    public static StorageComponent getStorageComponent() {
        return storageComponent;
    }

    @Override
    public void onCreate () {
        super.onCreate();
        // Branch.io
        Branch.getAutoInstance(this);

        //setup Dagger
        setupDagger();

        //Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Fabric.with(this, new Crashlytics());
            Timber.plant(new CrashlyticsTree());
        }

        // TODO: Move this to where you establish a user session
        logUser();

        Log.e("DEBUG", String.valueOf(BuildConfig.DEBUG));
    }

    private void setupDagger () {

        AppModule appModule = new AppModule(this);

        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .build();

        appComponent.inject(this);

        storageComponent = DaggerStorageComponent.builder()
                .appComponent(appComponent)
                .build();

        internetComponent = DaggerInternetComponent.builder()
                .appComponent(appComponent)
                .build();
    }

    private void logUser () {

        // TODO: Use the current user's information
        // You can call any combination of these three methods
        if (!BuildConfig.DEBUG) {
            Crashlytics.setUserIdentifier("12345");
            Crashlytics.setUserEmail("user@fabric.io");
            Crashlytics.setUserName("Test User");
        }
    }

    @Override
    protected void attachBaseContext (Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public class CrashlyticsTree extends Timber.Tree {
        private static final String CRASHLYTICS_KEY_PRIORITY = "priority";
        private static final String CRASHLYTICS_KEY_TAG = "tag";
        private static final String CRASHLYTICS_KEY_MESSAGE = "message";

        @Override
        protected void log (int priority, @Nullable String tag,
                            @Nullable String message, @Nullable Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                return;
            }

            Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority);
            Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag);
            Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message);

            if (t == null) {
                Crashlytics.logException(new Exception(message));
            } else {
                Crashlytics.logException(t);
            }
        }
    }
}
