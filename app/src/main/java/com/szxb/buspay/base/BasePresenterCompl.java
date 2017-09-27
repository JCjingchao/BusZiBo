package com.szxb.buspay.base;

import com.alibaba.fastjson.JSONObject;
import com.szxb.buspay.http.CallServer;
import com.szxb.buspay.http.HttpListener;
import com.szxb.buspay.http.JsonRequest;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.Map;

/**
 * 作者: Tangren on 2017/7/8
 * 包名：com.szxb.onlinbus.base
 * 邮箱：996489865@qq.com
 * TODO:网络请求处理层
 */

public class BasePresenterCompl<T> implements BaseModel {

    private JsonRequest request;

    protected void cancelRequest(int what) {
        if (request != null && !request.isCanceled())
            request.cancelBySign(what);
    }

    @Override
    public void requestJSONObjectData(int what, String url, Map<String, Object> map, RequestListener listener) {
        if (null == url)
            throw new IllegalArgumentException("map or url must no null");
        request = new JsonRequest(url, RequestMethod.POST);
        request.setCancelSign(what);
        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        if (map != null)
            request.add(map);
        CallServer.getHttpclient().add(what, request, new RequestHttpListener(listener));
    }

    private static class RequestHttpListener implements HttpListener<JSONObject> {

        private RequestListener listener;

        RequestHttpListener(RequestListener listener) {
            this.listener = listener;
        }

        @Override
        public void success(int what, Response<JSONObject> response) {
            Logger.d("what=" + what + ",返回结果:" + response.get().toString());
            if (listener != null) {
                if (response.get() != null) {
                    listener.onAllSuccess(what, response.get());
                } else listener.onFail(what, "请求结果为空!");
            }
        }

        @Override
        public void fail(int what, String e) {
            Logger.e("what=" + what + ",失败了" + e);
            if (listener != null) listener.onFail(what, e);
        }
    }
}
