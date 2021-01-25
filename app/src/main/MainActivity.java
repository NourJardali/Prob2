package com.example.prob2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saveButton = findViewById(R.id.saveData);

        if (checkPermissions()) {
            saveButton.setEnabled(false);
        }

    }



    private boolean checkPermissions(){
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for(String p : permissions){
            result = ContextCompat.checkSelfPermission(this, p);
            if(result != PackageManager.PERMISSION_GRANTED){
                listPermissionsNeeded.add(p);
            }
        }
        if(!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            //cheking if the permission is granted
            if(grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                //permission not granted
                //Display your message to let the user know that it requires permission to access the app
                Toast.makeText(this, "You denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }



    public void saveData(View view) {
        EditText filename = findViewById(R.id.etFilename);
        EditText data = findViewById(R.id.etData);
        try {
            File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            if(!folder.exists())
                folder.mkdirs();
            File myFile = new File(folder, filename.getText().toString());
            myFile.createNewFile();
            FileOutputStream fstream = new FileOutputStream(myFile);
            fstream.write(data.getText().toString().getBytes());
            fstream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayData(View view) {
        Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
        startActivity(intent);
    }
}