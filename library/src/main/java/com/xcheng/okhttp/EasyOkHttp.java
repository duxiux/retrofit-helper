package com.xcheng.okhttp;


import android.util.Log;

import com.xcheng.okhttp.callback.OkCall;
import com.xcheng.okhttp.request.GetRequest;
import com.xcheng.okhttp.request.OkHttpCall;
import com.xcheng.okhttp.request.OkConfig;
import com.xcheng.okhttp.request.PostFormRequest;
import com.xcheng.okhttp.request.PostStrRequest;
import com.xcheng.okhttp.util.EasyPreconditions;

/**
 * Created by chengxin on 17/6/22
 */
public class EasyOkHttp {
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OkConfig sOkConfig;
    public static final String TAG = EasyOkHttp.class.getSimpleName();

    //be private
    private EasyOkHttp() {
    }

    /**
     * init EasyOkHttp with okConfig
     */
    public static void init(OkConfig okConfig) {
        synchronized (EasyOkHttp.class) {
            if (sOkConfig != null) {
                Log.e(TAG, "try to initialize OkConfig which had already been initialized before");
                return;
            }
            sOkConfig = EasyPreconditions.checkNotNull(okConfig, "okConfig==null");
        }
    }


    /**
     * like  JobManager.create(this) JobManager.instance()
     */
    public static OkConfig getOkConfig() {
        if (sOkConfig == null) {
            synchronized (EasyOkHttp.class) {
                EasyPreconditions.checkState(sOkConfig != null, "EasyOkHttp must be init with OkConfig before using");
            }
        }
        return sOkConfig;
    }

    public static GetRequest.Builder get(String url) {
        return new GetRequest.Builder().url(url);
    }

    public static PostStrRequest.Builder postStr(String url) {
        return new PostStrRequest.Builder().url(url);
    }

    public static PostFormRequest.Builder postForm(String url) {
        return new PostFormRequest.Builder().url(url);
    }

    public static void cancel(Object tag) {
        for (OkCall<?> okCall : OkHttpCall.getCalls()) {
            if (okCall.request().tag().equals(tag)) {
                okCall.cancel();
            }
        }
    }

    public static void cancelAll() {
        for (OkCall<?> okCall : OkHttpCall.getCalls()) {
            okCall.cancel();
        }
    }
}

