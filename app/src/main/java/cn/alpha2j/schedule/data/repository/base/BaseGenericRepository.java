package cn.alpha2j.schedule.data.repository.base;

import org.greenrobot.greendao.AbstractDao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.alpha2j.schedule.data.entity.EntityIdentifier;

/**
 * @author alpha
 */
public abstract class BaseGenericRepository<T extends EntityIdentifier, DAO extends AbstractDao<T, Long>> implements GenericRepository<T, DAO> {

    protected final Class<T> mEntityClass;
    protected final Class<DAO> mDAOClass;

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
        this.mEntityClass = (Class<T>) arguments[0];
        this.mDAOClass = (Class<DAO>) arguments[1];
    }
}
