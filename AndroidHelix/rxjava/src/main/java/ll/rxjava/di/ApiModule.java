package ll.rxjava.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ll.rxjava.network.GitHubClient;
import ll.rxjava.models.ObservableRepoDb;


/**
 * Created by Le on 2016/3/4.
 */
@Module
public class ApiModule {
    private Application mApplication;

    public ApiModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    GitHubClient provideGitHubClient() {
        return new GitHubClient();
    }

    @Provides
    ObservableRepoDb provideObservableRepoDb() {
        return new ObservableRepoDb(mApplication);
    }
}