package com.example.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

/**
 * 自定义Dialog类，
 * 帮你简化Dialog创建，使用Build类快速创建。
 * 通过传入的布局、按钮、Text，快速获取到Dialog，不提供默认模板
 * 返回对象是Dialog，你依然可以拿到返回Dialog进行封装和修改
 * <p>
 * 使用指南
 * 1.如何定义Dialog中的属性
 * 如果是初始化属性直接去布局修改.
 * 示例:<TextView android:textColor="@android:color/holo_blue_bright"/>
 * <p>
 * 如果有在点击事件中修改Dialog修改按钮属性
 * 示例 onClick(v) 拿v来设置
 * <p>
 * 如果需要在点击事件修改Dialog其他属性
 * 先Build创建完Dialog，再使用@getView()获取View的点击事件，再拿Dialog对象设置
 * <p>
 * 2.大部分方法使用了注解来避免传入错误，使用的注解都是androidx.annotation里面的注解，没有自定义，注意导包。
 * <p>
 * 3.设置宽和高的属性
 * 使用ViewGroup.LayoutParams的字段设置填充效果，或者直接传入具体大小
 * 示例：
 * .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
 * .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
 * .setWidth(10)
 * <p>
 * 4.如果出现
 * 找不到***方法，原因这个view不是继承***
 * 请查看传入的View是否带有***的方法，如果有直接修改当前方法或者增加重载方法
 */

public class CommonDialog extends Dialog {
    private Build mBuild;

    public CommonDialog(@NonNull Build build) {
        // 使用自定义Dialog样式
        super(build.mContext, build.mStyle);
        mBuild = build;
        build();
    }

    private void build() {
        setContentView(mBuild.mRoot);
        // 设置宽和高
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = this.mBuild.mHeight;
        params.width = this.mBuild.mWidth;
        getWindow().setAttributes(params);

        // 设置点击Dialog以外的区域时Dialog消失
        setCanceledOnTouchOutside(mBuild.mCancel);
    }


    public <T extends View> T getView(@IdRes int viewId) {
        return this.mBuild.mRoot.findViewById(viewId);
    }

    public void setButtonOnClickListener(@IdRes int viewId,
                                         @NonNull View.OnClickListener onClickListener) {
        Button button = this.mBuild.mRoot.findViewById(viewId);
        button.setOnClickListener(v -> onClickListener.onClick(button));
    }


    public static class Build {
        private Context mContext;
        private View mRoot;
        private int mWidth;
        private int mHeight;
        private boolean mCancel;
        private int mStyle;

        public Build setContext(@NonNull Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public Build setLayout(@LayoutRes int mLayout) {
            mRoot = LayoutInflater.from(mContext).inflate(mLayout, null, false);
            return this;
        }

        public Build setWidth(int width) {
            mWidth = width;
            return this;
        }

        public Build setHeight(int height) {
            mHeight = height;
            return this;
        }

        public Build setText(@IdRes int viewId, @NonNull String text) {
            View view = mRoot.findViewById(viewId);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setText(text);
            } else {
                throw new NullPointerException("找不到setText方法，原因这个view不是继承TextView");
            }
            return this;
        }

        public Build setImageResource(@IdRes int viewId, @DrawableRes int drawableResId) {
            View view = mRoot.findViewById(viewId);
            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                imageView.setImageResource(drawableResId);
            } else {
                throw new NullPointerException("找不到setImageResource方法，原因这个view不是继承ImageView");
            }
            return this;
        }

        public Build setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
            View view = mRoot.findViewById(viewId);
            view.setOnClickListener(v -> onClickListener.onClick(v));
            return this;
        }

        public Build startAnimation(@IdRes int viewId, Animation animation) {
            View view = mRoot.findViewById(viewId);
            view.startAnimation(animation);
            animation.start();
            return this;
        }

        public Build setCanceledOnTouchOutside(boolean cancel) {
            mCancel = cancel;
            return this;
        }

        public Build setStyle(@StyleRes int style) {
            mStyle = style;
            return this;
        }

        public CommonDialog build() {
            return new CommonDialog(this);
        }

    }
}
