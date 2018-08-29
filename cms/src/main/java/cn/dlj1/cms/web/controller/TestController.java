package cn.dlj1.cms.web.controller;

import cn.dlj1.cms.db.condition.Cnd;
import cn.dlj1.cms.request.query.Query;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Locale;

@RestController
public class TestController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping
    public String test() {
        Query query = new Query();

        ServletRequestDataBinder binder = new ServletRequestDataBinder(query);
        binder.addCustomFormatter(new Formatter<Object>() {
            @Override
            public Object parse(String s, Locale locale) throws ParseException {
                return new Cnd[3];
            }

            @Override
            public String print(Object o, Locale locale) {
                return null;
            }
        }, Cnd[].class);


        binder.bind(request);

        BindingResult result = binder.getBindingResult();

        System.out.println(JSON.toJSONString(query));


        return "xxx";
    }

}
