package androidpath.ll.material.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidpath.ll.material.R;

/**
 * Created by Le on 2015/6/14.
 */
public class MyFragment extends Fragment {
    private TextView mTextView;

    public static MyFragment getInstence(int position) {
        MyFragment myFragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_my, container, false);
        mTextView = (TextView) layout.findViewById(R.id.tv);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTextView.setText("This is Page " + bundle.getInt("position"));
        }

        return layout;

    }
}
