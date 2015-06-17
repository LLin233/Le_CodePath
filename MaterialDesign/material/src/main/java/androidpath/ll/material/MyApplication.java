package androidpath.ll.material;

import android.app.Application;
import android.content.Context;

/**
 * Created by Le on 2015/6/15.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
