package com.app.photos.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.app.photos.photos.R;
import com.app.photos.presenter.GalleryPresenter;
import com.app.photos.presenter.GalleryPresenterImpl;
import com.app.photos.view.adapter.GridViewAdapter;
import com.app.photos.model.vo.ImageItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GalleryView {

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private GalleryPresenter presenter;
    private static MainActivity mApp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = this;
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView);
        presenter = new GalleryPresenterImpl();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    public void displayItems(ArrayList<ImageItem> items) {
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, items);
        gridView.setAdapter(gridAdapter);
    }

    public static Context context()
    {
        return mApp.getApplicationContext();
    }
}
