package com.youfan.common.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.youfan.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;

import java.util.Date;
import java.util.TimeZone;

@Slf4j
public class DateConverter implements Converter<Date> {

    @Override
    public Class supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.DATE;
    }

    @Override
    public Date convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        if (cellData.getType().equals(CellDataTypeEnum.NUMBER)) {

            String dataStr = cellData.getNumberValue()+"";
            try {
                Double doubleStr = Double.parseDouble(dataStr);
                Date d = DateUtil.getJavaDate(doubleStr, TimeZone.getTimeZone("Asia/Shanghai"));;
                log.info("number类型日期转换为：{}", doubleStr);

                if (DateUtils.parseStrToDate("yyyy-MM-dd", "9999-01-01").before(d)) {
                    log.error("字符串：{}日期格式错误。", dataStr);
                    return DateUtils.parseStrToDate("yyyy-MM-dd", "9999-01-01");
                }
                return d;
            }catch (Exception e){
                log.error("DateConverter 日期转换失败:{}",dataStr,e);
                return DateUtils.parseStrToDate("yyyy-MM-dd","9999-01-01");
            }

        } else if (cellData.getType().equals(CellDataTypeEnum.STRING)) {

            String dateStr = cellData.getStringValue();
            try {
                Date date = DateUtils.parseDate2(dateStr);
                return date;
            }catch (Exception e){
                log.error("DateConverter 日期转换失败:{}",dateStr,e);
                return DateUtils.parseStrToDate("yyyy-MM-dd","9999-01-01");
            }
        } else {
            return null;
        }

    }

    @Override
    public Date convertToJavaData(ReadConverterContext<?> context) throws Exception {
        return this.convertToJavaData(context.getReadCellData(), context.getContentProperty(), context.getAnalysisContext().currentReadHolder().globalConfiguration());

    }



}
