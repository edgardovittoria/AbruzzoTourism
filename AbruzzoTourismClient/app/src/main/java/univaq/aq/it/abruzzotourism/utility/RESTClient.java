package univaq.aq.it.abruzzotourism.utility;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.net.URI;
import java.net.URISyntaxException;

public class RESTClient {
    private static final String BASE_URL = "http://192.168.1.3:8080/AbruzzoTourism/rest/service";

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
        try {
            return BASE_URL + new URI(relativeUrl.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
