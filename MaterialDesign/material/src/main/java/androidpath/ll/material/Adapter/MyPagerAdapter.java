package androidpath.ll.material.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import androidpath.ll.material.ui.MyFragment;

/**
 * Created by Le on 2015/6/14.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    String[] tabs = {"TAB1", "TAB2", "TAB3"};

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        MyFragment myFragment = MyFragment.getInstence(position);
        return myFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}