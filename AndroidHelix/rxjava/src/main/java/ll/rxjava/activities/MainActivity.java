package ll.rxjava.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.OnClick;
import ll.rxjava.R;
import ll.rxjava.RxBus;
import ll.rxjava.models.TapEvent;
import rx.Observable;
import rx.functions.Action1;

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
        mRxBus = this.getRxBusSingleton();

        Observable.just("Hello, world!", "Leonard")
                .map(s -> s.hashCode())
                .map(i -> i.toString())
                .subscribe(s -> System.out.println(s));

        mRxBus.toObserverable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event instanceof TapEvent) {
                            showToast(((TapEvent) event).getMsg());
                        }
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
        mRxBus.send(new TapEvent("Rxjava!"));
    }
}