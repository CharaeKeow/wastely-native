package my.edu.utem.ftmk.bitp3453.achifapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DonationDetailActivity extends AppCompatActivity {
    private TextView txtDonationTitle, txtPhoneNo, txtQuantity, txtDescription, txtPickUpTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_detail);

        String donationTitle = getIntent().getStringExtra("donationTitle");
        String quantity = getIntent().getStringExtra("quantity");
        String description = getIntent().getStringExtra("description");
        String phoneNo = getIntent().getStringExtra("phoneNo");
        String pickUpTime = getIntent().getStringExtra("pickUpTime");

        txtDonationTitle = findViewById(R.id.item_detail_donation_title);
        txtPhoneNo = findViewById(R.id.item_detail_donation_phone_no);
        txtQuantity = findViewById(R.id.item_detail_donation_quantity);
        txtDescription = findViewById(R.id.item_detail_donation_description);
        txtPickUpTime = findViewById(R.id.item_detail_donation_pick_up_time);

        txtDonationTitle.setText(donationTitle);
        txtPhoneNo.setText(phoneNo);
        txtDescription.setText(description);
        txtPickUpTime.setText(pickUpTime);
        txtQuantity.setText(quantity);
    }
}