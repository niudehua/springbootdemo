package cn.niudehua.springbootdemo.domain.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户excel导入对象的错误信息
 *
 * @author deng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerImportErrorInfoExcel extends CustomerImportExcel {

    private static final long serialVersionUID = 3506592126678843433L;

    @ContentStyle(wrapped = true)
    @ExcelProperty(index = 5, value = "错误信息")
    @ColumnWidth(64)
    private String errMsg;
}
