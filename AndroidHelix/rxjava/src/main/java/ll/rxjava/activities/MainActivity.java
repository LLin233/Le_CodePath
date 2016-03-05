package ll.rxjava.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ll.rxjava.R;
import ll.rxjava.RxBus;
import ll.rxjava.models.TapEvent;
import rx.Observable;

public class MainActivity extends AppCompatActivity {
    private RxBus mRxBus = null;

    // This is better done with a DI Library like Dagger
    public RxBus getRxBusSingleton() {
        if (mRxBus == null) {
            mRxBus = new RxBus();
        }

        return mRxBus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRxBus = this.getRxBusSingleton();

        Observable.just("Hello, world!", "Leonard")
                .map(s -> s.hashCode())
                .map(i -> i.toString())
                .subscribe(s -> System.out.println(s));

        mRxBus.toObserverable()
                .subscribe(event -> {
                    if (event instanceof TapEvent) {
                        showToast(((TapEvent) event).getMsg());
                    }
                });
    }

    @OnClick(R.id.main_b_nocache)
    public void gotoNoCache(View view) {
        startActivity(new Intent(this, NocacheActivity.class));
    }

    @OnClick(R.id.main_b_cache)
    public void gotoCache(View view) {
        startActivity(new Intent(this, CacheActivity.class));
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button)
    public void onTapButtonClicked() {
        Log.i("Main", "clicked");
        mRxBus.send(new TapEvent("Rxjava!"));
    }
}