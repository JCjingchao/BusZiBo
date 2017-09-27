package com.szxb.buspay.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.szxb.buspay.util.Utils;
import com.szxb.xblog.XBLog;

/**
 * 作者: Tangren on 2017/7/8
 * 包名：com.szxb.onlinbus.base
 * 邮箱：996489865@qq.com
 * TODO:Activity MVP基类
 */

public abstract class BaseMVPActivity<V, T extends BasePresenter<V>> extends BaseActivity implements BaseView {

    public T mPresenter;

    protected T getChildPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (null != getChildPresenter()) {
            mPresenter = getChildPresenter();
        }
        if (null != mPresenter)
            mPresenter.attachView((V) this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSuccess(int what, String msg) {

    }

    @Override
    public void onFail(int what, String msg) {

    }

    public void KeyEvent(byte[] bytes) {
        XBLog.d("KeyEvent(BaseMVPActivity.java:45)" + Utils.printHexBinary(bytes));
        try {

            for (int i = 0; i < bytes.length; i++) {
                XBLog.d("KeyEvent(BaseMVPActivity.java:49)" + bytes[i] + "----" + i);
                if (bytes[i] > 0) {
                    XBLog.d("KeyEvent(BaseMVPActivity.java:49)" + bytes[i] + "----" + i);
                    switch (i) {
                        case 0:
                            XBLog.d("KeyEvent(BaseMVPActivity.java:47)" + "ONE");
                            OneKey();
                            break;
                        case 1:
                            XBLog.d("KeyEvent(BaseMVPActivity.java:53)" + "TWO");
                            TwoKey();
                            break;
                        case 2:
                            XBLog.d("KeyEvent(BaseMVPActivity.java:57)" + "THREE");
                            ThreeKey();
                            break;
                        case 3:
                            XBLog.d("KeyEvent(BaseMVPActivity.java:61)" + "FOUR");
                            FourKey();
                            break;
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    public void OneKey() {

    }

    public void TwoKey() {

    }

    public void ThreeKey() {

    }

    public void FourKey() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter)
            mPresenter.detachView();
    }
}
