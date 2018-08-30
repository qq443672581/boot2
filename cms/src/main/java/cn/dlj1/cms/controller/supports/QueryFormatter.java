package cn.dlj1.cms.controller.supports;

import cn.dlj1.cms.db.condition.Cnd;
import cn.dlj1.cms.db.condition.CndResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 *
 *
 * {@link cn.dlj1.cms.request.query.Query}
 * {@link Cnd}
 */
@Configuration
public class QueryFormatter {

    @Bean
    public CndFormatter cndFormatter() {
        return new CndFormatter();
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

}
