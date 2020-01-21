package com.example.jan20activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.os.Environment;

public class MainActivity extends AppCompatActivity {
    static final int READ_BLOCK_SIZE = 100;

    private String filename="delima.txt";
    private String filepath="delima_externalio";
    File akongFile;
    String akongGitype="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText txtbox = findViewById(R.id.txtbox);
        final Button btnClr = findViewById(R.id.btnClr);
        final Button btnRea = findViewById(R.id.btnRea);
        final Button btnWri = findViewById(R.id.btnWri);

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
                            FileInputStream fileIn=openFileInput("mytextfile.txt");
                            InputStreamReader InputRead= new InputStreamReader(fileIn);

                            char[] inputBuffer= new char[READ_BLOCK_SIZE];
                            String s="";
                            int charRead;

                            while ((charRead=InputRead.read(inputBuffer))>0) {
                                // char to string conversion
                                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                                s +=readstring;
                            }
                            InputRead.close();
                            txtbox.setText(s);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
        );

        btnWri.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            FileOutputStream fos = new FileOutputStream(akongFile);
                            fos.write(txtbox.getText().toString().getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getBaseContext(), "File saved successfully!",
                                Toast.LENGTH_SHORT).show();

                    }
                }
        );

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            btnWri.setEnabled(false);
        }

    }

    private static boolean isExternalStorageReadOnly() {
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
