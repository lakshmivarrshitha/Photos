package com.app.photos.model;

import com.app.photos.model.vo.ImageItem;

import java.util.ArrayList;

/**
 * Created by Venkatesan on 11/26/2016.
 */

public interface DataListener {
    public void onSuccessCallback(ArrayList<ImageItem> items);
    public void onErrorCallback(String message);
}
