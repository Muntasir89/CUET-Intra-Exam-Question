package com.example.cuetintra_examquestion.Util;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.view.View;

import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PdfDownload extends AsyncTask<String, Void, InputStream> implements OnLoadCompleteListener{
    String Url;
    PDFView pdfView;
    CircularDotsLoader loading;
    public PdfDownload(String url,PDFView pdf, CircularDotsLoader loader) {
        Url = url;
        pdfView = pdf;
        loading = loader;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    protected InputStream doInBackground(String... strings) {
        InputStream inputStream = null;

        try {
            URL url = new URL(Url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if(urlConnection.getResponseCode() == 200){
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    @Override
    protected void onPostExecute(InputStream inputStream){
        pdfView.fromStream(inputStream).onLoad(this).load();
    }

    @Override
    public void loadComplete(int nbPages) {
        loading.setVisibility(View.INVISIBLE);
    }
}
