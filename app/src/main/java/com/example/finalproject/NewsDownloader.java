package com.example.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class NewsDownloader extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static String Url = "https://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";
    ListView listView;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_downloader);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new CustomAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);
        new NewsDownloads().execute();

        Toolbar tbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, tbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        switch (item.getItemId()) {
            case R.id.item1:{
                message = "Navigating to Help Instructions.";
                startActivity(new Intent(NewsDownloader.this,About.class));
            }
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemnav1:
                startActivity(new Intent(NewsDownloader.this, NewsDownloader.class));
                break;
            case R.id.itemnav2:
                startActivity(new Intent(NewsDownloader.this, Favourites.class));
                break;
            case R.id.itemnav3:
                startActivity(new Intent(NewsDownloader.this,MainActivity.class));
                break;
        }
        return true;
    }

    class NewsDownloads extends AsyncTask<Void, String, List<NewsResponse>> {
        @Override
        protected List<NewsResponse> doInBackground(Void... voids) {
            return parseFeed();

        }

        public List<NewsResponse> parseFeed() {


            List<NewsResponse> items = new ArrayList<>();
            InputStream inputStream = null;
            try {


                URL url = new URL(Url);
                final URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(inputStream);
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("item");
                for (int i = 0; i < nodeList.getLength(); i++) {

                    Node node = nodeList.item(i);

                    Element fstElmnt = (Element) node;
                    NodeList nameList = fstElmnt.getElementsByTagName("title");
                    Element nameElement = (Element) nameList.item(0);
                    nameList = nameElement.getChildNodes();
                    String title = ((Node) nameList.item(0)).getNodeValue();
                    //title.add("" + ((Node) nameList.item(0)).getNodeValue());

                    NodeList websiteList = fstElmnt.getElementsByTagName("description");
                    Element websiteElement = (Element) websiteList.item(0);
                    websiteList = websiteElement.getChildNodes();
                    String description = ((Node) websiteList.item(0)).getNodeValue();

                    // description.add("" + ((Node) websiteList.item(0)).getNodeValue());

                    NodeList linkList = fstElmnt.getElementsByTagName("link");
                    Element linkElement = (Element) linkList.item(0);
                    linkList = linkElement.getChildNodes();
                    String link = ((Node) linkList.item(0)).getNodeValue();

                    NodeList pubDateList = fstElmnt.getElementsByTagName("pubDate");
                    Element pubDateElement = (Element) pubDateList.item(0);
                    pubDateList = pubDateElement.getChildNodes();
                    String pubDate = ((Node) pubDateList.item(0)).getNodeValue();

                    items.add(new NewsResponse(pubDate, description, title, link, null));

                }
            } catch (Exception e) {
                Log.i("MyXmlParser", "Error Io ==> " + e.getMessage());
            } finally {
                try {

                    inputStream.close();
                } catch (IOException e) {

                }
                return items;
            }
        }

        @Override
        protected void onPostExecute(List<NewsResponse> newsResponses) {
            adapter.addAll(newsResponses);
            super.onPostExecute(newsResponses);



        }
    }


}
