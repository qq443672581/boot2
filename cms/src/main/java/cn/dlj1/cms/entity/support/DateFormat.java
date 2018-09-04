package cn.dlj1.cms.entity.support;

import cn.dlj1.cms.utils.DateUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 日期格式化
 *
 *
 */
public class DateFormat {

    private static abstract class Base extends JsonSerializer {

        public abstract String getFormat();

        public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2)
                throws IOException, JsonProcessingException {
            if (null == arg0) {
                return;
            }
            arg1.writeString(DateUtils.getDateString((java.util.Date) arg0, getFormat()));
        }
    }

    public static class Date extends Base {
        @Override
        public String getFormat() {
            return DateUtils.FORMAT_DATE;
        }
    }

    public static class Time extends Base {
        @Override
        public String getFormat() {
            return DateUtils.FORMAT_TIME;
        }
    }

    public static class DateTime extends Base {
        @Override
        public String getFormat() {
            return DateUtils.FORMAT_DATETIME;
        }
    }

}
