package {{request.packageName}}.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class {{request.upperCamelCaseName}}Initializer implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent event) {
        }

        @Override
        public void contextDestroyed(ServletContextEvent event){
        }
}