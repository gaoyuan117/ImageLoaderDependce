package com.gaoyuan.imageloaderdependce.loader;

import android.graphics.Bitmap;

import com.gaoyuan.imageloaderdependce.request.BitmapRequest;

/**
 * Created by gaoyuan on 2017/7/8.
 */

public class NullLoader extends Abstractloader{
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        return null;
    }
}
