package cn.alpha2j.schedule.data.repository.base;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

import java.lang.reflect.Method;

import cn.alpha2j.schedule.Constants;
import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.data.entity.DaoMaster;
import cn.alpha2j.schedule.data.entity.DaoSession;
import cn.alpha2j.schedule.data.entity.EntityIdentifier;
import cn.alpha2j.schedule.exception.PrimaryKeyNotExistException;

/**
 * @author alpha
 */
public class GreenDAOGenericRepository<T extends EntityIdentifier, DAO extends AbstractDao<T, Long>> extends BaseGenericRepository<T, DAO> {

    protected DaoSession mDaoSession;
    protected DAO mDAO;

    public GreenDAOGenericRepository() {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApplication.getContext(), Constants.DATABASE_NAME);
        Database database = helper.getWritableDb();
        mDaoSession = new DaoMaster(database).newSession();

        try {
            String getDaoMethodName = "get" + mDAOClass.getSimpleName();
            Method method = mDaoSession.getClass().getDeclaredMethod(getDaoMethodName);
            mDAO = (DAO) method.invoke(mDaoSession);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long save(T entity) {

        if (entity == null) {
            throw new NullPointerException("参数entity不能为null");
        }

        return mDAO.insert(entity);
    }

    @Override
    public long saveOrUpdate(T entity) {

        if (entity == null) {
            throw new NullPointerException("参数entity不能为null");
        }

//        api哪里说是"row ID of newly inserted entity", 测试了下确实是当前数据行的id
        return mDAO.insertOrReplace(entity);
    }

    @Override
    public T findOne(long id) {

        if(id < 0) {
            throw new IllegalArgumentException("id参数不能小于0");
        }

        return mDAO.loadByRowId(id);
    }

    @Override
    public long count() {

        return mDAO.count();
    }

    @Override
    public void delete(T entity) {

        if (entity == null) {
            throw new NullPointerException("entity参数不能为空");
        }

        if (entity.getIdentifier() == null) {
            throw new PrimaryKeyNotExistException("标识主键不存在");
        }

        if (entity.getIdentifier() < 0) {
            throw new IllegalArgumentException("标识主键不能小于0");
        }

        mDAO.delete(entity);
    }

    @Override
    public void deleteAll() {

        mDAO.deleteAll();
    }

    @Override
    public void update(T entity) {

        if (entity == null) {
            throw new NullPointerException("entity参数不能为空");
        }

        if (entity.getIdentifier() == null) {
            throw new PrimaryKeyNotExistException("标识主键不存在");
        }

        if (entity.getIdentifier() < 0) {
            throw new IllegalArgumentException("标识主键不能小于0");
        }

        mDAO.update(entity);
    }
}
