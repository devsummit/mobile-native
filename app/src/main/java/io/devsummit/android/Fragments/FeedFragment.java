package io.devsummit.android.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.devsummit.android.Activities.MainActivity;
import io.devsummit.android.Helpers.EndlessRecyclerViewScrollListener;
import io.devsummit.android.Helpers.UserAuthenticationHelper;
import io.devsummit.android.Models.userfeed.FeedData;
import io.devsummit.android.R;
import io.devsummit.android.ViewAdapters.FeedListViewAdapter;
import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by ganesh on 27/09/17.
 */

public class FeedFragment extends Fragment {
    private UserAuthenticationHelper authHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private List<FeedData> feedData;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        authHelper = new UserAuthenticationHelper(getActivity());
        String token = authHelper.getAccessToken();
        MainActivity.userFeedController.getUserFeed(token, getContext(), 1);

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.feed_fragment, container, false);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.feeds_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //attachAdapter();

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page, totalItemsCount);
            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);

        return layout;
    }

    public void attachAdapter(){
        Realm realm = Realm.getDefaultInstance();
        feedData = realm.where(FeedData.class).findAllSorted("createdAt", Sort.DESCENDING);

        // specify an adapter (see also next example)
        mAdapter = new FeedListViewAdapter(feedData, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void loadNextDataFromApi(int offset, int totalItems) {
        // Send an API request to retrieve appropriate paginated data
        String token = authHelper.getAccessToken();
        offset = totalItems / 10;

        MainActivity.userFeedController.getUserFeed(token, getContext(), offset + 1);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }
}
