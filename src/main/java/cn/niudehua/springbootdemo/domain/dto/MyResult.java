package cn.niudehua.springbootdemo.domain.dto;

import cn.niudehua.springbootdemo.enums.ReturnCodeEnum;
import lombok.Data;

/**
 * @version V1.0
 * @author: linshenkx
 * @date: 2019/1/12
 * @description: 枚举包装类
 */
@Data
public class MyResult {
    private ReturnCodeEnum returnCode;
    private String message;
}

