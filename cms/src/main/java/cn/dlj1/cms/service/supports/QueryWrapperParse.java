package cn.dlj1.cms.service.supports;

import cn.dlj1.cms.db.condition.Between;
import cn.dlj1.cms.db.condition.Cnd;
import cn.dlj1.cms.db.condition.Condition;
import cn.dlj1.cms.db.condition.QueryType;
import cn.dlj1.cms.request.query.Sort;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class QueryWrapperParse {

    public static void parseSort(QueryWrapper wrapper, Sort sort) {
        if (null == sort) {
            return;
        }
        wrapper.orderBy(true, "asc".equals(sort.getType()), sort.getField());
    }

    public static void parseCnd(QueryWrapper wrapper, Cnd[] cnds) {
        if (null == cnds) {
            return;
        }
        for (Cnd cnd : cnds) {
            if (null == cnd) {
                continue;
            }
            QueryType queryType = cnd.getType();
            if (QueryType.isCompoundCnd(queryType)) {
                compound(wrapper, cnd);
            } else {
                one(wrapper, (Condition) cnd);
            }
        }
    }

    private static void one(QueryWrapper wrapper, Condition condition) {

        switch (condition.getType().getEn()) {

            case "equals":
                wrapper.eq(condition.getField(), condition.getValue());
                break;
            case "not_equals":
                wrapper.ne(condition.getField(), condition.getValue());
                break;
            case "gt":
                wrapper.gt(condition.getField(), condition.getValue());
                break;
            case "get":
            case "lt":
            case "let":
            case "like":
            case "start":
            case "end":
            case "in":
            case "not_in":
            case "is":
            default:
        }


    }

    private static void compound(QueryWrapper wrapper, Cnd cnd) {
        if (cnd.getType() == QueryType.BETWEEN) {
            Between c = (Between) cnd;
            wrapper.between(c.getField(), c.getLeft(), c.getRight());
        } else if (cnd.getType() == QueryType.AND) {
        } else if (cnd.getType() == QueryType.OR) {
        }

    }


}
