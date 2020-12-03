package cn.niudehua.springbootdemo.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 类名称：CustomerVO
 * ***********************
 * <p>
 * 类描述：CustomerVO实体
 *
 * @author deng on 2020/11/29下午1:06
 */
@Data
public class CustomerVO implements Serializable {
    private static final long serialVersionUID = -8575023073682596714L;
    /**
     * 数据表主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 生日
     */
    private LocalDate birthday;

}