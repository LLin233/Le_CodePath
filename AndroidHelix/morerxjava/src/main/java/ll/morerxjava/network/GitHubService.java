package ll.morerxjava.network;

import ll.morerxjava.adapters.RepoListAdapter;
import ll.morerxjava.adapters.UserListAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Le on 2016/4/9.
 */
public interface GitHubService {
    String ENDPOINT = "https://api.github.com";

    @GET("/users/{user}")
    Observable<UserListAdapter.GitHubUser> getUserData(@Path("user") String user);

    @GET("/users/{user}/repos")
    Observable<RepoListAdapter.GitHubRepo[]> getRepoData(@Path("user") String user);
}