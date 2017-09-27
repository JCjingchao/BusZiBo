package com.szxb.buspay.interfaces;

/**
 * 作者: Tangren on 2017/7/14
 * 包名：com.szxb.onlinbus.interfaces
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public interface OnPushTask<T> {

    void task(T entity);
    void task(int type,T entity);
    void message(String msg);
}
