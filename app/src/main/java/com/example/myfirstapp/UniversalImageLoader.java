package com.example.myfirstapp;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


/*
     * This file is downloaded from the following location:
     *     https://github.com/mitchtabian/ForSale/blob/bf047ab98c75b36baedc950f56c104aa0aaa95b1/app/src/main/java/codingwithmitch/com/forsale/util/UniversalImageLoader.java
     * The source code has not been modified.
     * The file has the following copyright from the original author:
     *     N/A
     */

public class UniversalImageLoader {

    private static final int defaultImage = R.drawable.ic_launcher_foreground;
    private Context mContext;

    /**
     * Set the context.
     * @param context
     */
    public UniversalImageLoader(Context context) {
        mContext = context;
    }

    /**
     * Set up the image loader.
     * @return configuration
     */
    public ImageLoaderConfiguration getConfig(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .considerExifParams(true)
                .cacheOnDisk(true).cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();

        return configuration;
    }

    /**
     * Loads image loader based on instance. The photo is displayed using the image URL.
     * @param imgURL
     * @param image
     */
    public static void setImage(String imgURL, ImageView image){

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(imgURL, image);
    }
}
