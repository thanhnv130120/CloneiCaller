package com.example.cloneicaller.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.cloneicaller.Models.DataModel;

import java.util.List;

@Dao
public interface PhoneDBDAO {

    @Query("SELECT * FROM DataBean")
    List<DataModel.DataBeanX.DataBean> getAll();

    //     thêm 1 hoặc nhiều User
    @Insert
    long[] insertAll(List<DataModel.DataBeanX.DataBean> dataBeanList);

//    @Update
//    long[] updatePhone(List<DataModel.DataBeanX.DataBean> dataBeanList);
    
}
