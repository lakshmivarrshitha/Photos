package com.app.photos.model.service;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.app.photos.model.DataModel;
import com.app.photos.model.vo.ImageItem;
import com.app.photos.photos.R;
import com.app.photos.view.MainActivity;

import java.util.ArrayList;

/**
 * Created by Venkatesan on 11/25/2016.
 */

public class LocalDataService implements DataModel {

    @Override
    public ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = MainActivity.context().getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.context().getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }
}
