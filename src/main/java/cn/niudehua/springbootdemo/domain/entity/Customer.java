package cn.niudehua.springbootdemo.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author deng
 * @datetime 2020/11/29 上午6:01
 */
@Data
@TableName(value = "customer")
public class Customer {
    private static final long serialVersionUID = -201287332335088689L;
    /**
     * 数据表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 年龄
     */
    @TableField(value = "age")
    private Integer age;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    private LocalDate birthday;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    @TableField(value = "modified_time", fill = FieldFill.UPDATE)
    private LocalDateTime modifiedTime;

    /**
     * 创建人
     */
    @TableField(value = "creator")
    private String creator;

    /**
     * 修改人
     */
    @TableField(value = "operator")
    private String operator;

    /**
     * 删除状态1：有效；-1：无效（删除时设置为-1）
     */
    @TableField(value = "deleted")
    @TableLogic
    private Integer deleted;

    /**
     * 数据版本号
     */
    @TableField(value = "version", fill = FieldFill.INSERT)
    @Version
    private Long version;
}