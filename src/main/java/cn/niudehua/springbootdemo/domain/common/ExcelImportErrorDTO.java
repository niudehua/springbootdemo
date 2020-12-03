package cn.niudehua.springbootdemo.domain.common;

import lombok.Data;


/**
 * excel单条数据导入结果
 *
 * @author deng
 */
@Data
public class ExcelImportErrorDTO<T> {
    private T object;
    private String errMsg;

    public ExcelImportErrorDTO() {
    }

    public ExcelImportErrorDTO(T object, String errMsg) {
        this.object = object;
        this.errMsg = errMsg;
    }
}