package com.example.shara.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shara on 2/6/2017.
 */

public class SetimageAsync extends AsyncTask<String, Void, Bitmap> {

    IData mainActivity;

    public SetimageAsync(IData mainActivity) {
        this.mainActivity = mainActivity;
    }

    public static interface IData {
        public void setupImage(Bitmap bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(params[0]);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            bitmap = BitmapFactory.decodeStream(con.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        mainActivity.setupImage(bitmap);
    }
}
