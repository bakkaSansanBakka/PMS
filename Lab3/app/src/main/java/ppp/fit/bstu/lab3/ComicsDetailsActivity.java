package ppp.fit.bstu.lab3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ComicsDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView nameTextView = findViewById(R.id.nameTextFieldDetailsActivity);
        TextView authorTextView = findViewById(R.id.authorTextFieldDetailsActivity);
        TextView publisherTextView = findViewById(R.id.publisherTextFieldDetailsActivity);
        TextView priceTextView = findViewById(R.id.priceTextFieldDetailsActivity);
        TextView coverTypeTextView = findViewById(R.id.coverTypeTextFieldDetailsActivity);
        TextView genreTextView = findViewById(R.id.genreTextFieldDetailsActivity);
        TextView languageTextView = findViewById(R.id.languageTextFieldDetailsActivity);
        TextView pagesNumberTextView = findViewById(R.id.pagesNumberTextFieldDetailsActivity);
        TextView yearOfPublicationTextView = findViewById(R.id.yearOfPublicationTextFieldDetailsActivity);

        Comics comics = (Comics) getIntent().getSerializableExtra("Comics");

        nameTextView.setText(comics.Name);
        authorTextView.setText(comics.Author);
        publisherTextView.setText(comics.Publisher);
        priceTextView.setText(Double.toString(comics.Price));
        coverTypeTextView.setText(comics.CoverType);
        genreTextView.setText(comics.Genre);
        languageTextView.setText(comics.Language);
        pagesNumberTextView.setText(Integer.toString(comics.PagesNumber));
        yearOfPublicationTextView.setText(Integer.toString(comics.YearOfPublication));
    }
}
