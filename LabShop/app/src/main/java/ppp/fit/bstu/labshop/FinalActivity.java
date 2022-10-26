package ppp.fit.bstu.labshop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ppp.fit.bstu.labshop.Models.Appliances;
import ppp.fit.bstu.labshop.Utils.BitmapConverter;

public class FinalActivity extends AppCompatActivity {
    private Appliances appliances;
    String fileName = "appliancesList.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Toolbar mToolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Lab Shop");

        MaterialTextView nameTextView = findViewById(R.id.nameTextFieldFinalActivity);;
        MaterialTextView priceTextView = findViewById(R.id.priceTextFieldFinalActivity);
        MaterialTextView categoryTextView = findViewById(R.id.categoryTextFieldFinalActivity);
        MaterialTextView isInStockTextView = findViewById(R.id.isInStockTextViewFinalActivity);

        appliances = (Appliances) getIntent().getSerializableExtra("AppliancesItem");

        nameTextView.setText(appliances.Name);
        priceTextView.setText(Double.toString(appliances.Price));
        categoryTextView.setText(appliances.Category);
        if (appliances.IsInStock) {
            isInStockTextView.setText("In Stock");
        }
        else {
            isInStockTextView.setText("Not In Stock");
        }
        loadImageFromStorage(appliances.Image);

        Button backButton = findViewById(R.id.goBackToSecondActivityButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalActivity.this, SecondActivity.class);
                intent.putExtra("AppliancesItem", appliances);

                startActivity(intent);
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Appliances> appliancesArrayList = readFromFile(getApplicationContext(), fileName);

                appliancesArrayList.add(appliances);

                String comicsArrayListJSONString = new Gson().toJson(appliancesArrayList);
                writeToFile(getApplicationContext(), comicsArrayListJSONString, fileName);

                Intent intent = new Intent(FinalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadImageFromStorage(String path)
    {
        try {
            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img = (ImageView)findViewById(R.id.imageViewFinalActivity);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    private void writeToFile(Context context, String data, String fileName) {
        File dir = new File(context.getFilesDir(), "labShop");
        if(!dir.exists()){
            dir.mkdir();
        }
        try {
            File file = new File(dir, fileName);
            FileWriter writer = new FileWriter(file);
            writer.append(data);
            writer.flush();
            writer.close();
            Toast.makeText(getApplicationContext(),"Save to JSON format success", Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private ArrayList<Appliances> readFromFile(Context context, String fileName) {
        String jsonString = "";
        ArrayList<Appliances> appliancesArrayList = new ArrayList<>();
        File dir = new File(context.getFilesDir(), "labShop");
        if(dir.exists()){
            try {
                File file = new File(dir, fileName);
                FileInputStream fis = new FileInputStream(file);
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    jsonString = jsonString + strLine;
                }
                in.close();

                Gson gson = new Gson();
                Type listComicsType = new TypeToken<ArrayList<Appliances>>() { }.getType();

                appliancesArrayList = gson.fromJson(jsonString, listComicsType);
            } catch (IOException e) {
                Log.e("Exception", "File read failed: " + e.toString());
            }
        }

        return appliancesArrayList;
    }
}
