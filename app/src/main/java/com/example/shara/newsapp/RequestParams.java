package com.example.shara.newsapp;

import android.os.AsyncTask;
import android.util.Xml;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by shara on 2/6/2017.
 */

public class RequestParams {

    String baseUrl, method;
    HashMap<String,String> params = new HashMap<String,String>();

    public RequestParams(String baseUrl, String method, HashMap<String, String> params) {
        this.baseUrl = baseUrl;
        this.method = method;
        this.params = params;
    }

    public HttpURLConnection setupConnection() throws IOException{
        HttpURLConnection con;
        if(method.equals("GET")){
            URL url = new URL(getEncodedUrl());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        }
        else{
            con = null;
        }

        return con;
    }

    public void addParam(String key, String value){
        params.put(key, value);
    }

    public String getEncodedParams(){
        StringBuilder sb = new StringBuilder();
        for(String key: params.keySet()){
            try {
                String value = URLEncoder.encode(params.get(key), Xml.Encoding.UTF_8.toString());
                if (sb.length()>0){
                    sb.append("&");
                }
                sb.append(key+"="+value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String getEncodedUrl(){
        return this.baseUrl+"?"+getEncodedParams();
    }
}
