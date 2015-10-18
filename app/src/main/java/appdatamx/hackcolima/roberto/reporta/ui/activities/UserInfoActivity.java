package appdatamx.hackcolima.roberto.reporta.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import appdatamx.hackcolima.roberto.reporta.R;
import appdatamx.hackcolima.roberto.reporta.percistence.UserNeuron;
import appdatamx.hackcolima.roberto.reporta.application.SuperActivity;

public class UserInfoActivity extends SuperActivity {

    private UserNeuron userNeuron;
    private ProfilePictureView profilePictureView;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        setToolbarAndTittle("Perfil");

        userNeuron = new UserNeuron(getApplicationContext());
        profilePictureView = (ProfilePictureView) findViewById(R.id.profile_image);

        Profile profile = Profile.getCurrentProfile();
        profilePictureView.setProfileId(profile.getId());

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code


                Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
                startActivity(intent);
                userNeuron.setItIsLogged(false);
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(), "Cerrar sesión con facebook cancelado", Toast.LENGTH_SHORT).show();
                userNeuron.setItIsLogged(true);
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(), "Ocurrio un error, intenta de nuevo", Toast.LENGTH_SHORT).show();
                userNeuron.setItIsLogged(true);
            }
        });



    }

}
