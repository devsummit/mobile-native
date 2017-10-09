package io.devsummit.android.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import io.devsummit.android.Controllers.UserFeedController;
import io.devsummit.android.Controllers.UserTicketController;
import io.devsummit.android.Helpers.ExitAppHelper;
import io.devsummit.android.Helpers.UserAuthenticationHelper;
import io.devsummit.android.R;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private UserAuthenticationHelper authHelper;
    private UserTicketController userTicketController;
    private ProgressBar mProgressView;
    private BottomNavigationView navigation;
    private UserFeedController userFeedController;

    @Override
    public void onBackPressed() {
        ExitAppHelper.exitTheApp(this);
    }

    private FrameLayout mContainer;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mContainer.removeAllViews();
            Fragment frag = null;
            String token = authHelper.getAccessToken();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    userFeedController.getUserFeed(token, MainActivity.this, 1);
                    break;
                case R.id.tickets:
                    userTicketController.getUserTickets(token, MainActivity.this);
                    break;
            }
            if (frag != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.container, frag, frag.getTag());
                ft.commit();
            }
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authHelper = new UserAuthenticationHelper(this);
        mProgressView = (ProgressBar) findViewById(R.id.main_activity_loader);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        userTicketController = new UserTicketController(MainActivity.this);
        mContainer = (FrameLayout) findViewById(R.id.container);
        userFeedController = new UserFeedController(MainActivity.this);
        Realm.init(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        userTicketController.getUserTickets(authHelper.getAccessToken(), MainActivity.this);
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
