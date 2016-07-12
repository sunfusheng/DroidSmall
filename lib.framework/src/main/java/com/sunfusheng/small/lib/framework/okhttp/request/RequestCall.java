package com.sunfusheng.small.lib.framework.okhttp.request;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.sunfusheng.small.lib.framework.okhttp.OkHttpProxy;
import com.sunfusheng.small.lib.framework.okhttp.callback.OkHttpCallBack;
import com.sunfusheng.small.lib.framework.util.FastJsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestCall {

    private OkHttpRequest okHttpRequest;
    private Request request;
    private Call call;

    private long readTimeOut = 30; // 单位为秒
    private long writeTimeOut = 30; // 单位为秒
    private long connTimeOut = 30; // 单位为秒

    private OkHttpClient clone;

    public RequestCall(OkHttpRequest request) {
        this.okHttpRequest = request;
    }

    public RequestCall readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public RequestCall writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public RequestCall connTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;
        return this;
    }

    public Call getCall() {
        return call;
    }

    public Request getRequest() {
        return request;
    }

    public OkHttpRequest getOkHttpRequest() {
        return okHttpRequest;
    }

    public Call generateCall(OkHttpCallBack callback) {
        request = generateRequest(callback);

        if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0) {
            readTimeOut = readTimeOut > 0 ? readTimeOut : OkHttpProxy.DEFAULT_MILLISECONDS;
            writeTimeOut = writeTimeOut > 0 ? writeTimeOut : OkHttpProxy.DEFAULT_MILLISECONDS;
            connTimeOut = connTimeOut > 0 ? connTimeOut : OkHttpProxy.DEFAULT_MILLISECONDS;

            clone = OkHttpProxy.getInstance().getOkHttpClient().newBuilder()
                    .readTimeout(readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                    .connectTimeout(connTimeOut, TimeUnit.SECONDS)
                    .build();

            call = clone.newCall(request);
        } else {
            call = OkHttpProxy.getInstance().getOkHttpClient().newCall(request);
        }
        return call;
    }

    private Request generateRequest(OkHttpCallBack callback) {
        return okHttpRequest.generateRequest(callback);
    }

    public Response execute() throws IOException {
        generateCall(null);
        return call.execute();
    }

    // 异步请求回调
    public void execute(OkHttpCallBack callback) {
        generateCall(callback);
        if (callback != null) {
            callback.onStart(request);
        }
        OkHttpProxy.getInstance().execute(this, callback);
    }

    // 同步解析成对象
    public <T> T execute(Class<T> clazz) throws IOException {
        generateCall(null);
        Response execute = call.execute();
        String response = execute.body().string();

        String status = execute.isSuccessful()? "Success":"Error code: "+execute.code();
        OkHttpProxy.printResponseInfo(execute.request(), response, status);

        return FastJsonUtil.parseJson(response, clazz);
    }

    // 同步解析成对象
    public <T> T execute(String parseWhat, Class<T> clazz) throws IOException, JSONException {
        generateCall(null);
        Response execute = call.execute();
        String response = execute.body().string();

        String status = execute.isSuccessful()? "Success":"Error code: "+execute.code();
        OkHttpProxy.printResponseInfo(execute.request(), response, status);

        if (!TextUtils.isEmpty(parseWhat)) {
            JSONObject jsonObject = new JSONObject(response);
            return JSON.parseObject(jsonObject.getString(parseWhat), clazz);
        }
        return JSON.parseObject(response, clazz);
    }

    // 同步解析成列表
    public <T> ArrayList<T> executeForList(String parseWhat, Class<T> clazz) throws IOException, JSONException {
        generateCall(null);
        Response execute = call.execute();
        String response = execute.body().string();

        String status = execute.isSuccessful()? "Success":"Error code: "+execute.code();
        OkHttpProxy.printResponseInfo(execute.request(), response, status);

        if (!TextUtils.isEmpty(parseWhat)) {
            JSONObject jsonObject = new JSONObject(response);
            return new ArrayList<T>(JSON.parseArray(jsonObject.getString(parseWhat), clazz));
        }
        return new ArrayList<T>(JSON.parseArray(response, clazz));
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }
}
