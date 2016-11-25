package com.app.photos.view;

import com.app.photos.model.vo.ImageItem;

import java.util.ArrayList;

/**
 * Created by Venkatesan on 11/25/2016.
 */

public interface GalleryView {
    public void displayItems(ArrayList<ImageItem> items);
}
