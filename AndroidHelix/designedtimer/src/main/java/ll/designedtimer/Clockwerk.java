package ll.designedtimer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

/**
 * Created by Le on 2015/12/10.
 */
public class Clockwerk implements Runnable{
    private long mTimeRemaining;
    private Handler mHandler;

    public Clockwerk(long timeRemaining, Handler handler){
        this.mHandler = handler;
        this.mTimeRemaining = timeRemaining;
    }

    public void start(){
        mHandler.postDelayed(this, 1000);
    }

    @Override
    public void run() {
        Log.d("Timer", "Run was called");
        mTimeRemaining -= 1000;
        if (mTimeRemaining > 0) {
            mHandler.postDelayed(this, 1000);
        }

    }

    public void setTimeRemaining(long timeRemaining) {
        mTimeRemaining = timeRemaining;
    }
}
