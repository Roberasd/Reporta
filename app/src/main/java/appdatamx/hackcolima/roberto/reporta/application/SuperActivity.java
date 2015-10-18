package appdatamx.hackcolima.roberto.reporta.application;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import appdatamx.hackcolima.roberto.reporta.R;
import appdatamx.hackcolima.roberto.reporta.ui.dialogs.LoaderDialog;

/**
 * Created by Roberto Avalos on 17/10/2015.
 */
public class SuperActivity extends AppCompatActivity {

    private LoaderDialog loaderDialog;
    public void setToolbarAndTittle(String title){
        //Toolbar toolbarMenu = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbarMenu);

        TextView toolbarTitle = (TextView) findViewById(R.id.titletoolbar);
        toolbarTitle.setText(title);

    }

    public void showLoaderDialog(){
        if (loaderDialog==null)
            loaderDialog = new LoaderDialog();
        loaderDialog.show(getFragmentManager(), "loaderDialog");
    }

    public void hideLoaderDialog(){
        if (loaderDialog!=null)
            loaderDialog.dismiss();
    }
}
