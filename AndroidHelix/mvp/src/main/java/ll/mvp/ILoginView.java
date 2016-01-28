package ll.mvp;

/**
 * Created by Le on 2016/1/28.
 */
public interface ILoginView {
    void showProgress();

    void hideProgress();

    void setLoginFailed(String msg);

    void setLoginSucess();

}
