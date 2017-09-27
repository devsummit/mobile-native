package io.devsummit.devsummit.Remote;

import io.devsummit.devsummit.Models.LoginModel;
import io.devsummit.devsummit.Models.RegisterModel;
import io.devsummit.devsummit.Models.login.Credentials;
import io.devsummit.devsummit.Models.login.MobileCredentials;
import io.devsummit.devsummit.Models.register.RegisterCredentials;
import io.devsummit.devsummit.Models.register.RegisterMobileCredentials;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
}
