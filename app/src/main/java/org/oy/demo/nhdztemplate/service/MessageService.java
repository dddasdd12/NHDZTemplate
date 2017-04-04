package org.oy.demo.nhdztemplate.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import org.oy.demo.nhdztemplate.UserAidl;


/**
 * 通信
 * Created by Mro on 2017/4/3.
 */
public class MessageService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    private final UserAidl.Stub mIBinder = new UserAidl.Stub() {
        @Override
        public String getUserName() throws RemoteException {
            return "5257794";
        }

        @Override
        public String getUserPwd() throws RemoteException {
            return "19880915";
        }
    };

}
