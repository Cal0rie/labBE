package com.youfan.common.enums;

public enum DelFlag {
    NOTDEL(0, "未删除"), DEL(2, "删除"),;

    private final Integer code;
    private final String info;

    DelFlag(Integer code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public Integer getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
