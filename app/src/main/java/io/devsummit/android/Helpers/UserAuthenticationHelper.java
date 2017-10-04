package io.devsummit.android.Helpers;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Date;

import io.devsummit.android.Activities.MainActivity;
import io.devsummit.android.Models.UserTicketModel;
import io.devsummit.android.Models.authmodel.JWTModel;
import io.devsummit.android.Models.authmodel.RefreshTokenModel;
import io.devsummit.android.Models.login.ProfileData;
import io.devsummit.android.Remote.APIService;
import io.devsummit.android.Remote.ApiUtils;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.devsummit.android.Constants.ACCESS_TOKEN;
import static io.devsummit.android.Constants.REFRESH_TOKEN;
import static io.devsummit.android.Constants.USER_AUTH;

/**
 * Created by ganesh on 25/09/17.
 */

public class UserAuthenticationHelper {
    private final Context context;
    private final SharedPreferences sharedPrefs;
    private APIService mAPIService;

    public UserAuthenticationHelper(Context context) {
        this.context = context;
        sharedPrefs = context.getSharedPreferences(USER_AUTH, 0);
        mAPIService = ApiUtils.getAPIService();
    }

    public void saveAccessToken(String accessToken, String refreshToken) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(ACCESS_TOKEN, accessToken).apply();
        editor.putString(REFRESH_TOKEN, refreshToken).apply();
    }

    public void removeAccessToken(){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(ACCESS_TOKEN, "DEFAULT").apply();
        editor.putString(REFRESH_TOKEN, "DEFAULT").apply();
    }

    public void removeProfileData(){
        final Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        try{
            RealmResults<ProfileData> row = realm.where(ProfileData.class).findAll();
            row.deleteAllFromRealm();
            realm.commitTransaction();

        } catch (Exception ex) {
            realm.cancelTransaction();
        }
    }

    public void CheckTokenExpired(final Activity act){
        final String[] token = {sharedPrefs.getString(ACCESS_TOKEN, "DEFAULT")};
        if (!token[0].equals("DEFAULT")) {
            JWTModel jwt = decodeToken(token[0]);
            int exp = (int) Math.floor(new Date().getTime() / 1000);

            // if not expired
            if (exp < jwt.getExp()) {
                act.startActivity(new Intent(act, MainActivity.class));
            } else {
                removeAccessToken();
                removeProfileData();
            }
        }




    }

    public String getAccessToken() {
        final String[] token = {sharedPrefs.getString(ACCESS_TOKEN, "DEFAULT")};
        if (token[0].equals("DEFAULT")) {
            return token[0];
        }

        JWTModel jwt = decodeToken(token[0]);
        int exp = (int) Math.floor(new Date().getTime() / 1000);

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

    private JWTModel decodeToken(String token) {
        String base64Url = token.split("\\.")[0];
        String base64 = base64Url.replace("-", "+").replace("_", "/");
        byte[] decoded = Base64.decode(base64, Base64.URL_SAFE);
        JWTModel jwt = new Gson().fromJson(new String(decoded), JWTModel.class);
        return jwt;
    }
}
