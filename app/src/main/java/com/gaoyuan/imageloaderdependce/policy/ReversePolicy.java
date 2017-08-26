package com.gaoyuan.imageloaderdependce.policy;

import com.gaoyuan.imageloaderdependce.request.BitmapRequest;

/**
 * Created by gaoyuan on 2017/5/23.
 */

public class ReversePolicy implements LoaderPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request2.getSeriaNo()-request1.getSeriaNo();

    }
}
