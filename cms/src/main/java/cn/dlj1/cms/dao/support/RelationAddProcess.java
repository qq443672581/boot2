package cn.dlj1.cms.dao.support;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.dao.annotation.MoreToMore;
import cn.dlj1.cms.dao.annotation.OneToMore;
import cn.dlj1.cms.dao.annotation.OneToOne;
import cn.dlj1.cms.entity.Entity;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;

public class RelationAddProcess {

    Field[] relationFields;

    HttpServletRequest request;

    ApplicationContext context;

    Entity entity;

    boolean recursion;

    public RelationAddProcess(Field[] relationFields, ApplicationContext context, Entity entity) {
        this.relationFields = relationFields;
        this.context = context;
        this.entity = entity;
    }

    public void process() {
        if (relationFields.length == 0) {
            return;
        }

        for (int i = 0; i < relationFields.length; i++) {
            if (null != relationFields[i].getAnnotation(OneToOne.class)) {
                // 一对一
                OneToOne oneToOne = relationFields[i].getAnnotation(OneToOne.class);
                Dao dao = (Dao) context.getBean(oneToOne.mapper());
                try {
                    Object relationValue = FieldUtils.readField(entity, oneToOne.field(), true);
                    Object relationEntity = FieldUtils.readField(relationFields[i], entity, true);
                    FieldUtils.writeField(relationEntity, oneToOne.one(), relationValue, true);
                    dao._add_(request, (Entity) relationEntity, recursion);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (null != relationFields[i].getAnnotation(OneToMore.class)) {
                // 一对多
                OneToMore oneToMore = relationFields[i].getAnnotation(OneToMore.class);
                Dao dao = (Dao) context.getBean(oneToMore.mapper());
                try {
                    Object relationValue = FieldUtils.readField(entity, oneToMore.field(), true);
                    Object relationEntity = FieldUtils.readField(relationFields[i], entity, true);
                    if (null != relationEntity) {
                        List relationList = (List) relationEntity;
                        for (int j = 0; j < relationList.size(); j++) {
                            Object obj = relationList.get(j);
                            FieldUtils.writeField(obj, oneToMore.more(), relationValue, true);
                            dao._add_(request, (Entity) obj, recursion);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            } else if (null != relationFields[i].getAnnotation(MoreToMore.class)) {
                MoreToMore moreToMore = relationFields[i].getAnnotation(MoreToMore.class);
                Dao dao = (Dao) context.getBean(moreToMore.middleMapper());
                try {
                    // 关联字段
                    Object relationValue = FieldUtils.readField(entity, moreToMore.field(), true);
                    Object relationEntity = FieldUtils.readField(relationFields[i], entity, true);
                    if (null != relationEntity) {
                        List relationList = (List) relationEntity;
                        for (int j = 0; j < relationList.size(); j++) {
                            Object obj = relationList.get(j);
                            // 关联字段
                            Object fieldValue = FieldUtils.readField(obj, moreToMore.more(), true);
                            try {
                                Object middleEntity = moreToMore.middleClazz().newInstance();

                                FieldUtils.writeField(middleEntity, moreToMore.leftField(), relationValue, true);
                                FieldUtils.writeField(middleEntity, moreToMore.rightField(), fieldValue, true);

                                dao._add_(request, (Entity) middleEntity, recursion);
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }


        }
    }
}
