package com.mcura.jaideep.queuemanagement.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mCURA1 on 4/8/2016.
 */
public class ScheduleModelResult implements Serializable {

    private List<ScheduleModel[]> list = new ArrayList<>();

    public List<ScheduleModel[]> getList() {
        return list;
    }

    public void setList(List<ScheduleModel[]> list) {
        this.list = list;
    }
}
