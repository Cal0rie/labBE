package com.youfan.common.handler;

import com.youfan.common.annotation.Excel;
import com.youfan.common.core.text.Convert;
import com.youfan.common.utils.DateUtils;
import com.youfan.common.utils.IdCodeUtils;
import com.youfan.common.utils.StringUtils;
import com.youfan.common.utils.poi.ExcelHandlerAdapter;
import com.youfan.common.utils.poi.ExcelUtil;
import com.youfan.common.utils.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * 自定义的事件处理器
 * 处理每一行数据读取
 * 实现接口
 *
 * @author youfan
 */
@Slf4j
public class SheetHandler<T> implements XSSFSheetXMLHandler.SheetContentsHandler {

    /**
     * 批量处理梳理
     */
    public Integer batchNum;

    public int failCount;

    /**
     * 开始解析的行号，第一行时，startRow为0
     */
    public Integer startRow;

    /**
     * 封装的entity对象
     */
    public Class<?> entity;

    /**
     * 当前解析的行号
     */
    private Integer currentRow;


    /**
     * 传入解析数据的service对象
     */
    public BiConsumer<List<T>,List<String>> uploadService;

    /**
     * 临时解析对象的构造器
     */
    Constructor<?> constructor;
    Constructor<?> noArgsConstructor;
    /**
     * 接收解析对象值
     */
    public List<T> list = new ArrayList<>();

    public List<String> codes = new ArrayList<>();

    /**
     * 解析头部数据
     */
    public List<String> headList = new ArrayList<>();

    Map<String, Integer> cellMap = new HashMap<>();
    /**
     * 解析单元格
     */
    public List<String> valueList = new ArrayList<>();

    public Map<Integer,String> valueMaps = new HashMap<>();

    /**
     * 实体@Excel注解属性集合
     */
    List<Object[]> fields = new ArrayList<>();

    Map<Integer, Object[]> fieldsMap = new HashMap<>();

    /**
     * 解析的列号,默认0为第一列
     */
    private Integer cellNum = 0;


    public SheetHandler(Integer batchNum, Integer startRow, BiConsumer<List<T>,List<String>> uploadService, Class<?> entity) throws Exception {
//        constructor = entity.getDeclaredConstructor(new Class[]{Map.class});
        noArgsConstructor = entity.getDeclaredConstructor();
        this.batchNum = batchNum == null ? 1000 : batchNum;
        this.startRow = startRow;
        this.uploadService = uploadService;
        this.entity = entity;
         this.fields = ExcelUtil.getFields(entity);
    }

    /**
     * 当开始解析某一行的时候触发
     * i:行索引
     */
    @Override
    public void startRow(int row) {
        currentRow = row;
    }

