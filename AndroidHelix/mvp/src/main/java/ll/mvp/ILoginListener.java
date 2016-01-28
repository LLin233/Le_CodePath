package ll.mvp;

/**
 * Created by Le on 2016/1/28.
 */
public interface ILoginListener {
    public void loginFailed(String msg);

    public void loginSuccess();
}
