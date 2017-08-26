package com.gaoyuan.imageloaderdependce.request;

import android.util.Log;

import com.gaoyuan.imageloaderdependce.loader.Loader;
import com.gaoyuan.imageloaderdependce.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * Created by gaoyuan on 2017/5/23.
 * 转发器
 * 请求转发线程 不断从请求队列中获取请求
 */

public class RequestDispatcher extends Thread{
    //请求队列
    private BlockingQueue<BitmapRequest> mRequestQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> blockingQueue){
        mRequestQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            try {
                //从队列中拿到对象
                BitmapRequest request = mRequestQueue.take();
                //处理请求对象
                String schema = pareSchema(request.getImageUrl());
                //获取Loader加载器
                Loader loader = LoaderManager.getInstance().getLoader(schema);
                loader.loadImage(request);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String pareSchema(String imageUrl) {
        if(imageUrl.contains("://")){
            return imageUrl.split("://")[0];
        }else {
            Log.i("gy","暂不支持此类型");
        }
        return null;
    }
}
