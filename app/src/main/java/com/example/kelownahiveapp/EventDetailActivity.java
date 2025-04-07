package com.example.kelownahiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class EventDetailActivity extends AppCompatActivity {

    private int[] eventImages = null;
    private int ratingCount = 0; // initial rating

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // Retrieve event data from the intent extras
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String dateTime = intent.getStringExtra("dateTime");
        String location = intent.getStringExtra("location");
        String description = intent.getStringExtra("description");
        eventImages = intent.getIntArrayExtra("imageResources");
        if (eventImages == null) {
            eventImages = new int[]{R.drawable.ic_launcher_foreground};
        }

        // Initialize event detail views
        TextView tvEventTitle = findViewById(R.id.tvEventTitle);
        TextView tvEventDateTime = findViewById(R.id.tvEventDateTime);
        TextView tvEventLocation = findViewById(R.id.tvEventLocation);
        TextView tvEventRSVP = findViewById(R.id.tvEventRSVP);
        TextView tvEventDescription = findViewById(R.id.tvEventDescription);
        final TextView tvRatingCount = findViewById(R.id.tvRatingCount);

        tvEventTitle.setText(title);
        tvEventDateTime.setText(dateTime);
        tvEventLocation.setText(location);
        tvEventRSVP.setText("RSVP: Open to All");
        tvEventDescription.setText(description);
        tvRatingCount.setText(String.valueOf(ratingCount));

        // Set up the image slider using ViewPager2
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        ImageSliderAdapter adapter = new ImageSliderAdapter(eventImages);
        viewPager.setAdapter(adapter);

        // Join/Request button toggle functionality
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
                        // Launch a chat activity or similar action here
                        break;
                }
            }
        });

        // Rating functionality: upvote and downvote buttons
        Button btnUpvote = findViewById(R.id.btnUpvote);
        Button btnDownvote = findViewById(R.id.btnDownvote);
        btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingCount++;
                tvRatingCount.setText(String.valueOf(ratingCount));
            }
        });
        btnDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingCount--;
                tvRatingCount.setText(String.valueOf(ratingCount));
            }
        });

        // Share functionality: share event details using an intent
        Button btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareText = title + "\n" + dateTime + "\n" + location + "\n" + description;
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Share Event"));
            }
        });

        // Comments functionality: allow users to post comments
        final LinearLayout llComments = findViewById(R.id.llComments);
        final EditText etComment = findViewById(R.id.etComment);
        Button btnPostComment = findViewById(R.id.btnPostComment);
        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = etComment.getText().toString().trim();
                if (!commentText.isEmpty()) {
                    TextView tvComment = new TextView(EventDetailActivity.this);
                    tvComment.setText(commentText);
                    llComments.addView(tvComment);
                    etComment.setText("");
                }
            }
        });
    }
}
