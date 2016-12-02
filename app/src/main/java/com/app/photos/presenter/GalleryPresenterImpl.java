package com.app.photos.presenter;

import com.app.photos.model.DataListener;
import com.app.photos.model.DataModel;
import com.app.photos.model.service.FlickrService;
import com.app.photos.model.service.LocalDataService;
import com.app.photos.model.vo.ImageItem;
import com.app.photos.view.GalleryView;

import java.util.ArrayList;

/**
 * Created by Venkatesan on 11/25/2016.
 */

public class GalleryPresenterImpl implements GalleryPresenter,DataListener {

    private GalleryView galleryView;
    private DataModel dataModel;

    @Override
    public void loadData() {
        galleryView.showPreloader();
        dataModel.getData();
    }

    @Override
    public void setView(GalleryView view) {
        galleryView = view;
        dataModel = new FlickrService(this);
    }

    @Override
    public void onSuccessCallback(ArrayList<ImageItem> items) {
        galleryView.hidePreloader();
        galleryView.displayItems(items);
    }

    @Override
    public void onErrorCallback(String message) {
        galleryView.hidePreloader();
        galleryView.showError(message);
    }
}
