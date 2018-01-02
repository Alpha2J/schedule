package cn.alpha2j.schedule.data.repository.base;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.AbstractDao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.annotation.TableName;
import cn.alpha2j.schedule.data.entity.EntityIdentifier;

/**
 * 换用greendao框架, 不再使用这个类来进行持久化
 *
 * @author alpha
 */
@Deprecated
public abstract class SqliteGenericRepository<T extends EntityIdentifier, DAO extends AbstractDao<T, Long>> extends BaseGenericRepository<T, DAO> {

    protected ScheduleDatabaseHelper mDatabaseHelper;

    protected final String TABLE_NAME;

    public SqliteGenericRepository() {

        String tableName = null;

        //如果实体标注了@TableName, 那么使用标注的表值
        Annotation[] annotations = mEntityClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof TableName) {
                tableName = ((TableName)annotation).value();
                break;
            }
        }

        //如果没有标注, 那么使用实体名字
        if(tableName == null) {
            tableName = mEntityClass.getSimpleName();
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
    public long save(T entity) {

        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Field[] fields = mEntityClass.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldValue = field.get(entity);
                String fieldValueStr = fieldValue == null ? null : fieldValue.toString();
                if(fieldValueStr != null) {
                    contentValues.put(field.getName(), fieldValueStr);
                }
            }

            long id = database.insert(TABLE_NAME, null, contentValues);

            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1L;
    }
}
