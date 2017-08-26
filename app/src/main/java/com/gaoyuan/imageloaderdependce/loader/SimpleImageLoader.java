package com.gaoyuan.imageloaderdependce.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.gaoyuan.imageloaderdependce.config.DisplayConfig;
import com.gaoyuan.imageloaderdependce.config.ImageLoaderConfig;
import com.gaoyuan.imageloaderdependce.request.BitmapRequest;
import com.gaoyuan.imageloaderdependce.request.RequestQueue;

/**
 * Created by gaoyuan on 2017/5/23.
 */

public class SimpleImageLoader {
    //配置
    private ImageLoaderConfig config;
    //请求队列
    private RequestQueue mRequestQueue;
    //单例对象
    private static volatile SimpleImageLoader mInstance;

    private SimpleImageLoader() {

    }

    private SimpleImageLoader(ImageLoaderConfig config) {
        this.config = config;
        //初始化请求队列
        mRequestQueue = new RequestQueue(config.getThreadCount());
        //开启请求队列
        mRequestQueue.start();
    }

    /**
     * 获取单例方法
     *
     * @param config
     * @return
     */
    public static SimpleImageLoader getInstance(ImageLoaderConfig config) {
        if (mInstance == null) {
            synchronized (SimpleImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new SimpleImageLoader(config);
                }
            }
        }

        return mInstance;
    }

    /**
     * 第二次获取单例
     *
     * @return
     */
    public static SimpleImageLoader getInstance() {
        if (mInstance == null) {
            throw new UnsupportedOperationException("没有初始化SimpleImageLoader");
        }
        return mInstance;
    }

    /**
     * 显示图片
     *
     * @param imageView
     * @param uri       file http开头
     */
    public void displayImage(ImageView imageView, String uri) {
        displayImage(imageView, uri, null);

    }

    /**
     * 动态配置
     *
     * @param imageView
     * @param uri
     * @param config
     */
    public void displayImage(ImageView imageView, String uri, DisplayConfig config) {
        displayImage(imageView, uri, config, null);
    }

    /**
     * 回掉方法
     *
     * @param imageView
     * @param uri
     * @param config
     * @param imageListener
     */
    public void displayImage(ImageView imageView, String uri, DisplayConfig config, ImageListener imageListener) {
        //实例化一个请求
        BitmapRequest request = new BitmapRequest(imageView,uri,config,imageListener);
        //添加到队列中
        mRequestQueue.addRequest(request);

    }

    /**
     * 接口回掉指导什么时候下载图片完成
     * 回掉bitmap方便 生成圆角图片等
     */
    public static interface ImageListener {
        void onComplete(ImageView imageView,  Bitmap bitmap,String uri);
    }

    public ImageLoaderConfig getConfig() {
        return config;
    }

}
