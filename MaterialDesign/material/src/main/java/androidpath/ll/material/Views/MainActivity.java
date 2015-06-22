package androidpath.ll.material.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import androidpath.ll.material.Adapter.MyPagerAdapter;
import androidpath.ll.material.R;


public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawer_layout;
    FloatingActionMenu actionMenu;
    FloatingActionButton actionButton;

    Animation animationFadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),
                this));
        setUpTabLayoutIconTop(viewPager);
        setUpFloatActionMenu();

    }

    private void init() {

        animationFadeIn = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        setUpToolBar();
        setUpDrawer();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
    }


    private void setUpFloatActionMenu() {
        ImageView floatButtonImageView = new ImageView(this); // Create an icon
        floatButtonImageView.setImageResource(R.mipmap.ic_launcher);

        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(floatButtonImageView)
                .build();

        ImageView iconSortName = new ImageView(this); // Create an icon
        iconSortName.setImageResource(R.drawable.ic_action_upcoming_orange);

        ImageView iconSortDate = new ImageView(this); // Create an icon
        iconSortDate.setImageResource(R.drawable.ic_action_upcoming_orange);

        ImageView iconSortRating = new ImageView(this); // Create an icon
        iconSortRating.setImageResource(R.drawable.ic_action_upcoming_orange);


        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton btnSortByName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton btnSortByDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton btnSortByRating = itemBuilder.setContentView(iconSortRating).build();

        actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(btnSortByName)
                .addSubActionView(btnSortByDate)
                .addSubActionView(btnSortByRating)
                .attachTo(actionButton)
                .build();
    }


    private void setUpTabLayout(ViewPager viewPager) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_personal);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_action_articles);
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


    private TextView createTabView(String tabText, int iconId) {
        TextView tab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_view, null);
        tab.setText(tabText);
        tab.setCompoundDrawablesWithIntrinsicBounds(0, iconId, 0, 0);
        return tab;
    }

    private void setUpDrawer() {
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        drawer_layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                actionButton.setAlpha(1 - slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                actionButton.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

                animationFadeIn.setDuration((long) drawerView.getScrollBarFadeDuration());
                actionButton.startAnimation(animationFadeIn);
                actionButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    private void setUpToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        mToolbar.setTitle("Moooo");
        setSupportActionBar(mToolbar);
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
            case R.id.action_tab_with_lib: {
                Intent intent = new Intent(this, UsingTabLibActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.action_vector: {
                Intent intent = new Intent(this, VectorDemoActivity.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
