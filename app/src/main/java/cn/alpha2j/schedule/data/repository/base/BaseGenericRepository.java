package cn.alpha2j.schedule.data.repository.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author alpha
 */
public abstract class BaseGenericRepository<T, ID extends Serializable> implements GenericRepository<T, ID> {

    protected final Class<T> entityClass;
    protected final Class<ID> idClass;

    @SuppressWarnings("unchecked")
    public BaseGenericRepository() {

        Type genericSuperclass = this.getClass().getGenericSuperclass();
        while (!(genericSuperclass instanceof ParameterizedType)) {
            if (!(genericSuperclass instanceof Class)) {
                throw new IllegalStateException("无法确定类型参数");
            }

            if (genericSuperclass == BaseGenericRepository.class) {
                throw new IllegalStateException("无法确定类型参数");
            }

            genericSuperclass = ((Class) genericSuperclass).getGenericSuperclass();
        }

        ParameterizedType type = (ParameterizedType) genericSuperclass;
        Type[] arguments = type.getActualTypeArguments();
        this.entityClass = (Class<T>) arguments[0];
        this.idClass = (Class<ID>) arguments[1];
    }
}
