package com.youfan.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.BiConsumer;

/**
 **/
@Slf4j
public class XssfSheetHandler {
    /**
     * @param inputStream 数据流
     * @param consumer    自定义回调
     * @param entity      解析数据实体
     * @param batchNum    批处理数量
     * @param startRow    excel解析正文行号
     * @throws Exception
     */
    public static void handlerData(InputStream inputStream, BiConsumer consumer,
                                   Class<?> entity, Integer batchNum, Integer startRow) throws Exception {
        //1.根据excel报表获取OPCPackage
        OPCPackage opcPackage = OPCPackage.open(inputStream);
        //2.创建XSSFReader
        XSSFReader reader = new XSSFReader(opcPackage);

        //3.获取SharedStringTable对象
        SharedStringsTable table = reader.getSharedStringsTable();
        //4.获取styleTable对象
        StylesTable stylesTable = reader.getStylesTable();
        //5.创建Sax的xmlReader对象
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        DataFormatter formatter = new CustomDataFormatter();
        XSSFSheetXMLHandler xmlHandler = new XSSFSheetXMLHandler(stylesTable, table,
                new SheetHandler(batchNum, startRow, consumer, entity), formatter,true);
        xmlReader.setContentHandler(xmlHandler);
        //7.逐行读取
        XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator) reader.getSheetsData();
        while (sheetIterator.hasNext()) {
            //每一个sheet的流数据
            InputStream stream = sheetIterator.next();
            InputSource is = new InputSource(stream);
            xmlReader.parse(is);
        }
    }

    private static class CustomDataFormatter extends DataFormatter {

        @Override
        public String formatRawCellContents(double value, int formatIndex, String formatString,
                                            boolean use1904Windowing) {

            // Is it a date?
            if (DateUtil.isADateFormat(formatIndex, formatString)) {
                if (DateUtil.isValidExcelDate(value)) {
                    Date d = DateUtil.getJavaDate(value, use1904Windowing);
                    try {
                        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
                    } catch (Exception e) {
                        log.error("Bad date value in Excel: " + d, e);
                    }
                }
            }
            return new DecimalFormat("##0.#####").format(value);
        }

    }
    }


