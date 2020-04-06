package com.example.finalproject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

public class ListArticleAdapter extends BaseAdapter {

    List<ArticleSetGet> articles;
    static LayoutInflater inflater;

    ListArticleAdapter(Context context, List<ArticleSetGet> articleSetGets) {
        this.articles = articleSetGets;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_guardian_article, null);
        }
        ArticleSetGet articleSetGet = articles.get(position);
        String title = articleSetGet.getTitle();

        String section = articleSetGet.getSectionName();

        TextView textView2 = convertView.findViewById(R.id.titleArticle);
        TextView textView4 = convertView.findViewById(R.id.worldNewsText);

        textView2.setText(title);

        textView4.setText("Section: "+section);
        return convertView;
    }
}