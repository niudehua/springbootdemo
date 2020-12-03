package cn.niudehua.springbootdemo.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author deng
 * @datetime 2020/11/28 下午6:27
 */
@Data
public class CustomerQueryDTO implements Serializable {

    private static final long serialVersionUID = 1323795190727373885L;

    private String username;

    private Integer age;
}