package com.ruoyi.framework.config;

import com.github.yitter.contract.IIdGenerator;
import com.ruoyi.common.annotation.*;
import com.ruoyi.common.enums.DelEnum;
import com.ruoyi.common.utils.SecurityUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * 字段填充拦截
 *
 * @author qinrongjun
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class FillFieldInterceptor implements Interceptor {
    private final IIdGenerator idGenerator;

    public FillFieldInterceptor(IIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object arg = args[1];
        Field[] allFields = FieldUtils.getAllFields(arg.getClass());
        int[] modify = {0};
        if (mappedStatement.getSqlCommandType() == SqlCommandType.UPDATE) {
            for (Field field : allFields) {
                if (modify[0] >= 2) {
                    break;
                }
                doModify(UpdateTime.class, field, arg, LocalDateTime::now, modify);
                doModify(UpdateBy.class, field, arg, SecurityUtils::getUsername, modify);
            }

        } else if (mappedStatement.getSqlCommandType() == SqlCommandType.INSERT) {
            for (Field field : allFields) {
                if (modify[0] >= 4) {
                    break;
                }
                doModify(Id.class, field, arg, idGenerator::newLong, modify);
                doModify(CreateTime.class, field, arg, LocalDateTime::now, modify);
                doModify(CreateBy.class, field, arg, SecurityUtils::getUsername, modify);
                doModify(DelFlag.class, field, arg, DelEnum.SHOW::getCode, modify);

            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private void doModify(Class<? extends Annotation> annotationClass, Field field, Object arg, Supplier<Object> supplier, int[] modify) throws IllegalAccessException {
        if ((field.isAnnotationPresent(annotationClass))) {
            field.setAccessible(true);
            if (field.get(arg) == null) {
                Object value = supplier.get();
                field.set(arg, value);
            }
            modify[0]++;
        }
    }
}
