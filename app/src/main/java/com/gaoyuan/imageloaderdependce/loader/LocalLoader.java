package com.gaoyuan.imageloaderdependce.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.gaoyuan.imageloaderdependce.request.BitmapRequest;
import com.gaoyuan.imageloaderdependce.utils.BitmapDecode;
import com.gaoyuan.imageloaderdependce.utils.ImageViewHelper;

import java.io.File;

/**
 * Created by gaoyuan on 2017/5/23.
 */

public class LocalLoader extends Abstractloader{
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        //得到本地图片的路径
        final String path = Uri.parse(request.getImageUrl()).getPath();
        final File file = new File(path);
        if(file==null){
            return null;
        }

        final BitmapDecode bitmapDecode = new BitmapDecode() {
            @Override
            protected Bitmap decodeWidthBitmap(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path,options);
            }
        };
        return bitmapDecode.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()),
                ImageViewHelper.getImageViewHeight(request.getImageView()));
    }
}
