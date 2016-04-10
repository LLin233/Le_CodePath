package ll.morerxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ll.morerxjava.network.NetworkWrapper;
import ll.morerxjava.adapters.UserListAdapter;

/**
 * Created by Le on 2016/4/9.
 */
public class NetworkActivity extends AppCompatActivity{
    @Bind(R.id.network_rv_list)
    RecyclerView mRvList;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(layoutManager);
        UserListAdapter adapter = new UserListAdapter(this::gotoDetailPage);
        NetworkWrapper.getUsersInto(adapter);
        mRvList.setAdapter(adapter);
    }

    public interface UserClickCallback {
        void onItemClicked(String name);
    }

    private void gotoDetailPage(String name) {
        startActivity(NetworkDetailActivity.from(NetworkActivity.this, name));
    }
}
