package appdatamx.hackcolima.roberto.reporta.ui;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import appdatamx.hackcolima.roberto.reporta.R;

/**
 * Created by Roberto Avalos on 17/10/2015.
 */
public class SuperActivity extends AppCompatActivity {

    public void setToolbarAndTittle(String title){
        Toolbar toolbarMenu = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarMenu);

        TextView toolbarTitle = (TextView) findViewById(R.id.titletoolbar);
        toolbarTitle.setText(title);

    }
}
