package com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean;

/**
 *  红包房间列表信息
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  15:56  
 */

public class GrabRedEnvelopeModule_Bean_RedRoomListInfo {
    private String id;
    private String category_id;
    private String housenum;
    private String housename;
    private String createdate;
    private String housestatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getHousenum() {
        return housenum;
    }

    public void setHousenum(String housenum) {
        this.housenum = housenum;
    }

    public String getHousename() {
        return housename;
    }

    public void setHousename(String housename) {
        this.housename = housename;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getHousestatus() {
        return housestatus;
    }

    public void setHousestatus(String housestatus) {
        this.housestatus = housestatus;
    }
}
