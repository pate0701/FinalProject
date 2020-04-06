package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<NewsResponse>{

    private final Context context;
    private final List<NewsResponse> listOfNews;
    private final List<NewsResponse> savedNewsList;
    public CustomAdapter(@NonNull Context context, List<NewsResponse> listOfNews) {

        super(context, R.layout.news_list);
        this.context = context;
        this.listOfNews = listOfNews;
        this.savedNewsList = new ArrayList<>();
    }

    public View getView(int position, View view, ViewGroup parent) {
        NewsResponse newsResponse = getItem(position);
        LayoutInflater inflater= LayoutInflater.from(context);
        View rowView=inflater.inflate(R.layout.news_list, null,true);

        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView description = (TextView) rowView.findViewById(R.id.description);
        TextView date = (TextView) rowView.findViewById(R.id.pubdate);
        TextView url = (TextView) rowView.findViewById(R.id.url);
        Button saveBtn = (Button) rowView.findViewById(R.id.save);
        title.setText(newsResponse.getTitle());
        description.setText(newsResponse.getExplanation());
        date.setText(newsResponse.getDate());
        url.setText(newsResponse.getHdurl());
        Linkify.addLinks(url, Linkify.WEB_URLS);
        saveBtn.setTag(position);

        DatabaseOpener dbOpener = new DatabaseOpener (getContext());
        SQLiteDatabase db = dbOpener.getWritableDatabase ();

        saveBtn.setOnClickListener((item) -> {
           Integer object = (Integer) item.getTag();
           saveBtn.setText(saveBtn.getText().equals("Favorite") ? "Saved" : "Favorite");

           String titleToSave=newsResponse.getTitle();
           String descToSave=newsResponse.getExplanation();
           String urlToSave=newsResponse.getHdurl();
           ContentValues newRowValues = new ContentValues();

           newRowValues.put(DatabaseOpener.COL_TITLE, titleToSave);
           newRowValues.put(DatabaseOpener.COL_DESCRIPTION, descToSave);
           newRowValues.put(DatabaseOpener.COL_URL, urlToSave);
           db.insert(DatabaseOpener.TABLE_NAME,null,newRowValues);

           Toast.makeText(getContext(),"Added to Favourites",Toast.LENGTH_LONG).show ();

        });

        return rowView;







    };



 /*   public void onClick(View view) {
        Toast.makeText(context,"hello",Toast.LENGTH_LONG);
        int position=(Integer) view.getTag();
        NewsResponse newsResponse= getItem(position);
        Log.i("CustomAdapter", "onClick: " +newsResponse.getExplanation());
   }*/

}
