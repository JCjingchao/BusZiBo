package com.szxb.buspay.base;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * 作者: Tangren on 2017/7/8
 * 包名：com.szxb.onlinbus.base
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public interface BaseModel {
    /**
     * 请求JSONObject数据
     *
     * @param what what在这里作为标示
     * @param url  请求地址
     * @param map  请求参数
     */
    void requestJSONObjectData(int what, String url, Map<String, Object> map, RequestListener listener);


    interface RequestListener<T> {

        void onAllSuccess(int what, JSONObject result);

        void onFail(int what,String msg);
    }
}
