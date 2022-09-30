package ppp.fit.bstu.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private Button nextButton;
    private TextInputLayout nameInput;
    private TextInputLayout authorInput;
    private TextInputLayout publisherInput;
    private TextInputLayout priceInput;

    private Comics comics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.goToSecondActivityButton);
        nameInput = findViewById(R.id.nameInput);
        authorInput = findViewById(R.id.authorInput);
        publisherInput = findViewById(R.id.publisherInput);
        priceInput = findViewById(R.id.priceInput);

        comics = (Comics) getIntent().getSerializableExtra("ComicsItem");
        if (comics == null) {
            comics = new Comics();
        }
        else {
            nameInput.getEditText().setText(comics.Name);
            authorInput.getEditText().setText(comics.Author);
            publisherInput.getEditText().setText(comics.Publisher);
            priceInput.getEditText().setText(Double.toString(comics.Price));
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comics.Name = nameInput.getEditText().getText().toString();
                comics.Author = authorInput.getEditText().getText().toString();
                comics.Publisher = publisherInput.getEditText().getText().toString();
                comics.Price = Double.parseDouble(priceInput.getEditText().getText().toString());

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("ComicsItem", comics);

                startActivity(intent);
            }
        });
    }
}