package androidpath.ll.material.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import androidpath.ll.material.R;
import androidpath.ll.material.ui.PageFragment;

/**
 * Created by Le on 2015/6/14.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private Context mContext;

    private String[] tabText;
    private int[] tabIcon = {
            R.drawable.ic_action_home,
            R.drawable.ic_action_articles,
            R.drawable.ic_action_personal
    };


    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        tabText = context.getResources().getStringArray(R.array.tabs);
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.getInstence(position + 1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        Drawable image = mContext.getResources().getDrawable(tabIcon[position]);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        // Replace blank spaces with image icon
//        SpannableString sb = new SpannableString("   " + tabText[position]);
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return sb;

        return tabText[position];
    }
}