package com.example.aidldemo;

/**
 * AIDL命令行常量
 */
public class AIDLCmdConst {

    //主进程调用Service进程
    public static final int MAIN_CALL_SERVICE_CMD_ASK_FOR_WECHAT = 1;
    public static final int MAIN_CALL_SERVICE_CMD_ASK_FOR_DINNER = MAIN_CALL_SERVICE_CMD_ASK_FOR_WECHAT + 1;

    //Service进程调用主进程
    public static final int SERVICE_CALL_MAIN_CMD_ASK_FOR_NAME = 10000;
}
