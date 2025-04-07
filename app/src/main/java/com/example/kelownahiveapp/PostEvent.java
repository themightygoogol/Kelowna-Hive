package com.example.kelownahiveapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class PostEvent extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDate;
    private EditText editTextLocation;
    private EditText editTextDescription;
    private CheckBox checkBoxRsvp;
    private Button buttonSubmit;
    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;

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


        editTextDate.setOnClickListener(v -> showDateTimePicker());

        buttonSubmit.setOnClickListener(v -> {

            String title = editTextTitle.getText().toString();
            String date = editTextDate.getText().toString();
            String location = editTextLocation.getText().toString();
            String description = editTextDescription.getText().toString();
            boolean isRsvp = checkBoxRsvp.isChecked();

            if (title.isEmpty() || date.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Title, Date, and Location are required.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isRsvp) {
                description += "\nRSVP: Yes";
            } else {
                description += "\nRSVP: No";
            }

            // Default image but images should not be mandatory
            int[] defaultImages = new int[]{ R.drawable.ic_launcher_foreground };


            Event event = new Event(title, date, location, description, defaultImages);


            Toast.makeText(this, "Event Created:\n" + event.getTitle() + "\n" + event.getDateTime(), Toast.LENGTH_LONG).show();
            // Finish the activity
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
}