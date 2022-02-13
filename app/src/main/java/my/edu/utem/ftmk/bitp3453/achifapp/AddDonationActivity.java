package my.edu.utem.ftmk.bitp3453.achifapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class AddDonationActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private boolean hasImage = false;
    private String currentPhotoPath;
    private FirebaseFirestore db;
    private EditText edtDonationTitle, edtPhoneNo, edtPickUpTime, edtDescription, edtQuantity;
    private String donationTitle, phoneNo, pickUpTime, description, quantity;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        if(hasImage) {
            Button btnAddImage = findViewById(R.id.btn_add_image);
            btnAddImage.setText(R.string.replace_image);
        }

        edtDonationTitle = findViewById(R.id.donation_title);
        edtPhoneNo = findViewById(R.id.phone_number);
        edtPickUpTime = findViewById(R.id.pick_up_time);
        edtDescription = findViewById(R.id.description);
        edtQuantity = findViewById(R.id.donation_quantity);
        btnSubmit = findViewById(R.id.btn_submit_donation);
    }

    public void addDonationImage(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            System.out.println("Test1");
            try {
                photoFile = createImageFile();
                System.out.println("Test2");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                System.out.println("Test3");
                Uri photoURI = FileProvider.getUriForFile(this,
                        "my.edu.utem.ftmk.bitp3453.achifapp",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ImageView imageView = findViewById(R.id.donation_image);
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();
        return image;
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

        CollectionReference dbDonation = db.collection("donation");

        //create new donation
        Donation donation = new Donation(donationTitle, phoneNo, description, pickUpTime, quantity);

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