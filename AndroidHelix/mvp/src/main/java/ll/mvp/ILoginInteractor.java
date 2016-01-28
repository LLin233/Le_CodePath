package ll.mvp;

/**
 * Created by Le on 2016/1/28.
 */
public interface ILoginInteractor {
    public void login(String account, String pwd, ILoginListener listener);
}
