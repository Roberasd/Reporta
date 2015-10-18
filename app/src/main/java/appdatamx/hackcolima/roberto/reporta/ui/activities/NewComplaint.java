package appdatamx.hackcolima.roberto.reporta.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import appdatamx.hackcolima.roberto.reporta.R;
import appdatamx.hackcolima.roberto.reporta.application.SuperActivity;
import appdatamx.hackcolima.roberto.reporta.utils.Constants;

public class NewComplaint extends SuperActivity {

    private ViewGroup rLSendComplaint;
    private EditText editDescription;
    private ImageView buttonCamera;
    private int PHOTO_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);

        setToolbarAndTittle("Crear denuncia");

        rLSendComplaint = (ViewGroup) findViewById(R.id.rlsendcomplaint);
        editDescription = (EditText) findViewById(R.id.editdescription);
        buttonCamera = (ImageView) findViewById(R.id.buttoncamera);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        rLSendComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = editDescription.getText().toString().trim();

                if(!description.equals(""))
                    requestToSendComplaint(description);
                else
                    Toast.makeText(getApplicationContext(), "debes de poner la descripción",
                            Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestToSendComplaint(String description) {


    }

    private void openCamera(){

        File file = new File(Environment.getExternalStorageDirectory(), Constants.MEDIA_DIRECTORY);
        file.mkdirs();

        String path = Environment.getExternalStorageDirectory() + File.separator
                + Constants.MEDIA_DIRECTORY + File.separator + Constants.TEMPORAL_PICTURE_NAME;

        File newFile = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        startActivityForResult(intent, PHOTO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean resultOK = (resultCode == RESULT_OK);

        if(resultOK){
            if(requestCode == PHOTO_CODE){

                String dirPiture =  Environment.getExternalStorageDirectory() + File.separator
                        + Constants.MEDIA_DIRECTORY + File.separator + Constants.TEMPORAL_PICTURE_NAME;

                Log.d("PATH", dirPiture);

            }

        }else{
            Toast.makeText(getApplicationContext(), "Error al tomar foto", Toast.LENGTH_SHORT).show();
        }
    }
}
