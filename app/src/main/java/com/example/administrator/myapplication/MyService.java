package com.example.administrator.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private MyBinder myBinder=new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("test","bind");
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test", "create");
    }

    @Override
    public void onDestroy() {
        Log.d("test","destory");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("test","start command");
        return Service.START_STICKY;
    }
    public class MyBinder extends Binder{
        public void callMethod(){
            Log.d("test","service call");
        }
    }
}
