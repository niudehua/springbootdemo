package cn.niudehua.springbootdemo.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @version V1.0
 * @author: linshenkx
 * @date: 2019/1/12
 * @description: 带编号的枚举接口
 */
public interface CodedEnum {
    /**
     * 使用jackson序列化/反序列化时枚举对应的值
     * 如果想要保留原来从字面量反序列化回枚举类的功能，
     * 需要自定义一个 @JsonCreator 的构造/静态工厂方法
     *
     * @return 自定义枚举code
     */
    @JsonValue
    int getCode();

}
