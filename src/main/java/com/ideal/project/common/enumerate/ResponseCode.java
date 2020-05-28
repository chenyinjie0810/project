package com.ideal.project.common.enumerate;


import com.ideal.project.common.entity.BaseCode;

/**
 * 接口相关返回码，*** *** 编码方式
 */
public enum ResponseCode implements BaseCode {
    SUCCESS("0", "成功"),

    /*
     * 系统通用错误码 001开头
     */
    INVALID_PARAMETER("001001", "参数错误"),
    SYS_ERROR("001002", "系统内部错误"),

    //登录错误码，100开头
    NO_LOGIN("100001", "未登录"),
    AUTH_ERROR("100002", "用户名密码错误"),
    NO_ACCOUNT("100003", "账号不存在"),
    AUTH_REFRESH_ERROR("100004", "非法用户刷新token"),
    AUTH_REFRESH_EXPIRED("100005", "RefreshToken过期，请重新登陆"),
    AUTH_NO_ROLE("100006","用户无操作系统权限，请联系管理员"),
    AUTH_TOKEN_EXPIRED("100007","Token过期，请重新刷新token"),
    UNAUTHORIZED("100008","Token为空"),
    ;

    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
