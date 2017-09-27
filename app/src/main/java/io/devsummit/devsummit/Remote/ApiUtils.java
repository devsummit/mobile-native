package io.devsummit.devsummit.Remote;

/**
 * Created by ganesh on 25/09/17.
 */

public class ApiUtils {

    private ApiUtils() {}

    //public static final String BASE_URL = "http://api.devsummit.io:8081/";
    public static final String BASE_URL = "http://localhost:5000/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
