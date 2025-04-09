package com.example.kelownahiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kelownahiveapp.util.EdgeToEdge;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enable edge-to-edge functionality using your custom utility
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply window insets as padding to the root view (with id "main")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Existing buttons (e.g., for going to MainPage and PostEvent)
        Button mainPageButton = findViewById(R.id.goToMain);
        Button postButton = findViewById(R.id.button_post);

        mainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainPage.class);
                startActivity(intent);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostEvent.class);
                startActivity(intent);
            }
        });

        // New button to launch EventDetailActivity
        // NOTE: Ensure your activity_main.xml contains a Button with the ID "btnEventDetails"
        Button eventDetailsButton = findViewById(R.id.btnEventDetails);
        eventDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
                // Adding sample extras for demonstration
                intent.putExtra("title", "Sample Event");
                intent.putExtra("dateTime", "July 30, 2025 6:00 PM");
                intent.putExtra("location", "Downtown Venue");
                intent.putExtra("description", "This is a sample event description.");
                startActivity(intent);
            }
        });
    }
}
