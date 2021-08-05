package cn.niudehua.springbootdemo.util;

import cn.niudehua.springbootdemo.enums.ErrorCodeEnum;
import cn.niudehua.springbootdemo.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


/**
 * 校验工具
 *
 * @author deng
 */
@Slf4j
public class ValidatorUtils {
    private ValidatorUtils() {

    }

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validateEntity(T obj, Class... groups) {
        Set<ConstraintViolation<T>> set = VALIDATOR.validate(obj, groups);
        // 如果校验结果不为空  则抛出异常
        if (CollectionUtils.isNotEmpty(set)) {
            StringBuilder exceptionMessage = new StringBuilder();
            set.forEach(
                    constraintViolation ->
                            exceptionMessage.append(constraintViolation.getMessage()).append(";")
            );
            throw new BizException(ErrorCodeEnum.PARAM_ERROR, exceptionMessage.toString());
        }
    }

}