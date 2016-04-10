package ll.morerxjava.network;

import ll.morerxjava.adapters.RepoListAdapter;
import ll.morerxjava.adapters.UserListAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Le on 2016/4/9.
 */
public class NetworkWrapper {
    private static final String[] mFamousUsers =
            {"LLin233", "JakeWharton", "7heaven", "devunwired", "JorgeCastilloPrz"};

    // 获取用户信息
    public static void getUsersInto(final UserListAdapter adapter) {
        GitHubService gitHubService =
                ServiceFactory.createServiceFrom(GitHubService.class, GitHubService.ENDPOINT);

        Observable.from(mFamousUsers)
                .flatMap(gitHubService::getUserData)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::addUser);
    }

    // 获取库信息
    public static void getReposInfo(final String username, final RepoListAdapter adapter) {
        GitHubService gitHubService =
                ServiceFactory.createServiceFrom(GitHubService.class, GitHubService.ENDPOINT);

        gitHubService.getRepoData(username)
                .flatMap(Observable::from)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::addRepo);
    }
}