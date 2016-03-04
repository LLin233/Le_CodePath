package ll.rxjava.network;

import java.util.ArrayList;

import ll.rxjava.models.Repo;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by Le on 2016/3/4.
 */
public class GitHubClient {
    private GitHubService mGitHubService;

    public GitHubClient() {
        mGitHubService = new Retrofit.Builder()
                .baseUrl(GitHubService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(GitHubService.class);
    }

    public Observable<ArrayList<Repo>> getRepos(String username) {
        return mGitHubService.getRepos(username);
    }
}
