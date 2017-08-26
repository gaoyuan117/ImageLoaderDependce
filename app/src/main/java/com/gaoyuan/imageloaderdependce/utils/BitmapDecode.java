package com.gaoyuan.imageloaderdependce.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by gaoyuan on 2017/7/8.
 * 解码图片
 */

public abstract class BitmapDecode {

    /**
     * 解码图片
     *
     * @return
     */
    public Bitmap decodeBitmap(int reqWidth, int reqHeight) {

        //初始化Options
        BitmapFactory.Options option = new BitmapFactory.Options();
        //只需要读取图片宽高信息，不需要全部加载到内存
        option.inJustDecodeBounds = true;
        //根据option加载bitmap
        decodeWidthBitmap(option);
        //计算图片的缩放比例
        caculateSampleSizeWithOption(option, reqWidth, reqHeight);
        return decodeWidthBitmap(option);
    }

    private void caculateSampleSizeWithOption(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //计算缩放的比例
        //图片的原始宽高
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;
        // reqWidth ImageView的宽
        if (width > reqWidth || height > reqHeight) {
            //宽高的缩放比例
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = Math.max(heightRatio, widthRatio);

            options.inSampleSize = inSampleSize;
            //每个像素两个字节
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inJustDecodeBounds = false;
            //当系统内存不足时可以回收bitmap
            options.inPurgeable = true;
            options.inInputShareable = true;
        }


    }

    protected abstract Bitmap decodeWidthBitmap(BitmapFactory.Options options);
}
