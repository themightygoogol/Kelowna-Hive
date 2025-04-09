package com.example.kelownahiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EventDetailActivity extends AppCompatActivity {

    private int[] eventImages = null;
    private int ratingCount = 0; // initial rating
    private static final String EVENTS_FILE = "events.json";

    private String eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // Retrieve event data from the intent extras
        Intent intent = getIntent();
        eventTitle = intent.getStringExtra("title");
        String dateTime = intent.getStringExtra("dateTime");
        String location = intent.getStringExtra("location");
        String description = intent.getStringExtra("description");
        String category = intent.getStringExtra("category");
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

        tvEventTitle.setText(eventTitle);
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

        // Share functionality: share event details via an intent
        Button btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareText = eventTitle + "\n" + dateTime + "\n" + location + "\n" + description;
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


            Button btnDelete = findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteEventByName();
                    Toast.makeText(EventDetailActivity.this, "Event deleted.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK); // Signal to EventListActivity to refresh its list.
                    finish();
                }
            });

    }

    // Deletes the event from internal storage based on its title.
    private void deleteEventByName() {
        try {
            File file = new File(getFilesDir(), EVENTS_FILE);
            List<Event> events;
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                String json = sb.toString();
                Gson gson = new Gson();
                Type eventListType = new TypeToken<ArrayList<Event>>() {}.getType();
                events = gson.fromJson(json, eventListType);
                if (events == null) {
                    events = new ArrayList<>();
                }
            } else {
                events = new ArrayList<>();
            }

            // Debug: Log the stored event titles.
            Log.d("DeleteEvent", "Attempting to delete event with title: " + eventTitle);
            for (Event e : events) {
                Log.d("DeleteEvent", "Stored event title: " + e.getTitle());
            }

            // Remove the event matching the title (assumes title uniqueness).
            boolean removed = false;
            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).getTitle() != null && events.get(i).getTitle().equals(eventTitle)) {
                    events.remove(i);
                    removed = true;
                    Log.d("DeleteEvent", "Removed event with title: " + eventTitle);
                    break;
                }
            }
            if (!removed) {
                Log.d("DeleteEvent", "No event found with title: " + eventTitle);
            }

            // Save the updated events back to internal storage.
            Gson gson = new Gson();
            String newJson = gson.toJson(events);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(newJson.getBytes());
            fos.close();
            Log.d("DeleteEvent", "Updated events after deletion: " + newJson);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DeleteEvent", "Error deleting event: " + e.getMessage());
            Toast.makeText(this, "Error deleting event: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}