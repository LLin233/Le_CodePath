package ll.designedtimer;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, DialogInterface.OnClickListener {
    private final String TAG = getClass().getSimpleName();

    @Bind(R.id.timeText)
    TextView mTimeText;
    @Bind(R.id.toggleButton)
    ToggleButton mToggleButton;


    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Typeface costomFont = Typeface.createFromAsset(getAssets(), "fonts/source_sans_pro_light.ttf");
        mTimeText.setTypeface(costomFont);
        mToggleButton.setOnCheckedChangeListener(this);

        mHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.v(TAG, "called");
                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(runnable, 1000);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            View view = mLayoutInflater.inflate(R.layout.user_input, null);
            EditText inputTime = (EditText) findViewById(R.id.input_time);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Enter The Time")
                    .setView(R.layout.user_input)
                    .setPositiveButton("OK", this)
                    .setNegativeButton("Cancel", this)
                    .show();
        } else {
            Toast.makeText(this, "end", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                Log.v(TAG, "OK");
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                Log.v(TAG, "Cancelled");
                break;

        }
    }
}
