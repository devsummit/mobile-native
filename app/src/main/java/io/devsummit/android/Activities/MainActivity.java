package io.devsummit.android.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import io.devsummit.android.Controllers.UserFeedController;
import io.devsummit.android.Controllers.UserTicketController;
import io.devsummit.android.Fragments.FeedFragment;
import io.devsummit.android.Fragments.TicketFragment;
import io.devsummit.android.Helpers.ExitAppHelper;
import io.devsummit.android.Helpers.UserAuthenticationHelper;
import io.devsummit.android.R;
import io.devsummit.android.ViewAdapters.MainViewPagerAdapter;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    public static UserTicketController userTicketController;
    public static UserFeedController userFeedController;
    public static TicketFragment ticketFragment;
    public static FeedFragment feedFragment;
    private UserAuthenticationHelper authHelper;
    private ProgressBar mProgressView;
    private BottomNavigationView navigation;
    private ViewPager viewPager;
    MenuItem prevMenuItem;

    @Override
    public void onBackPressed() {
        ExitAppHelper.exitTheApp(this);
    }

    public static MainActivity mMainActivity;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.tickets:
                    viewPager.setCurrentItem(1);
                    break;
            }
            return true;
        }

    };

    private ViewPager.OnPageChangeListener mOnPageChangeListerner
            = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            }
            else
            {
                navigation.getMenu().getItem(0).setChecked(false);
            }
            Log.d("page", "onPageSelected: "+position);
            navigation.getMenu().getItem(position).setChecked(true);

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };

    private void setupViewPager(ViewPager viewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        feedFragment=new FeedFragment();
        ticketFragment=new TicketFragment();
        adapter.addFragment(feedFragment);
        adapter.addFragment(ticketFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mMainActivity = MainActivity.this;

        authHelper = new UserAuthenticationHelper(this);
        mProgressView = (ProgressBar) findViewById(R.id.main_activity_loader);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        userTicketController = new UserTicketController(MainActivity.this);
        userFeedController = new UserFeedController(MainActivity.this);

        setupViewPager(viewPager);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.addOnPageChangeListener(mOnPageChangeListerner);
        userTicketController.getUserTickets(authHelper.getAccessToken());

        Realm.init(getApplicationContext());
    }

    /**
     * Shows the progress UI and hides the fetch ticket.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        mProgressView = (ProgressBar) findViewById(R.id.main_activity_loader);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
