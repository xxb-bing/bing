package com.fh.utils;

public enum ServerEnum {

    ACCOUNT_OR_PWD_ISNULL(1001,"账号或者密码不存在"),
    ACCOUNT_ERROR(5001,"账号异常，请联系管理员")
    ,ACCOUNT_NOT_EXIST(1002,"账号不存在请联系管理员")
    ,ACCOUNT_NOT_INVALID(1003,"账号无效请联系管理员")
    ,PWD_ERROR(1004,"密码输入有误，请重新输入")
    ,RIGHT_NO(1005,"该用户没有权限访问，请联系管理员")
    ,TOKEN_TIMEOUT(6001,"token失效了，请重新登录")
    ,EXPORT_NULL(7001,"导出数据是空的")
    ,LEAVE_DATE_ERROR(7001,"请假时间选择有误")
    ,APPROVAL_NO_PERMISSION(8001,"没有权限审批")
    ,SESSION_SHIXIAO(8003,"session失效")
    ,PERMISSION_NO_ACCESS(9001,"没有权限访问")
    ,PHONE_ISNULL(1006,"手机号不能为空")
    ,CHECKEDCODE_ISNULL(1007,"验证码不能为空")
    ,CHECKEDCODE_ERROR(1008,"验证码错误")
    ,SAFETY_ERROR(1009,"接口验签失败")
    ,SERVER_CONNECT_ERROR(8001,"服务器连接超时")
    ,SERVER_BUSYNESS(8002,"服务器请求忙碌")
    ,SERVER_NO_EXCEPTION(8003,"服务器未知异常")
    ,SUCCESS(200,"成功")
    ,ERROR(500,"失败")
    ;
    private ServerEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
    }
    private Integer code;
    private String message;


    public Integer getCode() {
        return code;
    }

    public  String getMessage() {
        return message;
    }
}
