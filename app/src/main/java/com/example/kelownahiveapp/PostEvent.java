package com.example.kelownahiveapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostEvent extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDate;
    private EditText editTextLocation;
    private EditText editTextDescription;
    private CheckBox checkBoxRsvp;
    private Button buttonSubmit;
    private Spinner spinnerCategories;
    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;

    private static final String EVENTS_FILE = "events.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.post_event), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextTitle = findViewById(R.id.editText_title);
        editTextDate = findViewById(R.id.editText_date);
        editTextLocation = findViewById(R.id.editText_location);
        editTextDescription = findViewById(R.id.editText_description);
        checkBoxRsvp = findViewById(R.id.checkBox_rsvp);
        buttonSubmit = findViewById(R.id.button_submit);
        Spinner spinnerCategories = findViewById(R.id.spinner_categories);
        String[] categories = {"Sports", "Food", "Workshops", "Art", "Education", "Meet up", "Markets","NA"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(adapter);

        editTextDate.setOnClickListener(v -> showDateTimePicker());

        buttonSubmit.setOnClickListener(v -> {

            String title = editTextTitle.getText().toString().trim();
            String date = editTextDate.getText().toString().trim();
            String location = editTextLocation.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            boolean isRsvp = checkBoxRsvp.isChecked();


            if (title.isEmpty() || date.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Title, Date, and Location are required.", Toast.LENGTH_SHORT).show();
                return;
            }
            String category = spinnerCategories.getSelectedItem().toString();
            if (category.equals("Select Category")) {
                Toast.makeText(this, "Please select a valid category.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isRsvp) {
                description += "\nRSVP: Yes";
            } else {
                description += "\nRSVP: No";
            }

            int[] defaultImages = new int[]{ R.drawable.ic_launcher_foreground };


            Event event = new Event(title, date, location, description,category,defaultImages);

            saveEventToInternalStorage(event);

            Toast.makeText(this, "Event Posted:\n" + event.getTitle() + "\n" + event.getDateTime(), Toast.LENGTH_LONG).show();
            finish();
        });
    }

    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    selectedYear = year;
                    selectedMonth = month;
                    selectedDay = dayOfMonth;

                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            PostEvent.this,
                            (TimePicker timeView, int hourOfDay, int minute) -> {
                                selectedHour = hourOfDay;
                                selectedMinute = minute;

                                String formatted = String.format("%04d-%02d-%02d %02d:%02d",
                                        selectedYear, selectedMonth + 1, selectedDay,
                                        selectedHour, selectedMinute);
                                editTextDate.setText(formatted);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                    );
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }
    private void saveEventToInternalStorage(Event event) {
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

            events.add(event);

            Gson gson = new Gson();
            String newJson = gson.toJson(events);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(newJson.getBytes());
            fos.close();
            Log.d("PostEvent", "Saved events: " + newJson);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PostEvent", "Error saving event: " + e.getMessage());
            Toast.makeText(this, "Error saving event: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}