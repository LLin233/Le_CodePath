package ll.designedtimer;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView mTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTimeText = (TextView) findViewById(R.id.timeText);
        Typeface costomFont = Typeface.createFromAsset(getAssets(), "fonts/source_sans_pro_light.ttf");
        mTimeText.setTypeface(costomFont);
    }
}
