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

            // Display collected information (you can replace this with saving logic)
            Toast.makeText(this, "Event: " + title +
                            "\nDate: " + date +
                            "\nLocation: " + location +
                            "\nRSVP: " + (isRsvp ? "Yes" : "No") +
                            "\nDescription: " + description,
                    Toast.LENGTH_LONG).show();
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

