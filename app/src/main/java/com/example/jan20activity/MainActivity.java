package com.example.jan20activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    static final int READ_BLOCK_SIZE = 100;

    private String filename = "Delima.txt";
    private String filepath = "Delima_ExternalIO";
    File myFile;
    String myInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText txtbox = findViewById(R.id.txtbox);
        final Button btnClr = findViewById(R.id.btnClr);
        final Button btnRea = findViewById(R.id.btnRea);
        final Button btnWri = findViewById(R.id.btnWri);

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            btnWri.setEnabled(false);
        }else {
            myFile = new File(getExternalFilesDir(filepath), filename);
        }

        btnClr.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtbox.setText("");
                    }
                }
        );

        btnRea.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileInputStream fis = new FileInputStream(myFile);
                            DataInputStream in = new DataInputStream(fis);
                            BufferedReader br =
                                    new BufferedReader(new InputStreamReader(in));
                            String strLine;
                            while ((strLine = br.readLine()) != null) {
                                myInput = myInput + strLine;
                            }
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        txtbox.setText(myInput);
                        Toast.makeText(getBaseContext(), "File data retrieved from External Storage.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        btnWri.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileOutputStream fos = new FileOutputStream(myFile);
                            fos.write(txtbox.getText().toString().getBytes());
                            fos.close();
                            Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            btnWri.setEnabled(false);
        }else {
            myFile = new File(getExternalFilesDir(filepath), filename);
        }
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}