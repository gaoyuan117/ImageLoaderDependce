package com.gaoyuan.imageloaderdependce.loader;

import com.gaoyuan.imageloaderdependce.request.BitmapRequest;

/**
 * Created by gaoyuan on 2017/5/23.
 */

public interface Loader {

    /**
     * 加载图片
     * @param request
     */
    void loadImage(BitmapRequest request);
}
