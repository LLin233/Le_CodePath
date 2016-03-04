package ll.rxjava.di;

import javax.inject.Singleton;

import dagger.Component;


import ll.rxjava.activities.CacheActivity;
import ll.rxjava.activities.NocacheActivity;


/**
 * Created by Le on 2016/3/4.
 */
@Singleton
@Component(modules = ApiModule.class)
public interface ApiComponent {
    void inject(NocacheActivity activity);
    void inject(CacheActivity activity);
}
