package com.example.mike.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DownloadService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String pdfLocation = "http://www.etgenealogy.se/sag/3-10-links.pdf";
        File pdfFilePath = createFilePath();
        downloadFile(pdfLocation, pdfFilePath);

        return super.onStartCommand(intent, flags, startId);
    }

    private File createFilePath(){

        String fileName = "myPdf.pdf";
        File filesDir = getBaseContext().getFilesDir();
        return new File(filesDir, fileName);

    }


    private void downloadFile(String fileName, File directory){

        int MEGABYTE = 1024 * 1024;

        URL url = null;
        try {
            url = new URL(fileName);
            HttpURLConnection httpURLConnection =
                    (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);

            int contentLength = httpURLConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;

            while((bufferLength = inputStream.read(buffer)) > 0){
                fileOutputStream.write(buffer, 0, bufferLength);
            }

            fileOutputStream.close();
            httpURLConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
