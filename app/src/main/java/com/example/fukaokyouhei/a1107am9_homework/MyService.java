package com.example.fukaokyouhei.a1107am9_homework;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public IBinder onBind(Intent intent) {
        if (IServiceMethod.class.getName().equals(intent.getAction())) {
            // IServiceMethodのインスタンスを返す
            return isvc;
        }
        return null;
    }

    private IServiceMethod.Stub isvc = new IServiceMethod.Stub() {
        @Override
        public String CallServiceMethod(String a, String b) throws RemoteException {
            if(a.equals(b)){
                return "等しい";
            }
            return "等しくない";
        }
    };
}
