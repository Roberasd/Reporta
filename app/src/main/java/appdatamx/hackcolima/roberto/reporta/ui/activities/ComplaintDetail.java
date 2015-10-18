package appdatamx.hackcolima.roberto.reporta.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import appdatamx.hackcolima.roberto.reporta.R;
import appdatamx.hackcolima.roberto.reporta.application.ApplicationController;
import appdatamx.hackcolima.roberto.reporta.application.SuperActivity;
import appdatamx.hackcolima.roberto.reporta.model.ComplaintModel;
import appdatamx.hackcolima.roberto.reporta.request.ComplaintDetailRequest;

public class ComplaintDetail extends SuperActivity {

    private ComplaintDetailRequest complaintDetailRequest;
    private ImageView complaintImage;
    private TextView texttodescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_detail);


        TextView titleToolbar = (TextView) findViewById(R.id.titletoolbar);
        texttodescription = (TextView) findViewById(R.id.texttodescription);
        complaintImage = (ImageView) findViewById(R.id.complaintImage);

        int complaintId = getIntent().getIntExtra("complaint_id", 0);

        titleToolbar.setText("Detalle de denuncia");

        complaintDetailRequest = new ComplaintDetailRequest(getApplicationContext());


        detailComplaint(complaintId);

    }

    private void detailComplaint(int id) {
        showLoaderDialog();
        complaintDetailRequest.getInfoComplaint(id, new ComplaintDetailRequest.ComplaintListener() {
            @Override
            public void onSuccess(ComplaintModel model) {

                texttodescription.setText(model.getDescription());

                ApplicationController.getInstance().getImageLoader(getApplicationContext())
                        .displayImage(model.getImage_url(), complaintImage);

                hideLoaderDialog();
            }

            @Override
            public void onFaliure(String error) {

            }
        });
    }

}
