package com.example.shara.newsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by shara on 2/6/2017.
 */

public class NewsUtil {
    static public class NewsJSONParser{
        static ArrayList<News> parseNews(String in) throws JSONException, ParseException {
            ArrayList<News> newsList = new ArrayList<>();
            JSONObject root = new JSONObject(in);
            JSONArray newsJSONArray=root.getJSONArray("articles");
            for(int count=0;count<newsJSONArray.length();count++){
                JSONObject newsJSONObject = newsJSONArray.getJSONObject(count);
                        News news=new News();
                news =  news.createFromJSON(newsJSONObject);
                newsList.add(news);
            }

            return newsList;
        }
    }
}
