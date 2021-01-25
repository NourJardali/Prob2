package com.example.prob2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
    }

    public void show(View view) {
        EditText editText = findViewById(R.id.etFilename);
        String FILENAME = editText.getText().toString();
        File folder = Environment.getExternalStorageDirectory();
        File myFile = new File(folder, FILENAME);
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(myFile);
            StringBuffer sbuffer = new StringBuffer();
            int i;
            while ((i = fstream.read())!= -1){
                sbuffer.append((char)i);
            }
            fstream.close();
            TextView textView = findViewById(R.id.data);
            textView.setText(sbuffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File does not exist", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}