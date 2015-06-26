package androidpath.ll.material.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import androidpath.ll.material.Adapter.MyPagerAdapter;
import androidpath.ll.material.R;
import androidpath.ll.material.Views.fragments.NavigationDrawerFragment;
import androidpath.ll.material.interfaces.SortListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG_SORT_NAME = "sortName";
    private static final String TAG_SORT_DATE = "sortDate";
    private static final String TAG_SORT_Rating = "sortRating";

    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter mPagerAdapter;
    private DrawerLayout mDrawerLayout;

    FloatingActionMenu actionMenu;
    FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar();
        setUpFloatActionMenu();
        setUpDrawer();
        setUpTab();
        setUpTabLayoutIconTop(viewPager);

    }

    private void setUpTab() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mPagerAdapter);
    }

    private void setUpToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        mToolbar.setTitle("Moooo");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void setUpDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, mDrawerLayout, mToolbar, actionButton, actionMenu);
    }

    private void setUpFloatActionMenu() {
        ImageView floatButtonImageView = new ImageView(this); // Create an icon
        floatButtonImageView.setImageResource(R.drawable.ic_action_new);

        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(floatButtonImageView)
                .setBackgroundDrawable(R.mipmap.button_action_red)
                .build();

        ImageView iconSortName = new ImageView(this); // Create an icon
        iconSortName.setImageResource(R.mipmap.ic_action_alphabets);

        ImageView iconSortDate = new ImageView(this); // Create an icon
        iconSortDate.setImageResource(R.mipmap.ic_action_calendar);

        ImageView iconSortRating = new ImageView(this); // Create an icon
        iconSortRating.setImageResource(R.mipmap.ic_action_important);

        //set up sub buttons
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.float_button_subs));

        SubActionButton btnSortByName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton btnSortByDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton btnSortByRating = itemBuilder.setContentView(iconSortRating).build();

        btnSortByName.setTag(TAG_SORT_NAME);
        btnSortByDate.setTag(TAG_SORT_DATE);
        btnSortByRating.setTag(TAG_SORT_Rating);

        btnSortByName.setOnClickListener(this);
        btnSortByDate.setOnClickListener(this);
        btnSortByRating.setOnClickListener(this);

        actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(btnSortByName)
                .addSubActionView(btnSortByDate)
                .addSubActionView(btnSortByRating)
                .attachTo(actionButton)
                .build();
    }

    private void setUpTabLayoutIconTop(ViewPager viewPager) {

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setCustomView(getTabView("TAB1", R.drawable.ic_action_home));
        tabLayout.getTabAt(1).setCustomView(getTabView("TAB2", R.drawable.ic_action_personal));
        tabLayout.getTabAt(2).setCustomView(getTabView("TAB3", R.drawable.ic_action_articles));
    }

    private View getTabView(String tabText, int iconId) {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        ImageView icon = ((ImageView) view.findViewById(R.id.tabIcon));
        icon.setImageResource(iconId);
        TextView title = ((TextView) view.findViewById(R.id.tabText));
        title.setText(tabText);
        return view;
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings: {
                return true;
            }

            case R.id.action_vector: {
                Intent intent = new Intent(this, VectorDemoActivity.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //handle float action button onClickEvent
    @Override
    public void onClick(View v) {
        Fragment fragment = (Fragment) mPagerAdapter.instantiateItem(viewPager, viewPager.getCurrentItem());
        if (fragment instanceof SortListener) {
            switch (v.getTag().toString()) {
                //TODO use eventbus to handle event
                case TAG_SORT_NAME:
                    ((SortListener) fragment).onSortByName();
                    break;
                case TAG_SORT_DATE:
                    ((SortListener) fragment).onSortByDate();
                    break;
                case TAG_SORT_Rating:
                    ((SortListener) fragment).onSortByRating();
                    break;
            }
        }


    }
}
