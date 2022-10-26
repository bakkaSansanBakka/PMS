package ppp.fit.bstu.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textview.MaterialTextView;

public class SecondActivity extends AppCompatActivity {
    private Button backButton;
    private Button nextButton;

    private AutoCompleteTextView coverTypeAutoCompleteTextView;
    private AutoCompleteTextView genreAutoCompleteTextView;
    private AutoCompleteTextView languageAutoCompleteTextView;

    MaterialTextView nameTextView;
    MaterialTextView authorTextView;
    MaterialTextView publisherTextView;
    MaterialTextView priceTextView;

    private Comics comics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        coverTypeAutoCompleteTextView = findViewById(R.id.coverTypeAutoCompleteTextView);
        genreAutoCompleteTextView = findViewById(R.id.genreAutoCompleteTextView);
        languageAutoCompleteTextView = findViewById(R.id.languageAutoCompleteTextView);

        String[] coverTypes = getResources().getStringArray(R.array.coverTypes);
        String[] genres = getResources().getStringArray(R.array.genres);
        String[] languages = getResources().getStringArray(R.array.languages);

        ArrayAdapter<String> coverTypesArrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, coverTypes);
        coverTypeAutoCompleteTextView.setAdapter(coverTypesArrayAdapter);

        ArrayAdapter<String> genresArrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, genres);
        genreAutoCompleteTextView.setAdapter(genresArrayAdapter);

        ArrayAdapter<String> languagesArrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, languages);
        languageAutoCompleteTextView.setAdapter(languagesArrayAdapter);

        nameTextView = findViewById(R.id.nameTextFieldSecondActivity);
        authorTextView = findViewById(R.id.authorTextFieldSecondActivity);
        publisherTextView = findViewById(R.id.publisherTextFieldSecondActivity);
        priceTextView = findViewById(R.id.priceTextFieldSecondActivity);

        comics = (Comics) getIntent().getSerializableExtra("ComicsItem");

        nameTextView.setText(comics.Name);
        authorTextView.setText(comics.Author);
        publisherTextView.setText(comics.Publisher);
        priceTextView.setText(Double.toString(comics.Price));
        if (comics.CoverType != null && !comics.CoverType.equals("")) {
            coverTypeAutoCompleteTextView.setText(comics.CoverType);
        }
        if (comics.Genre != null && !comics.Genre.equals("")) {
            genreAutoCompleteTextView.setText(comics.Genre);
        }
        if (comics.Language != null && !comics.Language.equals("")) {
            languageAutoCompleteTextView.setText(comics.Language);
        }

        backButton = findViewById(R.id.goBackToFirstActivityButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comics.CoverType = coverTypeAutoCompleteTextView.getText().toString();
                comics.Genre = genreAutoCompleteTextView.getText().toString();
                comics.Language = languageAutoCompleteTextView.getText().toString();

                Intent intent = new Intent(SecondActivity.this, FirstActivity.class);
                intent.putExtra("ComicsItem", comics);

                startActivity(intent);
            }
        });

        nextButton = findViewById(R.id.goToThirdActivityButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comics.CoverType = coverTypeAutoCompleteTextView.getText().toString();
                comics.Genre = genreAutoCompleteTextView.getText().toString();
                comics.Language = languageAutoCompleteTextView.getText().toString();

                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra("ComicsItem", comics);

                startActivity(intent);
            }
        });
    }
}