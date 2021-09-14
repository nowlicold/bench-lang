package com.bench.lang.base.web.enums;

import com.bench.common.enums.EnumBase;

/**
 * @className CommonMimeEnum
 * @autor cold
 * @DATE 2021/7/3 23:57
 **/
public enum CommonMimeEnum implements EnumBase {

    JSON("application/json"),

    YAML("text/yaml"),

    HTML("text/html"),

    JS("application/javascript"),

    TXT("text/plain"),

    XML("application/xml"),

    PNG("image/png"),

    OCTET_STREAM("application/octet-stream");;

    private String type;

    /**
     * @param type
     */
    private CommonMimeEnum(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

    @Override
    public String message() {
        // TODO Auto-generated method stub
        return type;
    }

    @Override
    public Number value() {
        return null;
    }
}
