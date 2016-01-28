package ll.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ILoginView{

    private TextView mAccount;
    private TextView mPwd;
    private Button mLogin;
    private ProgressBar mProgress;
    private ILoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new LoginPresenter(this);
        setupViews();
    }

    private void setupViews() {
        mAccount = (TextView) findViewById(R.id.account);
        mPwd = (TextView) findViewById(R.id.pwd);
        mLogin = (Button) findViewById(R.id.login);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.validateAccount(mAccount.getText().toString(), mPwd.getText().toString());
            }
        });

    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void setLoginFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLoginSucess() {
        Toast.makeText(this, "welcome", Toast.LENGTH_SHORT).show();
    }
}
