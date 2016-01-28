package ll.mvp;

/**
 * Created by Le on 2016/1/28.
 */
public class LoginPresenter implements ILoginPresenter, ILoginListener {
    private ILoginView mLoginView;
    private ILoginInteractor mLoginInteractor;

    public LoginPresenter(ILoginView loginview) {
        mLoginView = loginview;
        mLoginInteractor = new LoginInteractor();
    }

    @Override
    public void validateAccount(String account, String pwd) {
        mLoginView.showProgress();
        mLoginInteractor.login(account, pwd, this);
    }

    @Override
    public void loginFailed(String msg) {
        mLoginView.hideProgress();
        mLoginView.setLoginFailed(msg);
    }

    @Override
    public void loginSuccess() {
        mLoginView.hideProgress();
        mLoginView.setLoginSucess();
    }

}
