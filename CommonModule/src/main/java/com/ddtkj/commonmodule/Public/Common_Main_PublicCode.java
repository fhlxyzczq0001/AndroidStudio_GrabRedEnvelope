package com.ddtkj.commonmodule.Public;

/**全局标识
 * @author: Administrator 杨重诚
 * @date: 2016/11/3:9:41
 */

public enum Common_Main_PublicCode {
    //===============投资==============
    GRAB_RED_ENVELOPE_TAB_HOME("红包"),
    GRAB_RED_ENVELOPE_TAB_USER("我的");

    // 定义私有变量
    private String nCode;

    // 构造函数，枚举类型只能为私有
    private Common_Main_PublicCode(String _nCode) {
        this.nCode = _nCode;
    }

    @Override
    public String toString() {
        return String.valueOf(this.nCode);
    }
}
