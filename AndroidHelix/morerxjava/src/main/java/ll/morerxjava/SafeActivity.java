package ll.morerxjava;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Le on 2016/4/16.
 */
public class SafeActivity extends RxAppCompatActivity {
    private static final String TAG = "DEBUG-WCL: " + SafeActivity.class.getSimpleName();

    @Bind(R.id.simple_tv_text)
    TextView mTvText;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);

        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(this::showTime);
    }

    private void showTime(Long time) {
        mTvText.setText(String.valueOf("counter: " + time));
        Log.d(TAG, "counter: " + time);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Pause!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Close!");
    }
}