package cn.niudehua.springbootdemo.domain.dto;

import cn.niudehua.springbootdemo.util.ExcelPatternMsg;
import cn.niudehua.springbootdemo.util.InsertValidationGroup;
import cn.niudehua.springbootdemo.util.UpdateValidationGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author deng
 * @datetime 2020/11/28 下午6:27
 */
@Data
public class CustomerDTO implements Serializable {

    private static final long serialVersionUID = 981176118004422112L;

    /**
     * 数据表主键
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空",
            groups = InsertValidationGroup.class)
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空",
            groups = InsertValidationGroup.class)
    @Length(min = 6, max = 18,
            message = "密码长度不能少于{min},不能多于{max}",
            groups = {InsertValidationGroup.class, UpdateValidationGroup.class})
    private String password;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空",
            groups = {InsertValidationGroup.class, UpdateValidationGroup.class})
    @Email(groups = {InsertValidationGroup.class, UpdateValidationGroup.class})
    private String email;

    /**
     * 年龄
     */
    @Max(60)
    @Min(18)
    private Integer age;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空",
            groups = {InsertValidationGroup.class, UpdateValidationGroup.class})
    private String phone;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 数据版本号
     */
    @NotNull(message = "数据版本号不能为空", groups = UpdateValidationGroup.class)
    private Long version;
}