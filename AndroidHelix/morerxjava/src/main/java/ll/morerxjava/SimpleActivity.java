package ll.morerxjava;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class SimpleActivity extends AppCompatActivity {

    @Bind(R.id.simple_tv_text)
    TextView mTvText;

    // observe
    private Observable.OnSubscribe mObservableAction = new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext(sayMyName()); // action
            subscriber.onCompleted(); //finish
        }
    };

    // attach observed event on view
    private Subscriber<String> mTextSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            mTvText.setText(s);
        }
    };

    private Subscriber<String> mToastSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            Toast.makeText(SimpleActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);

        @SuppressWarnings("unchecked")
        Observable<String> observable = Observable.create(mObservableAction);

        observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(mTextSubscriber);
        observable.subscribe(mToastSubscriber);
    }

    private String sayMyName() {
        return "Hello, Leonard.";
    }
}