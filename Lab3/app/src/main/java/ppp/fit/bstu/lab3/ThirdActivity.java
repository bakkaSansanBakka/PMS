package ppp.fit.bstu.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class ThirdActivity extends AppCompatActivity {
    private Button backButton;
    private Button nextButton;

    private MaterialTextView nameTextView;
    private MaterialTextView authorTextView;
    private MaterialTextView publisherTextView;
    private MaterialTextView priceTextView;
    private MaterialTextView coverTypeTextView;
    private MaterialTextView genreTextView;
    private MaterialTextView languageTextView;
    private TextInputLayout pagesNumberInput;
    private TextInputLayout yearOfPublicationInput;

    private Comics comics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        nameTextView = findViewById(R.id.nameTextFieldThirdActivity);
        authorTextView = findViewById(R.id.authorTextFieldThirdActivity);
        publisherTextView = findViewById(R.id.publisherTextFieldThirdActivity);
        priceTextView = findViewById(R.id.priceTextFieldThirdActivity);
        coverTypeTextView = findViewById(R.id.coverTypeTextFieldThirdActivity);
        genreTextView = findViewById(R.id.genreTextFieldThirdActivity);
        languageTextView = findViewById(R.id.languageTextFieldThirdActivity);
        pagesNumberInput = findViewById(R.id.pagesNumberInput);
        yearOfPublicationInput = findViewById(R.id.yearOfPublicationInput);

        comics = (Comics) getIntent().getSerializableExtra("ComicsItem");

        nameTextView.setText(comics.Name);
        authorTextView.setText(comics.Author);
        publisherTextView.setText(comics.Publisher);
        priceTextView.setText(Double.toString(comics.Price));
        coverTypeTextView.setText(comics.CoverType);
        genreTextView.setText(comics.Genre);
        languageTextView.setText(comics.Language);
        if (comics.PagesNumber != null) {
            pagesNumberInput.getEditText().setText(Integer.toString(comics.PagesNumber));
        }
        if (comics.YearOfPublication != null) {
            yearOfPublicationInput.getEditText().setText(Integer.toString(comics.YearOfPublication));
        }

        backButton = findViewById(R.id.goBackToSecondActivityButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pagesNumberInput.getEditText().getText().toString().equals("")) {
                    comics.PagesNumber = Integer.parseInt(pagesNumberInput.getEditText().getText().toString());
                }
                if (!yearOfPublicationInput.getEditText().getText().toString().equals("")) {
                    comics.YearOfPublication = Integer.parseInt(yearOfPublicationInput.getEditText().getText().toString());
                }

                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                intent.putExtra("ComicsItem", comics);

                startActivity(intent);
            }
        });

        nextButton = findViewById(R.id.goToFinalActivityButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comics.PagesNumber = Integer.parseInt(pagesNumberInput.getEditText().getText().toString());
                comics.YearOfPublication = Integer.parseInt(yearOfPublicationInput.getEditText().getText().toString());

                Intent intent = new Intent(ThirdActivity.this, FinalActivity.class);
                intent.putExtra("ComicsItem", comics);

                startActivity(intent);
            }
        });
    }
}