package com.mhdarslan.jpgtopdfconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    Button converterBtn;
    private String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/myCamera/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        converterBtn = findViewById(R.id.converterBtn);

        ActivityCompat.requestPermissions(this,new String[] {WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        converterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file = directory + "123.jpg";
                Bitmap bitmap = BitmapFactory.decodeFile(file);

                PdfDocument pdfDocument = new PdfDocument();
                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(2560,1920,1).create();
                PdfDocument.Page page = pdfDocument.startPage(myPageInfo);

                page.getCanvas().drawBitmap(bitmap,0,0,null);
                pdfDocument.finishPage(page);

                String pdfFile = directory + "/myPDFFILE.pdf";
                File myPDFFILE = new File(pdfFile);
                try {
                    pdfDocument.writeTo(new FileOutputStream(myPDFFILE));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pdfDocument.close();
            }
        });

    }
}