package androidpath.ll.material.Views.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import androidpath.ll.material.Adapter.DrawerListAdapter;
import androidpath.ll.material.Models.DrawerItem;
import androidpath.ll.material.R;
import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * STEPS TO HANDLE THE RECYCLER CLICK
 * 1 Create a class that EXTENDS RecylcerView.OnItemTouchListener
 * 2 Create an interface inside that class that supports click and long click and indicates the View that was clicked and the position where it was clicked
 * 3 Create a GestureDetector to detect ACTION_UP single tap and Long Press events
 * 4 Return true from the singleTap to indicate your GestureDetector has consumed the event.
 * 5 Find the childView containing the coordinates specified by the MotionEvent and if the childView is not null and the listener is not null either, fire a long click event
 * 6 Use the onInterceptTouchEvent of your RecyclerView to check if the childView is not null, the listener is not null and the gesture detector consumed the touch event
 * 7 if above condition holds true, fire the click event.
 * 8 return false from the onInterceptedTouchEvent to give a chance to the childViews of the RecyclerView to process touch events if any.
 * 9 Add the onItemTouchListener object for our RecyclerView that uses our class created in step 1
 */

public class NavigationDrawerFragment extends Fragment {

    final String TAG = "LLin";

    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    @Bind(R.id.drawerList)
    RecyclerView mRecyclerView;
    private RecyclerTouchListener mRecyclerTouchListener;

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
        ButterKnife.bind(this, layout);
        mDrawerListAdapter = new DrawerListAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(mDrawerListAdapter);
        mRecyclerTouchListener = setUpRecyclerTouchListener();
        mRecyclerView.addOnItemTouchListener(mRecyclerTouchListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar, final FloatingActionButton actionButton, final FloatingActionMenu actionMenu) {

        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
                actionButton.setClickable(false);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
                actionButton.setClickable(true);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                super.onDrawerSlide(drawerView, slideOffset);
                if (actionMenu.isOpen()) {
                    actionMenu.close(true);
                }

                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                    actionButton.setAlpha(1 - slideOffset);
                }


            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
                if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer);
                }
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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

    private RecyclerTouchListener setUpRecyclerTouchListener() {
        RecyclerTouchListener recyclerTouchListener = new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int postion) {
                Toast.makeText(getActivity(), "onClick " + postion, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int postion) {
                Toast.makeText(getActivity(), "onLongClick " + postion, Toast.LENGTH_SHORT).show();
            }
        });
        return recyclerTouchListener;
    }


    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        /**
         * @see {@link {http://developer.android.com/reference/android/view/GestureDetector.html}}
         */

        private GestureDetector mGestureDetector;
        private ClickListener mClickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            Log.d(TAG, "Constructor invoked");
            this.mClickListener = clickListener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d(TAG, "onSingleTapUp" + e);
                    //Return true from this singleTap to indicate the GestureDetector has consumed the event
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null && clickListener != null) {
                        clickListener.onLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                    }
                    Log.d(TAG, "onLongPress " + e);
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            if ((childView != null) && (mClickListener != null) && mGestureDetector.onTouchEvent(e)) {
                mClickListener.onClick(childView, rv.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.d(TAG, "onTouchEvent " + e);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ClickListener {
        public void onClick(View view, int postion);

        public void onLongClick(View view, int postion);
    }


}
