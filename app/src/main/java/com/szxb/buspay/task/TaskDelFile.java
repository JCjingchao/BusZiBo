package com.szxb.buspay.task;

import android.content.Context;
import android.os.Environment;

import com.szxb.buspay.util.DateUtil;
import com.szxb.xblog.XBLog;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 作者: Tangren on 2017/8/1
 * 包名：com.szxb.buspay.task
 * 邮箱：996489865@qq.com
 * TODO:删除过期文件>3个月的文件
 */

public class TaskDelFile {

    /**
     * @param context 上下文对象
     * @param subPath 文件夹路径 "bus"
     */
    public static void del(Context context, String subPath) {
        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + subPath;
        File file = new File(path);
        File[] files = file.listFiles();// 读取
        FileThread thread = new FileThread(files, context, subPath);
        thread.start();
    }

    static class FileThread extends Thread {

        private File[] files;
        private Context context;
        private String subPath;

        public FileThread(File[] files, Context context, String subPath) {
            this.files = files;
            this.context = context;
            this.subPath = subPath;
        }

        @Override
        public void run() {
            super.run();
            getFileName(files, context, subPath);
        }
    }

    private static void getFileName(File[] files, Context context, String subPath) {
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileName(file.listFiles(), context, subPath);
                } else {
                    String fileName = file.getName();
                    if (fileName.endsWith(".txt")) {
                        try {
                            String s = fileName.substring(0, fileName.lastIndexOf("."));
                            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", new Locale("zh", "CN"));
                            String ph = Environment.getExternalStorageDirectory()
                                    .getAbsolutePath() + File.separator + subPath + "/" + s.trim() + ".txt";
                            File fe = new File(ph);

                            XBLog.d("getFileName(TaskDelFile.java:56)" + ph);
                            if (s.length() > 18) {
                                String fileSaveTime = s.substring(9, 17);
                                Date lastTime = format.parse(fileSaveTime);
                                if (DateUtil.isDelFile(lastTime)) {
                                    fe.delete();
                                }
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                            XBLog.e(e.getMessage(), "删除文件失败,请查看失败原因");
                        }
                    }
                }
            }
        }
    }
}
