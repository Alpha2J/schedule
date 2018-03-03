package cn.alpha2j.schedule.data.repository.base;

import org.greenrobot.greendao.AbstractDao;

import cn.alpha2j.schedule.data.entity.EntityIdentifier;
import cn.alpha2j.schedule.exception.PrimaryKeyNotExistException;

/**
 * @author alpha
 */
public interface GenericRepository<T extends EntityIdentifier, DAO extends AbstractDao<T, Long>> {

    /**
     * 增加一个实体
     *
     * @param entity 被增加的实体
     * @return 插入后的实体的行id
     * @throws NullPointerException entity 为null
     */
    long save(T entity);

    /**
     * 增加或者更新一个实体.
     * 如果标识主键在的话那么会更新实体.
     * 如果标识主键不存在, 那么插入一个实体
     *
     * @param entity 进行操作的实体
     * @return 更新前的id或者插入后的id
     * @throws NullPointerException 参数entity为空
     */
    long saveOrUpdate(T entity);

    /**
     * 利用id主键获取一个实体
     *
     * @param id 主键
     * @return 获取到的实体, 为null 如果实体不存在
     * @throws IllegalArgumentException 参数id小于0
     */
    T findOne(long id);

    /**
     * 数据表中存在该实体的总数
     * @return 实体总数
     */
    long count();

    /**
     * 删除指定实体
     *
     * @param entity 指定的实体, 需要带有id
     *
     * @throws NullPointerException 参数entity为空
     * @throws PrimaryKeyNotExistException 传入的实体不存在标识主键(id)
     * @throws IllegalArgumentException 传入的实体的标识主键不合法(小于0)
     */
    void delete(T entity);

    /**
     * 删除全部实体
     */
    void deleteAll();

    /**
     * 更新实体, 传入的主键id必须存在
     *
     * @param entity 需要更新的实体
     * @throws NullPointerException 参数entity为空
     * @throws PrimaryKeyNotExistException 传入的实体不存在标识主键(id)
     * @throws IllegalArgumentException 传入的实体的标识主键不合法(小于0)
     */
    void update(T entity);
}
