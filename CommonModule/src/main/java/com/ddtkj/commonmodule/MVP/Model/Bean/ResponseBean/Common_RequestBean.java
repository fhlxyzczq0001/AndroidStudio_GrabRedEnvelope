package com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean;
/**
 * 网络请求返回对象
 */
public class Common_RequestBean {

    private String code;
    private String msg;
    private Object data;

    private int type = 0;//仅用于订单号查询充值结果返回
    private String url = "";//服务器维护时的静态界面地址

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {

        return url;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", type=" + type +
                ", url='" + url + '\'' +
                '}';
    }
}
