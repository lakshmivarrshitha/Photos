package com.app.photos.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

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
    private ProgressBar spinner;
    private AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = this;
        setContentView(R.layout.activity_main);

        // Initially spinner should be hidden
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.INVISIBLE);

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

    @Override
    public void showPreloader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hidePreloader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void showError(final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Error")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
    }
    public static Context context()
    {
        return mApp.getApplicationContext();
    }
}
