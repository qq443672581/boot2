package cn.dlj1.boot2.component.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@JsonComponent
public class Json {

    public static class Serializer extends JsonSerializer<Object> {

        // arg0 即是Controller返回的对象
        // arg1 即是输出对象
        @Override
        public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
            // 新的map
            Map<String, Object> map = new HashMap<>(2);
            // 添加自定义值
            map.put("a", "这是一个处理添加参数");
            // 添加原返回值
            map.put("base", arg0);

            // 输出
            // raw 即未加工的 不会改变格式

            // 使用自带解析器
//            arg1.writeRaw(new ObjectMapper().writeValueAsString(map));
            //使用fastjson
            arg1.writeRaw (JSON.toJSONString(map));
        }

    }

}
