package io.devsummit.android.Controllers;

import android.content.Context;

import io.devsummit.android.Activities.MainActivity;
import io.devsummit.android.Helpers.InetConnectionHelper;
import io.devsummit.android.Helpers.RealmHelper;
import io.devsummit.android.Models.UserFeedModel;
import io.devsummit.android.Remote.APIService;
import io.devsummit.android.Remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wlisrausr on 04/10/17.
 */

public class UserFeedController {
    private APIService mAPIService;
    private MainActivity mMainActivity;
    private UserFeedModel userFeedResponse;

    public UserFeedController(MainActivity act) {
        mMainActivity = act;
        userFeedResponse = new UserFeedModel();
        mAPIService = ApiUtils.getAPIService();
    }

    public void getUserFeed(String token, final Context context, final int page) {
        if (mMainActivity != null) {
            mMainActivity.showProgress(true);
        }

        if (InetConnectionHelper.isNetworkAvailable(context)) {
            mAPIService.fetchUserFeed(token, page).enqueue(new Callback<UserFeedModel>() {
                @Override
                public void onResponse(Call<UserFeedModel> call, Response<UserFeedModel> response) {
                    userFeedResponse = response.body();

                    if (userFeedResponse.getMeta().getSuccess().booleanValue()) {
                        RealmHelper rh = new RealmHelper();

                        for (int i = 0; i < userFeedResponse.getData().size(); i++) {
                            rh.receiveData(userFeedResponse.getData().get(i));
                        }

                        if (page == 1) {
                            mMainActivity.feedFragment.attachAdapter();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserFeedModel> call, Throwable t) {
                }
            });
        }

        mMainActivity.showProgress(false);
    }
}
