package com.example.shara.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements FetchNews.IData, SetimageAsync.IData {

    ArrayList<News> news;
    int newsCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner newsSourceSpinner = (Spinner) findViewById(R.id.newsSourceSpinner);

        findViewById(R.id.getNewsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int getSelectedNewsItem = newsSourceSpinner.getSelectedItemPosition();
                String newsUrl = getResources().getString(R.string.getNewsBaseURL);
                HashMap<String, String> urlKeyValues = new HashMap<String, String>();

                urlKeyValues.put(getResources().getString(R.string.ApiKey), getResources().getString(R.string.MyApiKey));
                urlKeyValues.put(getResources().getString(R.string.SourceKey), getResources().getStringArray(R.array.newsSourceArrayValues)[getSelectedNewsItem]);

                RequestParams params = new RequestParams(newsUrl, getResources().getString(R.string.GetMethod), urlKeyValues);
//Add progress dialog and start
                new FetchNews(MainActivity.this).execute(params);
            }
        });

        findViewById(R.id.imageButtonFirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Close progress dialog
                LinearLayout newsLayout = (LinearLayout) findViewById(R.id.newsDetailsLinearLayout);

                newsLayout.removeAllViews();

                TextView newsText = new TextView(MainActivity.this);

                StringBuilder sb = new StringBuilder();

                sb.append(getResources().getString(R.string.NewsText) + news.get(0).getTitle() + "\n");
                sb.append(getResources().getString(R.string.AuthorText) + news.get(0).getAuthor() + "\n");
                sb.append(getResources().getString(R.string.Published) + news.get(0).getPublishedAt() + "\n");
                sb.append(getResources().getString(R.string.Description) + news.get(0).getDescription() + "\n");

                newsText.setText(sb.toString());
                newsLayout.addView(newsText);

                new SetimageAsync(MainActivity.this).execute(news.get(0).getUrlToImage());
                newsCounter = 0;
            }
        });
        findViewById(R.id.imageButtonLast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastCount = news.size() - 1;
                //Close progress dialog
                LinearLayout newsLayout = (LinearLayout) findViewById(R.id.newsDetailsLinearLayout);

                newsLayout.removeAllViews();

                TextView newsText = new TextView(MainActivity.this);

                StringBuilder sb = new StringBuilder();

                sb.append(getResources().getString(R.string.NewsText) + news.get(lastCount).getTitle() + "\n");
                sb.append(getResources().getString(R.string.AuthorText) + news.get(lastCount).getAuthor() + "\n");
                sb.append(getResources().getString(R.string.Published) + news.get(lastCount).getPublishedAt() + "\n");
                sb.append(getResources().getString(R.string.Description) + news.get(lastCount).getDescription() + "\n");

                newsText.setText(sb.toString());
                newsLayout.addView(newsText);

                new SetimageAsync(MainActivity.this).execute(news.get(lastCount).getUrlToImage());
                newsCounter = lastCount;
            }
        });
        findViewById(R.id.imageButtonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                newsCounter++;

                if (newsCounter < news.size()) {
                    //Close progress dialog
                    LinearLayout newsLayout = (LinearLayout) findViewById(R.id.newsDetailsLinearLayout);

                    newsLayout.removeAllViews();

                    TextView newsText = new TextView(MainActivity.this);

                    StringBuilder sb = new StringBuilder();

                    sb.append(getResources().getString(R.string.NewsText) + news.get(newsCounter).getTitle() + "\n");
                    sb.append(getResources().getString(R.string.AuthorText) + news.get(newsCounter).getAuthor() + "\n");
                    sb.append(getResources().getString(R.string.Published) + news.get(newsCounter).getPublishedAt() + "\n");
                    sb.append(getResources().getString(R.string.Description) + news.get(newsCounter).getDescription() + "\n");

                    newsText.setText(sb.toString());
                    newsLayout.addView(newsText);

                    new SetimageAsync(MainActivity.this).execute(news.get(newsCounter).getUrlToImage());
                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.MaxLimitReached), Toast.LENGTH_LONG).show();
                    newsCounter--;
                }
            }
        });
        findViewById(R.id.imageButtonPrevious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsCounter--;
                if (newsCounter >= 0) {
                    //Close progress dialog
                    LinearLayout newsLayout = (LinearLayout) findViewById(R.id.newsDetailsLinearLayout);

                    newsLayout.removeAllViews();

                    TextView newsText = new TextView(MainActivity.this);

                    StringBuilder sb = new StringBuilder();

                    sb.append(getResources().getString(R.string.NewsText) + news.get(newsCounter).getTitle() + "\n");
                    sb.append(getResources().getString(R.string.AuthorText) + news.get(newsCounter).getAuthor() + "\n");
                    sb.append(getResources().getString(R.string.Published) + news.get(newsCounter).getPublishedAt() + "\n");
                    sb.append(getResources().getString(R.string.Description) + news.get(newsCounter).getDescription() + "\n");

                    newsText.setText(sb.toString());
                    newsLayout.addView(newsText);

                    new SetimageAsync(MainActivity.this).execute(news.get(newsCounter).getUrlToImage());
                } else {
                    newsCounter++;Toast.makeText(MainActivity.this, getResources().getString(R.string.MinLimitReached), Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.finishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public void setupData(ArrayList<News> news) {

        this.news = news;
        //Close progress dialog
        LinearLayout newsLayout = (LinearLayout) findViewById(R.id.newsDetailsLinearLayout);

        TextView newsText = new TextView(this);

        StringBuilder sb = new StringBuilder();

        sb.append(getResources().getString(R.string.NewsText) + news.get(0).getTitle() + "\n");
        sb.append(getResources().getString(R.string.AuthorText) + news.get(0).getAuthor() + "\n");
        sb.append(getResources().getString(R.string.Published) + news.get(0).getPublishedAt() + "\n");
        sb.append(getResources().getString(R.string.Description) + news.get(0).getDescription() + "\n");

        newsText.setText(sb.toString());
        newsLayout.addView(newsText);

        new SetimageAsync(MainActivity.this).execute(news.get(0).getUrlToImage());
    }

    public void setupImage(Bitmap bitmap) {
        ImageView image = (ImageView) findViewById(R.id.downloadedImage);
        image.setImageBitmap(bitmap);
    }
}
