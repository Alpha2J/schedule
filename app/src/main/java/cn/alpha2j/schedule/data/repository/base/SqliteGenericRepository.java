package cn.alpha2j.schedule.data.repository.base;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.annotation.TableName;

/**
 * @author alpha
 */
public abstract class SqliteGenericRepository<T, ID extends Serializable> extends BaseGenericRepository<T, ID> {

    protected ScheduleDatabaseHelper mDatabaseHelper;

    protected final String TABLE_NAME;

    public SqliteGenericRepository() {

        String tableName = null;

        //如果实体标注了@TableName, 那么使用标注的表值
        Annotation[] annotations = entityClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof TableName) {
                tableName = ((TableName)annotation).value();
                break;
            }
        }

        //如果没有标注, 那么使用实体名字
        if(tableName == null) {
            tableName = entityClass.getSimpleName();
        }

        //设置表名
        TABLE_NAME = tableName;

        mDatabaseHelper = MyApplication.getDatabaseHelper();
    }

    /**
     * 这里假设实体不会继承, 所以是直接获取当前类的所有字段
     *
     * @param entity
     * @return
     */
    @Override
    public T save(T entity) {

        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Field[] fields = entityClass.getDeclaredFields();
        try {
            for (Field field : fields) {
                contentValues.put(field.getName(), field.get(entity).toString());
            }

            long id = database.insert(TABLE_NAME, null, contentValues);

            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public Iterable<T> save(Iterable<T> entities) {
        return null;
    }

    @Override
    public T findOne(ID id) {
        return null;
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
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
}
