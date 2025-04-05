package com.example.kelownahiveapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class EventDetailActivity extends AppCompatActivity {

    // Replace these with your actual images or use placeholders.
    private int[] eventImages = {
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // Initialize TextViews (optional if already set in XML)
        TextView tvEventTitle = findViewById(R.id.tvEventTitle);
        TextView tvEventDateTime = findViewById(R.id.tvEventDateTime);
        TextView tvEventLocation = findViewById(R.id.tvEventLocation);
        TextView tvEventRSVP = findViewById(R.id.tvEventRSVP);
        TextView tvEventDescription = findViewById(R.id.tvEventDescription);

        // Optionally update text programmatically
        tvEventTitle.setText("Sunday Football Watch Party");
        tvEventDateTime.setText("May 5th, 2025 (6 PM - 9 PM PST)");
        tvEventLocation.setText("Kelowna Downtown Library");
        tvEventRSVP.setText("RSVP: Open to All");
        tvEventDescription.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vitae orci vitae eros ullamcorper...");

        // Set up ViewPager2 with ImageSliderAdapter
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        ImageSliderAdapter adapter = new ImageSliderAdapter(eventImages);
        viewPager.setAdapter(adapter);

        // Set up the action button with toggle behavior
        final Button btnRequestJoin = findViewById(R.id.btnRequestJoin);
        btnRequestJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText = btnRequestJoin.getText().toString();
                switch (currentText) {
                    case "Request to Join":
                        btnRequestJoin.setText("Requested to Join");
                        break;
                    case "Requested to Join":
                        btnRequestJoin.setText("Enter Group Chat");
                        break;
                    case "Enter Group Chat":
                        // Here you can add code to launch a chat activity
                        break;
                }
            }
        });
    }
}
