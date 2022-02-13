package my.edu.utem.ftmk.bitp3453.achifapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.os.Bundle;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {
    private ImageCapture imageCapture;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        registerForActivityResult(new ActivityResultContracts.RequestPermission(), this::cameraGranted)
                .launch(Manifest.permission.CAMERA);
    }

    private void cameraGranted(Boolean isGranted) {
        if (isGranted) {

            cameraProviderFuture = ProcessCameraProvider.getInstance(this);

            cameraProviderFuture.addListener(this::startCamera, ContextCompat.getMainExecutor(this));
        }
    }

    private void startCamera() {
        try {
            ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

            PreviewView previewView = findViewById(R.id.previewView);

            Preview preview = new Preview.Builder().build();

            ImageCapture imageCapture = new ImageCapture.Builder()
                    .build();

            CameraSelector cameraSelector = new CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build();

            preview.setSurfaceProvider(previewView.getSurfaceProvider());

            cameraProvider.unbindAll();
            cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, preview);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void onClick() {

    }
}