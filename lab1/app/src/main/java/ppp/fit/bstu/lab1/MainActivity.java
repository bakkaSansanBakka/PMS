package ppp.fit.bstu.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ppp.fit.bstu.lab1.package1.*;

public class MainActivity extends AppCompatActivity {

    // TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int count = 0; count < 10; count++) {
            Log.d("MainActivity", " counter = " + count);
        }
        displayText();
    }

    private void displayText() {
        Text text = new Text();
        TextView newTest = findViewById(R.id.newtest);
        newTest.setText(text.getValue());
    }
}