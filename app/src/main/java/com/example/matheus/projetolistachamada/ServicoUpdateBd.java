package com.example.matheus.projetolistachamada;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Matheus on 17/11/2017.
 */

public class ServicoUpdateBd extends IntentService {

    private int count;
    private boolean ativo;
    private boolean stopAll;
    public ServicoUpdateBd(String name) {
        super(name);

        count = 0;
        ativo = true;
        stopAll = true;

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (stopAll&& ativo && count<5){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            count++;
            Log.i("Script", "count"+ count);
        }
        ativo = true;
        count = 0;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        Bundle b = intent.getExtras();
        if(b !=null){
            int desligar = b.getInt("desligar");

            if(desligar == 1){
                stopAll = false;
            }
        }

        return super.onStartCommand(intent, flags, startId);


    }

    //    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//
//        Log.i("Script","onCreate()");
//        super.onCreate();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.i("Script","onStartCommand()");
//        worker w = new worker(startId);
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    class worker extends Thread{
//        public int count =0;
//        public int startId;
//        public boolean ativo = true;
//
//        public worker(int startId){
//            this.startId = startId;
//        }
//
//
//        @Override
//        public void run() {
//            super.run();
//
//            while (ativo && count <10){
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                count ++;
//                Log.i("Script","Count"+ count);
//            }
//
//            stopSelf(startId);
//        }
//    }


}
