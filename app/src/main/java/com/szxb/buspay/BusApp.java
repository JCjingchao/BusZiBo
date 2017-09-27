package com.szxb.buspay;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.szxb.buspay.db.manager.DBCore;
import com.szxb.buspay.interfaces.IPosManage;
import com.szxb.buspay.manager.PosManager;
import com.szxb.buspay.task.TaskDelFile;
import com.szxb.buspay.task.TaskPushBillService;
import com.szxb.buspay.util.sound.SoundPoolUtil;
import com.szxb.xblog.AndroidLogAdapter;
import com.szxb.xblog.CsvFormatStrategy;
import com.szxb.xblog.DiskLogAdapter;
import com.szxb.xblog.FormatStrategy;
import com.szxb.xblog.PrettyFormatStrategy;
import com.szxb.xblog.XBLog;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 作者: Tangren on 2017/7/17
 * 包名：com.szxb.buspay
 * 邮箱：996489865@qq.com
 * TODO:一句话描述2017-09-27
 */

public class BusApp extends Application {

    private static PosManager manager;
    private static volatile BusApp instance = null;
    private Subject<Object, Object> RxBus = new SerializedSubject<>(PublishSubject.create());
    private final static String DB_NAME = "BUS_INFO";

    //是否存在SD卡,默认不存在
    private boolean isExistSD = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        manager = new PosManager();
        //初始化音源
        SoundPoolUtil.init(this);

        //初始化基本数据
        manager.loadFromPrefs();
        NoHttp.initialize(InitializationConfig.newBuilder(this)
                .networkExecutor(new OkHttpNetworkExecutor())
                .build());
        Logger.setDebug(true);
        DBCore.init(this, DB_NAME);
        //日志操作
        initLog(false);
        //文件过期处理,也可不在application中
        TaskDelFile.del(this, "bus");
        TaskPushBillService taskPushBillService = new TaskPushBillService();
        Intent intent = new Intent(BusApp.getInstance(), TaskPushBillService.class);
        startService(intent);
//        Cockroach.install(new Cockroach.ExceptionHandler() {
//            @Override
//            public void handlerException(final Thread thread, final Throwable throwable) {
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Log.d("App",
//                                    "run(App.java:56)" + throwable.toString());
//                        //   BusToast.showToast(BusApp.this, "异常错误", true);
//                        } catch (Throwable e) {
//
//                        }
//                    }
//                });
//            }
//        });
        Log.d("Strat","5");
    }

    private void initLog(boolean saveLog) {

        //日志策略
        FormatStrategy format = PrettyFormatStrategy.newBuilder()
                .tag("公交日志信息:")
                .build();
        //本地打印日志
        XBLog.addLogAdapter(new AndroidLogAdapter(format));

        //日志本地同步到文件,线上使用
        if (saveLog) {
            FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                    .tag("公交日志信息:")
                    .build();
            XBLog.addLogAdapter(new DiskLogAdapter(formatStrategy));
        }
    }



    public static BusApp getInstance() {
        if (instance == null) {
            synchronized (BusApp.class) {
                if (instance == null) {
                    instance = new BusApp();
                }
            }
        }
        return instance;
    }


    public static IPosManage getPosManager() {
        if (manager == null) {
            manager = new PosManager();
            manager.loadFromPrefs();
        }
        return manager;
    }


    private boolean hasObserVable() {
        return RxBus.hasObservers();
    }

    //发送
    public void post(Object o) {
        if (hasObserVable()) {
            RxBus.onNext(o);
        }
    }

    // 传递
    public <T> Observable<T> tObservable(Class<T> bus) {
        return RxBus.ofType(bus);

    }

    //取消订阅
    public void unRegister(Subscription subscription) {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


    public boolean isExistSD() {
        return isExistSD;
    }

    public void setExistSD(boolean existSD) {
        isExistSD = existSD;
    }
}
