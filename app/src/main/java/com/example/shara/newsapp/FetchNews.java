package com.example.shara.newsapp;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by shara on 2/6/2017.
 */

public class FetchNews extends AsyncTask<RequestParams, Void, ArrayList<News>> {

    IData mainActivity;

    public FetchNews(IData mainActivity) {
        this.mainActivity = mainActivity;
    }

    public static interface IData {
        public void setupData(ArrayList<News> news);
    }

    @Override
    protected ArrayList<News> doInBackground(RequestParams... params) {

        BufferedReader reader = null;

        try {
            HttpURLConnection con = params[0].setupConnection();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return NewsUtil.NewsJSONParser.parseNews(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<News> newsList) {
        mainActivity.setupData(newsList);
    }
}
