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

import io.devsummit.android.Activities.OrderedTicketActivity;
import io.devsummit.android.Controllers.UserTicketController;
import io.devsummit.android.Helpers.UserAuthenticationHelper;
import io.devsummit.android.Models.UserTicketModel;
import io.devsummit.android.R;
import io.devsummit.android.ViewAdapters.TicketListViewAdapter;

/**
 * Created by ganesh on 27/09/17.
 */

public class TicketFragment extends Fragment implements View.OnClickListener {
    private static final String USER_TICKETS = "user_ticket_model";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mContent;
    private UserTicketController userTicketController;
    private UserAuthenticationHelper authHelper;
    private UserTicketModel mUserTicketModel;
    private ImageButton myOrders;

    public static Fragment newInstance(UserTicketModel userTicketModel) {
        Fragment frag = new TicketFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(USER_TICKETS, userTicketModel);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mUserTicketModel = (UserTicketModel) getArguments().getSerializable(
                USER_TICKETS);

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.ticket_fragment, container, false);

        authHelper = new UserAuthenticationHelper(getActivity());

        if (mUserTicketModel.getData().size() > 0) {
            mRecyclerView = (RecyclerView) layout.findViewById(R.id.user_ticket_recycler_view);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            // mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // get ticket data
            // specify an adapter (see also next example)
            mAdapter = new TicketListViewAdapter(mUserTicketModel.getData());
            mRecyclerView.setAdapter(mAdapter);
        }

        myOrders = (ImageButton) layout.findViewById(R.id.button_my_order);
        myOrders.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize views
        mContent = view.findViewById(R.id.ticket_content);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), OrderedTicketActivity.class);
        startActivity(intent);
    }
}
