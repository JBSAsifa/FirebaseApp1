package com.example.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NextActivity extends AppCompatActivity {

    // Declare CardView variables
    private CardView locationCard, captureCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);  // Make sure this points to your XML layout

        // Initialize CardViews
        locationCard = findViewById(R.id.locationCard);
        captureCard = findViewById(R.id.captureCard);

        // Set click listener for Location card
        locationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LocationActivity
                Intent intent = new Intent(NextActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for Capture card
        captureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CaptureActivity
                Intent intent = new Intent(NextActivity.this, CaptureActivity.class);
                startActivity(intent);
            }
        });
    }
}
