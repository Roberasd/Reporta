package appdatamx.hackcolima.roberto.reporta.request;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import appdatamx.hackcolima.roberto.reporta.percistence.UserNeuron;
import appdatamx.hackcolima.roberto.reporta.web.WebService;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Roberto Avalos on 18/10/2015.
 */
public class RegisterUserRequest {

    private AsyncHttpClient client;
    private Context context;
    private UserNeuron userNeuron;

    public RegisterUserRequest(Context context){
        this.context = context;
        userNeuron = new UserNeuron(context);
    }

    public void registerUser(final RegisterUserListener listener){
        client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("uid", userNeuron.getUserId());
        params.put("email", userNeuron.getEmail());
        params.put("name", userNeuron.getName());

        String id = userNeuron.getUserId();

        client.post(context.getApplicationContext(), WebService.getRegisterUserUrl(), params, new AsyncHttpResponseHandler() {
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


    public interface RegisterUserListener{
        void onSuccess();
        void onFaliure(String error);
    }

}
