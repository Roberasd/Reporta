package appdatamx.hackcolima.roberto.reporta.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import appdatamx.hackcolima.roberto.reporta.R;
import appdatamx.hackcolima.roberto.reporta.percistence.UserNeuron;
import appdatamx.hackcolima.roberto.reporta.application.SuperActivity;

public class UserInfoActivity extends SuperActivity {

    private UserNeuron userNeuron;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        setToolbarAndTittle("Perfil");

        userNeuron = new UserNeuron(getApplicationContext());
        ProfilePictureView profilePictureView = (ProfilePictureView) findViewById(R.id.profile_image);

        Profile profile = Profile.getCurrentProfile();
        profilePictureView.setProfileId(profile.getId());

        Button loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
                startActivity(intent);
                userNeuron.logout();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UserInfoActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}
