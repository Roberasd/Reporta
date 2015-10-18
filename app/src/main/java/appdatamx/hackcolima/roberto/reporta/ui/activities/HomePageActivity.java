package appdatamx.hackcolima.roberto.reporta.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;

import appdatamx.hackcolima.roberto.reporta.R;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener{

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Home");

        ViewGroup rLSecurity = (ViewGroup) findViewById(R.id.rlsecurity);
        ViewGroup rLTrafic = (ViewGroup) findViewById(R.id.rltrafic);
        ViewGroup rLUrban = (ViewGroup) findViewById(R.id.rlurban);

        rLSecurity.setOnClickListener(this);
        rLTrafic.setOnClickListener(this);
        rLUrban.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlsecurity:
                break;
            case R.id.rltrafic:
                break;
            case R.id.rlurban:
                break;
        }
    }
}
