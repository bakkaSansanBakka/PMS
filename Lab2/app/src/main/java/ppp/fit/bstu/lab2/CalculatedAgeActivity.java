package ppp.fit.bstu.lab2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CalculatedAgeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView calculatedAgeTextView;
    private Button backButton;

    private String currentDateStr;
    private String birthDateStr;

    private LocalDate currentDate;
    private LocalDate birthDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculated_age_activity);

        Intent data = getIntent();
        calculatedAgeTextView = findViewById(R.id.calculatedAgeTextView);

        currentDateStr = data.getStringExtra("currentDate");
        birthDateStr = data.getStringExtra("birthDate");

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        currentDate = dateTimeFormatter.parseLocalDate(currentDateStr);
        birthDate = dateTimeFormatter.parseLocalDate(birthDateStr);

        Spinner calculateValueSpinner = findViewById(R.id.chooseCalculateValueSpinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.timeFrames, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        calculateValueSpinner.setAdapter(adapter);
        calculateValueSpinner.setOnItemSelectedListener(this);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private int getAgeInDays(LocalDate currentDate, LocalDate birthDate) {
        return Days.daysBetween(birthDate, currentDate).getDays();
    }

    private int getAgeInMonths(LocalDate currentDate, LocalDate birthDate) {
        return Months.monthsBetween(birthDate, currentDate).getMonths();
    }

    private int getAgeInWeeks(LocalDate currentDate, LocalDate birthDate) {
        return Weeks.weeksBetween(birthDate, currentDate).getWeeks();
    }

    private int getAgeInMinutes(LocalDate currentDate, LocalDate birthDate) {
        return Minutes.minutesBetween(birthDate, currentDate).getMinutes();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position) {
            case 0:
                calculatedAgeTextView.setText(Integer.toString(getAgeInDays(currentDate, birthDate)));
                break;
            case 1:
                calculatedAgeTextView.setText(Integer.toString(getAgeInMonths(currentDate, birthDate)));
                break;
            case 2:
                calculatedAgeTextView.setText(Integer.toString(getAgeInWeeks(currentDate, birthDate)));
                break;
            case 3:
                calculatedAgeTextView.setText(Integer.toString(getAgeInMinutes(currentDate, birthDate)));
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
