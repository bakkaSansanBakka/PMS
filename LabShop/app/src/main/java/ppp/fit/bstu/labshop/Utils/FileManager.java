package ppp.fit.bstu.labshop.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ppp.fit.bstu.labshop.Models.Appliances;

public class FileManager {
    public static void writeToFile(Context context, String data, String fileName) {
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
            Toast.makeText(context,"Save to JSON format success", Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static ArrayList<Appliances> readFromFile(Context context, String fileName) {
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
