package com.youfan.common.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class SexConverter implements Converter<String> {

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String sexStr = cellData.getStringValue();
        if(null !=sexStr){
            if(sexStr.equals("男")){
                sexStr="0";
            }else if(sexStr.equals("女")){
                sexStr="1";
            }else if(sexStr.equals("未知")){
                sexStr="2";
            }
        }
        return sexStr;
    }

    @Override
    public String convertToJavaData(ReadConverterContext<?> context) throws Exception {
        return this.convertToJavaData(context.getReadCellData(), context.getContentProperty(), context.getAnalysisContext().currentReadHolder().globalConfiguration());

    }


}
