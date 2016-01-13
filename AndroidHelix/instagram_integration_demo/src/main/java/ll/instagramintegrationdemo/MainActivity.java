package ll.instagramintegrationdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ll.instagramintegrationdemo.Model.CurrentLayout;
import ll.instagramintegrationdemo.Model.LoadPhotoEvent;
import ll.instagramintegrationdemo.Model.Media;

import static ll.instagramintegrationdemo.Model.CurrentLayout.GRID;
import static ll.instagramintegrationdemo.Model.CurrentLayout.LINEAR;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private CurrentLayout mCurrentLayout = GRID;

    public static final String clientId = "ffc2750bb25e40adaee85fcbaafe974a";
    public static final String clientSecret = "0fed2f4d373841b1b22b6e2fd82a53b0";
    public static final String redirectUri = "fotitionchallenge://connect";

    public Instagram mInstagram;


    private static final int GRID_COLUMN = 3;
    private RecyclerView mRecyclerView;
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


        Button loginButton = (Button) findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        //Gridlayout by default
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, GRID_COLUMN));
        mGridAdapter = new GridAdapter(this);
        mRecyclerView.setAdapter(mGridAdapter);


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
    }

    @Override
    protected void onResume() {
        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        super.onResume();
        if (mInstagram == null) {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(redirectUri)) {
                final String[] parts = uri.toString().split("=");
                mInstagram = new Instagram(parts[1]);
                accessToken = parts[1];
                Log.d(TAG, uri.toString());
                mInstagram.getUsersEndpoint().getSelf();
                mInstagram.getUsersEndpoint().getRecentMediaList();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_log_out:
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

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}