package io.devsummit.android.Controllers;

import io.devsummit.android.Activities.MainActivity;
import io.devsummit.android.Models.UserTicketModel;
import io.devsummit.android.Remote.APIService;
import io.devsummit.android.Remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ganesh on 27/09/17.
 */

public class UserTicketController {
    public UserTicketModel UserTickets;
    private APIService mAPIService;
    private MainActivity mMainActivity;


    public UserTicketController(MainActivity act) {
        mMainActivity = act;
        UserTickets = new UserTicketModel();
        mAPIService = ApiUtils.getAPIService();
    }

    public UserTicketModel getUserTickets(String token) {
        mMainActivity.showProgress(true);
        mAPIService.fetchUserTicket(token).enqueue(new Callback<UserTicketModel>() {
            @Override
            public void onResponse(Call<UserTicketModel> call, Response<UserTicketModel> response) {
                UserTickets = response.body();
                mMainActivity.ticketFragment.attachItemToAdapter(UserTickets.getData());
                mMainActivity.showProgress(false);
            }

            @Override
            public void onFailure(Call<UserTicketModel> call, Throwable t) {
                UserTickets = new UserTicketModel();
                mMainActivity.showProgress(false);
            }
        });

        return UserTickets;
    }

}
