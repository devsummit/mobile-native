package io.devsummit.android.Remote;

/**
 * Created by ganesh on 25/09/17.
 */

public class ApiUtils {

    //    public static final String BASE_URL = "http://api.devsummit.io:8081/";
    // public static final String BASE_URL = "http://localhost:5000/";
//    public static final String BASE_URL = "http://192.168.40.48:8081/";
    public static final String BASE_URL = "http://192.168.43.113:8081/";

    private ApiUtils() {
    }

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
