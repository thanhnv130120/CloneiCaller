package com.example.cloneicaller.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.example.cloneicaller.Models.Data;

public class PhoneDBQueryTask {

//    public PhoneDB phoneDB;
//
//
//    public PhoneDBQueryTask(Context context) {
//        phoneDB = Room.databaseBuilder(context,
//                PhoneDB.class, "phonedb.db").build();
//    }
//
//    public interface OnQuery<T> {
//        void onResult(T t);
//    }
//
//    public void insertData(OnQuery<long[]> onQuery, Data... data) {
//        new InsertUsersAsyncTask(onQuery).execute();
//    }
//
//    private class InsertUsersAsyncTask extends AsyncTask<Data, Void, long[]> {
//        OnQuery onQuery;
//
//        public InsertUsersAsyncTask(OnQuery onQuery) {
//            this.onQuery = onQuery;
//        }
//
//        @Override
//        protected long[] doInBackground(Data... data) {
//            return phoneDB.phoneDBDAO().insertAll(data);
//        }
//
//        @Override
//        protected void onPostExecute(long[] longs) {
//            super.onPostExecute(longs);
//            this.onQuery.onResult(longs);
//        }
//    }
}
