package ll.rxjava.models;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Le on 2016/3/4.
 */
public class ObservableRepoDb {
    private PublishSubject<ArrayList<Repo>> mPublishSubject;
    private RepoDbHelper mDbHelper;

    public ObservableRepoDb(Context context) {
        mDbHelper = new RepoDbHelper(context);
        mPublishSubject = PublishSubject.create();
    }

    public Observable<ArrayList<Repo>> getObservable() {
        Observable<ArrayList<Repo>> firstObservable = Observable.fromCallable(this::getRepoList);
        return firstObservable.concatWith(mPublishSubject);
    }

    private ArrayList<Repo> getRepoList() {
        mDbHelper.openForRead();
        ArrayList<Repo> repos = new ArrayList<>();
        Cursor c = mDbHelper.getAllRepo();
        if (!c.moveToFirst()) {
            return repos; // return empty list
        }

        do {
            repos.add(new Repo(
                    c.getString(RepoDbHelper.REPO_ID_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_NAME_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_DESCRIPTION_COLUMN_POSITION),
                    new Repo.Owner(c.getString(RepoDbHelper.REPO_OWNER_COLUMN_POSITION), "", "", "")));
        } while (c.moveToNext());
        c.close();
        mDbHelper.close();
        return repos;
    }

    public void insertRepoList(ArrayList<Repo> repos) {
        mDbHelper.open();
        mDbHelper.removeAllRepo();
        for (Repo repo : repos) {
            mDbHelper.addRepo(
                    repo.getId(),
                    repo.getName(),
                    repo.getDescription(),
                    repo.getOwner().getLogin()
            );
        }
        mDbHelper.close();
        mPublishSubject.onNext(repos); // update data
    }
}
