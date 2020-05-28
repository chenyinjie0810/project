package com.ideal.project.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.ideal.project.excelmodel.QuestionModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/5/27 14:45
 * 陈银杰
 */
public class EasyExcelUtil {

    /**
     * StringList 解析监听器
     */
    private static class StringExcelListener extends AnalysisEventListener {
        /**
         * 是否读取表格头部，默认为不读取
         */
        private boolean headFlag =false;
        /**
         * 自定义用于暂时存储data
         * 可以通过实例获取该值
         */
        private List<LinkedHashMap> datas = new ArrayList<>();

        @Override
        public void invokeHead(Map headMap, AnalysisContext context) {
            if (headFlag){
                headMap.put("headFlag",headFlag);
                datas.add((LinkedHashMap) headMap);
            }
        }
        /**
         * 每解析一行都会回调invoke()方法
         *
         * @param object
         * @param context
         */
        @Override
        public void invoke(Object object, AnalysisContext context) {
            LinkedHashMap map= (LinkedHashMap) object;
            map.put("headFlag",false);
            //数据存储到list，供批量处理，或后续自己业务逻辑处理。
            datas.add(map);
            //根据自己业务做处理
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            //解析结束销毁不用的资源
            //注意不要调用datas.clear(),否则getDatas为null
        }

        public List<LinkedHashMap> getDatas() {
            return datas;
        }

        public void setDatas(List<LinkedHashMap> datas) {
            this.datas = datas;
        }

        public StringExcelListener(boolean headFlag) {
            this.headFlag = headFlag;
        }

        public StringExcelListener() {
        }
    }

    /**
     * 使用 StringList 来读取Excel
     * @param inputStream Excel的输入流
     * @param headFlag 是否读取head头部
     * @return 返回 StringList 的列表
     */
    public static List<LinkedHashMap> readExcelWithMap(InputStream inputStream, boolean headFlag) {
        StringExcelListener listener = new StringExcelListener(headFlag);
        ExcelReader excelReader = EasyExcelFactory.read(inputStream ,listener).build();
        excelReader.readAll();
        excelReader.finish();
        return  listener.getDatas();
    }

    /**
     * 使用 StringList 来读取Excel
     * @param inputStream Excel的输入流
     * @return 返回 StringList 的列表
     */
    public static List<LinkedHashMap> readExcelWithMapBySheet(InputStream inputStream,ReadSheet readSheet) {
        StringExcelListener listener = new StringExcelListener();
        ExcelReader excelReader = EasyExcelFactory.read(inputStream ,listener).build();
        excelReader.read(readSheet);
        return  listener.getDatas();
    }

    /**
     * 模型解析监听器 -- 每解析一行会回调invoke()方法，整个excel解析结束会执行doAfterAllAnalysed()方法
     */
    private static class ModelExcelListener<E> extends AnalysisEventListener<E> {

        private List<E> dataList = new ArrayList<E>();

        /**
         * @desc: 读取特殊数据
         */
        @Override
        public void extra(CellExtra extra, AnalysisContext context) {
            switch (extra.getType()){
                case COMMENT:
                    break;
                case MERGE:
                    break;
                case HYPERLINK:
                    break;
                default:
            }
        }

        @Override
        public void invoke(E object, AnalysisContext context) {
            if (object instanceof QuestionModel){
            }
            dataList.add(object);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
        }

        public List<E> getDataList() {
            return dataList;
        }

        @SuppressWarnings("unused")
        public void setDataList(List<E> dataList) {
            this.dataList = dataList;
        }

    }

    /**
     * @desc: 使用模型 来读取Excel
     * @author: chenyj
     * @date: 2020/5/27
     * @param inputStream Excel的输入流
     * @param clazz 模型的类
     *
     * @return 返回 模型 的列表
     */
    public static <E> List<E> readExcelWithModel(InputStream inputStream, Class clazz) {
        // 解析每行结果在listener中处理
        ModelExcelListener<E> listener = new ModelExcelListener<E>();
        ExcelReader excelReader = EasyExcelFactory.read(inputStream,clazz,listener).build();
        excelReader.readAll();
        return listener.getDataList();
    }

    /**
     * @desc: 使用模型 来读取Excel
     * @author: chenyj
     * @date: 2020/5/27
     * @param inputStream Excel的输入流
     * @param clazz 模型的类
     * @param readSheet 指定sheet
     * @return 返回 模型 的列表
     */
    public static <E> List<E> readExcelWithModelBySheet(InputStream inputStream, Class clazz,ReadSheet readSheet) {
        // 解析每行结果在listener中处理
        ModelExcelListener<E> listener = new ModelExcelListener<E>();
        ExcelReader excelReader = EasyExcelFactory.read(inputStream,clazz,listener).extraRead(CellExtraTypeEnum.MERGE).build();
        excelReader.read(readSheet);
        excelReader.finish();
        return listener.getDataList();
    }

    /**
     * 使用 StringList 来写入Excel
     * @param outputStream Excel的输出流
     * @param data 要写入的以StringList为单位的数据
     * @param table 配置Excel的表的属性
     * @param excelTypeEnum Excel的格式(XLS或XLSX)
     */
    public static void writeExcelWithStringList(OutputStream outputStream, List<List<String>> data, Table table, ExcelTypeEnum excelTypeEnum) {
        //这里指定不需要表头，因为String通常表头已被包含在data里
        ExcelWriter writer = new ExcelWriter(outputStream, excelTypeEnum,false);
        //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系,无表头
        Sheet sheet1 = new Sheet(0, 0);
        writer.write0(data, sheet1,table);
        writer.finish();
    }

    /**
     * 使用 模型 来写入Excel
     *
     * @param outputStream Excel的输出流
     * @param data 要写入的以 模型 为单位的数据
     * @param clazz 模型的类
     * @param excelTypeEnum Excel的格式(XLS或XLSX)
     */
    public static void writeExcelWithModel(OutputStream outputStream, List<? extends BaseRowModel> data,
                                           Class<? extends BaseRowModel> clazz, ExcelTypeEnum excelTypeEnum)  {
        //这里指定需要表头，因为model通常包含表头信息
        ExcelWriter writer = new ExcelWriter(outputStream, excelTypeEnum,true);
        //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
        Sheet sheet1 = new Sheet(1, 0, clazz);
        writer.write(data, sheet1);
        writer.finish();
    }


    public static void main(String[] args) {
        try {
            InputStream inputStream=new FileInputStream("E:\\上海理想文档\\Normal_Cohort\\DG\\DGS questionnaire1.xlsx");
            //读入文件，每一行对应一个 Model，获取 Model 列表
           List<QuestionModel> objectList = EasyExcelUtil.readExcelWithModelBySheet(inputStream, QuestionModel.class,new ReadSheet(1));
            for(QuestionModel excelModel: objectList) {
                System.out.println(excelModel);
            }

            /*List<LinkedHashMap> lists = EasyExcelUtil.readExcelWithMapBySheet(inputStream,new ReadSheet(1));
            for (int i = 0; i < lists.size(); i++) {
                System.out.println("第"+i+"行的数据,有"+ lists.get(i).size()+"条数据，内容为："+JSON.toJSONString(lists.get(i)));
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
