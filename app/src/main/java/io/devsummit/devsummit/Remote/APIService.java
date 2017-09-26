package io.devsummit.devsummit.Remote;

import io.devsummit.devsummit.Models.LoginModel;
import io.devsummit.devsummit.Models.login.Credentials;
import io.devsummit.devsummit.Models.login.Data;
import io.devsummit.devsummit.Models.login.Included;
import io.devsummit.devsummit.Models.login.Links;
import io.devsummit.devsummit.Models.login.Meta;
import io.devsummit.devsummit.Models.login.MobileCredentials;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

}
