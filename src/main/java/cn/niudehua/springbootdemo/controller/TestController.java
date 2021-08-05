package cn.niudehua.springbootdemo.controller;

import cn.niudehua.springbootdemo.domain.dto.MyResult;
import cn.niudehua.springbootdemo.enums.ReturnCodeEnum;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 枚举前后端传递测试
 *
 * @author deng
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping(value = "/enumForm")
    public MyResult testEnumForm(MyResult myResult) {
        ReturnCodeEnum status = myResult.getReturnCode();
        System.out.println("name():" + status.name());
        System.out.println("ordinal():" + status.ordinal());
        System.out.println("getCode():" + status.getCode());
        return myResult;
    }

    @PostMapping(value = "/enumJson")
    public MyResult testEnumJson(@RequestBody MyResult myResult) {
        ReturnCodeEnum status = myResult.getReturnCode();
        System.out.println("name():" + status.name());
        System.out.println("ordinal():" + status.ordinal());
        System.out.println("getCode():" + status.getCode());
        return myResult;
    }

    @PostMapping(value = "/enumPath/{status}")
    public ReturnCodeEnum testEnumPath(
            @PathVariable ReturnCodeEnum status) {
        return status;
    }

    @PostMapping(value = "/enumParam")
    public ReturnCodeEnum testEnumParam(
            @RequestParam ReturnCodeEnum status) {
        return status;
    }
}
