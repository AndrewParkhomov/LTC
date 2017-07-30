package parkhomov.andrew.lensthicknesscalculator.activities.main;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT;

/**
 * Created by MyPC on 28.07.2017.
 */

public class MyApp extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if (!CONSTANT.DEBUG)
            Fabric.with(this, new Crashlytics());
    }
}
