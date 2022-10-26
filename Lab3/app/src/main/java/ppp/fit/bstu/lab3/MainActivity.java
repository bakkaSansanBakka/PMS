package ppp.fit.bstu.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ppp.fit.bstu.lab3.utils.ItemClickListener;
import ppp.fit.bstu.lab3.utils.ListViewAdapter;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    private ArrayList<Comics> comicsArrayList;
    String fileName = "comicsList.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comicsArrayList = readFromFile(getApplicationContext(), fileName);

        RecyclerView recyclerView = findViewById(R.id.comicsListView);
        if (comicsArrayList.size() > 0) {
            ListViewAdapter adapter = new ListViewAdapter(this, comicsArrayList);
            recyclerView.setAdapter(adapter);
            adapter.setClickListener(this);
            ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        }
        else {
            recyclerView.setVisibility(View.INVISIBLE);
        }

        Button createNewComicsButton = findViewById(R.id.createNewComicsButton);
        createNewComicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });

        Button moreButton = findViewById(R.id.moreButton);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        final Comics comicsItem = comicsArrayList.get(position);
        Intent intent = new Intent(this, ComicsDetailsActivity.class);
        intent.putExtra("Comics", comicsItem);
        startActivity(intent);
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
}