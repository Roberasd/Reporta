package appdatamx.hackcolima.roberto.reporta.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import appdatamx.hackcolima.roberto.reporta.R;
import appdatamx.hackcolima.roberto.reporta.application.SuperActivity;
import appdatamx.hackcolima.roberto.reporta.model.UserModel;
import appdatamx.hackcolima.roberto.reporta.percistence.UserNeuron;
import appdatamx.hackcolima.roberto.reporta.request.CheckUserIfExistRequest;
import appdatamx.hackcolima.roberto.reporta.request.RegisterUserRequest;

public class LoginActivity extends SuperActivity {

    private CallbackManager callbackManager;
    private UserNeuron userNeuron;
    private CheckUserIfExistRequest checkUserIfExistRequest;
    private RegisterUserRequest registerUserRequest;
    private UserModel model = new UserModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        userNeuron = new UserNeuron(getApplicationContext());
        checkUserIfExistRequest = new CheckUserIfExistRequest(getApplicationContext());
        registerUserRequest = new RegisterUserRequest(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

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
                                showLoaderDialog();
                                try {

                                    model.setId(object.getString("id"));
                                    model.setName(object.getString("name"));
                                    model.setAccessToken(AccessToken.getCurrentAccessToken().getToken());
                                    userNeuron.saveUserSignUp(model);
                                    Log.d("Rober", "access token " + model.getAccessToken());
                                    userIsRegistered();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                request.executeAsync();
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

    private void userIsRegistered() {
        checkUserIfExistRequest.checkUser(new CheckUserIfExistRequest.CheckUserIfExistRequestLitener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {

                try {
                    if (jsonObject.getString("message").equals("user not found"))
                        registerUser();
                    else {
                        startHomeActivity();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFaliure(String error) {
                hideLoaderDialog();
                LoginManager.getInstance().logOut();
                Log.d("Rober", "Error exist usuer " + error);
            }
        });
    }

    private void registerUser() {
        registerUserRequest.registerUser(new RegisterUserRequest.RegisterUserListener() {
            @Override
            public void onSuccess() {
                startHomeActivity();

            }

            @Override
            public void onFaliure(String error) {
                hideLoaderDialog();
                LoginManager.getInstance().logOut();
                Log.d("Rober", "registrando usuario " + error);
            }
        });
    }

    private void startHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
        startActivity(intent);
        userNeuron.setItIsLogged(true);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
