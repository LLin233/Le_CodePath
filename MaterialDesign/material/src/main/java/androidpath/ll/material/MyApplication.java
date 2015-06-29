package androidpath.ll.material;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Le on 2015/6/15.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        mInstance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
