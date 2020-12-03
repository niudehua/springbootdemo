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
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("createdTime")) {
            if (Objects.isNull(getFieldValByName("createdTime", metaObject))) {
                log.info("~~~insertFill~~~createdTime~~~");
                setFieldValByName("createdTime", LocalDateTime.now(), metaObject);
            }
        }
        if (metaObject.hasGetter("version")) {
            log.info("~~~insertFill~~~version~~~");
            setFieldValByName("version", 1L, metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("modifiedTime")) {
            if (Objects.isNull(getFieldValByName("modifiedTime", metaObject))) {
                log.info("~~~updateFill~~~modifiedTime~~~");
                setFieldValByName("modifiedTime", LocalDateTime.now(), metaObject);
            }
        }
    }

    /**
     * 重写填充策略，有值（且不为null）不覆盖
     * @param metaObject  metaObject
     * @param fieldName fieldName
     * @param fieldVal fieldVal
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
