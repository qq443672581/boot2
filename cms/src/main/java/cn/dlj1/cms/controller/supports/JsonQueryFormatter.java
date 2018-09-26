package cn.dlj1.cms.controller.supports;

import cn.dlj1.cms.dao.condition.Cnd;
import cn.dlj1.cms.dao.condition.CndResolver;
import cn.dlj1.cms.exception.MessageException;
import cn.dlj1.cms.utils.DateUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;


/**
 * 参数类型绑定
 * <p>
 * 适用于<b>JSON</b>提交时的绑定
 * <p>
 * <p>
 * {@link cn.dlj1.cms.request.query.Query}
 * {@link Cnd}
 */
@Configuration
public class JsonQueryFormatter {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();

        simpleModule.addDeserializer(Cnd[].class, new CndFormatter());
        simpleModule.addDeserializer(Date.class, new DateFormatter());
        simpleModule.addDeserializer(Time.class, new TimeFormatter());

        mapper.registerModule(simpleModule);
        return mapper;
    }

    /**
     * Cnd 绑定
     */
    public static class CndFormatter extends JsonDeserializer<Cnd[]> {
        @Override
        public Cnd[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            try {
                return CndResolver.parse(jsonParser.getText());
            } catch (Exception e) {
                throw new MessageException("查询条件格式错误!");
            }
        }
    }

    /**
     * 日期绑定
     * yyyy-MM-dd
     * yyyy-MM-dd HH:mm:ss
     */
    public static class DateFormatter extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String str = jsonParser.getText();
            if (null == str || str.trim().length() == 0) {
                return null;
            }
            java.util.Date date = null;
            java.sql.Date d = null;
            // 时间
            if (str.matches("\\d{4}\\-\\d{2}\\-\\d{2}")) {
                date = DateUtils.getDate(str);
                if (null == date) {
                    throw new MessageException("时间格式不正确!");
                }
                d = new java.sql.Date(date.getTime());

                //时间日期
            } else if (str.matches("\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}\\:\\d{2}\\:\\d{2}")) {
                date = DateUtils.getDateTime(str);
                if (null == date) {
                    throw new MessageException("时间格式不正确!");
                }
                d = new java.sql.Date(date.getTime());
            }
            if (null == d) {
                throw new MessageException("时间格式不正确!");
            }
            return d;
        }

    }

    /**
     * 时间绑定
     * HH:mm:ss
     */
    public static class TimeFormatter extends JsonDeserializer<java.sql.Time> {
        @Override
        public java.sql.Time deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String str = jsonParser.getText();
            if (null == str || str.trim().length() == 0) {
                return null;
            }
            java.util.Date date = null;
            java.sql.Time d = null;
            if (str.trim().matches("\\d{2}\\:\\d{2}\\:\\d{2}")) {
                date = DateUtils.getTime(str);
                if (null == date) {
                    throw new MessageException("时间格式不正确!");
                }
                d = new java.sql.Time(date.getTime());
            }
            if (null == d) {
                throw new MessageException("时间格式不正确!");
            }
            return d;
        }

    }

}
