package io.devsummit.devsummit.Remote;

import io.devsummit.devsummit.Models.LoginModel;
import io.devsummit.devsummit.Models.RegisterModel;
import io.devsummit.devsummit.Models.UserTicketModel;
import io.devsummit.devsummit.Models.login.Credentials;
import io.devsummit.devsummit.Models.login.MobileCredentials;
import io.devsummit.devsummit.Models.register.RegisterCredentials;
import io.devsummit.devsummit.Models.register.RegisterMobileCredentials;
import io.devsummit.devsummit.Models.NotificationModel;
import io.devsummit.devsummit.Models.authmodel.RefreshTokenModel;
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

    @POST
    @Headers("Content-Type:application/json")
    Call<RefreshTokenModel> refreshToken(@Body String refresh_token);

    @GET
    Call<UserTicketModel> fetchUserTicket(@Header("Authorization") String token);

}
