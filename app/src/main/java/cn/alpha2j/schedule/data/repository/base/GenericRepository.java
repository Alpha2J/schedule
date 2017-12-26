package cn.alpha2j.schedule.data.repository.base;

/**
 * @author alpha
 */
public interface GenericRepository<T, ID> {

    T save(T entity);

    Iterable<T> save(Iterable<T> entities);

    T findOne(ID id);

    Iterable<T> findAll(Iterable<ID> ids);

    long count();

    void delete();

    void delete(T entity);

    void delete(Iterable<? extends T> entities);

    void deleteAll();
}
