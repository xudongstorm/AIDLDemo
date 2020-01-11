package com.example.aidldemo.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.aidldemo.service.ActionService;
import com.example.aidldemo.IActionCall;
import com.example.aidldemo.IServiceCallMainAction;
import com.example.aidldemo.MyApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IPCUtils {

    private static IPCUtils mInstance;
    private IActionCall mActionCall;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private ExecutorService mThreadPool = new ThreadPoolExecutor(CPU_COUNT+1, 2*CPU_COUNT+1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private IPCUtils(){}

    public static IPCUtils getInstance(){
        if(mInstance == null){
            synchronized (IPCUtils.class){
                if(mInstance == null){
                    mInstance = new IPCUtils();
                }
            }
        }
        return mInstance;
    }

    public void bindService(){
        Intent intent = new Intent(MyApplication.getApplication(), ActionService.class);
        MyApplication.getApplication().bindService(intent, mCon, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mActionCall = IActionCall.Stub.asInterface(iBinder);
            registerMainProcessServiceCb();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            unRegisterMainProcessServiceCb();
            mActionCall = null;
        }
    };

    private void registerMainProcessServiceCb(){
        if(mActionCall == null){
            return;
        }
        try {
            mActionCall.registerListener(mServiceCallMainAction);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void unRegisterMainProcessServiceCb(){
        if(mActionCall == null){
            return;
        }
        try {
            mActionCall.unRegisterListener(mServiceCallMainAction);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private IServiceCallMainAction.Stub mServiceCallMainAction = new IServiceCallMainAction.Stub() {
        @Override
        public String doCallMainCmd(int cmd, String params) throws RemoteException {
            return ServiceCallMainUtils.getInstance().mainProcessReplayCmd(cmd, params);
        }
    };

    /**
     * 主进程调用Service进程调用此方法
     * @param cmd
     * @param params
     */
    public void doCallServiceCmd(final int cmd, final String params){
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mActionCall.doCommond(cmd, params);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Service进程调用主进程调用此方法
     * @param cmd
     * @param params
     */
    public void doCallMainCmd(final int cmd, final String params){
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                MainCallServiceUtils.getInstance().doCallMainCmd(cmd, params);
            }
        });
    }

}
