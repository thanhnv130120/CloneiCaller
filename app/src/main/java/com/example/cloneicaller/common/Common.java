package com.example.cloneicaller.common;

import android.app.Person;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;

import com.example.cloneicaller.item.ItemPerson;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Common {
    public static final int VIEW_TYPE_GROUP = 0;
    public static final int VIEW_TYPE_PERSON = 1;
    public static final int RESULT_CODE= 1000;
    public static List<String> alphabet_available = new ArrayList<>();
    public static ArrayList<ItemPerson> sortList (ArrayList<ItemPerson>people){
        Collections.sort(people, new Comparator<ItemPerson>() {
            @Override
            public int compare(ItemPerson o1, ItemPerson o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return people;
    }
    public static ArrayList<ItemPerson>addAlphabet(ArrayList<ItemPerson>list){
        int i = 0;
        ArrayList<ItemPerson>customList = new ArrayList<>();
        ItemPerson firstPerson = new ItemPerson();
        firstPerson.setName(String.valueOf(list.get(0).getName().charAt(0)));
        firstPerson.setViewType(VIEW_TYPE_GROUP);
        alphabet_available.add(String.valueOf(list.get(0).getName().charAt(0)));
        customList.add(firstPerson);
        for (i = 0; i < list.size()-1; i++) {
            ItemPerson itemPerson = new ItemPerson();
            char name1 = list.get(i).getName().charAt(0);
            char name2 = list.get(i+1).getName().charAt(0);
            if(name1==name2){
                list.get(i).setViewType(VIEW_TYPE_PERSON);
                customList.add(list.get(i));
            }
            else {
                list.get(i).setViewType(VIEW_TYPE_PERSON);
                customList.add(list.get(i));
                itemPerson.setName(String.valueOf(name2));
                itemPerson.setViewType(VIEW_TYPE_GROUP);
                alphabet_available.add(String.valueOf(name2));
                customList.add(itemPerson);
            }
        }
        list.get(i).setViewType(VIEW_TYPE_PERSON);
        customList.add(list.get(i));
        return customList;
    }
    public static int findPositionWithName(String name, ArrayList<ItemPerson>list){
        for (int i = 0; i < list.size() ; i++) {
            if (list.get(i).getName().equals(name)){
                return i;
            }
        }
        return 1;
    }
    public static ArrayList<String>getAlphabet(){
        ArrayList<String>result = new ArrayList<>();
        for (int i = 65; i < 90; i++) {
            char character = (char)i;
            result.add(String.valueOf(character));
        }
        return result;
    }
    public static ArrayList<ItemPerson>resolverArrayList(ItemPerson itemPerson, Context context){
        ArrayList<ItemPerson>personArrayList = new ArrayList<>();
        if(itemPerson==null){
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String selection = null;
            String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            String[] selectionArgs = null;
            String sortOrder = null;
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                personArrayList.add(new ItemPerson(name,-1,num));
            }
        }
        return personArrayList;
    }
    public static ArrayList<String>covertToStringArray(ArrayList<ItemPerson>people){
        ArrayList<String>name = new ArrayList<>();
        for (int i = 0; i < people.size(); i++) {
            name.add(people.get(i).getName());
        }
        return name;
    }
    public static void disconnectCall(){
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
    public static boolean checkUnknown(String number, Context context){
        boolean identified = false;
        ArrayList<ItemPerson>personArrayList = new ArrayList<>();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String selection = null;
            String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            String[] selectionArgs = null;
            String sortOrder = null;
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                personArrayList.add(new ItemPerson(name,-1,num));
            }
        for (ItemPerson itemPerson:personArrayList) {
            if(number==itemPerson.getNumber()){
                identified = true;
            }
        }
        return identified;
    }
}
