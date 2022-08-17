package com.youfan.common.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.youfan.common.utils.NumUtils;
import com.youfan.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntConverter implements Converter<Integer> {

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Integer i = 0;
        if (cellData.getType().equals(CellDataTypeEnum.NUMBER)) {

            return cellData.getNumberValue().intValue();

        } else if (cellData.getType().equals(CellDataTypeEnum.STRING)) {

            String dataStr = cellData.getStringValue();
            if (!StringUtils.isEmpty(dataStr) && NumUtils.isNumeric(dataStr)) {
                return Integer.parseInt(dataStr);
            } else if (!StringUtils.isEmpty(dataStr) && !NumUtils.isNumeric(dataStr)) {
                return -1;
            } else {
                return null;
            }

        }else{
            return null;
        }
    }

    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) throws Exception {
        return this.convertToJavaData(context.getReadCellData(), context.getContentProperty(), context.getAnalysisContext().currentReadHolder().globalConfiguration());

    }



}
