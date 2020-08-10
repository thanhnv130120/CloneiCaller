package com.example.cloneicaller.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.cloneicaller.Models.DataModel;

// khởi tạo cơ sở dữ liệu với bảng Data
@Database(entities = {DataModel.DataBeanX.DataBean.class}, version = 1, exportSchema = false)
public abstract class PhoneDB extends RoomDatabase {
    public abstract PhoneDBDAO phoneDBDAO();
}
