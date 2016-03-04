package ll.rxjava;

import android.app.Application;

import ll.rxjava.di.ApiComponent;
import ll.rxjava.di.ApiModule;
import ll.rxjava.di.DaggerApiComponent;

/**
 * Created by Le on 2016/3/4.
 */
public class RcApplication extends Application {
    private ApiComponent mApiComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mApiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule(this)).build();
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }
}
