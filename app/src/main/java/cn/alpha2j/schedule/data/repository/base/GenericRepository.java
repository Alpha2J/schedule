package cn.alpha2j.schedule.data.repository.base;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import org.greenrobot.greendao.AbstractDao;

/**
 * @author alpha
 */
public interface GenericRepository<T, DAO extends AbstractDao<T, Long>> {

    /**
     * 增加一个实体
     *
     * @param entity 需要增加的实体
     * @return 插入后的entity, 具有插入后的id, 插入失败返回null
     * @throws NullPointerException entity为null
     */
    Long save(T entity);

    /**
     * 插入指定数量的实体
     *
     * @param entities
     * @return 插入成功后的实体, 带有id, 插入失败返回的iterable长度为0
     * @throws NullPointerException 传入的参数为空
     * @throws IllegalArgumentException 传入的参数不符合最小长度
     */
    @NonNull
    Iterable<T> save(@Size(min = 1) Iterable<T> entities);

    /**
     * 获取一个实体
     *
     * @param id
     * @return
     */
    T findOne(Long id);

    Iterable<T> findAll(Iterable<Long> ids);

    long count();

    void delete();

    void delete(T entity);

    void delete(Iterable<? extends T> entities);

    void deleteAll();

    void update(T entity);
}
