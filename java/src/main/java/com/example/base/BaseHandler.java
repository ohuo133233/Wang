package com.example.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public abstract class BaseHandler<T> extends Handler {

    private WeakReference<T> mWeakReference;

    protected BaseHandler(T t) {
        mWeakReference = new WeakReference<T>(t);
    }

    protected abstract void handleMessage(T t, Message msg);

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mWeakReference == null) {
            return;
        }
        T t = mWeakReference.get();
        if (t != null) {
            handleMessage(t, msg);
        }
    }
}
