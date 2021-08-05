package cn.niudehua.springbootdemo.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;


/**
 * EasyExcel 工具类
 *
 * @author deng
 */
@Slf4j
public class EasyExcelUtils {
    private EasyExcelUtils() {
    }

    public static void webWriteExcel(HttpServletResponse response, List<?> objects, Class<?> clazz, String fileName) {
        webWriteExcel(response, objects, clazz, fileName, fileName);
    }

    public static void webWriteExcel(HttpServletResponse response, List<?> objects, Class<?> clazz, String fileName, String sheetName) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ExcelTypeEnum.XLSX.getValue());
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            writerExcel(objects, clazz, sheetName, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writerExcel(List<?> objects, Class<?> clazz, String sheetName, OutputStream outputStream) {
        EasyExcelFactory.write(outputStream, clazz).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet(sheetName).doWrite(objects);
    }

    public static <T> void webReadExcel(MultipartFile file, AnalysisEventListener<T> listener, Class<T> clazz, Integer sheetNo, String sheetName) throws IOException {
        EasyExcelFactory.read(file.getInputStream(), clazz, listener).sheet(sheetNo, sheetName).doRead();
    }

    public static <T> void webReadExcel(MultipartFile file, AnalysisEventListener<T> listener, Class<T> clazz, Integer sheetNo) throws IOException {
        EasyExcelFactory.read(file.getInputStream(), clazz, listener).sheet(sheetNo).doRead();
    }

    public static <T> void webReadExcel(MultipartFile file, AnalysisEventListener<T> listener, Class<T> clazz, String sheetName) throws IOException {
        EasyExcelFactory.read(file.getInputStream(), clazz, listener).sheet(sheetName).doRead();
    }

    public static <T> void webReadExcel(MultipartFile file, Class<T> clazz, AnalysisEventListener<T> listener) throws IOException {
        EasyExcelFactory.read(file.getInputStream(), clazz, listener).sheet().doRead();
    }

    public static <T> AnalysisEventListener<T> getListener(Class<T> clazz, Consumer<List<T>> consumer, int threshold) {
        return new AnalysisEventListener<T>() {

            private final List<T> list = new LinkedList<>();

            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                list.add(t);
                if (list.size() == threshold) {
                    consumer.accept(list);
                    list.clear();
                }
            }

            /**
             * 所有数据解析完成了 都会来调用
             *
             * @param analysisContext context
             */
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                if (list.size() > 0) {
                    consumer.accept(list);
                    list.clear();
                }
            }

            /**
             * 校验excel头部格式，必须完全匹配
             *
             * @param headMap 传入excel的头部（第一行数据）数据的index,name
             * @param context context
             */
            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                super.invokeHeadMap(headMap, context);
                if (clazz != null) {
                    try {
                        Map<Integer, String> indexNameMap = getIndexNameMap(clazz);
                        Set<Integer> keySet = indexNameMap.keySet();
                        String msg;
                        for (Integer key : keySet) {
                            if (StringUtils.isBlank(headMap.get(key))) {
                                msg = String.format("解析excel出错，第[%s]列为空，请传入正确格式的[%s]", key, indexNameMap.get(key));
                                throw new ExcelAnalysisException(msg);
                            }
                            if (!headMap.get(key).equals(indexNameMap.get(key))) {
                                msg = String.format("解析excel出错，第[%s]列[%s]错误，请传入正确格式的[%s]", key, headMap.get(key), indexNameMap.get(key));
                                throw new ExcelAnalysisException(msg);
                            }
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            }

            /**
             * 获取注解里ExcelProperty的value，用作校验excel
             *
             * @param clazz clazz
             * @return java.util.Map<java.lang.Integer, java.lang.String>
             * @throws NoSuchFieldException exception
             */
            public Map<Integer, String> getIndexNameMap(Class<T> clazz) throws NoSuchFieldException {
                Map<Integer, String> result = new HashMap<>(16);
                Field field;
                Field[] fields = clazz.getDeclaredFields();
                for (Field item : fields) {
                    field = clazz.getDeclaredField(item.getName());
                    ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                    if (excelProperty != null) {
                        int index = excelProperty.index();
                        String[] values = excelProperty.value();
                        StringBuilder value = new StringBuilder();
                        for (String v : values) {
                            value.append(v);
                        }
                        result.put(index, value.toString());
                    }
                }
                return result;
            }

        };

    }
}
