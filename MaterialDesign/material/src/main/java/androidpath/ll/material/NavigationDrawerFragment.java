package androidpath.ll.material;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidpath.ll.material.Adapter.DrawerListAdapter;
import androidpath.ll.material.Model.DrawerItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment{

    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";


    @LayoutRes
    private DrawerLayout mDrawerLayout;

    private RecyclerView mRecyclerView;
    private ActionBarDrawerToggle mDrawerToogle;
    private View containerView;
    private DrawerListAdapter mDrawerListAdapter;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, false); //user never open drawer before
        mFromSavedInstanceState = savedInstanceState != null ? true : false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        mDrawerListAdapter = new DrawerListAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(mDrawerListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {

        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToogle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }


            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToogle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToogle.syncState();
                if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer);
                }

            }
        });

    }

    public static List<DrawerItem> getData() {
        List<DrawerItem> drawerItems = new ArrayList<DrawerItem>();
        int[] icons = {
                R.drawable.ic_number1,
                R.drawable.ic_number2,
                R.drawable.ic_number3,
                R.drawable.ic_number4
        };
        String[] titles = {
                "Hello",
                "Leonard",
                "Inbox",
                "Events"
        };

        //for (int i = 0; i < titles.length && i < icons.length; i++)
        for (int i = 0; i < 100; i++) {
            DrawerItem item = new DrawerItem();
            item.setIconId(icons[i % icons.length]);
            item.setTitle(titles[i % titles.length]);
            drawerItems.add(item);
        }

        return drawerItems;
    }


    public static void saveToPreferences(Context context, String preferenceName, boolean preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(preferenceName, preferenceValue);
        editor.apply();
    }

    public static boolean readFromPreferences(Context context, String preferenceName, boolean defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getBoolean(preferenceName, defaultValue);
    }
}
