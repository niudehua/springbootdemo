package cn.niudehua.springbootdemo.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 类名称：LocalDateStringConverter
 * ***********************
 * <p>
 * 类描述：LocalDate String 转换器
 *
 * @author deng on 2020/11/27下午11:53
 */
public class LocalDateStringConverter implements Converter<LocalDate> {
    @Override
    public Class<LocalDate> supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return LocalDate.parse(cellData.getStringValue(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else {
            return LocalDate.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat()));
        }
    }

    @Override
    public CellData<LocalDate> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return
                    new CellData<>(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value));
        } else {
            return new CellData<>(DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat()).format(value));
        }

    }
}