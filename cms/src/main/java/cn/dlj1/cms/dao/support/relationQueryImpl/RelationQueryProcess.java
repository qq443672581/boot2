package cn.dlj1.cms.dao.support.relationQueryImpl;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.dao.annotation.MoreToMore;
import cn.dlj1.cms.dao.annotation.OneToMore;
import cn.dlj1.cms.dao.annotation.OneToOne;
import cn.dlj1.cms.dao.support.RelationFieldCache;
import cn.dlj1.cms.entity.annotation.TableFieldUtils;
import cn.dlj1.cms.exception.MessageException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.commons.lang.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RelationQueryProcess {

    static Logger log = LoggerFactory.getLogger(RelationQueryProcess.class);

    HttpServletRequest getRequest();

    Class getClazz();

    Map<String, Object> getMap();

    default Field[] getFields() {
        return RelationFieldCache.getRelationFields(getClazz());
    }

    default ApplicationContext getContext() {
        return WebApplicationContextUtils.getWebApplicationContext(getRequest().getServletContext());
    }

    default void check() {
        if (getFields().length == 0) {
            return;
        }
    }

    default void query() {
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

            if (null != field.getAnnotation(OneToOne.class)) {
                oneToOne(field);
                continue;
            }
            if (null != getFields()[i].getAnnotation(OneToMore.class)) {
                oneToMore(field);
                continue;
            }
            if (null != getFields()[i].getAnnotation(MoreToMore.class)) {
                MoreToMore(field);
                continue;
            }


        }
    }

    default Class getMapperEntityClazz(Class mapper) {
        return (Class) ((ParameterizedType) mapper.getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    /**
     * @param field
     * @throws IllegalAccessException
     */
    default void oneToOne(Field field) throws IllegalAccessException {
        OneToOne oneToOne = field.getAnnotation(OneToOne.class);
        Dao dao = (Dao) getContext().getBean(oneToOne.mapper());
        Class oneClazz = getMapperEntityClazz(oneToOne.mapper());
        Field oneField = FieldUtils.getField(oneClazz, oneToOne.one(), true);
        String fieldName = TableFieldUtils.getName(oneField);

        Object oneValue = getMap().get(oneToOne.field());
        if (null == oneValue) {
            return;
        }

        List<Map<String, Object>> list = dao.selectMaps(new QueryWrapper<>().eq(fieldName, oneValue));
        if (list.size() == 1) {
            getMap().put(field.getName(), list.get(0));
        }

    }

    /**
     * @param field
     * @throws IllegalAccessException
     */
    default void oneToMore(Field field) throws IllegalAccessException {
        OneToMore oneToMore = field.getAnnotation(OneToMore.class);
        Dao dao = (Dao) getContext().getBean(oneToMore.mapper());
        Class moreClazz = getMapperEntityClazz(oneToMore.mapper());
        Field moreField = FieldUtils.getField(moreClazz, oneToMore.more(), true);
        String fieldName = TableFieldUtils.getName(moreField);

        Object oneValue = getMap().get(oneToMore.field());
        if (null == oneValue) {
            return;
        }

        List<Map<String, Object>> list = dao.selectMaps(new QueryWrapper<>().eq(fieldName, oneValue));
        if (list.size() > 0) {
            getMap().put(field.getName(), list);
        }


    }

    /**
     * 多对多
     * 查询中间表，获取右边的关联字段值数组，查询右表
     *
     * @param field
     * @throws IllegalAccessException
     */
    default void MoreToMore(Field field) throws IllegalAccessException {
        MoreToMore moreToMore = field.getAnnotation(MoreToMore.class);
        BaseMapper middleDao = getContext().getBean(moreToMore.middleMapper());
        Class middleClazz = getMapperEntityClazz(moreToMore.middleMapper());
        BaseMapper rightDao = getContext().getBean(moreToMore.mapper());
        Class rightClazz = getMapperEntityClazz(moreToMore.mapper());

        // 获取关联值
        Object relationFieldValue = getMap().get(moreToMore.field());
        if (null == relationFieldValue) {
            return;
        }
        Field leftField = FieldUtils.getField(middleClazz, moreToMore.leftField(), true);
        Field rightField = FieldUtils.getField(middleClazz, moreToMore.rightField(), true);

        // 中间表的字段
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq(TableFieldUtils.getName(leftField), relationFieldValue);
        wrapper.select(TableFieldUtils.getName(rightField));
        List<Map<String, Object>> list = middleDao.selectMaps(wrapper);
        if (list.size() == 0) {
            return;
        }
        Set<Object> rightIds = new HashSet<>();
        list.forEach(stringObjectMap -> {
            rightIds.add(stringObjectMap.get(TableFieldUtils.getName(rightField)));
        });

        if (!RelationFieldCache.getEntityPkField(rightClazz).getName().equals(moreToMore.more())) {
            throw new MessageException("当前不支持关联字段非主键字段");
        }

        list = rightDao.selectBatchIds(rightIds);

        if (list.size() > 0) {
            getMap().put(field.getName(), list);
        }

    }

}
