package com.app.photos.model.service;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.app.photos.model.DataListener;
import com.app.photos.model.DataModel;
import com.app.photos.model.vo.ImageItem;
import com.app.photos.photos.R;
import com.app.photos.view.MainActivity;

import java.util.ArrayList;

/**
 * Created by Venkatesan on 11/25/2016.
 */

public class LocalDataService extends AsyncTask<Void,Void,ArrayList<ImageItem>> implements DataModel {

    DataListener listener;

    public LocalDataService(DataListener dl){
        listener = dl;
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
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = MainActivity.context().getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.context().getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }

    @Override
    protected void onPostExecute(ArrayList<ImageItem> items) {
        super.onPostExecute(items);
        listener.onSuccessCallback(items);
    }

}
