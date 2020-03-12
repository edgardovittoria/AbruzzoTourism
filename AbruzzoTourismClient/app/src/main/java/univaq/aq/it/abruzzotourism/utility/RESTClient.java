package univaq.aq.it.abruzzotourism.utility;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RESTClient {
    private static final String BASE_URL = "http://10.0.2.2:8080/AbruzzoTourism/rest/service";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler){
        client.addHeader("Accept","application/json");
        client.addHeader("Content-Type","application/json");
        client.post(getAbsoluteUrl(url), params, asyncHttpResponseHandler);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler){
        client.addHeader("Accept","application/json");
        client.get(getAbsoluteUrl(url), params, asyncHttpResponseHandler);
    }

    public static void put(String url, RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler){
        client.addHeader("Accept","application/json");
        client.addHeader("Content-Type","application/json");
        client.put(getAbsoluteUrl(url), params, asyncHttpResponseHandler);
    }




    public static String getAbsoluteUrl(String relativeUrl){
        return BASE_URL + relativeUrl;
    }
}
