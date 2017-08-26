package com.gaoyuan.imageloaderdependce.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.gaoyuan.imageloaderdependce.cache.BitmapCache;
import com.gaoyuan.imageloaderdependce.config.DisplayConfig;
import com.gaoyuan.imageloaderdependce.request.BitmapRequest;

/**
 * Created by gaoyuan on 2017/5/23.
 * 抽象类的作用是为了抽出内存缓存和磁盘缓存公共的代码
 */

public abstract class Abstractloader implements Loader {
    // 拿到用户自定义的缓存策略
    BitmapCache bitmapCache = SimpleImageLoader.getInstance().getConfig().getBitmapCache();
    //拿到显示配置
    DisplayConfig displayConfig = SimpleImageLoader.getInstance().getConfig().getDisplayConfig();


    @Override
    public void loadImage(BitmapRequest request) {

        //从缓存中取到Bitmap 不知道是内存缓存还是硬盘缓存
        Bitmap bitmap = bitmapCache.get(request);

        if (bitmap == null) {
            //显示默认的加载图片
            showLoadingImage(request);
            //加载图片
            bitmap = onLoad(request);
            //缓存图片
            cacheBitmap(request, bitmap);
        }
        deliveryToUIThread(request,bitmap);

    }

    /**
     * 交给主线程显示
     *
     * @param request
     * @param bitmap
     */
    protected void deliveryToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if (imageView != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(request, bitmap);
                }

            });
        }

    }

    private void updateImageView(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        //加载正常  防止图片错位
        if (bitmap != null && imageView.getTag().equals(request.getImageUrl())) {
            imageView.setImageBitmap(bitmap);
        }
        //有可能加载失败
        if (bitmap == null && displayConfig != null && displayConfig.failImage != -1) {
            imageView.setImageResource(displayConfig.failImage);
        }
        //监听
        //回调 给圆角图片  特殊图片进行扩展
        if (request.imageListener != null) {
            request.imageListener.onComplete(imageView, bitmap, request.getImageUrl());
        }
    }

    /**
     * 缓存图片
     *
     * @param request
     */
    protected void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (request != null && bitmap != null) {
            synchronized (Abstractloader.class) {
                bitmapCache.put(request, bitmap);
            }
        }
    }

    /**
     * 抽象加载策略 因为网络加载策略和本地加载策略有差异
     *
     * @param request
     */
    protected abstract Bitmap onLoad(BitmapRequest request);

    /**
     * 加载前显示的图片
     *
     * @param request
     */
    protected void showLoadingImage(BitmapRequest request) {

        //指定了  显示配置
        if (hasLoadingPlaceHolder()) {
            final ImageView imageView = request.getImageView();
            if (imageView != null) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(displayConfig.loadingImage);
                    }
                });
            }
        }
    }

    /**
     * 判断有没有设置加载中图片
     *
     * @return
     */
    protected boolean hasLoadingPlaceHolder() {
        return displayConfig != null && displayConfig.loadingImage > 0;
    }
}
