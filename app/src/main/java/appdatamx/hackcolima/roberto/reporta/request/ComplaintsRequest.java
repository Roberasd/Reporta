package appdatamx.hackcolima.roberto.reporta.request;

import android.content.Context;

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
public class ComplaintsRequest {

    private AsyncHttpClient client;
    private Context context;
    private UserNeuron userNeuron;

    public ComplaintsRequest(Context context){
        this.context = context;
        userNeuron = new UserNeuron(context);
    }

    public void getAllComplainst(final ComplaintsListener listener){
        client = new AsyncHttpClient();

        String id = userNeuron.getUserId();

        client.get(context.getApplicationContext(), WebService.getCheckUserUrl(id), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String responseStr = new String(responseBody);

                ArrayList<ComplaintModel> clientsModelArrayList = new ArrayList<ComplaintModel>();

                Gson gson = new Gson();
                JsonParser jsonParser = new JsonParser();

                JsonArray jArray = jsonParser.parse(responseStr).getAsJsonArray();

                for (JsonElement obj : jArray) {

                    ComplaintModel complaintModel = gson.fromJson(obj, ComplaintModel.class);
                    clientsModelArrayList.add(complaintModel);
                }

                listener.onSuccess(clientsModelArrayList);
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


    public interface ComplaintsListener{
        void onSuccess(ArrayList<ComplaintModel> clientsModelArrayList);
        void onFaliure(String error);
    }
}
