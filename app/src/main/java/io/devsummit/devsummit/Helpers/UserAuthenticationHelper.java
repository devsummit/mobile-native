package io.devsummit.devsummit.Helpers;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.Gson;

import java.util.Date;

import io.devsummit.devsummit.Models.authmodel.JWTModel;
import io.devsummit.devsummit.Models.authmodel.RefreshTokenModel;
import io.devsummit.devsummit.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.devsummit.devsummit.Constants.ACCESS_TOKEN;
import static io.devsummit.devsummit.Constants.REFRESH_TOKEN;
import static io.devsummit.devsummit.Constants.USER_AUTH;

/**
 * Created by ganesh on 25/09/17.
 */

public class UserAuthenticationHelper {
    private final Context context;
    private final SharedPreferences sharedPrefs;
    private APIService mAPIService;

    public UserAuthenticationHelper(Context context){
        this.context = context;
        sharedPrefs = context.getSharedPreferences(USER_AUTH, 0);
    }

    public void saveAccessToken(String accessToken, String refreshToken) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(ACCESS_TOKEN, accessToken).apply();
        editor.putString(REFRESH_TOKEN, refreshToken).apply();
    }

    public String getAccessToken(){
        final String[] token = {sharedPrefs.getString(ACCESS_TOKEN, "DEFAULT")};
        if (token[0].equals("DEFAULT")) {
            return token[0];
        }

        JWTModel jwt = decodeToken(token[0]);
        int exp = (int)Math.floor(new Date().getTime()/1000);

        // if not expired
        if (exp < jwt.getExp()) {
            return token[0];
        }

        String refreshToken = sharedPrefs.getString(REFRESH_TOKEN, "DEFAULT");

        mAPIService.refreshToken(refreshToken).enqueue(new Callback<RefreshTokenModel>() {
            @Override
            public void onResponse(Call<RefreshTokenModel> call, Response<RefreshTokenModel> response) {
                saveAccessToken(response.body().getData().getAccessToken(), response.body().getData().getRefreshToken());
                token[0] = response.body().getData().getAccessToken();
            }

            @Override
            public void onFailure(Call<RefreshTokenModel> call, Throwable t) {
            }
        });

        return token[0];
    }

    private JWTModel decodeToken(String token){
        String base64Url = token.split("\\.")[0];
        String base64 = base64Url.replace("-", "+").replace("_", "/");
        byte[] decoded = Base64.decode(base64, Base64.URL_SAFE);
        JWTModel jwt = new Gson().fromJson(new String(decoded), JWTModel.class);
        return jwt;
    }
}
