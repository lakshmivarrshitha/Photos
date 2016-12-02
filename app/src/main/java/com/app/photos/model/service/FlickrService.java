package com.app.photos.model.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.app.photos.model.DataListener;
import com.app.photos.model.DataModel;
import com.app.photos.model.vo.ImageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Venkatesan on 11/25/2016.
 */

public class FlickrService extends AsyncTask<Void,Void,ArrayList<ImageItem>> implements DataModel {

    DataListener listener;

    //String url = "https://api.flickr.com/services/rest/?method=flickr.stats.getPopularPhotos&api_key=666050671748f689904d29bc49f931a1&per_page=60&page=1&format=json&nojsoncallback=1";
    String url = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=666050671748f689904d29bc49f931a1&per_page=60&page=1&format=json&nojsoncallback=1";
    private static final String FLICKR_QUERY_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=666050671748f689904d29bc49f931a1&per_page=100&page=5&format=json&nojsoncallback=1&license=1,2,3,4,5,6&content_type=1&privacy_filter=1&tags=trip,temple,couple,frost";

    public FlickrService(DataListener listener) {
        this.listener = listener;
    }

    @Override
    public void getData() {
        this.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<ImageItem> doInBackground(Void... voids) {
        JSONParser jParser = new JSONParser();
        ArrayList<ImageItem> items = new ArrayList<ImageItem>();

        try {
            // get json from url here
            JSONObject json = jParser.getJSONFromUrl(url);
            System.out.println("JSON: " + json);

            // JSONArray dataArray = json.getJSONArray("photo");
            JSONObject photos = json.getJSONObject("photos");
            JSONArray dataArray = photos.getJSONArray("photo");
            int thumbnailsCount = dataArray.length();

            for (int i = 0; i < thumbnailsCount; i++) {
                String farm =  Integer.toString(dataArray.getJSONObject(i).getInt("farm"));
                String server = Integer.toString(dataArray.getJSONObject(i).getInt("server"));
                String id = Integer.toString(dataArray.getJSONObject(i).getInt("id"));
                String secret = dataArray.getJSONObject(i).getString("secret");
                String picURL = "https://farm"+farm +".staticflickr.com/"+server+"/"+id+"_"+secret+"_"+"z"+".jpg";
                //String picURL = String.format("http://farm%s.staticflickr.com/%s/%s_%s_z.jpg", farm, server, id, secret);

                Log.d("URL",picURL);
                URL url = new URL(picURL);
                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                ImageItem item = new ImageItem(image,id.toString());
                items.add(item);
            }
        } catch (Exception e) {
            listener.onErrorCallback(e.getMessage().toString());
        }

        return items;
    }

    @Override
    protected void onPostExecute(ArrayList<ImageItem> items) {
        super.onPostExecute(items);
        listener.onSuccessCallback(items);
    }


    public static class JSONParser {
        static InputStream is = null;
        static JSONObject jObj = null;
        static String json = "";

        // constructor
        public JSONParser() {

        }

        public JSONObject getJSONFromUrl(String jsonUrl) throws Exception {

            int resCode = 0;

            try {

                URL url = new URL(jsonUrl);
                HttpURLConnection c = (HttpURLConnection)url.openConnection();
                c.setRequestMethod("GET");
                c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.connect();
                int status = c.getResponseCode();
                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();
                        json = sb.toString();
                }


                // try to parse the string to a JSON object
                try
                {
                    jObj = new JSONObject(json);
                    Log.d("JSon string from flickr",json);
                }
                catch (JSONException e)
                {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                    throw e;
                }


            } catch (Exception ex) {
                Log.e("Post Error", resCode + "\n Exception" + ex);
                throw ex;
            }

            return jObj;
        }
    }
}
