package cn.niudehua.springbootdemo.domain.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * excel数据导入结果
 *
 * @author deng
 */
@Data
public class ExcelImportResult<T> {
    private List<ExcelImportSuccessDTO<T>> successDTOList;
    private List<ExcelImportErrorDTO<T>> errorDTOList;

    public ExcelImportResult(List<ExcelImportSuccessDTO<T>> successDTOList, List<ExcelImportErrorDTO<T>> errorDTOList) {
        this.successDTOList = successDTOList;
        this.errorDTOList = errorDTOList;
    }

    public ExcelImportResult(List<ExcelImportErrorDTO<T>> errorDTOList) {
        this.successDTOList = new ArrayList<>();
        this.errorDTOList = errorDTOList;
    }
}