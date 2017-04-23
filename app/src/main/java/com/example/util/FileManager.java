package com.example.util;

/**
 * Created by HP on 2016/12/16.
 */

public class FileManager {
    public static String getSaveFilePath() {
        if (CommonUtil.hasSDCard()) {
            return CommonUtil.getRootFilePath() + "OLLY/files/";
        } else {
            return CommonUtil.getRootFilePath() + "OLLY/files";
        }
    }
}
