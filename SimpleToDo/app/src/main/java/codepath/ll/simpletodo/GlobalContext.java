package codepath.ll.simpletodo;

import android.app.Application;
import android.util.Log;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Le on 2015/6/6.
 */
public class GlobalContext extends Application {

    //singleton
    private static GlobalContext globalContext = null;
    private boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = this;
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("GlobalContext", "onTerminate");
        ActiveAndroid.dispose();
    }

    public static GlobalContext getInstance() {
        return globalContext;
    }
}