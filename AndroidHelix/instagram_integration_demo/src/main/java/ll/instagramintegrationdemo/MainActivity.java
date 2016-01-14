package ll.instagramintegrationdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ll.instagramintegrationdemo.Model.CurrentLayout;
import ll.instagramintegrationdemo.Model.LoadPhotoEvent;
import ll.instagramintegrationdemo.Model.Media;
import ll.instagramintegrationdemo.Utils.Constants;
import ll.instagramintegrationdemo.Utils.Instagram;
import ll.instagramintegrationdemo.adapter.GridAdapter;
import ll.instagramintegrationdemo.adapter.ListAdapter;

import static ll.instagramintegrationdemo.Model.CurrentLayout.GRID;
import static ll.instagramintegrationdemo.Model.CurrentLayout.LINEAR;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private final String TAG = getClass().getSimpleName();
    private CurrentLayout mCurrentLayout = GRID;

    public static final String clientId = "ffc2750bb25e40adaee85fcbaafe974a";
    public static final String clientSecret = "0fed2f4d373841b1b22b6e2fd82a53b0";
    public static final String redirectUri = "fotitionchallenge://connect";

    public Instagram mInstagram;
    private static SharedPreferences mSharedPreferences;

    private static final int GRID_COLUMN = 3;

    private RecyclerView mRecyclerView;
    private Button mLoginButton;
    private SwipeRefreshLayout mSwipeLayout;
    private GridAdapter mGridAdapter;
    private ListAdapter mListAdapter;
    private List<Media> mDatas = new ArrayList<>();
    private String accessToken;

//    public static final String clientId = "e9989d8e76b343d282cbe13c6fc9b1ad";
//    public static final String clientSecret = "1e830a4fbaeb4dd4a41ff9c97a8673ef";
//    public static final String redirectUri = "fotitionchallenge://";

    // https://api.instagram.com/oauth/authorize/?client_id=e9989d8e76b343d282cbe13c6fc9b1ad&redirect_uri=fotitionchallenge://&response_type=code

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mLoginButton = (Button) findViewById(R.id.loginbutton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithOauth();
            }
        });


        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeLayout.setOnRefreshListener(this);

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        //Gridlayout by default
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, GRID_COLUMN));
        mGridAdapter = new GridAdapter(this);
        mRecyclerView.setAdapter(mGridAdapter);


    }

    private void loginWithOauth() {
        try {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(Instagram.requestOAuthUrl(clientId, redirectUri, null)));
            Log.d("OAUTH URL", Uri.parse(Instagram.requestOAuthUrl(clientId, redirectUri, null)).toString());
            startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    void onEvent(LoadPhotoEvent event) {
        mDatas = event.getMediaList();
        switch (mCurrentLayout) {
            case GRID:
                mGridAdapter.updateItems(mDatas.size());
                mGridAdapter.setDataSets(mDatas);
                break;
            case LINEAR:
                mListAdapter.updateItems(mDatas.size());
                mListAdapter.setDataSets(mDatas);
        }
        if (mSwipeLayout.isRefreshing()) {
            mSwipeLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onResume() {
        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        super.onResume();
        if (!isInstagramLoggedIn()) {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(redirectUri)) {
                final String[] parts = uri.toString().split("=");
                accessToken = parts[1];
                mInstagram = new Instagram(accessToken);
                mLoginButton.setVisibility(View.GONE);
                saveAccessToken(accessToken);
                mInstagram.getUsersEndpoint().requestSelf();
                mInstagram.getUsersEndpoint().requestRecentMediaList();
            }
        } else {
            mLoginButton.setVisibility(View.GONE);
            accessToken = mSharedPreferences.getString(Constants.PREF_KEY_OAUTH_TOKEN, "");
            Log.d(TAG, accessToken);
            mInstagram = new Instagram(accessToken);
            mInstagram.getUsersEndpoint().requestRecentMediaList();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem loginItem = menu.findItem(R.id.action_log_in);
        if (isInstagramLoggedIn()) {
            loginItem.setTitle("Log out");
        } else {
            loginItem.setTitle("Log in");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_log_in:
                if (isInstagramLoggedIn()) {
                    logoutFromInstagram();
                    item.setTitle("Log in");
                } else {
                    loginWithOauth();
                    item.setTitle("Log out");
                }
                return true;
            case R.id.action_layout_toggle:
                if (mCurrentLayout == GRID) {
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    mListAdapter = new ListAdapter(this);
                    mRecyclerView.setAdapter(mListAdapter);
                    mListAdapter.updateItems(mDatas.size());
                    mListAdapter.setDataSets(mDatas);
                    mCurrentLayout = LINEAR;
                    item.setTitle("Grid");
                } else if (mCurrentLayout == LINEAR) {
                    mRecyclerView.setLayoutManager(new GridLayoutManager(this, GRID_COLUMN));
                    mGridAdapter = new GridAdapter(this);
                    mRecyclerView.setAdapter(mGridAdapter);
                    mGridAdapter.updateItems(mDatas.size());
                    mGridAdapter.setDataSets(mDatas);
                    mCurrentLayout = GRID;
                    item.setTitle("List");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveAccessToken(String accessToken) {
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putString(Constants.PREF_KEY_OAUTH_TOKEN, accessToken);
        e.putBoolean(Constants.PREF_KEY_INSTAGRAM_LOGIN, true);
        e.apply(); // save changes
    }


    private void logoutFromInstagram() {
        // Clear the shared preferences
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.remove(Constants.PREF_KEY_OAUTH_TOKEN);
        e.remove(Constants.PREF_KEY_INSTAGRAM_LOGIN);
        e.apply();

        // After this take the appropriate action
        // I am showing the hiding/showing buttons again
        mLoginButton.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    private boolean isInstagramLoggedIn() {
        return mSharedPreferences.getBoolean(Constants.PREF_KEY_INSTAGRAM_LOGIN, false);
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        if (isInstagramLoggedIn()) {
            mInstagram.getUsersEndpoint().requestRecentMediaList();
        } else {
            Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT);
        }
    }
}