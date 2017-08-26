package com.gaoyuan.imageloaderdependce.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.gaoyuan.imageloaderdependce.config.DisplayConfig;
import com.gaoyuan.imageloaderdependce.loader.SimpleImageLoader;
import com.gaoyuan.imageloaderdependce.policy.LoaderPolicy;
import com.gaoyuan.imageloaderdependce.utils.MD5Utils;

import java.lang.ref.SoftReference;

/**
 * Created by gaoyuan on 2017/5/23.
 * 请求体
 */

//List集合实现排序要 implements Comparator
public class BitmapRequest implements Comparable<BitmapRequest> {
    //加载策略
    private LoaderPolicy loaderPolicy = SimpleImageLoader.getInstance().getConfig().getLoadPolicy();
    //编号 优先级
    private int seriaNo;

    //持有ImageView的软引用
    private SoftReference<ImageView> imageViewSoft;
    //图片路径
    private String imageUrl;
    //MD5的图片路径
    private String imageuriMD5;
    //下载完成的监听
    public SimpleImageLoader.ImageListener imageListener;

    public DisplayConfig displayConfig;


    public BitmapRequest(ImageView imageView, String imageUrl, DisplayConfig config,
                         SimpleImageLoader.ImageListener imageListener) {
        imageViewSoft = new SoftReference<ImageView>(imageView);
        //设置可见的Image的tag，要下载的图片路径
        imageView.setTag(imageUrl);
        this.imageUrl = imageUrl;
        this.imageuriMD5 = MD5Utils.toMD5(imageUrl);
        if (config != null) {
            displayConfig = config;
        }
        this.imageListener = imageListener;
    }

    /**
     * 优先级的确定
     *
     * @return
     */
    @Override
    public int compareTo(@NonNull BitmapRequest o) {
        return loaderPolicy.compareto(o, this);

    }


    public int getSeriaNo() {
        return seriaNo;
    }

    public void setSeriaNo(int seriaNo) {
        this.seriaNo = seriaNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitmapRequest request = (BitmapRequest) o;

        if (seriaNo != request.seriaNo) return false;
        return loaderPolicy != null ? loaderPolicy.equals(request.loaderPolicy) : request.loaderPolicy == null;

    }

    @Override
    public int hashCode() {
        int result = loaderPolicy != null ? loaderPolicy.hashCode() : 0;
        result = 31 * result + seriaNo;
        return result;
    }

    public ImageView getImageView() {
        return imageViewSoft.get();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageuriMD5() {
        return imageuriMD5;
    }

    public SimpleImageLoader.ImageListener getImageListener() {
        return imageListener;
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }


}
