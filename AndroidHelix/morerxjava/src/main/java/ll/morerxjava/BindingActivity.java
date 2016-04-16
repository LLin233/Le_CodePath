package ll.morerxjava;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.support.design.widget.RxSnackbar;
import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BindingActivity extends AppCompatActivity {

    @Bind(R.id.rxbinding_t_toolbar)
    Toolbar mTToolbar;
    @Bind(R.id.rxbinding_et_usual_approach)
    EditText mEtUsualApproach;
    @Bind(R.id.rxbinding_et_reactive_approach) EditText mEtReactiveApproach;
    @Bind(R.id.rxbinding_tv_show)
    TextView mTvShow;
    @Bind(R.id.rxbinding_fab_fab)
    FloatingActionButton mFabFab;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        ButterKnife.bind(this);

        initToolbar();
        initFabButton();
        initEditText();
    }

    private void initToolbar() {
        setSupportActionBar(mTToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RxToolbar.itemClicks(mTToolbar).subscribe(this::onToolbarItemClicked);

        RxToolbar.navigationClicks(mTToolbar).subscribe(this::onToolbarNavigationClicked);
    }

    private void onToolbarItemClicked(MenuItem menuItem) {
        String m = "Clicked\"" + menuItem.getTitle() + "\"";
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }

    private void onToolbarNavigationClicked(Void v) {
        Toast.makeText(this, "NavigationClicked", Toast.LENGTH_SHORT).show();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rxbinding, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initFabButton() {
        RxView.clicks(mFabFab).subscribe(this::onFabClicked);
    }

    private void onFabClicked(Void v) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Snackbar", Snackbar.LENGTH_SHORT);
        snackbar.show();
        RxSnackbar.dismisses(snackbar).subscribe(this::onSnackbarDismissed);
    }

    private void onSnackbarDismissed(int event) {
        String text = "Snackbar cancel:" + event;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void initEditText() {
        mEtUsualApproach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvShow.setText(s);
            }

            @Override public void afterTextChanged(Editable s) {

            }

        });

        RxTextView.textChanges(mEtReactiveApproach).subscribe(mTvShow::setText);
    }
}