package com.example.aidldemo.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.widget.Toast;

import com.example.aidldemo.IServiceCallMainAction;
import com.example.aidldemo.MyApplication;

import static com.example.aidldemo.AIDLCmdConst.MAIN_CALL_SERVICE_CMD_ASK_FOR_DINNER;
import static com.example.aidldemo.AIDLCmdConst.MAIN_CALL_SERVICE_CMD_ASK_FOR_WECHAT;

/**
 * 主进程调用Service进程的方法，运行在Service进程的binder线程池
 */
public class MainCallServiceUtils {

    private static MainCallServiceUtils mInstances;
    private RemoteCallbackList<IServiceCallMainAction> mCallbackList = new RemoteCallbackList<>();
    private IServiceCallMainAction mListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private MainCallServiceUtils(){}

    public static MainCallServiceUtils getInstance(){
        if(mInstances == null){
            synchronized (MainCallServiceUtils.class){
                if(mInstances == null){
                    mInstances = new MainCallServiceUtils();
                }
            }
        }
        return mInstances;
    }

    public String mainCallServiceCmd(int cmd, String params){
        String result = "";
        switch (cmd){
            case MAIN_CALL_SERVICE_CMD_ASK_FOR_WECHAT:
                getWeChat();
                break;
            case MAIN_CALL_SERVICE_CMD_ASK_FOR_DINNER:
                replyInvitedDinner();
                break;
        }
        return result;
    }

    public void registerCallMainProcessListener(IServiceCallMainAction listener){
        if(mCallbackList != null){
            mCallbackList.register(listener);
        }
    }

    public void unRegisterCallMainProcessListener(IServiceCallMainAction listener){
        if(mCallbackList != null){
            mCallbackList.unregister(listener);
        }
    }

    public synchronized String doCallMainCmd(int cmd,String params){
        String result = "";
        if(mCallbackList == null){
            return result;
        }
        if(mListener != null){
            try {
                result = mListener.doCallMainCmd(cmd, params);
            } catch (RemoteException e) {
                e.printStackTrace();
            };
        }else{
            int n = mCallbackList.beginBroadcast();
            for(int i=0; i<n; i++){
                mListener = mCallbackList.getBroadcastItem(i);
                if(mListener != null){
                    try {
                        result = mListener.doCallMainCmd(cmd, params);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    };
                }
            }
            mCallbackList.finishBroadcast();
        }
        return result;
    }

    private String getWeChat(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.getApplication(), "XiaoFang", Toast.LENGTH_SHORT).show();
            }
        });
        return "XiaoFang";
    }

    private String replyInvitedDinner(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.getApplication(), "ToNight", Toast.LENGTH_SHORT).show();
            }
        });
        return "ToNight";
    }
}
