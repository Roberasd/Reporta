package appdatamx.hackcolima.roberto.reporta.percistence;

import android.content.Context;
import android.content.SharedPreferences;

import appdatamx.hackcolima.roberto.reporta.model.UserModel;

/**
 * Created by Robert Avalos on 18/10/15.
 */
public class UserNeuron {

    private static final String PREF_NAME = "reporta_user";


    private static final String _ID = "user_id";
    private static final String NAME = "user_name";

    private static final String LOGGED            = "logged";

    private SharedPreferences manager;
    private SharedPreferences.Editor editor;

    public UserNeuron(Context context){
        manager = context.getSharedPreferences(PREF_NAME, 0);
        editor = manager.edit();
    }

    public void saveUserSignUp(UserModel user){
        editor.putString(NAME, user.getName());
        editor.putString(_ID, user.getId());

        editor.commit();
    }


    public String getUserId(){
        return manager.getString(_ID, "");
    }

    public void setItIsLogged(boolean logged){
        editor.putBoolean(LOGGED, logged);
        editor.commit();
    }

    public boolean getItIsLogged(){
        return manager.getBoolean(LOGGED, false);
    }
}
