package ppp.fit.bstu.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FinalActivity extends AppCompatActivity {
    private Button backButton;
    private Button saveButton;

    private MaterialTextView nameTextView;
    private MaterialTextView authorTextView;
    private MaterialTextView publisherTextView;
    private MaterialTextView priceTextView;
    private MaterialTextView coverTypeTextView;
    private MaterialTextView genreTextView;
    private MaterialTextView languageTextView;
    private MaterialTextView pagesNumberTextView;
    private MaterialTextView yearOfPublicationTextView;

    private Comics comics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        nameTextView = findViewById(R.id.nameTextFieldFinalActivity);
        authorTextView = findViewById(R.id.authorTextFieldFinalActivity);
        publisherTextView = findViewById(R.id.publisherTextFieldFinalActivity);
        priceTextView = findViewById(R.id.priceTextFieldFinalActivity);
        coverTypeTextView = findViewById(R.id.coverTypeTextFieldFinalActivity);
        genreTextView = findViewById(R.id.genreTextFieldFinalActivity);
        languageTextView = findViewById(R.id.languageTextFieldFinalActivity);
        pagesNumberTextView = findViewById(R.id.pagesNumberTextFieldFinalActivity);
        yearOfPublicationTextView = findViewById(R.id.yearOfPublicationTextFieldFinalActivity);

        comics = (Comics) getIntent().getSerializableExtra("ComicsItem");

        nameTextView.setText(comics.Name);
        authorTextView.setText(comics.Author);
        publisherTextView.setText(comics.Publisher);
        priceTextView.setText(Double.toString(comics.Price));
        coverTypeTextView.setText(comics.CoverType);
        genreTextView.setText(comics.Genre);
        languageTextView.setText(comics.Language);
        pagesNumberTextView.setText(Integer.toString(comics.PagesNumber));
        yearOfPublicationTextView.setText(Integer.toString(comics.YearOfPublication));

        backButton = findViewById(R.id.goBackToThirdActivityButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalActivity.this, ThirdActivity.class);
                intent.putExtra("ComicsItem", comics);

                startActivity(intent);
            }
        });

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "comicsList.json";
                ArrayList<Comics> comicsArrayList = readFromFile(getApplicationContext(), fileName);

                comicsArrayList.add(comics);

                String comicsArrayListJSONString = new Gson().toJson(comicsArrayList);
                writeToFile(getApplicationContext(), comicsArrayListJSONString, fileName);

                Intent intent = new Intent(FinalActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }

    private void writeToFile(Context context, String data, String fileName) {
        File dir = new File(context.getFilesDir(), "lab3");
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

    private ArrayList<Comics> readFromFile(Context context, String fileName) {
        String jsonString = "";
        ArrayList<Comics> comicsArrayList = new ArrayList<>();
        File dir = new File(context.getFilesDir(), "lab3");
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
                Type listComicsType = new TypeToken<ArrayList<Comics>>() { }.getType();

                comicsArrayList = gson.fromJson(jsonString, listComicsType);
            } catch (IOException e) {
                Log.e("Exception", "File read failed: " + e.toString());
            }
        }

        return comicsArrayList;
    }

    @Override
    public void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        comics = (Comics) savedInstanceState.getSerializable("comicsItem");
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("comicsItem", comics);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}