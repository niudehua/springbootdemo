package cn.niudehua.springbootdemo.util;

import cn.niudehua.springbootdemo.listener.EasyExcelListener;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Consumer;


/**
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

    public static <T> void webReadExcel(MultipartFile file, Consumer<List<T>> consumer, Class<T> clazz, int threshold, Integer sheetNo, String sheetName) throws IOException {
        AnalysisEventListener<T> listener = genListener(consumer, clazz, threshold);
        webReadExcel(file, listener, clazz, sheetNo, sheetName);
    }

    public static <T> void webReadExcel(MultipartFile file, Consumer<List<T>> consumer, Class<T> clazz, int threshold, Integer sheetNo) throws IOException {
        AnalysisEventListener<T> listener = genListener(consumer, clazz, threshold);
        webReadExcel(file, listener, clazz, sheetNo);
    }

    public static <T> void webReadExcel(MultipartFile file, Consumer<List<T>> consumer, Class<T> clazz, int threshold, String sheetName) throws IOException {
        AnalysisEventListener<T> listener = genListener(consumer, clazz, threshold);
        webReadExcel(file, listener, clazz, sheetName);
    }

    public static <T> void webReadExcel(MultipartFile file, Consumer<List<T>> consumer, Class<T> clazz, int threshold) throws IOException {
        AnalysisEventListener<T> listener = genListener(consumer, clazz, threshold);
        webReadExcel(file, clazz, listener);
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

    private static <T> void webReadExcel(MultipartFile file, Class<T> clazz, AnalysisEventListener<T> listener) throws IOException {
        EasyExcelFactory.read(file.getInputStream(), clazz, listener).sheet().doRead();
    }

    private static <T> AnalysisEventListener<T> genListener(Consumer<List<T>> consumer, Class<T> clazz, int threshold) {
        return new EasyExcelListener<>(clazz, consumer, threshold);
    }


}