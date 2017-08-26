package com.gaoyuan.imageloaderdependce.cache;

import android.graphics.Bitmap;

import com.gaoyuan.imageloaderdependce.request.BitmapRequest;

/**
 * Created by gaoyuan on 2017/5/23.
 */

public interface BitmapCache {

    /**
     * 缓存Bitmap
     *
     * @param bitmapRequest
     * @param bitmap
     */
    void put(BitmapRequest bitmapRequest, Bitmap bitmap);

    /**
     * 通过请求，取Bitmap
     *
     * @param bitmapRequest
     * @return
     */
    Bitmap get(BitmapRequest bitmapRequest);

    /**
     * 移除缓存
     *
     * @param bitmapRequest
     */
    void remove(BitmapRequest bitmapRequest);
}
