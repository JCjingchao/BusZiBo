//package com.szxb.buspay.util.ftp;
//
//import android.os.Environment;
//import android.os.storage.StorageManager;
//import android.util.Log;
//
//import com.alibaba.fastjson.JSONObject;
//import com.szxb.buspay.BusApp;
//import com.szxb.buspay.db.manager.DBManager;
//import com.szxb.buspay.db.sp.CommonSharedPreferences;
//import com.szxb.buspay.entity.CardPayEntity;
//import com.szxb.buspay.interfaces.UploadListener;
//import com.szxb.buspay.util.DateUtil;
//import com.szxb.xblog.XBLog;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//
//import rx.Observable;
//import rx.Subscriber;
//import rx.Subscription;
//import rx.functions.Action1;
//import rx.functions.Func1;
//import rx.functions.Func2;
//import rx.schedulers.Schedulers;
//
//import static android.content.Context.STORAGE_SERVICE;
//
///**
// * 作者: Tangren on 2017/7/26
// * 包名：com.szxb.buspay.util
// * 邮箱：996489865@qq.com
// * TODO:读取数据库文件,并写入文件上传是FTP
// */
//
//public class WriteFileUtil {
//
//    private UploadListener listener;
//
//    private String subPath = "bus";
//
//    /**
//     * @param subPath 文件夹路径，默认/bus
//     */
//    public WriteFileUtil(String subPath) {
//        this.subPath = subPath;
//    }
//
//    public WriteFileUtil() {
//    }
//
//    private Subscription subscribe;
//
//    private boolean isSuccess = false;
//
//    /**
//     * 保存文件到内置sd卡,并上传到ftp
//     *
//     * @param fileName1 scanQ60012017072501.txt
//     * @param fileName2 cardQ60012017072501.txt
//     */
//    public void saveFileAndUploadNet(final String fileName1, final String fileName2) {
//        Log.d("WriteFileUtil",
//                "saveFileAndUploadNet(WriteFileUtil.java:71)" + DateUtil.getCurrentTime());
//        Query query = new Query().invoke();
//        Observable<List<SwipeEntity>> swipe = query.getSwipe();
//        Observable<List<JSONObject>> card = query.getCard();
//
//        //合并处理写入内置SD卡中
//        subscribe = Observable.zip(swipe, card, new Func2<List<SwipeEntity>, List<JSONObject>, Boolean>() {
//            @Override
//            public Boolean call(List<SwipeEntity> swipeEntities, List<JSONObject> cardPayEntities) {
//                FileOutputStream swipeStaram = null;
//                FileOutputStream cardStaram = null;
//                String rootPath = Environment.getExternalStorageDirectory() + "/" + subPath;
//                File file = new File(rootPath);
//                if (!file.exists()) {
//                    file.mkdir();
//                }
//                try {
//                    Log.d("WriteFileUtil",
//                            "call(WriteFileUtil.java:89)swipeEntities.size()=" + swipeEntities.size());
//                    if (swipeEntities != null && !swipeEntities.isEmpty()) {
//                        File saveFile = new File(file, fileName1);
//
//                        swipeStaram = new FileOutputStream(saveFile);
//                        for (int i = 0; i < swipeEntities.size(); i++) {
//                            swipeStaram.write(swipeEntities.get(i).getBiz_data_single().getBytes());
//                            swipeStaram.write("\r\n".getBytes());
//                        }
//                        swipeStaram.close();
//                        Log.d("WriteFileUtil",
//                                "call(WriteFileUtil.java:101)扫码写入完毕");
//                    }
//
//                    //刷卡写文件
//                    if (cardPayEntities != null && !cardPayEntities.isEmpty()) {
//
//                        File saveFile = new File(file, fileName2);
//
//                        cardStaram = new FileOutputStream(saveFile);
//                        for (int i = 0; i < cardPayEntities.size(); i++) {
//                            cardStaram.write(cardPayEntities.get(i).toString().getBytes());
//                            cardStaram.write("\r\n".getBytes());
//                        }
//                        cardStaram.close();
//                        Log.d("WriteFileUtil",
//                                "call(WriteFileUtil.java:117)刷卡写入完毕");
//                    }
//
//                } catch (FileNotFoundException e) {
//                    XBLog.d("call(WriteFileUtil.java:120)" + e.getMessage());
//                    Log.d("WriteFileUtil",
//                            "call(WriteFileUtil.java:122)" + e.getMessage());
//                    e.printStackTrace();
//                    return false;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    XBLog.d("call(WriteFileUtil.java:125)" + e.getMessage());
//                    Log.d("WriteFileUtil",
//                            "call(WriteFileUtil.java:129)" + e.getMessage());
//                    return false;
//                }
//                return true;
//            }
//        }).flatMap(new Func1<Boolean, Observable<Boolean>>() {
//            @Override
//            public Observable<Boolean> call(Boolean aBoolean) {
//                Log.d("WriteFileUtil",
//                        "call(WriteFileUtil.java:138)写文件是否成功=" + aBoolean);
//                //提交服务器
//                if (!aBoolean) return Observable.just(Boolean.FALSE);
//                try {
//                    File scan = new File(Environment.getExternalStorageDirectory() + "/" + subPath + "/" + fileName1);
//                    File card = new File(Environment.getExternalStorageDirectory() + "/" + subPath + "/" + fileName2);
//
//                    FileInputStream scanStream = new FileInputStream(scan);
//                    FileInputStream cardStream = new FileInputStream(card);
//                    List<InputStream> inputStreams = new ArrayList<InputStream>();
//                    inputStreams.add(scanStream);
//                    inputStreams.add(cardStream);
//                    isSuccess = new FTP()
//                            .builder("120.24.212.72")
//                            .setPort(2121)
//                            .setLogin("administrator", "@@#Dzw2017")
//                            .setPath("/" + subPath)
//                            .setFileName(new String[]{fileName1, fileName2})
//                            .setInput(inputStreams)
//                            .build();
////                    isSuccess = new FTP()
////                            .builder("192.168.0.104")
////                            .setPort(21)
////                            .setLogin("1234", "1234")
////                            .setPath("/" + subPath)
////                            .setFileName(new String[]{fileName1, fileName2})
////                            .setInput(inputStreams)
////                            .build();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.d("WriteFileUtil",
//                            "call(WriteFileUtil.java:170)失败");
//                    Log.d("WriteFileUtil",
//                            "call(WriteFileUtil.java:170)" + e.getMessage());
//                    return Observable.just(Boolean.FALSE);
//                }
//                return Observable.just(isSuccess);
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean aBoolean) {
//                        Log.d("WriteFileUtil",
//                                "call(WriteFileUtil.java:180)提交数据" + aBoolean);
//                        if (aBoolean) {
//                            //保存上传日期
//                            CommonSharedPreferences.put("lastTimePushFile", DateUtil.getCurrentDate());
//                            //成功
//                            Log.d("WriteFileUtil",
//                                    "call(WriteFileUtil.java:182)Success");
//
//                        } else {
//                            //失败
//                            Log.d("WriteFileUtil",
//                                    "call(WriteFileUtil.java:187)提交失败");
//                            XBLog.d("call(WriteFileUtil.java:174)提交记录失败!");
//
//                        }
//                    }
//                });
//    }
//
//    //解除订阅
//    public void cancelSubScribe() {
//        if (subscribe != null && !subscribe.isUnsubscribed()) {
//            subscribe.unsubscribe();
//        }
//    }
//
//
//    /**
//     * 保存文件到外置sd卡中
//     */
//    public void saveFileToOutSD(final String fileName1, final String fileName2) {
//        Query query = new Query().invoke();
//        Observable<List<SwipeEntity>> swipe = query.getSwipe();
//        Observable<List<JSONObject>> card = query.getCard();
//
//        subscribe = Observable.zip(swipe, card, new Func2<List<SwipeEntity>, List<JSONObject>, Boolean>() {
//            @Override
//            public Boolean call(List<SwipeEntity> swipeEntities, List<JSONObject> cardPayEntities) {
//                FileOutputStream swipeStaram = null;
//                FileOutputStream cardStaram = null;
//                String rootPath = getSecondaryStoragePath() + "/" + subPath;
//                File file = new File(rootPath);
//                if (!file.exists()) {
//                    file.mkdir();
//                }
//                try {
//                    if (swipeEntities != null && !swipeEntities.isEmpty()) {
//                        File saveFile = new File(file, fileName1);
//
//                        swipeStaram = new FileOutputStream(saveFile);
//                        for (int i = 0; i < swipeEntities.size(); i++) {
//                            swipeStaram.write(swipeEntities.get(i).getBiz_data_single().getBytes());
//                            swipeStaram.write("\r\n".getBytes());
//                        }
//                        swipeStaram.close();
//                    }
//
//                    //刷卡写文件
//                    if (cardPayEntities != null && !cardPayEntities.isEmpty()) {
//
//                        File saveFile = new File(file, fileName2);
//
//                        cardStaram = new FileOutputStream(saveFile);
//                        for (int i = 0; i < cardPayEntities.size(); i++) {
//                            cardStaram.write(cardPayEntities.get(i).toString().getBytes());
//                            cardStaram.write("\r\n".getBytes());
//                        }
//                        cardStaram.close();
//                    }
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    return false;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//                return true;
//            }
//        }).subscribeOn(Schedulers.io())
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean aBoolean) {
//                        if (aBoolean) {
//                            //保存上传日期
//                            CommonSharedPreferences.put("lastTimePushFile", DateUtil.getCurrentDate());
//                        }
//                    }
//                });
//    }
//
//
//    public List<JSONObject> getCardJSonList() {
//        List<JSONObject> listjson = new ArrayList<>();
//        List<CardPayEntity> list = DBManager.getCardPayListSaveFile();
//        Log.d("WriteFileUtil",
//                "getCardJSonList(WriteFileUtil.java:246)list.size()=" + list.size());
//        for (int i = 0; i < list.size(); i++) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject = CardPayEntity.toJSon(list.get(i));
//            listjson.add(jsonObject);
//        }
//
//        return listjson;
//    }
//
//
//    //获取外部SD卡
//    public String getSecondaryStoragePath() {
//        String outPath = null;
//        try {
//            StorageManager sm = (StorageManager) BusApp.getInstance().getSystemService(STORAGE_SERVICE);
//            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", (Class<?>) null);
//            String[] paths = (String[]) getVolumePathsMethod.invoke(sm, (Object) null);
//            // second element in paths[] is secondary storage path
//            outPath = paths.length <= 1 ? null : paths[1];
//            return outPath;
//        } catch (Exception e) {
//            outPath = "/storage/sdcard1";
//            Log.d("TestActivity",
//                    "getSecondaryStoragePath(TestActivity.java:98)" + e.getMessage());
//        }
//        return outPath;
//    }
//
//    private class Query {
//
//        private Observable<List<SwipeEntity>> swipe;
//        private Observable<List<JSONObject>> card;
//
//        private Observable<List<SwipeEntity>> getSwipe() {
//            return swipe;
//        }
//
//        private Observable<List<JSONObject>> getCard() {
//            return card;
//        }
//
//        private Query invoke() {
//            //扫码Observable
//            swipe = Observable.create(new Observable.OnSubscribe<List<SwipeEntity>>() {
//                @Override
//                public void call(Subscriber<? super List<SwipeEntity>> subscriber) {
//                    subscriber.onNext(DBManager.getSwipeEntityListSaveFile());
//                }
//            });
//
//            //刷卡Observable
//            card = Observable.create(new Observable.OnSubscribe<List<JSONObject>>() {
//                @Override
//                public void call(Subscriber<? super List<JSONObject>> subscriber) {
//                    subscriber.onNext(getCardJSonList());
//                }
//            });
//            return this;
//        }
//    }
//}
