package appdatamx.hackcolima.roberto.reporta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import appdatamx.hackcolima.roberto.reporta.ui.activities.HomePageActivity;

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

                Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mSpalsh.start();
    }
}
