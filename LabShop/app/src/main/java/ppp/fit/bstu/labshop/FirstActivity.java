package ppp.fit.bstu.labshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import ppp.fit.bstu.labshop.Models.Appliances;

public class FirstActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Appliances appliances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        mToolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Lab Shop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RadioButton notInStockRadioButton = findViewById(R.id.notInStockRadioButton);
        RadioButton inStockRadioButton = findViewById(R.id.inStockRadioButton);
        inStockRadioButton.setChecked(true);

        TextInputLayout nameInput = findViewById(R.id.nameInput);
        TextInputLayout priceInput = findViewById(R.id.priceInput);

        AutoCompleteTextView categoryAutoCompleteTextView = findViewById(R.id.categoryAutoCompleteTextView);
        String[] categories = getResources().getStringArray(R.array.categories);

        ArrayAdapter<String> categoriesArrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, categories);
        categoryAutoCompleteTextView.setAdapter(categoriesArrayAdapter);

        appliances = (Appliances) getIntent().getSerializableExtra("AppliancesItem");
        if (appliances == null) {
            appliances = new Appliances();
        }
        else {
            nameInput.getEditText().setText(appliances.Name);
            priceInput.getEditText().setText(Double.toString(appliances.Price));
            if (appliances.Category != null && !appliances.Category.equals("")) {
                categoryAutoCompleteTextView.setText(appliances.Category);
            }
            if (!appliances.IsInStock) {
                notInStockRadioButton.setChecked(true);
            }
        }

        Button nextButton = findViewById(R.id.goToSecondActivityButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appliances.Name = nameInput.getEditText().getText().toString();
                appliances.Price = Double.parseDouble(priceInput.getEditText().getText().toString());
                appliances.Category = categoryAutoCompleteTextView.getText().toString();
                if (inStockRadioButton.isChecked()) {
                    appliances.IsInStock = true;
                }
                else {
                    appliances.IsInStock = false;
                }

                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra("AppliancesItem", appliances);

                startActivity(intent);
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.inStockRadioButton:
                if (checked) {
                    appliances.IsInStock = true;
                }
                break;
            case R.id.notInStockRadioButton:
                if (checked){
                    appliances.IsInStock = false;
                }
                break;
        }
    }
}
