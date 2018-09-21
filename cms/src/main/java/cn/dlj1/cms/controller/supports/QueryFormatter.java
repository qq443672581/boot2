package cn.dlj1.cms.controller.supports;

import cn.dlj1.cms.dao.condition.Cnd;
import cn.dlj1.cms.dao.condition.CndResolver;
import cn.dlj1.cms.exception.MessageException;
import cn.dlj1.cms.utils.DateUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * 参数类型绑定
 * <p>
 * <p>
 * {@link cn.dlj1.cms.request.query.Query}
 * {@link Cnd}
 */
@Configuration
public class QueryFormatter {

    @Bean
    public CndFormatter cndFormatter() {
        return new CndFormatter();
    }

    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }

    @Bean
    public TimeFormatter timeFormatter() {
        return new TimeFormatter();
    }

    public static class CndFormatter extends SuperFormatter<Cnd[]> {
        @Override
        public Cnd[] parse(String s, Locale locale) throws ParseException {
            try {
                return CndResolver.parse(s);
            } catch (Exception e) {
                throw new ParseException("查询条件结构错误!", -1);
            }
        }
    }

    public abstract static class SuperFormatter<T> implements Formatter<T> {
        @Override
        public abstract T parse(String s, Locale locale) throws ParseException;

        @Override
        public String print(T o, Locale locale) {
            return null;
        }
    }

    /**
     * 日期绑定
     */
    public static class DateFormatter extends SuperFormatter<java.sql.Date> {
        @Override
        public java.sql.Date parse(String s, Locale locale) throws ParseException {
            if (null == s || s.trim().length() == 0) {
                return null;
            }
            java.util.Date date = null;
            java.sql.Date d = null;
            if (s.matches("\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}\\:\\d{2}\\:\\d{2}")) {
                date = DateUtils.getDateTime(s);
                if (null == date) {
                    throw new MessageException("时间格式不正确!");
                }
                d = new java.sql.Date(date.getTime());
            } else if (s.matches("\\d{4}\\-\\d{2}\\-\\d{2}")) {
                date = DateUtils.getDate(s);
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
     */
    public static class TimeFormatter extends SuperFormatter<java.sql.Time> {
        @Override
        public java.sql.Time parse(String s, Locale locale) throws ParseException {
            if (null == s || s.trim().length() == 0) {
                return null;
            }
            java.util.Date date = null;
            java.sql.Time d = null;
            if (s.matches("\\d{2}\\:\\d{2}\\:\\d{2}")) {
                date = DateUtils.getTime(s);
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
