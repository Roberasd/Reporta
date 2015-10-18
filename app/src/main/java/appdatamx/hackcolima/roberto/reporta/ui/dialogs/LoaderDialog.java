package appdatamx.hackcolima.roberto.reporta.ui.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appdatamx.hackcolima.roberto.reporta.R;

/**
 * Created by Roberto Avalos on 18/10/2015.
 */

public class LoaderDialog extends DialogFragment {

    private View rootView;
    private boolean option;
    private TextView text;

    public static LoaderDialog newInstance(){
        LoaderDialog fragment = new LoaderDialog();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, R.style.Theme_Dialog_Transparents);
        super.onCreate(savedInstanceState);

        if(getArguments() != null)
            option = getArguments().getBoolean("bool");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_loader, null);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
        getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_fadein_fadeout);
    }

}
