package androidpath.ll.material.Views;

import android.annotation.SuppressLint;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.telly.mrvector.MrVector;

import androidpath.ll.material.R;
import androidpath.ll.material.Utils.SDKVersionUtil;

public class VectorDemoActivity extends AppCompatActivity {
    private ImageView mImageView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_demo);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setUp svg
        mImageView = (ImageView) findViewById(R.id.vectorImage);
        Drawable drawable = null;

        if (SDKVersionUtil.isLolipopOrGreater()) {
            drawable = MrVector.inflate(getResources(), R.drawable.animator_vector_clock);
        } else {
            drawable = MrVector.inflate(getResources(), R.drawable.vector_clock);
        }

        if (SDKVersionUtil.isJellyBeanOrGreater()) {
            mImageView.setBackground(drawable);
        } else {
            mImageView.setBackgroundDrawable(drawable);
        }

        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

}
