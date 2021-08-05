package cn.niudehua.springbootdemo.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @version V1.0
 * @author: linshenkx
 * @date: 2019/1/13
 * @description: 枚举类
 */
public enum ReturnCodeEnum implements CodedEnum {

    /**
     * 正常
     */
    OK(200),
    /**
     * 出错
     */
    ERROR(500);

    private final int code;

    ReturnCodeEnum(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    @JsonCreator
    public static ReturnCodeEnum create(String name) {
        try {
            return ReturnCodeEnum.valueOf(name);
        } catch (IllegalArgumentException e) {
            int code = Integer.parseInt(name);
            for (ReturnCodeEnum value : ReturnCodeEnum.values()) {
                if (value.code == code) {
                    return value;
                }
            }
        }
        throw new IllegalArgumentException("No element matches " + name);
    }


}
