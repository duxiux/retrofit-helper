package com.xcheng.okhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.xcheng.okhttp.error.EasyError;
import com.xcheng.okhttp.request.OkResponse;

import java.io.IOException;

import okhttp3.Response;

public class BitmapParser extends ErrorParser<Bitmap> {

    @NonNull
    @Override
    public OkResponse<Bitmap> parseNetworkResponse(OkCall<Bitmap> okCall, Response response) throws IOException {
        if (response.isSuccessful()) {
            return OkResponse.success(BitmapFactory.decodeStream(response.body().byteStream()));
        }
        return OkResponse.error(EasyError.create("response error"));
    }
}