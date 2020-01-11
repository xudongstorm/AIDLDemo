package com.example.aidldemo.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.aidldemo.MyApplication;

import static com.example.aidldemo.AIDLCmdConst.SERVICE_CALL_MAIN_CMD_ASK_FOR_NAME;

/**
 * 处理Service进程调用主进程的回调，运行在主线程的binder线程池
 */
public class ServiceCallMainUtils {

    private static ServiceCallMainUtils mInstance;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ServiceCallMainUtils(){}

    public static ServiceCallMainUtils getInstance(){
        if(mInstance == null){
            synchronized (ServiceCallMainUtils.class){
                if(mInstance == null){
                    mInstance = new ServiceCallMainUtils();
                }
            }
        }
        return mInstance;
    }

    public String mainProcessReplayCmd(int cmd, String params){
        String result = "";
        switch (cmd){
            case SERVICE_CALL_MAIN_CMD_ASK_FOR_NAME:
                getName();
                break;
        }
        return result;
    }

    private String getName(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.getApplication(), "DongDong", Toast.LENGTH_SHORT).show();
            }
        });
        return "DongDong";
    }
}
