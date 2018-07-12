package com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean;

/**
 *  红包群列表信息
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  15:56  
 */

public class GrabRedEnvelopeModule_Bean_RedGroupListInfo {
    private String id;
    private String house_id;
    private String hbcount;
    private String endtime;
    private String hbstatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getHbcount() {
        return hbcount;
    }

    public void setHbcount(String hbcount) {
        this.hbcount = hbcount;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getHbstatus() {
        return hbstatus;
    }

    public void setHbstatus(String hbstatus) {
        this.hbstatus = hbstatus;
    }
}
