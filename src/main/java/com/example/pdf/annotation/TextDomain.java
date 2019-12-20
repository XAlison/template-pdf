package com.example.pdf.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TextDomain {

    /**
     * 是否将其按字符拆分 eg:身份证号码按字符拆分逐位填入
     */
    boolean isSplit() default false;

    /**
     * 字体大小
     */
    int fontSize() default 12;
}
