package com.app.photos.presenter;

import com.app.photos.view.GalleryView;

/**
 * Created by Venkatesan on 11/25/2016.
 */

public interface GalleryPresenter {
    public void loadData();
    public void setView(GalleryView view);
}
