package com.example.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.example.aidldemo.IActionCall;
import com.example.aidldemo.IServiceCallMainAction;
import com.example.aidldemo.utils.MainCallServiceUtils;

public class ActionService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMainActionCall;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private IActionCall.Stub mMainActionCall = new IActionCall.Stub() {
        @Override
        public String doCommond(int cmd, String params) throws RemoteException {
            return MainCallServiceUtils.getInstance().mainCallServiceCmd(cmd, params);
        }

        @Override
        public void registerListener(IServiceCallMainAction listener) throws RemoteException {
            MainCallServiceUtils.getInstance().registerCallMainProcessListener(listener);
        }

        @Override
        public void unRegisterListener(IServiceCallMainAction listener) throws RemoteException {
            MainCallServiceUtils.getInstance().unRegisterCallMainProcessListener(listener);
        }
    };
}
