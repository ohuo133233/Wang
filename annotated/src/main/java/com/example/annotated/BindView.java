package com.example.annotated;

import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 保留到类级别
@Retention(RetentionPolicy.CLASS)
// 作用到字段上
@Target(ElementType.FIELD)
public @interface BindView {
    // 限制只能传入res里面的id
    @IdRes
    int value();
}
