package cn.niudehua.springbootdemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author deng
 */
@AllArgsConstructor
@Getter
public enum ErrorCodeEnum implements BaseErrorInfoInterface {
    // 0*** 成功
    SUCCESS("0000", "操作成功!"),
    // 1*** 参数异常
    PARAM_ERROR("1001", "参数异常"),
    PARAM_NULL("1002", "参数为空"),
    PARAM_FORMAT_ERROR("1003", "参数格式不正确"),
    PARAM_VALUE_ERROR("1004", "参数值不正确"),
    // 2*** 系统异常
    SYS_ERROR("2001", "系统异常"),
    UNKNOWN_ERROR("2002", "未知异常"),
    // 3*** 业务异常
    XXX("3001", "业务异常"),
    INSERT_ERROR("3002", "新增失败"),
    UPDATE_ERROR("3003", "更新失败"),
    DELETE_ERROR("3004", "删除失败"),
    EASY_EXCEL_ERROR("3005", "easyExcel异常"),
    RATE_LIMITER_ERROR("3006", "限流异常"),
    FILE_UPLOAD_ERROR("3007", "文件上传失败");
    private final String resultCode;
    private final String resultMessage;

}
