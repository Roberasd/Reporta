package appdatamx.hackcolima.roberto.reporta.request;

import android.content.Context;

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
public class CheckUserIfExistRequest {

    private AsyncHttpClient client;
    private Context context;
    private UserNeuron userNeuron;

    public CheckUserIfExistRequest(Context context){
        this.context = context;
        userNeuron = new UserNeuron(context);
    }

    public void checkUser(final CheckUserIfExistRequestLitener listener){
        client = new AsyncHttpClient();

        String id = userNeuron.getUserId();

        client.get(context.getApplicationContext(), WebService.getCheckUserUrl(id), new AsyncHttpResponseHandler() {
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


    public interface CheckUserIfExistRequestLitener{
        void onSuccess();
        void onFaliure(String error);
    }
}
