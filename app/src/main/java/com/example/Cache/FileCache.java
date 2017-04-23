package com.example.Cache;

import android.content.Context;

import com.example.util.FileManager;

/**
 * Created by HP on 2016/12/16.
 */

public class FileCache extends AbstractFileCache{

    public FileCache(Context context) {
        super(context);

    }


    @Override
    public String getSavePath(String url) {
        String filename = String.valueOf(url.hashCode());
       // System.out.println("save+path:"+getCacheDir() + filename);
        return getCacheDir() + filename;
    }

    @Override
    public String getCacheDir() {

        return FileManager.getSaveFilePath();//文件基本目录
    }

}
