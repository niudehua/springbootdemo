package cn.niudehua.springbootdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：HelloController
 * ***********************
 * <p>
 * 类描述：Hello SpringBoot
 *
 * @author deng on 2020/12/416:31
 */
@RestController
@Slf4j
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        log.info("Hello SpringBoot!");
        return "Hello SpringBoot!";
    }

    @GetMapping("jrebel")
    public String jrebelTest() {
        log.info("Hello JREBEL");
        return "Hello JREBEL";
    }

    @GetMapping("jrebel2")
    public String jrebelTest2() {
        log.info("Hello JREBEL");
        return "Hello JREBEL2";
    }

}