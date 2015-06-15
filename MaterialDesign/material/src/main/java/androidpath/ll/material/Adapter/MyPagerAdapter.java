package androidpath.ll.material.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import androidpath.ll.material.R;
import androidpath.ll.material.ui.MyFragment;

/**
 * Created by Le on 2015/6/14.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    String[] tabText;
    int[] icons = {R.drawable.ic_action_home, R.drawable.ic_action_articles, R.drawable.ic_action_personal};

    @Nullable
    Drawable drawable;

    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        tabText = context.getResources().getStringArray(R.array.tabs);
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

        if (mContext != null) {
            drawable = mContext.getResources().getDrawable(icons[position]);
            assert drawable != null;
            drawable.setBounds(0, 0, 144, 144);
        }

        ImageSpan imageSpan = new ImageSpan(drawable);
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}