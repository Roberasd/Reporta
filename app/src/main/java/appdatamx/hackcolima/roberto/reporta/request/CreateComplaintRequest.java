package appdatamx.hackcolima.roberto.reporta.request;

import android.content.Context;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import appdatamx.hackcolima.roberto.reporta.percistence.UserNeuron;
import appdatamx.hackcolima.roberto.reporta.web.WebService;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Roberto Avalos on 18/10/2015.
 */
public class CreateComplaintRequest {

    private AsyncHttpClient client;
    private Context context;
    private UserNeuron userNeuron;

    public CreateComplaintRequest(Context context){
        this.context = context;
        userNeuron = new UserNeuron(context);
    }

    public void createComplaint(int categoryId, String description, String picture, LatLng latLng, final CreateComplaintListener listener){
        client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        File newFile = new File(picture);

        try {
            params.put("report[description]", description);
            params.put("report[latitude]", latLng.latitude);
            params.put("report[longitude]", latLng.longitude);
            params.put("report[picture]", newFile);
            params.put("report[category_id]", categoryId);
            params.put("report[user_id]", userNeuron.getUserId());
            //params.put("user_id", userNeuron.getUserId());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        client.addHeader("X-Auth-Token", userNeuron.getAccessToken());
        client.post(context.getApplicationContext(), WebService.createComplaintUrl(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                listener.onSuccess();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFaliure(error.getMessage());
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }


    public interface CreateComplaintListener{
        void onSuccess();
        void onFaliure(String error);
    }
}
