package com.bench.lang.base.enums;

import com.bench.common.enums.EnumBase;

/**
 * 移动设备系统枚举
 *
 * @author cold
 *
 * @version $Id: MobileSystemEnum.java, v 0.1 2016年1月4日 上午11:30:29 cold Exp $
 */
public enum MobileSystemEnum implements EnumBase {

    ANDROID("安卓系统"),

    IOS("苹果IOS"),

    UNKNOWN("未知");

    private String message;

    private MobileSystemEnum(String message) {
        this.message = message;
    }

    @Override
    public String message() {
        // TODO Auto-generated method stub
        return message;
    }

    @Override
    public Number value() {
        // TODO Auto-generated method stub
        return null;
    }

}