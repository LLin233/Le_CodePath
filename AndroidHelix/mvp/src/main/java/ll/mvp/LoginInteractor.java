package ll.mvp;

import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by Le on 2016/1/28.
 */
public class LoginInteractor implements ILoginInteractor {

    @Override
    public void login(String account, String pwd, final ILoginListener listener) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
            listener.loginFailed("Please enter account name and password");
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.loginSuccess();
                }
            }, 2000);
        }

    }
}
