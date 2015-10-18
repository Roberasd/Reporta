package appdatamx.hackcolima.roberto.reporta.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import appdatamx.hackcolima.roberto.reporta.R;
import appdatamx.hackcolima.roberto.reporta.application.SuperActivity;
import appdatamx.hackcolima.roberto.reporta.request.CreateComplaintRequest;
import appdatamx.hackcolima.roberto.reporta.utils.Constants;

public class NewComplaint extends SuperActivity {

    private ViewGroup rLSendComplaint;
    private EditText editDescription;
    private ImageView complaintImage;
    private CreateComplaintRequest createComplaintRequest;
    private String dirPicture;
    private int PHOTO_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);

        setToolbarAndTittle("Crear denuncia");

        rLSendComplaint = (ViewGroup) findViewById(R.id.rlsendcomplaint);
        editDescription = (EditText) findViewById(R.id.editdescription);
        ImageView buttonCamera = (ImageView) findViewById(R.id.buttoncamera);
        complaintImage = (ImageView) findViewById(R.id.complaintImage);

        createComplaintRequest = new CreateComplaintRequest(getApplicationContext());

        Bundle bundle = getIntent().getParcelableExtra("bundle");
        final LatLng latLng = bundle.getParcelable("latLng");

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

                if(!description.equals("") && !dirPicture.equals(""))
                    requestToSendComplaint(description, latLng);
                else
                    Toast.makeText(getApplicationContext(), "debes de poner la descripción y tomar una foto",
                            Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestToSendComplaint(String description, LatLng latLng) {
        showLoaderDialog();
        createComplaintRequest.createComplaint(1, description, dirPicture, latLng, new CreateComplaintRequest.CreateComplaintListener() {
            @Override
            public void onSuccess() {
                hideLoaderDialog();
                Intent intent = new Intent(NewComplaint.this, HomePageActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Denuncia hecha. ¡Gracias!",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFaliure(String error) {
                Log.d("Roberto", "Crear denuncua " + error);
                hideLoaderDialog();
                Toast.makeText(getApplicationContext(), "Error al realiar denuncia",
                        Toast.LENGTH_SHORT).show();

            }
        });

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

                dirPicture =  Environment.getExternalStorageDirectory() + File.separator
                        + Constants.MEDIA_DIRECTORY + File.separator + Constants.TEMPORAL_PICTURE_NAME;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inDither = true;
                Bitmap bitmap = BitmapFactory.decodeFile(dirPicture, options);

                complaintImage.setImageBitmap(bitmap);

                File newFile = new File(dirPicture);

                try {
                    FileOutputStream fOut = new FileOutputStream(newFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 25, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    Log.i("Rob", "No se encontro el path a la imagen: " + '\n' + e.getMessage());
                } catch (IOException e) {
                    Log.i("Rob", "Error de escritura: " + e.getMessage() + '\n');
                }

            }

        }else{
            Toast.makeText(getApplicationContext(), "Error al tomar foto", Toast.LENGTH_SHORT).show();
        }
    }
}