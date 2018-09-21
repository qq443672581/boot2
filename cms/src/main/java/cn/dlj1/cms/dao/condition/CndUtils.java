package cn.dlj1.cms.dao.condition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Cnd 条件工具类
 *
 * @author fivewords(443672581@qq.com)
 * @date 2017年12月27日
 */
public class CndUtils {

    /**
     * 创建一个cnd数组
     *
     * @param size
     * @return
     */
    public static Cnd[] create(int size) {
        return new Cnd[size];
    }

    /**
     * 创建一个空数组
     *
     * @return
     */
    public static Cnd[] create() {
        return create(0);
    }

    public static Cnd[] add(Cnd[] cnds, Cnd cnd) {
        if (null == cnds) {
            cnds = new Cnd[0];
        }
        if (null != cnd) {
            cnds = Arrays.copyOf(cnds, cnds.length + 1);
            cnds[cnds.length - 1] = cnd;
        }
        return cnds;
    }

    /**
     * 多个
     *
     * @param cnds
     * @param cs
     * @return
     */
    public static Cnd[] add(Cnd[] cnds, Cnd[] cs) {
        if (null == cnds) {
            cnds = new Cnd[0];
        }
        if (null != cs && cs.length > 0) {
            int j = 0;
            int i = cnds.length;
            int length = cnds.length + cs.length;
            cnds = Arrays.copyOf(cnds, length);
            for (; i < length; i++) {
                cnds[i] = cs[j++];
            }
        }
        return cnds;
    }

    /**
     * 用于简单cnd
     *
     * @param field
     * @param type
     * @param value
     * @return
     */
    public static Cnd simple(String field, String type, Object value) {
        return simple(field, QueryType.getType(type), value);
    }

    public static Cnd simple(String field, QueryType type, Object value) {
        return new Condition(field, type, value);
    }

    /**
     * between
     *
     * @param field
     * @param left
     * @param right
     * @return
     */
    public static Cnd between(String field, Object left, Object right) {
        return new Between(field, left, right);
    }

    /**
     * and 多个条件
     *
     * @param cnds
     * @return
     */
    public static Cnd and(Cnd... cnds) {
        return new Conditions(QueryType.AND, cnds);
    }

    /**
     * or 多个条件
     *
     * @param cnds
     * @return
     */
    public static Cnd or(Cnd... cnds) {
        return new Conditions(QueryType.OR, cnds);
    }


    //////////////////////////////////////////////
    public static Cnd equals(String field, Object value) {
        return new Condition(field, QueryType.EQUALS, value);
    }

    public static Cnd like(String field, Object value) {
        return new Condition(field, QueryType.LIKE, value);
    }

    public static Cnd start(String field, Object value) {
        return new Condition(field, QueryType.START, value);
    }

    public static Cnd get(String field, Object value) {
        return new Condition(field, QueryType.GET, value);
    }

    public static Cnd in(String field, Object value) {
        return new Condition(field, QueryType.IN, value);
    }

    /**
     * 转为一个map
     * 普通类型会转化为 k-v
     * between       k-[v1,v2]
     * and
     * or
     *
     * @param cnds
     * @return
     */
    public static Map<String, Object> toObjMap(Cnd[] cnds) {
        if (null == cnds || cnds.length == 0) {
            return new HashMap<>(0);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        for (Cnd cnd : cnds) {
            if (QueryType.isCompoundCnd(cnd.getType())) {
                if (cnd.getType() == QueryType.BETWEEN) {
                    Between between = (Between) cnd;
                    map.put(between.getField(),
                            new Object[]{between.getLeft(), between.getRight()});
                } else if (cnd.getType() == QueryType.AND) {
                } else if (cnd.getType() == QueryType.OR) {

                }
            } else {
                Condition c = (Condition) cnd;
                map.put(c.getField(), c.getValue());
            }
        }
        return map;
    }

}
