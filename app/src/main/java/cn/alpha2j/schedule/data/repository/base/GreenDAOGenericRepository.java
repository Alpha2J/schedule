package cn.alpha2j.schedule.data.repository.base;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

import java.lang.reflect.Method;

import cn.alpha2j.schedule.Constants;
import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.data.entity.DaoMaster;
import cn.alpha2j.schedule.data.entity.DaoSession;

/**
 * @author alpha
 */
public class GreenDAOGenericRepository<T, DAO extends AbstractDao<T, Long>> extends BaseGenericRepository<T, DAO> {

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
    public Long save(T entity) {

        if(entity == null) {
            throw new NullPointerException("参数entity不能为空");
        }

        return mDAO.insert(entity);
    }

    @NonNull
    @Override
    public Iterable<T> save(@Size(min = 1) Iterable<T> entities) {

        return null;
    }

    @Override
    public T findOne(Long id) {
        return null;
    }

    @Override
    public Iterable<T> findAll(Iterable<Long> ids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete() {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void delete(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void update(T entity) {

        mDAO.update(entity);
    }
}
