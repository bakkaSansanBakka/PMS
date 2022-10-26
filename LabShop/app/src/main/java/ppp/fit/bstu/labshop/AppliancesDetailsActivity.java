package ppp.fit.bstu.labshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ppp.fit.bstu.labshop.Models.Appliances;

public class AppliancesDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar mToolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Lab Shop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MaterialTextView isInStockTextViewDetailsActivity = findViewById(R.id.isInStockTextViewDetailsActivity);
        MaterialTextView nameTextFieldDetailsActivity = findViewById(R.id.nameTextFieldDetailsActivity);
        MaterialTextView priceTextFieldDetailsActivity = findViewById(R.id.priceTextFieldDetailsActivity);
        MaterialTextView categoryTextFieldDetailsActivity = findViewById(R.id.categoryTextFieldDetailsActivity);

        Appliances appliances = (Appliances) getIntent().getSerializableExtra("AppliancesItem");
        int position = (int) getIntent().getIntExtra("position", 0);

        if (appliances.IsInStock) {
            isInStockTextViewDetailsActivity.setText("In Stock");
        }
        else {
            isInStockTextViewDetailsActivity.setText("Not In Stock");
        }
        nameTextFieldDetailsActivity.setText(appliances.Name);
        priceTextFieldDetailsActivity.setText(Double.toString(appliances.Price));
        categoryTextFieldDetailsActivity.setText(appliances.Category);
        loadImageFromStorage(appliances.Image);

        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppliancesDetailsActivity.this, FirstForEditActivity.class);
                intent.putExtra("AppliancesItem", appliances);
                intent.putExtra("position", position);

                startActivity(intent);
            }
        });
    }

    private void loadImageFromStorage(String path)
    {
        try {
            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img = (ImageView)findViewById(R.id.imageViewDetailsActivity);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
