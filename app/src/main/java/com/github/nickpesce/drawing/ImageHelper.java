package com.github.nickpesce.drawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import nickpesce.github.com.downup.R;

/**
 * Created by Nick on 9/19/2015.
 */
public class ImageHelper {
    public static Bitmap scaleBitmap(Bitmap b, int w, int h)
    {
        return Bitmap.createScaledBitmap(b, w, h, true);
    }

    public static Bitmap getBitmapFromResource(Context context, int resource)
    {
        return BitmapFactory.decodeResource(context.getResources(), resource);
    }

    public static Bitmap getScaledBitmapFromResource(Context context, int resource, int w, int h)
    {
        return scaleBitmap(getBitmapFromResource(context, resource), w, h);
    }


}
