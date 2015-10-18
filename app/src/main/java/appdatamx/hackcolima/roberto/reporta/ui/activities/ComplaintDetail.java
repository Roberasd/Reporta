package appdatamx.hackcolima.roberto.reporta.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import appdatamx.hackcolima.roberto.reporta.R;
import appdatamx.hackcolima.roberto.reporta.application.SuperActivity;
import appdatamx.hackcolima.roberto.reporta.model.ComplaintModel;
import appdatamx.hackcolima.roberto.reporta.request.ComplaintDetailRequest;

public class ComplaintDetail extends SuperActivity {

    private ComplaintDetailRequest complaintDetailRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_detail);

        Toolbar toolbarMenu = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarMenu);

        TextView titleToolbar = (TextView) findViewById(R.id.titletoolbar);
        int complaintId = getIntent().getIntExtra("complaint_id", 0);
        int category = getIntent().getIntExtra("category", 0);

        switch (category){
            case 1:
                toolbarMenu.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case 2:
                toolbarMenu.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case 3:
                toolbarMenu.setBackgroundColor(getResources().getColor(R.color.green));
                break;
        }
        titleToolbar.setText("Detalle de denuncia");

        complaintDetailRequest = new ComplaintDetailRequest(getApplicationContext());




        detailComplaint(complaintId);

    }

    private void detailComplaint(int id) {
        complaintDetailRequest.getInfoComplaint(id, new ComplaintDetailRequest.ComplaintListener() {
            @Override
            public void onSuccess(ComplaintModel model) {

            }

            @Override
            public void onFaliure(String error) {

            }
        });
    }

}
