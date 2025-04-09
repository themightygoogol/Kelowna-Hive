package com.example.kelownahiveapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class MainPage extends AppCompatActivity implements View.OnClickListener {
    private final Map<Integer, String> buttonCategories = new HashMap<Integer, String>() {{
        put(R.id.stickyNote1, "Sports");
        put(R.id.stickyNote2, "Food");
        put(R.id.stickyNote3, "Education");
        put(R.id.stickyNote4, "Meet up");
        put(R.id.stickyNote5, "Workshops");
        put(R.id.stickyNote6, "Markets");
        put(R.id.stickyNote7, "Art");
        put(R.id.all_button, "All");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ImageButton home = findViewById(R.id.home_button);

        for (int buttonId : buttonCategories.keySet()) {
            ImageButton btn = findViewById(buttonId);
            btn.setOnClickListener(this);
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        String category = buttonCategories.get(v.getId());
        if (category != null) {
            Intent intent = new Intent(this, CategoryPage.class);
            intent.putExtra("CATEGORY_NAME", category);
            startActivity(intent);
        }
    }


}