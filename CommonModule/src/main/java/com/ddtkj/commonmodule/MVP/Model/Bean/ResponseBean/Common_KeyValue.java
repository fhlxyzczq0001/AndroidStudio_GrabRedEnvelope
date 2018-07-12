package com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean;

/**
 * Created by Administrator on 2017/6/15.
 *
 * @author lizhipei
 */

public class Common_KeyValue<T, T2> {
    private T key;
    private T2 value;

    public Common_KeyValue(T key, T2 value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public T2 getValue() {
        return value;
    }
}
