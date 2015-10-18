package appdatamx.hackcolima.roberto.reporta.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import appdatamx.hackcolima.roberto.reporta.R;
import appdatamx.hackcolima.roberto.reporta.model.UserModel;
import appdatamx.hackcolima.roberto.reporta.percistence.UserNeuron;
import appdatamx.hackcolima.roberto.reporta.request.CheckUserIfExistRequest;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private UserNeuron userNeuron;
    private CheckUserIfExistRequest checkUserIfExistRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        userNeuron = new UserNeuron(getApplicationContext());
        checkUserIfExistRequest = new CheckUserIfExistRequest(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_prfile", "email"));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // Application code
                                UserModel model = new UserModel();

                                try {

                                    if(!object.getString("email").equals(""))
                                        model.setEmail(object.getString("email"));

                                    model.setId(object.getString("id"));
                                    model.setName(object.getString("name"));
                                    userNeuron.saveUserSignUp(model);
                                    userIsRegistered(model);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                request.executeAsync();

                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                startActivity(intent);
                userNeuron.setItIsLogged(true);
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(), "Inicio de sesión con facebook cancelado", Toast.LENGTH_SHORT).show();
                userNeuron.setItIsLogged(false);
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(), "Ocurrio un error, intenta de nuevo", Toast.LENGTH_SHORT).show();
                userNeuron.setItIsLogged(false);
            }
        });

    }

    private void userIsRegistered(UserModel model) {
        checkUserIfExistRequest.checkUser(new CheckUserIfExistRequest.CheckUserIfExistRequestLitener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFaliure(String error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
