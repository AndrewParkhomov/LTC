package parkhomov.andrew.lensthicknesscalculator.activities.main;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.BuildConfig;
import io.fabric.sdk.android.Fabric;
import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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
        if (!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("raleway.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
