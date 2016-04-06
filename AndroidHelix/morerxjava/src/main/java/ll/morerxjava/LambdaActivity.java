package ll.morerxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class LambdaActivity extends AppCompatActivity {

    @Bind(R.id.simple_tv_text)
    TextView mTvText;

    final String[] mManyWords = {"Hello", "I", "am", "your", "friend", "Leonard"};
    final List<String> mManyWordList = Arrays.asList(mManyWords);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);

        Observable<String> obShow = Observable.just(sayMyName());

        obShow.observeOn(AndroidSchedulers.mainThread())
                .map(String::toUpperCase).subscribe(mTvText::setText);

        Observable<String> obMap = Observable.from(mManyWords);

        obMap.observeOn(AndroidSchedulers.mainThread())
                .map(String::toUpperCase)
                .subscribe(this::showToast);

        Observable.just(mManyWordList)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .reduce(this::mergeString)
                .subscribe(this::showToast);
    }


    private String sayMyName() {
        return "Hello, Leonard!";
    }

    private void showToast(String s) {
        Toast.makeText(LambdaActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private String mergeString(String s1, String s2) {
        return String.format("%s %s", s1, s2);
    }
}
