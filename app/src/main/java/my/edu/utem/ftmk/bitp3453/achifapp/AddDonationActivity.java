package my.edu.utem.ftmk.bitp3453.achifapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class AddDonationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private EditText edtDonationTitle, edtPhoneNo, edtPickUpTime, edtDescription, edtQuantity;
    private String donationTitle, phoneNo, pickUpTime, description, quantity;
    private Button btnSubmit;
    private TextView txtLocation;
    private double longitude, latitude;

    //for location services
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);

        edtDonationTitle = findViewById(R.id.donation_title);
        edtPhoneNo = findViewById(R.id.phone_number);
        edtPickUpTime = findViewById(R.id.pick_up_time);
        edtDescription = findViewById(R.id.description);
        edtQuantity = findViewById(R.id.donation_quantity);
        btnSubmit = findViewById(R.id.btn_submit_donation);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        txtLocation = findViewById(R.id.txtLocation);

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                   Boolean fineLocationGranted = result.getOrDefault(
                           Manifest.permission.ACCESS_FINE_LOCATION, false);
                   Boolean coarseLocationGranted = result.getOrDefault(
                           Manifest.permission.ACCESS_COARSE_LOCATION, false);

                   if (fineLocationGranted != null && fineLocationGranted) {
                       fusedLocationProviderClient.getLastLocation()
                               .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                   @Override
                                   public void onSuccess(Location location) {
                                       if (location != null) {
                                           txtLocation.setText(String.format(Locale.getDefault(), "%f. %f", location.getLatitude(), location.getLongitude()));
                                           longitude = location.getLongitude();
                                           latitude = location.getLatitude();
                                           // Get the SupportMapFragment and request notification when the map is ready to be used.
                                           SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                                   .findFragmentById(R.id.add_donation_map);
                                           mapFragment.getMapAsync(AddDonationActivity.this);
                                       }
                                   }
                               });
                   }
                });

        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void submitDonation(View view) {
        donationTitle = edtDonationTitle.getText().toString();
        phoneNo = edtPhoneNo.getText().toString();
        description = edtDescription.getText().toString();
        pickUpTime = edtPickUpTime.getText().toString();
        quantity = edtQuantity.getText().toString();

        if (TextUtils.isEmpty(donationTitle)) {
            edtDonationTitle.setError("Please enter donation title");
        } else if (TextUtils.isEmpty(phoneNo)) {
            edtPhoneNo.setError("Please enter phone number");
        } else if (TextUtils.isEmpty(description)) {
            edtDescription.setError("Please enter description");
        } else if (TextUtils.isEmpty(pickUpTime)) {
            edtPickUpTime.setError("Please enter pick-up time");
        } else if (TextUtils.isEmpty(quantity)) {
            edtQuantity.setError("Please enter quantity");
        }else {
            addToFirestore(donationTitle, phoneNo, description, pickUpTime, quantity);
        }
    }

    public void addToFirestore(String donationTitle, String phoneNo, String description, String pickUpTime, String quantity) {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            CollectionReference dbDonation = db.collection("donation");

            //create new donation
            Donation donation = new Donation(donationTitle, phoneNo, description, pickUpTime, quantity, latitude, longitude, currentUser.getUid());

            //add to Firestore
            dbDonation.add(donation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(AddDonationActivity.this, "Donation successfully added!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddDonationActivity.this, "Fail to add a new donation. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}