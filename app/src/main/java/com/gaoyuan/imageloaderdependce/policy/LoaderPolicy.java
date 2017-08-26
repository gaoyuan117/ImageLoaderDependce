package com.gaoyuan.imageloaderdependce.policy;

import com.gaoyuan.imageloaderdependce.request.BitmapRequest;

/**
 * Created by gaoyuan on 2017/5/23.
 * 记载策略
 */

public interface LoaderPolicy {
    /**
     * 两个BitmapRequest 进行优先级比较
     * @param request1
     * @param request2
     * @return
     */
    int compareto(BitmapRequest request1,BitmapRequest request2);
}
