package cn.niudehua.springbootdemo.domain.common;

import cn.niudehua.springbootdemo.util.ExcelPatternMsg;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 类名称：PageQuery
 * ***********************
 * <p>
 * 类描述：通用分页查询对象
 *
 * @author deng on 2020/11/29上午2:33
 */
@Data
public class PageQuery<T> implements Serializable {
    private static final long serialVersionUID = -3425440727680093949L;
    /**
     * 当前页
     */
    @NotNull(message = "页号不能为空")
    @Min(value = 1, message = "页号最小为{value}")
    private Integer pageNo = 1;

    /**
     * 每页条数
     */
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小为{value}")
    @Max(value = 100, message = "每页条数不能超过{value}")
    private Integer pageSize = 10;

    /**
     * 动态查询条件
     */
    @Valid
    @NotNull(message = "动态查询条件不能为null")
    private transient T query;

}