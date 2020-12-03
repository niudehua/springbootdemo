package cn.niudehua.springbootdemo.util;

import cn.niudehua.springbootdemo.domain.common.ExcelImportErrorDTO;
import cn.niudehua.springbootdemo.domain.common.ExcelImportResult;
import cn.niudehua.springbootdemo.domain.common.ExcelImportSuccessDTO;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @author deng
 */
@Slf4j
public class EasyExcelValidatorUtils {
    private EasyExcelValidatorUtils() {

    }

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> String validateExcelEntity(T obj) throws NoSuchFieldException {
        StringBuilder result = new StringBuilder();
        Set<ConstraintViolation<T>> set = VALIDATOR.validate(obj, Default.class);
        if (set != null && set.size() != 0) {
            for (ConstraintViolation<T> cv : set) {
                Field declaredField = obj.getClass().getDeclaredField(cv.getPropertyPath().toString());
                ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
                result.append("[").append(annotation.value()[0]).append(cv.getMessage()).append("];");
            }
        }
        return result.toString();
    }

    public static <T> ExcelImportResult<T> checkImportExcel(List<T> objects) {
        List<ExcelImportSuccessDTO<T>> successObjectDTOList = new ArrayList<>();
        List<ExcelImportErrorDTO<T>> errorObjectDTOList = new ArrayList<>();
        for (T object : objects) {
            try {
                String s = validateExcelEntity(object);
                if (StringUtils.isNotBlank(s)) {
                    ExcelImportErrorDTO<T> excelImportErrorObjectDTO = new ExcelImportErrorDTO<>();
                    excelImportErrorObjectDTO.setObject(object);
                    excelImportErrorObjectDTO.setErrMsg(s);
                    errorObjectDTOList.add(excelImportErrorObjectDTO);
                } else {
                    ExcelImportSuccessDTO<T> excelImportSuccessObjectDTO = new ExcelImportSuccessDTO<>();
                    excelImportSuccessObjectDTO.setObject(object);
                    successObjectDTOList.add(excelImportSuccessObjectDTO);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        log.info("校验失败数据条数:{}", errorObjectDTOList.size());
        return new ExcelImportResult<>(successObjectDTOList, errorObjectDTOList);

    }
}