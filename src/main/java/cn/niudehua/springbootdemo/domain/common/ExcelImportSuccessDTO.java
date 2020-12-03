package cn.niudehua.springbootdemo.domain.common;

import lombok.Data;

/**
 * excel单条数据导入结果
 *
 * @author deng
 */
@Data
public class ExcelImportSuccessDTO<T> {
    private T object;
}