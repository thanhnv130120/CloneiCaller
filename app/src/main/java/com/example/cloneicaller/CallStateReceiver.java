package com.example.cloneicaller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.BlockedNumberContract;
import android.telecom.TelecomManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.android.internal.telephony.ITelephony;
import com.example.cloneicaller.Models.Contact;
import com.example.cloneicaller.Models.DataModel;
import com.example.cloneicaller.Room.BlockItemDatabase;
import com.example.cloneicaller.Room.PhoneDB;
import com.example.cloneicaller.adapter.ListHistoryAdapter;
import com.example.cloneicaller.common.Common;
import com.example.cloneicaller.fragment.FragmentCallKeyboard;
import com.example.cloneicaller.item.BlockerPersonItem;
import com.google.android.gms.common.internal.Constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.cloneicaller.common.Common.formatPhoneNumber;
import static com.example.cloneicaller.common.Common.getCallDetails;

public class CallStateReceiver extends BroadcastReceiver {

    //Thanhnv
    ITelephony iTelephony;
    PhoneDB phoneDB;
    private static final String TAG = null;
    public static String incommingNumber;

    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    public static String incomingNumber, numberDiary;
    public static String outgoingNumber, outgoingNumber2, outgoingNumber3, phoneData = "";
    DataModel.DataBeanX.DataBean dataBean;
    public static String savedNumber;
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    String income;
    List<Contact> contactList;

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
//            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                List<BlockerPersonItem> blockerItem = Common.checkRealDialer(items);
                if ((incomingNumber != null)) {
                    if (!Common.checkUnknown(incomingNumber, context) &&
                            preferencesBlockCall.getBoolean("checked", false) == true) {
                        Log.e("Broadcast", "Unknown?Checked!");
                        Toast.makeText(context, "Unknown?Checked !", Toast.LENGTH_LONG).show();
                        endCall(context);
                    }
                    if (Common.checkInside(incomingNumber, Common.checkLier(blockerItem)) &&
                            preferencesLie.getBoolean("checked", false)) {
                        Log.e("Broadcast", "Lie or Owe?Checked!");
                        Toast.makeText(context, "Lie or Owe?Checked !", Toast.LENGTH_LONG).show();
                        endCall(context);
                    }
                    if (Common.checkInside(incomingNumber, Common.checkAdvertise(blockerItem)) &&
                            preferencesBlockAdvertise.getBoolean("checked", false) == true) {
                        Log.e("Broadcast", "Advertised?Checked!");
                        Toast.makeText(context, "Advertised?Checked !", Toast.LENGTH_LONG).show();
                        endCall(context);
                    }
                    if (preferencesBlockForeign.getBoolean("checked", false) == true) {
                        Log.e("Broadcast", "Foreign number?Checked!");
                        Toast.makeText(context, "Foreign number?Checked!", Toast.LENGTH_LONG).show();
                        endCall(context);
                    }
//                }
            }
        }

            //Outgoing call detect
        phoneDB = PhoneDB.getInstance(context);
        contactList = getCallDetails(context);
        Log.e("ABC", contactList.size() + "");

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            savedNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        } else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state1 = 0;
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state1 = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state1 = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state1 = TelephonyManager.CALL_STATE_RINGING;
            }


            onCallStateChanged(context, state1, number);
        }
    }

    private void onCallStateChanged(Context context, int state, String number) {
        if (lastState == state) {

            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                onIncomingCallReceived(context, number, callStartTime);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false;
                    callStartTime = new Date();
                    onOutgoingCallStarted(context, savedNumber, callStartTime);
                } else {
                    isIncoming = true;
                    callStartTime = new Date();
                    onIncomingCallAnswered(context, savedNumber, callStartTime);
                }

                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    onMissedCall(context, savedNumber, callStartTime);
                } else if (isIncoming) {
                    onIncomingCallEnded(context, savedNumber, callStartTime, new Date());
                } else {
                    onOutgoingCallEnded(context, savedNumber, callStartTime, new Date());
                }
                break;
        }
        lastState = state;
    }

    private void onOutgoingCallEnded(Context context, String savedNumber, Date callStartTime, Date date) {

        String phoneNumInDia = Common.findPhoneFromDiary(savedNumber, context);
        phoneNumInDia = Common.formatPhoneNumber(phoneNumInDia);

        String phoneNumInCallL = Common.findPhoneFromCallog(savedNumber, contactList, context);
        Log.e("ABC1232123", phoneNumInCallL + "");
        phoneNumInCallL = Common.formatPhoneNumber(phoneNumInCallL);
        Log.e("ABC1232123", phoneNumInCallL + "");
        savedNumber = Common.formatPhoneNumber(savedNumber);
        Log.e("ABCOutEnd", savedNumber + "");
        String dataPhone = "";
        try {
            dataBean = phoneDB.phoneDBDAO().getDataByPhone(savedNumber);
            dataPhone = dataBean.getPhone();
            Log.e("ABCOutEnd", dataBean.getPhone() + "");
        } catch (Exception e) {

        }
        if (savedNumber.equals(phoneNumInDia)) {
            context.startService(new Intent(context, DialogDisplayAfterCall1.class));
        } else if (savedNumber.equals(dataPhone) && !savedNumber.equals(phoneNumInCallL)) {
            context.startService(new Intent(context, DialogDisplayAfterCall2.class));
        } else if (!savedNumber.equals(phoneNumInCallL)) {
            context.startService(new Intent(context, DialogDisplayAfterCall4.class));
        }

    }

    private void onIncomingCallEnded(Context context, String savedNumber, Date callStartTime, Date date) {

        String phoneIncomeDiary = Common.findPhoneFromDiary(savedNumber, context);
        String phoneIncomeCallLog = Common.findPhoneFromCallog(savedNumber, contactList, context);
        Log.e("ABCNothing", phoneIncomeCallLog);
        String dataPhone = "";

        try {
            dataBean = phoneDB.phoneDBDAO().getDataByPhone(savedNumber);
            dataPhone = dataBean.getPhone();
            Log.e("ABCOutEnd", dataBean.getPhone() + "");
        } catch (Exception e) {

        }
        Log.e("ABCIncomeDiary", phoneIncomeDiary);
        if (phoneIncomeDiary.length() > 0 && !phoneIncomeDiary.equals("+84")) {
            context.startService(new Intent(context, DialogDisplayAfterCall1.class));
        } else if (dataPhone.length() > 0 && phoneIncomeCallLog.length() == 0) {
            context.startService(new Intent(context, DialogDisplayAfterCall2.class));
        } else if (phoneIncomeDiary.length() == 0 && phoneIncomeCallLog.length() == 0) {
            context.startService(new Intent(context, DialogDisplayAfterCall3.class));
        }
    }

    private void onOutgoingCallStarted(Context context, String savedNumber, Date callStartTime) {
        Log.e("ABCOutStarted", savedNumber + "");
    }

    private void onMissedCall(Context context, String savedNumber, Date callStartTime) {
        Log.e("ABCMissed", savedNumber + "");
    }

    private void onIncomingCallAnswered(Context context, String savedNumber, Date callStartTime) {
        Log.e("ABCInAns", savedNumber + "");
    }

    private void onIncomingCallReceived(Context context, String number, Date callStartTime) {
        Log.e("ABC", number + "");
    }


    public void blockedcall(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Cursor c = context.getContentResolver().query(BlockedNumberContract.BlockedNumbers.CONTENT_URI,
                    new String[]{BlockedNumberContract.BlockedNumbers.COLUMN_ID,
                            BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER,
                            BlockedNumberContract.BlockedNumbers.COLUMN_E164_NUMBER}, null,
                    null, null);
            ContentValues values = new ContentValues();
            values.put(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER, "0836918988");
            Uri uri = context.getContentResolver().insert(BlockedNumberContract.BlockedNumbers.CONTENT_URI, values);
            context.getContentResolver().delete(uri, null, null);
        }
    }
    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
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
            } catch (SecurityException e) {
                Log.e("AAAA", e.getMessage());
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            if ( ContextCompat.checkSelfPermission( context, android.Manifest.permission.ANSWER_PHONE_CALLS ) == PackageManager.PERMISSION_GRANTED) {
                TelecomManager tm = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
                tm.endCall();
            }else {
                Log.e("permission","can not check");
            }
        }
    }
//    public static void requestCallLogPermissionsForResult(Activity activity) {
//        Set<String> set = new HashSet<>();
//        set.addAll(Arrays.asList(PERMISSION_GROUP_CALL_LOG));
//        set.addAll(Arrays.asList(PERMISSION_GROUP_PHONE));
//        String[] permissions = set.toArray(new String[set.size()]);
//
//        ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE_PERMISSION_REQUEST);
//    }
    public void blockContact(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        Class clazz = null;
        try {
            clazz = Class.forName(telephonyManager.getClass().getName());
            Method method = clazz.getDeclaredMethod("getITelephony");
            method.setAccessible(true);
            ITelephony telephonyService = (ITelephony) method.invoke(telephonyManager);
            telephonyService.endCall();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
