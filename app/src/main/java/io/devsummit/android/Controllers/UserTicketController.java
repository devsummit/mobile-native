package io.devsummit.android.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import io.devsummit.android.Activities.LoginActivity;
import io.devsummit.android.Helpers.UserAuthenticationHelper;
import io.devsummit.android.R;
import io.devsummit.android.Activities.MainActivity;
import io.devsummit.android.Fragments.TicketFragment;
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
    private UserAuthenticationHelper authHelper;


    public UserTicketController(MainActivity act) {
        mMainActivity = act;
        UserTickets = new UserTicketModel();
        mAPIService = ApiUtils.getAPIService();
        authHelper = new UserAuthenticationHelper(act);
    }

    public void getUserTickets(String token, final Context context) {
        mMainActivity.showProgress(true);
        mMainActivity.enableNavigation(false);
        mAPIService.fetchUserTicket(token).enqueue(new Callback<UserTicketModel>() {
            @Override
            public void onResponse(Call<UserTicketModel> call, Response<UserTicketModel> response) {
                UserTickets = response.body();
                if(UserTickets.getMessage() == null){
                AppCompatActivity activity = (AppCompatActivity) context;
                Fragment frag = TicketFragment.newInstance(response.body());
                android.support.v4.app.FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.add(R.id.container, frag, frag.getTag());
                ft.commit();
                } else {
                    authHelper.removeAccessToken();
                    authHelper.removeProfileData();
                    Intent i = new Intent(context, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }
                mMainActivity.showProgress(false);
                mMainActivity.enableNavigation(true);

            }

            @Override
            public void onFailure(Call<UserTicketModel> call, Throwable t) {
                UserTickets = new UserTicketModel();
                mMainActivity.showProgress(false);
                mMainActivity.enableNavigation(true);
            }
        });
    }

}
