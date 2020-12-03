package cn.niudehua.springbootdemo.enums;

/**
 * 类名称：BaseErrorInfoInterface
 * ***********************
 * <p>
 * 类描述：基础的接口类,自定义的错误描述枚举类需实现该接口
 *
 * @author deng on 2020/11/28下午9:23
 */
public interface BaseErrorInfoInterface {
    /**
     * 错误码
     *
     * @return String
     */
    String getResultCode();

    /**
     * 错误描述
     *
     * @return String
     */
    String getResultMessage();
}