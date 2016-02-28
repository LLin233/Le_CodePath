package ll.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
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
        ButterKnife.bind(this);
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

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button)
    public void onTapButtonClicked() {
        mRxBus.send(new TapEvent("Rxjava!"));
    }
}