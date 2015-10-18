package appdatamx.hackcolima.roberto.reporta.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.appevents.AppEventsLogger;

import appdatamx.hackcolima.roberto.reporta.R;
import appdatamx.hackcolima.roberto.reporta.percistence.UserNeuron;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread mSpalsh = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this){
                        wait(3000);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                UserNeuron userNeuron = new UserNeuron(getApplicationContext());
                Intent intent;

                if(userNeuron.getItIsLogged()){
                    intent = new Intent(SplashActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                finish();


            }
        });

        mSpalsh.start();
    }

}
