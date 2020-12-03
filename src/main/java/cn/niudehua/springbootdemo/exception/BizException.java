package cn.niudehua.springbootdemo.exception;

import cn.niudehua.springbootdemo.enums.ErrorCodeEnum;
import lombok.Getter;


/**
 * 业务异常
 *
 * @author deng
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -990373642957661293L;

    @Getter
    private final String errorCode;

    public BizException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getResultMessage());
        this.errorCode = errorCodeEnum.getResultCode();
    }

    public BizException(ErrorCodeEnum errorCodeEnum, String message) {
        super(message);
        this.errorCode = errorCodeEnum.getResultCode();
    }

    public BizException(ErrorCodeEnum errorCodeEnum, Throwable cause) {
        super(cause);
        this.errorCode = errorCodeEnum.getResultCode();
    }

}