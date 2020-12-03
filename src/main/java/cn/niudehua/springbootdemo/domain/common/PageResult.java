package cn.niudehua.springbootdemo.domain.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 类名称：PageResult
 * ***********************
 * <p>
 * 类描述：通用分页查询实体类
 *
 * @author deng on 2020/11/29上午2:08
 */
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -2361768759989635348L;
    /**
     * 当前页
     */
    private Long pageNo;
    /**
     * 每页条数
     */
    private Long pageSize;
    /**
     * 数据总条数
     */
    private Long total;
    /**
     * 总页数
     */
    private Long pageNum;
    /**
     * 数据
     */
    private transient T data;

}