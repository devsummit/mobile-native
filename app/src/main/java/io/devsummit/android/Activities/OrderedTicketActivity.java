package io.devsummit.android.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.devsummit.android.Controllers.OrderedTicketController;
import io.devsummit.android.Helpers.UserAuthenticationHelper;
import io.devsummit.android.Models.OrderedTicketModel;
import io.devsummit.android.R;
import io.devsummit.android.ViewAdapters.OrderedTicketListViewAdapter;

public class OrderedTicketActivity extends AppCompatActivity {
    public OrderedTicketController OrderedTickets;
    public String UserToken;
    private UserAuthenticationHelper authHelper;
    private OrderedTicketModel mOrderedTicket;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private OrderedTicketListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_ticket);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.list_order);
        }

        authHelper = new UserAuthenticationHelper(this);
        OrderedTickets = new OrderedTicketController(OrderedTicketActivity.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.orderedTicketRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        UserToken = authHelper.getAccessToken();
        OrderedTickets.getOrderedTickets(UserToken);
    }

    public void setAdapter(OrderedTicketModel orderedTickets) {
        mAdapter = new OrderedTicketListViewAdapter(orderedTickets.getData());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
