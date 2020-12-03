package cn.niudehua.springbootdemo.domain.dto.excel;

import cn.niudehua.springbootdemo.convert.LocalDateStringConverter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 客户excel导出对象
 *
 * @author deng
 */
@Data
@HeadStyle(fillBackgroundColor = 14, horizontalAlignment = HorizontalAlignment.CENTER)
@HeadRowHeight(20)
@ColumnWidth(32)
public class CustomerExportExcel implements Serializable {

    private static final long serialVersionUID = -1980566392968587119L;

    @ExcelProperty(index = 0, value = "用户名")
    private String username;

    @ExcelProperty(index = 1, value = "邮箱")
    private String email;

    @ExcelProperty(index = 2, value = "年龄")
    private Integer age;

    @ExcelProperty(index = 3, value = "手机号")
    private String phone;

    @ExcelProperty(index = 4, value = "生日(导入格式：yyyy-MM-dd)", converter = LocalDateStringConverter.class)
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(64)
    private LocalDate birthday;

}
