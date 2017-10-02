package io.devsummit.android.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.devsummit.android.R;
import io.devsummit.android.Controllers.UserTicketController;
import io.devsummit.android.Helpers.UserAuthenticationHelper;
import io.devsummit.android.Models.UserTicketModel;
import io.devsummit.android.ViewAdapters.TicketListViewAdapter;

/**
 * Created by ganesh on 27/09/17.
 */

public class TicketFragment extends Fragment {
    private static final String USER_TICKETS = "user_ticket_model";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mContent;
    private UserTicketController userTicketController;
    private UserAuthenticationHelper authHelper;
    private UserTicketModel mUserTicketModel;

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

        View layout = inflater.inflate(R.layout.ticket_fragment, container, false);

        // Inflate the layout for this fragment
        authHelper = new UserAuthenticationHelper(getActivity());
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.user_ticket_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // mRecyclerView.setHasFixedSize(true);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new TicketListViewAdapter(mUserTicketModel.getData());
        mRecyclerView.setAdapter(mAdapter);
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
}
