package io.devsummit.android.Remote;

import io.devsummit.android.Models.LoginModel;
import io.devsummit.android.Models.NotificationModel;
import io.devsummit.android.Models.RegisterModel;
import io.devsummit.android.Models.UserTicketModel;
import io.devsummit.android.Models.authmodel.RefreshTokenModel;
import io.devsummit.android.Models.login.Credentials;
import io.devsummit.android.Models.login.MobileCredentials;
import io.devsummit.android.Models.register.RegisterCredentials;
import io.devsummit.android.Models.register.RegisterMobileCredentials;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by ganesh on 25/09/17.
 */

public interface APIService {
    @POST("auth/login")
    @Headers("Content-Type:application/json")
    Call<LoginModel> login(@Body Credentials credentials);

    @POST("auth/login")
    @Headers("Content-Type:application/json")
    Call<LoginModel> loginMobile(@Body MobileCredentials credentials);

    @POST("auth/register")
    @Headers("Content-Type:application/json")
    Call<RegisterModel> registerMobile(@Body RegisterMobileCredentials credentials);

    @POST("auth/register")
    @Headers("Content-Type:application/json")
    Call<RegisterModel> register(@Body RegisterCredentials credentials);

    @GET("api/v1/notifications?page=1")
    Call<NotificationModel> initialFetchNotification(@Header("Authorization") String token);

    @GET
    @Headers("Content-Type: application/json")
    Call<NotificationModel> fetchNotification(@Header("Authorization") String token, @Url String url);

    @POST("auth/refreshtoken")
    @Headers("Content-Type:application/json")
    Call<RefreshTokenModel> refreshToken(@Body String refresh_token);

    @GET("api/v1/user/tickets")
    Call<UserTicketModel> fetchUserTicket(@Header("Authorization") String token);

}
