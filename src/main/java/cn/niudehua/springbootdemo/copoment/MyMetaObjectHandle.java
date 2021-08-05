package cn.niudehua.springbootdemo.copoment;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author deng
 * @datetime 2020/8/21 10:44 下午
 */
@Component
@Slf4j
public class MyMetaObjectHandle implements MetaObjectHandler {

    public static final String CREATED_TIME = "createdTime";
    public static final String VERSION = "version";
    public static final String MODIFIED_TIME = "modifiedTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter(CREATED_TIME)
                && Objects.isNull(getFieldValByName(CREATED_TIME, metaObject))) {
            log.info("~~~insertFill~~~createdTime~~~");
            setFieldValByName(CREATED_TIME, LocalDateTime.now(), metaObject);
        }
        if (metaObject.hasGetter(VERSION)) {
            log.info("~~~insertFill~~~version~~~");
            setFieldValByName(VERSION, 1L, metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter(MODIFIED_TIME)
                && Objects.isNull(getFieldValByName(MODIFIED_TIME, metaObject))) {
            log.info("~~~updateFill~~~modifiedTime~~~");
            setFieldValByName(MODIFIED_TIME, LocalDateTime.now(), metaObject);
        }
    }

    /**
     * 重写填充策略，有值（且不为null）不覆盖
     *
     * @param metaObject metaObject
     * @param fieldName  fieldName
     * @param fieldVal   fieldVal
     * @return MetaObjectHandler
     */
    @Override
    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<Object> fieldVal) {
        Object o = fieldVal.get();
        if (Objects.nonNull(o)) {
            metaObject.setValue(fieldName, o);
        }
        return this;
    }
}
