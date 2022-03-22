package com.example.widget.toast;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java.R;

public class CustomToast {
    private static Toast toast = null;
    private int mLayout;
    private View toastRoot;
    private TextView mTextView;

    public void showSeatTempToast(Context context, String message) {
        if (context == null || TextUtils.isEmpty(message)) {
            return;
        }
        if (mLayout == 0) {
            toastRoot = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
        } else {
            toastRoot = LayoutInflater.from(context).inflate(mLayout, null);
        }

        mTextView = toastRoot.findViewById(R.id.text);
        mTextView.setText(message);
        WindowManager.LayoutParams parameter = new WindowManager.LayoutParams();
        parameter.type = WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG;
        toastRoot.setLayoutParams(parameter);
        if (toast == null) {
            toast = new Toast(context);
        }
        toast.setGravity(Gravity.TOP, 0, 48);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }
}

