package appdatamx.hackcolima.roberto.reporta.ui.activities;

import android.os.Bundle;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;


import appdatamx.hackcolima.roberto.reporta.R;
import appdatamx.hackcolima.roberto.reporta.percistence.UserNeuron;
import appdatamx.hackcolima.roberto.reporta.application.SuperActivity;

public class UserInfoActivity extends SuperActivity {

    private UserNeuron userNeuron;
    private ProfilePictureView profilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        setToolbarAndTittle("Perfil");

        userNeuron = new UserNeuron(getApplicationContext());
        profilePictureView = (ProfilePictureView) findViewById(R.id.profile_image);

        Profile profile = Profile.getCurrentProfile();
        profilePictureView.setProfileId(profile.getId());



    }

}
