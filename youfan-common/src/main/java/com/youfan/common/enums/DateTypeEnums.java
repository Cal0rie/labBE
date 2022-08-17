package com.youfan.common.enums;

import com.youfan.common.utils.StringUtils;

public enum DateTypeEnums {
    VARCHAR("varchar", "字符串",""),
    INT("int", "整数",""),
    LONG("long", "长整数",""),
    DOUBLE("double", "双精度小数",""),
    FLOAT("float", "单精度小数",""),
    BIGDECIMAL("bigdecimal", "BigDecimal类型",""),
    DATE("date", "日期","yyyy-MM-dd"),
    BOOLEAN("boolean", "真假类型",""),
    IMG("img", "图片类型",""),

    INPUT("input", "输入框",""),
    TEXT("text", "文本框",""),
    NUMBER("number", "整数类型",""),
    //double
    PERCENT("percent", "百分比",""),
    //date
    DATETIME("datetime", "日期时间","yyyy-MM-dd HH:mm:ss"),
    TIME("time", "时间类型","HH:mm:ss"),
    COMBOBOX("combobox", "下拉框",""),
    ;

    private final String code;
    private final String info;
    private final String format;

    DateTypeEnums(String code, String info, String format)
    {
        this.code = code;
        this.info = info;
        this.format = format;
    }

    public String getCode()
    {
        return code;
    }

    public String getFormat() {
        return format;
    }

    public String getInfo()
    {
        return info;
    }

    public static String getFormatByCode(String code){
        if(!StringUtils.isEmpty(code)){
        for(DateTypeEnums dateTypeEnums:DateTypeEnums.values()){
            if(dateTypeEnums.getCode().equals(code)){
                return dateTypeEnums.getFormat();
            }
        }
        }
        return "";
    }

}
