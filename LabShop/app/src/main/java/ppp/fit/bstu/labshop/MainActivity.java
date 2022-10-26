package ppp.fit.bstu.labshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

import ppp.fit.bstu.labshop.Models.Appliances;
import ppp.fit.bstu.labshop.Utils.ItemClickListener;
import ppp.fit.bstu.labshop.Utils.ListViewAdapter;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    private Toolbar mToolbar;
    private ArrayList<Appliances> appliancesArrayList;
    String fileName = "appliancesList.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Lab Shop");

        appliancesArrayList = readFromFile(getApplicationContext(), fileName);

        RecyclerView recyclerView = findViewById(R.id.appliancesListView);
        if (appliancesArrayList.size() > 0) {
            ListViewAdapter adapter = new ListViewAdapter(this, appliancesArrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter);
            adapter.setClickListener(this);
            ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        }
        else {
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_item) {
            Intent intent = new Intent(MainActivity.this, FirstActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position) {
        final Appliances appliancesItem = appliancesArrayList.get(position);
        Intent intent = new Intent(this, AppliancesDetailsActivity.class);
        intent.putExtra("AppliancesItem", appliancesItem);
        intent.putExtra("position", position);
        startActivity(intent);
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