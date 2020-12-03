package cn.niudehua.springbootdemo.advice;

import cn.niudehua.springbootdemo.domain.common.ResponseResult;
import cn.niudehua.springbootdemo.enums.ErrorCodeEnum;
import cn.niudehua.springbootdemo.exception.BizException;
import com.alibaba.excel.exception.ExcelAnalysisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理类
 *
 * @author deng
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务类异常
     *
     * @param e exception
     * @return ResponseResult
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public <T> ResponseResult<T> bizExceptionHandler(BizException e) {
        log.error("发生业务异常！", e);
        return ResponseResult.error(e.getErrorCode(),
                e.getMessage());
    }

    @ExceptionHandler(value = ExcelAnalysisException.class)
    @ResponseBody
    public <T> ResponseResult<T> excelAnalysisExceptionHandler(ExcelAnalysisException e) {
        log.error("excel解析异常！原因是:", e);
        return ResponseResult.error(ErrorCodeEnum.EASY_EXCEL_ERROR.getResultCode(),
                e.getMessage());
    }

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public <T> ResponseResult<T> nullPointerExceptionHandler(NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return ResponseResult.error(ErrorCodeEnum.PARAM_NULL);
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public <T> ResponseResult<T> runtimeExceptionHandler(RuntimeException e) {
        log.error("发生运行时异常！", e);
        return ResponseResult.error(ErrorCodeEnum.UNKNOWN_ERROR.getResultCode(),
                e.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public <T> ResponseResult<T> throwableHandler(Throwable throwable) {
        log.error("捕获到throwable异常！原因是:", throwable);
        return ResponseResult.error(ErrorCodeEnum.SYS_ERROR.getResultCode(),
                throwable.getMessage());
    }


}

