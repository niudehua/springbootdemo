package cn.niudehua.springbootdemo.domain.dto.excel;

import cn.niudehua.springbootdemo.util.ExcelPatternMsg;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 客户导入excel对象
 *
 * @author deng
 */
@Data
@HeadStyle(fillBackgroundColor = 14, horizontalAlignment = HorizontalAlignment.CENTER)
@HeadRowHeight(20)
@ColumnWidth(32)
public class CustomerImportExcel implements Serializable {

    private static final long serialVersionUID = 1485843301433186761L;

    @NotBlank
    @Max(32)
    @ExcelProperty(index = 0, value = "用户名")
    private String username;

    @Email
    @NotBlank
    @Max(32)
    @ExcelProperty(index = 1, value = "邮箱")
    private String email;

    @Max(3)
    @ExcelProperty(index = 2, value = "年龄")
    private String age;

    @NotBlank
    @Pattern(regexp = ExcelPatternMsg.PHONE, message = ExcelPatternMsg.PHONE_MSG)
    @ExcelProperty(index = 3, value = "手机号")
    private String customerClassName;

    @ExcelProperty(index = 4, value = "生日(导入格式：yyyy-MM-dd)")
    @Pattern(regexp = ExcelPatternMsg.DATE1, message = ExcelPatternMsg.DATE1_MSG)
    @ColumnWidth(64)
    private String birthday;

}
