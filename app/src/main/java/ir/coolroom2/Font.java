package ir.coolroom2;


import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by jefferson on 3/26/2017.
 */


public class Font extends Application {
    public void onCreate()
    {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()

                .setDefaultFontPath("font/iransans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
