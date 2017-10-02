package io.devsummit.android.Controllers;

import io.devsummit.android.Models.NotificationModel;
import io.devsummit.android.Remote.APIService;
import io.devsummit.android.Remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ganesh on 27/09/17.
 */

public class NotificationController {
    public NotificationModel Notifications;
    private APIService mAPIService;


    public NotificationController() {
        Notifications = new NotificationModel();
        mAPIService = ApiUtils.getAPIService();
    }

    public NotificationModel getNotification(String token) {
        mAPIService.initialFetchNotification(token).enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                Notifications = response.body();
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                Notifications = new NotificationModel();
            }
        });
        return Notifications;
    }

    public void getMoreNotification(String token, String url) {
        mAPIService.fetchNotification(token, url).enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                Notifications.setLinks(response.body().getLinks());
                for (int i = 0; i < response.body().getData().size(); i++) {
                    Notifications.getData().add(response.body().getData().get(i));
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
            }
        });
    }
}
