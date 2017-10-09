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

import io.devsummit.android.Controllers.UserFeedController;
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
    private static final String USER_FEED = "user_feed_model";
    private View mContent;
    private UserAuthenticationHelper authHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private UserFeedController userFeedController;
    private List<FeedData> feedData;

    public static Fragment newInstance() {
        Fragment frag = new FeedFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Realm realm = Realm.getDefaultInstance();
        feedData = realm.where(FeedData.class).findAllSorted("createdAt", Sort.DESCENDING);

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.feed_fragment, container, false);

        authHelper = new UserAuthenticationHelper(getActivity());
        userFeedController = new UserFeedController(null);

        if (feedData.size() > 0) {
            mRecyclerView = (RecyclerView) layout.findViewById(R.id.feeds_recycler_view);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter
            mAdapter = new FeedListViewAdapter(getContext(), feedData);
            mRecyclerView.setAdapter(mAdapter);

            scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    loadNextDataFromApi(page);
                }
            };

            mRecyclerView.addOnScrollListener(scrollListener);
        }

        return layout;
    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        String token = authHelper.getAccessToken();

        userFeedController.getUserFeed(token, getContext(), offset + 1);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize views
        mContent = view.findViewById(R.id.feed_content);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }
}