    /**
     * 当结束解析某一行的时候触发
     * i:行索引
     */
    @Override
    public void endRow(int row) {
        try {
            cellNum = 0;
            //建立实体类属性与excel列号的关系
            if (this.currentRow < startRow) {
                for (Object[] objects : fields)
                {
                    Excel attr = (Excel) objects[1];
                    //根据实体类注解中的name, 获取属性对应的列号
                    Integer column = cellMap.get(attr.name());
                    if (column != null)
                    {
                        fieldsMap.put(column, objects);
                    }
                }
                log.info("解析头部数据：{}" , headList);
            }

            if (!StringUtils.isEmpty(valueMaps.get(0))&&this.currentRow >= startRow) {
//                log.info("解析表格数据：{}" , valueMaps);
//                T data = (T) constructor.newInstance(valueMaps);
                T entity = null;
                for (Map.Entry<Integer, Object[]> entry : fieldsMap.entrySet())
                {
                    Object val = valueMaps.get( entry.getKey());

                    // 如果不存在实例则新建.
                    entity = (entity == null ? (T)noArgsConstructor.newInstance() : entity);
                    // 从map中得到对应列的field.
                    Field field = (Field) entry.getValue()[0];
                    Excel attr = (Excel) entry.getValue()[1];
                    // 取得类型,并根据对象类型设置值.

                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType)
                    {
                        String s = Convert.toStr(val);

                        if (StringUtils.endsWith(s, ".0"))
                        {
                            val = StringUtils.substringBefore(s, ".0");
                        }
                        else
                        {
                            String dateFormat = field.getAnnotation(Excel.class).dateFormat();
                            if (StringUtils.isNotEmpty(dateFormat))
                            {
                                val = DateUtils.parseDateToStr(dateFormat, (Date) val);
                            }
                            else
                            {
                                val = Convert.toStr(val);
                            }
                        }
                    }
                    else if ((Integer.TYPE == fieldType || Integer.class == fieldType) && StringUtils.isNumeric(Convert.toStr(val)))
                    {
                        val = Convert.toInt(val);
                    }
                    else if (Long.TYPE == fieldType || Long.class == fieldType)
                    {
                        val = Convert.toLong(val);
                    }
                    else if (Double.TYPE == fieldType || Double.class == fieldType)
                    {
                        val = Convert.toDouble(val);
                    }
                    else if (Float.TYPE == fieldType || Float.class == fieldType)
                    {
                        val = Convert.toFloat(val);
                    }
                    else if (BigDecimal.class == fieldType)
                    {
                        val = Convert.toBigDecimal(val);
                    }
                    else if (Date.class == fieldType)
                    {
//                        if(null!=val&&val.equals("20212.1.30")){
//                            log.info("");
//                        }
                        if (val instanceof String)
                        {
//                            String dateFormat = field.getAnnotation(Excel.class).dateFormat();
//                            if (StringUtils.isNotEmpty(dateFormat))
//                            {
//                                val = DateUtils.parseStrToDate(dateFormat,  val.toString());
//                            }
//                            else
//                            {
                            ((String) val).replaceAll(",",".").replaceAll(".·",".");
                                val = DateUtils.parseDate2(val);
//                            }


                        }
                        else if (val instanceof Double)
                        {
                            val = DateUtil.getJavaDate((Double) val);
                        }
                    }
                    else if (Boolean.TYPE == fieldType || Boolean.class == fieldType)
                    {
                        val = Convert.toBool(val, false);
                    }
                    if (StringUtils.isNotNull(fieldType))
                    {
                        String propertyName = field.getName();
                        if (StringUtils.isNotEmpty(attr.targetAttr()))
                        {
                            propertyName = field.getName() + "." + attr.targetAttr();
                        }
                        else if (StringUtils.isNotEmpty(attr.readConverterExp()))
                        {
                            val = ExcelUtil.reverseByExp(Convert.toStr(val), attr.readConverterExp(), attr.separator());
                        }
                        else if (StringUtils.isNotEmpty(attr.dictType()))
                        {

                            val = ExcelUtil.reverseDictByExp(Convert.toStr(val), attr.dictType(), attr.separator());
                        }
                        else if (!attr.handler().equals(ExcelHandlerAdapter.class))
                        {
                            val = ExcelUtil.dataFormatHandlerAdapter2(val, attr);
                        }
//                        else if (Excel.ColumnType.IMAGE == attr.cellType() && StringUtils.isNotEmpty(pictures))
//                        {
//                            PictureData image = pictures.get(row.getRowNum() + "_" + entry.getKey());
//                            if (image == null)
//                            {
//                                val = "";
//                            }
//                            else
//                            {
//                                byte[] data = image.getData();
//                                val = FileUtils.writeImportBytes(data);
//                            }
//                        }
                        ReflectUtils.invokeSetter(entity, propertyName, val);
                    }
                }
                ReflectUtils.invokeMethod(entity,"initDatas",new Class[]{},new Object[]{});
                String idCode = ReflectUtils.invokeMethod(entity,"getIdCode",new Class[]{},new Object[]{});

               if(null!=idCode && IdCodeUtils.isValidatedAllIdCode(idCode)) {
                   list.add(entity);
                   codes.add( ReflectUtils.invokeMethod(entity,"initUserParam",new Class[]{},new Object[]{}));
               }else{
                   failCount++;
                   log.warn("身份证号码：{}格式不正确。",idCode);
               }

            }

            if (list.size() >= batchNum) {
                // 回调接口，处理数据
                log.info("分页保存数据----------------");
                saveData(list,codes);
            }
        } catch (Exception e) {
            log.error("-----------",e);
            e.printStackTrace();
        } finally {
            headList.clear();
            valueList.clear();
            valueMaps.clear();
        }
    }

    /**
     * 对行中的每一个表格进行处理
     * cellReference: 单元格名称
     * value：数据
     * xssfComment：批注
     */
    @Override
    public void cell(String cellReference, String value, XSSFComment xssfComment) {
        try {
            if (currentRow < startRow) {
                // 加载表头数据
                headList.add(value);
                cellMap.put(value,ExcelUtil.excelToNum(cellReference.replaceAll("[1-9]{1}[0-9]*","")));
            } else {
                // 获取表格数据
//                log.info("列数据：{}，单元格名称：{}",value,cellReference);
                valueMaps.put(ExcelUtil.excelToNum(cellReference.replaceAll("[1-9]{1}[0-9]*","")),value);
                valueList.add(value);
            }
            cellNum++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析完sheet，多个sheet会回调多次
     */
    @Override
    public void endSheet() {
        log.info("解析Excel完成------");
        saveData(list,codes);
    }

    private void saveData(List<T> dataList,List<String> codes) {
        try {
            if (dataList.size() > 0) {
//                codes.add("failCount" + failCount);
                uploadService.accept(dataList.stream().distinct().collect(Collectors.toList()), codes.stream().distinct().collect(Collectors.toList()));
                dataList.clear();
                codes.clear();
                failCount = 0;
            }
        }catch (Exception e){
            log.error("导入数据失败",e);
        }
    }
}

