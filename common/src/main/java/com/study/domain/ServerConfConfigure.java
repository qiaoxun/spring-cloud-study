package com.study.domain;

import java.util.List;

public class ServerConfConfigure {
    private List<String> addList;
    private List<String> updateList;
    private List<String> removeList;

    public List<String> getAddList() {
        return addList;
    }

    public void setAddList(List<String> addList) {
        this.addList = addList;
    }

    public List<String> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<String> updateList) {
        this.updateList = updateList;
    }

    public List<String> getRemoveList() {
        return removeList;
    }

    public void setRemoveList(List<String> removeList) {
        this.removeList = removeList;
    }

    @Override
    public String toString() {
        return "ServerConfConfigure{" +
                "addList=" + addList +
                ", updateList=" + updateList +
                ", removeList=" + removeList +
                '}';
    }
}
