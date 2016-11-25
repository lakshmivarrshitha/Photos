package com.app.photos.presenter;

import com.app.photos.model.DataModel;
import com.app.photos.model.service.LocalDataService;
import com.app.photos.view.GalleryView;

/**
 * Created by Venkatesan on 11/25/2016.
 */

public class GalleryPresenterImpl implements GalleryPresenter {

    private GalleryView galleryView;
    private DataModel dataModel;

    @Override
    public void loadData() {
        galleryView.displayItems(dataModel.getData());
    }

    @Override
    public void setView(GalleryView view) {
        galleryView = view;
        dataModel = new LocalDataService();
    }
}
