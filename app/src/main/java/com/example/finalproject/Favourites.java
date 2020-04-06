package com.example.finalproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {
    ListView list;
    ArrayList<NewsResponse> newsResponseList = new ArrayList<>();
    ListAdapter adapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);

        list = findViewById(R.id.newsList);
        loadDataFromDatabase();

        adapter = new ListAdapter();
        list.setAdapter(adapter);


    }


    private void loadDataFromDatabase() {
        DatabaseOpener dbOpener = new DatabaseOpener(this);
        db = dbOpener.getWritableDatabase();

        String[] columns = {DatabaseOpener.COL_ID, DatabaseOpener.COL_TITLE, DatabaseOpener.COL_DESCRIPTION, DatabaseOpener.COL_URL};

        Cursor results = db.query(false, DatabaseOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int idColIndex = results.getColumnIndex(DatabaseOpener.COL_ID);
        int titleColumnIndex = results.getColumnIndex(DatabaseOpener.COL_TITLE);
        int descriptionColumnIndex = results.getColumnIndex(DatabaseOpener.COL_DESCRIPTION);
        int urlColumnIndex = results.getColumnIndex(DatabaseOpener.COL_URL);


        while (results.moveToNext())
        {
            long id = results.getLong(idColIndex);
            String title = results.getString(titleColumnIndex);
            String description = results.getString(descriptionColumnIndex);
            String url = results.getString(urlColumnIndex);

            newsResponseList.add(new NewsResponse(id, title, description, url));

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showNews(position);
                }
            });
        }
    }


    protected void showNews(int position) {
        NewsResponse selectedNews = newsResponseList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selected News")
                .setMessage("Title : " + selectedNews.getTitle() + "\nDescription : " + selectedNews.getExplanation())
                .setPositiveButton("Open In Browser", (click, b) -> {
                   String Url= selectedNews.getHdurl();
                   Intent intent=new Intent((Intent.ACTION_VIEW));
                   intent.setData(Uri.parse(Url));
                   startActivity(intent);
                })
                .setNegativeButton("Delete", (click, b) -> {
                    deleteNews(selectedNews); //remove the contact from database
                    newsResponseList.remove(position); //remove the contact from contact list
                    adapter.notifyDataSetChanged(); //there is one less item so update the list
                })
                .setNeutralButton("dismiss", (click, b) -> {
                })
                .create().show();

    }

    protected void deleteNews(NewsResponse news) {
        db.delete(DatabaseOpener.TABLE_NAME, DatabaseOpener.COL_ID + "= ?", new String[]{Long.toString(news.getId())});
       // db.close();
    }


    protected class ListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return newsResponseList.size();
        }

        public NewsResponse getItem(int position) {
            return newsResponseList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        public View getView(int position, View old, ViewGroup parent) {
            View newView = getLayoutInflater().inflate(R.layout.fav_news_list_row, parent, false);

            NewsResponse thisRow = getItem(position);

            //get the TextViews
            TextView newsTitle = (TextView) newView.findViewById(R.id.row_title);
            TextView newsDesc = (TextView) newView.findViewById(R.id.row_desc);
            TextView newsUrl = (TextView) newView.findViewById(R.id.row_url);

            //update the text fields:
            newsTitle.setText(thisRow.getTitle());
            newsDesc.setText(thisRow.getExplanation());
            newsUrl.setText(thisRow.getHdurl());
            //newsUrl.setText(thisRow.getUrl());

            //return the row:
            return newView;
        }

    }


}