package com.szxb.buspay.base;

/**
 * 作者: Tangren on 2017/7/8
 * 包名：com.szxb.onlinbus.base
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public interface BaseView {

    void onSuccess(int what, String msg);

    void onFail(int what, String msg);
}
