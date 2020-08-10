package com.example.cloneicaller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


import java.lang.reflect.Method;

public class CallStateReceiver extends BroadcastReceiver {

    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    public static String incomingNumber = "";
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.e("AAAA", "state");
        incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            Toast.makeText(context, "Ringing State Number is - " + incomingNumber, Toast.LENGTH_LONG).show();
            Log.e("AAAA", "state: " + incomingNumber);
            Intent intent1 = new Intent(context, DialogBeforeCallActivity.class);
            context.startService(intent1);
            if ((incomingNumber != null) && incomingNumber.equals("6505551212")) {
                disconnectCall();
            }
        }

        //Outgoing call detect
        else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            Log.e("ABC", "started");
            Intent intent1 = new Intent(context, DialogBeforeCallActivity.class);
            context.startService(intent1);
        } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            Log.e("ABC", "ended");
            Intent i = new Intent(context, DialogOutgoingActivity.class);
            context.startService(i);
            DialogBeforeCallActivity.removeView();
        }

        checkPermission(context);

    }

    public void checkPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.getPackageName()));
                ((Activity) context).startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        } else {
            context.startService(new Intent(context, DialogBeforeCallActivity.class));
        }
    }

    public void disconnectCall() {
        try {

            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
