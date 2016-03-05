package ll.rxjava.activities;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ll.rxjava.R;
import ll.rxjava.RcApplication;
import ll.rxjava.adapters.ListAdapter;
import ll.rxjava.models.ObservableRepoDb;
import ll.rxjava.models.Repo;
import ll.rxjava.network.GitHubClient;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Le on 2016/2/28.
 */
public class CacheActivity extends AppCompatActivity {

    @Bind(R.id.cache_rv_list)
    RecyclerView mRvList;
    @Bind(R.id.cache_srl_swipe)
    SwipeRefreshLayout mSrlSwipe;

    @Inject
    Application mApplication;
    @Inject
    ObservableRepoDb mRepoDb;
    @Inject
    GitHubClient mGitHubClient;

    private ListAdapter mListAdapter; //

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        ButterKnife.bind(this);

        ((RcApplication) getApplication()).getApiComponent().inject(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mApplication);
        mRvList.setLayoutManager(layoutManager);

        mListAdapter = new ListAdapter();
        mRvList.setAdapter(mListAdapter);

        mSrlSwipe.setOnRefreshListener(this::fetchUpdates);
    }

    @Override protected void onResume() {
        super.onResume();
        mRepoDb.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setData);

        fetchUpdates();
        Toast.makeText(mApplication, "updating", Toast.LENGTH_SHORT).show();
    }

    private void setData(ArrayList<Repo> repos) {
        mListAdapter.setRepos(repos);
        Toast.makeText(mApplication, "Data loading completed", Toast.LENGTH_SHORT).show();
    }

    private void fetchUpdates() {
        mGitHubClient.getRepos("LLin233")
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mRepoDb::insertRepoList, this::fetchError, this::fetchComplete);
    }

    private void fetchError(Throwable throwable) {
        mSrlSwipe.setRefreshing(false);
    }

    private void fetchComplete() {
        mSrlSwipe.setRefreshing(false);
    }
}
