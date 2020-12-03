package cn.niudehua.springbootdemo.domain.common;

import cn.niudehua.springbootdemo.enums.BaseErrorInfoInterface;
import cn.niudehua.springbootdemo.enums.ErrorCodeEnum;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义数据格式
 *
 * @author deng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {
    private Boolean success;
    private String code;
    private String message;
    private T result;

    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> rb = new ResponseResult<>();
        rb.setSuccess(Boolean.TRUE);
        rb.setCode(ErrorCodeEnum.SUCCESS.getResultCode());
        rb.setMessage(ErrorCodeEnum.SUCCESS.getResultMessage());
        rb.setResult(data);
        return rb;
    }

    public static <T> ResponseResult<T> error(String code, String message) {
        ResponseResult<T> rb = new ResponseResult<>();
        rb.setSuccess(Boolean.FALSE);
        rb.setCode(code);
        rb.setMessage(message);
        return rb;
    }

    public static <T> ResponseResult<T> error(BaseErrorInfoInterface errorInfo) {
        return error(errorInfo.getResultCode(), errorInfo.getResultMessage());
    }

    public static <T> ResponseResult<T> error(String message) {
        return error("-1", message);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, true);
    }

}