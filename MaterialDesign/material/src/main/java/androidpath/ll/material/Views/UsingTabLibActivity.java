package androidpath.ll.material.Views;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import androidpath.ll.material.Adapter.MyPagerAdapter;
import androidpath.ll.material.R;
import androidpath.ll.material.Views.widget.SlidingTabLayout;

public class UsingTabLibActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_tab_lib);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        mToolbar.setTitle("Moooo");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), this));

        SlidingTabLayout slidingTab = (SlidingTabLayout) findViewById(R.id.tabs);
        slidingTab.setDistributeEvenly(true);
        slidingTab.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        slidingTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        slidingTab.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        slidingTab.setViewPager(viewPager);

    }

}
