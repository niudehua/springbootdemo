package cn.niudehua.springbootdemo.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 类名称：LocalDateTimeStringConverter
 * ***********************
 * <p>
 * 类描述：LocalDateTime String 转换器
 *
 * @author deng on 2020/11/27下午11:53
 */
public class LocalDateTimeStringConverter implements Converter<LocalDateTime> {
    @Override
    public Class<LocalDateTime> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return LocalDateTime.parse(cellData.getStringValue(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else {
            return LocalDateTime.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat()));
        }
    }

    @Override
    public CellData<LocalDateTime> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return
                    new CellData<>(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value));
        } else {
            return new CellData<>(DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat()).format(value));
        }

    }
}