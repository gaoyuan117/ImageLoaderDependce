package com.gaoyuan.imageloaderdependce.cache;

import android.graphics.Bitmap;
import android.provider.Settings;
import android.util.LruCache;

import com.gaoyuan.imageloaderdependce.request.BitmapRequest;

/**
 * Created by gaoyuan on 2017/5/23.
 */

public class MemoryCache implements BitmapCache {
    private LruCache<String, Bitmap> lruCache;

    public MemoryCache() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024 / 8);
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        lruCache.put(bitmapRequest.getImageuriMD5(), bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        return lruCache.get(bitmapRequest.getImageuriMD5());
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
        lruCache.remove(bitmapRequest.getImageuriMD5());
    }
}
