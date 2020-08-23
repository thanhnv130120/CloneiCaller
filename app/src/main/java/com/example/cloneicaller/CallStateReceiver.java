package com.example.cloneicaller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.BlockedNumberContract;
import android.telecom.TelecomManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.cloneicaller.call.ITelephony;

import java.lang.reflect.Method;

public class CallStateReceiver extends BroadcastReceiver {

    ITelephony iTelephony;
    private static final String TAG = null;
    public static String incommingNumber;
    String incno1= "9916090941";
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
//        Log.e("AAAA", "state");
//        incommingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//            //Toast.makeText(context, "Ringing State Number is - " + incomingNumber, Toast.LENGTH_LONG).show();
//            Log.e("AAAA", "state: " + incommingNumber);
////            if ((incomingNumber != null) && incomingNumber.equals("0836918988")) {
//////                telephonyService.silentRinger();
//////                telephonyService.endCall();
////                Log.e("HANG UP", incomingNumber);
////                endCall(context);
//////                disconnectCall();
////                disblock(context,intent);
////                TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
////                try {
////                    Class clazz = Class.forName(telephonyManager.getClass().getName());
////                    Method method = clazz.getDeclaredMethod("getITelephony");
////                    method.setAccessible(true);
////                    Log.e("AAAA","show block log");
////                    ITelephony telephonyService = (ITelephony) method.invoke(telephonyManager);
////                    telephonyService.endCall();
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//            } else {
//                Log.e("AAAA", "Wrong state");
//            }
//        }
        //        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        try {
//            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
//            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//            Log.e("AAAA", "state: " + incomingNumber);
//            Toast.makeText(context, incomingNumber, Toast.LENGTH_SHORT).show();
//            Class c = Class.forName(tm.getClass().getName());
//            Method m = c.getDeclaredMethod("getITelephony");
//            m.setAccessible(true);
//            ITelephony telephonyService = (ITelephony) m.invoke(tm);
//            if ((incomingNumber != null) && incomingNumber.equals("(650) 555-666")) {
//                telephonyService.silentRinger();
//                telephonyService.endCall();
//                Log.e("HANG UP", incomingNumber);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Bundle bundle = intent.getExtras();
//
//        if (null == bundle)
//            return;
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        try {
//            // Java reflection to gain access to TelephonyManager's
//            // ITelephony getter
//            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            Log.v(TAG, "Get getTeleService...");
//            Class c = Class.forName(tm.getClass().getName());
//            Method m = c.getDeclaredMethod("getITelephony");
//            m.setAccessible(true);
//            ITelephony telephonyService = (ITelephony) m.invoke(tm);
//            Bundle b = intent.getExtras();
//            incommingNumber = b.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
//            Log.v(TAG, incommingNumber);
//            Log.v(TAG, incno1);
//            if (incommingNumber.equals(incno1)) {
//                telephonyService = (ITelephony) m.invoke(tm);
//                telephonyService.silentRinger();
//                telephonyService.endCall();
//                Log.v(TAG, "BYE BYE BYE");
//            } else {
//
//                telephonyService.answerRingingCall();
//                Log.v(TAG, "HELLO HELLO HELLO");
//            }
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    //}

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
    public void blockedcall(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Cursor c = context.getContentResolver().query(BlockedNumberContract.BlockedNumbers.CONTENT_URI,
                    new String[]{BlockedNumberContract.BlockedNumbers.COLUMN_ID,
                            BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER,
                            BlockedNumberContract.BlockedNumbers.COLUMN_E164_NUMBER}, null, null, null);
            ContentValues values = new ContentValues();
            values.put(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER, "0836918988");
            Uri uri = context.getContentResolver().insert(BlockedNumberContract.BlockedNumbers.CONTENT_URI, values);
            context.getContentResolver().delete(uri, null, null);
        }
    }

    public static void endCall(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            ITelephony telephonyService;
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                Method m = tm.getClass().getDeclaredMethod("getITelephony");

                m.setAccessible(true);
                telephonyService = (ITelephony) m.invoke(tm);
                telephonyService.endCall();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
                TelecomManager tm = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
                tm.endCall();
        }
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

    public void disblock(Context context, Intent intent) {
        String serviceManagerName = "android.os.ServiceManager";
        String serviceManagerNativeName = "android.os.ServiceManagerNative";
        String telephonyName = "com.android.internal.telephony.ITelephony";
        Class<?> telephonyClass;
        Class<?> telephonyStubClass;
        Class<?> serviceManagerClass;
        Class<?> serviceManagerNativeClass;
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                Log.e("AAAA", "state: " + incomingNumber);
                Toast.makeText(context, incomingNumber, Toast.LENGTH_SHORT).show();
                Class c = Class.forName(telephonyName.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                ITelephony telephonyService = (ITelephony) m.invoke(telephonyName);
                if ((incomingNumber != null) && incomingNumber.equals("0836918988")) {
                    telephonyService.silentRinger();
                    telephonyService.endCall();
                    Log.e("HANG UP", incomingNumber);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
