package com.study.distribute.lock.mapper;

import com.study.distribute.lock.domain.LocksInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LockerMapper {

    @Select("select * from resource_lock")
    List<LocksInfo> listAllLocksInfo();

    @Select("select 1")
    int getInt();

}
