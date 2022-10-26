package ppp.fit.bstu.labshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ppp.fit.bstu.labshop.Models.Appliances;
import ppp.fit.bstu.labshop.Utils.BitmapConverter;

public class SecondForEditActivity extends AppCompatActivity {
    int SELECT_PICTURE = 200;
    Bitmap selectedImageBitmap;
    Uri selectedImageUri;

    private Appliances appliances;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_edit_second);

        Toolbar mToolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Lab Shop");

        imageView = findViewById(R.id.imageViewSecondActivity);

        appliances = (Appliances) getIntent().getSerializableExtra("AppliancesItem");
        loadImageFromStorage(appliances.Image);

        int position = (int) getIntent().getIntExtra("position", 0);

        Button backButton = findViewById(R.id.goBackToFirstActivityButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondForEditActivity.this, FirstForEditActivity.class);
                intent.putExtra("AppliancesItem", appliances);
                intent.putExtra("position", position);

                startActivity(intent);
            }
        });

        Button nextButton = findViewById(R.id.goToFinalActivityButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImageBitmap != null) {
                    String imageName = getFileName(selectedImageUri);
                    appliances.Image = saveImageToInternalStorageAndGetImagePath(selectedImageBitmap, imageName);
                }

                Intent intent = new Intent(SecondForEditActivity.this, FinalForEditActivity.class);
                intent.putExtra("AppliancesItem", appliances);
                intent.putExtra("position", position);

                startActivity(intent);
            }
        });

        Button selectImageButton = findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
    }

    private void loadImageFromStorage(String path)
    {
        try {
            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img = (ImageView)findViewById(R.id.imageViewSecondActivity);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    void chooseImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (null != selectedImageBitmap) {
                    imageView.setImageBitmap(selectedImageBitmap);
                }
            }
        }
    }

    private String saveImageToInternalStorageAndGetImagePath(Bitmap bitmapImage, String imageFileName){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, imageFileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.toString();
    }

    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
