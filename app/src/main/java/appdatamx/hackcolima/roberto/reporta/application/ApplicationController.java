package appdatamx.hackcolima.roberto.reporta.application;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by Robert Avalos on 18/10/15.
 */
public class ApplicationController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
