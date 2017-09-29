package io.devsummit.devsummit.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import io.devsummit.devsummit.Fragments.FeedFragment;
import io.devsummit.devsummit.Fragments.TicketFragment;
import io.devsummit.devsummit.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment frag = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    frag = FeedFragment.newInstance();
                    break;
                case R.id.tickets:
                    frag = TicketFragment.newInstance();
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Fragment frag = TicketFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, frag, frag.getTag());
        ft.commit();
    }
}
