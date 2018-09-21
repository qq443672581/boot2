package cn.dlj1.cms.dao.condition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * cnd json解析
 *
 * @author fivewords(443672581@qq.com)
 * @date 2018年8月30日
 */
public class CndResolver {

    private static final Cnd[] empty = new Cnd[0];

    public static Cnd[] parse(String cndJson) {
        if (null == cndJson || "".equals(cndJson) ||
                !cndJson.startsWith("[") || !cndJson.endsWith("]")) {
            return empty;
        }
        JSONArray arr = JSON.parseArray(cndJson);
        Cnd[] cnds = new Cnd[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            Cnd cnd = one(arr.getJSONObject(i));
            cnds[i] = cnd;
        }
        return cnds;
    }

    /**
     * 解析一个
     *
     * @param one
     * @return
     */
    private static Cnd one(JSONObject one) {
        String typeStr = one.getString("type");
        if (null == typeStr || "".equals(typeStr)) {
            typeStr = "equals";
        }

        QueryType type = QueryType.getType(typeStr.toLowerCase());
        Cnd cnd = null;
        if (type == QueryType.BETWEEN) {
            cnd = between(one);
        } else if (type == QueryType.AND || type == QueryType.OR) {
            cnd = composite(type, one);
        } else {
            cnd = simple(type, one);
        }
        return cnd;
    }

    private static Cnd simple(QueryType type, JSONObject obj) {
        String field = obj.getString("field");
        Object value = obj.get("value");
        if (null != value && value.getClass() == JSONArray.class) {
            value = ((JSONArray) value).toArray();
        }
        return CndUtils.simple(field, type, value);
    }

    private static Cnd between(JSONObject obj) {
        String field = obj.getString("field");
        Object left = obj.get("left");
        Object right = obj.get("right");
        return CndUtils.between(field, left, right);
    }

    private static Cnd composite(QueryType type, JSONObject obj) {
        JSONArray cnds = obj.getJSONArray("cnds");
        if (null == cnds || cnds.size() == 0) {
            return null;
        }
        Conditions cnd = new Conditions();
        cnd.setType(type);
        for (int i = 0; i < cnds.size(); i++) {
            cnd.add(one(cnds.getJSONObject(i)));
        }
        return cnd;
    }


}
