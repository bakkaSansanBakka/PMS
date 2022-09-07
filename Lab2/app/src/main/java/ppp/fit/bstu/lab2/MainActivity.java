package ppp.fit.bstu.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private DatePickerDialog currentDatePickerDialog;
    private DatePickerDialog birthDatePickerDialog;
    private Button currentDateButton;
    private Button birthDateButton;
    private Button calculateAgeButton;
    private TextView calculatedAgeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCurrentDatePicker();
        initBirthDatePicker();

        currentDateButton = findViewById(R.id.currentDatePickerButton);
        currentDateButton.setText(getTodaysDate());

        birthDateButton = findViewById(R.id.birthDatePickerButton);
        birthDateButton.setText(getTodaysDate());

        calculatedAgeTextView = findViewById(R.id.calculatedAgeTextView);

        calculateAgeButton = findViewById(R.id.calculateButton);
        calculateAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalculatedAgeActivity.class);
                intent.putExtra("currentDate", currentDateButton.getText());
                intent.putExtra("birthDate", birthDateButton.getText());

                startActivity(intent);
            }
        });
    }

    private void initBirthDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                birthDateButton.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        birthDatePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return makeDateString(day, month, year);
    }

    private void initCurrentDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                currentDateButton.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        currentDatePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int _day, int _month, int _year) {
        String day = Integer.toString(_day);
        String month = Integer.toString(_month);
        String year = Integer.toString(_year);

        if (_day < 10) {
            day = "0" + _day;
        }

        if (_month < 10) {
            month = "0" + _month;
        }

        return day + "/" + month + "/" + year;
    }

    public void openCurrentDatePicker(View view) {
        currentDatePickerDialog.show();
    }

    public void openBirthDatePicker(View view) {
        birthDatePickerDialog.show();
    }

    public void clearDatePickers(View view) {
        currentDateButton.setText(getTodaysDate());
        birthDateButton.setText(getTodaysDate());
    }

}