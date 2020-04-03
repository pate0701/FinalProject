package com.example.finalproject;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchImage extends AsyncTask<Void,Void, Bitmap> {

    ImageView mImageResult;
    String imageUrl, jsonUrl;
    double latitude, longitude;
    boolean imageFound = true;
    ProgressDialog mProgressDialog;
    public FetchImage(ImageView imageResult, double lat, double longi,ProgressDialog progressDialog) {
        latitude = lat;
        longitude = longi;
        mImageResult = imageResult;
        mProgressDialog =progressDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute ();

    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        imageUrl = "https://dev.virtualearth.net/REST/V1/Imagery/Map/Birdseye/"+latitude+","+longitude+"/20?dir=180&ms=500,500&key=AhNW2HpyQ9W60Q140dmnrsQvQiACNLjQxFgFCBEcn_eCEEDt_2jvgUX7YAY8Yxwi";

        InputStream in = null;
        Bitmap bmp =null;
        try {

                in = new URL (imageUrl).openStream ();
                bmp = BitmapFactory.decodeStream (in);

        }catch (FileNotFoundException e)
        {
            imageFound =false;
        }
        catch (IOException e) {
            e.printStackTrace ();
        }
        return bmp;
    }

    protected void onPostExecute(Bitmap bmp) {
        if(bmp != null)
            mImageResult.setImageBitmap (bmp);
        else if (!imageFound)
        {
            mImageResult.setImageResource (R.drawable.error404);
        }
        mProgressDialog.dismiss ();
    }
}
