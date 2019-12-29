package com.study.distribute.lock.mysql.mapper;

import com.study.distribute.lock.mysql.domain.LockerInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LockerMapper {

    @Select("select id, resource_name resourceName, node_info nodeInfo, count, description, update_time updateTime, create_time createTime from resource_lock")
    List<LockerInfo> listAllLockerInfo();

    @Select("select id, resource_name resourceName, node_info nodeInfo, count, description, update_time updateTime, create_time createTime from resource_lock where resource_name=#{resource_name}")
    LockerInfo getLockerInfoByResourceName(@Param("resource_name") String resourceName);

    @Insert("insert into resource_lock(resource_name, node_info, count, description, update_time, create_time) values(#{resourceName}, #{nodeInfo}, #{count}, #{description}, #{updateTime, jdbcType=TIMESTAMP}, #{createTime, jdbcType=TIMESTAMP})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertData(LockerInfo lockerInfo);

    @Update("update resource_lock set count = count + 1 where resource_name=#{resource_name}")
    int increaseCountByOne(@Param("resource_name") String resourceName);

    @Update("update resource_lock set count = count - 1 where resource_name=#{resource_name}")
    int decreaseCountByOne(@Param("resource_name") String resourceName);

    @Update("delete from resource_lock where resource_name=#{resource_name}")
    int deleteLockerInfoByResourceName(@Param("resource_name") String resourceName);

}
