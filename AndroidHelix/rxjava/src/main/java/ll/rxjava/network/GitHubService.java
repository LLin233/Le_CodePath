package ll.rxjava.network;

import java.util.ArrayList;

import ll.rxjava.models.Repo;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Le on 2016/3/4.
 */
public interface GitHubService {
    String BASE_URL = "https://api.github.com";

    @GET("/users/{user}/repos")
    Observable<ArrayList<Repo>> getRepos(@Path("user") String user);
}
