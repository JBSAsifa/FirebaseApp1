package com.example.firebaseapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class CaptureActivity extends AppCompatActivity {

    private ImageView logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_capture);

        // Initialize views
        Button captureImageBtn = findViewById(R.id.captureImageBtn);
        Button loadFromGalleryBtn = findViewById(R.id.loadFromGalleryBtn);
        logoImage = findViewById(R.id.logoImage);

        // Handle "Capture Image" button click
        captureImageBtn.setOnClickListener(v -> openCamera());

        // Handle "Load From Gallery" button click
        loadFromGalleryBtn.setOnClickListener(v -> openGallery());
    }

    // Launch Camera
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            captureImageLauncher.launch(takePictureIntent);
        } else {
            Toast.makeText(this, "No camera app available!", Toast.LENGTH_SHORT).show();
        }
    }

    // Launch Gallery
    private void openGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryImageLauncher.launch(pickPhotoIntent);
    }

    // Handle the result from the Camera
    private final ActivityResultLauncher<Intent> captureImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                    logoImage.setImageBitmap(imageBitmap); // Set captured image to the ImageView
                }
            }
    );

    // Handle the result from the Gallery
    private final ActivityResultLauncher<Intent> galleryImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        logoImage.setImageBitmap(bitmap); // Set selected image to the ImageView
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
}
