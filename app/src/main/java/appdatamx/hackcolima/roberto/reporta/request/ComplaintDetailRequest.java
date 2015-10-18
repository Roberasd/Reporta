package appdatamx.hackcolima.roberto.reporta.request;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import appdatamx.hackcolima.roberto.reporta.model.ComplaintModel;
import appdatamx.hackcolima.roberto.reporta.percistence.UserNeuron;
import appdatamx.hackcolima.roberto.reporta.web.WebService;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Roberto Avalos on 18/10/2015.
 */
public class ComplaintDetailRequest {
    private AsyncHttpClient client;
    private Context context;
    private UserNeuron userNeuron;

    public ComplaintDetailRequest(Context context){
        this.context = context;
        userNeuron = new UserNeuron(context);
    }

    public void getInfoComplaint(int id, final ComplaintListener listener){
        client = new AsyncHttpClient();

        client.addHeader("X-AUTH-TOKEN", userNeuron.getUserId());
        client.get(context.getApplicationContext(), WebService.getComplaintDetailUrl(String.valueOf(id)), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String responseStr = new String(responseBody);
                Log.d("Roberto", "detalle de denuncia " + responseStr);

                Gson gson = new Gson();
                ComplaintModel complaintModel = gson.fromJson(responseStr, ComplaintModel.class);

                listener.onSuccess(complaintModel);
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


    public interface ComplaintListener{
        void onSuccess(ComplaintModel model);
        void onFaliure(String error);
    }
}
