package com.example.Cache;

import android.content.Context;
import android.util.Log;

import com.example.util.FileHelper;

import java.io.File;

/**
 * Created by HP on 2016/12/16.
 */

public abstract class AbstractFileCache {

    private String dirString;

    public AbstractFileCache(Context context) {

        dirString = getCacheDir();
        //创建文件夹
        boolean ret = FileHelper.createDirectory(dirString);
        //System.out.println("dirString");
        Log.e("", "FileHelper.createDirectory:" + dirString + ", ret = " + ret);
    }

    public File getFile(String url) {
        File f = new File(getSavePath(url));
        return f;
    }

    public abstract String  getSavePath(String url);
    public abstract String  getCacheDir();

    public void clear() {
        FileHelper.deleteDirectory(dirString);
    }

}
