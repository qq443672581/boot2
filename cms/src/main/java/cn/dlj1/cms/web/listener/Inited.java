package cn.dlj1.cms.web.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Inited implements CommandLineRunner {

    private static Log log = LogFactory.getLog(Inited.class);

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) throws Exception {

    }
}
