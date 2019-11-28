package com.study.distribute.lock.domain;

import java.sql.Timestamp;

public class LockerInfo {
    private int id;
    private String resourceName;
    private String nodeInfo;
    private int count;
    private String description;
    private Timestamp updateTime;
    private Timestamp createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(String nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "LocksInfo{" +
                "id=" + id +
                ", resourceName='" + resourceName + '\'' +
                ", nodeInfo='" + nodeInfo + '\'' +
                ", count=" + count +
                ", description='" + description + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }
}
