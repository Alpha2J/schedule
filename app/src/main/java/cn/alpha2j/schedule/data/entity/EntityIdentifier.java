package cn.alpha2j.schedule.data.entity;

/**
 * @author alpha
 */
public interface EntityIdentifier {

    /**
     * 返回实体的唯一标识, 一般是主键id
     * @return id标识
     */
    Long getIdentifier();
}
