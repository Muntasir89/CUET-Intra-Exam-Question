package com.example.cuetintra_examquestion.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader;
import com.example.cuetintra_examquestion.R;
import com.example.cuetintra_examquestion.Util.PdfDownload;
import com.example.cuetintra_examquestion.Util.SQLiteUtil;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class ShowPDF extends AppCompatActivity {
    static SQLiteUtil objUtil;
    Toolbar toolbar;
    TextView toolbar_title;
    PDFView pdfView;
    String path = "", cust_title = "";
    CircularDotsLoader loader;
    static PdfDownload loadPdf;
    MenuItem itemBookmark;
    public ShowPDF() {
    }

    //FloatingActionButton Save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_pdf);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(getIntent().getStringExtra("title"));
        //SQLiteUtil object initialization
        objUtil = new SQLiteUtil(this);

        pdfView = findViewById(R.id.pdf_viewer);
        loader = findViewById(R.id.loading);

        //Save = findViewById(R.id.Save);

        if(getIntent() != null){
            path = getIntent().getStringExtra("path");
            LoadPDF();
        }
        /*Save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    private void LoadPDF() {
        loadPdf = (PdfDownload) new PdfDownload(path, pdfView, loader).execute();
    }

    /*private void SaveFile() {
        Dexter.withContext(ShowPDF.this).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                        } else {
                            Toast.makeText(ShowPDF.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home://Previous Activity
                this.finish();
                return true;
            case R.id.Refresh:
                LoadPDF();
                return true;
            case R.id.Bookmark:
                Bookmark();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Bookmark() {
        if(objUtil.getData(path)){
            if(objUtil.deleteData(path)){
                Toast.makeText(this, "Removed from bookmark", Toast.LENGTH_SHORT).show();
                itemBookmark.setIcon(R.drawable.ic_bookmark_white);
            }else{
                Toast.makeText(this, "Sorry, Could not remove from bookmark", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Bookmark");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Enter your file name");

            final EditText input = new EditText(ShowPDF.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("Enter here");

            alertDialog.setView(input);

            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cust_title = input.getText().toString();
                    if(!cust_title.matches("")){
                        if(objUtil.insertData(cust_title, path)){
                            Toast.makeText(ShowPDF.this, "Bookmarked this file", Toast.LENGTH_SHORT).show();
                            itemBookmark.setIcon(R.drawable.ic_bookmarked_white);
                            cust_title = "";
                        }else{
                            Toast.makeText(ShowPDF.this, ""+cust_title, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ShowPDF.this, "File name is empty", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.load_menu, menu);
        itemBookmark = menu.findItem(R.id.Bookmark);
        if(objUtil.getData(path))
            itemBookmark.setIcon(R.drawable.ic_bookmarked_white);
        else
            itemBookmark.setIcon(R.drawable.ic_bookmark_white);
        //first parameter is the file for icon and second one is menu
        return super.onCreateOptionsMenu(menu);
    }
}