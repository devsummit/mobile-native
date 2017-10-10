package io.devsummit.android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import io.devsummit.android.Activities.MainActivity;
import io.devsummit.android.Activities.OrderedTicketActivity;
import io.devsummit.android.Helpers.UserAuthenticationHelper;
import io.devsummit.android.Models.userticket.Datum;
import io.devsummit.android.R;
import io.devsummit.android.ViewAdapters.TicketListViewAdapter;

/**
 * Created by ganesh on 27/09/17.
 */

public class TicketFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private UserAuthenticationHelper authHelper;
    private ImageButton myOrders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        authHelper = new UserAuthenticationHelper(getContext());
        String token = authHelper.getAccessToken();
        MainActivity.userTicketController.getUserTickets(token);

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.ticket_fragment, container, false);

        mRecyclerView = layout.findViewById(R.id.user_ticket_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        myOrders = layout.findViewById(R.id.button_my_order);
        myOrders.setOnClickListener(this);

        return layout;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), OrderedTicketActivity.class);
        startActivity(intent);
    }

    public void attachItemToAdapter(List<Datum> userTickets){
        // specify an adapter (see also next example)
        mAdapter = new TicketListViewAdapter(userTickets);
        mRecyclerView.setAdapter(mAdapter);
    }
}
