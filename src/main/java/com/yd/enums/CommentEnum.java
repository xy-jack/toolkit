package com.yd.enums;

/**
 * 公用枚举类
 */
public enum CommentEnum {

    SUCCESS("0", "成功"),
    ERROR("-1", "失败")

    ;

    private String code;
    private String message;

    CommentEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
