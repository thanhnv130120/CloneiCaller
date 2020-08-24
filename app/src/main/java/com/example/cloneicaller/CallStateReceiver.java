package com.example.cloneicaller;

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
import android.os.IBinder;
import android.provider.BlockedNumberContract;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.android.internal.telephony.ITelephony;
import com.example.cloneicaller.Room.BlockItemDatabase;
import com.example.cloneicaller.common.Common;
import com.example.cloneicaller.item.BlockerPersonItem;

import java.lang.reflect.Method;
import java.util.List;

public class CallStateReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferencesLie = context.getSharedPreferences("blockLieCall", Context.MODE_PRIVATE);
        SharedPreferences preferencesBlockAdvertise = context.getSharedPreferences("blockAdvertiseCall", Context.MODE_PRIVATE);
        SharedPreferences preferencesBlockCall = context.getSharedPreferences("blockUnknownCall", Context.MODE_PRIVATE);
        SharedPreferences preferencesBlockForeign = context.getSharedPreferences("blockForeign", Context.MODE_PRIVATE);
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.e("AAAA", "state");
        BlockItemDatabase database = Room.databaseBuilder(context.getApplicationContext(), BlockItemDatabase.class,
                "blockItems")
                .allowMainThreadQueries()
                .build();
        List<BlockerPersonItem> items = database.getItemDao().getItems();
        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            Toast.makeText(context, "Ringing State Number is - " + incomingNumber, Toast.LENGTH_LONG).show();
            Log.e("AAAA", "state: " + incomingNumber);
            Intent intent2 = new Intent(context, DialogBeforeCallActivity.class);
            context.startService(intent2);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                List<BlockerPersonItem> blockerItem = Common.checkRealDialer(items);
                if ((incomingNumber != null)) {
                    if (!Common.checkUnknown(incomingNumber, context) && preferencesBlockCall.getBoolean("checked",false)==true) {
                        Log.e("Broadcast", "Unknown?Checked!");
                        Toast.makeText(context, "Unknown?Checked !", Toast.LENGTH_LONG).show();
                        CallStateReceiver.endCall(context);
                    }
                    if (Common.checkInside(incomingNumber, Common.checkLier(blockerItem)) && preferencesLie.getBoolean("checked",false)) {
                        Log.e("Broadcast", "Lie or Owe?Checked!");
                        Toast.makeText(context, "Lie or Owe?Checked !", Toast.LENGTH_LONG).show();
                        CallStateReceiver.endCall(context);
                    }
                    if (Common.checkInside(incomingNumber, Common.checkAdvertise(blockerItem)) && preferencesBlockAdvertise.getBoolean("checked",false)==true) {
                        Log.e("Broadcast", "Advertised?Checked!");
                        Toast.makeText(context, "Advertised?Checked !", Toast.LENGTH_LONG).show();
                        CallStateReceiver.endCall(context);
                    }
                    if (preferencesBlockForeign.getBoolean("checked",false) == true) {
                        Log.e("Broadcast", "Foreign number?Checked!");
                        Toast.makeText(context, "Foreign number?Checked!", Toast.LENGTH_LONG).show();
                        CallStateReceiver.endCall(context);
                    }
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
//            DialogBeforeCallActivity.removeView();
            }

            checkPermission(context);
        }
    }
    ITelephony iTelephony;
    private static final String TAG = null;
    public static String incommingNumber;

    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    public static String incomingNumber = "";


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
                Log.e("AAAA", e.getMessage());
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
