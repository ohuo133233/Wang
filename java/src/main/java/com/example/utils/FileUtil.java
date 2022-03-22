package com.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileUtil {

    private static final String TAG = "FileUtil";
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public interface FileCallback {
        void onSuccess(String result);

        void onFailure(String msg);
    }

    public static void readJsonFileFromSdcard(final String dest, final String fileName, final FileCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(dest, fileName);
                String jsonString = null;
                try {
                    FileInputStream fis = new FileInputStream(file);
                    int size = fis.available();
                    byte[] buffer = new byte[size];
                    fis.read(buffer);
                    jsonString = new String(buffer);
                    callback.onSuccess(jsonString);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    callback.onFailure(e.getLocalizedMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /**
     * 删除指定目录指定前后缀的文件
     *
     * @param dirPath
     * @param regEx
     */
    public static void delete(String dirPath, String regEx) {
        executor.execute(new DeleteRunnable(dirPath,  regEx));
    }

}
