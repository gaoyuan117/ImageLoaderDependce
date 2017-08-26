package com.gaoyuan.imageloaderdependce.policy;

import com.gaoyuan.imageloaderdependce.request.BitmapRequest;

/**
 * Created by gaoyuan on 2017/5/23.
 * 加载策略 先进先出
 */

public class SerialPolicy implements LoaderPolicy{
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request1.getSeriaNo()-request2.getSeriaNo();
    }
}
