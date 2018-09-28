package cn.dlj1.cms.dao.support.relationQueryImpl;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.dao.annotation.MoreToMore;
import cn.dlj1.cms.dao.annotation.OneToMore;
import cn.dlj1.cms.dao.annotation.OneToOne;
import cn.dlj1.cms.dao.support.DaoUtils;
import cn.dlj1.cms.dao.support.RelationFieldCache;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.entity.annotation.CloumnUtils;
import cn.dlj1.cms.entity.annotation.TableFieldUtils;
import cn.dlj1.cms.exception.MessageException;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.sf.jsqlparser.statement.select.TableFunction;
import org.apache.commons.lang.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public interface RelationEditProcess {

    static Logger log = LoggerFactory.getLogger(RelationEditProcess.class);

    HttpServletRequest getRequest();

    Entity getEntity();

    default Field[] getFields() {
        return RelationFieldCache.getRelationFields(getEntity().getClass());
    }

    default ApplicationContext getContext() {
        return WebApplicationContextUtils.getWebApplicationContext(getRequest().getServletContext());
    }

    default void check() {
        if (getFields().length == 0) {
            return;
        }
    }

    default void edit() {
        check();

        try {
            process();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new MessageException("系统异常!");
        }

    }

    default void process() throws IllegalAccessException {
        Field[] fields = getFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Object relationObj = FieldUtils.readField(field, getEntity(), true);
            if (null == relationObj) {
                continue;
            }
            ;

            if (null != field.getAnnotation(OneToOne.class)) {
                if (!(relationObj instanceof Entity)) {
                    throw new MessageException(String.format("字段[%s]不支持的数据类型!", CloumnUtils.getName(field)));
                }
                // 如果 oneData 属性都为空;
                if (DaoUtils.entityPropertyIsAllEmpty(relationObj)) {
                    continue;
                }
                OneToOne oneToOne = field.getAnnotation(OneToOne.class);
                Object oneValue = FieldUtils.readField(getEntity(), oneToOne.field(), true);
                FieldUtils.writeField(relationObj, oneToOne.one(), null, true);

                if (null == oneValue) {
                    throw new MessageException("修改的数据有误!");
                }
                oneToOne(field, (Entity) relationObj);
                continue;
            }
            if (null != getFields()[i].getAnnotation(OneToMore.class)) {
                if (!(relationObj instanceof List)) {
                    throw new MessageException(String.format("字段[%s]不支持的数据类型!", CloumnUtils.getName(field)));
                }
                List<Entity> list = (List<Entity>) relationObj;
                if (list.size() == 0) {
                    continue;
                }
                oneToMore(field, list);
                continue;
            }
            if (null != getFields()[i].getAnnotation(MoreToMore.class)) {
                if (!(relationObj instanceof List)) {
                    throw new MessageException(String.format("字段[%s]不支持的数据类型!", CloumnUtils.getName(field)));
                }
                List<Entity> list = (List<Entity>) relationObj;
                if (list.size() == 0) {
                    continue;
                }
                MoreToMore(field, list);
                continue;
            }


        }
    }

    default Class getMapperEntityClazz(Class mapper) {
        return (Class) ((ParameterizedType) mapper.getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    /**
     * 只修改
     *
     * @param field
     * @param oneData
     * @throws IllegalAccessException
     */
    default void oneToOne(Field field, Entity oneData) throws IllegalAccessException {
        OneToOne oneToOne = field.getAnnotation(OneToOne.class);
        Dao dao = (Dao) getContext().getBean(oneToOne.mapper());
        Class oneClazz = getMapperEntityClazz(oneToOne.mapper());
        Field oneField = FieldUtils.getField(oneClazz, oneToOne.one(), true);
        String fieldName = TableFieldUtils.getName(oneField);

        Object oneValue = FieldUtils.readField(getEntity(), oneToOne.field(), true);

        // 制空
        FieldUtils.writeField(oneField, oneData, null, true);

        int ret = dao.update(oneData, new QueryWrapper<>().eq(fieldName, oneValue));
        if (1 != ret) {
            log.warn("修改关联数据时失败:{}", JSON.toJSONString(oneData));
            throw new MessageException("修改失败!");
        }
    }

    /**
     * 修改 删除
     * 如果实体中的属性只有id不为空，那么删除掉这条信息
     *
     * @param field
     * @param moreData
     * @throws IllegalAccessException
     */
    default void oneToMore(Field field, List<Entity> moreData) throws IllegalAccessException {
        OneToMore oneToMore = field.getAnnotation(OneToMore.class);
        Dao dao = (Dao) getContext().getBean(oneToMore.mapper());
        Class moreClazz = getMapperEntityClazz(oneToMore.mapper());
        // 检查是否存在ID
        Field morePk = RelationFieldCache.getEntityPkField(moreClazz);
        // 关联字段
        Object relationValue = FieldUtils.readField(getEntity(), oneToMore.field(), true);

        for (int j = 0; j < moreData.size(); j++) {
            Entity ele = moreData.get(j);
            // 属性都为空
            if (DaoUtils.entityPropertyIsAllEmpty(ele)) {
                continue;
            }

            int ret = 0;
            // 获取多一方中每个实体的主键
            Object id = FieldUtils.readField(morePk, ele, true);
            if (null == id) {
                // 设置关联字段
                FieldUtils.writeField(ele, oneToMore.more(), relationValue, true);
                ret = dao.insert(ele);
            } else {
                // 判断是修改model，还是直接进行删除
                boolean isDelete = DaoUtils.entityPropertyOnlyPkIsNotNull(ele);
                if (isDelete) {
                    ret = dao.delete(
                            new QueryWrapper<>().eq(TableFieldUtils.getName(morePk), id)
                                    .eq(TableFieldUtils.getName(FieldUtils.getField(moreClazz, oneToMore.more(), true)), relationValue));
                } else {
                    // 制空id和关联字段
                    FieldUtils.writeField(ele, morePk.getName(), null, true);
                    FieldUtils.writeField(ele, oneToMore.more(), null, true);
                    ret = dao.update(ele,
                            new QueryWrapper<>().eq(TableFieldUtils.getName(morePk), id)
                                    .eq(TableFieldUtils.getName(FieldUtils.getField(moreClazz, oneToMore.more(), true)), relationValue));
                }
            }
            if (1 != ret) {
                log.warn("修改关联数据时失败:{}", JSON.toJSONString(ele));
                throw new MessageException("修改失败!");
            }
        }

    }

    /**
     * 多对多对于中间表来说
     * 只有 删除 或者新增 没有修改操作
     * <p>
     * 如果只有id 那么就是删除
     * <p>
     * 如果没有ID有右表的字段 那么表明是新增
     *
     * @param field
     * @param moreData
     * @throws IllegalAccessException
     */
    default void MoreToMore(Field field, List<Entity> moreData) throws IllegalAccessException {
        MoreToMore moreToMore = field.getAnnotation(MoreToMore.class);
        Dao middleDao = (Dao) getContext().getBean(moreToMore.middleMapper());
        Class middleClazz = getMapperEntityClazz(moreToMore.middleMapper());

        Class moreClazz = getMapperEntityClazz(moreToMore.mapper());
        // 检查是否存在ID
        Field morePk = RelationFieldCache.getEntityPkField(moreClazz);

        // 关联字段
        Object relationValue = FieldUtils.readField(getEntity(), moreToMore.field(), true);

        for (int j = 0; j < moreData.size(); j++) {
            Entity ele = moreData.get(j);
            // 属性都为空
            if (DaoUtils.entityPropertyIsAllEmpty(ele)) {
                continue;
            }

            int ret = 0;
            // 获取多一方中每个实体的主键
            Object id = FieldUtils.readField(morePk, ele, true);
            if (null == id) {
                throw new MessageException("修改数据结构有误!");
            }

            boolean isDelete = DaoUtils.entityPropertyOnlyPkIsNotNull(ele);
            if (isDelete) {
                ret = middleDao.delete(
                        new QueryWrapper<>()
                                .eq(TableFieldUtils.getName(FieldUtils.getField(middleClazz, moreToMore.leftField(), true)), relationValue)
                                .eq(TableFieldUtils.getName(FieldUtils.getField(middleClazz, moreToMore.rightField(), true)), id));
            } else {
                try {
                    Entity middleEntity = (Entity) middleClazz.newInstance();

                    FieldUtils.writeField(middleEntity, moreToMore.leftField(), relationValue, true);
                    FieldUtils.writeField(middleEntity, moreToMore.rightField(), id, true);

                    ret = middleDao.insert(middleEntity);

                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
            if (1 != ret) {
                log.warn("修改关联数据时失败:{}", JSON.toJSONString(ele));
                throw new MessageException("修改失败!");
            }

        }
    }

}
